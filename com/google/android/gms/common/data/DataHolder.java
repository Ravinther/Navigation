package com.google.android.gms.common.data;

import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.HashMap;

public final class DataHolder implements SafeParcelable {
    public static final zze CREATOR;
    private static final zza zzabs;
    boolean mClosed;
    private final int mVersionCode;
    private final int zzWu;
    private final String[] zzabk;
    Bundle zzabl;
    private final CursorWindow[] zzabm;
    private final Bundle zzabn;
    int[] zzabo;
    int zzabp;
    private Object zzabq;
    private boolean zzabr;

    public static class zza {
        private final String[] zzabk;
        private final ArrayList<HashMap<String, Object>> zzabt;
        private final String zzabu;
        private final HashMap<Object, Integer> zzabv;
        private boolean zzabw;
        private String zzabx;

        private zza(String[] strArr, String str) {
            this.zzabk = (String[]) zzx.zzv(strArr);
            this.zzabt = new ArrayList();
            this.zzabu = str;
            this.zzabv = new HashMap();
            this.zzabw = false;
            this.zzabx = null;
        }
    }

    /* renamed from: com.google.android.gms.common.data.DataHolder.1 */
    static class C06471 extends zza {
        C06471(String[] strArr, String str) {
            super(str, null);
        }
    }

    static {
        CREATOR = new zze();
        zzabs = new C06471(new String[0], null);
    }

    DataHolder(int versionCode, String[] columns, CursorWindow[] windows, int statusCode, Bundle metadata) {
        this.mClosed = false;
        this.zzabr = true;
        this.mVersionCode = versionCode;
        this.zzabk = columns;
        this.zzabm = windows;
        this.zzWu = statusCode;
        this.zzabn = metadata;
    }

    private void zzh(String str, int i) {
        if (this.zzabl == null || !this.zzabl.containsKey(str)) {
            throw new IllegalArgumentException("No such column: " + str);
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.zzabp) {
            throw new CursorIndexOutOfBoundsException(i, this.zzabp);
        }
    }

    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.zzabm) {
                    close.close();
                }
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.zzabr && this.zzabm.length > 0 && !isClosed()) {
                Log.e("DataBuffer", "Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (" + (this.zzabq == null ? "internal object: " + toString() : this.zzabq.toString()) + ")");
                close();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public int getCount() {
        return this.zzabp;
    }

    public int getStatusCode() {
        return this.zzWu;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zze.zza(this, dest, flags);
    }

    public int zzbo(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.zzabp;
        zzx.zzY(z);
        while (i2 < this.zzabo.length) {
            if (i < this.zzabo[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.zzabo.length ? i2 - 1 : i2;
    }

    public String zzd(String str, int i, int i2) {
        zzh(str, i);
        return this.zzabm[i2].getString(i, this.zzabl.getInt(str));
    }

    public Bundle zznP() {
        return this.zzabn;
    }

    public void zznT() {
        int i;
        int i2 = 0;
        this.zzabl = new Bundle();
        for (i = 0; i < this.zzabk.length; i++) {
            this.zzabl.putInt(this.zzabk[i], i);
        }
        this.zzabo = new int[this.zzabm.length];
        i = 0;
        while (i2 < this.zzabm.length) {
            this.zzabo[i2] = i;
            i += this.zzabm[i2].getNumRows() - (i - this.zzabm[i2].getStartPosition());
            i2++;
        }
        this.zzabp = i;
    }

    String[] zznU() {
        return this.zzabk;
    }

    CursorWindow[] zznV() {
        return this.zzabm;
    }

    public void zzq(Object obj) {
        this.zzabq = obj;
    }
}
