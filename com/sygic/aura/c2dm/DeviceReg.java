package com.sygic.aura.c2dm;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import com.sygic.aura.ProjectsConsts;

public class DeviceReg {
    private static final String TAG;
    public static String mStrDeviceId;

    static {
        TAG = DeviceReg.class.getName();
        mStrDeviceId = null;
    }

    public static void checkRegistration(Context context, String strDeviceId) {
        mStrDeviceId = strDeviceId;
        try {
            checkDevice(context);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (C2DMessaging.getRegistrationId(context).equals("")) {
            String senderId = ProjectsConsts.getString(16);
            if (senderId == null) {
                senderId = "522187714847";
            }
            C2DMessaging.register(context, senderId);
        }
    }

    public static void checkDevice(Context context) {
        int version = VERSION.SDK_INT;
        if (version < 8) {
            throw new UnsupportedOperationException("Device must be at least API Level 8 (instead of " + version + ")");
        }
        try {
            context.getPackageManager().getPackageInfo("com.google.android.gsf", 0);
        } catch (NameNotFoundException e) {
            throw new UnsupportedOperationException("Device does not have package com.google.android.gsf");
        }
    }
}
