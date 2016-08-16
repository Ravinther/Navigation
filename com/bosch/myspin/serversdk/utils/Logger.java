package com.bosch.myspin.serversdk.utils;

import android.os.Environment;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Logger {
    private static File f411a;
    private static boolean f412b;
    private static long f413c;
    private static LogLevel f414d;
    private static boolean f415e;
    private static BufferedWriter f416f;

    public enum LogComponent {
        SDKMain(1),
        MySpinService(2),
        MySpinProtocol(4),
        PhoneCall(8),
        NavigateTo(16),
        VoiceControl(32),
        ScreenCapturing(64),
        TouchInjection(128),
        Connection(256),
        EventListener(512),
        Keyboard(1024),
        Maps(2048),
        UI(4096),
        Config(8192),
        AppTransitions(16384),
        None(0),
        All(Long.MAX_VALUE),
        FilterConnectivity(((MySpinProtocol.value() | MySpinService.value()) | Connection.value()) | AppTransitions.value()),
        FilterInput(Keyboard.value() | TouchInjection.value()),
        FilterUIElements(Maps.value() | UI.value()),
        FilterServices((NavigateTo.value() | VoiceControl.value()) | PhoneCall.value()),
        FilterSystem(((((FilterConnectivity.value() | SDKMain.value()) | ScreenCapturing.value()) | TouchInjection.value()) | MySpinProtocol.value()) | UI.value());
        
        private final long f408a;

        private LogComponent(long j) {
            this.f408a = j;
        }

        public long value() {
            return this.f408a;
        }
    }

    public enum LogLevel {
        ERROR(1),
        WARN(2),
        INFO(3),
        DEBUG(4);
        
        private final int f410a;

        private LogLevel(int i) {
            this.f410a = i;
        }

        public int toInt() {
            return this.f410a;
        }
    }

    static {
        m352a();
    }

    public static void setComponent(LogComponent... logComponentArr) {
        long j = 0;
        for (LogComponent value : logComponentArr) {
            j |= value.value();
        }
        f413c = j;
    }

    public static void setLevel(LogLevel logLevel) {
        f414d = logLevel;
    }

    public static void setIsDetailed(boolean z) {
        f415e = z;
    }

    public static void setConfig(LogLevel logLevel, boolean z, LogComponent... logComponentArr) {
        setLevel(logLevel);
        setComponent(logComponentArr);
        setIsDetailed(z);
    }

    private static int m350a(LogLevel logLevel, long j, String str, String str2, Throwable th) {
        if ((f413c & j) == 0 || (logLevel.toInt() > f414d.toInt() && logLevel.toInt() != LogLevel.ERROR.toInt())) {
            return 0;
        }
        int i = logLevel.toInt() <= LogLevel.ERROR.toInt() ? 6 : logLevel == LogLevel.WARN ? 5 : 4;
        Object stringBuilder = new StringBuilder();
        Object stringBuilder2 = new StringBuilder();
        stringBuilder.append('[').append(logLevel.toString().charAt(0)).append('/').append(str).append(']');
        if (f415e) {
            stringBuilder2.append('[').append(new Throwable().fillInStackTrace().getStackTrace()[2].getLineNumber()).append(']');
        }
        stringBuilder2.append(str2);
        if (th != null) {
            stringBuilder2.append('\n').append(Log.getStackTraceString(th));
        }
        if (f416f != null) {
            try {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(stringBuilder).append('\t');
                stringBuilder3.append(stringBuilder2).append('\n');
                f416f.write(stringBuilder3.toString());
                f416f.newLine();
                f416f.flush();
            } catch (IOException e) {
                Log.e("Config", e.getMessage());
            }
        }
        return Log.println(i, stringBuilder.toString(), stringBuilder2.toString());
    }

    public static int logDebug(LogComponent logComponent, String str) {
        return m350a(LogLevel.DEBUG, logComponent.value(), logComponent.name(), str, null);
    }

    public static int logInfo(LogComponent logComponent, String str) {
        return m350a(LogLevel.INFO, logComponent.value(), logComponent.name(), str, null);
    }

    public static int logWarning(LogComponent logComponent, String str) {
        return m350a(LogLevel.WARN, logComponent.value(), logComponent.name(), str, null);
    }

    public static int logError(LogComponent logComponent, String str) {
        return m350a(LogLevel.ERROR, logComponent.value(), logComponent.name(), str, null);
    }

    public static int logInfo(LogComponent logComponent, String str, Throwable th) {
        return m350a(LogLevel.INFO, logComponent.value(), logComponent.name(), str, th);
    }

    public static int logWarning(LogComponent logComponent, String str, Throwable th) {
        return m350a(LogLevel.WARN, logComponent.value(), logComponent.name(), str, th);
    }

    public static int logError(LogComponent logComponent, String str, Throwable th) {
        return m350a(LogLevel.ERROR, logComponent.value(), logComponent.name(), str, th);
    }

    private static boolean m352a() {
        if (f412b) {
            return false;
        }
        try {
            if (m356c()) {
                f411a = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "myspin");
                if (m353a(new File(f411a, "config.ini"))) {
                    m355b();
                    f412b = true;
                    Log.i(LogComponent.Config.name(), "Logger/prepare: Read log config from log file is: " + f414d + ", " + f413c + ", " + f415e);
                    return f412b;
                }
                f412b = false;
                return f412b;
            }
            throw new C0233d("External storage is not read- and writeable. Log file manager stopped.");
        } catch (C0233d e) {
            Log.w(LogComponent.Config.name(), "Logger/" + e.getMessage());
        }
    }

    private static void m355b() {
        File file = new File(f411a, "mySPINLogs.log");
        try {
            if (file.createNewFile()) {
                Log.d(LogComponent.Config.name(), "Logger/Found " + file.getAbsolutePath() + " is exists");
            }
            f416f = new BufferedWriter(new FileWriter(file, true));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
            f416f.write("======================================================================");
            f416f.newLine();
            f416f.write(simpleDateFormat.format(new Date()));
            f416f.newLine();
            f416f.write("======================================================================");
            f416f.newLine();
        } catch (IOException e) {
            Log.w(LogComponent.Config.name(), "Logger/createLogFile: " + e.getMessage());
        }
    }

    private static boolean m356c() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean m353a(java.io.File r8) throws com.bosch.myspin.serversdk.utils.C0233d {
        /*
        r0 = 1;
        r1 = 0;
        r2 = new com.bosch.myspin.serversdk.utils.a;
        r2.<init>();
        r3 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3.<init>(r8);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r2.load(r3);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3 = "myspin.loglevel";
        r3 = r2.getProperty(r3);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r4 = "myspin.logcomponent";
        r4 = r2.getProperty(r4);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = "myspin.logdetails";
        r2 = r2.getProperty(r5);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = com.bosch.myspin.serversdk.utils.Logger.LogComponent.Config;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = r5.name();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6.<init>();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r7 = "Logger/readConfig: config.ini found (";
        r6 = r6.append(r7);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r7 = f411a;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r7 = r7.getAbsolutePath();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = r6.append(r7);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r7 = ")";
        r6 = r6.append(r7);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = r6.toString();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        android.util.Log.i(r5, r6);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = m354b(r4);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        f413c = r6;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = m351a(r3);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        setLevel(r5);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = m357c(r2);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        setIsDetailed(r5);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = com.bosch.myspin.serversdk.utils.Logger.LogComponent.Config;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = r5.name();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6.<init>();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r7 = "Logger/readConfig: myspin.loglevel=";
        r6 = r6.append(r7);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3 = r6.append(r3);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = " ==> ";
        r3 = r3.append(r6);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = f414d;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3 = r3.append(r6);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3 = r3.toString();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        android.util.Log.i(r5, r3);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3 = com.bosch.myspin.serversdk.utils.Logger.LogComponent.Config;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3 = r3.name();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5.<init>();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = "Logger/readConfig: myspin.logcomponent=";
        r5 = r5.append(r6);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r4 = r5.append(r4);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = " ==> ";
        r4 = r4.append(r5);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r6 = f413c;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r4 = r4.append(r6);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r4 = r4.toString();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        android.util.Log.i(r3, r4);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3 = com.bosch.myspin.serversdk.utils.Logger.LogComponent.Config;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r3 = r3.name();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r4 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r4.<init>();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r5 = "Logger/readConfig: myspin.logdetails=";
        r4 = r4.append(r5);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r2 = r4.append(r2);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r4 = " ==> ";
        r2 = r2.append(r4);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r4 = f415e;	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r2 = r2.append(r4);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        r2 = r2.toString();	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
        android.util.Log.i(r3, r2);	 Catch:{ FileNotFoundException -> 0x00e1, IOException -> 0x014d }
    L_0x00e0:
        return r0;
    L_0x00e1:
        r2 = move-exception;
        r3 = com.bosch.myspin.serversdk.utils.Logger.LogLevel.INFO;
        r0 = new com.bosch.myspin.serversdk.utils.Logger.LogComponent[r0];
        r4 = com.bosch.myspin.serversdk.utils.Logger.LogComponent.All;
        r0[r1] = r4;
        setConfig(r3, r1, r0);
        r0 = "Config";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Logger/readConfig: ";
        r3 = r3.append(r4);
        r2 = r2.getMessage();
        r2 = r3.append(r2);
        r2 = r2.toString();
        android.util.Log.w(r0, r2);
    L_0x010b:
        r0 = com.bosch.myspin.serversdk.utils.Logger.LogComponent.Config;
        r0 = r0.name();
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Logger/readConfig: use default configuration (";
        r2 = r2.append(r3);
        r3 = f414d;
        r2 = r2.append(r3);
        r3 = ", ";
        r2 = r2.append(r3);
        r4 = f413c;
        r2 = r2.append(r4);
        r3 = ", ";
        r2 = r2.append(r3);
        r3 = f415e;
        r2 = r2.append(r3);
        r3 = ")";
        r2 = r2.append(r3);
        r2 = r2.toString();
        android.util.Log.i(r0, r2);
        r0 = r1;
        goto L_0x00e0;
    L_0x014d:
        r2 = move-exception;
        r3 = com.bosch.myspin.serversdk.utils.Logger.LogLevel.INFO;
        r0 = new com.bosch.myspin.serversdk.utils.Logger.LogComponent[r0];
        r4 = com.bosch.myspin.serversdk.utils.Logger.LogComponent.All;
        r0[r1] = r4;
        setConfig(r3, r1, r0);
        r0 = "Config";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Logger/readConfig: ";
        r3 = r3.append(r4);
        r2 = r2.getMessage();
        r2 = r3.append(r2);
        r2 = r2.toString();
        android.util.Log.w(r0, r2);
        goto L_0x010b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bosch.myspin.serversdk.utils.Logger.a(java.io.File):boolean");
    }

    private static LogLevel m351a(String str) {
        try {
            String toUpperCase = str.trim().substring(0, 1).toUpperCase(Locale.getDefault());
            if (toUpperCase.equals("D")) {
                return LogLevel.DEBUG;
            }
            if (toUpperCase.equals("W")) {
                return LogLevel.WARN;
            }
            if (toUpperCase.equals("I")) {
                return LogLevel.INFO;
            }
            return LogLevel.ERROR;
        } catch (IndexOutOfBoundsException e) {
            Log.w(LogComponent.Config.name(), "Logger/parseLevelString: Log level not found!");
        } catch (NullPointerException e2) {
            Log.w(LogComponent.Config.name(), "Logger/parseLevelString: Null log level");
        }
    }

    private static long m354b(String str) {
        int i = 0;
        String[] split = str.split(",");
        if (split != null && split.length > 0) {
            if (split.length > 1) {
                long j = 0;
                for (String str2 : split) {
                    for (LogComponent logComponent : LogComponent.values()) {
                        if (str2.equalsIgnoreCase(logComponent.name())) {
                            j |= logComponent.value();
                            break;
                        }
                    }
                }
                return j;
            }
            LogComponent[] values = LogComponent.values();
            int length = values.length;
            while (i < length) {
                LogComponent logComponent2 = values[i];
                if (str.equalsIgnoreCase(logComponent2.name())) {
                    return logComponent2.value();
                }
                i++;
            }
        }
        Log.i(LogComponent.Config.name(), "Logger/parseComponentString: The component not found!");
        return LogComponent.None.value();
    }

    private static boolean m357c(String str) {
        return Boolean.parseBoolean(str);
    }
}
