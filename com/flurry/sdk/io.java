package com.flurry.sdk;

import android.text.TextUtils;
import java.util.Arrays;

public class io {
    private static String f810a;

    static {
        f810a = io.class.getName();
    }

    public static String m816a(String str) {
        String str2 = "a=" + jc.m946a().m959d();
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        String str3 = "cid=" + m817b(str);
        return String.format("%s&%s", new Object[]{str2, str3});
    }

    private static String m817b(String str) {
        byte[] bArr = null;
        if (str != null && str.trim().length() > 0) {
            try {
                byte[] f = lc.m1276f(str);
                if (f == null || f.length != 20) {
                    jq.m1016a(6, f810a, "sha1 is not " + 20 + " bytes long: " + Arrays.toString(f));
                    f = null;
                } else {
                    try {
                        jq.m1016a(5, f810a, "syndication hashedId is:" + new String(f));
                    } catch (Exception e) {
                        bArr = f;
                        jq.m1016a(6, f810a, "Exception in getHashedSyndicationIdString()");
                        return lc.m1263a(bArr);
                    }
                }
                bArr = f;
            } catch (Exception e2) {
                jq.m1016a(6, f810a, "Exception in getHashedSyndicationIdString()");
                return lc.m1263a(bArr);
            }
        }
        return lc.m1263a(bArr);
    }
}
