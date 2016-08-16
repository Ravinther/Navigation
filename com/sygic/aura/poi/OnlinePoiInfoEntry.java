package com.sygic.aura.poi;

import com.sygic.aura.data.LongPosition;

public class OnlinePoiInfoEntry {
    protected final String mAddress;
    protected final String mAddressLines;
    protected final String mBookingUrl;
    protected final String mCity;
    protected final String mCountry;
    protected final String mCountryCode;
    protected final String mDataSource;
    protected final String mHomepage;
    protected final String mIcon;
    protected final String mId;
    protected final String mLang;
    protected final String mName;
    protected final String mPhotoLink;
    protected final LongPosition mPosition;
    protected final String mPostalCode;
    protected final String mPrice;
    protected final int mPriceLevel;
    protected final String mProviderLink;
    protected final float mRating;
    protected final long mRatingColor;
    protected final int mRatingCount;
    protected final String mRatingLink;
    protected final String mReference;
    protected final String mRegion;
    protected final EItemType mType;

    public enum EItemType {
        None(1),
        Service(2),
        Foursquare(3),
        Yelp(4),
        TripAdvisor(5),
        Viator(6);
        
        final int mValue;

        private EItemType(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return this.mValue;
        }

        public static EItemType fromInt(int value) {
            for (EItemType eType : values()) {
                if (eType.getValue() == value) {
                    return eType;
                }
            }
            return None;
        }
    }

    private OnlinePoiInfoEntry(int type, String name, String addr, String city, String region, String postalCode, String country, String countryCode, String addressLines, String homepage, String providerLink, String ratingLink, String photoLink, String icon, String dataSource, String reference, String lang, String id, long position, float rating, int ratingCount, long ratingColor, String price, int priceLevel, String bookingUrl) {
        this.mType = EItemType.fromInt(type);
        this.mName = name;
        this.mAddress = addr;
        this.mCity = city;
        this.mRegion = region;
        this.mPostalCode = postalCode;
        this.mCountry = country;
        this.mCountryCode = countryCode;
        this.mAddressLines = addressLines;
        this.mHomepage = homepage;
        this.mProviderLink = providerLink;
        this.mRatingLink = ratingLink;
        this.mPhotoLink = photoLink;
        this.mIcon = icon;
        this.mDataSource = dataSource;
        this.mReference = reference;
        this.mLang = lang;
        this.mId = id;
        this.mPosition = new LongPosition(position);
        this.mRating = rating;
        this.mRatingCount = ratingCount;
        this.mRatingColor = ratingColor;
        this.mPrice = price;
        this.mPriceLevel = priceLevel;
        this.mBookingUrl = bookingUrl;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.mId.equals(((OnlinePoiInfoEntry) o).mId);
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public EItemType getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public String getCity() {
        return this.mCity;
    }

    public String getRegion() {
        return this.mRegion;
    }

    public String getPostalCode() {
        return this.mPostalCode;
    }

    public String getCountry() {
        return this.mCountry;
    }

    public String getCountryCode() {
        return this.mCountryCode;
    }

    public String getAddressLines() {
        return this.mAddressLines;
    }

    public String getHomepage() {
        return this.mHomepage;
    }

    public String getProviderLink() {
        return this.mProviderLink;
    }

    public String getRatingLink() {
        return this.mRatingLink;
    }

    public String getPhotoLink() {
        return this.mPhotoLink;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public String getDataSource() {
        return this.mDataSource;
    }

    public String getReference() {
        return this.mReference;
    }

    public String getLang() {
        return this.mLang;
    }

    public String getId() {
        return this.mId;
    }

    public LongPosition getPosition() {
        return this.mPosition;
    }

    public float getRating() {
        return this.mRating;
    }

    public int getRatingCount() {
        return this.mRatingCount;
    }

    public long getRatingColor() {
        return this.mRatingColor;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public int getPriceLevel() {
        return this.mPriceLevel;
    }

    public String getBookingUrl() {
        return this.mBookingUrl;
    }
}
