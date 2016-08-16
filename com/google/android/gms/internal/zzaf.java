package com.google.android.gms.internal;

import java.io.IOException;

public interface zzaf {

    public static final class zza extends zzrr<zza> {
        public int level;
        public int zzhN;
        public int zzhO;

        public zza() {
            zzA();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzaf_zza = (zza) o;
            return (this.level == com_google_android_gms_internal_zzaf_zza.level && this.zzhN == com_google_android_gms_internal_zzaf_zza.zzhN && this.zzhO == com_google_android_gms_internal_zzaf_zza.zzhO) ? zza((zzrr) com_google_android_gms_internal_zzaf_zza) : false;
        }

        public int hashCode() {
            return ((((((this.level + 527) * 31) + this.zzhN) * 31) + this.zzhO) * 31) + zzDk();
        }

        public zza zzA() {
            this.level = 1;
            this.zzhN = 0;
            this.zzhO = 0;
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        protected int zzB() {
            int zzB = super.zzB();
            if (this.level != 1) {
                zzB += zzrq.zzB(1, this.level);
            }
            if (this.zzhN != 0) {
                zzB += zzrq.zzB(2, this.zzhN);
            }
            return this.zzhO != 0 ? zzB + zzrq.zzB(3, this.zzhO) : zzB;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            if (this.level != 1) {
                com_google_android_gms_internal_zzrq.zzz(1, this.level);
            }
            if (this.zzhN != 0) {
                com_google_android_gms_internal_zzrq.zzz(2, this.zzhN);
            }
            if (this.zzhO != 0) {
                com_google_android_gms_internal_zzrq.zzz(3, this.zzhO);
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }

    public static final class zzb extends zzrr<zzb> {
        private static volatile zzb[] zzhP;
        public int name;
        public int[] zzhQ;
        public int zzhR;
        public boolean zzhS;
        public boolean zzhT;

        public zzb() {
            zzD();
        }

        public static zzb[] zzC() {
            if (zzhP == null) {
                synchronized (zzrv.zzbck) {
                    if (zzhP == null) {
                        zzhP = new zzb[0];
                    }
                }
            }
            return zzhP;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzb)) {
                return false;
            }
            zzb com_google_android_gms_internal_zzaf_zzb = (zzb) o;
            return (zzrv.equals(this.zzhQ, com_google_android_gms_internal_zzaf_zzb.zzhQ) && this.zzhR == com_google_android_gms_internal_zzaf_zzb.zzhR && this.name == com_google_android_gms_internal_zzaf_zzb.name && this.zzhS == com_google_android_gms_internal_zzaf_zzb.zzhS && this.zzhT == com_google_android_gms_internal_zzaf_zzb.zzhT) ? zza((zzrr) com_google_android_gms_internal_zzaf_zzb) : false;
        }

        public int hashCode() {
            int i = 1231;
            int hashCode = ((this.zzhS ? 1231 : 1237) + ((((((zzrv.hashCode(this.zzhQ) + 527) * 31) + this.zzhR) * 31) + this.name) * 31)) * 31;
            if (!this.zzhT) {
                i = 1237;
            }
            return ((hashCode + i) * 31) + zzDk();
        }

        protected int zzB() {
            int i = 0;
            int zzB = super.zzB();
            if (this.zzhT) {
                zzB += zzrq.zzc(1, this.zzhT);
            }
            int zzB2 = zzrq.zzB(2, this.zzhR) + zzB;
            if (this.zzhQ == null || this.zzhQ.length <= 0) {
                zzB = zzB2;
            } else {
                for (int zzls : this.zzhQ) {
                    i += zzrq.zzls(zzls);
                }
                zzB = (zzB2 + i) + (this.zzhQ.length * 1);
            }
            if (this.name != 0) {
                zzB += zzrq.zzB(4, this.name);
            }
            return this.zzhS ? zzB + zzrq.zzc(6, this.zzhS) : zzB;
        }

        public zzb zzD() {
            this.zzhQ = zzsa.zzbcn;
            this.zzhR = 0;
            this.name = 0;
            this.zzhS = false;
            this.zzhT = false;
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            if (this.zzhT) {
                com_google_android_gms_internal_zzrq.zzb(1, this.zzhT);
            }
            com_google_android_gms_internal_zzrq.zzz(2, this.zzhR);
            if (this.zzhQ != null && this.zzhQ.length > 0) {
                for (int zzz : this.zzhQ) {
                    com_google_android_gms_internal_zzrq.zzz(3, zzz);
                }
            }
            if (this.name != 0) {
                com_google_android_gms_internal_zzrq.zzz(4, this.name);
            }
            if (this.zzhS) {
                com_google_android_gms_internal_zzrq.zzb(6, this.zzhS);
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }

    public static final class zzc extends zzrr<zzc> {
        private static volatile zzc[] zzhU;
        public String key;
        public long zzhV;
        public long zzhW;
        public boolean zzhX;
        public long zzhY;

        public zzc() {
            zzF();
        }

        public static zzc[] zzE() {
            if (zzhU == null) {
                synchronized (zzrv.zzbck) {
                    if (zzhU == null) {
                        zzhU = new zzc[0];
                    }
                }
            }
            return zzhU;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzc)) {
                return false;
            }
            zzc com_google_android_gms_internal_zzaf_zzc = (zzc) o;
            if (this.key == null) {
                if (com_google_android_gms_internal_zzaf_zzc.key != null) {
                    return false;
                }
            } else if (!this.key.equals(com_google_android_gms_internal_zzaf_zzc.key)) {
                return false;
            }
            return (this.zzhV == com_google_android_gms_internal_zzaf_zzc.zzhV && this.zzhW == com_google_android_gms_internal_zzaf_zzc.zzhW && this.zzhX == com_google_android_gms_internal_zzaf_zzc.zzhX && this.zzhY == com_google_android_gms_internal_zzaf_zzc.zzhY) ? zza((zzrr) com_google_android_gms_internal_zzaf_zzc) : false;
        }

        public int hashCode() {
            return (((((this.zzhX ? 1231 : 1237) + (((((((this.key == null ? 0 : this.key.hashCode()) + 527) * 31) + ((int) (this.zzhV ^ (this.zzhV >>> 32)))) * 31) + ((int) (this.zzhW ^ (this.zzhW >>> 32)))) * 31)) * 31) + ((int) (this.zzhY ^ (this.zzhY >>> 32)))) * 31) + zzDk();
        }

        protected int zzB() {
            int zzB = super.zzB();
            if (!this.key.equals("")) {
                zzB += zzrq.zzl(1, this.key);
            }
            if (this.zzhV != 0) {
                zzB += zzrq.zzd(2, this.zzhV);
            }
            if (this.zzhW != 2147483647L) {
                zzB += zzrq.zzd(3, this.zzhW);
            }
            if (this.zzhX) {
                zzB += zzrq.zzc(4, this.zzhX);
            }
            return this.zzhY != 0 ? zzB + zzrq.zzd(5, this.zzhY) : zzB;
        }

        public zzc zzF() {
            this.key = "";
            this.zzhV = 0;
            this.zzhW = 2147483647L;
            this.zzhX = false;
            this.zzhY = 0;
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            if (!this.key.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(1, this.key);
            }
            if (this.zzhV != 0) {
                com_google_android_gms_internal_zzrq.zzb(2, this.zzhV);
            }
            if (this.zzhW != 2147483647L) {
                com_google_android_gms_internal_zzrq.zzb(3, this.zzhW);
            }
            if (this.zzhX) {
                com_google_android_gms_internal_zzrq.zzb(4, this.zzhX);
            }
            if (this.zzhY != 0) {
                com_google_android_gms_internal_zzrq.zzb(5, this.zzhY);
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }

    public static final class zzd extends zzrr<zzd> {
        public com.google.android.gms.internal.zzag.zza[] zzhZ;
        public com.google.android.gms.internal.zzag.zza[] zzia;
        public zzc[] zzib;

        public zzd() {
            zzG();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzd)) {
                return false;
            }
            zzd com_google_android_gms_internal_zzaf_zzd = (zzd) o;
            return (zzrv.equals(this.zzhZ, com_google_android_gms_internal_zzaf_zzd.zzhZ) && zzrv.equals(this.zzia, com_google_android_gms_internal_zzaf_zzd.zzia) && zzrv.equals(this.zzib, com_google_android_gms_internal_zzaf_zzd.zzib)) ? zza((zzrr) com_google_android_gms_internal_zzaf_zzd) : false;
        }

        public int hashCode() {
            return ((((((zzrv.hashCode(this.zzhZ) + 527) * 31) + zzrv.hashCode(this.zzia)) * 31) + zzrv.hashCode(this.zzib)) * 31) + zzDk();
        }

        protected int zzB() {
            int i;
            int i2 = 0;
            int zzB = super.zzB();
            if (this.zzhZ != null && this.zzhZ.length > 0) {
                i = zzB;
                for (zzrx com_google_android_gms_internal_zzrx : this.zzhZ) {
                    if (com_google_android_gms_internal_zzrx != null) {
                        i += zzrq.zzc(1, com_google_android_gms_internal_zzrx);
                    }
                }
                zzB = i;
            }
            if (this.zzia != null && this.zzia.length > 0) {
                i = zzB;
                for (zzrx com_google_android_gms_internal_zzrx2 : this.zzia) {
                    if (com_google_android_gms_internal_zzrx2 != null) {
                        i += zzrq.zzc(2, com_google_android_gms_internal_zzrx2);
                    }
                }
                zzB = i;
            }
            if (this.zzib != null && this.zzib.length > 0) {
                while (i2 < this.zzib.length) {
                    zzrx com_google_android_gms_internal_zzrx3 = this.zzib[i2];
                    if (com_google_android_gms_internal_zzrx3 != null) {
                        zzB += zzrq.zzc(3, com_google_android_gms_internal_zzrx3);
                    }
                    i2++;
                }
            }
            return zzB;
        }

        public zzd zzG() {
            this.zzhZ = com.google.android.gms.internal.zzag.zza.zzQ();
            this.zzia = com.google.android.gms.internal.zzag.zza.zzQ();
            this.zzib = zzc.zzE();
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            int i = 0;
            if (this.zzhZ != null && this.zzhZ.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx : this.zzhZ) {
                    if (com_google_android_gms_internal_zzrx != null) {
                        com_google_android_gms_internal_zzrq.zza(1, com_google_android_gms_internal_zzrx);
                    }
                }
            }
            if (this.zzia != null && this.zzia.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx2 : this.zzia) {
                    if (com_google_android_gms_internal_zzrx2 != null) {
                        com_google_android_gms_internal_zzrq.zza(2, com_google_android_gms_internal_zzrx2);
                    }
                }
            }
            if (this.zzib != null && this.zzib.length > 0) {
                while (i < this.zzib.length) {
                    zzrx com_google_android_gms_internal_zzrx3 = this.zzib[i];
                    if (com_google_android_gms_internal_zzrx3 != null) {
                        com_google_android_gms_internal_zzrq.zza(3, com_google_android_gms_internal_zzrx3);
                    }
                    i++;
                }
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }

    public static final class zze extends zzrr<zze> {
        private static volatile zze[] zzic;
        public int key;
        public int value;

        public zze() {
            zzI();
        }

        public static zze[] zzH() {
            if (zzic == null) {
                synchronized (zzrv.zzbck) {
                    if (zzic == null) {
                        zzic = new zze[0];
                    }
                }
            }
            return zzic;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zze)) {
                return false;
            }
            zze com_google_android_gms_internal_zzaf_zze = (zze) o;
            return (this.key == com_google_android_gms_internal_zzaf_zze.key && this.value == com_google_android_gms_internal_zzaf_zze.value) ? zza((zzrr) com_google_android_gms_internal_zzaf_zze) : false;
        }

        public int hashCode() {
            return ((((this.key + 527) * 31) + this.value) * 31) + zzDk();
        }

        protected int zzB() {
            return (super.zzB() + zzrq.zzB(1, this.key)) + zzrq.zzB(2, this.value);
        }

        public zze zzI() {
            this.key = 0;
            this.value = 0;
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            com_google_android_gms_internal_zzrq.zzz(1, this.key);
            com_google_android_gms_internal_zzrq.zzz(2, this.value);
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }

    public static final class zzf extends zzrr<zzf> {
        public String version;
        public String[] zzid;
        public String[] zzie;
        public com.google.android.gms.internal.zzag.zza[] zzif;
        public zze[] zzig;
        public zzb[] zzih;
        public zzb[] zzii;
        public zzb[] zzij;
        public zzg[] zzik;
        public String zzil;
        public String zzim;
        public String zzin;
        public zza zzio;
        public float zzip;
        public boolean zziq;
        public String[] zzir;
        public int zzis;

        public zzf() {
            zzJ();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzf)) {
                return false;
            }
            zzf com_google_android_gms_internal_zzaf_zzf = (zzf) o;
            if (!zzrv.equals(this.zzid, com_google_android_gms_internal_zzaf_zzf.zzid) || !zzrv.equals(this.zzie, com_google_android_gms_internal_zzaf_zzf.zzie) || !zzrv.equals(this.zzif, com_google_android_gms_internal_zzaf_zzf.zzif) || !zzrv.equals(this.zzig, com_google_android_gms_internal_zzaf_zzf.zzig) || !zzrv.equals(this.zzih, com_google_android_gms_internal_zzaf_zzf.zzih) || !zzrv.equals(this.zzii, com_google_android_gms_internal_zzaf_zzf.zzii) || !zzrv.equals(this.zzij, com_google_android_gms_internal_zzaf_zzf.zzij) || !zzrv.equals(this.zzik, com_google_android_gms_internal_zzaf_zzf.zzik)) {
                return false;
            }
            if (this.zzil == null) {
                if (com_google_android_gms_internal_zzaf_zzf.zzil != null) {
                    return false;
                }
            } else if (!this.zzil.equals(com_google_android_gms_internal_zzaf_zzf.zzil)) {
                return false;
            }
            if (this.zzim == null) {
                if (com_google_android_gms_internal_zzaf_zzf.zzim != null) {
                    return false;
                }
            } else if (!this.zzim.equals(com_google_android_gms_internal_zzaf_zzf.zzim)) {
                return false;
            }
            if (this.zzin == null) {
                if (com_google_android_gms_internal_zzaf_zzf.zzin != null) {
                    return false;
                }
            } else if (!this.zzin.equals(com_google_android_gms_internal_zzaf_zzf.zzin)) {
                return false;
            }
            if (this.version == null) {
                if (com_google_android_gms_internal_zzaf_zzf.version != null) {
                    return false;
                }
            } else if (!this.version.equals(com_google_android_gms_internal_zzaf_zzf.version)) {
                return false;
            }
            if (this.zzio == null) {
                if (com_google_android_gms_internal_zzaf_zzf.zzio != null) {
                    return false;
                }
            } else if (!this.zzio.equals(com_google_android_gms_internal_zzaf_zzf.zzio)) {
                return false;
            }
            return (Float.floatToIntBits(this.zzip) == Float.floatToIntBits(com_google_android_gms_internal_zzaf_zzf.zzip) && this.zziq == com_google_android_gms_internal_zzaf_zzf.zziq && zzrv.equals(this.zzir, com_google_android_gms_internal_zzaf_zzf.zzir) && this.zzis == com_google_android_gms_internal_zzaf_zzf.zzis) ? zza((zzrr) com_google_android_gms_internal_zzaf_zzf) : false;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.version == null ? 0 : this.version.hashCode()) + (((this.zzin == null ? 0 : this.zzin.hashCode()) + (((this.zzim == null ? 0 : this.zzim.hashCode()) + (((this.zzil == null ? 0 : this.zzil.hashCode()) + ((((((((((((((((zzrv.hashCode(this.zzid) + 527) * 31) + zzrv.hashCode(this.zzie)) * 31) + zzrv.hashCode(this.zzif)) * 31) + zzrv.hashCode(this.zzig)) * 31) + zzrv.hashCode(this.zzih)) * 31) + zzrv.hashCode(this.zzii)) * 31) + zzrv.hashCode(this.zzij)) * 31) + zzrv.hashCode(this.zzik)) * 31)) * 31)) * 31)) * 31)) * 31;
            if (this.zzio != null) {
                i = this.zzio.hashCode();
            }
            return (((((((this.zziq ? 1231 : 1237) + ((((hashCode + i) * 31) + Float.floatToIntBits(this.zzip)) * 31)) * 31) + zzrv.hashCode(this.zzir)) * 31) + this.zzis) * 31) + zzDk();
        }

        protected int zzB() {
            int i;
            int i2;
            int i3;
            int i4 = 0;
            int zzB = super.zzB();
            if (this.zzie == null || this.zzie.length <= 0) {
                i = zzB;
            } else {
                i2 = 0;
                i3 = 0;
                for (String str : this.zzie) {
                    if (str != null) {
                        i3++;
                        i2 += zzrq.zzfy(str);
                    }
                }
                i = (zzB + i2) + (i3 * 1);
            }
            if (this.zzif != null && this.zzif.length > 0) {
                i2 = i;
                for (zzrx com_google_android_gms_internal_zzrx : this.zzif) {
                    if (com_google_android_gms_internal_zzrx != null) {
                        i2 += zzrq.zzc(2, com_google_android_gms_internal_zzrx);
                    }
                }
                i = i2;
            }
            if (this.zzig != null && this.zzig.length > 0) {
                i2 = i;
                for (zzrx com_google_android_gms_internal_zzrx2 : this.zzig) {
                    if (com_google_android_gms_internal_zzrx2 != null) {
                        i2 += zzrq.zzc(3, com_google_android_gms_internal_zzrx2);
                    }
                }
                i = i2;
            }
            if (this.zzih != null && this.zzih.length > 0) {
                i2 = i;
                for (zzrx com_google_android_gms_internal_zzrx22 : this.zzih) {
                    if (com_google_android_gms_internal_zzrx22 != null) {
                        i2 += zzrq.zzc(4, com_google_android_gms_internal_zzrx22);
                    }
                }
                i = i2;
            }
            if (this.zzii != null && this.zzii.length > 0) {
                i2 = i;
                for (zzrx com_google_android_gms_internal_zzrx222 : this.zzii) {
                    if (com_google_android_gms_internal_zzrx222 != null) {
                        i2 += zzrq.zzc(5, com_google_android_gms_internal_zzrx222);
                    }
                }
                i = i2;
            }
            if (this.zzij != null && this.zzij.length > 0) {
                i2 = i;
                for (zzrx com_google_android_gms_internal_zzrx2222 : this.zzij) {
                    if (com_google_android_gms_internal_zzrx2222 != null) {
                        i2 += zzrq.zzc(6, com_google_android_gms_internal_zzrx2222);
                    }
                }
                i = i2;
            }
            if (this.zzik != null && this.zzik.length > 0) {
                i2 = i;
                for (zzrx com_google_android_gms_internal_zzrx22222 : this.zzik) {
                    if (com_google_android_gms_internal_zzrx22222 != null) {
                        i2 += zzrq.zzc(7, com_google_android_gms_internal_zzrx22222);
                    }
                }
                i = i2;
            }
            if (!this.zzil.equals("")) {
                i += zzrq.zzl(9, this.zzil);
            }
            if (!this.zzim.equals("")) {
                i += zzrq.zzl(10, this.zzim);
            }
            if (!this.zzin.equals("0")) {
                i += zzrq.zzl(12, this.zzin);
            }
            if (!this.version.equals("")) {
                i += zzrq.zzl(13, this.version);
            }
            if (this.zzio != null) {
                i += zzrq.zzc(14, this.zzio);
            }
            if (Float.floatToIntBits(this.zzip) != Float.floatToIntBits(0.0f)) {
                i += zzrq.zzc(15, this.zzip);
            }
            if (this.zzir != null && this.zzir.length > 0) {
                i3 = 0;
                zzB = 0;
                for (String str2 : this.zzir) {
                    if (str2 != null) {
                        zzB++;
                        i3 += zzrq.zzfy(str2);
                    }
                }
                i = (i + i3) + (zzB * 2);
            }
            if (this.zzis != 0) {
                i += zzrq.zzB(17, this.zzis);
            }
            if (this.zziq) {
                i += zzrq.zzc(18, this.zziq);
            }
            if (this.zzid == null || this.zzid.length <= 0) {
                return i;
            }
            i2 = 0;
            i3 = 0;
            while (i4 < this.zzid.length) {
                String str3 = this.zzid[i4];
                if (str3 != null) {
                    i3++;
                    i2 += zzrq.zzfy(str3);
                }
                i4++;
            }
            return (i + i2) + (i3 * 2);
        }

        public zzf zzJ() {
            this.zzid = zzsa.zzbcs;
            this.zzie = zzsa.zzbcs;
            this.zzif = com.google.android.gms.internal.zzag.zza.zzQ();
            this.zzig = zze.zzH();
            this.zzih = zzb.zzC();
            this.zzii = zzb.zzC();
            this.zzij = zzb.zzC();
            this.zzik = zzg.zzK();
            this.zzil = "";
            this.zzim = "";
            this.zzin = "0";
            this.version = "";
            this.zzio = null;
            this.zzip = 0.0f;
            this.zziq = false;
            this.zzir = zzsa.zzbcs;
            this.zzis = 0;
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            int i = 0;
            if (this.zzie != null && this.zzie.length > 0) {
                for (String str : this.zzie) {
                    if (str != null) {
                        com_google_android_gms_internal_zzrq.zzb(1, str);
                    }
                }
            }
            if (this.zzif != null && this.zzif.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx : this.zzif) {
                    if (com_google_android_gms_internal_zzrx != null) {
                        com_google_android_gms_internal_zzrq.zza(2, com_google_android_gms_internal_zzrx);
                    }
                }
            }
            if (this.zzig != null && this.zzig.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx2 : this.zzig) {
                    if (com_google_android_gms_internal_zzrx2 != null) {
                        com_google_android_gms_internal_zzrq.zza(3, com_google_android_gms_internal_zzrx2);
                    }
                }
            }
            if (this.zzih != null && this.zzih.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx22 : this.zzih) {
                    if (com_google_android_gms_internal_zzrx22 != null) {
                        com_google_android_gms_internal_zzrq.zza(4, com_google_android_gms_internal_zzrx22);
                    }
                }
            }
            if (this.zzii != null && this.zzii.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx222 : this.zzii) {
                    if (com_google_android_gms_internal_zzrx222 != null) {
                        com_google_android_gms_internal_zzrq.zza(5, com_google_android_gms_internal_zzrx222);
                    }
                }
            }
            if (this.zzij != null && this.zzij.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx2222 : this.zzij) {
                    if (com_google_android_gms_internal_zzrx2222 != null) {
                        com_google_android_gms_internal_zzrq.zza(6, com_google_android_gms_internal_zzrx2222);
                    }
                }
            }
            if (this.zzik != null && this.zzik.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx22222 : this.zzik) {
                    if (com_google_android_gms_internal_zzrx22222 != null) {
                        com_google_android_gms_internal_zzrq.zza(7, com_google_android_gms_internal_zzrx22222);
                    }
                }
            }
            if (!this.zzil.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(9, this.zzil);
            }
            if (!this.zzim.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(10, this.zzim);
            }
            if (!this.zzin.equals("0")) {
                com_google_android_gms_internal_zzrq.zzb(12, this.zzin);
            }
            if (!this.version.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(13, this.version);
            }
            if (this.zzio != null) {
                com_google_android_gms_internal_zzrq.zza(14, this.zzio);
            }
            if (Float.floatToIntBits(this.zzip) != Float.floatToIntBits(0.0f)) {
                com_google_android_gms_internal_zzrq.zzb(15, this.zzip);
            }
            if (this.zzir != null && this.zzir.length > 0) {
                for (String str2 : this.zzir) {
                    if (str2 != null) {
                        com_google_android_gms_internal_zzrq.zzb(16, str2);
                    }
                }
            }
            if (this.zzis != 0) {
                com_google_android_gms_internal_zzrq.zzz(17, this.zzis);
            }
            if (this.zziq) {
                com_google_android_gms_internal_zzrq.zzb(18, this.zziq);
            }
            if (this.zzid != null && this.zzid.length > 0) {
                while (i < this.zzid.length) {
                    String str3 = this.zzid[i];
                    if (str3 != null) {
                        com_google_android_gms_internal_zzrq.zzb(19, str3);
                    }
                    i++;
                }
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }

    public static final class zzg extends zzrr<zzg> {
        private static volatile zzg[] zzit;
        public int[] zziA;
        public int[] zziB;
        public int[] zziC;
        public int[] zziD;
        public int[] zziu;
        public int[] zziv;
        public int[] zziw;
        public int[] zzix;
        public int[] zziy;
        public int[] zziz;

        public zzg() {
            zzL();
        }

        public static zzg[] zzK() {
            if (zzit == null) {
                synchronized (zzrv.zzbck) {
                    if (zzit == null) {
                        zzit = new zzg[0];
                    }
                }
            }
            return zzit;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzg)) {
                return false;
            }
            zzg com_google_android_gms_internal_zzaf_zzg = (zzg) o;
            return (zzrv.equals(this.zziu, com_google_android_gms_internal_zzaf_zzg.zziu) && zzrv.equals(this.zziv, com_google_android_gms_internal_zzaf_zzg.zziv) && zzrv.equals(this.zziw, com_google_android_gms_internal_zzaf_zzg.zziw) && zzrv.equals(this.zzix, com_google_android_gms_internal_zzaf_zzg.zzix) && zzrv.equals(this.zziy, com_google_android_gms_internal_zzaf_zzg.zziy) && zzrv.equals(this.zziz, com_google_android_gms_internal_zzaf_zzg.zziz) && zzrv.equals(this.zziA, com_google_android_gms_internal_zzaf_zzg.zziA) && zzrv.equals(this.zziB, com_google_android_gms_internal_zzaf_zzg.zziB) && zzrv.equals(this.zziC, com_google_android_gms_internal_zzaf_zzg.zziC) && zzrv.equals(this.zziD, com_google_android_gms_internal_zzaf_zzg.zziD)) ? zza((zzrr) com_google_android_gms_internal_zzaf_zzg) : false;
        }

        public int hashCode() {
            return ((((((((((((((((((((zzrv.hashCode(this.zziu) + 527) * 31) + zzrv.hashCode(this.zziv)) * 31) + zzrv.hashCode(this.zziw)) * 31) + zzrv.hashCode(this.zzix)) * 31) + zzrv.hashCode(this.zziy)) * 31) + zzrv.hashCode(this.zziz)) * 31) + zzrv.hashCode(this.zziA)) * 31) + zzrv.hashCode(this.zziB)) * 31) + zzrv.hashCode(this.zziC)) * 31) + zzrv.hashCode(this.zziD)) * 31) + zzDk();
        }

        protected int zzB() {
            int i;
            int i2;
            int i3 = 0;
            int zzB = super.zzB();
            if (this.zziu == null || this.zziu.length <= 0) {
                i = zzB;
            } else {
                i2 = 0;
                for (int zzls : this.zziu) {
                    i2 += zzrq.zzls(zzls);
                }
                i = (zzB + i2) + (this.zziu.length * 1);
            }
            if (this.zziv != null && this.zziv.length > 0) {
                zzB = 0;
                for (int zzls2 : this.zziv) {
                    zzB += zzrq.zzls(zzls2);
                }
                i = (i + zzB) + (this.zziv.length * 1);
            }
            if (this.zziw != null && this.zziw.length > 0) {
                zzB = 0;
                for (int zzls22 : this.zziw) {
                    zzB += zzrq.zzls(zzls22);
                }
                i = (i + zzB) + (this.zziw.length * 1);
            }
            if (this.zzix != null && this.zzix.length > 0) {
                zzB = 0;
                for (int zzls222 : this.zzix) {
                    zzB += zzrq.zzls(zzls222);
                }
                i = (i + zzB) + (this.zzix.length * 1);
            }
            if (this.zziy != null && this.zziy.length > 0) {
                zzB = 0;
                for (int zzls2222 : this.zziy) {
                    zzB += zzrq.zzls(zzls2222);
                }
                i = (i + zzB) + (this.zziy.length * 1);
            }
            if (this.zziz != null && this.zziz.length > 0) {
                zzB = 0;
                for (int zzls22222 : this.zziz) {
                    zzB += zzrq.zzls(zzls22222);
                }
                i = (i + zzB) + (this.zziz.length * 1);
            }
            if (this.zziA != null && this.zziA.length > 0) {
                zzB = 0;
                for (int zzls222222 : this.zziA) {
                    zzB += zzrq.zzls(zzls222222);
                }
                i = (i + zzB) + (this.zziA.length * 1);
            }
            if (this.zziB != null && this.zziB.length > 0) {
                zzB = 0;
                for (int zzls2222222 : this.zziB) {
                    zzB += zzrq.zzls(zzls2222222);
                }
                i = (i + zzB) + (this.zziB.length * 1);
            }
            if (this.zziC != null && this.zziC.length > 0) {
                zzB = 0;
                for (int zzls22222222 : this.zziC) {
                    zzB += zzrq.zzls(zzls22222222);
                }
                i = (i + zzB) + (this.zziC.length * 1);
            }
            if (this.zziD == null || this.zziD.length <= 0) {
                return i;
            }
            i2 = 0;
            while (i3 < this.zziD.length) {
                i2 += zzrq.zzls(this.zziD[i3]);
                i3++;
            }
            return (i + i2) + (this.zziD.length * 1);
        }

        public zzg zzL() {
            this.zziu = zzsa.zzbcn;
            this.zziv = zzsa.zzbcn;
            this.zziw = zzsa.zzbcn;
            this.zzix = zzsa.zzbcn;
            this.zziy = zzsa.zzbcn;
            this.zziz = zzsa.zzbcn;
            this.zziA = zzsa.zzbcn;
            this.zziB = zzsa.zzbcn;
            this.zziC = zzsa.zzbcn;
            this.zziD = zzsa.zzbcn;
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            int i = 0;
            if (this.zziu != null && this.zziu.length > 0) {
                for (int zzz : this.zziu) {
                    com_google_android_gms_internal_zzrq.zzz(1, zzz);
                }
            }
            if (this.zziv != null && this.zziv.length > 0) {
                for (int zzz2 : this.zziv) {
                    com_google_android_gms_internal_zzrq.zzz(2, zzz2);
                }
            }
            if (this.zziw != null && this.zziw.length > 0) {
                for (int zzz22 : this.zziw) {
                    com_google_android_gms_internal_zzrq.zzz(3, zzz22);
                }
            }
            if (this.zzix != null && this.zzix.length > 0) {
                for (int zzz222 : this.zzix) {
                    com_google_android_gms_internal_zzrq.zzz(4, zzz222);
                }
            }
            if (this.zziy != null && this.zziy.length > 0) {
                for (int zzz2222 : this.zziy) {
                    com_google_android_gms_internal_zzrq.zzz(5, zzz2222);
                }
            }
            if (this.zziz != null && this.zziz.length > 0) {
                for (int zzz22222 : this.zziz) {
                    com_google_android_gms_internal_zzrq.zzz(6, zzz22222);
                }
            }
            if (this.zziA != null && this.zziA.length > 0) {
                for (int zzz222222 : this.zziA) {
                    com_google_android_gms_internal_zzrq.zzz(7, zzz222222);
                }
            }
            if (this.zziB != null && this.zziB.length > 0) {
                for (int zzz2222222 : this.zziB) {
                    com_google_android_gms_internal_zzrq.zzz(8, zzz2222222);
                }
            }
            if (this.zziC != null && this.zziC.length > 0) {
                for (int zzz22222222 : this.zziC) {
                    com_google_android_gms_internal_zzrq.zzz(9, zzz22222222);
                }
            }
            if (this.zziD != null && this.zziD.length > 0) {
                while (i < this.zziD.length) {
                    com_google_android_gms_internal_zzrq.zzz(10, this.zziD[i]);
                    i++;
                }
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }

    public static final class zzi extends zzrr<zzi> {
        private static volatile zzi[] zziN;
        public String name;
        public com.google.android.gms.internal.zzag.zza zziO;
        public zzd zziP;

        public zzi() {
            zzO();
        }

        public static zzi[] zzN() {
            if (zziN == null) {
                synchronized (zzrv.zzbck) {
                    if (zziN == null) {
                        zziN = new zzi[0];
                    }
                }
            }
            return zziN;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzi)) {
                return false;
            }
            zzi com_google_android_gms_internal_zzaf_zzi = (zzi) o;
            if (this.name == null) {
                if (com_google_android_gms_internal_zzaf_zzi.name != null) {
                    return false;
                }
            } else if (!this.name.equals(com_google_android_gms_internal_zzaf_zzi.name)) {
                return false;
            }
            if (this.zziO == null) {
                if (com_google_android_gms_internal_zzaf_zzi.zziO != null) {
                    return false;
                }
            } else if (!this.zziO.equals(com_google_android_gms_internal_zzaf_zzi.zziO)) {
                return false;
            }
            if (this.zziP == null) {
                if (com_google_android_gms_internal_zzaf_zzi.zziP != null) {
                    return false;
                }
            } else if (!this.zziP.equals(com_google_android_gms_internal_zzaf_zzi.zziP)) {
                return false;
            }
            return zza((zzrr) com_google_android_gms_internal_zzaf_zzi);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.zziO == null ? 0 : this.zziO.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + 527) * 31)) * 31;
            if (this.zziP != null) {
                i = this.zziP.hashCode();
            }
            return ((hashCode + i) * 31) + zzDk();
        }

        protected int zzB() {
            int zzB = super.zzB();
            if (!this.name.equals("")) {
                zzB += zzrq.zzl(1, this.name);
            }
            if (this.zziO != null) {
                zzB += zzrq.zzc(2, this.zziO);
            }
            return this.zziP != null ? zzB + zzrq.zzc(3, this.zziP) : zzB;
        }

        public zzi zzO() {
            this.name = "";
            this.zziO = null;
            this.zziP = null;
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            if (!this.name.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(1, this.name);
            }
            if (this.zziO != null) {
                com_google_android_gms_internal_zzrq.zza(2, this.zziO);
            }
            if (this.zziP != null) {
                com_google_android_gms_internal_zzrq.zza(3, this.zziP);
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }

    public static final class zzj extends zzrr<zzj> {
        public zzi[] zziQ;
        public zzf zziR;
        public String zziS;

        public zzj() {
            zzP();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof zzj)) {
                return false;
            }
            zzj com_google_android_gms_internal_zzaf_zzj = (zzj) o;
            if (!zzrv.equals(this.zziQ, com_google_android_gms_internal_zzaf_zzj.zziQ)) {
                return false;
            }
            if (this.zziR == null) {
                if (com_google_android_gms_internal_zzaf_zzj.zziR != null) {
                    return false;
                }
            } else if (!this.zziR.equals(com_google_android_gms_internal_zzaf_zzj.zziR)) {
                return false;
            }
            if (this.zziS == null) {
                if (com_google_android_gms_internal_zzaf_zzj.zziS != null) {
                    return false;
                }
            } else if (!this.zziS.equals(com_google_android_gms_internal_zzaf_zzj.zziS)) {
                return false;
            }
            return zza((zzrr) com_google_android_gms_internal_zzaf_zzj);
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.zziR == null ? 0 : this.zziR.hashCode()) + ((zzrv.hashCode(this.zziQ) + 527) * 31)) * 31;
            if (this.zziS != null) {
                i = this.zziS.hashCode();
            }
            return ((hashCode + i) * 31) + zzDk();
        }

        protected int zzB() {
            int zzB = super.zzB();
            if (this.zziQ != null && this.zziQ.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx : this.zziQ) {
                    if (com_google_android_gms_internal_zzrx != null) {
                        zzB += zzrq.zzc(1, com_google_android_gms_internal_zzrx);
                    }
                }
            }
            if (this.zziR != null) {
                zzB += zzrq.zzc(2, this.zziR);
            }
            return !this.zziS.equals("") ? zzB + zzrq.zzl(3, this.zziS) : zzB;
        }

        public zzj zzP() {
            this.zziQ = zzi.zzN();
            this.zziR = null;
            this.zziS = "";
            this.zzbca = null;
            this.zzbcl = -1;
            return this;
        }

        public void zza(zzrq com_google_android_gms_internal_zzrq) throws IOException {
            if (this.zziQ != null && this.zziQ.length > 0) {
                for (zzrx com_google_android_gms_internal_zzrx : this.zziQ) {
                    if (com_google_android_gms_internal_zzrx != null) {
                        com_google_android_gms_internal_zzrq.zza(1, com_google_android_gms_internal_zzrx);
                    }
                }
            }
            if (this.zziR != null) {
                com_google_android_gms_internal_zzrq.zza(2, this.zziR);
            }
            if (!this.zziS.equals("")) {
                com_google_android_gms_internal_zzrq.zzb(3, this.zziS);
            }
            super.zza(com_google_android_gms_internal_zzrq);
        }
    }
}
