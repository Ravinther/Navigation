package com.google.android.gms.internal;

import com.google.android.gms.internal.zzqp.zzc;
import com.google.android.gms.internal.zzqp.zzg;
import com.google.android.gms.tagmanager.zzbg;
import org.json.JSONException;

public final class zzqm {
    public static zzql zzaTG;

    /* renamed from: com.google.android.gms.internal.zzqm.1 */
    static class C09811 implements zzql {
        C09811() {
        }

        public Object zzt(byte[] bArr) throws zzg {
            if (bArr == null) {
                throw new zzg("Cannot parse a null byte[]");
            } else if (bArr.length == 0) {
                throw new zzg("Cannot parse a 0 length byte[]");
            } else {
                try {
                    zzc zzeN = zzqj.zzeN(new String(bArr));
                    if (zzeN != null) {
                        zzbg.m1448v("The container was successfully parsed from the resource");
                    }
                    return zzeN;
                } catch (JSONException e) {
                    throw new zzg("The resource data is corrupted. The container cannot be extracted from the binary data");
                } catch (zzg e2) {
                    throw new zzg("The resource data is invalid. The container cannot be extracted from the binary data");
                }
            }
        }
    }

    static {
        zzaTG = new C09811();
    }
}
