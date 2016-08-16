package com.sygic.aura.helper.resources.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.util.LongSparseArray;
import android.util.TypedValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

@TargetApi(18)
public class JellyBeanMr2Resources extends JellyBeanMr1Resources {
    public JellyBeanMr2Resources(Context context) throws Exception {
        super(context);
    }

    protected void init() throws NoSuchFieldException {
        super.init();
        this.sfLayoutDirConfig = Resources.class.getDeclaredField("LAYOUT_DIR_CONFIG");
        this.sfLayoutDirConfig.setAccessible(true);
        this.mfAccessLock = Resources.class.getDeclaredField("mAccessLock");
        this.mfAccessLock.setAccessible(true);
        this.mfTmpValue = Resources.class.getDeclaredField("mTmpValue");
        this.mfTmpValue.setAccessible(true);
    }

    public Drawable getDrawable(int id) throws NotFoundException {
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
            Drawable res = loadDrawable(value, id);
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
            if (isColorDrawable) {
                ((LongSparseArray) this.mfColorDrawableCache.get(this)).put(key, new WeakReference(cs));
            } else {
                ((LongSparseArray) this.mfDrawableCache.get(this)).put(key, new WeakReference(cs));
            }
        }
    }

    protected boolean verifyPreloadConfigReflect(int changingConfigurations, int allowVarying, int resourceId, String name) {
        try {
            Method method = Resources.class.getDeclaredMethod("verifyPreloadConfig", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class});
            method.setAccessible(true);
            return ((Boolean) method.invoke(this, new Object[]{Integer.valueOf(changingConfigurations), Integer.valueOf(allowVarying), Integer.valueOf(resourceId), name})).booleanValue();
        } catch (Exception e) {
            return true;
        }
    }
}
