package com.sygic.aura.poi.detail.badge;

import loquendo.tts.engine.TTSConst;

public class PoiBadgeHelper {

    /* renamed from: com.sygic.aura.poi.detail.badge.PoiBadgeHelper.1 */
    static /* synthetic */ class C14531 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type;

        static {
            $SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type = new int[Type.values().length];
            try {
                $SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type[Type.FOURSQUARE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type[Type.YELP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type[Type.EXTENSIONS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type[Type.DEFAULT_STARS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type[Type.DEFAULT_BITMAP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type[Type.DEFAULT_TEXT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public enum Type {
        FOURSQUARE,
        YELP,
        EXTENSIONS,
        DEFAULT_BITMAP,
        DEFAULT_TEXT,
        DEFAULT_STARS
    }

    private PoiBadgeHelper() {
    }

    public static PoiBadge create(Type type, String title, String url, int rating, int ratingMax, String bitmapPath) {
        switch (C14531.$SwitchMap$com$sygic$aura$poi$detail$badge$PoiBadgeHelper$Type[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return new FoursquareBadge(title, url, rating);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return new YelpBadge(title, url, rating);
            case TTSConst.TTSUNICODE /*3*/:
                return new ExtensionsBadge(title, url);
            case TTSConst.TTSXML /*4*/:
                return new StarsPoiBadge(title, url, rating, ratingMax, 2131034225, 2131034227);
            case TTSConst.TTSEVT_TEXT /*5*/:
                return new BitmapPoiBadge(title, url, rating, ratingMax, bitmapPath);
            default:
                return new TextPoiBadge(title, url, rating, ratingMax, 2131034225);
        }
    }
}
