package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener;
import loquendo.tts.engine.TTSConst;

class zzo implements ContainerHolder {
    private Status zzQA;
    private final Looper zzYV;
    private Container zzaPa;
    private Container zzaPb;
    private zzb zzaPc;
    private zza zzaPd;
    private TagManager zzaPe;
    private boolean zzahz;

    public interface zza {
    }

    private class zzb extends Handler {
        private final ContainerAvailableListener zzaPf;
        final /* synthetic */ zzo zzaPg;

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TTSConst.TTSMULTILINE /*1*/:
                    zzeA((String) msg.obj);
                default:
                    zzbg.m1447e("Don't know how to handle this message.");
            }
        }

        protected void zzeA(String str) {
            this.zzaPf.onContainerAvailable(this.zzaPg, str);
        }
    }

    public zzo(Status status) {
        this.zzQA = status;
        this.zzYV = null;
    }

    public zzo(TagManager tagManager, Looper looper, Container container, zza com_google_android_gms_tagmanager_zzo_zza) {
        this.zzaPe = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzYV = looper;
        this.zzaPa = container;
        this.zzaPd = com_google_android_gms_tagmanager_zzo_zza;
        this.zzQA = Status.zzaaD;
        tagManager.zza(this);
    }

    public synchronized Container getContainer() {
        Container container = null;
        synchronized (this) {
            if (this.zzahz) {
                zzbg.m1447e("ContainerHolder is released.");
            } else {
                if (this.zzaPb != null) {
                    this.zzaPa = this.zzaPb;
                    this.zzaPb = null;
                }
                container = this.zzaPa;
            }
        }
        return container;
    }

    public Status getStatus() {
        return this.zzQA;
    }

    public synchronized void release() {
        if (this.zzahz) {
            zzbg.m1447e("Releasing a released ContainerHolder.");
        } else {
            this.zzahz = true;
            this.zzaPe.zzb(this);
            this.zzaPa.release();
            this.zzaPa = null;
            this.zzaPb = null;
            this.zzaPd = null;
            this.zzaPc = null;
        }
    }

    public synchronized void zzew(String str) {
        if (!this.zzahz) {
            this.zzaPa.zzew(str);
        }
    }
}
