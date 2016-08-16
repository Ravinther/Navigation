package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhl;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public class zzi {

    /* renamed from: com.google.android.gms.ads.internal.purchase.zzi.1 */
    class C05871 implements ServiceConnection {
        final /* synthetic */ zzi zzCn;
        final /* synthetic */ Context zzrn;

        C05871(zzi com_google_android_gms_ads_internal_purchase_zzi, Context context) {
            this.zzCn = com_google_android_gms_ads_internal_purchase_zzi;
            this.zzrn = context;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            boolean z = false;
            zzb com_google_android_gms_ads_internal_purchase_zzb = new zzb(this.zzrn.getApplicationContext(), false);
            com_google_android_gms_ads_internal_purchase_zzb.zzM(service);
            int zza = com_google_android_gms_ads_internal_purchase_zzb.zza(3, this.zzrn.getPackageName(), "inapp");
            zzhl zzbA = zzp.zzbA();
            if (zza == 0) {
                z = true;
            }
            zzbA.zzB(z);
            this.zzrn.unbindService(this);
            com_google_android_gms_ads_internal_purchase_zzb.destroy();
        }

        public void onServiceDisconnected(ComponentName name) {
        }
    }

    public void zza(Context context, boolean z, GInAppPurchaseManagerInfoParcel gInAppPurchaseManagerInfoParcel) {
        Intent intent = new Intent();
        intent.setClassName(context, "com.google.android.gms.ads.purchase.InAppPurchaseActivity");
        intent.putExtra("com.google.android.gms.ads.internal.purchase.useClientJar", z);
        GInAppPurchaseManagerInfoParcel.zza(intent, gInAppPurchaseManagerInfoParcel);
        context.startActivity(intent);
    }

    public String zzal(String str) {
        String str2 = null;
        if (str != null) {
            try {
                str2 = new JSONObject(str).getString("developerPayload");
            } catch (JSONException e) {
                zzb.zzaE("Fail to parse purchase data");
            }
        }
        return str2;
    }

    public String zzam(String str) {
        String str2 = null;
        if (str != null) {
            try {
                str2 = new JSONObject(str).getString("purchaseToken");
            } catch (JSONException e) {
                zzb.zzaE("Fail to parse purchase data");
            }
        }
        return str2;
    }

    public int zzc(Bundle bundle) {
        Object obj = bundle.get("RESPONSE_CODE");
        if (obj == null) {
            zzb.zzaE("Bundle with null response code, assuming OK (known issue)");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            if (obj instanceof Long) {
                return (int) ((Long) obj).longValue();
            }
            zzb.zzaE("Unexpected type for intent response code. " + obj.getClass().getName());
            return 5;
        }
    }

    public int zzd(Intent intent) {
        if (intent == null) {
            return 5;
        }
        Object obj = intent.getExtras().get("RESPONSE_CODE");
        if (obj == null) {
            zzb.zzaE("Intent with no response code, assuming OK (known issue)");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            if (obj instanceof Long) {
                return (int) ((Long) obj).longValue();
            }
            zzb.zzaE("Unexpected type for intent response code. " + obj.getClass().getName());
            return 5;
        }
    }

    public String zze(Intent intent) {
        return intent == null ? null : intent.getStringExtra("INAPP_PURCHASE_DATA");
    }

    public String zzf(Intent intent) {
        return intent == null ? null : intent.getStringExtra("INAPP_DATA_SIGNATURE");
    }

    public void zzy(Context context) {
        ServiceConnection c05871 = new C05871(this, context);
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        context.bindService(intent, c05871, 1);
    }
}
