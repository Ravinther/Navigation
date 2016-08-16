package com.nineoldandroids.animation;

import android.view.animation.Interpolator;

public abstract class Keyframe implements Cloneable {
    float mFraction;
    boolean mHasValue;
    private Interpolator mInterpolator;
    Class mValueType;

    static class FloatKeyframe extends Keyframe {
        float mValue;

        FloatKeyframe(float fraction, float value) {
            this.mFraction = fraction;
            this.mValue = value;
            this.mValueType = Float.TYPE;
            this.mHasValue = true;
        }

        FloatKeyframe(float fraction) {
            this.mFraction = fraction;
            this.mValueType = Float.TYPE;
        }

        public float getFloatValue() {
            return this.mValue;
        }

        public Object getValue() {
            return Float.valueOf(this.mValue);
        }

        public void setValue(Object value) {
            if (value != null && value.getClass() == Float.class) {
                this.mValue = ((Float) value).floatValue();
                this.mHasValue = true;
            }
        }

        public FloatKeyframe clone() {
            FloatKeyframe kfClone = new FloatKeyframe(getFraction(), this.mValue);
            kfClone.setInterpolator(getInterpolator());
            return kfClone;
        }
    }

    public abstract Keyframe clone();

    public abstract Object getValue();

    public abstract void setValue(Object obj);

    public Keyframe() {
        this.mInterpolator = null;
        this.mHasValue = false;
    }

    public static Keyframe ofFloat(float fraction, float value) {
        return new FloatKeyframe(fraction, value);
    }

    public static Keyframe ofFloat(float fraction) {
        return new FloatKeyframe(fraction);
    }

    public boolean hasValue() {
        return this.mHasValue;
    }

    public float getFraction() {
        return this.mFraction;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }
}
