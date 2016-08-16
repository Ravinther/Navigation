package com.sygic.aura.feature.tts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.widget.Toast;
import com.sygic.aura.SygicMain;
import com.sygic.aura.feature.sound.LowSoundFeature;
import com.sygic.base.C1799R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class TtsAndroid implements OnInitListener {
    private final AudioManager mAudioManager;
    private List<Locale> mAvailableTTSLocales;
    private Context mContext;
    private Map<String, Locale> mIsoGoogleMap;
    private Map<String, Locale> mIsoMap;
    private List<String> mLstLangs;
    private TTSDataRequestListener mTTSListener;
    private TextToSpeech mTts;
    private boolean m_bInitialized;
    private boolean m_bOnInit;

    /* renamed from: com.sygic.aura.feature.tts.TtsAndroid.1 */
    class C12441 implements OnUtteranceCompletedListener {
        final /* synthetic */ long val$id;

        C12441(long j) {
            this.val$id = j;
        }

        public void onUtteranceCompleted(String utteranceId) {
            if (Long.parseLong(utteranceId) == this.val$id) {
                synchronized (TtsAndroid.this) {
                    TtsAndroid.this.notify();
                }
            }
        }
    }

    public TtsAndroid(Context context) {
        this.m_bOnInit = false;
        this.m_bInitialized = false;
        this.mContext = context;
        this.mTts = new TextToSpeech(context, this);
        this.mLstLangs = new ArrayList();
        this.mAvailableTTSLocales = new ArrayList();
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
    }

    public void onInit(int status) {
        if (status == 0) {
            this.m_bOnInit = true;
            return;
        }
        this.m_bOnInit = false;
        this.mTts = null;
    }

    public boolean load() {
        return this.mTts != null && this.m_bOnInit;
    }

    public void unload() {
        if (this.mTts != null) {
            this.mTts.shutdown();
        }
    }

    @TargetApi(8)
    public String getEngine() {
        return this.mTts.getDefaultEngine();
    }

    public boolean openConfiguration(Context context) {
        if (getEngine() == null) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
        intent.addFlags(268435456);
        for (ResolveInfo info : pm.queryIntentActivities(intent, 0)) {
            if (getEngine().equals(info.activityInfo.packageName)) {
                intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }

    public Locale getLanguage() {
        if (this.mTts == null || !this.m_bOnInit) {
            return null;
        }
        return this.mTts.getLanguage();
    }

    private int setLanguage(String strLanguage) {
        return setLanguage(getLocale(strLanguage));
    }

    public int setLanguage(Locale locale) {
        return this.mTts.setLanguage(locale);
    }

    public boolean init(String strLanguage) {
        boolean z = true;
        int iRet = -2;
        if (this.mTts != null && this.m_bOnInit) {
            iRet = setLanguage(strLanguage);
        }
        if (iRet != 1) {
            z = false;
        }
        this.m_bInitialized = z;
        return this.m_bInitialized;
    }

    public String getLanguageList(String[] supportedLocales, boolean installed) {
        StringBuilder str = null;
        if (this.mLstLangs != null) {
            this.mLstLangs.clear();
        }
        if (this.mTts != null && this.m_bOnInit) {
            Locale[] arr = getAvailableLanguages(supportedLocales, installed);
            if (arr != null && arr.length > 0) {
                str = new StringBuilder();
                for (Locale locale : arr) {
                    String strIso = getIso(locale);
                    if (!(strIso == null || "".equals(strIso))) {
                        str.append(strIso).append(",");
                        this.mLstLangs.add(strIso);
                    }
                }
            }
        }
        if (str == null) {
            return null;
        }
        return str.toString();
    }

    public String getVoiceList(String strLanguage) {
        if (!this.mLstLangs.contains(strLanguage)) {
            return null;
        }
        this.mLstLangs.remove(strLanguage);
        return "System:Android,";
    }

    public boolean isPlaying() {
        if (this.mTts == null || !this.m_bOnInit) {
            return false;
        }
        return this.mTts.isSpeaking();
    }

    public boolean play(String strText) {
        int iRet = -1;
        if (this.mTts != null && this.m_bOnInit && this.m_bInitialized) {
            HashMap<String, String> params = new HashMap(1);
            params.put("streamType", Integer.toString(LowSoundFeature.getAudioStreamType(this.mAudioManager.isBluetoothScoOn())));
            long id = SystemClock.currentThreadTimeMillis();
            params.put("utteranceId", Long.toString(id));
            this.mTts.setOnUtteranceCompletedListener(new C12441(id));
            iRet = this.mTts.speak(strText, 0, params);
            synchronized (this) {
                try {
                    wait(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (iRet == 0) {
            return true;
        }
        return false;
    }

    public boolean stop() {
        int iRet = -1;
        if (this.mTts != null && this.m_bOnInit) {
            iRet = this.mTts.stop();
        }
        return iRet == 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.TargetApi(15)
    private java.util.Locale[] getAvailableLanguages(java.lang.String[] r10, boolean r11) {
        /*
        r9 = this;
        monitor-enter(r9);
        if (r11 != 0) goto L_0x002a;
    L_0x0003:
        r6 = "com.google.android.tts";
        r7 = r9.getEngine();	 Catch:{ all -> 0x0052 }
        r6 = r6.equals(r7);	 Catch:{ all -> 0x0052 }
        if (r6 == 0) goto L_0x002a;
    L_0x0010:
        r6 = r9.mAvailableTTSLocales;	 Catch:{ all -> 0x0052 }
        r6 = r6.isEmpty();	 Catch:{ all -> 0x0052 }
        if (r6 != 0) goto L_0x002a;
    L_0x0018:
        r6 = r9.mAvailableTTSLocales;	 Catch:{ all -> 0x0052 }
        r7 = r9.mAvailableTTSLocales;	 Catch:{ all -> 0x0052 }
        r7 = r7.size();	 Catch:{ all -> 0x0052 }
        r7 = new java.util.Locale[r7];	 Catch:{ all -> 0x0052 }
        r6 = r6.toArray(r7);	 Catch:{ all -> 0x0052 }
        r6 = (java.util.Locale[]) r6;	 Catch:{ all -> 0x0052 }
        monitor-exit(r9);	 Catch:{ all -> 0x0052 }
    L_0x0029:
        return r6;
    L_0x002a:
        monitor-exit(r9);	 Catch:{ all -> 0x0052 }
        r0 = new java.util.ArrayList;
        r0.<init>();
        if (r10 == 0) goto L_0x007c;
    L_0x0032:
        r6 = r9.mTts;
        if (r6 == 0) goto L_0x007c;
    L_0x0036:
        r6 = android.os.Build.VERSION.SDK_INT;
        r7 = 15;
        if (r6 < r7) goto L_0x007c;
    L_0x003c:
        r7 = r10.length;
        r6 = 0;
    L_0x003e:
        if (r6 >= r7) goto L_0x007c;
    L_0x0040:
        r5 = r10[r6];
        r4 = r9.getLocale(r5);
        r8 = r9.mTts;	 Catch:{ Exception -> 0x0071 }
        r3 = r8.isLanguageAvailable(r4);	 Catch:{ Exception -> 0x0071 }
        switch(r3) {
            case -1: goto L_0x0076;
            case 0: goto L_0x004f;
            case 1: goto L_0x0055;
            case 2: goto L_0x0055;
            default: goto L_0x004f;
        };
    L_0x004f:
        r6 = r6 + 1;
        goto L_0x003e;
    L_0x0052:
        r6 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x0052 }
        throw r6;
    L_0x0055:
        r8 = r9.mTts;	 Catch:{ Exception -> 0x0071 }
        r2 = r8.getFeatures(r4);	 Catch:{ Exception -> 0x0071 }
        if (r2 == 0) goto L_0x006d;
    L_0x005d:
        r8 = r2.isEmpty();	 Catch:{ Exception -> 0x0071 }
        if (r8 != 0) goto L_0x006d;
    L_0x0063:
        r8 = "embeddedTts";
        r8 = r2.contains(r8);	 Catch:{ Exception -> 0x0071 }
        r8 = r8 ^ r11;
        if (r8 != 0) goto L_0x004f;
    L_0x006d:
        r0.add(r4);	 Catch:{ Exception -> 0x0071 }
        goto L_0x004f;
    L_0x0071:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x004f;
    L_0x0076:
        if (r11 != 0) goto L_0x004f;
    L_0x0078:
        r0.add(r4);	 Catch:{ Exception -> 0x0071 }
        goto L_0x004f;
    L_0x007c:
        r6 = r0.size();
        r6 = new java.util.Locale[r6];
        r6 = r0.toArray(r6);
        r6 = (java.util.Locale[]) r6;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.tts.TtsAndroid.getAvailableLanguages(java.lang.String[], boolean):java.util.Locale[]");
    }

    private String getIso(Locale locale) {
        if (locale != null) {
            for (Entry<String, Locale> set : getIsoMap().entrySet()) {
                if (((Locale) set.getValue()).equals(locale)) {
                    return (String) set.getKey();
                }
            }
        }
        return "";
    }

    private Locale getLocale(String strLanguage) {
        return (Locale) getIsoMap().get(strLanguage);
    }

    private Locale getGoogleTTSLocale(String strLanguage) {
        return (Locale) getGoogleIsoMap().get(strLanguage);
    }

    private Map<String, Locale> getIsoMap() {
        if (this.mIsoMap == null) {
            this.mIsoMap = new HashMap(53);
            this.mIsoMap.put("ENG", Locale.UK);
            this.mIsoMap.put("ENU", Locale.US);
            this.mIsoMap.put("ENA", new Locale("en", "AU"));
            this.mIsoMap.put("FRA", Locale.FRANCE);
            this.mIsoMap.put("DEU", Locale.GERMANY);
            this.mIsoMap.put("ESP", new Locale("es", "ES"));
            this.mIsoMap.put("ITA", Locale.ITALY);
            this.mIsoMap.put("SWE", new Locale("sv", "SE"));
            this.mIsoMap.put("DAN", new Locale("da", "DK"));
            this.mIsoMap.put("NOR", new Locale("nb", "NO"));
            this.mIsoMap.put("FIN", new Locale("fi", "FI"));
            this.mIsoMap.put("POR", new Locale("pt", "PT"));
            this.mIsoMap.put("TUR", new Locale("tr", "TR"));
            this.mIsoMap.put("RUS", new Locale("ru", "RU"));
            this.mIsoMap.put("NLD", new Locale("nl", "NL"));
            this.mIsoMap.put("BEN", new Locale("nl", "BE"));
            this.mIsoMap.put("CZE", new Locale("cs", "CZ"));
            this.mIsoMap.put("POL", new Locale("pl", "PL"));
            this.mIsoMap.put("JPN", Locale.JAPAN);
            this.mIsoMap.put("THA", new Locale("th", "TH"));
            this.mIsoMap.put("FRC", new Locale("fr", "CA"));
            this.mIsoMap.put("GRE", new Locale("el", "GR"));
            this.mIsoMap.put("IDN", new Locale("id", "ID"));
            this.mIsoMap.put("KOR", Locale.KOREA);
            this.mIsoMap.put("POB", new Locale("pt", "BR"));
            this.mIsoMap.put("SVK", new Locale("sk", "SK"));
            this.mIsoMap.put("CAT", new Locale("ca", "ES"));
            this.mIsoMap.put("CHI", Locale.CHINA);
            this.mIsoMap.put("ENI", new Locale("en", "IN"));
            this.mIsoMap.put("ESM", new Locale("es", "MX"));
            this.mIsoMap.put("ESA", new Locale("es", "US"));
            this.mIsoMap.put("ESR", new Locale("es", "AR"));
            this.mIsoMap.put("ARA", new Locale("ar", "ae"));
            this.mIsoMap.put("HKG", new Locale("zh", "HK"));
            this.mIsoMap.put("TWN", new Locale("zh", "TW"));
            this.mIsoMap.put("BEL", new Locale("nl", "BE"));
            this.mIsoMap.put("ESG", new Locale("gl", "ES"));
            this.mIsoMap.put("IND", new Locale("hi", "IN"));
            this.mIsoMap.put("HUN", new Locale("hu", "HU"));
            this.mIsoMap.put("RUM", new Locale("ro", "RO"));
        }
        return this.mIsoMap;
    }

    private Map<String, Locale> getGoogleIsoMap() {
        if (this.mIsoGoogleMap == null) {
            this.mIsoGoogleMap = new HashMap(17);
            this.mIsoGoogleMap.put("por-bra", new Locale("pt", "BR"));
            this.mIsoGoogleMap.put("fra-fra", Locale.FRANCE);
            this.mIsoGoogleMap.put("pol-pol", new Locale("pl", "PL"));
            this.mIsoGoogleMap.put("kor-kor", Locale.KOREA);
            this.mIsoGoogleMap.put("eng-ind", new Locale("en", "IN"));
            this.mIsoGoogleMap.put("nld-nld", new Locale("nl", "NL"));
            this.mIsoGoogleMap.put("eng-gbr", Locale.UK);
            this.mIsoGoogleMap.put("rus-rus", new Locale("ru", "RU"));
            this.mIsoGoogleMap.put("eng-usa", Locale.US);
            this.mIsoGoogleMap.put("spa-usa", new Locale("es", "US"));
            this.mIsoGoogleMap.put("ita-ita", Locale.ITALY);
            this.mIsoGoogleMap.put("deu-deu", Locale.GERMANY);
            this.mIsoGoogleMap.put("spa-esp", new Locale("es", "ES"));
            this.mIsoGoogleMap.put("jpn-jpn", Locale.JAPAN);
            this.mIsoGoogleMap.put("ind-idn", new Locale("id", "ID"));
            this.mIsoGoogleMap.put("hin-ind", new Locale("hi", "IN"));
        }
        return this.mIsoGoogleMap;
    }

    public void checkTTSData(TTSDataRequestListener listener) {
        Activity activity = this.mContext instanceof Activity ? (Activity) this.mContext : SygicMain.getActivity();
        PackageManager pm = this.mContext.getPackageManager();
        Intent intent = new Intent();
        intent.setAction("android.speech.tts.engine.CHECK_TTS_DATA");
        List<ResolveInfo> resolveInfo = pm.queryIntentActivities(intent, 0);
        if (resolveInfo.isEmpty()) {
            Toast.makeText(activity, C1799R.string.anui_settings_voices_tts_no_engine, 0).show();
            return;
        }
        for (ResolveInfo info : resolveInfo) {
            if (getEngine().equals(info.activityInfo.packageName)) {
                intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                break;
            }
        }
        this.mTTSListener = listener;
        activity.startActivityForResult(intent, 228);
    }

    public synchronized void processAvailableTTSData(ArrayList<String> availableTTSData) {
        this.mAvailableTTSLocales.clear();
        Iterator it = availableTTSData.iterator();
        while (it.hasNext()) {
            this.mAvailableTTSLocales.add(getGoogleTTSLocale((String) it.next()));
        }
        if (this.mTTSListener != null) {
            this.mTTSListener.onDataReady();
        }
    }
}
