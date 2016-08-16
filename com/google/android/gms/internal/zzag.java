package com.google.android.gms.internal;

import java.io.IOException;

public interface zzag {

    public static final class zza extends zzrr<zza> {
        private static volatile zza[] zziT;
        public int type;
        public String zziU;
        public zza[] zziV;
        public zza[] zziW;
        public zza[] zziX;
        public String zziY;
        public String zziZ;
        public long zzja;
        public boolean zzjb;
        public zza[] zzjc;
        public int[] zzjd;
        public boolean zzje;

        public zza() {
            zzR();
        }

        public static zza[] zzQ() {
            if (zziT == null) {
                synchronized (zzrv.zzbck) {
                    if (zziT == null) {
                        zziT = new zza[0];
                    }
                }
            }
            return zziT;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzag_zza = (zza) o;
            if (this.type != com_google_android_gms_internal_zzag_zza.type) {
                return false;
            }
            if (this.zziU == null) {
                if (com_google_android_gms_internal_zzag_zza.zziU != null) {
                    return false;
                }
            } else if (!this.zziU.equals(com_google_android_gms_internal_zzag_zza.zziU)) {
                return false;
            }
            if (!zzrv.equals(this.zziV, com_google_android_gms_internal_zzag_zza.zziV) || !zzrv.equals(this.zziW, com_google_android_gms_internal_zzag_zza.zziW) || !zzrv.equals(this.zziX, com_google_android_gms_internal_zzag_zza.zziX)) {
                return false;
            }
            if (this.zziY == null) {
                if (com_google_android_gms_internal_zzag_zza.zziY != null) {
                    return false;
                }
            } else if (!this.zziY.equals(com_google_android_gms_internal_zzag_zza.zziY)) {
                return false;
            }
            if (this.zziZ == null) {
                if (com_google_android_gms_internal_zzag_zza.zziZ != null) {
                    return false;
                }
            } else if (!this.zziZ.equals(com_google_android_gms_internal_zzag_zza.zziZ)) {
                return false;
            }
            return (this.zzja == com_google_android_gms_internal_zzag_zza.zzja && this.zzjb == com_google_android_gms_internal_zzag_zza.zzjb && zzrv.equals(this.zzjc, com_google_android_gms_internal_zzag_zza.zzjc) && zzrv.equals(this.zzjd, com_google_android_gms_internal_zzag_zza.zzjd) && this.zzje == com_google_android_gms_internal_zzag_zza.zzje) ? zza((zzrr) com_google_android_gms_internal_zzag_zza) : false;
        }

        public int hashCode() {
            int i = 1231;
            int i2 = 0;
            int hashCode = ((this.zziY == null ? 0 : this.zziY.hashCode()) + (((((((((this.zziU == null ? 0 : this.zziU.hashCode()) + ((this.type + 527) * 31)) * 31) + zzrv.hashCode(this.zziV)) * 31) + zzrv.hashCode(this.zziW)) * 31) + zzrv.hashCode(this.zziX)) * 31)) * 31;
            if (this.zziZ != null) {
                i2 = this.zziZ.hashCode();
            }
            hashCode = ((((((this.zzjb ? 1231 : 1237) + ((((hashCode + i2) * 31) + ((int) (this.zzja ^ (this.zzja >>> 32)))) * 31)) * 31) + zzrv.hashCode(this.zzjc)) * 31) + zzrv.hashCode(this.zzjd)) * 31;
            if (!this.zzje) {
                i = 1237;
            }
            return ((hashCode + i) * 31) + zzDk();
        }

        protected int zzB() {
            int i;
            int i2 = 0;
            int zzB = super.zzB() + zzrq.zzB(1, this.type);
            if (!this.zziU.equals("")) {
                zzB += zzrq.zzl(2, this.zziU);
            }
            if (this.zziV != null && this.zziV.length > 0) {
                i = zzB;
                for (zzrx com_google_android_gms_internal_zzrx : this.zziV) {
                    if (com_google_android_gms_internal_zzrx != null) {
                        i += zzrq.zzc(3, com_google_android_gms_internal_zzrx);
                    }
                }
                zzB = i;
            }
            if (this.zziW != null && this.zziW.length > 0) {
                i = zzB;
                for (zzrx com_google_android_gms_internal_zzrx2 : this.zziW) {
                    if (com_google_android_gms_internal_zzrx2 != null) {
                        i += zzrq.zzc(4, com_google_android_gms_internal_zzrx2);
                    }
                }
                zzB = i;
            }
            if (this.zziX != null && this.zziX.length > 0) {
                i = zzB;
                for (zzrx com_google_android_gms_internal_zzrx22 : this.zziX) {
                    if (com_google_android_gms_internal_zzrx22 != null) {
                        i += zzrq.zzc(5, com_google_android_gms_internal_zzrx22);
                    }
                }
                zzB = i;
            }
            if (!this.zziY.equals("")) {
                zzB += zzrq.zzl(6, this.zziY);
            }
            if (!this.zziZ.equals("")) {
                zzB += zzrq.zzl(7, this.zziZ);
            }
            if (this.zzja != 0) {
                zzB += zzrq.zzd(8, this.zzja);
            }
            if (this.zzje) {
                zzB += zzrq.zzc(9, this.zzje);
            }
            if (this.zzjd != null && this.zzjd.length > 0) {
                int i3 = 0;
                for (int zzls : this.zzjd) {
                    i3 += zzrq.zzls(zzls);
                }
                zzB = (zzB + i3) + (this.zzjd.length * 1);
            }
            if (this.zzjc != null && this.zzjc.length > 0) {
                while (i2 < this.zzjc.length) {
                    zzrx com_google_android_gms_internal_zzrx3 = this.zzjc[i2];
                    if (com_google_android_gms_internal_zzrx3 != null) {
                        zzB += zzrq.zzc(11, com_google_android_gms_internal_zzrx3);
                    }
                    i2++;
                }
            }
            return this.zzjb ? zzB + zzrq.zzc(12, this.zzjb) : zzB;
        }

        public zza zzR() {
            this.type = 1;
            this.zziU = "";
            this.zziV = zzQ();
            this.zziW = zzQ();
            this.zziX = zzQ();
            this.zziY = "";
            this.zziZ = "";
            this.zzja = 0;
            this.zzjb = false;
            this.zzjc = zzQ();
            this.zzjd = zzsa.zzbcn;
            this.zzje = false;
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            int i = 0;
            com_google_android_gms_internal_zzrq.zzz(1, this.type);
            if (!this.zziU.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(2, this.zziU);
            }
            if (this.zziV != null && this.zziV.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx : this.zziV) {
                    if (com_google_android_gms_internal_zzrx != null) {
                        com_google_android_gms_internal_zzrq.zza(3, com_google_android_gms_internal_zzrx);
                    }
                }
            }
            if (this.zziW != null && this.zziW.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx2 : this.zziW) {
                    if (com_google_android_gms_internal_zzrx2 != null) {
                        com_google_android_gms_internal_zzrq.zza(4, com_google_android_gms_internal_zzrx2);
                    }
                }
            }
            if (this.zziX != null && this.zziX.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx22 : this.zziX) {
                    if (com_google_android_gms_internal_zzrx22 != null) {
                        com_google_android_gms_internal_zzrq.zza(5, com_google_android_gms_internal_zzrx22);
                    }
                }
            }
            if (!this.zziY.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(6, this.zziY);
            }
            if (!this.zziZ.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(7, this.zziZ);
            }
            if (this.zzja != 0) {
                com_google_android_gms_internal_zzrq.zzb(8, this.zzja);
            }
            if (this.zzje) {
                com_google_android_gms_internal_zzrq.zzb(9, this.zzje);
            }
            if (this.zzjd != null && this.zzjd.length > 0) {
                for (int zzz : this.zzjd) {
                    com_google_android_gms_internal_zzrq.zzz(10, zzz);
                }
            }
            if (this.zzjc != null && this.zzjc.length > 0) {
                while (i < this.zzjc.length) {
                    zzrx com_google_android_gms_internal_zzrx3 = this.zzjc[i];
                    if (com_google_android_gms_internal_zzrx3 != null) {
                        com_google_android_gms_internal_zzrq.zza(11, com_google_android_gms_internal_zzrx3);
                    }
                    i++;
                }
            }
            if (this.zzjb) {
                com_google_android_gms_internal_zzrq.zzb(12, this.zzjb);
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }
}
