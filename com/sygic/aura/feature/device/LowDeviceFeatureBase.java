package com.sygic.aura.feature.device;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import com.crashlytics.android.Crashlytics;
import com.sygic.aura.c2dm.DeviceReg;
import com.sygic.aura.utils.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

/* compiled from: LowDeviceFeature */
class LowDeviceFeatureBase extends LowDeviceFeature {
    private LowDeviceFeatureBase() {
    }

    protected LowDeviceFeatureBase(Context context) {
        super(context);
    }

    public void vibrate(long lTime) {
        if (this.mVibrator != null) {
            this.mVibrator.vibrate(lTime);
        }
    }

    public String getId(String strDevice) {
        String strId = null;
        if (strDevice.equalsIgnoreCase("imei")) {
            strId = getImei();
        } else if (strDevice.equalsIgnoreCase("sdcard")) {
            strId = DeviceID.get(FileUtils.getSDCardPath());
        } else if (strDevice.compareToIgnoreCase("sdcard_reduced") == 0) {
            strId = DeviceID.get(FileUtils.getSDCardPath());
            if (strId != null && strId.length() > 2) {
                strId = strId.substring(2);
            }
        } else if (strDevice.compareToIgnoreCase("mac") == 0) {
            strId = getWifiMacAddress();
        } else if (strDevice.compareToIgnoreCase("imsi") == 0) {
            strId = this.mTelephonyManager.getSubscriberId();
        } else if (strDevice.compareToIgnoreCase("uuid") == 0) {
            strId = getSystemProperties(this.mContext, "tcc.hwinfo.uuid");
        } else if (strDevice.compareToIgnoreCase("android_id") == 0) {
            strId = System.getString(this.mContext.getContentResolver(), "android_id");
        }
        if (strId == null || strId.equals("")) {
            strId = getWifiMacAddress();
        }
        if (!(strId == null || strId.equals(""))) {
            DeviceReg.checkRegistration(this.mContext, strId);
        }
        return strId;
    }

    public String getLocale() {
        return Locale.getDefault().toString();
    }

    public boolean getFeature(boolean bReturned, int nFeature) {
        return bReturned;
    }

    public String getImei() {
        return this.mTelephonyManager.getDeviceId();
    }

    public String getMacAddress() {
        return getWifiMacAddress();
    }

    protected String getWifiMacAddress() {
        String strId = getWifiMacAddressInternal();
        if (strId != null || !tryEnableWifi()) {
            return strId;
        }
        strId = getWifiMacAddressInternal();
        tryDisableWifi();
        return strId;
    }

    private String getWifiMacAddressInternal() {
        String strId = null;
        WifiManager wm = (WifiManager) this.mContext.getSystemService("wifi");
        if (wm != null) {
            WifiInfo wi = wm.getConnectionInfo();
            if (wi == null) {
                return null;
            }
            strId = wi.getMacAddress();
            if (strId == null || VERSION.SDK_INT >= 23) {
                strId = getMacFromFile();
            } else {
                strId = strId.replace(":", "");
            }
        }
        return strId;
    }

    private String getMacFromFile() {
        Exception e;
        Throwable th;
        StringBuilder sb = new StringBuilder();
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fis = new FileInputStream(new File("/sys/class/net/wlan0", "address"));
            while (true) {
                try {
                    int b = fis.read();
                    if (b == -1) {
                        break;
                    }
                    sb.append((char) b);
                } catch (Exception e2) {
                    e = e2;
                    fileInputStream = fis;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fis;
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            fileInputStream = fis;
            return sb.toString().trim();
        } catch (Exception e4) {
            e = e4;
            try {
                Crashlytics.log(6, "MAC_ADDRESS", "unable to read mac address from file");
                Crashlytics.logException(e);
                if (fileInputStream == null) {
                    return null;
                }
                try {
                    fileInputStream.close();
                    return null;
                } catch (IOException e32) {
                    e32.printStackTrace();
                    return null;
                }
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e322) {
                        e322.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }

    protected boolean tryEnableWifi() {
        WifiManager wm = (WifiManager) this.mContext.getSystemService("wifi");
        if (wm == null || wm.getWifiState() != 1) {
            return false;
        }
        wm.setWifiEnabled(true);
        int i = 0;
        while (true) {
            if (wm.getWifiState() == 3 && i <= 10) {
                return true;
            }
            i++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    private void tryDisableWifi() {
        WifiManager wm = (WifiManager) this.mContext.getSystemService("wifi");
        if (wm != null) {
            wm.setWifiEnabled(false);
        }
    }

    private static String getSystemProperties(Context context, String key) {
        String strRet = "";
        try {
            Class SystemProperties = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) SystemProperties.getMethod("get", new Class[]{String.class}).invoke(SystemProperties, new Object[]{new String(key)});
        } catch (IllegalArgumentException e) {
            return "";
        } catch (Exception e2) {
            return "";
        }
    }
}
