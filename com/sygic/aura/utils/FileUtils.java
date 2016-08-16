package com.sygic.aura.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class FileUtils {
    private static final String[] EXLUDE_DIRS;
    private static final Set<String> EXLUDE_DIRS_SET;
    private static final FileFilter sMmcblkFilter;
    private static final Pattern sPattern;

    /* renamed from: com.sygic.aura.utils.FileUtils.1 */
    static class C17711 implements FileFilter {
        C17711() {
        }

        public boolean accept(File pathname) {
            return pathname.isDirectory() && FileUtils.sPattern.matcher(pathname.getName()).matches();
        }
    }

    /* renamed from: com.sygic.aura.utils.FileUtils.2 */
    static class C17722 implements FileFilter {
        final /* synthetic */ String val$strDir;
        final /* synthetic */ String val$strSubDir;

        C17722(String str, String str2) {
            this.val$strDir = str;
            this.val$strSubDir = str2;
        }

        public boolean accept(File f) {
            StringBuilder str = new StringBuilder(f.getParent()).append("/").append(this.val$strDir).append("/").append(this.val$strSubDir);
            if (f.isDirectory() && f.getName().compareTo(this.val$strDir) == 0 && FileUtils.fileExists(str.toString())) {
                return true;
            }
            return false;
        }
    }

    static {
        EXLUDE_DIRS = new String[]{"/proc", "/dev", "/etc", "/sbin/", "/etc", "/sbin", "/sys", "/system", "/data"};
        EXLUDE_DIRS_SET = new HashSet(Arrays.asList(EXLUDE_DIRS));
        sPattern = Pattern.compile("mmcblk\\d+");
        sMmcblkFilter = new C17711();
    }

    public static boolean fileExists(String strFile) {
        File file = new File(strFile);
        return file != null && file.exists();
    }

    public static void copyFile(String strFrom, String strTo) throws IOException {
        try {
            copyFile(new FileInputStream(new File(strFrom)), strTo);
        } catch (IOException e) {
            throw new IOException(String.format("Failed to copy from file '%s' to '%s'", new Object[]{strFrom, strTo}), e);
        }
    }

    public static void copyFile(InputStream isFrom, String strTo) throws IOException {
        File outputFile = new File(strTo);
        if (outputFile.getParentFile().mkdirs() || outputFile.getParentFile().isDirectory()) {
            try {
                copyFile(isFrom, new FileOutputStream(outputFile));
                return;
            } catch (IOException e) {
                throw new IOException(String.format("Failed to copy file from stream to '%s'", new Object[]{strTo}), e);
            }
        }
        throw new IOException(String.format("Failed to create directories for '%s'", new Object[]{strTo}));
    }

    public static void copyFile(InputStream isFrom, OutputStream osTo) throws IOException {
        try {
            byte[] buff = new byte[1024];
            while (true) {
                int len = isFrom.read(buff);
                if (len == -1) {
                    break;
                }
                osTo.write(buff, 0, len);
            }
            if (isFrom != null) {
                try {
                    isFrom.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (osTo != null) {
                osTo.close();
            }
        } catch (Throwable th) {
            if (isFrom != null) {
                try {
                    isFrom.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (osTo != null) {
                osTo.close();
            }
        }
    }

    public static int getFileCount(File dir) {
        int i = 0;
        if (dir == null || !dir.exists()) {
            return 0;
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return 0;
        }
        int count = 0;
        int length = files.length;
        while (i < length) {
            File file = files[i];
            if (file.isFile()) {
                count++;
            } else {
                count += getFileCount(file);
            }
            i++;
        }
        return count;
    }

    public static void deleteFile(String strName) {
        if (fileExists(strName)) {
            File file = new File(strName);
            if (file != null && file.isFile()) {
                file.delete();
            }
        }
    }

    public static ArrayList<String> listAssetDir(AssetManager am, String strDir) {
        try {
            String[] arr = am.list(strDir);
            if (arr != null && arr.length > 0) {
                ArrayList<String> arrayList = new ArrayList();
                for (String string : arr) {
                    String string2 = strDir + "/" + string2;
                    arrayList.add(string2);
                    ArrayList<String> arrs = listAssetDir(am, string2);
                    if (arrs != null) {
                        arrayList.addAll(arrs);
                    }
                }
                return arrayList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isSDCardPresent() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static String getSDCardPath() {
        File f = Environment.getExternalStorageDirectory();
        if (f == null || !f.isDirectory()) {
            return "/sdcard";
        }
        return f.getAbsolutePath();
    }

    public static String getSygicRoot(String strDir, String strSubDir) {
        if (strDir == null || strSubDir == null) {
            return null;
        }
        String strSDPath = getSDCardPath();
        File sdDir = new File(strSDPath);
        FileFilter flSDFilter = new C17722(strDir, strSubDir);
        String strRoot = findDir(sdDir, flSDFilter, 1);
        if (strRoot != null) {
            return strRoot;
        }
        strRoot = findDir(new File("/"), flSDFilter, 3);
        if (strRoot == null) {
            return strSDPath;
        }
        return strRoot;
    }

    private static String findDir(File file, FileFilter filter, int iMaxLevel) {
        return findDir(file, filter, iMaxLevel, 0);
    }

    private static String findDir(File file, FileFilter filter, int iMaxLevel, int iCurLevel) {
        int i = 0;
        if (iMaxLevel > -1 && iCurLevel > iMaxLevel) {
            return null;
        }
        File[] dirs = file.listFiles(filter);
        if (dirs != null && dirs.length > 0) {
            return dirs[0].getParent();
        }
        iCurLevel++;
        File[] entries = file.listFiles();
        if (entries != null) {
            int length = entries.length;
            while (i < length) {
                File entry = entries[i];
                if (!(entry == null || !entry.isDirectory() || EXLUDE_DIRS_SET.contains(entry.getPath()))) {
                    String strRet = findDir(entry, filter, iMaxLevel, iCurLevel);
                    if (strRet != null) {
                        return strRet;
                    }
                }
                i++;
            }
        }
        return null;
    }

    public static boolean checkSdPermissions() {
        if (VERSION.SDK_INT <= 18 || !fileExists(Utils.getSygicDirPath())) {
            return true;
        }
        try {
            File f = new File(Utils.getSygicDirPath().concat("/file.tmp"));
            if (f == null) {
                return false;
            }
            f.createNewFile();
            boolean isWritable = f.canWrite();
            f.delete();
            return isWritable;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File getExternalStorage(Context ctx) {
        File[] dirs = ContextCompat.getExternalFilesDirs(ctx, null);
        if (dirs == null) {
            return null;
        }
        for (int i = dirs.length - 1; i >= 0; i--) {
            if (dirs[i] != null) {
                return dirs[i];
            }
        }
        return null;
    }
}
