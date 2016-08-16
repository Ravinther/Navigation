package com.sygic.aura.feature.tts;

import android.content.Context;
import android.os.Environment;
import com.sygic.aura.utils.FileUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import loquendo.tts.engine.TTSConst;
import loquendo.tts.engine.TTSEvent;
import loquendo.tts.engine.TTSException;
import loquendo.tts.engine.TTSLanguage;
import loquendo.tts.engine.TTSListener;
import loquendo.tts.engine.TTSReader;
import loquendo.tts.engine.TTSSession;
import loquendo.tts.engine.TTSVoice;

public class TTS_Loquendo implements TTSListener {
    private final String TAG;
    private AndroidAudioDestination mAudioDest;
    private final List<String> mLstLangs;
    private TTSLanguage mTtsLanguage;
    private TTSVoice mTtsVoice;
    private TTSReader m_TtsReader;
    private TTSSession m_TtsSession;
    private boolean m_bIsPlaying;
    private int m_nVolume;

    /* renamed from: com.sygic.aura.feature.tts.TTS_Loquendo.1 */
    class C12431 implements Runnable {
        C12431() {
        }

        public void run() {
            TTS_Loquendo.this.TTS_Deinit();
            try {
                if (TTS_Loquendo.this.m_TtsReader != null) {
                    TTS_Loquendo.this.m_TtsReader = null;
                }
                if (TTS_Loquendo.this.m_TtsSession != null) {
                    TTS_Loquendo.this.m_TtsSession.delete();
                    TTS_Loquendo.this.m_TtsSession = null;
                }
            } catch (TTSException e) {
                e.printStackTrace();
            }
        }
    }

    public TTS_Loquendo(Context ctx) {
        this.TAG = "Sygic TTS";
        this.m_TtsSession = null;
        this.m_TtsReader = null;
        this.m_bIsPlaying = false;
        this.m_nVolume = -1;
        this.mAudioDest = null;
        this.mLstLangs = new ArrayList();
    }

    public boolean TTS_Load(String strTtsPath, String strResPath, long dwType) {
        String strLicName = "LoquendoTTSLicense.txt";
        if (!FileUtils.fileExists("/mnt/sdcard/LoquendoTTS/global.ttsconf") && !FileUtils.fileExists("/sdcard/LoquendoTTS/global.ttsconf") && !FileUtils.fileExists(Environment.getExternalStorageDirectory().getPath() + "/LoquendoTTS/global.ttsconf")) {
            return false;
        }
        try {
            this.m_TtsSession = new TTSSession();
        } catch (TTSException e) {
            e.printStackTrace();
        }
        if (this.m_TtsSession == null) {
            return false;
        }
        try {
            this.m_TtsReader = new TTSReader(this.m_TtsSession);
            if (this.m_TtsReader == null) {
                return false;
            }
            this.mAudioDest = new AndroidAudioDestination(this.m_TtsReader, "_Android");
            if (this.mAudioDest == null) {
                return false;
            }
            this.m_TtsReader.addTTSListener(this);
            this.mAudioDest.setAudio(16000, "linear", 1);
            return true;
        } catch (TTSException e2) {
            e2.printStackTrace();
        }
    }

    public void TTS_Unload() {
        new Thread(new C12431()).start();
    }

    public boolean TTS_Init(String strLanguage, String strVoice, int nVolume) {
        if (this.m_TtsSession == null) {
            return false;
        }
        try {
            this.m_TtsReader.setParam("TextCoding", "utf8");
            this.mTtsLanguage = new TTSLanguage(this.m_TtsSession, getLanguage(strLanguage));
            this.m_TtsReader.setLanguage(this.mTtsLanguage);
            this.mTtsVoice = new TTSVoice(this.m_TtsSession, strVoice);
            this.m_TtsReader.setVoice(this.mTtsVoice);
            TTS_SetVolume(nVolume);
        } catch (TTSException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean TTS_Deinit() {
        if (this.m_TtsSession == null) {
            return false;
        }
        try {
            if (this.mTtsLanguage != null) {
                this.mTtsLanguage.delete();
                this.mTtsLanguage = null;
            }
            if (this.mTtsVoice != null) {
                this.mTtsVoice.delete();
                this.mTtsVoice = null;
            }
            return true;
        } catch (TTSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String TTS_GetLanguageList() {
        int i = 0;
        if (this.m_TtsSession == null) {
            return null;
        }
        if (this.mLstLangs != null) {
            this.mLstLangs.clear();
        }
        String strLanguages = null;
        String str = "";
        Iterator it = this.m_TtsSession.query("Id", "*", 1024, false, true, false);
        if (it.hasNext()) {
            strLanguages = it.next().toString();
        }
        if (strLanguages == null) {
            return str;
        }
        String[] arrLang = strLanguages.split(";");
        int length = arrLang.length;
        while (i < length) {
            String strIso = addLanguage(arrLang[i]);
            str = str + strIso;
            this.mLstLangs.add(strIso.replace(",", ""));
            i++;
        }
        return str;
    }

    public String TTS_GetVoiceList(String strLanguage) {
        int i = false;
        if (this.m_TtsSession == null || !this.mLstLangs.contains(strLanguage)) {
            return null;
        }
        this.mLstLangs.remove(strLanguage);
        String strVoices = null;
        String strRetVoices = "";
        Iterator it = this.m_TtsSession.query("Id", "MotherTongue=" + getLanguage(strLanguage), 1024, false, true, true);
        if (it.hasNext()) {
            strVoices = it.next().toString();
        }
        if (strVoices == null || strVoices.length() == 0) {
            return strRetVoices;
        }
        strRetVoices = strVoices + ":";
        String[] arrLang = strVoices.split(";");
        int length = arrLang.length;
        while (i < length) {
            Iterator it2 = this.m_TtsSession.queryAttribute("Gender", arrLang[i], 1024, true);
            while (it2.hasNext()) {
                String str = it2.next().toString();
                if (str.equals("Female") || str.equals("female")) {
                    strRetVoices = strRetVoices + "Female,";
                } else if (str.equals("Male") || str.equals("male")) {
                    strRetVoices = strRetVoices + "Male,";
                }
            }
            i++;
        }
        if (strRetVoices.equals(":")) {
            return "";
        }
        return strRetVoices;
    }

    public boolean TTS_SetVolume(int nVolume) {
        if (this.m_TtsReader == null) {
            return false;
        }
        try {
            if (this.m_nVolume != nVolume) {
                this.m_nVolume = nVolume;
                this.m_TtsReader.setVolume(100);
            }
            return true;
        } catch (TTSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean TTS_SetSpeed(int nRate) {
        if (this.m_TtsReader == null) {
            return false;
        }
        try {
            this.m_TtsReader.setSpeed(nRate);
            return true;
        } catch (TTSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean TTS_IsPlaying() {
        return this.m_bIsPlaying;
    }

    public boolean TTS_Play(String strTextToPlay) {
        if (this.m_TtsSession == null || this.m_TtsReader == null || strTextToPlay == null) {
            return false;
        }
        TTS_Stop();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        try {
            if (this.mAudioDest != null) {
                this.mAudioDest.unlock();
            }
            this.m_TtsReader.read(strTextToPlay, false, false);
            return true;
        } catch (TTSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean TTS_Stop() {
        if (this.m_TtsReader == null) {
            return false;
        }
        try {
            this.m_TtsReader.stop();
            if (this.mAudioDest != null && this.mAudioDest.isSpeaking()) {
                this.mAudioDest.stop();
            }
            return true;
        } catch (TTSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eventOccurred(TTSEvent event) {
        switch (event.getReason().intValue()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.m_bIsPlaying = true;
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                this.m_bIsPlaying = false;
                break;
        }
        this.mAudioDest.eventOccurred(event);
    }

    private String getLanguage(String strLng) {
        if (strLng.equals("ENG")) {
            return "EnglishGb";
        }
        if (strLng.equals("ENU")) {
            return "EnglishUs";
        }
        if (strLng.equals("ENA")) {
            return "EnglishAu";
        }
        if (strLng.equals("FRA")) {
            return "FrenchFr";
        }
        if (strLng.equals("DEU")) {
            return "German";
        }
        if (strLng.equals("ESP")) {
            return "SpanishEs";
        }
        if (strLng.equals("ITA")) {
            return "Italian";
        }
        if (strLng.equals("SWE")) {
            return "Swedish";
        }
        if (strLng.equals("DAN")) {
            return "Danish";
        }
        if (strLng.equals("NOR")) {
            return "Norwegian";
        }
        if (strLng.equals("FIN")) {
            return "Finnish";
        }
        if (strLng.equals("POR")) {
            return "PortuguesePt";
        }
        if (strLng.equals("TUR")) {
            return "Turkish";
        }
        if (strLng.equals("ICE")) {
            return "Icelandi";
        }
        if (strLng.equals("RUS")) {
            return "Russian";
        }
        if (strLng.equals("NLD")) {
            return "Dutch";
        }
        if (strLng.equals("BEN")) {
            return "DutchBe";
        }
        if (strLng.equals("CZE")) {
            return "Czech";
        }
        if (strLng.equals("POL")) {
            return "Polish";
        }
        if (strLng.equals("MTW")) {
            return "MandarinTw";
        }
        if (strLng.equals("CNT")) {
            return "Cantonese";
        }
        if (strLng.equals("MCH")) {
            return "ChineseMn";
        }
        if (strLng.equals("JPN")) {
            return "Japanese";
        }
        if (strLng.equals("THA")) {
            return "Thai";
        }
        if (strLng.equals("FRC")) {
            return "FrenchCa";
        }
        if (strLng.equals("GRE")) {
            return "Greek";
        }
        if (strLng.equals("HIN")) {
            return "Hindi";
        }
        if (strLng.equals("KOR")) {
            return "Korean";
        }
        if (strLng.equals("POB")) {
            return "PortugueseBr";
        }
        if (strLng.equals("ESM")) {
            return "SpanishMx";
        }
        if (strLng.equals("ARA")) {
            return "Arabic";
        }
        if (strLng.equals("SVK")) {
            return "Slovak";
        }
        if (strLng.equals("CAT")) {
            return "CatalanCe";
        }
        if (strLng.equals("GAL")) {
            return "Galician";
        }
        if (strLng.equals("CHI")) {
            return "Chinese";
        }
        if (strLng.equals("ENI")) {
            return "EnglishIn";
        }
        if (strLng.equals("RUM")) {
            return "Romanian";
        }
        if (strLng.equals("ESA")) {
            return "SpanishAm";
        }
        if (strLng.equals("ESR")) {
            return "SpanishAr";
        }
        if (strLng.equals("ESC")) {
            return "SpanishCl";
        }
        return "";
    }

    private String addLanguage(String strLng) {
        if (strLng.equals("EnglishGb")) {
            return "ENG,";
        }
        if (strLng.equals("EnglishUs")) {
            return "ENU,";
        }
        if (strLng.equals("EnglishAu")) {
            return "ENA,";
        }
        if (strLng.equals("FrenchFr")) {
            return "FRA,";
        }
        if (strLng.equals("German")) {
            return "DEU,";
        }
        if (strLng.equals("SpanishEs")) {
            return "ESP,";
        }
        if (strLng.equals("Italian")) {
            return "ITA,";
        }
        if (strLng.equals("Swedish")) {
            return "SWE,";
        }
        if (strLng.equals("Danish")) {
            return "DAN,";
        }
        if (strLng.equals("Norwegian")) {
            return "NOR,";
        }
        if (strLng.equals("Finnish")) {
            return "FIN,";
        }
        if (strLng.equals("PortuguesePt")) {
            return "POR,";
        }
        if (strLng.equals("Turkish")) {
            return "TUR,";
        }
        if (strLng.equals("Icelandic")) {
            return "ICE,";
        }
        if (strLng.equals("Russian")) {
            return "RUS,";
        }
        if (strLng.equals("Dutch")) {
            return "NLD,";
        }
        if (strLng.equals("DutchBe")) {
            return "BEN,";
        }
        if (strLng.equals("Czech")) {
            return "CZE,";
        }
        if (strLng.equals("Polish")) {
            return "POL,";
        }
        if (strLng.equals("MandarinTw")) {
            return "MTW,";
        }
        if (strLng.equals("Contonese")) {
            return "CNT,";
        }
        if (strLng.equals("ChineseMn")) {
            return "MCH,";
        }
        if (strLng.equals("Japanese")) {
            return "JPN,";
        }
        if (strLng.equals("Thai")) {
            return "THA,";
        }
        if (strLng.equals("FrenchCa")) {
            return "FRC,";
        }
        if (strLng.equals("Greek")) {
            return "GRE,";
        }
        if (strLng.equals("Hindi")) {
            return "HIN,";
        }
        if (strLng.equals("Korean")) {
            return "KOR,";
        }
        if (strLng.equals("PortugueseBr")) {
            return "POB,";
        }
        if (strLng.equals("SpanishMx")) {
            return "ESM,";
        }
        if (strLng.equals("Arabic")) {
            return "ARA,";
        }
        if (strLng.equals("Slovak")) {
            return "SVK,";
        }
        if (strLng.equals("CatalanCe")) {
            return "CAT,";
        }
        if (strLng.equals("Galician")) {
            return "GAL,";
        }
        if (strLng.equals("Chinese")) {
            return "CHI,";
        }
        if (strLng.equals("EnglishIn")) {
            return "ENI,";
        }
        if (strLng.equals("Romanian")) {
            return "RUM,";
        }
        if (strLng.equals("SpanishAm")) {
            return "ESA,";
        }
        if (strLng.equals("SpanishAr")) {
            return "ESR,";
        }
        if (strLng.equals("SpanishCl")) {
            return "ESC,";
        }
        return "";
    }
}
