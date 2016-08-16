package com.sygic.aura.route.data;

public class WarningItem {
    public static final int SUBTYPE_SHARP_CURVE_LEFT = 1;
    public static final int SUBTYPE_SHARP_CURVE_RIGHT = 2;
    private int mDistance;
    private boolean mIsInUSA;
    private int mRecommendedSpeed;
    private boolean mShouldShowDistance;
    private int mSubType;
    private Type mType;

    public enum Type {
        RAILWAY,
        SPEEDCAM,
        SHARP_CURVE
    }

    public WarningItem(int type, int subType, int distance, int recommendedSpeed, boolean isInUSA, boolean shouldShowDistance) {
        this.mType = Type.values()[type];
        this.mSubType = subType;
        this.mDistance = distance;
        this.mRecommendedSpeed = recommendedSpeed;
        this.mIsInUSA = isInUSA;
        this.mShouldShowDistance = shouldShowDistance;
    }

    public Type getType() {
        return this.mType;
    }

    public int getSubType() {
        return this.mSubType;
    }

    public int getDistance() {
        return this.mDistance;
    }

    public int getRecommendedSpeed() {
        return this.mRecommendedSpeed;
    }

    public boolean isInUSA() {
        return this.mIsInUSA;
    }

    public boolean shouldShowDistance() {
        return this.mShouldShowDistance;
    }

    public int getIconResId() {
        return 2131166163;
    }

    public int hashCode() {
        return (((((((((this.mIsInUSA ? 1231 : 1237) + 31) * 31) + this.mRecommendedSpeed) * 31) + this.mDistance) * 31) + this.mSubType) * 31) + this.mType.ordinal();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WarningItem other = (WarningItem) obj;
        if (this.mIsInUSA == other.mIsInUSA && this.mRecommendedSpeed == other.mRecommendedSpeed && this.mDistance == other.mDistance && this.mSubType == other.mSubType && this.mType == other.mType) {
            return true;
        }
        return false;
    }
}
