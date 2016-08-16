package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class ShareOpenGraphAction extends ShareOpenGraphValueContainer<ShareOpenGraphAction, Builder> {
    public static final Creator<ShareOpenGraphAction> CREATOR;

    /* renamed from: com.facebook.share.model.ShareOpenGraphAction.1 */
    static class C03941 implements Creator<ShareOpenGraphAction> {
        C03941() {
        }

        public ShareOpenGraphAction createFromParcel(Parcel in) {
            return new ShareOpenGraphAction(in);
        }

        public ShareOpenGraphAction[] newArray(int size) {
            return new ShareOpenGraphAction[size];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareOpenGraphValueContainer.Builder<ShareOpenGraphAction, Builder> {
        public Builder setActionType(String actionType) {
            putString("og:type", actionType);
            return this;
        }

        public ShareOpenGraphAction build() {
            return new ShareOpenGraphAction();
        }

        public Builder readFrom(ShareOpenGraphAction model) {
            return model == null ? this : ((Builder) super.readFrom(model)).setActionType(model.getActionType());
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((ShareOpenGraphAction) parcel.readParcelable(ShareOpenGraphAction.class.getClassLoader()));
        }
    }

    private ShareOpenGraphAction(Builder builder) {
        super((com.facebook.share.model.ShareOpenGraphValueContainer.Builder) builder);
    }

    ShareOpenGraphAction(Parcel in) {
        super(in);
    }

    public String getActionType() {
        return getString("og:type");
    }

    static {
        CREATOR = new C03941();
    }
}
