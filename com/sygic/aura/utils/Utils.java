package com.sygic.aura.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Utils {
    private static Process mProcLogCat;
    private static String m_strIniFilesDir;
    private static String m_strSygicDir;
    public static String m_strSygicPath;

    /* renamed from: com.sygic.aura.utils.Utils.1 */
    static class C17751 implements Runnable {
        final /* synthetic */ String val$strLogCat;

        C17751(String str) {
            this.val$strLogCat = str;
        }

        public void run() {
            Throwable th;
            BufferedReader br = null;
            OutputStreamWriter osw = null;
            try {
                Runtime.getRuntime().exec(new String[]{"logcat", "-c"});
                Utils.mProcLogCat = Runtime.getRuntime().exec("/system/bin/logcat -b main -b system");
                if (Utils.mProcLogCat == null) {
                    if (Utils.mProcLogCat != null) {
                        Utils.mProcLogCat.destroy();
                        Utils.mProcLogCat = null;
                    }
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (osw != null) {
                        try {
                            osw.write("---Log finished---");
                            osw.flush();
                            osw.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        return;
                    }
                    return;
                }
                BufferedReader br2 = new BufferedReader(new InputStreamReader(Utils.mProcLogCat.getInputStream()));
                try {
                    OutputStreamWriter osw2 = new OutputStreamWriter(new FileOutputStream(this.val$strLogCat, false));
                    if (br2 == null || osw2 == null) {
                        if (Utils.mProcLogCat != null) {
                            Utils.mProcLogCat.destroy();
                            Utils.mProcLogCat = null;
                        }
                        if (br2 != null) {
                            try {
                                br2.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        if (osw2 != null) {
                            try {
                                osw2.write("---Log finished---");
                                osw2.flush();
                                osw2.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                            return;
                        }
                        return;
                    }
                    try {
                        osw2.write("---Starting log---\n");
                        osw2.flush();
                        while (Utils.mProcLogCat != null) {
                            String strLine = br2.readLine();
                            if (strLine != null) {
                                osw2.write(strLine + "\n");
                                osw2.flush();
                            }
                        }
                        osw2.write("---Log finished---");
                        osw2.flush();
                        br2.close();
                        br = null;
                        try {
                            osw2.close();
                            osw = null;
                            if (Utils.mProcLogCat != null) {
                                Utils.mProcLogCat.destroy();
                                Utils.mProcLogCat = null;
                            }
                            if (br != null) {
                                try {
                                    br.close();
                                } catch (IOException e2222) {
                                    e2222.printStackTrace();
                                }
                            }
                            if (osw != null) {
                                try {
                                    osw.write("---Log finished---");
                                    osw.flush();
                                    osw.close();
                                } catch (IOException e22222) {
                                    e22222.printStackTrace();
                                }
                            }
                        } catch (IOException e3) {
                            osw = osw2;
                            if (Utils.mProcLogCat != null) {
                                Utils.mProcLogCat.destroy();
                                Utils.mProcLogCat = null;
                            }
                            if (br != null) {
                                try {
                                    br.close();
                                } catch (IOException e222222) {
                                    e222222.printStackTrace();
                                }
                            }
                            if (osw == null) {
                                try {
                                    osw.write("---Log finished---");
                                    osw.flush();
                                    osw.close();
                                } catch (IOException e2222222) {
                                    e2222222.printStackTrace();
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            osw = osw2;
                            if (Utils.mProcLogCat != null) {
                                Utils.mProcLogCat.destroy();
                                Utils.mProcLogCat = null;
                            }
                            if (br != null) {
                                try {
                                    br.close();
                                } catch (IOException e22222222) {
                                    e22222222.printStackTrace();
                                }
                            }
                            if (osw != null) {
                                try {
                                    osw.write("---Log finished---");
                                    osw.flush();
                                    osw.close();
                                } catch (IOException e222222222) {
                                    e222222222.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e4) {
                        osw = osw2;
                        br = br2;
                        if (Utils.mProcLogCat != null) {
                            Utils.mProcLogCat.destroy();
                            Utils.mProcLogCat = null;
                        }
                        if (br != null) {
                            br.close();
                        }
                        if (osw == null) {
                            osw.write("---Log finished---");
                            osw.flush();
                            osw.close();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        osw = osw2;
                        br = br2;
                        if (Utils.mProcLogCat != null) {
                            Utils.mProcLogCat.destroy();
                            Utils.mProcLogCat = null;
                        }
                        if (br != null) {
                            br.close();
                        }
                        if (osw != null) {
                            osw.write("---Log finished---");
                            osw.flush();
                            osw.close();
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    br = br2;
                    if (Utils.mProcLogCat != null) {
                        Utils.mProcLogCat.destroy();
                        Utils.mProcLogCat = null;
                    }
                    if (br != null) {
                        br.close();
                    }
                    if (osw == null) {
                        osw.write("---Log finished---");
                        osw.flush();
                        osw.close();
                    }
                } catch (Throwable th4) {
                    th = th4;
                    br = br2;
                    if (Utils.mProcLogCat != null) {
                        Utils.mProcLogCat.destroy();
                        Utils.mProcLogCat = null;
                    }
                    if (br != null) {
                        br.close();
                    }
                    if (osw != null) {
                        osw.write("---Log finished---");
                        osw.flush();
                        osw.close();
                    }
                    throw th;
                }
            } catch (IOException e6) {
                if (Utils.mProcLogCat != null) {
                    Utils.mProcLogCat.destroy();
                    Utils.mProcLogCat = null;
                }
                if (br != null) {
                    br.close();
                }
                if (osw == null) {
                    osw.write("---Log finished---");
                    osw.flush();
                    osw.close();
                }
            } catch (Throwable th5) {
                th = th5;
                if (Utils.mProcLogCat != null) {
                    Utils.mProcLogCat.destroy();
                    Utils.mProcLogCat = null;
                }
                if (br != null) {
                    br.close();
                }
                if (osw != null) {
                    osw.write("---Log finished---");
                    osw.flush();
                    osw.close();
                }
                throw th;
            }
        }
    }

    static {
        m_strSygicDir = "Sygic";
        m_strIniFilesDir = "Android";
        m_strSygicPath = null;
    }

    public static int getAndroidVersion() {
        return VERSION.SDK_INT;
    }

    public static String getSygicDirName() {
        return m_strSygicDir;
    }

    public static void setSygicDirName(String strDirName) {
        m_strSygicDir = strDirName;
    }

    public static String getIniFilesDir() {
        return m_strIniFilesDir;
    }

    public static String getSygicDirPath() {
        if (m_strSygicPath == null) {
            m_strSygicPath = FileUtils.getSygicRoot(getSygicDirName(), getIniFilesDir());
            if (m_strSygicPath == null) {
                m_strSygicPath = FileUtils.getSDCardPath();
            }
            m_strSygicPath += "/" + m_strSygicDir;
        }
        return m_strSygicPath;
    }

    public static void setSygicDirPath(String strPath) {
        m_strSygicPath = strPath;
    }

    public static String getBaseClassName() {
        return "com.sygic.aura";
    }

    public static void runLogs() {
        String strLogCat = getSygicDirPath() + "/" + getIniFilesDir() + "/logcat.txt";
        if (FileUtils.fileExists(strLogCat)) {
            new Thread(new C17751(strLogCat)).start();
        }
    }

    public static void stopLogs() {
        if (mProcLogCat != null) {
            mProcLogCat.destroy();
            mProcLogCat = null;
        }
    }

    public static int getAppVersion(Context context) {
        int nVersionCode = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return nVersionCode;
        }
    }

    public static int getResVersion() {
        int nVersion = 0;
        String strVerFile = getSygicDirPath() + "/version";
        File inputFile = new File(getSygicDirPath() + "/version");
        if (inputFile != null && FileUtils.fileExists(strVerFile)) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(inputFile));
                String str = in.readLine();
                in.close();
                if (str != null) {
                    str = str.trim();
                    if (str.length() > 0) {
                        nVersion = Integer.parseInt(str);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (NumberFormatException e3) {
                e3.printStackTrace();
            }
        }
        return nVersion;
    }

    public static void setResVersion(int nVersion) {
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        BufferedWriter out = null;
        try {
            BufferedWriter out2 = new BufferedWriter(new FileWriter(new File(getSygicDirPath() + "/version")));
            try {
                out2.write(String.valueOf(nVersion));
                if (out2 != null) {
                    try {
                        out2.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        out = out2;
                        return;
                    }
                }
                out = out2;
            } catch (FileNotFoundException e4) {
                e2 = e4;
                out = out2;
                try {
                    e2.printStackTrace();
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e322) {
                            e322.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e322 = e5;
                out = out2;
                e322.printStackTrace();
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e3222) {
                        e3222.printStackTrace();
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                if (out != null) {
                    out.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e6) {
            e2 = e6;
            e2.printStackTrace();
            if (out != null) {
                out.close();
            }
        } catch (IOException e7) {
            e3222 = e7;
            e3222.printStackTrace();
            if (out != null) {
                out.close();
            }
        }
    }

    public static String getSygicString(Context context, String strResId) {
        if (strResId == null) {
            return null;
        }
        int id = context.getResources().getIdentifier("app_name", "string", context.getPackageName());
        return id != 0 ? strResId.replace("%app_name%", context.getString(id)) : strResId;
    }

    public static String getSygicString(Context context, int nResId) {
        if (nResId == 0) {
            return null;
        }
        return getSygicString(context, context.getString(nResId));
    }
}
