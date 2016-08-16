package com.sygic.aura.store.data;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.feature.tts.TtsAndroid;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.settings.data.VoiceEntry;
import com.sygic.aura.store.data.ComponentEntry.InstallStatus;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SystemTTSEntry extends ComponentEntry {
    public static final Creator<SystemTTSEntry> CREATOR;

    /* renamed from: com.sygic.aura.store.data.SystemTTSEntry.1 */
    class C17061 implements Runnable {
        final /* synthetic */ Locale val$current;
        final /* synthetic */ TtsAndroid val$tts;

        C17061(TtsAndroid ttsAndroid, Locale locale) {
            this.val$tts = ttsAndroid;
            this.val$current = locale;
        }

        public void run() {
            this.val$tts.play(".");
            this.val$tts.setLanguage(this.val$current);
        }
    }

    /* renamed from: com.sygic.aura.store.data.SystemTTSEntry.2 */
    static class C17072 implements Creator<SystemTTSEntry> {
        C17072() {
        }

        public SystemTTSEntry createFromParcel(Parcel source) {
            return new SystemTTSEntry(source);
        }

        public SystemTTSEntry[] newArray(int size) {
            return new SystemTTSEntry[size];
        }
    }

    protected SystemTTSEntry(VoiceEntry voice) {
        super(Integer.toString(voice.hashCode() * -1), voice.getLanguage(), null, voice.getLanguageISO(), InstallStatus.IsNotInstalled.ordinal(), false, false, false, -1);
    }

    public static SystemTTSEntry[] createFromVoiceEntries(VoiceEntry[] voices) {
        int i = 0;
        if (voices == null) {
            return new SystemTTSEntry[0];
        }
        List<SystemTTSEntry> results = new ArrayList(voices.length);
        int length = voices.length;
        while (i < length) {
            VoiceEntry voice = voices[i];
            if (voice.isTTS()) {
                results.add(new SystemTTSEntry(voice));
            }
            i++;
        }
        return (SystemTTSEntry[]) results.toArray(new SystemTTSEntry[results.size()]);
    }

    public String getSummary() {
        return "System";
    }

    public boolean install() {
        TtsAndroid tts = SygicHelper.getAndroidTts();
        if (tts == null) {
            return false;
        }
        if (!"com.google.android.tts".equals(tts.getEngine())) {
            return SygicHelper.getAndroidTts().openConfiguration(SygicHelper.getActivity());
        }
        Locale current = tts.getLanguage();
        boolean ret = tts.init(this.mISO);
        new Handler().post(new C17061(tts, current));
        return ret;
    }

    public EViewType getType() {
        return EViewType.TYPE_COMPONENT;
    }

    protected SystemTTSEntry(Parcel in) {
        super(in);
    }

    static {
        CREATOR = new C17072();
    }
}
