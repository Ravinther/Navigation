package com.sygic.aura.poi.nearbypoi.model;

import com.sygic.aura.data.LongPosition;
import com.sygic.aura.poi.PoiManager;
import com.sygic.aura.poi.provider.FoursquarePoiProvider;
import com.sygic.aura.poi.provider.PoiProvider;
import com.sygic.aura.poi.provider.SygicPoiProvider;
import com.sygic.aura.poi.provider.TripAdvisorPoiProvider;
import com.sygic.aura.poi.provider.ViatorPoiProvider;
import com.sygic.aura.poi.provider.YelpPoiProvider;
import com.sygic.aura.search.model.data.OnlinePoiListItem.OnlinePoiProviders;
import java.util.ArrayList;
import loquendo.tts.engine.TTSConst;

public class NearbyPoiGroup {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int GT_EXTENSIONS = -9006;
    private Category[] mCategories;
    private int mCategoryScrollOffset;
    private final String mIconChar;
    private final int mIconColor;
    private final String mName;
    private String mStringCode;
    private final int mType;

    /* renamed from: com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup.1 */
    static /* synthetic */ class C14611 {
        static final /* synthetic */ int[] f1266x22eabc7f;

        static {
            f1266x22eabc7f = new int[OnlinePoiProviders.values().length];
            try {
                f1266x22eabc7f[OnlinePoiProviders.Yelp.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1266x22eabc7f[OnlinePoiProviders.Foursquare.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1266x22eabc7f[OnlinePoiProviders.Tripadvisor.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1266x22eabc7f[OnlinePoiProviders.Viator.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static class Category {
        private final String mName;
        private final String mStringCode;
        private final int mType;

        public Category(String name, int type, String stringCode) {
            this.mName = name;
            this.mType = type;
            this.mStringCode = stringCode;
        }

        public String getName() {
            return this.mName;
        }

        public int getType() {
            return this.mType;
        }

        public String getStringCode() {
            return this.mStringCode;
        }
    }

    static {
        $assertionsDisabled = !NearbyPoiGroup.class.desiredAssertionStatus() ? true : $assertionsDisabled;
    }

    public static PoiProvider[] providersFromGroupType(int type, LongPosition position) {
        ArrayList<PoiProvider> result = new ArrayList();
        result.add(new SygicPoiProvider());
        int[] serviceIds = PoiManager.nativeGetProvidersForGroup(type, position);
        if (serviceIds != null) {
            for (int id : serviceIds) {
                OnlinePoiProviders provider = OnlinePoiProviders.fromInt(id);
                if (provider != null) {
                    switch (C14611.f1266x22eabc7f[provider.ordinal()]) {
                        case TTSConst.TTSMULTILINE /*1*/:
                            result.add(new YelpPoiProvider());
                            break;
                        case TTSConst.TTSPARAGRAPH /*2*/:
                            result.add(new FoursquarePoiProvider());
                            break;
                        case TTSConst.TTSUNICODE /*3*/:
                            result.add(new TripAdvisorPoiProvider());
                            break;
                        case TTSConst.TTSXML /*4*/:
                            result.add(new ViatorPoiProvider());
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return (PoiProvider[]) result.toArray(new PoiProvider[result.size()]);
    }

    public static Class<? extends PoiProvider> getDefaultProviderFromGroupType(int type, LongPosition position) {
        int defaultProvider = PoiManager.nativeGetDefaultProviderForGroup(type, position);
        if (defaultProvider == 0) {
            return SygicPoiProvider.class;
        }
        OnlinePoiProviders provider = OnlinePoiProviders.fromInt(defaultProvider);
        if (provider == null) {
            return SygicPoiProvider.class;
        }
        switch (C14611.f1266x22eabc7f[provider.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return YelpPoiProvider.class;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return FoursquarePoiProvider.class;
            case TTSConst.TTSUNICODE /*3*/:
                return TripAdvisorPoiProvider.class;
            case TTSConst.TTSXML /*4*/:
                return ViatorPoiProvider.class;
            default:
                if ($assertionsDisabled) {
                    return null;
                }
                throw new AssertionError();
        }
    }

    public NearbyPoiGroup(String name, int iconChar, int iconColor, int type, String stringCode) {
        this.mName = name;
        this.mIconChar = Character.toString((char) iconChar);
        this.mIconColor = iconColor;
        this.mType = type;
        this.mStringCode = stringCode;
    }

    private NearbyPoiGroup(String name) {
        this.mName = name;
        this.mIconChar = "\ue3c5";
        this.mIconColor = 0;
        this.mType = 305;
        this.mStringCode = "test";
    }

    public String getName() {
        return this.mName;
    }

    public String getIconChar() {
        return this.mIconChar;
    }

    public int getIconColor() {
        return this.mIconColor;
    }

    public Category[] getCategories() {
        return this.mCategories;
    }

    public void setCategories(Category[] categories) {
        this.mCategories = categories;
    }

    public int getType() {
        return this.mType;
    }

    public String getStringCode() {
        return this.mStringCode;
    }

    public void setCategoryScrollOffset(int categoryScrollOffset) {
        this.mCategoryScrollOffset = categoryScrollOffset;
    }

    public int getCategoryScrollOffset() {
        return this.mCategoryScrollOffset;
    }

    public static NearbyPoiGroup getDummyItem(String name) {
        return new NearbyPoiGroup(name);
    }
}
