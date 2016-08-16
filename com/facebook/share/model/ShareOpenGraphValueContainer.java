package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import java.util.Set;

public abstract class ShareOpenGraphValueContainer<P extends ShareOpenGraphValueContainer, E extends Builder> implements ShareModel {
    private final Bundle bundle;

    public static abstract class Builder<P extends ShareOpenGraphValueContainer, E extends Builder> {
        private Bundle bundle;

        public Builder() {
            this.bundle = new Bundle();
        }

        public E putString(String key, String value) {
            this.bundle.putString(key, value);
            return this;
        }

        public E readFrom(P model) {
            if (model != null) {
                this.bundle.putAll(model.getBundle());
            }
            return this;
        }
    }

    protected ShareOpenGraphValueContainer(Builder<P, E> builder) {
        this.bundle = (Bundle) builder.bundle.clone();
    }

    ShareOpenGraphValueContainer(Parcel in) {
        this.bundle = in.readBundle(Builder.class.getClassLoader());
    }

    public Object get(String key) {
        return this.bundle.get(key);
    }

    public String getString(String key) {
        return this.bundle.getString(key);
    }

    public Bundle getBundle() {
        return (Bundle) this.bundle.clone();
    }

    public Set<String> keySet() {
        return this.bundle.keySet();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeBundle(this.bundle);
    }
}
