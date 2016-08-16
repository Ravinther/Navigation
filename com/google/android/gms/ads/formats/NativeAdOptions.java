package com.google.android.gms.ads.formats;

public final class NativeAdOptions {
    private final boolean zznV;
    private final int zznW;
    private final boolean zznX;

    public static final class Builder {
        private boolean zznV;
        private int zznW;
        private boolean zznX;

        public Builder() {
            this.zznV = false;
            this.zznW = 0;
            this.zznX = false;
        }

        public NativeAdOptions build() {
            return new NativeAdOptions();
        }

        public Builder setImageOrientation(int orientation) {
            this.zznW = orientation;
            return this;
        }

        public Builder setRequestMultipleImages(boolean shouldRequestMultipleImages) {
            this.zznX = shouldRequestMultipleImages;
            return this;
        }

        public Builder setReturnUrlsForImageAssets(boolean shouldReturnUrls) {
            this.zznV = shouldReturnUrls;
            return this;
        }
    }

    private NativeAdOptions(Builder builder) {
        this.zznV = builder.zznV;
        this.zznW = builder.zznW;
        this.zznX = builder.zznX;
    }

    public int getImageOrientation() {
        return this.zznW;
    }

    public boolean shouldRequestMultipleImages() {
        return this.zznX;
    }

    public boolean shouldReturnUrlsForImageAssets() {
        return this.zznV;
    }
}
