package com.sygic.aura.helper.resources.impl;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.util.LongSparseArray;
import android.util.TypedValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HoneyCombResources extends LegacyResources {
    public HoneyCombResources(Context context) throws Exception {
        super(context);
    }

    protected void init() throws NoSuchFieldException {
        super.init();
        this.sfPreloadedColorDrawables = Resources.class.getDeclaredField("sPreloadedColorDrawables");
        this.sfPreloadedColorDrawables.setAccessible(true);
        this.mfColorDrawableCache = Resources.class.getDeclaredField("mColorDrawableCache");
        this.mfColorDrawableCache.setAccessible(true);
    }

    protected Object getLoadedApk(Context context, Object activityThread) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (context.getApplicationInfo() == null) {
            return null;
        }
        Method method = activityThread.getClass().getDeclaredMethod("peekPackageInfo", new Class[]{String.class, Boolean.TYPE});
        method.setAccessible(true);
        return method.invoke(activityThread, new Object[]{ai.packageName, Boolean.valueOf(true)});
    }

    protected void putDrawable(TypedValue value, Theme theme, boolean isColorDrawable, ConstantState cs, long key) throws IllegalAccessException {
        if (!this.mfPreloading.getBoolean(this)) {
            synchronized (this.mfAccessLock.get(this)) {
                if (isColorDrawable) {
                    ((LongSparseArray) this.mfColorDrawableCache.get(this)).put(key, new WeakReference(cs));
                } else {
                    ((LongSparseArray) this.mfDrawableCache.get(this)).put(key, new WeakReference(cs));
                }
            }
        } else if (isColorDrawable) {
            ((LongSparseArray) this.sfPreloadedColorDrawables.get(null)).put(key, cs);
        } else {
            ((LongSparseArray) this.sfPreloadedDrawables.get(null)).put(key, cs);
        }
    }

    protected ConstantState getPreloadedDrawable(boolean isColorDrawable, long key) throws IllegalAccessException {
        if (isColorDrawable) {
            return (ConstantState) ((LongSparseArray) this.sfPreloadedColorDrawables.get(null)).get(key);
        }
        Object drawableArray = this.sfPreloadedDrawables.get(null);
        if (drawableArray instanceof LongSparseArray[]) {
            return (ConstantState) ((LongSparseArray[]) drawableArray)[((Configuration) this.mfConfiguration.get(this)).getLayoutDirection()].get(key);
        }
        if (drawableArray instanceof LongSparseArray) {
            return (ConstantState) ((LongSparseArray) drawableArray).get(key);
        }
        return null;
    }

    protected Drawable getCachedDrawableReflect(boolean isColorDrawable, long key, Theme theme) {
        try {
            Method method = Resources.class.getDeclaredMethod("getCachedDrawable", new Class[]{LongSparseArray.class, Long.TYPE});
            method.setAccessible(true);
            Object[] objArr = new Object[2];
            objArr[0] = isColorDrawable ? this.mfColorDrawableCache.get(this) : this.mfDrawableCache.get(this);
            objArr[1] = Long.valueOf(key);
            return (Drawable) method.invoke(this, objArr);
        } catch (Exception e) {
            return null;
        }
    }
}
