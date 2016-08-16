package com.flurry.sdk;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class jv {
    private final Pattern f629a;

    public jv() {
        this.f629a = Pattern.compile(".*?(%\\{\\w+\\}).*?");
    }

    public String m550b(String str) {
        Matcher matcher = this.f629a.matcher(str);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    protected boolean m549a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return false;
        }
        return str2.equals("%{" + str + "}");
    }
}
