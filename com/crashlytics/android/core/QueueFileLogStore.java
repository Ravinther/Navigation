package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import io.fabric.sdk.android.services.common.QueueFile.ElementReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

class QueueFileLogStore implements FileLogStore {
    private QueueFile logFile;
    private final int maxLogSize;
    private final File workingFile;

    /* renamed from: com.crashlytics.android.core.QueueFileLogStore.1 */
    class C03011 implements ElementReader {
        final /* synthetic */ byte[] val$logBytes;
        final /* synthetic */ int[] val$offsetHolder;

        C03011(byte[] bArr, int[] iArr) {
            this.val$logBytes = bArr;
            this.val$offsetHolder = iArr;
        }

        public void read(InputStream in, int length) throws IOException {
            try {
                in.read(this.val$logBytes, this.val$offsetHolder[0], length);
                int[] iArr = this.val$offsetHolder;
                iArr[0] = iArr[0] + length;
            } finally {
                in.close();
            }
        }
    }

    public QueueFileLogStore(File workingFile, int maxLogSize) {
        this.workingFile = workingFile;
        this.maxLogSize = maxLogSize;
    }

    public void writeToLog(long timestamp, String msg) {
        openLogFile();
        doWriteToLog(timestamp, msg);
    }

    public ByteString getLogAsByteString() {
        if (!this.workingFile.exists()) {
            return null;
        }
        openLogFile();
        if (this.logFile == null) {
            return null;
        }
        int[] offsetHolder = new int[]{0};
        byte[] logBytes = new byte[this.logFile.usedBytes()];
        try {
            this.logFile.forEach(new C03011(logBytes, offsetHolder));
        } catch (IOException e) {
            Fabric.getLogger().m1456e("Fabric", "A problem occurred while reading the Crashlytics log file.", e);
        }
        return ByteString.copyFrom(logBytes, 0, offsetHolder[0]);
    }

    public void closeLogFile() {
        CommonUtils.closeOrLog(this.logFile, "There was a problem closing the Crashlytics log file.");
        this.logFile = null;
    }

    public void deleteLogFile() {
        closeLogFile();
        this.workingFile.delete();
    }

    private void openLogFile() {
        if (this.logFile == null) {
            try {
                this.logFile = new QueueFile(this.workingFile);
            } catch (IOException e) {
                Fabric.getLogger().m1456e("Fabric", "Could not open log file: " + this.workingFile, e);
            }
        }
    }

    private void doWriteToLog(long timestamp, String msg) {
        if (this.logFile != null) {
            if (msg == null) {
                msg = "null";
            }
            try {
                int quarterMaxLogSize = this.maxLogSize / 4;
                if (msg.length() > quarterMaxLogSize) {
                    msg = "..." + msg.substring(msg.length() - quarterMaxLogSize);
                }
                msg = msg.replaceAll("\r", " ").replaceAll("\n", " ");
                this.logFile.add(String.format(Locale.US, "%d %s%n", new Object[]{Long.valueOf(timestamp), msg}).getBytes("UTF-8"));
                while (!this.logFile.isEmpty() && this.logFile.usedBytes() > this.maxLogSize) {
                    this.logFile.remove();
                }
            } catch (IOException e) {
                Fabric.getLogger().m1456e("Fabric", "There was a problem writing to the Crashlytics log.", e);
            }
        }
    }
}
