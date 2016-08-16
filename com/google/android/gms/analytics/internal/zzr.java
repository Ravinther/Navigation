package com.google.android.gms.analytics.internal;

import android.content.pm.ApplicationInfo;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzlw;
import java.util.HashSet;
import java.util.Set;

public class zzr {
    private final zzf zzKa;
    private volatile Boolean zzMN;
    private String zzMO;
    private Set<Integer> zzMP;

    protected zzr(zzf com_google_android_gms_analytics_internal_zzf) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzf);
        this.zzKa = com_google_android_gms_analytics_internal_zzf;
    }

    public String zzjA() {
        return (String) zzy.zzNq.get();
    }

    public String zzjB() {
        return (String) zzy.zzNr.get();
    }

    public zzm zzjC() {
        return zzm.zzbh((String) zzy.zzNt.get());
    }

    public zzo zzjD() {
        return zzo.zzbi((String) zzy.zzNu.get());
    }

    public Set<Integer> zzjE() {
        String str = (String) zzy.zzNz.get();
        if (this.zzMP == null || this.zzMO == null || !this.zzMO.equals(str)) {
            String[] split = TextUtils.split(str, ",");
            Set hashSet = new HashSet();
            for (String parseInt : split) {
                try {
                    hashSet.add(Integer.valueOf(Integer.parseInt(parseInt)));
                } catch (NumberFormatException e) {
                }
            }
            this.zzMO = str;
            this.zzMP = hashSet;
        }
        return this.zzMP;
    }

    public long zzjF() {
        return ((Long) zzy.zzNI.get()).longValue();
    }

    public long zzjG() {
        return ((Long) zzy.zzNJ.get()).longValue();
    }

    public long zzjH() {
        return ((Long) zzy.zzNM.get()).longValue();
    }

    public int zzjI() {
        return ((Integer) zzy.zzNd.get()).intValue();
    }

    public int zzjJ() {
        return ((Integer) zzy.zzNf.get()).intValue();
    }

    public String zzjK() {
        return "google_analytics_v4.db";
    }

    public String zzjL() {
        return "google_analytics2_v4.db";
    }

    public long zzjM() {
        return 86400000;
    }

    public int zzjN() {
        return ((Integer) zzy.zzNC.get()).intValue();
    }

    public int zzjO() {
        return ((Integer) zzy.zzND.get()).intValue();
    }

    public long zzjP() {
        return ((Long) zzy.zzNE.get()).longValue();
    }

    public long zzjQ() {
        return ((Long) zzy.zzNN.get()).longValue();
    }

    public boolean zzjk() {
        return zzd.zzacF;
    }

    public boolean zzjl() {
        if (this.zzMN == null) {
            synchronized (this) {
                if (this.zzMN == null) {
                    ApplicationInfo applicationInfo = this.zzKa.getContext().getApplicationInfo();
                    String zzj = zzlw.zzj(this.zzKa.getContext(), Process.myPid());
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        boolean z = str != null && str.equals(zzj);
                        this.zzMN = Boolean.valueOf(z);
                    }
                    if ((this.zzMN == null || !this.zzMN.booleanValue()) && "com.google.android.gms.analytics".equals(zzj)) {
                        this.zzMN = Boolean.TRUE;
                    }
                    if (this.zzMN == null) {
                        this.zzMN = Boolean.TRUE;
                        this.zzKa.zzie().zzbc("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzMN.booleanValue();
    }

    public boolean zzjm() {
        return ((Boolean) zzy.zzMZ.get()).booleanValue();
    }

    public int zzjn() {
        return ((Integer) zzy.zzNs.get()).intValue();
    }

    public int zzjo() {
        return ((Integer) zzy.zzNw.get()).intValue();
    }

    public int zzjp() {
        return ((Integer) zzy.zzNx.get()).intValue();
    }

    public int zzjq() {
        return ((Integer) zzy.zzNy.get()).intValue();
    }

    public long zzjr() {
        return ((Long) zzy.zzNh.get()).longValue();
    }

    public long zzjs() {
        return ((Long) zzy.zzNg.get()).longValue();
    }

    public long zzjt() {
        return ((Long) zzy.zzNk.get()).longValue();
    }

    public long zzju() {
        return ((Long) zzy.zzNl.get()).longValue();
    }

    public int zzjv() {
        return ((Integer) zzy.zzNm.get()).intValue();
    }

    public int zzjw() {
        return ((Integer) zzy.zzNn.get()).intValue();
    }

    public long zzjx() {
        return (long) ((Integer) zzy.zzNA.get()).intValue();
    }

    public String zzjy() {
        return (String) zzy.zzNp.get();
    }

    public String zzjz() {
        return (String) zzy.zzNo.get();
    }
}
