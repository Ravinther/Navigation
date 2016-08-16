package com.flurry.sdk;

import com.flurry.sdk.jy.C0480c;
import java.io.InputStream;
import java.io.OutputStream;

public class jx<RequestObjectType, ResponseObjectType> extends jy {
    private C0417a<RequestObjectType, ResponseObjectType> f977a;
    private RequestObjectType f978b;
    private ResponseObjectType f979c;
    private kk<RequestObjectType> f980d;
    private kk<ResponseObjectType> f981e;

    /* renamed from: com.flurry.sdk.jx.a */
    public interface C0417a<RequestObjectType, ResponseObjectType> {
        void m558a(jx<RequestObjectType, ResponseObjectType> jxVar, ResponseObjectType responseObjectType);
    }

    /* renamed from: com.flurry.sdk.jx.1 */
    class C04811 implements C0480c {
        final /* synthetic */ jx f960a;

        C04811(jx jxVar) {
            this.f960a = jxVar;
        }

        public void m1054a(jy jyVar, OutputStream outputStream) throws Exception {
            if (this.f960a.f978b != null && this.f960a.f980d != null) {
                this.f960a.f980d.m531a(outputStream, this.f960a.f978b);
            }
        }

        public void m1053a(jy jyVar, InputStream inputStream) throws Exception {
            if (jyVar.m1078e() && this.f960a.f981e != null) {
                this.f960a.f979c = this.f960a.f981e.m532b(inputStream);
            }
        }

        public void m1052a(jy jyVar) {
            this.f960a.m1089o();
        }
    }

    public void m1093a(RequestObjectType requestObjectType) {
        this.f978b = requestObjectType;
    }

    public void m1092a(kk<RequestObjectType> kkVar) {
        this.f980d = kkVar;
    }

    public void m1094b(kk<ResponseObjectType> kkVar) {
        this.f981e = kkVar;
    }

    public void m1091a(C0417a<RequestObjectType, ResponseObjectType> c0417a) {
        this.f977a = c0417a;
    }

    public void m1090a() {
        m1088n();
        super.m1068a();
    }

    private void m1088n() {
        m1070a(new C04811(this));
    }

    private void m1089o() {
        if (this.f977a != null && !m1076c()) {
            this.f977a.m558a(this, this.f979c);
        }
    }
}
