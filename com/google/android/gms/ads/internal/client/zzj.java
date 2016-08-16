package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.google.android.gms.C0568R;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.internal.zzgk;

@zzgk
public final class zzj {
    private final String zzoZ;
    private final AdSize[] zzsL;

    public zzj(Context context, AttributeSet attributeSet) {
        Object obj = 1;
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, C0568R.styleable.AdsAttrs);
        Object string = obtainAttributes.getString(C0568R.styleable.AdsAttrs_adSize);
        Object string2 = obtainAttributes.getString(C0568R.styleable.AdsAttrs_adSizes);
        Object obj2 = !TextUtils.isEmpty(string) ? 1 : null;
        if (TextUtils.isEmpty(string2)) {
            obj = null;
        }
        if (obj2 != null && r1 == null) {
            this.zzsL = zzE(string);
        } else if (obj2 == null && r1 != null) {
            this.zzsL = zzE(string2);
        } else if (obj2 == null || r1 == null) {
            throw new IllegalArgumentException("Required XML attribute \"adSize\" was missing.");
        } else {
            throw new IllegalArgumentException("Either XML attribute \"adSize\" or XML attribute \"supportedAdSizes\" should be specified, but not both.");
        }
        this.zzoZ = obtainAttributes.getString(C0568R.styleable.AdsAttrs_adUnitId);
        if (TextUtils.isEmpty(this.zzoZ)) {
            throw new IllegalArgumentException("Required XML attribute \"adUnitId\" was missing.");
        }
    }

    private static AdSize[] zzE(String str) {
        String[] split = str.split("\\s*,\\s*");
        AdSize[] adSizeArr = new AdSize[split.length];
        for (int i = 0; i < split.length; i++) {
            String trim = split[i].trim();
            if (trim.matches("^(\\d+|FULL_WIDTH)\\s*[xX]\\s*(\\d+|AUTO_HEIGHT)$")) {
                String[] split2 = trim.split("[xX]");
                split2[0] = split2[0].trim();
                split2[1] = split2[1].trim();
                try {
                    adSizeArr[i] = new AdSize("FULL_WIDTH".equals(split2[0]) ? -1 : Integer.parseInt(split2[0]), "AUTO_HEIGHT".equals(split2[1]) ? -2 : Integer.parseInt(split2[1]));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + trim);
                }
            } else if ("BANNER".equals(trim)) {
                adSizeArr[i] = AdSize.BANNER;
            } else if ("LARGE_BANNER".equals(trim)) {
                adSizeArr[i] = AdSize.LARGE_BANNER;
            } else if ("FULL_BANNER".equals(trim)) {
                adSizeArr[i] = AdSize.FULL_BANNER;
            } else if ("LEADERBOARD".equals(trim)) {
                adSizeArr[i] = AdSize.LEADERBOARD;
            } else if ("MEDIUM_RECTANGLE".equals(trim)) {
                adSizeArr[i] = AdSize.MEDIUM_RECTANGLE;
            } else if ("SMART_BANNER".equals(trim)) {
                adSizeArr[i] = AdSize.SMART_BANNER;
            } else if ("WIDE_SKYSCRAPER".equals(trim)) {
                adSizeArr[i] = AdSize.WIDE_SKYSCRAPER;
            } else {
                throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + trim);
            }
        }
        if (adSizeArr.length != 0) {
            return adSizeArr;
        }
        throw new IllegalArgumentException("Could not parse XML attribute \"adSize\": " + str);
    }

    public String getAdUnitId() {
        return this.zzoZ;
    }

    public AdSize[] zzi(boolean z) {
        if (z || this.zzsL.length == 1) {
            return this.zzsL;
        }
        throw new IllegalArgumentException("The adSizes XML attribute is only allowed on PublisherAdViews.");
    }
}
