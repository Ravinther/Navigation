package com.sygic.aura.feature.device;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

class DeviceID {
    private static String get() {
        File cidfile = findCidFile(new File("/sys/class/mmc_host"));
        if (cidfile == null) {
            return "";
        }
        String line = readFirstLine(cidfile);
        if (line == null) {
            return "";
        }
        return line.substring(0, 2) + line.substring(18, 26);
    }

    public static String get(String strPath) {
        String strRet = get();
        if (strPath != null) {
            String strMount = checkLine("/proc/mounts", new String[]{strPath}, new int[]{1}, 0);
            if (strMount != null) {
                String strId = strMount.substring(strMount.lastIndexOf("/") + 1);
                if (strId != null) {
                    String[] arrStrId = strId.split(":");
                    if (arrStrId != null && arrStrId.length > 1) {
                        String strBlock = checkLine("/proc/partitions", new String[]{arrStrId[0], arrStrId[1]}, new int[]{0, 1}, 3);
                        if (strBlock != null) {
                            try {
                                strBlock = new File("/sys/class/block/" + strBlock).getCanonicalPath();
                                if (strBlock != null) {
                                    String strMmc = null;
                                    if (strBlock.indexOf("mmc_host/") != -1) {
                                        strMmc = strBlock.substring(strBlock.indexOf("mmc_host/") + 9);
                                    }
                                    if (strMmc != null) {
                                        String strMmc0 = strMmc.substring(0, strMmc.indexOf("/"));
                                        strBlock = strMmc.substring(strMmc.indexOf("/") + 1);
                                        String strMmc01 = strBlock.substring(0, strBlock.indexOf("/"));
                                        if (!(strMmc0 == null || strMmc01 == null)) {
                                            String strType = readFirstLine(new File("/sys/class/mmc_host/" + strMmc0 + "/" + strMmc01 + "/type"));
                                            String strSdId = readFirstLine(new File("/sys/class/mmc_host/" + strMmc0 + "/" + strMmc01 + "/cid"));
                                            if (!(strSdId == null || strType == null || !strType.equalsIgnoreCase("sd"))) {
                                                strRet = strSdId.substring(0, 2) + strSdId.substring(18, 26);
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                return strRet;
                            }
                        }
                    }
                }
            }
        }
        return strRet;
    }

    private static File findCidFile(File rootdir) {
        if (!rootdir.isDirectory()) {
            return null;
        }
        File cidfile = getCidFile(rootdir);
        if (cidfile != null) {
            return cidfile;
        }
        for (File file : rootdir.listFiles()) {
            if (file.getName().substring(0, 3).equalsIgnoreCase("mmc")) {
                cidfile = findCidFile(file);
                if (cidfile != null) {
                    return cidfile;
                }
            }
        }
        return null;
    }

    private static File getCidFile(File dir) {
        int i = 0;
        if (!dir.isDirectory()) {
            return null;
        }
        File[] files = dir.listFiles();
        File cidfile = null;
        Boolean is_sd_type = Boolean.valueOf(false);
        int length = files.length;
        while (i < length) {
            File file = files[i];
            String name = file.getName();
            if (name.equals("cid") && file.isFile()) {
                cidfile = file;
            }
            if (!is_sd_type.booleanValue() && name.equals("type") && file.isFile() && file.canRead()) {
                String line = readFirstLine(file);
                if (line != null) {
                    is_sd_type = Boolean.valueOf(line.equalsIgnoreCase("sd"));
                }
                if (!is_sd_type.booleanValue()) {
                    return null;
                }
            }
            i++;
        }
        if (is_sd_type.booleanValue()) {
            return cidfile;
        }
        return null;
    }

    private static String readFirstLine(File f) {
        try {
            String line = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(f)))).readLine();
            if (line != null) {
                return line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String checkLine(java.lang.String r11, java.lang.String[] r12, int[] r13, int r14) {
        /*
        r8 = 0;
        r9 = r12.length;
        r10 = r13.length;
        if (r9 == r10) goto L_0x0007;
    L_0x0005:
        r9 = 0;
    L_0x0006:
        return r9;
    L_0x0007:
        r3 = new java.io.File;	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r3.<init>(r11);	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r4 = new java.io.FileReader;	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r4.<init>(r3);	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r1 = new java.io.BufferedReader;	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r1.<init>(r4);	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
    L_0x0016:
        r7 = r1.readLine();	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        if (r7 == 0) goto L_0x003a;
    L_0x001c:
        r9 = r7.trim();	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r10 = "\\s+";
        r0 = r9.split(r10);	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r9 = r0.length;	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r10 = r13.length;	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        if (r9 < r10) goto L_0x0016;
    L_0x002b:
        r6 = 0;
        r5 = 0;
    L_0x002d:
        r9 = r13.length;	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        if (r5 >= r9) goto L_0x0035;
    L_0x0030:
        r9 = r0.length;	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r10 = r13[r5];	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        if (r9 >= r10) goto L_0x0042;
    L_0x0035:
        r9 = r13.length;	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        if (r6 != r9) goto L_0x0016;
    L_0x0038:
        r8 = r0[r14];	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
    L_0x003a:
        r1.close();	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r4.close();	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
    L_0x0040:
        r9 = r8;
        goto L_0x0006;
    L_0x0042:
        r9 = r13[r5];	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r9 = r0[r9];	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r10 = r12[r5];	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        r9 = r9.equals(r10);	 Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0058 }
        if (r9 == 0) goto L_0x0050;
    L_0x004e:
        r6 = r6 + 1;
    L_0x0050:
        r5 = r5 + 1;
        goto L_0x002d;
    L_0x0053:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x0040;
    L_0x0058:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.device.DeviceID.checkLine(java.lang.String, java.lang.String[], int[], int):java.lang.String");
    }
}
