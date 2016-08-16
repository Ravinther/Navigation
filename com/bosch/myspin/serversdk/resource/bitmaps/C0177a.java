package com.bosch.myspin.serversdk.resource.bitmaps;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;
import com.bosch.myspin.serversdk.resource.ResourceLoader;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.HashMap;

/* renamed from: com.bosch.myspin.serversdk.resource.bitmaps.a */
public class C0177a extends ResourceLoader {
    private static HashMap<Integer, Bitmap> f142a;

    static {
        f142a = new HashMap();
    }

    public static Bitmap m140a(Resources resources, int i) {
        try {
            if (f142a.containsKey(Integer.valueOf(i))) {
                return (Bitmap) f142a.get(Integer.valueOf(i));
            }
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Options options = new Options();
            options.inScaled = true;
            options.inDensity = 240;
            options.inTargetDensity = displayMetrics.densityDpi;
            return ResourceLoader.loadBitmapJNI(i, options);
        } catch (Throwable e) {
            if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
                Logger.logError(LogComponent.UI, "BitmapLoader/loadBitmap cannot load bitmap: " + i, e);
            }
            return null;
        }
    }
}
