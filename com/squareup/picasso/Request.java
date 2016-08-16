package com.squareup.picasso;

import android.graphics.Bitmap.Config;
import android.net.Uri;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Request {
    private static final long TOO_LONG_LOG;
    public final boolean centerCrop;
    public final boolean centerInside;
    public final Config config;
    public final boolean hasRotationPivot;
    int id;
    public final int resourceId;
    public final float rotationDegrees;
    public final float rotationPivotX;
    public final float rotationPivotY;
    long started;
    public final int targetHeight;
    public final int targetWidth;
    public final List<Transformation> transformations;
    public final Uri uri;

    public static final class Builder {
        private boolean centerCrop;
        private boolean centerInside;
        private Config config;
        private boolean hasRotationPivot;
        private int resourceId;
        private float rotationDegrees;
        private float rotationPivotX;
        private float rotationPivotY;
        private int targetHeight;
        private int targetWidth;
        private List<Transformation> transformations;
        private Uri uri;

        Builder(Uri uri, int resourceId) {
            this.uri = uri;
            this.resourceId = resourceId;
        }

        boolean hasImage() {
            return (this.uri == null && this.resourceId == 0) ? false : true;
        }

        boolean hasSize() {
            return this.targetWidth != 0;
        }

        public Builder resize(int targetWidth, int targetHeight) {
            if (targetWidth <= 0) {
                throw new IllegalArgumentException("Width must be positive number.");
            } else if (targetHeight <= 0) {
                throw new IllegalArgumentException("Height must be positive number.");
            } else {
                this.targetWidth = targetWidth;
                this.targetHeight = targetHeight;
                return this;
            }
        }

        public Builder centerCrop() {
            if (this.centerInside) {
                throw new IllegalStateException("Center crop can not be used after calling centerInside");
            }
            this.centerCrop = true;
            return this;
        }

        public Builder transform(Transformation transformation) {
            if (transformation == null) {
                throw new IllegalArgumentException("Transformation must not be null.");
            }
            if (this.transformations == null) {
                this.transformations = new ArrayList(2);
            }
            this.transformations.add(transformation);
            return this;
        }

        public Request build() {
            if (this.centerInside && this.centerCrop) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            } else if (this.centerCrop && this.targetWidth == 0) {
                throw new IllegalStateException("Center crop requires calling resize.");
            } else if (!this.centerInside || this.targetWidth != 0) {
                return new Request(this.resourceId, this.transformations, this.targetWidth, this.targetHeight, this.centerCrop, this.centerInside, this.rotationDegrees, this.rotationPivotX, this.rotationPivotY, this.hasRotationPivot, this.config, null);
            } else {
                throw new IllegalStateException("Center inside requires calling resize.");
            }
        }
    }

    static {
        TOO_LONG_LOG = TimeUnit.SECONDS.toNanos(5);
    }

    private Request(Uri uri, int resourceId, List<Transformation> transformations, int targetWidth, int targetHeight, boolean centerCrop, boolean centerInside, float rotationDegrees, float rotationPivotX, float rotationPivotY, boolean hasRotationPivot, Config config) {
        this.uri = uri;
        this.resourceId = resourceId;
        if (transformations == null) {
            this.transformations = null;
        } else {
            this.transformations = Collections.unmodifiableList(transformations);
        }
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        this.centerCrop = centerCrop;
        this.centerInside = centerInside;
        this.rotationDegrees = rotationDegrees;
        this.rotationPivotX = rotationPivotX;
        this.rotationPivotY = rotationPivotY;
        this.hasRotationPivot = hasRotationPivot;
        this.config = config;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Request{");
        if (this.resourceId > 0) {
            sb.append(this.resourceId);
        } else {
            sb.append(this.uri);
        }
        if (!(this.transformations == null || this.transformations.isEmpty())) {
            for (Transformation transformation : this.transformations) {
                sb.append(' ').append(transformation.key());
            }
        }
        if (this.targetWidth > 0) {
            sb.append(" resize(").append(this.targetWidth).append(',').append(this.targetHeight).append(')');
        }
        if (this.centerCrop) {
            sb.append(" centerCrop");
        }
        if (this.centerInside) {
            sb.append(" centerInside");
        }
        if (this.rotationDegrees != 0.0f) {
            sb.append(" rotation(").append(this.rotationDegrees);
            if (this.hasRotationPivot) {
                sb.append(" @ ").append(this.rotationPivotX).append(',').append(this.rotationPivotY);
            }
            sb.append(')');
        }
        if (this.config != null) {
            sb.append(' ').append(this.config);
        }
        sb.append('}');
        return sb.toString();
    }

    String logId() {
        long delta = System.nanoTime() - this.started;
        if (delta > TOO_LONG_LOG) {
            return plainId() + '+' + TimeUnit.NANOSECONDS.toSeconds(delta) + 's';
        }
        return plainId() + '+' + TimeUnit.NANOSECONDS.toMillis(delta) + "ms";
    }

    String plainId() {
        return "[R" + this.id + ']';
    }

    String getName() {
        if (this.uri != null) {
            return String.valueOf(this.uri.getPath());
        }
        return Integer.toHexString(this.resourceId);
    }

    public boolean hasSize() {
        return this.targetWidth != 0;
    }

    boolean needsTransformation() {
        return needsMatrixTransform() || hasCustomTransformations();
    }

    boolean needsMatrixTransform() {
        return (this.targetWidth == 0 && this.rotationDegrees == 0.0f) ? false : true;
    }

    boolean hasCustomTransformations() {
        return this.transformations != null;
    }
}
