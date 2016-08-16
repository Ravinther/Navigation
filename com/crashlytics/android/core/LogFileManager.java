package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;

class LogFileManager {
    private static final NoopLogStore NOOP_LOG_STORE;
    private final Context context;
    private FileLogStore currentLog;
    private final File logFileDir;

    private static final class NoopLogStore implements FileLogStore {
        private NoopLogStore() {
        }

        public void writeToLog(long timestamp, String msg) {
        }

        public ByteString getLogAsByteString() {
            return null;
        }

        public void closeLogFile() {
        }

        public void deleteLogFile() {
        }
    }

    static {
        NOOP_LOG_STORE = new NoopLogStore();
    }

    public LogFileManager(Context context, File filesDir) {
        this.context = context;
        this.logFileDir = new File(filesDir, "log-files");
        this.currentLog = NOOP_LOG_STORE;
    }

    public void onSessionChange(String sessionId) {
        clearLog();
        if (isLoggingEnabled()) {
            setLogFile(getWorkingFileForSession(sessionId), 65536);
            return;
        }
        Fabric.getLogger().m1453d("Fabric", "Preferences requested no custom logs. Aborting log file creation.");
        this.currentLog = NOOP_LOG_STORE;
    }

    public void writeToLog(long timestamp, String msg) {
        this.currentLog.writeToLog(timestamp, msg);
    }

    public ByteString getByteStringForLog() {
        return this.currentLog.getLogAsByteString();
    }

    public void clearLog() {
        this.currentLog.deleteLogFile();
    }

    void setLogFile(File workingFile, int maxLogSize) {
        this.currentLog.closeLogFile();
        this.currentLog = new QueueFileLogStore(workingFile, maxLogSize);
    }

    private File getWorkingFileForSession(String sessionId) {
        ensureTargetDirectoryExists();
        return new File(this.logFileDir, "crashlytics-userlog-" + sessionId + ".temp");
    }

    private void ensureTargetDirectoryExists() {
        if (!this.logFileDir.exists()) {
            this.logFileDir.mkdirs();
        }
    }

    private boolean isLoggingEnabled() {
        return CommonUtils.getBooleanResourceValue(this.context, "com.crashlytics.CollectCustomLogs", true);
    }
}
