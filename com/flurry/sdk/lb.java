package com.flurry.sdk;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class lb {
    private static String f1107a;

    static {
        f1107a = lb.class.getSimpleName();
    }

    public static File m1253a(boolean z) {
        File file = null;
        Context c = jc.m946a().m957c();
        if (z && "mounted".equals(Environment.getExternalStorageState()) && (VERSION.SDK_INT >= 19 || c.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            file = c.getExternalFilesDir(null);
        }
        if (file == null) {
            return c.getFilesDir();
        }
        return file;
    }

    public static File m1256b(boolean z) {
        Context c = jc.m946a().m957c();
        File file = null;
        if (z && "mounted".equals(Environment.getExternalStorageState()) && (VERSION.SDK_INT >= 19 || c.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            file = c.getExternalCacheDir();
        }
        if (file == null) {
            return c.getCacheDir();
        }
        return file;
    }

    public static boolean m1255a(File file) {
        if (file == null || file.getAbsoluteFile() == null) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return true;
        }
        if (parentFile.mkdirs() || parentFile.isDirectory()) {
            return true;
        }
        jq.m1016a(6, f1107a, "Unable to create persistent dir: " + parentFile);
        return false;
    }

    public static boolean m1257b(File file) {
        if (file != null && file.isDirectory()) {
            for (String file2 : file.list()) {
                if (!m1257b(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    @Deprecated
    public static String m1258c(File file) {
        Throwable th;
        Throwable th2;
        if (file == null || !file.exists()) {
            jq.m1016a(4, f1107a, "Persistent file doesn't exist.");
            return null;
        }
        StringBuilder stringBuilder;
        jq.m1016a(4, f1107a, "Loading persistent data: " + file.getAbsolutePath());
        Closeable fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                stringBuilder = new StringBuilder();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    stringBuilder.append(new String(bArr, 0, read));
                }
                lc.m1265a(fileInputStream);
            } catch (Throwable th3) {
                th = th3;
                try {
                    jq.m1017a(6, f1107a, "Error when loading persistent file", th);
                    lc.m1265a(fileInputStream);
                    stringBuilder = null;
                    if (stringBuilder != null) {
                        return null;
                    }
                    return stringBuilder.toString();
                } catch (Throwable th4) {
                    th2 = th4;
                    lc.m1265a(fileInputStream);
                    throw th2;
                }
            }
        } catch (Throwable th5) {
            fileInputStream = null;
            th2 = th5;
            lc.m1265a(fileInputStream);
            throw th2;
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        }
        return null;
    }

    @Deprecated
    public static void m1254a(File file, String str) {
        Closeable fileOutputStream;
        Throwable th;
        if (file == null) {
            jq.m1016a(4, f1107a, "No persistent file specified.");
        } else if (str == null) {
            jq.m1016a(4, f1107a, "No data specified; deleting persistent file: " + file.getAbsolutePath());
            file.delete();
        } else {
            jq.m1016a(4, f1107a, "Writing persistent data: " + file.getAbsolutePath());
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(str.getBytes());
                    lc.m1265a(fileOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        jq.m1017a(6, f1107a, "Error writing persistent file", th);
                        lc.m1265a(fileOutputStream);
                    } catch (Throwable th3) {
                        th = th3;
                        lc.m1265a(fileOutputStream);
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                lc.m1265a(fileOutputStream);
                throw th;
            }
        }
    }
}
