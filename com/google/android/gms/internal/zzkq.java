package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzkq<T> {
    private static zza zzaaX;
    private static int zzaaY;
    private static String zzaaZ;
    private static final Object zzpm;
    private T zzNR;
    protected final String zztP;
    protected final T zztQ;

    /* renamed from: com.google.android.gms.internal.zzkq.1 */
    static class C09691 extends zzkq<Boolean> {
        C09691(String str, Boolean bool) {
            super(str, bool);
        }

        protected /* synthetic */ Object zzbX(String str) {
            return zzbY(str);
        }

        protected Boolean zzbY(String str) {
            return zzkq.zzaaX.zzb(this.zztP, (Boolean) this.zztQ);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkq.2 */
    static class C09702 extends zzkq<Long> {
        C09702(String str, Long l) {
            super(str, l);
        }

        protected /* synthetic */ Object zzbX(String str) {
            return zzbZ(str);
        }

        protected Long zzbZ(String str) {
            return zzkq.zzaaX.getLong(this.zztP, (Long) this.zztQ);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkq.3 */
    static class C09713 extends zzkq<Integer> {
        C09713(String str, Integer num) {
            super(str, num);
        }

        protected /* synthetic */ Object zzbX(String str) {
            return zzca(str);
        }

        protected Integer zzca(String str) {
            return zzkq.zzaaX.zzb(this.zztP, (Integer) this.zztQ);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkq.4 */
    static class C09724 extends zzkq<Float> {
        C09724(String str, Float f) {
            super(str, f);
        }

        protected /* synthetic */ Object zzbX(String str) {
            return zzcb(str);
        }

        protected Float zzcb(String str) {
            return zzkq.zzaaX.zzb(this.zztP, (Float) this.zztQ);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkq.5 */
    static class C09735 extends zzkq<String> {
        C09735(String str, String str2) {
            super(str, str2);
        }

        protected /* synthetic */ Object zzbX(String str) {
            return zzcc(str);
        }

        protected String zzcc(String str) {
            return zzkq.zzaaX.getString(this.zztP, (String) this.zztQ);
        }
    }

    private interface zza {
        Long getLong(String str, Long l);

        String getString(String str, String str2);

        Boolean zzb(String str, Boolean bool);

        Float zzb(String str, Float f);

        Integer zzb(String str, Integer num);
    }

    static {
        zzpm = new Object();
        zzaaX = null;
        zzaaY = 0;
        zzaaZ = "com.google.android.providers.gsf.permission.READ_GSERVICES";
    }

    protected zzkq(String str, T t) {
        this.zzNR = null;
        this.zztP = str;
        this.zztQ = t;
    }

    public static boolean isInitialized() {
        return zzaaX != null;
    }

    public static zzkq<Float> zza(String str, Float f) {
        return new C09724(str, f);
    }

    public static zzkq<Integer> zza(String str, Integer num) {
        return new C09713(str, num);
    }

    public static zzkq<Long> zza(String str, Long l) {
        return new C09702(str, l);
    }

    public static zzkq<Boolean> zzg(String str, boolean z) {
        return new C09691(str, Boolean.valueOf(z));
    }

    public static int zznM() {
        return zzaaY;
    }

    public static zzkq<String> zzu(String str, String str2) {
        return new C09735(str, str2);
    }

    public final T get() {
        return this.zzNR != null ? this.zzNR : zzbX(this.zztP);
    }

    protected abstract T zzbX(String str);

    public final T zznN() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            T t = get();
            return t;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
