package com.sygic.aura.resources;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;

public class Typefaces {
    private static final SparseArray<Typeface> fontsCache;

    static {
        fontsCache = new SparseArray(2);
    }

    public static Typeface getFont(Context context, int assetPathId) {
        return getFont(context.getResources(), assetPathId);
    }

    public static Typeface getFont(Resources resources, int assetPathId) {
        Typeface typeface;
        synchronized (fontsCache) {
            if (fontsCache.indexOfKey(assetPathId) < 0) {
                if (assetPathId == 2131166102) {
                    try {
                        fontsCache.put(assetPathId, Typeface.createFromFile(ResourceManager.nativeGetResourcePath() + "/fonts/" + resources.getString(assetPathId)));
                    } catch (Exception e) {
                        Log.e("Typefaces", "Could not get typeface  ", e);
                        typeface = null;
                    }
                } else {
                    String str;
                    SparseArray sparseArray = fontsCache;
                    AssetManager assets = resources.getAssets();
                    if (assetPathId <= 0) {
                        str = "icons.ttf";
                    } else {
                        str = resources.getString(assetPathId);
                    }
                    sparseArray.put(assetPathId, Typeface.createFromAsset(assets, str));
                }
            }
            typeface = (Typeface) fontsCache.get(assetPathId);
        }
        return typeface;
    }
}
