package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

public class zzh implements OnClickListener {
    private final Activity mActivity;
    private final Intent mIntent;
    private final Fragment zzadh;
    private final int zzadi;

    public zzh(Activity activity, Intent intent, int i) {
        this.mActivity = activity;
        this.zzadh = null;
        this.mIntent = intent;
        this.zzadi = i;
    }

    public zzh(Fragment fragment, Intent intent, int i) {
        this.mActivity = null;
        this.zzadh = fragment;
        this.mIntent = intent;
        this.zzadi = i;
    }

    public void onClick(DialogInterface dialog, int which) {
        try {
            if (this.mIntent != null && this.zzadh != null) {
                this.zzadh.startActivityForResult(this.mIntent, this.zzadi);
            } else if (this.mIntent != null) {
                this.mActivity.startActivityForResult(this.mIntent, this.zzadi);
            }
            dialog.dismiss();
        } catch (ActivityNotFoundException e) {
            Log.e("SettingsRedirect", "Can't redirect to app settings for Google Play services");
        }
    }
}
