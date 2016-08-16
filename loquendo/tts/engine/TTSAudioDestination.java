package loquendo.tts.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TTSAudioDestination implements TTSListener {
    private static boolean logEnabled;
    private static String logFile;
    private static TTSFormatter logFormatter;
    private static FileHandler logHandler;
    private static Level logLevel;
    private static Logger logger;
    private long audioChannel;
    private boolean bOutputRawFile;
    private final int bufferDim;
    private EmptyBuffer dequeuer;
    private FileOutputStream fos;
    private TTSReader hReader;
    private boolean idle;
    private String name;
    private PipedOutputStream out;
    private volatile boolean speaking;

    class EmptyBuffer extends Thread {
        private boolean stillAlive;

        public EmptyBuffer(String str, Object obj) {
            super(str);
            this.stillAlive = true;
        }

        public void killThis() {
            this.stillAlive = false;
            TTSAudioDestination.this.log(Level.FINEST, "stillAlive=false");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r7 = this;
            r6 = 0;
            r0 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
            r1 = new byte[r0];
        L_0x0005:
            monitor-enter(r7);
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x0038 }
            r2 = 1;
            r0.idle = r2;	 Catch:{ Exception -> 0x0038 }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x0038 }
            r2 = java.util.logging.Level.INFO;	 Catch:{ Exception -> 0x0038 }
            r3 = "idle";
            r0.log(r2, r3);	 Catch:{ Exception -> 0x0038 }
            r7.wait();	 Catch:{ Exception -> 0x0038 }
        L_0x0019:
            monitor-exit(r7);	 Catch:{ all -> 0x003d }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;
            r2 = java.util.logging.Level.INFO;
            r3 = "!idle";
            r0.log(r2, r3);
            r0 = loquendo.tts.engine.TTSAudioDestination.this;
            r0.idle = r6;
            r0 = r7.stillAlive;
            if (r0 != 0) goto L_0x0040;
        L_0x002d:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;
            r1 = java.util.logging.Level.FINE;
            r2 = "Exit thread";
            r0.log(r1, r2);
            return;
        L_0x0038:
            r0 = move-exception;
            r0.printStackTrace();	 Catch:{ all -> 0x003d }
            goto L_0x0019;
        L_0x003d:
            r0 = move-exception;
            monitor-exit(r7);	 Catch:{ all -> 0x003d }
            throw r0;
        L_0x0040:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.speaking;	 Catch:{ Exception -> 0x00f5 }
            if (r0 == 0) goto L_0x0143;
        L_0x0048:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = java.util.logging.Level.FINEST;	 Catch:{ Exception -> 0x00f5 }
            r3 = "while(speaking): getBufferData called";
            r0.log(r2, r3);	 Catch:{ Exception -> 0x00f5 }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.getBufferData(r1);	 Catch:{ Exception -> 0x00f5 }
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r3 = java.util.logging.Level.FINE;	 Catch:{ Exception -> 0x00f5 }
            r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00f5 }
            r4.<init>();	 Catch:{ Exception -> 0x00f5 }
            r5 = "GetBufferData returned ";
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.append(r0);	 Catch:{ Exception -> 0x00f5 }
            r5 = " elements to out";
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.toString();	 Catch:{ Exception -> 0x00f5 }
            r2.log(r3, r4);	 Catch:{ Exception -> 0x00f5 }
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r3 = java.util.logging.Level.FINE;	 Catch:{ Exception -> 0x00f5 }
            r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00f5 }
            r4.<init>();	 Catch:{ Exception -> 0x00f5 }
            r5 = "out: ";
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r5 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r5 = r5.out;	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.toString();	 Catch:{ Exception -> 0x00f5 }
            r2.log(r3, r4);	 Catch:{ Exception -> 0x00f5 }
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r3 = java.util.logging.Level.FINE;	 Catch:{ Exception -> 0x00f5 }
            r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00f5 }
            r4.<init>();	 Catch:{ Exception -> 0x00f5 }
            r5 = "buffer: ";
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.append(r1);	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.toString();	 Catch:{ Exception -> 0x00f5 }
            r2.log(r3, r4);	 Catch:{ Exception -> 0x00f5 }
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r3 = java.util.logging.Level.FINE;	 Catch:{ Exception -> 0x00f5 }
            r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00f5 }
            r4.<init>();	 Catch:{ Exception -> 0x00f5 }
            r5 = "buffer.length: ";
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r5 = r1.length;	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.toString();	 Catch:{ Exception -> 0x00f5 }
            r2.log(r3, r4);	 Catch:{ Exception -> 0x00f5 }
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = r2.out;	 Catch:{ Exception -> 0x00f5 }
            r3 = 0;
            r2.write(r1, r3, r0);	 Catch:{ Exception -> 0x00f5 }
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = r2.bOutputRawFile;	 Catch:{ Exception -> 0x00f5 }
            if (r2 == 0) goto L_0x00ee;
        L_0x00e4:
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = r2.fos;	 Catch:{ Exception -> 0x00f5 }
            r3 = 0;
            r2.write(r1, r3, r0);	 Catch:{ Exception -> 0x00f5 }
        L_0x00ee:
            r2 = 10;
            java.lang.Thread.sleep(r2);	 Catch:{ Exception -> 0x00f5 }
            goto L_0x0040;
        L_0x00f5:
            r0 = move-exception;
            r2 = loquendo.tts.engine.TTSAudioDestination.this;
            r3 = java.util.logging.Level.SEVERE;
            r4 = new java.lang.StringBuilder;
            r4.<init>();
            r5 = "5: ";
            r4 = r4.append(r5);
            r5 = r0.getMessage();
            r4 = r4.append(r5);
            r4 = r4.toString();
            r2.log(r3, r4);
            r2 = new java.io.StringWriter;
            r2.<init>();
            r3 = new java.io.PrintWriter;
            r3.<init>(r2);
            r0.printStackTrace(r3);
            r0 = loquendo.tts.engine.TTSAudioDestination.this;
            r3 = java.util.logging.Level.SEVERE;
            r4 = new java.lang.StringBuilder;
            r4.<init>();
            r5 = "5: ";
            r4 = r4.append(r5);
            r2 = r2.toString();
            r2 = r4.append(r2);
            r2 = r2.toString();
            r0.log(r3, r2);
            goto L_0x0005;
        L_0x0143:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = java.util.logging.Level.FINEST;	 Catch:{ Exception -> 0x00f5 }
            r3 = "getBufferData between while";
            r0.log(r2, r3);	 Catch:{ Exception -> 0x00f5 }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.getBufferData(r1);	 Catch:{ Exception -> 0x00f5 }
        L_0x0153:
            if (r0 <= 0) goto L_0x01a9;
        L_0x0155:
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r3 = java.util.logging.Level.FINE;	 Catch:{ Exception -> 0x00f5 }
            r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00f5 }
            r4.<init>();	 Catch:{ Exception -> 0x00f5 }
            r5 = "while(elements > 0): ";
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.append(r0);	 Catch:{ Exception -> 0x00f5 }
            r5 = " elements will be written in out";
            r4 = r4.append(r5);	 Catch:{ Exception -> 0x00f5 }
            r4 = r4.toString();	 Catch:{ Exception -> 0x00f5 }
            r2.log(r3, r4);	 Catch:{ Exception -> 0x00f5 }
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = r2.out;	 Catch:{ Exception -> 0x00f5 }
            r3 = 0;
            r2.write(r1, r3, r0);	 Catch:{ Exception -> 0x00f5 }
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = r2.bOutputRawFile;	 Catch:{ Exception -> 0x00f5 }
            if (r2 == 0) goto L_0x0193;
        L_0x0189:
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = r2.fos;	 Catch:{ Exception -> 0x00f5 }
            r3 = 0;
            r2.write(r1, r3, r0);	 Catch:{ Exception -> 0x00f5 }
        L_0x0193:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = java.util.logging.Level.FINEST;	 Catch:{ Exception -> 0x00f5 }
            r3 = "while(elements > 0): getBufferData called";
            r0.log(r2, r3);	 Catch:{ Exception -> 0x00f5 }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.getBufferData(r1);	 Catch:{ Exception -> 0x00f5 }
            r2 = 10;
            java.lang.Thread.sleep(r2);	 Catch:{ Exception -> 0x00f5 }
            goto L_0x0153;
        L_0x01a9:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.out;	 Catch:{ Exception -> 0x00f5 }
            r0.flush();	 Catch:{ Exception -> 0x00f5 }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.out;	 Catch:{ Exception -> 0x00f5 }
            r0.close();	 Catch:{ Exception -> 0x00f5 }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = java.util.logging.Level.FINE;	 Catch:{ Exception -> 0x00f5 }
            r3 = "Flush and close executed";
            r0.log(r2, r3);	 Catch:{ Exception -> 0x00f5 }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = 0;
            r0.out = r2;	 Catch:{ Exception -> 0x00f5 }
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.bOutputRawFile;	 Catch:{ Exception -> 0x00f5 }
            if (r0 == 0) goto L_0x01ea;
        L_0x01d3:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.fos;	 Catch:{ Exception -> 0x00f5 }
            if (r0 == 0) goto L_0x01e4;
        L_0x01db:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ IOException -> 0x01f1 }
            r0 = r0.fos;	 Catch:{ IOException -> 0x01f1 }
            r0.close();	 Catch:{ IOException -> 0x01f1 }
        L_0x01e4:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r2 = 0;
            r0.fos = r2;	 Catch:{ Exception -> 0x00f5 }
        L_0x01ea:
            r0 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r0.cleanBuffer();	 Catch:{ Exception -> 0x00f5 }
            goto L_0x0005;
        L_0x01f1:
            r0 = move-exception;
            r2 = loquendo.tts.engine.TTSAudioDestination.this;	 Catch:{ Exception -> 0x00f5 }
            r3 = java.util.logging.Level.SEVERE;	 Catch:{ Exception -> 0x00f5 }
            r0 = r0.getMessage();	 Catch:{ Exception -> 0x00f5 }
            r2.log(r3, r0);	 Catch:{ Exception -> 0x00f5 }
            goto L_0x01e4;
            */
            throw new UnsupportedOperationException("Method not decompiled: loquendo.tts.engine.TTSAudioDestination.EmptyBuffer.run():void");
        }
    }

    public class TTSFormatter extends Formatter {
        private String calcDate(long j) {
            return new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss.SSS").format(new Date(j));
        }

        public String format(LogRecord logRecord) {
            return calcDate(logRecord.getMillis()) + " - " + logRecord.getLevel().getName() + "\t- " + logRecord.getSourceClassName() + "\t- " + logRecord.getMessage() + "\n";
        }
    }

    static {
        logger = Logger.getLogger(TTSAudioDestination.class.getName());
        logLevel = Level.SEVERE;
        logEnabled = false;
        logHandler = null;
        logFormatter = null;
        logFile = null;
        System.loadLibrary("ttsloquendoengine");
        TTSConfiguration.resolveTTSSymbols(System.getProperty("user.dir"));
    }

    public TTSAudioDestination(TTSReader tTSReader, String str) {
        this.audioChannel = 0;
        this.speaking = false;
        this.bufferDim = 16384;
        this.idle = true;
        this.dequeuer = null;
        this.hReader = null;
        this.name = null;
        this.fos = null;
        this.bOutputRawFile = false;
        this.out = null;
        this.hReader = tTSReader;
        this.name = str;
        this.dequeuer = new EmptyBuffer("AudioDestination " + str, this);
        iniLogging();
        log(Level.FINEST, this.dequeuer + ".start() ");
        this.dequeuer.start();
        this.audioChannel = _allocateBuffer(16384);
        log(Level.FINEST, "new EmptyBuffer " + str + " - audioChannel: " + this.audioChannel);
    }

    private native long _allocateBuffer(int i);

    private native int _cleanBuffer(long j);

    private native int _deallocateBuffer(long j);

    private native int _getBufferData(long j, byte[] bArr);

    private void iniLogging() {
        logFile = System.getProperty("loquendo.jni.logFileName");
        String property = System.getProperty("loquendo.jni.logLevel");
        if (property != null && property.length() > 0) {
            if (property.equalsIgnoreCase("OFF")) {
                logLevel = Level.OFF;
            } else if (property.equalsIgnoreCase("ALL")) {
                logLevel = Level.ALL;
            } else if (property.equalsIgnoreCase("SEVERE")) {
                logLevel = Level.SEVERE;
            } else if (property.equalsIgnoreCase("WARNING")) {
                logLevel = Level.WARNING;
            } else if (property.equalsIgnoreCase("INFO")) {
                logLevel = Level.INFO;
            } else if (property.equalsIgnoreCase("CONFIG")) {
                logLevel = Level.CONFIG;
            } else if (property.equalsIgnoreCase("FINE")) {
                logLevel = Level.FINE;
            } else if (property.equalsIgnoreCase("FINER")) {
                logLevel = Level.FINER;
            } else if (property.equalsIgnoreCase("FINEST")) {
                logLevel = Level.FINEST;
            }
        }
        if (logFile != null && logFile.length() > 0) {
            try {
                logHandler = new FileHandler(System.getProperty("user.dir") + "/" + logFile);
            } catch (SecurityException e) {
            } catch (IOException e2) {
            }
            logFormatter = new TTSFormatter();
            logHandler.setFormatter(logFormatter);
            logger.setUseParentHandlers(false);
            logger.addHandler(logHandler);
            logger.setLevel(logLevel);
            logEnabled = true;
        }
    }

    private void log(Level level, String str) {
        if (logEnabled) {
            logger.log(level, str);
        }
    }

    public int cleanBuffer() {
        log(Level.FINE, "CH " + this.audioChannel);
        return _cleanBuffer(this.audioChannel);
    }

    public void deallocateBuffer() {
        if (this.audioChannel != 0) {
            log(Level.FINEST, "[" + this.name + "] " + "_deallocateBuffer(CH: " + this.audioChannel + ")");
            _deallocateBuffer(this.audioChannel);
            this.audioChannel = 0;
        }
    }

    public void eventOccurred(TTSEvent tTSEvent) {
        switch (tTSEvent.getReason().intValue()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                log(Level.INFO, "AUDIOSTART");
                synchronized (this.dequeuer) {
                    if (!this.speaking) {
                        this.speaking = true;
                        if (this.out != null) {
                            this.dequeuer.notify();
                        }
                    }
                    break;
                }
            case TTSConst.TTSMULTILINE /*1*/:
                log(Level.INFO, "ENDOFSPEECH");
                synchronized (this.dequeuer) {
                    this.speaking = false;
                    try {
                        this.dequeuer.wait(500);
                    } catch (Exception e) {
                        log(Level.SEVERE, "4: " + e.getMessage());
                        break;
                    }
                    break;
                }
            default:
        }
    }

    public int getBufferData(byte[] bArr) {
        try {
            log(Level.FINEST, "[" + this.name + "] " + "CH " + this.audioChannel + ": with buffer " + bArr);
            int _getBufferData = _getBufferData(this.audioChannel, bArr);
            log(Level.FINE, "[" + this.name + "] " + "CH " + this.audioChannel + ": " + _getBufferData + " bytes");
            return _getBufferData;
        } catch (Exception e) {
            return 0;
        }
    }

    public String getName() {
        return this.name;
    }

    public boolean isSpeaking() {
        return this.speaking;
    }

    public void killThread() {
        while (!this.idle) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log(Level.SEVERE, "3: " + e.getMessage());
            }
        }
        log(Level.FINEST, "dequeuer.killThis() called from killThread");
        this.dequeuer.killThis();
        synchronized (this.dequeuer) {
            this.dequeuer.notify();
        }
        this.dequeuer = null;
    }

    public void setAudio(int i, String str, int i2) throws TTSException {
        this.hReader.setAudio("ttsloquendoengine", this.audioChannel + "", i, str, i2);
    }

    public void setPipe(PipedInputStream pipedInputStream) {
        while (this.out != null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log(Level.SEVERE, "1: " + e.getMessage());
            }
        }
        try {
            this.out = new PipedOutputStream(pipedInputStream);
            if (this.bOutputRawFile) {
                try {
                    this.fos = new FileOutputStream(new File(System.getProperty("user.dir") + "/jni.raw"));
                } catch (FileNotFoundException e2) {
                    log(Level.SEVERE, e2.getMessage());
                }
            }
            log(Level.FINEST, "Allocation " + this.out);
        } catch (Exception e3) {
            log(Level.SEVERE, "2: " + e3.getMessage());
        }
        if (this.speaking) {
            synchronized (this.dequeuer) {
                this.dequeuer.notify();
            }
        }
    }

    public void stop() {
        this.speaking = false;
    }
}
