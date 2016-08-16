package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzw;
import java.util.ArrayList;
import java.util.List;

public class zzhx {
    private final String[] zzHU;
    private final double[] zzHV;
    private final double[] zzHW;
    private final int[] zzHX;
    private int zzHY;

    public static class zza {
        public final int count;
        public final String name;
        public final double zzHZ;
        public final double zzIa;
        public final double zzIb;

        public zza(String str, double d, double d2, double d3, int i) {
            this.name = str;
            this.zzIa = d;
            this.zzHZ = d2;
            this.zzIb = d3;
            this.count = i;
        }

        public boolean equals(Object other) {
            if (!(other instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzhx_zza = (zza) other;
            return zzw.equal(this.name, com_google_android_gms_internal_zzhx_zza.name) && this.zzHZ == com_google_android_gms_internal_zzhx_zza.zzHZ && this.zzIa == com_google_android_gms_internal_zzhx_zza.zzIa && this.count == com_google_android_gms_internal_zzhx_zza.count && Double.compare(this.zzIb, com_google_android_gms_internal_zzhx_zza.zzIb) == 0;
        }

        public int hashCode() {
            return zzw.hashCode(this.name, Double.valueOf(this.zzHZ), Double.valueOf(this.zzIa), Double.valueOf(this.zzIb), Integer.valueOf(this.count));
        }

        public String toString() {
            return zzw.zzu(this).zzg("name", this.name).zzg("minBound", Double.valueOf(this.zzIa)).zzg("maxBound", Double.valueOf(this.zzHZ)).zzg("percent", Double.valueOf(this.zzIb)).zzg("count", Integer.valueOf(this.count)).toString();
        }
    }

    public static class zzb {
        private final List<String> zzIc;
        private final List<Double> zzId;
        private final List<Double> zzIe;

        public zzb() {
            this.zzIc = new ArrayList();
            this.zzId = new ArrayList();
            this.zzIe = new ArrayList();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.gms.internal.zzhx.zzb zza(java.lang.String r7, double r8, double r10) {
            /*
            r6 = this;
            r0 = 0;
            r1 = r0;
        L_0x0002:
            r0 = r6.zzIc;
            r0 = r0.size();
            if (r1 >= r0) goto L_0x0026;
        L_0x000a:
            r0 = r6.zzIe;
            r0 = r0.get(r1);
            r0 = (java.lang.Double) r0;
            r2 = r0.doubleValue();
            r0 = r6.zzId;
            r0 = r0.get(r1);
            r0 = (java.lang.Double) r0;
            r4 = r0.doubleValue();
            r0 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
            if (r0 >= 0) goto L_0x003e;
        L_0x0026:
            r0 = r6.zzIc;
            r0.add(r1, r7);
            r0 = r6.zzIe;
            r2 = java.lang.Double.valueOf(r8);
            r0.add(r1, r2);
            r0 = r6.zzId;
            r2 = java.lang.Double.valueOf(r10);
            r0.add(r1, r2);
            return r6;
        L_0x003e:
            r0 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
            if (r0 != 0) goto L_0x0046;
        L_0x0042:
            r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
            if (r0 < 0) goto L_0x0026;
        L_0x0046:
            r0 = r1 + 1;
            r1 = r0;
            goto L_0x0002;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzhx.zzb.zza(java.lang.String, double, double):com.google.android.gms.internal.zzhx$zzb");
        }

        public zzhx zzgz() {
            return new zzhx();
        }
    }

    private zzhx(zzb com_google_android_gms_internal_zzhx_zzb) {
        int size = com_google_android_gms_internal_zzhx_zzb.zzId.size();
        this.zzHU = (String[]) com_google_android_gms_internal_zzhx_zzb.zzIc.toArray(new String[size]);
        this.zzHV = zzc(com_google_android_gms_internal_zzhx_zzb.zzId);
        this.zzHW = zzc(com_google_android_gms_internal_zzhx_zzb.zzIe);
        this.zzHX = new int[size];
        this.zzHY = 0;
    }

    private double[] zzc(List<Double> list) {
        double[] dArr = new double[list.size()];
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = ((Double) list.get(i)).doubleValue();
        }
        return dArr;
    }

    public List<zza> getBuckets() {
        List<zza> arrayList = new ArrayList(this.zzHU.length);
        for (int i = 0; i < this.zzHU.length; i++) {
            arrayList.add(new zza(this.zzHU[i], this.zzHW[i], this.zzHV[i], ((double) this.zzHX[i]) / ((double) this.zzHY), this.zzHX[i]));
        }
        return arrayList;
    }

    public void zza(double d) {
        this.zzHY++;
        int i = 0;
        while (i < this.zzHW.length) {
            if (this.zzHW[i] <= d && d < this.zzHV[i]) {
                int[] iArr = this.zzHX;
                iArr[i] = iArr[i] + 1;
            }
            if (d >= this.zzHW[i]) {
                i++;
            } else {
                return;
            }
        }
    }
}
