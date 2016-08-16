package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.resources.ResourceManager;

public class ProductDetailEntry extends ProductEntry {
    public static final Creator<ProductDetailEntry> CREATOR;
    private final String[] mAttributes;
    private final boolean mHasFiles;
    private final boolean mInstallable;
    private final String[] mPaymentMethods;
    private final String[] mPreviews;
    private final String mPublisher;
    private final ELoginRequirement mRequiresLogin;
    private final String[] mScreenshots;
    private final boolean mShowList;
    private final long mSize;
    private final String mStoreId;
    private final String mText;

    /* renamed from: com.sygic.aura.store.data.ProductDetailEntry.1 */
    static class C17011 implements Creator<ProductDetailEntry> {
        C17011() {
        }

        public ProductDetailEntry createFromParcel(Parcel source) {
            return new ProductDetailEntry(null);
        }

        public ProductDetailEntry[] newArray(int size) {
            return new ProductDetailEntry[size];
        }
    }

    public enum ActionButtonType {
        NONE,
        BUY,
        INSTALL,
        INSTALLING,
        INSTALLED,
        UNINSTALL,
        UPDATE,
        UNSUBSCRIBE,
        MANAGE,
        PRICE_CLICK
    }

    public enum ELoginRequirement {
        LoginNotRequired,
        LoginRecommended,
        LoginRequired
    }

    public ProductDetailEntry(String id, String title, String iconUrl, String storeId, String text, String desc, String desc2, String strPrice, String strStrikedPrice, int discount, int purchase, int licenseStatus, long expires, int installStatus, int localInstallStatus, boolean update, boolean required, boolean showList, long size, int requiresLogin, boolean installable, String publisher, String[] screenshots, String[] previews, String[] attributes, boolean hasFiles, String[] paymentMethods) {
        super(id, title, iconUrl, desc, desc2, strPrice, strStrikedPrice, discount, purchase, licenseStatus, expires, installStatus, localInstallStatus, update, required, 0);
        this.mStoreId = storeId;
        this.mText = text;
        this.mSize = size;
        this.mShowList = showList;
        this.mRequiresLogin = ELoginRequirement.values()[requiresLogin];
        this.mInstallable = installable;
        this.mPublisher = publisher;
        this.mPreviews = previews;
        this.mExpires = expires;
        this.mAttributes = attributes;
        this.mHasFiles = hasFiles;
        this.mPaymentMethods = paymentMethods;
        if (screenshots == null) {
            this.mScreenshots = screenshots;
            return;
        }
        this.mScreenshots = new String[screenshots.length];
        for (int i = 0; i < screenshots.length; i++) {
            this.mScreenshots[i] = ResourceManager.sanitizeUrl(screenshots[i]);
        }
    }

    public String getStoreId() {
        return this.mStoreId;
    }

    public String getText() {
        return this.mText;
    }

    public long getExpires() {
        return this.mExpires;
    }

    public boolean isInstallable() {
        return this.mInstallable;
    }

    public boolean showList() {
        return this.mShowList;
    }

    public String[] getScreenshots() {
        return this.mScreenshots;
    }

    public String[] getPreviews() {
        return this.mPreviews;
    }

    public String[] getAttributes() {
        return this.mAttributes;
    }

    public boolean hasFiles() {
        return this.mHasFiles;
    }

    public String[] getPaymentMethods() {
        return this.mPaymentMethods;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = (byte) 1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.mStoreId);
        dest.writeString(this.mText);
        dest.writeLong(this.mSize);
        dest.writeByte(this.mShowList ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mRequiresLogin == null ? -1 : this.mRequiresLogin.ordinal());
        if (this.mInstallable) {
            b = (byte) 1;
        } else {
            b = (byte) 0;
        }
        dest.writeByte(b);
        if (!this.mHasFiles) {
            b2 = (byte) 0;
        }
        dest.writeByte(b2);
        dest.writeString(this.mPublisher);
        dest.writeStringArray(this.mScreenshots);
        dest.writeStringArray(this.mPreviews);
        dest.writeStringArray(this.mAttributes);
        dest.writeStringArray(this.mPaymentMethods);
    }

    private ProductDetailEntry(Parcel in) {
        boolean z;
        boolean z2 = true;
        super(in);
        this.mStoreId = in.readString();
        this.mText = in.readString();
        this.mSize = in.readLong();
        if (in.readByte() != null) {
            z = true;
        } else {
            z = false;
        }
        this.mShowList = z;
        int tmpMRequiresLogin = in.readInt();
        this.mRequiresLogin = tmpMRequiresLogin == -1 ? null : ELoginRequirement.values()[tmpMRequiresLogin];
        if (in.readByte() != null) {
            z = true;
        } else {
            z = false;
        }
        this.mInstallable = z;
        if (in.readByte() == null) {
            z2 = false;
        }
        this.mHasFiles = z2;
        this.mPublisher = in.readString();
        this.mScreenshots = in.createStringArray();
        this.mPreviews = in.createStringArray();
        this.mAttributes = in.createStringArray();
        this.mPaymentMethods = in.createStringArray();
    }

    static {
        CREATOR = new C17011();
    }
}
