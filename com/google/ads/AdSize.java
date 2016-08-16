package com.google.ads;

@Deprecated
public final class AdSize {
    public static final AdSize BANNER;
    public static final AdSize IAB_BANNER;
    public static final AdSize IAB_LEADERBOARD;
    public static final AdSize IAB_MRECT;
    public static final AdSize IAB_WIDE_SKYSCRAPER;
    public static final AdSize SMART_BANNER;
    private final com.google.android.gms.ads.AdSize zzaJ;

    static {
        SMART_BANNER = new AdSize(-1, -2, "mb");
        BANNER = new AdSize(320, 50, "mb");
        IAB_MRECT = new AdSize(300, 250, "as");
        IAB_BANNER = new AdSize(468, 60, "as");
        IAB_LEADERBOARD = new AdSize(728, 90, "as");
        IAB_WIDE_SKYSCRAPER = new AdSize(160, 600, "as");
    }

    private AdSize(int width, int height, String type) {
        this(new com.google.android.gms.ads.AdSize(width, height));
    }

    public AdSize(com.google.android.gms.ads.AdSize adSize) {
        this.zzaJ = adSize;
    }

    public boolean equals(Object other) {
        if (!(other instanceof AdSize)) {
            return false;
        }
        return this.zzaJ.equals(((AdSize) other).zzaJ);
    }

    public int getHeight() {
        return this.zzaJ.getHeight();
    }

    public int getWidth() {
        return this.zzaJ.getWidth();
    }

    public int hashCode() {
        return this.zzaJ.hashCode();
    }

    public String toString() {
        return this.zzaJ.toString();
    }
}
