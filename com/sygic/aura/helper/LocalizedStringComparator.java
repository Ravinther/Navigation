package com.sygic.aura.helper;

import android.text.TextUtils;
import java.text.Collator;
import java.util.Comparator;

public class LocalizedStringComparator implements Comparator<String> {
    private static LocalizedStringComparator mInstance;
    private static String mLanguage;
    private Collator mCollator;

    public static LocalizedStringComparator getInstance(String language) {
        if (mInstance == null || TextUtils.equals(mLanguage, language)) {
            mInstance = new LocalizedStringComparator(language);
        }
        return mInstance;
    }

    private LocalizedStringComparator(String language) {
        mLanguage = language;
        this.mCollator = Collator.getInstance(LocaleHelper.getLocale(language));
        this.mCollator.setDecomposition(1);
        this.mCollator.setStrength(2);
    }

    public int compare(String lhs, String rhs) {
        return this.mCollator.compare(lhs, rhs);
    }
}
