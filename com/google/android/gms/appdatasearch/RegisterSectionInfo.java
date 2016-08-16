package com.google.android.gms.appdatasearch;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class RegisterSectionInfo implements SafeParcelable {
    public static final zzi CREATOR;
    final int mVersionCode;
    public final String name;
    public final int weight;
    public final String zzPA;
    public final boolean zzPB;
    public final boolean zzPC;
    public final String zzPD;
    public final Feature[] zzPE;
    final int[] zzPF;
    public final String zzPG;

    public static final class zza {
        private final String mName;
        private String zzPH;
        private boolean zzPI;
        private int zzPJ;
        private boolean zzPK;
        private String zzPL;
        private final List<Feature> zzPM;
        private BitSet zzPN;
        private String zzPO;

        public zza(String str) {
            this.mName = str;
            this.zzPJ = 1;
            this.zzPM = new ArrayList();
        }

        public zza zzM(boolean z) {
            this.zzPI = z;
            return this;
        }

        public zza zzby(String str) {
            this.zzPH = str;
            return this;
        }

        public RegisterSectionInfo zzld() {
            int i = 0;
            int[] iArr = null;
            if (this.zzPN != null) {
                iArr = new int[this.zzPN.cardinality()];
                int nextSetBit = this.zzPN.nextSetBit(0);
                while (nextSetBit >= 0) {
                    int i2 = i + 1;
                    iArr[i] = nextSetBit;
                    nextSetBit = this.zzPN.nextSetBit(nextSetBit + 1);
                    i = i2;
                }
            }
            return new RegisterSectionInfo(this.mName, this.zzPH, this.zzPI, this.zzPJ, this.zzPK, this.zzPL, (Feature[]) this.zzPM.toArray(new Feature[this.zzPM.size()]), iArr, this.zzPO);
        }
    }

    static {
        CREATOR = new zzi();
    }

    RegisterSectionInfo(int versionCode, String name, String format, boolean noIndex, int weight, boolean indexPrefixes, String subsectionSeparator, Feature[] features, int[] semanticLabels, String schemaOrgProperty) {
        this.mVersionCode = versionCode;
        this.name = name;
        this.zzPA = format;
        this.zzPB = noIndex;
        this.weight = weight;
        this.zzPC = indexPrefixes;
        this.zzPD = subsectionSeparator;
        this.zzPE = features;
        this.zzPF = semanticLabels;
        this.zzPG = schemaOrgProperty;
    }

    RegisterSectionInfo(String name, String format, boolean noIndex, int weight, boolean indexPrefixes, String subsectionSeparator, Feature[] features, int[] semanticLabels, String schemaOrgProperty) {
        this(2, name, format, noIndex, weight, indexPrefixes, subsectionSeparator, features, semanticLabels, schemaOrgProperty);
    }

    public int describeContents() {
        zzi com_google_android_gms_appdatasearch_zzi = CREATOR;
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzi com_google_android_gms_appdatasearch_zzi = CREATOR;
        zzi.zza(this, out, flags);
    }
}
