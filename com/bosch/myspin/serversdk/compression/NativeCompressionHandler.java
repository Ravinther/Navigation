package com.bosch.myspin.serversdk.compression;

import android.graphics.Bitmap;
import com.bosch.myspin.serversdk.compression.C0163a.C0162a;

public class NativeCompressionHandler implements C0162a {
    private static NativeCompressionHandler f73a;

    private static native int compressAndShareNative(Bitmap bitmap, long j, int i, int i2, int i3, int i4, int i5);

    private NativeCompressionHandler() {
    }

    public static NativeCompressionHandler m88a() {
        if (f73a == null) {
            f73a = new NativeCompressionHandler();
        }
        return f73a;
    }

    public int m89a(Bitmap bitmap, long j, int i, int i2, int i3, int i4, int i5) {
        return compressAndShareNative(bitmap, j, i, i2, i4, i3, i5);
    }
}
