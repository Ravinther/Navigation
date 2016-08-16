package com.sygic.aura.settings.data;

import android.text.TextUtils;

public class SoundEntry implements SoundInterface {
    protected boolean mIsTTS;
    protected String mStrFolder;
    protected String mStrName;

    public SoundEntry(String strName, String strFolder, boolean bIsTTS) {
        this.mStrName = strName;
        this.mStrFolder = strFolder;
        this.mIsTTS = bIsTTS;
    }

    public SoundEntry(SoundEntry entry) {
        this(entry.mStrName, entry.mStrFolder, entry.mIsTTS);
    }

    public String getName() {
        return this.mStrName;
    }

    public String getFolder() {
        return this.mStrFolder;
    }

    public String getSample() {
        return this.mStrFolder;
    }

    public boolean isTTS() {
        return this.mIsTTS;
    }

    public void setName(String name) {
        this.mStrName = name;
    }

    public static String getSoundName(String path) {
        if (TextUtils.isEmpty(path) || path.indexOf(46) < 0) {
            return "";
        }
        return path.substring(path.indexOf(47) + 1, path.indexOf(46));
    }
}
