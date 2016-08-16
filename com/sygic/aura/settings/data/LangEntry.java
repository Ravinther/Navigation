package com.sygic.aura.settings.data;

public class LangEntry {
    protected String mStrFileName;
    protected String mStrISO;
    protected String mStrLanguage;

    public LangEntry(String strLanguage, String strISO, String strFileName) {
        this.mStrLanguage = strLanguage;
        this.mStrISO = strISO;
        this.mStrFileName = strFileName;
    }

    public LangEntry(LangEntry entry) {
        this(entry.mStrLanguage, entry.mStrISO, entry.mStrFileName);
    }

    public String getLanguage() {
        return this.mStrLanguage;
    }

    public String getISO() {
        return this.mStrISO;
    }

    public String getFileName() {
        return this.mStrFileName;
    }
}
