package com.google.android.gms.tagmanager;

class zzde extends Number implements Comparable<zzde> {
    private double zzaSK;
    private long zzaSL;
    private boolean zzaSM;

    private zzde(double d) {
        this.zzaSK = d;
        this.zzaSM = false;
    }

    private zzde(long j) {
        this.zzaSL = j;
        this.zzaSM = true;
    }

    public static zzde zzT(long j) {
        return new zzde(j);
    }

    public static zzde zza(Double d) {
        return new zzde(d.doubleValue());
    }

    public static zzde zzeX(String str) throws NumberFormatException {
        try {
            return new zzde(Long.parseLong(str));
        } catch (NumberFormatException e) {
            try {
                return new zzde(Double.parseDouble(str));
            } catch (NumberFormatException e2) {
                throw new NumberFormatException(str + " is not a valid TypedNumber");
            }
        }
    }

    public byte byteValue() {
        return (byte) ((int) longValue());
    }

    public /* synthetic */ int compareTo(Object x0) {
        return zza((zzde) x0);
    }

    public double doubleValue() {
        return zzAW() ? (double) this.zzaSL : this.zzaSK;
    }

    public boolean equals(Object other) {
        return (other instanceof zzde) && zza((zzde) other) == 0;
    }

    public float floatValue() {
        return (float) doubleValue();
    }

    public int hashCode() {
        return new Long(longValue()).hashCode();
    }

    public int intValue() {
        return zzAY();
    }

    public long longValue() {
        return zzAX();
    }

    public short shortValue() {
        return zzAZ();
    }

    public String toString() {
        return zzAW() ? Long.toString(this.zzaSL) : Double.toString(this.zzaSK);
    }

    public boolean zzAV() {
        return !zzAW();
    }

    public boolean zzAW() {
        return this.zzaSM;
    }

    public long zzAX() {
        return zzAW() ? this.zzaSL : (long) this.zzaSK;
    }

    public int zzAY() {
        return (int) longValue();
    }

    public short zzAZ() {
        return (short) ((int) longValue());
    }

    public int zza(zzde com_google_android_gms_tagmanager_zzde) {
        return (zzAW() && com_google_android_gms_tagmanager_zzde.zzAW()) ? new Long(this.zzaSL).compareTo(Long.valueOf(com_google_android_gms_tagmanager_zzde.zzaSL)) : Double.compare(doubleValue(), com_google_android_gms_tagmanager_zzde.doubleValue());
    }
}
