package com.sygic.aura.helper;

import java.text.Collator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

public class LocaleHelper {
    private static HashMap<String, Locale> sLanguageMapping;

    public static Locale getLocale(String language) {
        if (sLanguageMapping == null) {
            sLanguageMapping = getLanguageToLocaleMapping();
        }
        Locale result = (Locale) sLanguageMapping.get(language);
        return result != null ? result : Locale.getDefault();
    }

    private static HashMap<String, Locale> getLanguageToLocaleMapping() {
        HashMap<String, Locale> result = new HashMap();
        result.put("arabic.lang", new Locale("ar"));
        result.put("bulgarian.lang", new Locale("bg"));
        result.put("catalan.lang", new Locale("ca"));
        result.put("chinese_simplified.lang", Locale.SIMPLIFIED_CHINESE);
        result.put("chinese_traditional_hkg.lang", new Locale("zh", "HK"));
        result.put("chinese_traditional_twn.lang", new Locale("zh", "TW"));
        result.put("croatian.lang", new Locale("hr"));
        result.put("czech.lang", new Locale("cs"));
        result.put("danish.lang", new Locale("da"));
        result.put("dutch.lang", new Locale("nl"));
        result.put("english.lang", Locale.UK);
        result.put("english_us.lang", Locale.US);
        result.put("farsi.lang", new Locale("fa"));
        result.put("finnish.lang", new Locale("fi"));
        result.put("french.lang", Locale.FRENCH);
        result.put("german.lang", Locale.GERMAN);
        result.put("greek.lang", new Locale("el"));
        result.put("hebrew.lang", new Locale("iw"));
        result.put("hungarian.lang", new Locale("hu"));
        result.put("indonesia.lang", new Locale("in"));
        result.put("italian.lang", Locale.ITALIAN);
        result.put("kurdish.lang", new Locale("ku"));
        result.put("malay.lang", new Locale("ms"));
        result.put("norwegian.lang", new Locale("no"));
        result.put("polish.lang", new Locale("pl"));
        result.put("portuguese_br.lang", new Locale("pt", "BR"));
        result.put("portuguese_pt.lang", new Locale("pt", "PT"));
        result.put("romanian.lang", new Locale("ro"));
        result.put("russian.lang", new Locale("ru"));
        result.put("serbian.lang", new Locale("sr"));
        result.put("slovak.lang", new Locale("sk"));
        result.put("slovene.lang", new Locale("sl"));
        result.put("spanish.lang", new Locale("es"));
        result.put("swedish.lang", new Locale("sv"));
        result.put("thai.lang", new Locale("th"));
        result.put("turkish.lang", new Locale("tr"));
        result.put("ukrainian.lang", new Locale("uk"));
        result.put("urdu.lang", new Locale("ur"));
        result.put("vietnamese.lang", new Locale("vi"));
        List<Locale> availableLocales = Arrays.asList(Collator.getAvailableLocales());
        Iterator<Entry<String, Locale>> it = result.entrySet().iterator();
        while (it.hasNext()) {
            if (!availableLocales.contains(((Entry) it.next()).getValue())) {
                it.remove();
            }
        }
        return result;
    }
}
