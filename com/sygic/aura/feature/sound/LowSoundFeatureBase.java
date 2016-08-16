package com.sygic.aura.feature.sound;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import com.sygic.aura.IRemoteService;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.base.C1799R;
import loquendo.tts.engine.TTSConst;

/* compiled from: LowSoundFeature */
class LowSoundFeatureBase extends LowSoundFeature {
    private BroadcastReceiver mCallStateReceiver;
    private boolean mHfpNotified;
    private boolean mIsHfpStarting;
    private boolean mIsInCall;
    private boolean mIsSpeakerForced;
    private long mLastPlayedTime;
    private ScoStateReceiver mScoStateReceiver;

    /* renamed from: com.sygic.aura.feature.sound.LowSoundFeatureBase.1 */
    class LowSoundFeature extends BroadcastReceiver {

        /* renamed from: com.sygic.aura.feature.sound.LowSoundFeatureBase.1.1 */
        class LowSoundFeature implements Runnable {
            LowSoundFeature() {
            }

            public void run() {
                LowSoundFeatureBase.this.forceSpeaker(LowSoundFeatureBase.this.mIsSpeakerForced);
            }
        }

        LowSoundFeature() {
        }

        public void onReceive(Context context, Intent intent) {
            if (TelephonyManager.EXTRA_STATE_IDLE.equals(intent.getStringExtra("state"))) {
                LowSoundFeatureBase.this.mIsInCall = false;
                LowSoundFeatureBase.this.mHandler.postDelayed(new LowSoundFeature(), 1000);
                return;
            }
            LowSoundFeatureBase.this.mIsInCall = true;
        }
    }

    /* renamed from: com.sygic.aura.feature.sound.LowSoundFeatureBase.2 */
    class LowSoundFeature implements Runnable {
        LowSoundFeature() {
        }

        public void run() {
            LowSoundFeatureBase.this.mSoundManager.abandonAudioFocus();
            if (LowSoundFeatureBase.this.mAudioManager.isBluetoothScoOn()) {
                LowSoundFeatureBase.this.mAudioManager.stopBluetoothSco();
                LowSoundFeatureBase.this.mLastPlayedTime = SystemClock.elapsedRealtime();
            }
        }
    }

    /* compiled from: LowSoundFeature */
    private class ScoStateReceiver extends BroadcastReceiver {
        private boolean mFirst;

        private ScoStateReceiver() {
            this.mFirst = true;
        }

        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1)) {
                case PoiListItem.ITEM_SPECIAL_NEARBY_POI /*-1*/:
                    break;
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    if (this.mFirst) {
                        this.mFirst = false;
                        return;
                    }
                    break;
                case TTSConst.TTSMULTILINE /*1*/:
                    LowSoundFeatureBase.this.mContext.unregisterReceiver(this);
                    synchronized (LowSoundFeatureBase.this) {
                        LowSoundFeatureBase.this.mHfpNotified = true;
                        LowSoundFeatureBase.this.notify();
                        break;
                    }
                    return;
                default:
                    return;
            }
            LowSoundFeatureBase.this.mContext.unregisterReceiver(this);
            LowSoundFeatureBase.this.mAudioManager.stopBluetoothSco();
            synchronized (LowSoundFeatureBase.this) {
                LowSoundFeatureBase.this.mHfpNotified = true;
                LowSoundFeatureBase.this.notify();
            }
        }

        public ScoStateReceiver reset() {
            this.mFirst = true;
            return this;
        }
    }

    protected LowSoundFeatureBase(Context context, IRemoteService service, Handler handler) {
        boolean z = false;
        super(context, service, handler);
        this.mIsSpeakerForced = false;
        this.mIsInCall = false;
        this.mLastPlayedTime = 0;
        this.mScoStateReceiver = new ScoStateReceiver();
        this.mCallStateReceiver = new LowSoundFeature();
        if (((TelephonyManager) context.getSystemService("phone")).getCallState() != 0) {
            z = true;
        }
        this.mIsInCall = z;
    }

    public int init(long lRate, int nChannels) {
        if (this.mSound == null) {
            this.mSound = new Sound();
            this.mContext.registerReceiver(this.mCallStateReceiver, new IntentFilter("android.intent.action.PHONE_STATE"));
        }
        if (this.mSound == null) {
            return -1;
        }
        return this.mSound.Init(lRate, nChannels, LowSoundFeature.getAudioStreamType(shouldStartBluetoothSco()));
    }

    public void deinit() {
        if (this.mSound != null) {
            this.mSound = null;
            this.mContext.unregisterReceiver(this.mCallStateReceiver);
        }
        if (this.mAudioManager != null) {
            if (this.mnAudioMode != -1) {
                this.mAudioManager.setMode(this.mnAudioMode);
            }
            this.mAudioManager.setSpeakerphoneOn(this.mbSpeakerOn);
        }
    }

    public void setVolume(int nVolume) {
        if (this.mSound != null) {
            this.mSound.SetVolume(nVolume);
        }
    }

    public void write(byte[] lpBuffer, int nSize) {
        if (!this.mIsInCall) {
            waitForHfpIfNecessary();
            if (this.mSound != null) {
                this.mSound.Write(lpBuffer, nSize);
            }
        }
    }

    public void play() {
        if (this.mSound != null && !this.mIsInCall) {
            this.mSound.Play();
        }
    }

    public void stop() {
        stop(false);
    }

    public void stop(boolean immediately) {
        if (this.mSound != null) {
            this.mSound.Stop(immediately);
        }
    }

    public boolean mutex(boolean bMutex) {
        if (this.mRemoteService != null) {
            try {
                return this.mRemoteService.soundMutex(bMutex);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            if (this.mSoundManager != null) {
                int iDelay;
                if (!bMutex) {
                    iDelay = ProjectsConsts.getInt(22);
                    if (iDelay < 0) {
                        iDelay = 0;
                    }
                    this.mHandler.postDelayed(new LowSoundFeature(), (long) iDelay);
                } else if (SystemClock.elapsedRealtime() - this.mLastPlayedTime < 1000) {
                    return false;
                } else {
                    iDelay = ProjectsConsts.getInt(21);
                    if (iDelay < 0) {
                        iDelay = 0;
                    }
                    if (iDelay > 0) {
                        try {
                            Thread.sleep((long) iDelay);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                    }
                    boolean shouldStartBluetoothSco = shouldStartBluetoothSco();
                    this.mSoundManager.requestAudioFocus(LowSoundFeature.getAudioStreamType(shouldStartBluetoothSco));
                    if (shouldStartBluetoothSco) {
                        startBluetoothSco();
                    }
                }
            }
            return true;
        }
    }

    public void forceSpeaker(boolean bValue) {
        this.mIsSpeakerForced = bValue;
        if (this.mAudioManager != null) {
            if (bValue) {
                this.mAudioManager.setMode(2);
            } else {
                this.mAudioManager.setMode(0);
            }
            this.mAudioManager.setSpeakerphoneOn(bValue);
        }
    }

    private boolean shouldStartBluetoothSco() {
        if (PreferenceManager.getDefaultSharedPreferences(this.mContext).getBoolean(this.mContext.getString(C1799R.string.settings_sound_bluetooth_hfp), true) && isBluetoothHfpConnected() && this.mAudioManager.isBluetoothScoAvailableOffCall() && !this.mAudioManager.isMusicActive()) {
            return true;
        }
        return false;
    }

    private boolean isBluetoothHfpConnected() {
        boolean z = true;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            return false;
        }
        if (defaultAdapter.getProfileConnectionState(1) != 2) {
            z = false;
        }
        return z;
    }

    private void startBluetoothSco() {
        this.mContext.registerReceiver(this.mScoStateReceiver.reset(), new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
        this.mIsHfpStarting = true;
        this.mHfpNotified = false;
        this.mAudioManager.startBluetoothSco();
    }

    public int getBufferingTime() {
        if (shouldStartBluetoothSco()) {
            return getHfpDelayInMillis();
        }
        return 0;
    }

    private int getHfpDelayInMillis() {
        return PreferenceManager.getDefaultSharedPreferences(this.mContext).getInt(this.mContext.getString(C1799R.string.settings_sound_bluetooth_hfp_delay), 30) * 100;
    }

    public void waitForHfpIfNecessary() {
        if (this.mIsHfpStarting) {
            this.mIsHfpStarting = false;
            waitForHfpConnection();
        }
    }

    private void waitForHfpConnection() {
        try {
            if (!this.mHfpNotified) {
                synchronized (this) {
                    wait(10000);
                }
            }
            if (this.mAudioManager.isBluetoothScoOn()) {
                Thread.sleep((long) getHfpDelayInMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
