package com.sygic.aura.helper.resources.impl;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.ExtendedTypedArray;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.view.View;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.resources.ExtendedResources;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LegacyResources extends ExtendedResources {
    public LegacyResources(Context context) throws Exception {
        super(context);
        Field fCompatibilityInfo = Resources.class.getDeclaredField("mCompatibilityInfo");
        fCompatibilityInfo.setAccessible(true);
        fCompatibilityInfo.set(this, fCompatibilityInfo.get(context.getResources()));
        init();
    }

    protected TypedArray retrieveTypedArray(Resources resources, int len) {
        try {
            TypedArray obtain;
            synchronized (this.mfAccessLock.get(this)) {
                obtain = ExtendedTypedArray.obtain(this.mfCachedStyledAttributes, resources, len);
            }
            return obtain;
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedResources", e.getMessage());
            return null;
        }
    }

    protected Method getAMRetrieveAttributes() throws NoSuchMethodException {
        return AssetManager.class.getDeclaredMethod("retrieveAttributes", new Class[]{Integer.TYPE, int[].class, int[].class, int[].class});
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
        this.mfAccessLock = Resources.class.getDeclaredField("mTmpValue");
        this.mfAccessLock.setAccessible(true);
        this.mfCachedStyledAttributes = Resources.class.getDeclaredField("mCachedStyledAttributes");
        this.mfCachedStyledAttributes.setAccessible(true);
        this.mfTmpValue = this.mfAccessLock;
    }

    protected void setup(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentActivityThread", new Class[0]);
        method.setAccessible(true);
        Object activityThread = method.invoke(null, new Object[0]);
        Field field = Class.forName("android.view.View").getDeclaredField("mResources");
        field.setAccessible(true);
        field.set(((Activity) context).getWindow().getDecorView(), this);
        field = Class.forName("android.content.ContextWrapper").getDeclaredField("mBase");
        field.setAccessible(true);
        Object cObject = field.get(context.getApplicationContext());
        ExtendedResources.initInternal(cObject, this);
        setContextBaseObject(context);
        Object loadedApk = getLoadedApk(context, activityThread);
        if (loadedApk != null) {
            field = loadedApk.getClass().getDeclaredField("mResources");
            field.setAccessible(true);
            field.set(loadedApk, this);
        }
        ExtendedResources.initInternalManager(getResourceHolder(activityThread, cObject), this);
        field = Class.forName("android.view.ContextThemeWrapper").getDeclaredField("mTheme");
        field.setAccessible(true);
        field.set(context, null);
    }

    protected void setActionBarResources(Context context, Resources resources) {
        boolean result;
        try {
            ActionBar supportActionBar = ((ActionBarActivity) context).getSupportActionBar();
            if (supportActionBar == null) {
                result = false;
            } else {
                Field field = supportActionBar.getClass().getDeclaredField("mContextView");
                field.setAccessible(true);
                Object view = field.get(supportActionBar);
                field = View.class.getDeclaredField("mContext");
                field.setAccessible(true);
                ExtendedResources.initInternal(field.get(view), resources);
                result = true;
            }
        } catch (Exception e) {
            result = false;
        }
        CrashlyticsHelper.setCustomKey("setting actionbar resources", result);
    }

    protected void setContextBaseObject(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field field = Class.forName("android.view.ContextThemeWrapper").getDeclaredField("mBase");
        field.setAccessible(true);
        ExtendedResources.initInternal(field.get(context), this);
    }

    protected Object getLoadedApk(Context context, Object activityThread) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (context.getApplicationInfo() == null) {
            return null;
        }
        Method method = activityThread.getClass().getDeclaredMethod("getPackageInfoNoCheck", new Class[]{ApplicationInfo.class});
        method.setAccessible(true);
        return method.invoke(activityThread, new Object[]{ai});
    }

    protected Object getResourceHolder(Object activityThread, Object cObject) throws NoSuchFieldException, IllegalAccessException {
        return activityThread;
    }

    public Drawable getDrawable(int id) throws NotFoundException {
        try {
            Drawable loadDrawable;
            synchronized (this.mfAccessLock.get(this)) {
                TypedValue value = (TypedValue) this.mfTmpValue.get(this);
                getValue(id, value, true);
                loadDrawable = loadDrawable(value, id, null);
            }
            return loadDrawable;
        } catch (IllegalAccessException e) {
            throw new NotFoundException("IllegalAccessException");
        }
    }

    protected Drawable newConstantStateDrawable(ConstantState cs, Theme theme) {
        return cs.newDrawable(this);
    }

    protected void putDrawable(TypedValue value, Theme theme, boolean isColorDrawable, ConstantState cs, long key) throws IllegalAccessException {
        if (this.mfPreloading.getBoolean(this)) {
            ((LongSparseArray) this.sfPreloadedDrawables.get(null)).put(key, cs);
            return;
        }
        synchronized (this.mfAccessLock.get(this)) {
            ((LongSparseArray) this.mfDrawableCache.get(this)).put(key, new WeakReference(cs));
        }
    }

    protected ConstantState getPreloadedDrawable(boolean isColorDrawable, long key) throws IllegalAccessException {
        return (ConstantState) ((LongSparseArray) this.sfPreloadedDrawables.get(null)).get(key);
    }

    protected Drawable getCachedDrawableReflect(boolean isColorDrawable, long key, Theme theme) {
        try {
            Method method = Resources.class.getDeclaredMethod("getCachedDrawable", new Class[]{Long.TYPE});
            method.setAccessible(true);
            return (Drawable) method.invoke(this, new Object[]{Long.valueOf(key)});
        } catch (Exception e) {
            return null;
        }
    }
}
