package com.sygic.aura.search.model.data;

import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.OnlinePoiInfoEntry.EItemType;
import com.sygic.aura.poi.PoiManager;
import loquendo.tts.engine.TTSConst;

public class OnlinePoiListItem extends PoiListItem {
    static final /* synthetic */ boolean $assertionsDisabled;
    private String mBookingUrl;
    private String mDetailUrl;
    private String mImageUrl;
    private LongPosition mPosition;
    private String mPrice;
    private int mPriceLevel;
    private float mRating;
    private int mRatingColor;
    private EItemType mType;
    private int mVotes;

    public enum OnlinePoiProviders {
        ;
        
        private final int id;

        static {
            $assertionsDisabled = !OnlinePoiListItem.class.desiredAssertionStatus();
            Foursquare = new OnlinePoiProviders("Foursquare", 0, 256);
            Yelp = new OnlinePoiProviders("Yelp", 1, 2048);
            Tripadvisor = new OnlinePoiProviders("Tripadvisor", 2, 4096);
            Viator = new OnlinePoiProviders("Viator", 3, 8192);
            $VALUES = new OnlinePoiProviders[]{Foursquare, Yelp, Tripadvisor, Viator};
        }

        private OnlinePoiProviders(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }

        public static OnlinePoiProviders fromInt(int id) {
            switch (id) {
                case 256:
                    return Foursquare;
                case 2048:
                    return Yelp;
                case 4096:
                    return Tripadvisor;
                case 8192:
                    return Viator;
                default:
                    if ($assertionsDisabled) {
                        CrashlyticsHelper.logError("OnlinePoiListItem", "unknown provider: " + id);
                        return null;
                    }
                    throw new AssertionError();
            }
        }
    }

    static {
        $assertionsDisabled = !OnlinePoiListItem.class.desiredAssertionStatus();
    }

    public static PoiListItem getSpecialItemInstance(String label) {
        return new OnlinePoiListItem(label, -1);
    }

    public OnlinePoiListItem(String strName, int groupId) {
        super(strName, groupId);
    }

    public OnlinePoiListItem(String strName, String strExtName, MapSelection mapSel, long id, int distance, int groupId, int poiIcon, int poiColor, String strIconFile, String formattedDistance, long longPosition, float rating, int votes, int ratingColor, String imageUrl, String detailUrl, String price, int priceLevel, int type, String bookingUrl) {
        super(strName, strExtName, mapSel, id, distance, groupId, poiIcon, poiColor, strIconFile, formattedDistance);
        this.mPosition = new LongPosition(longPosition);
        this.mRating = rating;
        this.mVotes = votes;
        this.mRatingColor = ratingColor;
        this.mImageUrl = imageUrl;
        this.mDetailUrl = detailUrl;
        this.mPrice = price;
        this.mPriceLevel = priceLevel;
        this.mBookingUrl = bookingUrl;
        this.mType = EItemType.fromInt(type);
    }

    public LongPosition getPosition() {
        return this.mPosition;
    }

    public float getRating() {
        return this.mRating;
    }

    public int getVotes() {
        return this.mVotes;
    }

    public int getRatingColor() {
        return this.mRatingColor;
    }

    public String getImageUrl() {
        return this.mImageUrl;
    }

    public EItemType getProviderType() {
        return this.mType;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public int getPriceLevel() {
        return this.mPriceLevel;
    }

    public String getDetailUrl() {
        return this.mDetailUrl;
    }

    public String getBookingUrl() {
        return this.mBookingUrl;
    }

    public MapSelection getMapSel() {
        if (this.mMapSel == null) {
            this.mMapSel = PoiManager.nativeGetOnlineSelectionAt(this.mPosition);
        }
        return super.getMapSel();
    }

    public static OnlinePoiProviders getProvider(int type) {
        switch (type) {
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                return OnlinePoiProviders.Foursquare;
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                return OnlinePoiProviders.Yelp;
            case TTSConst.TTSEVT_ERROR /*16*/:
                return OnlinePoiProviders.Tripadvisor;
            case TTSConst.TTSEVT_JUMP /*17*/:
                return OnlinePoiProviders.Viator;
            default:
                if ($assertionsDisabled) {
                    CrashlyticsHelper.logError("OnlinePoiListItem", "unknown search type: " + type);
                    return null;
                }
                throw new AssertionError();
        }
    }
}
