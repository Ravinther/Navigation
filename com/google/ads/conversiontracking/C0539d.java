package com.google.ads.conversiontracking;

import com.google.ads.conversiontracking.C0552g.C0550c;

/* renamed from: com.google.ads.conversiontracking.d */
public class C0539d {
    public final boolean f1154a;
    public final boolean f1155b;
    public final int f1156c;
    public final long f1157d;
    public final String f1158e;
    public final String f1159f;
    public final String f1160g;
    public long f1161h;

    public C0539d(String str, C0550c c0550c, boolean z, boolean z2) {
        this.f1160g = str;
        this.f1155b = z2;
        this.f1154a = z;
        this.f1161h = 0;
        this.f1157d = C0552g.m1381a();
        this.f1156c = 0;
        if (z2 || !z) {
            this.f1159f = null;
            this.f1158e = null;
            return;
        }
        this.f1159f = C0552g.m1402b(c0550c);
        this.f1158e = C0552g.m1389a(c0550c);
    }

    public C0539d(long j, String str, String str2, boolean z, boolean z2, String str3, long j2, int i) {
        this.f1161h = j;
        this.f1160g = str;
        this.f1159f = str2;
        this.f1155b = z;
        this.f1154a = z2;
        this.f1158e = str3;
        this.f1157d = j2;
        this.f1156c = i;
    }
}
