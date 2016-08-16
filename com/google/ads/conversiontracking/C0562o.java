package com.google.ads.conversiontracking;

import android.content.Intent;
import android.net.Uri;

/* renamed from: com.google.ads.conversiontracking.o */
public class C0562o {
    private static final Uri f1232a;
    private static final Uri f1233b;

    static {
        f1232a = Uri.parse("http://plus.google.com/");
        f1233b = f1232a.buildUpon().appendPath("circles").appendPath("find").build();
    }

    public static Intent m1425a() {
        return new Intent("android.settings.DATE_SETTINGS");
    }

    public static Intent m1426a(String str) {
        Uri fromParts = Uri.fromParts("package", str, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(fromParts);
        return intent;
    }

    public static Intent m1427b(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(C0562o.m1429d(str));
        intent.setPackage("com.android.vending");
        intent.addFlags(524288);
        return intent;
    }

    public static Intent m1428c(String str) {
        Uri parse = Uri.parse("bazaar://search?q=pname:" + str);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(parse);
        intent.setFlags(524288);
        return intent;
    }

    private static Uri m1429d(String str) {
        return Uri.parse("market://details").buildUpon().appendQueryParameter("id", str).build();
    }
}
