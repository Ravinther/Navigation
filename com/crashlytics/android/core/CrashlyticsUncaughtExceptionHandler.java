package com.crashlytics.android.core;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CrashlyticsUncaughtExceptionHandler implements UncaughtExceptionHandler {
    static final FilenameFilter ANY_SESSION_FILENAME_FILTER;
    static final Comparator<File> LARGEST_FILE_NAME_FIRST;
    private static final Map<String, String> SEND_AT_CRASHTIME_HEADER;
    static final FilenameFilter SESSION_FILE_FILTER;
    private static final Pattern SESSION_FILE_PATTERN;
    static final Comparator<File> SMALLEST_FILE_NAME_FIRST;
    private final CrashlyticsCore crashlyticsCore;
    private final UncaughtExceptionHandler defaultHandler;
    private final AtomicInteger eventCounter;
    private final CrashlyticsExecutorServiceWrapper executorServiceWrapper;
    private final File filesDir;
    private final IdManager idManager;
    private final AtomicBoolean isHandlingException;
    private final LogFileManager logFileManager;
    private boolean powerConnected;
    private final BroadcastReceiver powerConnectedReceiver;
    private final BroadcastReceiver powerDisconnectedReceiver;
    private final AtomicBoolean receiversRegistered;
    private final SessionDataWriter sessionDataWriter;

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.11 */
    class AnonymousClass11 implements Callable<Void> {
        final /* synthetic */ Map val$keyData;

        AnonymousClass11(Map map) {
            this.val$keyData = map;
        }

        public Void call() throws Exception {
            new MetaDataStore(CrashlyticsUncaughtExceptionHandler.this.filesDir).writeKeyData(CrashlyticsUncaughtExceptionHandler.this.getCurrentSessionId(), this.val$keyData);
            return null;
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.15 */
    class AnonymousClass15 implements FilenameFilter {
        final /* synthetic */ String val$sessionId;

        AnonymousClass15(String str) {
            this.val$sessionId = str;
        }

        public boolean accept(File f, String name) {
            return name.startsWith(this.val$sessionId);
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.16 */
    class AnonymousClass16 implements Runnable {
        final /* synthetic */ File val$toSend;

        AnonymousClass16(File file) {
            this.val$toSend = file;
        }

        public void run() {
            if (CommonUtils.canTryConnection(CrashlyticsUncaughtExceptionHandler.this.crashlyticsCore.getContext())) {
                Fabric.getLogger().m1453d("Fabric", "Attempting to send crash report at time of crash...");
                CreateReportSpiCall call = CrashlyticsUncaughtExceptionHandler.this.crashlyticsCore.getCreateReportSpiCall(Settings.getInstance().awaitSettingsData());
                if (call != null) {
                    new ReportUploader(call).forceUpload(new SessionReport(this.val$toSend, CrashlyticsUncaughtExceptionHandler.SEND_AT_CRASHTIME_HEADER));
                }
            }
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.1 */
    static class C02911 implements FilenameFilter {
        C02911() {
        }

        public boolean accept(File dir, String filename) {
            return filename.length() == ".cls".length() + 35 && filename.endsWith(".cls");
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.2 */
    static class C02922 implements Comparator<File> {
        C02922() {
        }

        public int compare(File file1, File file2) {
            return file2.getName().compareTo(file1.getName());
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.3 */
    static class C02933 implements Comparator<File> {
        C02933() {
        }

        public int compare(File file1, File file2) {
            return file1.getName().compareTo(file2.getName());
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.4 */
    static class C02944 implements FilenameFilter {
        C02944() {
        }

        public boolean accept(File file, String filename) {
            return CrashlyticsUncaughtExceptionHandler.SESSION_FILE_PATTERN.matcher(filename).matches();
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.5 */
    class C02955 extends BroadcastReceiver {
        C02955() {
        }

        public void onReceive(Context context, Intent intent) {
            CrashlyticsUncaughtExceptionHandler.this.powerConnected = true;
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.6 */
    class C02966 extends BroadcastReceiver {
        C02966() {
        }

        public void onReceive(Context context, Intent intent) {
            CrashlyticsUncaughtExceptionHandler.this.powerConnected = false;
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.7 */
    class C02977 implements Callable<Void> {
        final /* synthetic */ Throwable val$ex;
        final /* synthetic */ Date val$now;
        final /* synthetic */ Thread val$thread;

        C02977(Date date, Thread thread, Throwable th) {
            this.val$now = date;
            this.val$thread = thread;
            this.val$ex = th;
        }

        public Void call() throws Exception {
            CrashlyticsUncaughtExceptionHandler.this.handleUncaughtException(this.val$now, this.val$thread, this.val$ex);
            return null;
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.8 */
    class C02988 implements Callable<Void> {
        final /* synthetic */ String val$msg;
        final /* synthetic */ long val$timestamp;

        C02988(long j, String str) {
            this.val$timestamp = j;
            this.val$msg = str;
        }

        public Void call() throws Exception {
            if (!CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get()) {
                CrashlyticsUncaughtExceptionHandler.this.logFileManager.writeToLog(this.val$timestamp, this.val$msg);
            }
            return null;
        }
    }

    /* renamed from: com.crashlytics.android.core.CrashlyticsUncaughtExceptionHandler.9 */
    class C02999 implements Runnable {
        final /* synthetic */ Throwable val$ex;
        final /* synthetic */ Date val$now;
        final /* synthetic */ Thread val$thread;

        C02999(Date date, Thread thread, Throwable th) {
            this.val$now = date;
            this.val$thread = thread;
            this.val$ex = th;
        }

        public void run() {
            if (!CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get()) {
                CrashlyticsUncaughtExceptionHandler.this.doWriteNonFatal(this.val$now, this.val$thread, this.val$ex);
            }
        }
    }

    private static class AnySessionPartFileFilter implements FilenameFilter {
        private AnySessionPartFileFilter() {
        }

        public boolean accept(File file, String fileName) {
            return !CrashlyticsUncaughtExceptionHandler.SESSION_FILE_FILTER.accept(file, fileName) && CrashlyticsUncaughtExceptionHandler.SESSION_FILE_PATTERN.matcher(fileName).matches();
        }
    }

    static class FileNameContainsFilter implements FilenameFilter {
        private final String string;

        public FileNameContainsFilter(String s) {
            this.string = s;
        }

        public boolean accept(File dir, String filename) {
            return filename.contains(this.string) && !filename.endsWith(".cls_temp");
        }
    }

    static class SessionPartFileFilter implements FilenameFilter {
        private final String sessionId;

        public SessionPartFileFilter(String sessionId) {
            this.sessionId = sessionId;
        }

        public boolean accept(File file, String fileName) {
            if (fileName.equals(this.sessionId + ".cls") || !fileName.contains(this.sessionId) || fileName.endsWith(".cls_temp")) {
                return false;
            }
            return true;
        }
    }

    static {
        SESSION_FILE_FILTER = new C02911();
        LARGEST_FILE_NAME_FIRST = new C02922();
        SMALLEST_FILE_NAME_FIRST = new C02933();
        ANY_SESSION_FILENAME_FILTER = new C02944();
        SESSION_FILE_PATTERN = Pattern.compile("([\\d|A-Z|a-z]{12}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{4}\\-[\\d|A-Z|a-z]{12}).+");
        SEND_AT_CRASHTIME_HEADER = Collections.singletonMap("X-CRASHLYTICS-SEND-FLAGS", "1");
    }

    CrashlyticsUncaughtExceptionHandler(UncaughtExceptionHandler handler, CrashlyticsListener listener, CrashlyticsExecutorServiceWrapper executorServiceWrapper, IdManager idManager, SessionDataWriter sessionDataWriter, CrashlyticsCore crashlyticsCore) {
        this.eventCounter = new AtomicInteger(0);
        this.receiversRegistered = new AtomicBoolean(false);
        this.defaultHandler = handler;
        this.executorServiceWrapper = executorServiceWrapper;
        this.idManager = idManager;
        this.crashlyticsCore = crashlyticsCore;
        this.sessionDataWriter = sessionDataWriter;
        this.isHandlingException = new AtomicBoolean(false);
        this.filesDir = crashlyticsCore.getSdkDirectory();
        this.logFileManager = new LogFileManager(crashlyticsCore.getContext(), this.filesDir);
        notifyCrashlyticsListenerOfPreviousCrash(listener);
        this.powerConnectedReceiver = new C02955();
        IntentFilter powerConnectedFilter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
        this.powerDisconnectedReceiver = new C02966();
        IntentFilter powerDisconnectedFilter = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
        Context context = crashlyticsCore.getContext();
        context.registerReceiver(this.powerConnectedReceiver, powerConnectedFilter);
        context.registerReceiver(this.powerDisconnectedReceiver, powerDisconnectedFilter);
        this.receiversRegistered.set(true);
    }

    public synchronized void uncaughtException(Thread thread, Throwable ex) {
        this.isHandlingException.set(true);
        try {
            Fabric.getLogger().m1453d("Fabric", "Crashlytics is handling uncaught exception \"" + ex + "\" from thread " + thread.getName());
            if (!this.receiversRegistered.getAndSet(true)) {
                Fabric.getLogger().m1453d("Fabric", "Unregistering power receivers.");
                Context context = this.crashlyticsCore.getContext();
                context.unregisterReceiver(this.powerConnectedReceiver);
                context.unregisterReceiver(this.powerDisconnectedReceiver);
            }
            this.executorServiceWrapper.executeSyncLoggingException(new C02977(new Date(), thread, ex));
            Fabric.getLogger().m1453d("Fabric", "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, ex);
            this.isHandlingException.set(false);
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Fabric", "An error occurred in the uncaught exception handler", e);
            Fabric.getLogger().m1453d("Fabric", "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, ex);
            this.isHandlingException.set(false);
        } catch (Throwable th) {
            Fabric.getLogger().m1453d("Fabric", "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, ex);
            this.isHandlingException.set(false);
        }
    }

    private void handleUncaughtException(Date time, Thread thread, Throwable ex) throws Exception {
        writeFatal(time, thread, ex);
        doCloseSessions();
        doOpenSession();
        trimSessionFiles();
        if (!this.crashlyticsCore.shouldPromptUserBeforeSendingCrashReports()) {
            sendSessionReports();
        }
    }

    boolean isHandlingException() {
        return this.isHandlingException.get();
    }

    private void notifyCrashlyticsListenerOfPreviousCrash(CrashlyticsListener listener) {
        Fabric.getLogger().m1453d("Fabric", "Checking for previous crash marker.");
        File markerFile = new File(this.crashlyticsCore.getSdkDirectory(), "crash_marker");
        if (markerFile.exists()) {
            markerFile.delete();
            if (listener != null) {
                try {
                    listener.crashlyticsDidDetectCrashDuringPreviousExecution();
                } catch (Exception e) {
                    Fabric.getLogger().m1456e("Fabric", "Exception thrown by CrashlyticsListener while notifying of previous crash.", e);
                }
            }
        }
    }

    void writeToLog(long timestamp, String msg) {
        this.executorServiceWrapper.executeAsync(new C02988(timestamp, msg));
    }

    void writeNonFatalException(Thread thread, Throwable ex) {
        this.executorServiceWrapper.executeAsync(new C02999(new Date(), thread, ex));
    }

    private void writeFatal(Date time, Thread thread, Throwable ex) {
        Exception e;
        Throwable th;
        ClsFileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            new File(this.filesDir, "crash_marker").createNewFile();
            String currentSessionId = getCurrentSessionId();
            if (currentSessionId != null) {
                CrashlyticsCore.recordFatalExceptionEvent(currentSessionId);
                ClsFileOutputStream fos2 = new ClsFileOutputStream(this.filesDir, currentSessionId + "SessionCrash");
                try {
                    cos = CodedOutputStream.newInstance(fos2);
                    writeSessionEvent(cos, time, thread, ex, "crash", true);
                    fos = fos2;
                } catch (Exception e2) {
                    e = e2;
                    fos = fos2;
                    try {
                        Fabric.getLogger().m1456e("Fabric", "An error occurred in the fatal exception logger", e);
                        ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                        CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fos = fos2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                    throw th;
                }
            }
            Fabric.getLogger().m1456e("Fabric", "Tried to write a fatal exception while no session was open.", null);
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().m1456e("Fabric", "An error occurred in the fatal exception logger", e);
            ExceptionUtils.writeStackTraceIfNotNull(e, fos);
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
        }
    }

    private void writeExternalCrashEvent(SessionEventData crashEventData) throws IOException {
        Exception e;
        Throwable th;
        ClsFileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            String currentSessionId = getCurrentSessionId();
            if (currentSessionId != null) {
                CrashlyticsCore.recordFatalExceptionEvent(currentSessionId);
                ClsFileOutputStream fos2 = new ClsFileOutputStream(this.filesDir, currentSessionId + "SessionCrash");
                try {
                    cos = CodedOutputStream.newInstance(fos2);
                    NativeCrashWriter.writeNativeCrash(crashEventData, this.logFileManager, new MetaDataStore(this.filesDir).readKeyData(currentSessionId), cos);
                    fos = fos2;
                } catch (Exception e2) {
                    e = e2;
                    fos = fos2;
                    try {
                        Fabric.getLogger().m1456e("Fabric", "An error occurred in the native crash logger", e);
                        ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                        CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                        CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fos = fos2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
                    throw th;
                }
            }
            Fabric.getLogger().m1456e("Fabric", "Tried to write a native crash while no session was open.", null);
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().m1456e("Fabric", "An error occurred in the native crash logger", e);
            ExceptionUtils.writeStackTraceIfNotNull(e, fos);
            CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
            CommonUtils.closeOrLog(fos, "Failed to close fatal exception file output stream.");
        }
    }

    void cacheKeyData(Map<String, String> keyData) {
        this.executorServiceWrapper.executeAsync(new AnonymousClass11(keyData));
    }

    void ensureOpenSessionExists() {
        this.executorServiceWrapper.executeAsync(new Callable<Void>() {
            public Void call() throws Exception {
                if (CrashlyticsUncaughtExceptionHandler.this.hasOpenSession()) {
                    CrashlyticsUncaughtExceptionHandler.this.logFileManager.onSessionChange(CrashlyticsUncaughtExceptionHandler.this.getCurrentSessionId());
                } else {
                    CrashlyticsUncaughtExceptionHandler.this.doOpenSession();
                }
                return null;
            }
        });
    }

    private String getCurrentSessionId() {
        File[] sessionBeginFiles = listFilesMatching(new FileNameContainsFilter("BeginSession"));
        Arrays.sort(sessionBeginFiles, LARGEST_FILE_NAME_FIRST);
        return sessionBeginFiles.length > 0 ? getSessionIdFromSessionFile(sessionBeginFiles[0]) : null;
    }

    private String getSessionIdFromSessionFile(File sessionFile) {
        return sessionFile.getName().substring(0, 35);
    }

    boolean hasOpenSession() {
        return listSessionBeginFiles().length > 0;
    }

    boolean finalizeSessions() {
        return ((Boolean) this.executorServiceWrapper.executeSyncLoggingException(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                if (CrashlyticsUncaughtExceptionHandler.this.isHandlingException.get()) {
                    Fabric.getLogger().m1453d("Fabric", "Skipping session finalization because a crash has already occurred.");
                    return Boolean.valueOf(false);
                }
                SessionEventData crashEventData = CrashlyticsUncaughtExceptionHandler.this.crashlyticsCore.getExternalCrashEventData();
                if (crashEventData != null) {
                    CrashlyticsUncaughtExceptionHandler.this.writeExternalCrashEvent(crashEventData);
                }
                CrashlyticsUncaughtExceptionHandler.this.doCloseSessions();
                CrashlyticsUncaughtExceptionHandler.this.doOpenSession();
                Fabric.getLogger().m1453d("Fabric", "Open sessions were closed and a new session was opened.");
                return Boolean.valueOf(true);
            }
        })).booleanValue();
    }

    private void doOpenSession() throws Exception {
        Date startedAt = new Date();
        String sessionIdentifier = new CLSUUID(this.idManager).toString();
        Fabric.getLogger().m1453d("Fabric", "Opening an new session with ID " + sessionIdentifier);
        this.logFileManager.onSessionChange(sessionIdentifier);
        writeBeginSession(sessionIdentifier, startedAt);
        writeSessionApp(sessionIdentifier);
        writeSessionOS(sessionIdentifier);
        writeSessionDevice(sessionIdentifier);
    }

    private void doCloseSessions() throws Exception {
        trimOpenSessions(8);
        String currentSessionId = getCurrentSessionId();
        if (currentSessionId != null) {
            writeSessionUser(currentSessionId);
            SessionSettingsData settingsData = this.crashlyticsCore.getSessionSettingsData();
            if (settingsData != null) {
                int maxLoggedExceptionsCount = settingsData.maxCustomExceptionEvents;
                Fabric.getLogger().m1453d("Fabric", "Closing all open sessions.");
                File[] sessionBeginFiles = listSessionBeginFiles();
                if (sessionBeginFiles != null && sessionBeginFiles.length > 0) {
                    for (File sessionBeginFile : sessionBeginFiles) {
                        String sessionIdentifier = getSessionIdFromSessionFile(sessionBeginFile);
                        Fabric.getLogger().m1453d("Fabric", "Closing session: " + sessionIdentifier);
                        writeSessionPartsToSessionFile(sessionBeginFile, sessionIdentifier, maxLoggedExceptionsCount);
                    }
                    return;
                }
                return;
            }
            Fabric.getLogger().m1453d("Fabric", "Unable to close session. Settings are not loaded.");
            return;
        }
        Fabric.getLogger().m1453d("Fabric", "No open sessions exist.");
    }

    private void closeWithoutRenamingOrLog(ClsFileOutputStream fos) {
        if (fos != null) {
            try {
                fos.closeInProgressStream();
            } catch (IOException ex) {
                Fabric.getLogger().m1456e("Fabric", "Error closing session file stream in the presence of an exception", ex);
            }
        }
    }

    private void deleteSessionPartFilesFor(String sessionId) {
        for (File file : listSessionPartFilesFor(sessionId)) {
            file.delete();
        }
    }

    private File[] listSessionPartFilesFor(String sessionId) {
        return listFilesMatching(new SessionPartFileFilter(sessionId));
    }

    private File[] listCompleteSessionFiles() {
        return listFilesMatching(SESSION_FILE_FILTER);
    }

    File[] listSessionBeginFiles() {
        return listFilesMatching(new FileNameContainsFilter("BeginSession"));
    }

    private File[] listFilesMatching(FilenameFilter filter) {
        return ensureFileArrayNotNull(this.filesDir.listFiles(filter));
    }

    private File[] ensureFileArrayNotNull(File[] files) {
        return files == null ? new File[0] : files;
    }

    private void trimSessionEventFiles(String sessionId, int limit) {
        Utils.capFileCount(this.filesDir, new FileNameContainsFilter(sessionId + "SessionEvent"), limit, SMALLEST_FILE_NAME_FIRST);
    }

    void trimSessionFiles() {
        Utils.capFileCount(this.filesDir, SESSION_FILE_FILTER, 4, SMALLEST_FILE_NAME_FIRST);
    }

    private void trimOpenSessions(int maxOpenSessionCount) {
        Set<String> sessionIdsToKeep = new HashSet();
        File[] beginSessionFiles = listSessionBeginFiles();
        Arrays.sort(beginSessionFiles, LARGEST_FILE_NAME_FIRST);
        int count = Math.min(maxOpenSessionCount, beginSessionFiles.length);
        for (int i = 0; i < count; i++) {
            sessionIdsToKeep.add(getSessionIdFromSessionFile(beginSessionFiles[i]));
        }
        for (File sessionPartFile : listFilesMatching(new AnySessionPartFileFilter())) {
            String fileName = sessionPartFile.getName();
            Matcher matcher = SESSION_FILE_PATTERN.matcher(fileName);
            matcher.matches();
            if (!sessionIdsToKeep.contains(matcher.group(1))) {
                Fabric.getLogger().m1453d("Fabric", "Trimming open session file: " + fileName);
                sessionPartFile.delete();
            }
        }
    }

    void cleanInvalidTempFiles() {
        this.executorServiceWrapper.executeAsync(new Runnable() {
            public void run() {
                CrashlyticsUncaughtExceptionHandler.this.doCleanInvalidTempFiles(CrashlyticsUncaughtExceptionHandler.this.listFilesMatching(ClsFileOutputStream.TEMP_FILENAME_FILTER));
            }
        });
    }

    void doCleanInvalidTempFiles(File[] invalidFiles) {
        deleteLegacyInvalidCacheDir();
        for (File invalidFile : invalidFiles) {
            Fabric.getLogger().m1453d("Fabric", "Found invalid session part file: " + invalidFile);
            String sessionId = getSessionIdFromSessionFile(invalidFile);
            FilenameFilter sessionFilter = new AnonymousClass15(sessionId);
            Fabric.getLogger().m1453d("Fabric", "Deleting all part files for invalid session: " + sessionId);
            for (File sessionFile : listFilesMatching(sessionFilter)) {
                Fabric.getLogger().m1453d("Fabric", "Deleting session file: " + sessionFile);
                sessionFile.delete();
            }
        }
    }

    private void deleteLegacyInvalidCacheDir() {
        File cacheDir = new File(this.crashlyticsCore.getSdkDirectory(), "invalidClsFiles");
        if (cacheDir.exists()) {
            if (cacheDir.isDirectory()) {
                for (File cacheFile : cacheDir.listFiles()) {
                    cacheFile.delete();
                }
            }
            cacheDir.delete();
        }
    }

    private void writeBeginSession(String sessionId, Date startedAt) throws Exception {
        Exception e;
        Throwable th;
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            FileOutputStream fos2 = new ClsFileOutputStream(this.filesDir, sessionId + "BeginSession");
            try {
                cos = CodedOutputStream.newInstance(fos2);
                this.sessionDataWriter.writeBeginSession(cos, sessionId, String.format(Locale.US, "Crashlytics Android SDK/%s", new Object[]{this.crashlyticsCore.getVersion()}), startedAt.getTime() / 1000);
                CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(fos2, "Failed to close begin session file.");
            } catch (Exception e2) {
                e = e2;
                fos = fos2;
                try {
                    ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                    CommonUtils.closeOrLog(fos, "Failed to close begin session file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos2;
                CommonUtils.flushOrLog(cos, "Failed to flush to session begin file.");
                CommonUtils.closeOrLog(fos, "Failed to close begin session file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            ExceptionUtils.writeStackTraceIfNotNull(e, fos);
            throw e;
        }
    }

    private void writeSessionApp(String sessionId) throws Exception {
        Exception e;
        Throwable th;
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            FileOutputStream fos2 = new ClsFileOutputStream(this.filesDir, sessionId + "SessionApp");
            try {
                cos = CodedOutputStream.newInstance(fos2);
                this.sessionDataWriter.writeSessionApp(cos, this.crashlyticsCore.getPackageName(), this.crashlyticsCore.getVersionCode(), this.crashlyticsCore.getVersionName(), this.idManager.getAppInstallIdentifier(), DeliveryMechanism.determineFrom(this.crashlyticsCore.getInstallerPackageName()).getId());
                CommonUtils.flushOrLog(cos, "Failed to flush to session app file.");
                CommonUtils.closeOrLog(fos2, "Failed to close session app file.");
            } catch (Exception e2) {
                e = e2;
                fos = fos2;
                try {
                    ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session app file.");
                    CommonUtils.closeOrLog(fos, "Failed to close session app file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos2;
                CommonUtils.flushOrLog(cos, "Failed to flush to session app file.");
                CommonUtils.closeOrLog(fos, "Failed to close session app file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            ExceptionUtils.writeStackTraceIfNotNull(e, fos);
            throw e;
        }
    }

    private void writeSessionOS(String sessionId) throws Exception {
        Exception e;
        Throwable th;
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            FileOutputStream fos2 = new ClsFileOutputStream(this.filesDir, sessionId + "SessionOS");
            try {
                cos = CodedOutputStream.newInstance(fos2);
                this.sessionDataWriter.writeSessionOS(cos, CommonUtils.isRooted(this.crashlyticsCore.getContext()));
                CommonUtils.flushOrLog(cos, "Failed to flush to session OS file.");
                CommonUtils.closeOrLog(fos2, "Failed to close session OS file.");
            } catch (Exception e2) {
                e = e2;
                fos = fos2;
                try {
                    ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to session OS file.");
                    CommonUtils.closeOrLog(fos, "Failed to close session OS file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos2;
                CommonUtils.flushOrLog(cos, "Failed to flush to session OS file.");
                CommonUtils.closeOrLog(fos, "Failed to close session OS file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            ExceptionUtils.writeStackTraceIfNotNull(e, fos);
            throw e;
        }
    }

    private void writeSessionDevice(String sessionId) throws Exception {
        Exception e;
        Throwable th;
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            FileOutputStream clsFileOutputStream = new ClsFileOutputStream(this.filesDir, sessionId + "SessionDevice");
            try {
                cos = CodedOutputStream.newInstance(clsFileOutputStream);
                Context context = this.crashlyticsCore.getContext();
                StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
                this.sessionDataWriter.writeSessionDevice(cos, this.idManager.getDeviceUUID(), CommonUtils.getCpuArchitectureInt(), Build.MODEL, Runtime.getRuntime().availableProcessors(), CommonUtils.getTotalRamInBytes(), ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()), CommonUtils.isEmulator(context), this.idManager.getDeviceIdentifiers(), CommonUtils.getDeviceState(context), Build.MANUFACTURER, Build.PRODUCT);
                CommonUtils.flushOrLog(cos, "Failed to flush session device info.");
                CommonUtils.closeOrLog(clsFileOutputStream, "Failed to close session device file.");
            } catch (Exception e2) {
                e = e2;
                fos = clsFileOutputStream;
                try {
                    ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush session device info.");
                    CommonUtils.closeOrLog(fos, "Failed to close session device file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = clsFileOutputStream;
                CommonUtils.flushOrLog(cos, "Failed to flush session device info.");
                CommonUtils.closeOrLog(fos, "Failed to close session device file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            ExceptionUtils.writeStackTraceIfNotNull(e, fos);
            throw e;
        }
    }

    private void writeSessionUser(String sessionId) throws Exception {
        Exception e;
        Throwable th;
        FileOutputStream fos = null;
        CodedOutputStream cos = null;
        try {
            FileOutputStream fos2 = new ClsFileOutputStream(this.filesDir, sessionId + "SessionUser");
            try {
                cos = CodedOutputStream.newInstance(fos2);
                UserMetaData userMetaData = getUserMetaData(sessionId);
                if (userMetaData.isEmpty()) {
                    CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                    CommonUtils.closeOrLog(fos2, "Failed to close session user file.");
                    return;
                }
                this.sessionDataWriter.writeSessionUser(cos, userMetaData.id, userMetaData.name, userMetaData.email);
                CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                CommonUtils.closeOrLog(fos2, "Failed to close session user file.");
            } catch (Exception e2) {
                e = e2;
                fos = fos2;
                try {
                    ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                    CommonUtils.closeOrLog(fos, "Failed to close session user file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos2;
                CommonUtils.flushOrLog(cos, "Failed to flush session user file.");
                CommonUtils.closeOrLog(fos, "Failed to close session user file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            ExceptionUtils.writeStackTraceIfNotNull(e, fos);
            throw e;
        }
    }

    private UserMetaData getUserMetaData(String sessionId) {
        return isHandlingException() ? new UserMetaData(this.crashlyticsCore.getUserIdentifier(), this.crashlyticsCore.getUserName(), this.crashlyticsCore.getUserEmail()) : new MetaDataStore(this.filesDir).readUserData(sessionId);
    }

    private void writeSessionEvent(CodedOutputStream cos, Date time, Thread thread, Throwable ex, String eventType, boolean includeAllThreads) throws Exception {
        Thread[] threads;
        Map<String, String> attributes;
        Context context = this.crashlyticsCore.getContext();
        long eventTime = time.getTime() / 1000;
        float batteryLevel = CommonUtils.getBatteryLevel(context);
        int batteryVelocity = CommonUtils.getBatteryVelocity(context, this.powerConnected);
        boolean proximityEnabled = CommonUtils.getProximitySensorEnabled(context);
        int orientation = context.getResources().getConfiguration().orientation;
        long usedRamBytes = CommonUtils.getTotalRamInBytes() - CommonUtils.calculateFreeRamInBytes(context);
        long diskUsedBytes = CommonUtils.calculateUsedDiskSpaceInBytes(Environment.getDataDirectory().getPath());
        RunningAppProcessInfo runningAppProcessInfo = CommonUtils.getAppProcessInfo(context.getPackageName(), context);
        List<StackTraceElement[]> stacks = new LinkedList();
        StackTraceElement[] exceptionStack = ex.getStackTrace();
        if (includeAllThreads) {
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            threads = new Thread[allStackTraces.size()];
            int i = 0;
            for (Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
                threads[i] = (Thread) entry.getKey();
                stacks.add(entry.getValue());
                i++;
            }
        } else {
            threads = new Thread[0];
        }
        if (CommonUtils.getBooleanResourceValue(context, "com.crashlytics.CollectCustomKeys", true)) {
            attributes = this.crashlyticsCore.getAttributes();
            if (attributes != null && attributes.size() > 1) {
                attributes = new TreeMap(attributes);
            }
        } else {
            attributes = new TreeMap();
        }
        CodedOutputStream codedOutputStream = cos;
        Thread thread2 = thread;
        Throwable th = ex;
        String str = eventType;
        this.sessionDataWriter.writeSessionEvent(codedOutputStream, eventTime, thread2, th, str, threads, batteryLevel, batteryVelocity, proximityEnabled, orientation, usedRamBytes, diskUsedBytes, runningAppProcessInfo, stacks, exceptionStack, this.logFileManager, attributes);
    }

    private void doWriteNonFatal(Date time, Thread thread, Throwable ex) {
        Exception e;
        Throwable th;
        String currentSessionId = getCurrentSessionId();
        if (currentSessionId != null) {
            CrashlyticsCore.recordLoggedExceptionEvent(currentSessionId);
            ClsFileOutputStream fos = null;
            CodedOutputStream cos = null;
            try {
                Fabric.getLogger().m1453d("Fabric", "Crashlytics is logging non-fatal exception \"" + ex + "\" from thread " + thread.getName());
                ClsFileOutputStream fos2 = new ClsFileOutputStream(this.filesDir, currentSessionId + "SessionEvent" + CommonUtils.padWithZerosToMaxIntWidth(this.eventCounter.getAndIncrement()));
                try {
                    cos = CodedOutputStream.newInstance(fos2);
                    writeSessionEvent(cos, time, thread, ex, "error", false);
                    CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                    CommonUtils.closeOrLog(fos2, "Failed to close non-fatal file output stream.");
                    fos = fos2;
                } catch (Exception e2) {
                    e = e2;
                    fos = fos2;
                    try {
                        Fabric.getLogger().m1456e("Fabric", "An error occurred in the non-fatal exception logger", e);
                        ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                        CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                        CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                        trimSessionEventFiles(currentSessionId, 64);
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                        CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fos = fos2;
                    CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                    CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Fabric.getLogger().m1456e("Fabric", "An error occurred in the non-fatal exception logger", e);
                ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                CommonUtils.flushOrLog(cos, "Failed to flush to non-fatal file.");
                CommonUtils.closeOrLog(fos, "Failed to close non-fatal file output stream.");
                trimSessionEventFiles(currentSessionId, 64);
                return;
            }
            try {
                trimSessionEventFiles(currentSessionId, 64);
                return;
            } catch (Exception e4) {
                Fabric.getLogger().m1456e("Fabric", "An error occurred when trimming non-fatal files.", e4);
                return;
            }
        }
        Fabric.getLogger().m1456e("Fabric", "Tried to write a non-fatal exception while no session was open.", null);
    }

    private void writeSessionPartsToSessionFile(File sessionBeginFile, String sessionId, int maxLoggedExceptionsCount) {
        Exception e;
        Throwable th;
        Fabric.getLogger().m1453d("Fabric", "Collecting session parts for ID " + sessionId);
        File[] fatalFiles = listFilesMatching(new FileNameContainsFilter(sessionId + "SessionCrash"));
        boolean hasFatal = fatalFiles != null && fatalFiles.length > 0;
        Fabric.getLogger().m1453d("Fabric", String.format(Locale.US, "Session %s has fatal exception: %s", new Object[]{sessionId, Boolean.valueOf(hasFatal)}));
        File[] nonFatalFiles = listFilesMatching(new FileNameContainsFilter(sessionId + "SessionEvent"));
        boolean hasNonFatal = nonFatalFiles != null && nonFatalFiles.length > 0;
        Fabric.getLogger().m1453d("Fabric", String.format(Locale.US, "Session %s has non-fatal exceptions: %s", new Object[]{sessionId, Boolean.valueOf(hasNonFatal)}));
        if (hasFatal || hasNonFatal) {
            ClsFileOutputStream fos = null;
            try {
                ClsFileOutputStream fos2 = new ClsFileOutputStream(this.filesDir, sessionId);
                try {
                    CodedOutputStream cos = CodedOutputStream.newInstance(fos2);
                    Fabric.getLogger().m1453d("Fabric", "Collecting SessionStart data for session ID " + sessionId);
                    writeToCosFromFile(cos, sessionBeginFile);
                    cos.writeUInt64(4, new Date().getTime() / 1000);
                    cos.writeBool(5, hasFatal);
                    writeInitialPartsTo(cos, sessionId);
                    if (hasNonFatal) {
                        if (nonFatalFiles.length > maxLoggedExceptionsCount) {
                            Fabric.getLogger().m1453d("Fabric", String.format(Locale.US, "Trimming down to %d logged exceptions.", new Object[]{Integer.valueOf(maxLoggedExceptionsCount)}));
                            trimSessionEventFiles(sessionId, maxLoggedExceptionsCount);
                            nonFatalFiles = listFilesMatching(new FileNameContainsFilter(sessionId + "SessionEvent"));
                        }
                        writeNonFatalEventsTo(cos, nonFatalFiles, sessionId);
                    }
                    if (hasFatal) {
                        writeToCosFromFile(cos, fatalFiles[0]);
                    }
                    cos.writeUInt32(11, 1);
                    cos.writeEnum(12, 3);
                    CommonUtils.flushOrLog(cos, "Error flushing session file stream");
                    if (null != null) {
                        closeWithoutRenamingOrLog(fos2);
                        fos = fos2;
                    } else {
                        CommonUtils.closeOrLog(fos2, "Failed to close CLS file");
                        fos = fos2;
                    }
                } catch (Exception e2) {
                    e = e2;
                    fos = fos2;
                    try {
                        Fabric.getLogger().m1456e("Fabric", "Failed to write session file for session ID: " + sessionId, e);
                        ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                        CommonUtils.flushOrLog(null, "Error flushing session file stream");
                        if (true) {
                            CommonUtils.closeOrLog(fos, "Failed to close CLS file");
                        } else {
                            closeWithoutRenamingOrLog(fos);
                        }
                        Fabric.getLogger().m1453d("Fabric", "Removing session part files for ID " + sessionId);
                        deleteSessionPartFilesFor(sessionId);
                    } catch (Throwable th2) {
                        th = th2;
                        CommonUtils.flushOrLog(null, "Error flushing session file stream");
                        if (null == null) {
                            closeWithoutRenamingOrLog(fos);
                        } else {
                            CommonUtils.closeOrLog(fos, "Failed to close CLS file");
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fos = fos2;
                    CommonUtils.flushOrLog(null, "Error flushing session file stream");
                    if (null == null) {
                        CommonUtils.closeOrLog(fos, "Failed to close CLS file");
                    } else {
                        closeWithoutRenamingOrLog(fos);
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                Fabric.getLogger().m1456e("Fabric", "Failed to write session file for session ID: " + sessionId, e);
                ExceptionUtils.writeStackTraceIfNotNull(e, fos);
                CommonUtils.flushOrLog(null, "Error flushing session file stream");
                if (true) {
                    CommonUtils.closeOrLog(fos, "Failed to close CLS file");
                } else {
                    closeWithoutRenamingOrLog(fos);
                }
                Fabric.getLogger().m1453d("Fabric", "Removing session part files for ID " + sessionId);
                deleteSessionPartFilesFor(sessionId);
            }
        }
        Fabric.getLogger().m1453d("Fabric", "No events present for session ID " + sessionId);
        Fabric.getLogger().m1453d("Fabric", "Removing session part files for ID " + sessionId);
        deleteSessionPartFilesFor(sessionId);
    }

    private void writeNonFatalEventsTo(CodedOutputStream cos, File[] nonFatalFiles, String sessionId) {
        Arrays.sort(nonFatalFiles, CommonUtils.FILE_MODIFIED_COMPARATOR);
        for (File nonFatalFile : nonFatalFiles) {
            try {
                Fabric.getLogger().m1453d("Fabric", String.format(Locale.US, "Found Non Fatal for session ID %s in %s ", new Object[]{sessionId, nonFatalFile.getName()}));
                writeToCosFromFile(cos, nonFatalFile);
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Fabric", "Error writting non-fatal to session.", e);
            }
        }
    }

    private void writeInitialPartsTo(CodedOutputStream cos, String sessionId) throws IOException {
        for (String tag : new String[]{"SessionUser", "SessionApp", "SessionOS", "SessionDevice"}) {
            File[] sessionPartFiles = listFilesMatching(new FileNameContainsFilter(sessionId + tag));
            if (sessionPartFiles.length == 0) {
                Fabric.getLogger().m1456e("Fabric", "Can't find " + tag + " data for session ID " + sessionId, null);
            } else {
                Fabric.getLogger().m1453d("Fabric", "Collecting " + tag + " data for session ID " + sessionId);
                writeToCosFromFile(cos, sessionPartFiles[0]);
            }
        }
    }

    private void writeToCosFromFile(CodedOutputStream cos, File file) throws IOException {
        Throwable th;
        if (file.exists()) {
            byte[] bytes = new byte[((int) file.length())];
            FileInputStream fis = null;
            try {
                FileInputStream fis2 = new FileInputStream(file);
                int offset = 0;
                while (offset < bytes.length) {
                    try {
                        int numRead = fis2.read(bytes, offset, bytes.length - offset);
                        if (numRead < 0) {
                            break;
                        }
                        offset += numRead;
                    } catch (Throwable th2) {
                        th = th2;
                        fis = fis2;
                    }
                }
                CommonUtils.closeOrLog(fis2, "Failed to close file input stream.");
                cos.writeRawBytes(bytes);
                return;
            } catch (Throwable th3) {
                th = th3;
                CommonUtils.closeOrLog(fis, "Failed to close file input stream.");
                throw th;
            }
        }
        Fabric.getLogger().m1456e("Fabric", "Tried to include a file that doesn't exist: " + file.getName(), null);
    }

    private void sendSessionReports() {
        for (File toSend : listCompleteSessionFiles()) {
            this.executorServiceWrapper.executeAsync(new AnonymousClass16(toSend));
        }
    }
}
