package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.BackgroundPriorityRunnable;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ReportUploader {
    static final Map<String, String> HEADER_INVALID_CLS_FILE;
    private static final short[] RETRY_INTERVALS;
    private static final FilenameFilter crashFileFilter;
    private final CreateReportSpiCall createReportCall;
    private final Object fileAccessLock;
    private Thread uploadThread;

    /* renamed from: com.crashlytics.android.core.ReportUploader.1 */
    static class C03021 implements FilenameFilter {
        C03021() {
        }

        public boolean accept(File dir, String filename) {
            return filename.endsWith(".cls") && !filename.contains("Session");
        }
    }

    private class Worker extends BackgroundPriorityRunnable {
        private final float delay;

        Worker(float delay) {
            this.delay = delay;
        }

        public void onRun() {
            try {
                attemptUploadWithRetry();
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Fabric", "An unexpected error occurred while attempting to upload crash reports.", e);
            }
            ReportUploader.this.uploadThread = null;
        }

        private void attemptUploadWithRetry() {
            Fabric.getLogger().m1453d("Fabric", "Starting report processing in " + this.delay + " second(s)...");
            if (this.delay > 0.0f) {
                try {
                    Thread.sleep((long) (this.delay * 1000.0f));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            CrashlyticsCore crashlyticsCore = CrashlyticsCore.getInstance();
            CrashlyticsUncaughtExceptionHandler handler = crashlyticsCore.getHandler();
            List<Report> reports = ReportUploader.this.findReports();
            if (!handler.isHandlingException()) {
                if (reports.isEmpty() || crashlyticsCore.canSendWithUserApproval()) {
                    int retryCount = 0;
                    while (!reports.isEmpty() && !CrashlyticsCore.getInstance().getHandler().isHandlingException()) {
                        Fabric.getLogger().m1453d("Fabric", "Attempting to send " + reports.size() + " report(s)");
                        for (Report report : reports) {
                            ReportUploader.this.forceUpload(report);
                        }
                        reports = ReportUploader.this.findReports();
                        if (!reports.isEmpty()) {
                            int retryCount2 = retryCount + 1;
                            long interval = (long) ReportUploader.RETRY_INTERVALS[Math.min(retryCount, ReportUploader.RETRY_INTERVALS.length - 1)];
                            Fabric.getLogger().m1453d("Fabric", "Report submisson: scheduling delayed retry in " + interval + " seconds");
                            try {
                                Thread.sleep(1000 * interval);
                                retryCount = retryCount2;
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }
                    return;
                }
                Fabric.getLogger().m1453d("Fabric", "User declined to send. Removing " + reports.size() + " Report(s).");
                for (Report report2 : reports) {
                    report2.remove();
                }
            }
        }
    }

    static {
        crashFileFilter = new C03021();
        HEADER_INVALID_CLS_FILE = Collections.singletonMap("X-CRASHLYTICS-INVALID-SESSION", "1");
        RETRY_INTERVALS = new short[]{(short) 10, (short) 20, (short) 30, (short) 60, (short) 120, (short) 300};
    }

    public ReportUploader(CreateReportSpiCall createReportCall) {
        this.fileAccessLock = new Object();
        if (createReportCall == null) {
            throw new IllegalArgumentException("createReportCall must not be null.");
        }
        this.createReportCall = createReportCall;
    }

    public synchronized void uploadReports(float delay) {
        if (this.uploadThread == null) {
            this.uploadThread = new Thread(new Worker(delay), "Crashlytics Report Uploader");
            this.uploadThread.start();
        }
    }

    boolean forceUpload(Report report) {
        boolean removed = false;
        synchronized (this.fileAccessLock) {
            try {
                boolean sent = this.createReportCall.invoke(new CreateReportRequest(new ApiKey().getValue(CrashlyticsCore.getInstance().getContext()), report));
                Fabric.getLogger().m1457i("Fabric", "Crashlytics report upload " + (sent ? "complete: " : "FAILED: ") + report.getFileName());
                if (sent) {
                    report.remove();
                    removed = true;
                }
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Fabric", "Error occurred sending report " + report, e);
            }
        }
        return removed;
    }

    List<Report> findReports() {
        Fabric.getLogger().m1453d("Fabric", "Checking for crash reports...");
        synchronized (this.fileAccessLock) {
            File[] clsFiles = CrashlyticsCore.getInstance().getSdkDirectory().listFiles(crashFileFilter);
        }
        List<Report> reports = new LinkedList();
        for (File file : clsFiles) {
            Fabric.getLogger().m1453d("Fabric", "Found crash report " + file.getPath());
            reports.add(new SessionReport(file));
        }
        if (reports.isEmpty()) {
            Fabric.getLogger().m1453d("Fabric", "No reports found.");
        }
        return reports;
    }
}
