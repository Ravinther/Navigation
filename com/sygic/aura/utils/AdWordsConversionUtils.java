package com.sygic.aura.utils;

import android.content.Context;
import com.google.ads.conversiontracking.AdWordsConversionReporter;

public class AdWordsConversionUtils {
    public static void reportConversion(Context context, String price) {
        AdWordsConversionReporter.reportWithConversionId(context.getApplicationContext(), "968582660", "eFAQCKfMzlsQhMztzQM", price, true);
    }
}
