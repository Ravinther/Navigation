package com.flurry.android.tumblr;

import android.os.Bundle;
import android.text.TextUtils;
import com.flurry.sdk.dz;
import com.flurry.sdk.dz.a;
import com.flurry.sdk.jc;
import com.flurry.sdk.jk;
import com.flurry.sdk.jl;
import com.flurry.sdk.jm;
import com.flurry.sdk.jq;
import com.flurry.sdk.le;
import com.flurry.sdk.lk;
import java.util.concurrent.atomic.AtomicInteger;
import loquendo.tts.engine.TTSConst;

public abstract class Post {
    private static final String f539a;
    private static AtomicInteger f540g;
    private String f541b;
    private String f542c;
    private String f543d;
    private PostListener f544e;
    private int f545f;
    private final jl<dz> f546h;

    /* renamed from: com.flurry.android.tumblr.Post.1 */
    class C04111 implements jl<dz> {
        final /* synthetic */ Post f555a;

        /* renamed from: com.flurry.android.tumblr.Post.1.1 */
        class C04101 extends le {
            final /* synthetic */ dz f552a;
            final /* synthetic */ PostListener f553b;
            final /* synthetic */ C04111 f554c;

            C04101(C04111 c04111, dz dzVar, PostListener postListener) {
                this.f554c = c04111;
                this.f552a = dzVar;
                this.f553b = postListener;
            }

            public void m492a() {
                switch (C04122.f556a[this.f552a.b.ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        jq.m1016a(3, Post.f539a, "Post success for " + this.f552a.c);
                        this.f553b.onPostSuccess(this.f552a.f);
                        jm.m997a().m1004b("com.flurry.android.impl.analytics.tumblr.TumblrEvents", this.f554c.f555a.f546h);
                        lk.m1314a().m1315a(this.f554c.f555a.f545f);
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        String str = this.f552a.e;
                        if (TextUtils.isEmpty(str)) {
                            str = "Internal error.";
                        }
                        jq.m1016a(3, Post.f539a, "Post failed for " + this.f552a.c + " with error code: " + this.f552a.d + "  and error message: " + str);
                        this.f553b.onPostFailure(str);
                        jm.m997a().m1004b("com.flurry.android.impl.analytics.tumblr.TumblrEvents", this.f554c.f555a.f546h);
                        lk.m1314a().m1315a(this.f554c.f555a.f545f);
                    default:
                }
            }
        }

        C04111(Post post) {
            this.f555a = post;
        }

        public /* synthetic */ void m494a(jk jkVar) {
            m493a((dz) jkVar);
        }

        public void m493a(dz dzVar) {
            if (dzVar.c == this.f555a.f545f && dzVar.b != null) {
                PostListener b = this.f555a.f544e;
                if (b != null) {
                    jc.m946a().m953a(new C04101(this, dzVar, b));
                }
            }
        }
    }

    /* renamed from: com.flurry.android.tumblr.Post.2 */
    static /* synthetic */ class C04122 {
        static final /* synthetic */ int[] f556a;

        static {
            f556a = new int[a.values().length];
            try {
                f556a[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f556a[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    abstract Bundle m483c();

    static {
        f539a = Post.class.getName();
        f540g = new AtomicInteger(0);
    }

    Post() {
        this.f545f = 0;
        this.f546h = new C04111(this);
        this.f545f = f540g.incrementAndGet();
        jm.m997a().m1002a("com.flurry.android.impl.analytics.tumblr.TumblrEvents", this.f546h);
    }

    public void setAndroidDeeplink(String str) {
        this.f542c = str;
    }

    public void setIOSDeepLink(String str) {
        this.f541b = str;
    }

    public void setWebLink(String str) {
        this.f543d = str;
    }

    public void setPostListener(PostListener postListener) {
        this.f544e = postListener;
    }

    String m484d() {
        return this.f542c;
    }

    String m485e() {
        return this.f541b;
    }

    String m486f() {
        return this.f543d;
    }

    int m487g() {
        return this.f545f;
    }
}
