package com.sygic.aura.feature.sound;

import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.os.Handler;
import com.sygic.aura.SygicMain;
import com.sygic.aura.utils.Utils;

public class Sound {
    private AudioTrack m_AudioTrack;
    private final Runnable m_ReleaseRunnable;

    /* renamed from: com.sygic.aura.feature.sound.Sound.1 */
    class C12311 implements Runnable {
        C12311() {
        }

        public void run() {
            if (Sound.this.m_AudioTrack != null && Sound.this.m_AudioTrack.getPlayState() != 3) {
                Sound.this.m_AudioTrack.release();
            }
        }
    }

    /* renamed from: com.sygic.aura.feature.sound.Sound.2 */
    class C12322 implements OnPlaybackPositionUpdateListener {
        C12322() {
        }

        public void onMarkerReached(AudioTrack track) {
            synchronized (Sound.this) {
                Sound.this.notify();
            }
        }

        public void onPeriodicNotification(AudioTrack track) {
        }
    }

    public Sound() {
        this.m_AudioTrack = null;
        this.m_ReleaseRunnable = new C12311();
    }

    public int Init(long lRate, int nChannels, int streamType) {
        if (nChannels == 1) {
            if (Utils.getAndroidVersion() < 5) {
                nChannels = 2;
            } else {
                nChannels = 4;
            }
        } else if (nChannels == 2) {
            if (Utils.getAndroidVersion() < 5) {
                nChannels = 3;
            } else {
                nChannels = 12;
            }
        } else if (Utils.getAndroidVersion() < 5) {
            nChannels = 1;
        } else {
            nChannels = 1;
        }
        try {
            this.m_AudioTrack = new AudioTrack(streamType, (int) lRate, nChannels, 2, 32768, 1);
            if (this.m_AudioTrack != null) {
                return 0;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
    }

    public void SetVolume(int nVolume) {
        if (this.m_AudioTrack != null) {
            this.m_AudioTrack.setStereoVolume(((float) nVolume) / 10.0f, ((float) nVolume) / 10.0f);
        }
    }

    public void Write(byte[] lpBuffer, int nSize) {
        if (this.m_AudioTrack != null) {
            synchronized (this) {
                this.m_AudioTrack.setPlaybackPositionUpdateListener(new C12322());
                this.m_AudioTrack.setNotificationMarkerPosition(nSize / 2);
                int nBytesRead = 0;
                while (nBytesRead < nSize) {
                    int nRead = this.m_AudioTrack.write(lpBuffer, nBytesRead, nSize - nBytesRead);
                    if (nRead <= 0) {
                        return;
                    }
                    nBytesRead += nRead;
                }
                try {
                    wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Play() {
        if (this.m_AudioTrack != null) {
            SygicMain.getInstance().getFeature().getAutomotiveFeature().onAudioPlay();
            if (this.m_AudioTrack.getState() == 1) {
                Handler handler = SygicMain.getHandler();
                if (handler != null) {
                    handler.removeCallbacks(this.m_ReleaseRunnable);
                }
                this.m_AudioTrack.play();
            }
        }
    }

    public void Stop(boolean immediately) {
        if (this.m_AudioTrack != null) {
            if (this.m_AudioTrack.getState() == 1) {
                this.m_AudioTrack.stop();
                if (immediately) {
                    this.m_AudioTrack.flush();
                }
            }
            SygicMain.getInstance().getFeature().getAutomotiveFeature().onAudioStop();
            Handler handler = SygicMain.getHandler();
            if (handler != null) {
                handler.removeCallbacks(this.m_ReleaseRunnable);
                handler.postDelayed(this.m_ReleaseRunnable, 1000);
            }
        }
    }
}
