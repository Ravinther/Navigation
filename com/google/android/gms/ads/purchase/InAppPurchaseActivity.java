package com.google.android.gms.ads.purchase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzfn;
import com.google.android.gms.internal.zzfs;

public class InAppPurchaseActivity extends Activity {
    private zzfn zzJM;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (this.zzJM != null) {
                this.zzJM.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onActivityResult to in-app purchase manager:", e);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.zzJM = zzfs.zze(this);
        if (this.zzJM == null) {
            zzb.zzaE("Could not create in-app purchase manager.");
            finish();
            return;
        }
        try {
            this.zzJM.onCreate();
        } catch (Throwable e) {
            zzb.zzd("Could not forward onCreate to in-app purchase manager:", e);
            finish();
        }
    }

    protected void onDestroy() {
        try {
            if (this.zzJM != null) {
                this.zzJM.onDestroy();
            }
        } catch (Throwable e) {
            zzb.zzd("Could not forward onDestroy to in-app purchase manager:", e);
        }
        super.onDestroy();
    }
}
