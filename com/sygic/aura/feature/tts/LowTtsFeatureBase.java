package com.sygic.aura.feature.tts;

import android.content.Context;
import android.content.Intent;
import com.sygic.aura.SygicMain;

/* compiled from: LowTtsFeature */
class LowTtsFeatureBase extends LowTtsFeature {
    protected LowTtsFeatureBase() {
    }

    protected LowTtsFeatureBase(Context context) {
        super(context);
    }

    public boolean load(String strTtsPath, String strResPath, long dwType) {
        boolean bRet = false;
        if (this.mTtsSound == null) {
            this.mTtsSound = new TTS_Loquendo(this.mCtx);
        }
        if (this.mTtsAndroid == null) {
            this.mTtsAndroid = new TtsAndroid(this.mCtx);
        }
        if (this.mTtsSound != null) {
            bRet = false | this.mTtsSound.TTS_Load(strTtsPath, strResPath, dwType);
        }
        if (this.mTtsAndroid != null) {
            return bRet | this.mTtsAndroid.load();
        }
        return bRet;
    }

    public void unload() {
        if (this.mTtsSound != null) {
            this.mTtsSound.TTS_Unload();
            this.mTtsSound = null;
        }
        if (this.mTtsAndroid != null) {
            this.mTtsAndroid.unload();
            this.mTtsAndroid = null;
        }
    }

    public int initialize(String strLanguage, String strVoice, int nVolume) {
        int iRet = 0;
        this.mbAndroid = strVoice.contains("System");
        if (!(this.mTtsSound == null || this.mbAndroid || !this.mTtsSound.TTS_Init(strLanguage, strVoice, nVolume))) {
            iRet = 1;
        }
        if (this.mTtsAndroid != null && this.mbAndroid && this.mTtsAndroid.init(strLanguage)) {
            return 2;
        }
        return iRet;
    }

    public boolean uninitialize() {
        if (this.mTtsSound != null && !this.mbAndroid) {
            return this.mTtsSound.TTS_Deinit();
        }
        if (this.mTtsAndroid == null || !this.mbAndroid) {
            return false;
        }
        return true;
    }

    public String getLanguageList(String[] supportedLocales, boolean installed) {
        String str;
        String strRet = "";
        if (this.mTtsSound != null && installed) {
            str = this.mTtsSound.TTS_GetLanguageList();
            if (str != null) {
                strRet = strRet + str;
            }
        }
        if (this.mTtsAndroid != null) {
            str = this.mTtsAndroid.getLanguageList(supportedLocales, installed);
            if (str != null) {
                strRet = strRet + str;
            }
        }
        if (strRet.equals("")) {
            return null;
        }
        return strRet;
    }

    public String getVoiceList(String strLanguage) {
        String str;
        String strRet = "";
        if (this.mTtsSound != null) {
            str = this.mTtsSound.TTS_GetVoiceList(strLanguage);
            if (str != null) {
                strRet = strRet + str;
            }
        }
        if (this.mTtsAndroid != null) {
            str = this.mTtsAndroid.getVoiceList(strLanguage);
            if (str != null) {
                strRet = strRet + str;
            }
        }
        if (strRet.equals("")) {
            return null;
        }
        return strRet;
    }

    public boolean setVolume(int nVolume) {
        if (this.mTtsSound == null || this.mbAndroid) {
            return false;
        }
        return this.mTtsSound.TTS_SetVolume(nVolume);
    }

    public boolean setSpeed(int nRate) {
        if (this.mTtsSound == null || this.mbAndroid) {
            return false;
        }
        return this.mTtsSound.TTS_SetSpeed(nRate);
    }

    public boolean isPlaying() {
        boolean bRet = false;
        if (!(this.mTtsSound == null || this.mbAndroid)) {
            bRet = false | this.mTtsSound.TTS_IsPlaying();
        }
        if (this.mTtsAndroid == null || !this.mbAndroid) {
            return bRet;
        }
        return bRet | this.mTtsAndroid.isPlaying();
    }

    public boolean play(String strTextToPlay, boolean bSynch) {
        if (!(this.mTtsAndroid == null && this.mTtsSound == null)) {
            SygicMain.getInstance().getFeature().getAutomotiveFeature().onAudioPlay();
        }
        if (this.mTtsSound != null && !this.mbAndroid) {
            return this.mTtsSound.TTS_Play(strTextToPlay);
        }
        if (this.mTtsAndroid == null || !this.mbAndroid) {
            return false;
        }
        SygicMain.getInstance().getFeature().getSoundFeature().waitForHfpIfNecessary();
        return this.mTtsAndroid.play(strTextToPlay);
    }

    public boolean stop() {
        if (this.mTtsSound != null && !this.mbAndroid) {
            return this.mTtsSound.TTS_Stop();
        }
        if (this.mTtsAndroid != null && !this.mbAndroid) {
            return this.mTtsAndroid.stop();
        }
        if (!(this.mTtsAndroid == null && this.mTtsSound == null)) {
            SygicMain.getInstance().getFeature().getAutomotiveFeature().onAudioStop();
        }
        return false;
    }

    public TtsAndroid getTtsAndroid() {
        return this.mTtsAndroid;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 228:
                if (resultCode == 1 && data.hasExtra("availableVoices")) {
                    this.mTtsAndroid.processAvailableTTSData(data.getStringArrayListExtra("availableVoices"));
                }
            default:
        }
    }
}
