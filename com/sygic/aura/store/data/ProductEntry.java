package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ProductEntry extends StoreEntry {
    public static final Creator<ProductEntry> CREATOR;
    protected String mDesc;
    protected String mDesc2;
    protected int mDiscount;
    protected long mExpires;
    protected EInstallStatus mInstallStatus;
    private final ELicenseStatus mLicenseStatus;
    protected EInstallStatus mLocalInstallStatus;
    protected EPurchase mPurchase;
    protected boolean mRequired;
    protected String mStrikedPrice;
    protected boolean mUpdate;
    protected String mstrPrice;

    /* renamed from: com.sygic.aura.store.data.ProductEntry.1 */
    static class C17021 implements Creator<ProductEntry> {
        C17021() {
        }

        public ProductEntry createFromParcel(Parcel source) {
            return new ProductEntry(source);
        }

        public ProductEntry[] newArray(int size) {
            return new ProductEntry[size];
        }
    }

    public enum EInstallStatus {
        IsNone,
        IsNotInstalled,
        IsInstalled,
        IsPartiallyInstalled,
        IsUninstalled,
        IsUninstalling,
        IsCorrupted
    }

    public enum ELicenseStatus {
        LsNone,
        LsSubscribed,
        LsActivated,
        LsExpired,
        LsUnsubscribed
    }

    public enum EPurchase {
        PNone,
        PPaid,
        PFree
    }

    public ProductEntry(String id, String title, String iconUrl, String desc, String desc2, String strPrice, String strStrikedPrice, int discount, int purchase, int licenseStatus, long expires, int installStatus, int localInstallStatus, boolean update, boolean required, int index) {
        super(id, title, iconUrl, index);
        this.mDesc = desc;
        this.mDesc2 = desc2;
        this.mstrPrice = strPrice;
        this.mDiscount = discount;
        this.mStrikedPrice = strStrikedPrice;
        this.mPurchase = EPurchase.values()[purchase];
        this.mLicenseStatus = ELicenseStatus.values()[licenseStatus];
        this.mExpires = expires;
        this.mInstallStatus = EInstallStatus.values()[installStatus];
        this.mLocalInstallStatus = EInstallStatus.values()[localInstallStatus];
        this.mUpdate = update;
        this.mRequired = required;
    }

    public EViewType getType() {
        return EViewType.TYPE_PRODUCT;
    }

    public String getDescription() {
        return this.mDesc;
    }

    public long getExpires() {
        return this.mExpires;
    }

    public String getStrikedPrice() {
        return this.mStrikedPrice;
    }

    public int getDiscount() {
        return this.mDiscount;
    }

    public String getPrice() {
        return this.mstrPrice;
    }

    public EPurchase getPurchase() {
        return this.mPurchase;
    }

    public ELicenseStatus getLicenseStatus() {
        return this.mLicenseStatus;
    }

    public EInstallStatus getInstallStatus() {
        return this.mInstallStatus;
    }

    public EInstallStatus getLocalInstallStatus() {
        return this.mLocalInstallStatus;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = (byte) 1;
        int i = -1;
        super.writeToParcel(dest, flags);
        dest.writeString(this.mDesc);
        dest.writeString(this.mDesc2);
        dest.writeString(this.mstrPrice);
        dest.writeString(this.mStrikedPrice);
        dest.writeInt(this.mDiscount);
        dest.writeInt(this.mPurchase == null ? -1 : this.mPurchase.ordinal());
        dest.writeInt(this.mLicenseStatus == null ? -1 : this.mLicenseStatus.ordinal());
        dest.writeLong(this.mExpires);
        dest.writeInt(this.mInstallStatus == null ? -1 : this.mInstallStatus.ordinal());
        if (this.mLocalInstallStatus != null) {
            i = this.mLocalInstallStatus.ordinal();
        }
        dest.writeInt(i);
        if (this.mUpdate) {
            b = (byte) 1;
        } else {
            b = (byte) 0;
        }
        dest.writeByte(b);
        if (!this.mRequired) {
            b2 = (byte) 0;
        }
        dest.writeByte(b2);
    }

    protected ProductEntry(Parcel in) {
        boolean z;
        boolean z2 = true;
        EInstallStatus eInstallStatus = null;
        super(in);
        this.mDesc = in.readString();
        this.mDesc2 = in.readString();
        this.mstrPrice = in.readString();
        this.mStrikedPrice = in.readString();
        this.mDiscount = in.readInt();
        int tmpMPurchase = in.readInt();
        this.mPurchase = tmpMPurchase == -1 ? null : EPurchase.values()[tmpMPurchase];
        int tmpMLicenseStatus = in.readInt();
        this.mLicenseStatus = tmpMLicenseStatus == -1 ? null : ELicenseStatus.values()[tmpMLicenseStatus];
        this.mExpires = in.readLong();
        int tmpMInstallStatus = in.readInt();
        this.mInstallStatus = tmpMInstallStatus == -1 ? null : EInstallStatus.values()[tmpMInstallStatus];
        int tmpMLocalInstallStatus = in.readInt();
        if (tmpMLocalInstallStatus != -1) {
            eInstallStatus = EInstallStatus.values()[tmpMLocalInstallStatus];
        }
        this.mLocalInstallStatus = eInstallStatus;
        if (in.readByte() != null) {
            z = true;
        } else {
            z = false;
        }
        this.mUpdate = z;
        if (in.readByte() == null) {
            z2 = false;
        }
        this.mRequired = z2;
    }

    static {
        CREATOR = new C17021();
    }
}
