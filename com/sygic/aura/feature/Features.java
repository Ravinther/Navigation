package com.sygic.aura.feature;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import com.sygic.aura.IRemoteService;
import com.sygic.aura.IRemoteService.Stub;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.SygicMain;
import com.sygic.aura.feature.automotive.LowAutomotiveFeature;
import com.sygic.aura.feature.common.CommonFeature;
import com.sygic.aura.feature.device.LowDeviceFeature;
import com.sygic.aura.feature.gl.LowGlFeature;
import com.sygic.aura.feature.gps.LowGpsFeature;
import com.sygic.aura.feature.net.LowNetFeature;
import com.sygic.aura.feature.phone.LowPhoneFeature;
import com.sygic.aura.feature.sound.LowSoundFeature;
import com.sygic.aura.feature.store.LowStoreFeature;
import com.sygic.aura.feature.system.LowSystemFeature;
import com.sygic.aura.feature.time.LowTimeFeature;
import com.sygic.aura.feature.tts.LowTtsFeature;

public class Features {
    private Activity mActivity;
    private LowAutomotiveFeature mAutomotiveFeature;
    private CommonFeature mCommonFeature;
    private Context mCtx;
    private LowDeviceFeature mDeviceFeature;
    private LowGlFeature mGlFeature;
    private LowGpsFeature mGpsFeature;
    private Handler mHandler;
    private LowNetFeature mNetFeature;
    private LowPhoneFeature mPhoneFeature;
    private IRemoteService mRemoteService;
    private LowSoundFeature mSoundFeature;
    private LowStoreFeature mStoreFeature;
    private LowSystemFeature mSystemFeature;
    private LowTimeFeature mTimeFeature;
    private LowTtsFeature mTtsFeature;

    /* renamed from: com.sygic.aura.feature.Features.1 */
    class C12111 implements ServiceConnection {
        C12111() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            Features.this.mRemoteService = Stub.asInterface(service);
        }

        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private Features() {
        this.mCtx = null;
    }

    public Features(Context context, Handler handler) {
        this.mCtx = null;
        this.mCtx = context;
        this.mHandler = handler;
        bindRemoteService();
    }

    private void bindRemoteService() {
        if (ProjectsConsts.getBoolean(2)) {
            this.mCtx.bindService(new Intent("com.sygic.aura.SYGIC_SERVICE"), new C12111(), 1);
        }
    }

    public LowTimeFeature getTimeFeature() {
        if (this.mTimeFeature == null) {
            this.mTimeFeature = LowTimeFeature.createInstance();
        }
        return this.mTimeFeature;
    }

    public LowSoundFeature getSoundFeature() {
        if (this.mSoundFeature == null) {
            this.mSoundFeature = LowSoundFeature.createInstance(this.mCtx, this.mRemoteService, this.mHandler);
        }
        return this.mSoundFeature;
    }

    public LowTtsFeature getTtsFeature() {
        if (this.mTtsFeature == null) {
            this.mTtsFeature = LowTtsFeature.createInstance(this.mCtx);
        }
        return this.mTtsFeature;
    }

    public LowNetFeature getNetFeature() {
        if (this.mNetFeature == null) {
            this.mNetFeature = LowNetFeature.createInstance(this.mCtx);
        }
        return this.mNetFeature;
    }

    public LowPhoneFeature getPhoneFeature() {
        if (this.mPhoneFeature == null) {
            this.mPhoneFeature = LowPhoneFeature.createInstance(this.mCtx);
        }
        return this.mPhoneFeature;
    }

    public LowSystemFeature getSystemFeature() {
        if (this.mSystemFeature == null) {
            this.mSystemFeature = LowSystemFeature.createInstance(this.mCtx);
        }
        return this.mSystemFeature;
    }

    public LowAutomotiveFeature getAutomotiveFeature() {
        if (this.mAutomotiveFeature == null) {
            this.mAutomotiveFeature = LowAutomotiveFeature.createInstance(this.mCtx);
        }
        return this.mAutomotiveFeature;
    }

    public LowDeviceFeature getDeviceFeature() {
        if (this.mDeviceFeature == null) {
            this.mDeviceFeature = LowDeviceFeature.createInstance(this.mCtx);
        }
        return this.mDeviceFeature;
    }

    public LowStoreFeature getStoreFeature() {
        if (this.mStoreFeature == null) {
            this.mStoreFeature = LowStoreFeature.createInstance(this.mCtx, this.mHandler);
        }
        return this.mStoreFeature;
    }

    public LowGlFeature getGlFeature() {
        if (this.mGlFeature == null) {
            if (SygicMain.getSurface() == null) {
                throw new IllegalStateException("Null surface in getGlFeature()");
            }
            this.mGlFeature = LowGlFeature.createInstance(this.mCtx);
        }
        return this.mGlFeature;
    }

    public LowGpsFeature getGpsFeature() {
        if (this.mGpsFeature == null) {
            this.mGpsFeature = LowGpsFeature.createInstance(this.mCtx, this.mHandler);
        }
        return this.mGpsFeature;
    }

    public CommonFeature getCommonFeature() {
        if (this.mCommonFeature == null) {
            this.mCommonFeature = CommonFeature.createInstance(this.mCtx, this.mHandler);
        }
        return this.mCommonFeature;
    }

    public void setActivity(Activity act) {
        this.mActivity = act;
    }

    public Activity getActivity() {
        return this.mActivity;
    }
}
