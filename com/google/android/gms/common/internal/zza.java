package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class zza extends com.google.android.gms.common.internal.zzp.zza {
    private Context mContext;
    private Account zzOY;
    int zzacB;

    public static Account zzb(zzp com_google_android_gms_common_internal_zzp) {
        Account account = null;
        if (com_google_android_gms_common_internal_zzp != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                account = com_google_android_gms_common_internal_zzp.getAccount();
            } catch (RemoteException e) {
                Log.w("AccountAccessor", "Remote account accessor probably died");
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return account;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return !(o instanceof zza) ? false : this.zzOY.equals(((zza) o).zzOY);
    }

    public Account getAccount() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == this.zzacB) {
            return this.zzOY;
        }
        if (GooglePlayServicesUtil.zze(this.mContext, callingUid)) {
            this.zzacB = callingUid;
            return this.zzOY;
        }
        throw new SecurityException("Caller is not GooglePlayServices");
    }
}
