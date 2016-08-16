package com.bosch.myspin.serversdk.resource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;

public class ResourceLoader {
    public static native Bitmap loadBitmapJNI(int i, Options options);

    public static native byte[] loadByteArrayJNI(int i);

    public static byte[] m139a(int i) {
        try {
            return loadByteArrayJNI(i);
        } catch (UnsatisfiedLinkError e) {
            if (!"Dalvik".equals(System.getProperty("java.vm.name"))) {
                return null;
            }
            throw e;
        }
    }
}
