package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import java.util.Locale;

public class ImageRequest {
    private boolean allowCachedRedirects;
    private Callback callback;
    private Object callerTag;
    private Context context;
    private Uri imageUri;

    public static class Builder {
        private boolean allowCachedRedirects;
        private Callback callback;
        private Object callerTag;
        private Context context;
        private Uri imageUrl;

        public Builder(Context context, Uri imageUri) {
            Validate.notNull(imageUri, "imageUri");
            this.context = context;
            this.imageUrl = imageUri;
        }

        public Builder setCallback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Builder setCallerTag(Object callerTag) {
            this.callerTag = callerTag;
            return this;
        }

        public Builder setAllowCachedRedirects(boolean allowCachedRedirects) {
            this.allowCachedRedirects = allowCachedRedirects;
            return this;
        }

        public ImageRequest build() {
            return new ImageRequest();
        }
    }

    public interface Callback {
        void onCompleted(ImageResponse imageResponse);
    }

    public static Uri getProfilePictureUri(String userId, int width, int height) {
        Validate.notNullOrEmpty(userId, "userId");
        width = Math.max(width, 0);
        height = Math.max(height, 0);
        if (width == 0 && height == 0) {
            throw new IllegalArgumentException("Either width or height must be greater than 0");
        }
        android.net.Uri.Builder builder = new android.net.Uri.Builder().scheme("https").authority("graph.facebook.com").path(String.format(Locale.US, "%s/picture", new Object[]{userId}));
        if (height != 0) {
            builder.appendQueryParameter("height", String.valueOf(height));
        }
        if (width != 0) {
            builder.appendQueryParameter("width", String.valueOf(width));
        }
        builder.appendQueryParameter("migration_overrides", "{october_2012:true}");
        return builder.build();
    }

    private ImageRequest(Builder builder) {
        this.context = builder.context;
        this.imageUri = builder.imageUrl;
        this.callback = builder.callback;
        this.allowCachedRedirects = builder.allowCachedRedirects;
        this.callerTag = builder.callerTag == null ? new Object() : builder.callerTag;
    }

    public Context getContext() {
        return this.context;
    }

    public Uri getImageUri() {
        return this.imageUri;
    }

    public Callback getCallback() {
        return this.callback;
    }

    public boolean isCachedRedirectAllowed() {
        return this.allowCachedRedirects;
    }

    public Object getCallerTag() {
        return this.callerTag;
    }
}
