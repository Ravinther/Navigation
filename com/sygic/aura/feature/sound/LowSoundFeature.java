package com.sygic.aura.feature.sound;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import com.sygic.aura.IRemoteService;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.utils.Utils;

public abstract class LowSoundFeature {
    protected AudioManager mAudioManager;
    protected Context mContext;
    protected transient Handler mHandler;
    protected IRemoteService mRemoteService;
    protected Sound mSound;
    protected SoundManager mSoundManager;
    protected boolean mbSpeakerOn;
    protected int mnAudioMode;

    public abstract void deinit();

    public abstract void forceSpeaker(boolean z);

    public abstract int getBufferingTime();

    public abstract int init(long j, int i);

    public abstract boolean mutex(boolean z);

    public abstract void play();

    public abstract void setVolume(int i);

    public abstract void stop();

    public abstract void stop(boolean z);

    public abstract void waitForHfpIfNecessary();

    public abstract void write(byte[] bArr, int i);

    protected LowSoundFeature(Context context, IRemoteService service, Handler handler) {
        this.mnAudioMode = -1;
        this.mbSpeakerOn = false;
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mRemoteService = service;
        this.mHandler = handler;
        if (Utils.getAndroidVersion() >= 8) {
            this.mSoundManager = new SoundManager(this.mAudioManager);
        }
        if (this.mAudioManager != null) {
            this.mnAudioMode = this.mAudioManager.getMode();
            this.mbSpeakerOn = this.mAudioManager.isSpeakerphoneOn();
        }
    }

    public static LowSoundFeature createInstance(Context context, IRemoteService service, Handler handler) {
        return new LowSoundFeatureBase(context, service, handler);
    }

    public static int getAudioStreamType(boolean startingBluetooth) {
        if (startingBluetooth) {
            return 0;
        }
        return ProjectsConsts.getInt(20);
    }
}
