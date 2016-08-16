package com.sygic.aura.helper.resources.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.util.LongSparseArray;
import android.util.TypedValue;
import com.sygic.aura.helper.CrashlyticsHelper;

@TargetApi(23)
public class MarshmallowResources extends LollipopMr1Resources {
    public MarshmallowResources(Context context) throws Exception {
        super(context);
    }

    protected void init() throws NoSuchFieldException {
        this.sfTraceForPreload = Resources.class.getDeclaredField("TRACE_FOR_PRELOAD");
        this.sfTraceForPreload.setAccessible(true);
        this.sfPreloadedDrawables = Resources.class.getDeclaredField("sPreloadedDrawables");
        this.sfPreloadedDrawables.setAccessible(true);
        this.mfDrawableCache = Resources.class.getDeclaredField("mDrawableCache");
        this.mfDrawableCache.setAccessible(true);
        this.mfConfiguration = Resources.class.getDeclaredField("mConfiguration");
        this.mfConfiguration.setAccessible(true);
        this.mfPreloading = Resources.class.getDeclaredField("mPreloading");
        this.mfPreloading.setAccessible(true);
        this.sfPreloadedColorDrawables = Resources.class.getDeclaredField("sPreloadedColorDrawables");
        this.sfPreloadedColorDrawables.setAccessible(true);
        this.mfColorDrawableCache = Resources.class.getDeclaredField("mColorDrawableCache");
        this.mfColorDrawableCache.setAccessible(true);
        this.sfLayoutDirConfig = Resources.class.getDeclaredField("LAYOUT_DIR_CONFIG");
        this.sfLayoutDirConfig.setAccessible(true);
        this.mfAccessLock = Resources.class.getDeclaredField("mAccessLock");
        this.mfAccessLock.setAccessible(true);
        this.mfTmpValue = Resources.class.getDeclaredField("mTmpValue");
        this.mfTmpValue.setAccessible(true);
        this.sfTraceForMissPreload = Resources.class.getDeclaredField("TRACE_FOR_MISS_PRELOAD");
        this.sfTraceForMissPreload.setAccessible(true);
    }

    protected void putDrawable(TypedValue value, Theme theme, boolean isColorDrawable, ConstantState cs, long key) throws IllegalAccessException {
        if (this.mfPreloading.getBoolean(this)) {
            int changingConfigs = cs.getChangingConfigurations();
            if (isColorDrawable) {
                if (verifyPreloadConfigReflect(changingConfigs, 0, value.resourceId, "drawable")) {
                    ((LongSparseArray) this.sfPreloadedColorDrawables.get(null)).put(key, cs);
                    return;
                }
                return;
            } else if (!verifyPreloadConfigReflect(changingConfigs, this.sfLayoutDirConfig.getInt(null), value.resourceId, "drawable")) {
                return;
            } else {
                if ((this.sfLayoutDirConfig.getInt(null) & changingConfigs) == 0) {
                    ((LongSparseArray[]) this.sfPreloadedDrawables.get(null))[0].put(key, cs);
                    ((LongSparseArray[]) this.sfPreloadedDrawables.get(null))[1].put(key, cs);
                    return;
                }
                ((LongSparseArray[]) this.sfPreloadedDrawables.get(null))[((Configuration) this.mfConfiguration.get(this)).getLayoutDirection()].put(key, cs);
                return;
            }
        }
        synchronized (this.mfAccessLock.get(this)) {
            Object cache;
            if (isColorDrawable) {
                try {
                    cache = this.mfColorDrawableCache.get(this);
                } catch (Exception e) {
                    CrashlyticsHelper.logException("MPreviewResources", e.getMessage(), e);
                }
            } else {
                cache = this.mfDrawableCache.get(this);
            }
            cache.getClass().getMethod("put", new Class[]{Long.TYPE, Theme.class, Object.class}).invoke(cache, new Object[]{Long.valueOf(key), theme, cs});
        }
    }

    protected Drawable getCachedDrawableReflect(boolean isColorDrawable, long key, Theme theme) {
        try {
            return (Drawable) Class.forName("android.content.res.DrawableCache").getDeclaredMethod("getInstance", new Class[]{Long.TYPE, Theme.class}).invoke(isColorDrawable ? this.mfColorDrawableCache.get(this) : this.mfDrawableCache.get(this), new Object[]{Long.valueOf(key), theme});
        } catch (Exception e) {
            return tryHTCCache(isColorDrawable, key, theme);
        }
    }
}
