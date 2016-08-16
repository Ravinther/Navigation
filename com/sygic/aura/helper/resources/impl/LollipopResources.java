package com.sygic.aura.helper.resources.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.ExtendedTypedArray;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import android.util.TypedValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

@TargetApi(21)
public class LollipopResources extends KitKatResources {
    public LollipopResources(Context context) throws Exception {
        super(context);
    }

    protected void setContextBaseObject(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
    }

    protected void init() throws NoSuchFieldException {
        super.init();
        this.sfTraceForMissPreload = Resources.class.getDeclaredField("TRACE_FOR_MISS_PRELOAD");
        this.sfTraceForMissPreload.setAccessible(true);
    }

    public Drawable getDrawable(int id, Theme theme) throws NotFoundException {
        try {
            TypedValue value;
            synchronized (this.mfAccessLock.get(this)) {
                value = (TypedValue) this.mfTmpValue.get(this);
                if (value == null) {
                    value = new TypedValue();
                } else {
                    this.mfTmpValue.set(this, null);
                }
                getValue(id, value, true);
            }
            Drawable res = loadDrawable(value, id, theme);
            synchronized (this.mfAccessLock.get(this)) {
                if (this.mfTmpValue.get(this) == null) {
                    this.mfTmpValue.set(this, value);
                }
            }
            return res;
        } catch (IllegalAccessException e) {
            throw new NotFoundException("IllegalAccessException");
        }
    }

    protected Drawable newConstantStateDrawable(ConstantState cs, Theme theme) {
        return cs.newDrawable(this, theme);
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
            String themeKey;
            Map<String, LongSparseArray<WeakReference<ConstantState>>> caches;
            try {
                Field f = theme.getClass().getDeclaredField("mKey");
                f.setAccessible(true);
                themeKey = (String) f.get(theme);
            } catch (Exception e) {
                themeKey = "";
            }
            if (isColorDrawable) {
                caches = (Map) this.mfColorDrawableCache.get(this);
            } else {
                caches = (Map) this.mfDrawableCache.get(this);
            }
            LongSparseArray<WeakReference<ConstantState>> themedCache = (LongSparseArray) caches.get(themeKey);
            if (themedCache == null) {
                themedCache = new LongSparseArray(1);
                caches.put(themeKey, themedCache);
            }
            themedCache.put(key, new WeakReference(cs));
        }
    }

    protected TypedArray retrieveTypedArray(Resources resources, int len) {
        return ExtendedTypedArray.obtain(resources, len);
    }

    protected Method getAMRetrieveAttributes() throws NoSuchMethodException {
        return AssetManager.class.getDeclaredMethod("retrieveAttributes", new Class[]{Long.TYPE, int[].class, int[].class, int[].class});
    }

    protected Drawable getCachedDrawableReflect(boolean isColorDrawable, long key, Theme theme) {
        try {
            Method method = Resources.class.getDeclaredMethod("getCachedDrawable", new Class[]{ArrayMap.class, Long.TYPE, Theme.class});
            method.setAccessible(true);
            Object[] objArr = new Object[3];
            objArr[0] = isColorDrawable ? this.mfColorDrawableCache.get(this) : this.mfDrawableCache.get(this);
            objArr[1] = Long.valueOf(key);
            objArr[2] = theme;
            return (Drawable) method.invoke(this, objArr);
        } catch (Exception e) {
            return tryHTCCache(isColorDrawable, key, theme);
        }
    }

    protected Drawable tryHTCCache(boolean isColorDrawable, long key, Theme theme) {
        try {
            Method method = Resources.class.getDeclaredMethod("getCachedDrawable", new Class[]{Class.forName("android.content.res.Resources$ArrayMap"), Long.TYPE, Theme.class});
            method.setAccessible(true);
            Object[] objArr = new Object[3];
            objArr[0] = isColorDrawable ? this.mfColorDrawableCache.get(this) : this.mfDrawableCache.get(this);
            objArr[1] = Long.valueOf(key);
            objArr[2] = theme;
            return (Drawable) method.invoke(this, objArr);
        } catch (Exception e) {
            return null;
        }
    }
}
