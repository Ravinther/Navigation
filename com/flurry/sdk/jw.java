package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class jw {
    private final HashMap<String, String> f959a;

    public jw() {
        this.f959a = new HashMap();
    }

    public void m1047a(String str, String str2) {
        if (str != null) {
            this.f959a.put(str, str2);
        }
    }

    public String m1046a(String str) {
        if (str == null) {
            return null;
        }
        return (String) this.f959a.get(str);
    }

    public int m1045a() {
        return this.f959a.size();
    }

    public String m1048b() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Entry> entrySet = this.f959a.entrySet();
        if (entrySet.size() > 0) {
            for (Entry entry : entrySet) {
                stringBuilder.append(lc.m1273c((String) entry.getKey()));
                stringBuilder.append("=");
                stringBuilder.append(lc.m1273c((String) entry.getValue()));
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof jw)) {
            return false;
        }
        jw jwVar = (jw) obj;
        if (jwVar.m1045a() != m1045a()) {
            return false;
        }
        for (Entry entry : this.f959a.entrySet()) {
            String str = (String) entry.getValue();
            String a = jwVar.m1046a((String) entry.getKey());
            if (str != a && (str == null || !str.equals(a))) {
                return false;
            }
        }
        return true;
    }
}
