package com.flurry.sdk;

public class hz extends jv {
    private static final String f630a;

    static {
        f630a = hz.class.getSimpleName();
    }

    public String m552a(String str) {
        String b = m550b(str);
        while (b != null) {
            str = m551b(str, b);
            b = m550b(str);
        }
        return str;
    }

    private String m551b(String str, String str2) {
        String valueOf;
        if (m549a("timestamp_epoch_millis", str2)) {
            valueOf = String.valueOf(System.currentTimeMillis());
            jq.m1016a(3, f630a, "Replacing param timestamp_epoch_millis with: " + valueOf);
            return str.replace(str2, lc.m1273c(valueOf));
        } else if (m549a("session_duration_millis", str2)) {
            valueOf = Long.toString(ip.m818a().m826f());
            jq.m1016a(3, f630a, "Replacing param session_duration_millis with: " + valueOf);
            return str.replace(str2, lc.m1273c(valueOf));
        } else if (m549a("fg_timespent_millis", str2)) {
            valueOf = Long.toString(ip.m818a().m826f());
            jq.m1016a(3, f630a, "Replacing param fg_timespent_millis with: " + valueOf);
            return str.replace(str2, lc.m1273c(valueOf));
        } else {
            jq.m1016a(3, f630a, "Unknown param: " + str2);
            return str.replace(str2, "");
        }
    }
}
