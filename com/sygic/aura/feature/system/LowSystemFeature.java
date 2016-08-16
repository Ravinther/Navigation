package com.sygic.aura.feature.system;

import android.content.Context;
import android.media.MediaScannerConnection;
import com.sygic.aura.clazz.ImageInfo;
import com.sygic.aura.feature.ActivityListener;
import com.sygic.aura.feature.ResultListener;

public abstract class LowSystemFeature implements ActivityListener, ResultListener {
    protected Context mCtx;
    protected DropBoxLogin mDropBoxLogin;
    protected FacebookConnect mFacebookConnect;
    protected ImageInfo mImageInfo;
    protected MediaScannerConnection mMsc;
    protected boolean m_bEnableEvents;
    protected boolean mbIsLabrary;

    public enum EEventType {
        ETNone(0),
        ETStart(1),
        ETEnd(2),
        ETUserId(3),
        ETMarketplace(4),
        ETFBEvent(5);
        
        final int nValue;

        private EEventType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }

        static EEventType createFromInt(int value) {
            for (EEventType eSize : values()) {
                if (eSize.getValue() == value) {
                    return eSize;
                }
            }
            return ETNone;
        }
    }

    public abstract void browserOpenUri(String str, String str2, String str3);

    public abstract void createShortcut(String str, String str2);

    public abstract void enableEventLogging(boolean z);

    public abstract String getDeviceName();

    public abstract boolean getFullscreen();

    public abstract String getGUID();

    public abstract Object getImage(int i, int i2);

    public abstract String getOSVersion();

    public abstract String getPackageName();

    public abstract Object getPhoto(int i, int i2);

    public abstract String getPushToken();

    public abstract String getReferral();

    public abstract int getRotationLock();

    public abstract void logEvent(String str, String str2, String str3, int i);

    public abstract void sendEmail(String str, String str2, String str3);

    public abstract void setFullscreen(boolean z);

    public abstract void setRotationLock(boolean z);

    public abstract Object takePhoto(long j, long j2);

    protected LowSystemFeature() {
        this.m_bEnableEvents = false;
        this.mbIsLabrary = false;
    }

    public LowSystemFeature(Context context) {
        this.m_bEnableEvents = false;
        this.mbIsLabrary = false;
        this.mCtx = context;
    }

    public static LowSystemFeature createInstance(Context context) {
        return new LowSystemFeatureBase(context);
    }

    public void setLibrary(boolean bIsLibrary) {
        this.mbIsLabrary = bIsLibrary;
    }
}
