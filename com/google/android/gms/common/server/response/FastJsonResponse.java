package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.internal.zzlj;
import com.google.android.gms.internal.zzls;
import com.google.android.gms.internal.zzlt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import loquendo.tts.engine.TTSConst;

public abstract class FastJsonResponse {

    public interface zza<I, O> {
        I convertBack(O o);
    }

    public static class Field<I, O> implements SafeParcelable {
        public static final zza CREATOR;
        private final int mVersionCode;
        protected final int zzaeQ;
        protected final boolean zzaeR;
        protected final int zzaeS;
        protected final boolean zzaeT;
        protected final String zzaeU;
        protected final int zzaeV;
        protected final Class<? extends FastJsonResponse> zzaeW;
        protected final String zzaeX;
        private FieldMappingDictionary zzaeY;
        private zza<I, O> zzaeZ;

        static {
            CREATOR = new zza();
        }

        Field(int versionCode, int typeIn, boolean typeInArray, int typeOut, boolean typeOutArray, String outputFieldName, int safeParcelableFieldId, String concreteTypeName, ConverterWrapper wrappedConverter) {
            this.mVersionCode = versionCode;
            this.zzaeQ = typeIn;
            this.zzaeR = typeInArray;
            this.zzaeS = typeOut;
            this.zzaeT = typeOutArray;
            this.zzaeU = outputFieldName;
            this.zzaeV = safeParcelableFieldId;
            if (concreteTypeName == null) {
                this.zzaeW = null;
                this.zzaeX = null;
            } else {
                this.zzaeW = SafeParcelResponse.class;
                this.zzaeX = concreteTypeName;
            }
            if (wrappedConverter == null) {
                this.zzaeZ = null;
            } else {
                this.zzaeZ = wrappedConverter.zzoX();
            }
        }

        public I convertBack(O output) {
            return this.zzaeZ.convertBack(output);
        }

        public int describeContents() {
            zza com_google_android_gms_common_server_response_zza = CREATOR;
            return 0;
        }

        public int getVersionCode() {
            return this.mVersionCode;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field\n");
            stringBuilder.append("            versionCode=").append(this.mVersionCode).append('\n');
            stringBuilder.append("                 typeIn=").append(this.zzaeQ).append('\n');
            stringBuilder.append("            typeInArray=").append(this.zzaeR).append('\n');
            stringBuilder.append("                typeOut=").append(this.zzaeS).append('\n');
            stringBuilder.append("           typeOutArray=").append(this.zzaeT).append('\n');
            stringBuilder.append("        outputFieldName=").append(this.zzaeU).append('\n');
            stringBuilder.append("      safeParcelFieldId=").append(this.zzaeV).append('\n');
            stringBuilder.append("       concreteTypeName=").append(zzpk()).append('\n');
            if (zzpj() != null) {
                stringBuilder.append("     concreteType.class=").append(zzpj().getCanonicalName()).append('\n');
            }
            stringBuilder.append("          converterName=").append(this.zzaeZ == null ? "null" : this.zzaeZ.getClass().getCanonicalName()).append('\n');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel out, int flags) {
            zza com_google_android_gms_common_server_response_zza = CREATOR;
            zza.zza(this, out, flags);
        }

        public void zza(FieldMappingDictionary fieldMappingDictionary) {
            this.zzaeY = fieldMappingDictionary;
        }

        public int zzoZ() {
            return this.zzaeQ;
        }

        public int zzpa() {
            return this.zzaeS;
        }

        public boolean zzpf() {
            return this.zzaeR;
        }

        public boolean zzpg() {
            return this.zzaeT;
        }

        public String zzph() {
            return this.zzaeU;
        }

        public int zzpi() {
            return this.zzaeV;
        }

        public Class<? extends FastJsonResponse> zzpj() {
            return this.zzaeW;
        }

        String zzpk() {
            return this.zzaeX == null ? null : this.zzaeX;
        }

        public boolean zzpl() {
            return this.zzaeZ != null;
        }

        ConverterWrapper zzpm() {
            return this.zzaeZ == null ? null : ConverterWrapper.zza(this.zzaeZ);
        }

        public Map<String, Field<?, ?>> zzpn() {
            zzx.zzv(this.zzaeX);
            zzx.zzv(this.zzaeY);
            return this.zzaeY.zzcx(this.zzaeX);
        }
    }

    private void zza(StringBuilder stringBuilder, Field field, Object obj) {
        if (field.zzoZ() == 11) {
            stringBuilder.append(((FastJsonResponse) field.zzpj().cast(obj)).toString());
        } else if (field.zzoZ() == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(zzls.zzcA((String) obj));
            stringBuilder.append("\"");
        } else {
            stringBuilder.append(obj);
        }
    }

    private void zza(StringBuilder stringBuilder, Field field, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                zza(stringBuilder, field, obj);
            }
        }
        stringBuilder.append("]");
    }

    public String toString() {
        Map zzpb = zzpb();
        StringBuilder stringBuilder = new StringBuilder(100);
        for (String str : zzpb.keySet()) {
            Field field = (Field) zzpb.get(str);
            if (zza(field)) {
                Object zza = zza(field, zzb(field));
                if (stringBuilder.length() == 0) {
                    stringBuilder.append("{");
                } else {
                    stringBuilder.append(",");
                }
                stringBuilder.append("\"").append(str).append("\":");
                if (zza != null) {
                    switch (field.zzpa()) {
                        case TTSConst.TTSEVT_TAG /*8*/:
                            stringBuilder.append("\"").append(zzlj.zzi((byte[]) zza)).append("\"");
                            break;
                        case TTSConst.TTSEVT_PAUSE /*9*/:
                            stringBuilder.append("\"").append(zzlj.zzj((byte[]) zza)).append("\"");
                            break;
                        case TTSConst.TTSEVT_RESUME /*10*/:
                            zzlt.zza(stringBuilder, (HashMap) zza);
                            break;
                        default:
                            if (!field.zzpf()) {
                                zza(stringBuilder, field, zza);
                                break;
                            }
                            zza(stringBuilder, field, (ArrayList) zza);
                            break;
                    }
                }
                stringBuilder.append("null");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("}");
        } else {
            stringBuilder.append("{}");
        }
        return stringBuilder.toString();
    }

    protected <O, I> I zza(Field<I, O> field, Object obj) {
        return field.zzaeZ != null ? field.convertBack(obj) : obj;
    }

    protected boolean zza(Field field) {
        return field.zzpa() == 11 ? field.zzpg() ? zzcw(field.zzph()) : zzcv(field.zzph()) : zzcu(field.zzph());
    }

    protected Object zzb(Field field) {
        String zzph = field.zzph();
        if (field.zzpj() == null) {
            return zzct(field.zzph());
        }
        zzx.zza(zzct(field.zzph()) == null, "Concrete field shouldn't be value object: %s", field.zzph());
        Map zzpd = field.zzpg() ? zzpd() : zzpc();
        if (zzpd != null) {
            return zzpd.get(zzph);
        }
        try {
            return getClass().getMethod("get" + Character.toUpperCase(zzph.charAt(0)) + zzph.substring(1), new Class[0]).invoke(this, new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Object zzct(String str);

    protected abstract boolean zzcu(String str);

    protected boolean zzcv(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean zzcw(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    public abstract Map<String, Field<?, ?>> zzpb();

    public HashMap<String, Object> zzpc() {
        return null;
    }

    public HashMap<String, Object> zzpd() {
        return null;
    }
}
