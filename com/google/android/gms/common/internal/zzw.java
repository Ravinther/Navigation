package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class zzw {

    public static final class zza {
        private final Object zzIr;
        private final List<String> zzael;

        private zza(Object obj) {
            this.zzIr = zzx.zzv(obj);
            this.zzael = new ArrayList();
        }

        public String toString() {
            StringBuilder append = new StringBuilder(100).append(this.zzIr.getClass().getSimpleName()).append('{');
            int size = this.zzael.size();
            for (int i = 0; i < size; i++) {
                append.append((String) this.zzael.get(i));
                if (i < size - 1) {
                    append.append(", ");
                }
            }
            return append.append('}').toString();
        }

        public zza zzg(String str, Object obj) {
            this.zzael.add(((String) zzx.zzv(str)) + "=" + String.valueOf(obj));
            return this;
        }
    }

    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }

    public static zza zzu(Object obj) {
        return new zza(null);
    }
}
