package com.flurry.sdk;

import android.util.SparseArray;
import com.flurry.android.tumblr.Post;

public class lk {
    private static lk f1118b;
    private SparseArray<Post> f1119a;

    static {
        f1118b = null;
    }

    private lk() {
        this.f1119a = new SparseArray();
    }

    public static lk m1314a() {
        if (f1118b == null) {
            f1118b = new lk();
        }
        return f1118b;
    }

    public void m1316a(int i, Post post) {
        this.f1119a.append(i, post);
    }

    public void m1315a(int i) {
        this.f1119a.remove(i);
    }
}
