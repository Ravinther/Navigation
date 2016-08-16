package com.sygic.aura.feature.device;

import android.content.Context;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

public abstract class LowDeviceFeature {
    protected Context mContext;
    protected TelephonyManager mTelephonyManager;
    protected Vibrator mVibrator;

    public abstract boolean getFeature(boolean z, int i);

    public abstract String getId(String str);

    public abstract String getImei();

    public abstract String getLocale();

    public abstract String getMacAddress();

    public abstract void vibrate(long j);

    protected LowDeviceFeature() {
    }

    protected LowDeviceFeature(Context context) {
        this.mContext = context;
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public static LowDeviceFeature createInstance(Context context) {
        return new LowDeviceFeatureBase(context);
    }
}
