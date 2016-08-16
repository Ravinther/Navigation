package com.flurry.android.tumblr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.sdk.ej;
import com.flurry.sdk.ff;
import com.flurry.sdk.gz;
import com.flurry.sdk.hi;
import com.flurry.sdk.jc;
import com.flurry.sdk.jq;
import com.flurry.sdk.lk;
import java.util.HashMap;

public class TumblrShare {
    private static final String f559a;

    static {
        f559a = TumblrShare.class.getName();
    }

    public static Bitmap getTumblrImage() {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f559a, "Device SDK Version older than 10");
            return null;
        } else if (jc.m946a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized.");
        } else {
            gz gzVar = new gz();
            gzVar.r();
            return gzVar.o();
        }
    }

    public static void setOAuthConfig(String str, String str2) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f559a, "Device SDK Version older than 10");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Consumer api key cannot be null or empty.");
        } else if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("Consumer secret cannot be null or empty.");
        } else {
            ej.a(str);
            ej.b(str2);
        }
    }

    public static void post(Context context, Post post) {
        if (VERSION.SDK_INT < 10) {
            jq.m1024b(f559a, "Device SDK Version older than 10");
        } else if (jc.m946a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before posting on Tumblr");
        } else if (context == null) {
            throw new IllegalArgumentException("Context cannot be null.");
        } else if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("Context cannot be null.");
        } else if (post == null) {
            throw new IllegalArgumentException("Post object cannot be null.");
        } else if (TextUtils.isEmpty(ej.a())) {
            throw new IllegalArgumentException("Consumer api key cannot be null or empty. Please set consumer key using setOAuthConfig().");
        } else if (TextUtils.isEmpty(ej.b())) {
            throw new IllegalArgumentException("Consumer secret cannot be null or empty. Please set consumer secret using setOAuthConfig().");
        } else {
            hi.m500a().m512b("ShareClick", post.m484d(), new HashMap());
            lk.m1314a().m1316a(post.m487g(), post);
            ff.a(context, post.m483c());
        }
    }
}
