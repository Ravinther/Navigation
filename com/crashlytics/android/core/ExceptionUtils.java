package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

final class ExceptionUtils {
    public static void writeStackTraceIfNotNull(Throwable ex, OutputStream os) {
        if (os != null) {
            writeStackTrace(ex, os);
        }
    }

    private static void writeStackTrace(Throwable ex, OutputStream os) {
        Exception e;
        Throwable th;
        PrintWriter writer = null;
        try {
            Writer writer2 = new PrintWriter(os);
            Writer writer3;
            try {
                writeStackTrace(ex, writer2);
                CommonUtils.closeOrLog(writer2, "Failed to close stack trace writer.");
                writer3 = writer2;
            } catch (Exception e2) {
                e = e2;
                writer3 = writer2;
                try {
                    Fabric.getLogger().m1456e("Fabric", "Failed to create PrintWriter", e);
                    CommonUtils.closeOrLog(writer, "Failed to close stack trace writer.");
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.closeOrLog(writer, "Failed to close stack trace writer.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                writer3 = writer2;
                CommonUtils.closeOrLog(writer, "Failed to close stack trace writer.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().m1456e("Fabric", "Failed to create PrintWriter", e);
            CommonUtils.closeOrLog(writer, "Failed to close stack trace writer.");
        }
    }

    private static void writeStackTrace(Throwable ex, Writer writer) {
        boolean first = true;
        while (ex != null) {
            try {
                String message = getMessage(ex);
                if (message == null) {
                    message = "";
                }
                writer.write((first ? "" : "Caused by: ") + ex.getClass().getName() + ": " + message + "\n");
                first = false;
                for (StackTraceElement element : ex.getStackTrace()) {
                    writer.write("\tat " + element.toString() + "\n");
                }
                ex = ex.getCause();
            } catch (Exception e) {
                Fabric.getLogger().m1456e("Fabric", "Could not write stack trace", e);
                return;
            }
        }
    }

    private static String getMessage(Throwable t) {
        String message = t.getLocalizedMessage();
        if (message == null) {
            return null;
        }
        return message.replaceAll("(\r\n|\n|\f)", " ");
    }
}
