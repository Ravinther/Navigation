package com.sygic.aura.settings.data;

public class VoiceEntry extends SoundEntry implements SoundInterface {
    private static final String TEST_FILE = "test.wav";
    private static final String VOICES_DIR = "voices";
    protected String mStrGender;
    protected String mStrISO;
    protected String mStrLanguage;
    protected String mStrLanguageISO;

    public VoiceEntry(String strISO, String strLanguageISO, String strLanguage, String strName, String strGender, String strFolder, boolean bTTS) {
        super(strName, strFolder, bTTS);
        this.mStrISO = strISO;
        this.mStrLanguageISO = strLanguageISO;
        this.mStrLanguage = strLanguage;
        this.mStrGender = strGender;
    }

    public VoiceEntry(VoiceEntry entry) {
        this(entry.mStrISO, entry.mStrLanguageISO, entry.mStrLanguage, entry.mStrName, entry.mStrGender, entry.mStrFolder, entry.mIsTTS);
    }

    public String getFolder() {
        return this.mStrFolder;
    }

    public String getName() {
        return getPersonName();
    }

    public String getSample() {
        return String.format("%s/%s/%s", new Object[]{VOICES_DIR, this.mStrFolder, TEST_FILE});
    }

    public String getPersonName() {
        return this.mStrName;
    }

    public String getISO() {
        return this.mStrISO;
    }

    public String getLanguageISO() {
        return this.mStrLanguageISO;
    }

    public String getLanguage() {
        return this.mStrLanguage;
    }

    public String getGender() {
        return this.mStrGender;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoiceEntry)) {
            return false;
        }
        VoiceEntry that = (VoiceEntry) o;
        if (this.mIsTTS != that.mIsTTS) {
            return false;
        }
        if (!this.mStrGender.equals(that.mStrGender)) {
            return false;
        }
        if (!this.mStrISO.equals(that.mStrISO)) {
            return false;
        }
        if (!this.mStrLanguage.equals(that.mStrLanguage)) {
            return false;
        }
        if (this.mStrName.equals(that.mStrName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((this.mStrISO.hashCode() * 31) + this.mStrLanguage.hashCode()) * 31) + this.mStrName.hashCode()) * 31) + this.mStrGender.hashCode()) * 31) + (this.mIsTTS ? 1 : 0);
    }
}
