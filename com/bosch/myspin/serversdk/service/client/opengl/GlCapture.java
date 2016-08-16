package com.bosch.myspin.serversdk.service.client.opengl;

import android.graphics.Bitmap;

public class GlCapture {
    public static native int capture(Bitmap bitmap);

    public static native int detectFormat();
}
