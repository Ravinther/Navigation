package com.google.android.gms.analytics.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.zzx;
import java.util.UUID;

public class zzai extends zzd {
    private SharedPreferences zzOw;
    private long zzOx;
    private long zzOy;
    private final zza zzOz;

    public final class zza {
        private final String mName;
        private final long zzOA;
        final /* synthetic */ zzai zzOB;

        private zza(zzai com_google_android_gms_analytics_internal_zzai, String str, long j) {
            this.zzOB = com_google_android_gms_analytics_internal_zzai;
            zzx.zzcs(str);
            zzx.zzZ(j > 0);
            this.mName = str;
            this.zzOA = j;
        }

        private void zzkE() {
            long currentTimeMillis = this.zzOB.zzid().currentTimeMillis();
            Editor edit = this.zzOB.zzOw.edit();
            edit.remove(zzkJ());
            edit.remove(zzkK());
            edit.putLong(zzkI(), currentTimeMillis);
            edit.commit();
        }

        private long zzkF() {
            long zzkH = zzkH();
            return zzkH == 0 ? 0 : Math.abs(zzkH - this.zzOB.zzid().currentTimeMillis());
        }

        private long zzkH() {
            return this.zzOB.zzOw.getLong(zzkI(), 0);
        }

        private String zzkI() {
            return this.mName + ":start";
        }

        private String zzkJ() {
            return this.mName + ":count";
        }

        public void zzbl(String str) {
            if (zzkH() == 0) {
                zzkE();
            }
            if (str == null) {
                str = "";
            }
            synchronized (this) {
                long j = this.zzOB.zzOw.getLong(zzkJ(), 0);
                if (j <= 0) {
                    Editor edit = this.zzOB.zzOw.edit();
                    edit.putString(zzkK(), str);
                    edit.putLong(zzkJ(), 1);
                    edit.apply();
                    return;
                }
                Object obj = (UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE) < Long.MAX_VALUE / (j + 1) ? 1 : null;
                Editor edit2 = this.zzOB.zzOw.edit();
                if (obj != null) {
                    edit2.putString(zzkK(), str);
                }
                edit2.putLong(zzkJ(), j + 1);
                edit2.apply();
            }
        }

        public Pair<String, Long> zzkG() {
            long zzkF = zzkF();
            if (zzkF < this.zzOA) {
                return null;
            }
            if (zzkF > this.zzOA * 2) {
                zzkE();
                return null;
            }
            String string = this.zzOB.zzOw.getString(zzkK(), null);
            zzkF = this.zzOB.zzOw.getLong(zzkJ(), 0);
            zzkE();
            return (string == null || zzkF <= 0) ? null : new Pair(string, Long.valueOf(zzkF));
        }

        protected String zzkK() {
            return this.mName + ":value";
        }
    }

    protected zzai(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzOy = -1;
        this.zzOz = new zza("monitoring", zzif().zzjQ(), null);
    }

    public void zzbk(String str) {
        zzic();
        zzio();
        Editor edit = this.zzOw.edit();
        if (TextUtils.isEmpty(str)) {
            edit.remove("installation_campaign");
        } else {
            edit.putString("installation_campaign", str);
        }
        if (!edit.commit()) {
            zzbb("Failed to commit campaign data");
        }
    }

    protected void zzhB() {
        this.zzOw = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
    }

    public long zzkA() {
        zzic();
        zzio();
        if (this.zzOy == -1) {
            this.zzOy = this.zzOw.getLong("last_dispatch", 0);
        }
        return this.zzOy;
    }

    public void zzkB() {
        zzic();
        zzio();
        long currentTimeMillis = zzid().currentTimeMillis();
        Editor edit = this.zzOw.edit();
        edit.putLong("last_dispatch", currentTimeMillis);
        edit.apply();
        this.zzOy = currentTimeMillis;
    }

    public String zzkC() {
        zzic();
        zzio();
        CharSequence string = this.zzOw.getString("installation_campaign", null);
        return TextUtils.isEmpty(string) ? null : string;
    }

    public zza zzkD() {
        return this.zzOz;
    }

    public long zzky() {
        zzic();
        zzio();
        if (this.zzOx == 0) {
            long j = this.zzOw.getLong("first_run", 0);
            if (j != 0) {
                this.zzOx = j;
            } else {
                j = zzid().currentTimeMillis();
                Editor edit = this.zzOw.edit();
                edit.putLong("first_run", j);
                if (!edit.commit()) {
                    zzbb("Failed to commit first run time");
                }
                this.zzOx = j;
            }
        }
        return this.zzOx;
    }

    public zzaj zzkz() {
        return new zzaj(zzid(), zzky());
    }
}
