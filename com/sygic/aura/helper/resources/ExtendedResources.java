package com.sygic.aura.helper.resources;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ExtendedTypedArray;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.resources.impl.HoneyCombResources;
import com.sygic.aura.helper.resources.impl.JellyBeanMr1Resources;
import com.sygic.aura.helper.resources.impl.JellyBeanMr2Resources;
import com.sygic.aura.helper.resources.impl.KitKatResources;
import com.sygic.aura.helper.resources.impl.LegacyResources;
import com.sygic.aura.helper.resources.impl.LollipopMr1Resources;
import com.sygic.aura.helper.resources.impl.LollipopResources;
import com.sygic.aura.helper.resources.impl.MarshmallowResources;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ExtendedResources extends Resources {
    protected final boolean mIsDalvik;
    protected Field mfAccessLock;
    protected Field mfCachedStyledAttributes;
    protected Field mfColorDrawableCache;
    protected Field mfConfiguration;
    protected Field mfDrawableCache;
    protected Field mfPreloading;
    protected Field mfTmpValue;
    protected Field sfLayoutDirConfig;
    protected Field sfPreloadedColorDrawables;
    protected Field sfPreloadedDrawables;
    protected Field sfTraceForMissPreload;
    protected Field sfTraceForPreload;

    protected abstract Method getAMRetrieveAttributes() throws NoSuchMethodException;

    protected abstract Drawable getCachedDrawableReflect(boolean z, long j, Theme theme);

    protected abstract ConstantState getPreloadedDrawable(boolean z, long j) throws IllegalAccessException;

    protected abstract Drawable newConstantStateDrawable(ConstantState constantState, Theme theme);

    protected abstract void putDrawable(TypedValue typedValue, Theme theme, boolean z, ConstantState constantState, long j) throws IllegalAccessException;

    protected abstract TypedArray retrieveTypedArray(Resources resources, int i);

    protected abstract void setup(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    public static void init(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        try {
            ExtendedResources resources;
            if (VERSION.SDK_INT >= 23) {
                resources = new MarshmallowResources(context);
            } else if (VERSION.SDK_INT >= 22) {
                resources = new LollipopMr1Resources(context);
            } else if (VERSION.SDK_INT >= 21) {
                resources = new LollipopResources(context);
            } else if (VERSION.SDK_INT >= 19) {
                resources = new KitKatResources(context);
            } else if (VERSION.SDK_INT >= 18) {
                resources = new JellyBeanMr2Resources(context);
            } else if (VERSION.SDK_INT >= 17) {
                resources = new JellyBeanMr1Resources(context);
            } else if (VERSION.SDK_INT >= 11) {
                resources = new HoneyCombResources(context);
            } else {
                resources = new LegacyResources(context);
            }
            resources.setup(context);
        } catch (Exception e) {
            throw new NotFoundException("ReflectiveOperationException");
        }
    }

    protected static void initInternal(Object cObject, Resources resources) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field field = cObject.getClass().getDeclaredField("mResources");
        field.setAccessible(true);
        field.set(cObject, resources);
    }

    protected static void initInternalManager(Object resourcesHolder, Resources resources) throws NoSuchFieldException, IllegalAccessException {
        Field field = resourcesHolder.getClass().getDeclaredField("mActiveResources");
        field.setAccessible(true);
        for (Entry value : ((Map) field.get(resourcesHolder)).entrySet()) {
            value.setValue(new WeakReference(resources));
        }
    }

    protected ExtendedResources(Context context) {
        super(context.getAssets(), context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        String jm_version = System.getProperty("java.vm.version");
        boolean z = jm_version.startsWith("1") || jm_version.startsWith("0");
        this.mIsDalvik = z;
    }

    public Drawable loadDrawable(TypedValue value, int id) throws NotFoundException {
        return loadDrawable(value, id, null);
    }

    public Drawable loadDrawable(TypedValue value, int id, Theme theme) throws NotFoundException {
        try {
            long key;
            Drawable dr;
            if (this.sfTraceForPreload.getBoolean(null) && (id >>> 24) == 1) {
                String name = getResourceName(id);
                if (name != null) {
                    Log.d("PreloadDrawable", name);
                }
            }
            boolean isColorDrawable = false;
            if (value.type >= 28 && value.type <= 31) {
                isColorDrawable = true;
            }
            if (isColorDrawable) {
                key = (long) value.data;
            } else {
                key = (((long) value.assetCookie) << 32) | ((long) value.data);
            }
            if (!this.mfPreloading.getBoolean(this)) {
                Drawable cachedDrawable = getCachedDrawableReflect(isColorDrawable, key, theme);
                if (cachedDrawable != null) {
                    return cachedDrawable;
                }
            }
            ConstantState cs = getPreloadedDrawable(isColorDrawable, key);
            if (cs != null) {
                dr = newConstantStateDrawable(cs, theme);
            } else if (isColorDrawable) {
                dr = new ColorDrawable(value.data);
            } else {
                dr = loadDrawableForCookie(value, id, theme);
            }
            if (dr != null) {
                dr.setChangingConfigurations(value.changingConfigurations);
                cacheDrawable(value, theme, isColorDrawable, key, dr);
            }
            return dr;
        } catch (IllegalAccessException e) {
            throw new NotFoundException("IllegalAccessException");
        }
    }

    private void cacheDrawable(TypedValue value, Theme theme, boolean isColorDrawable, long key, Drawable dr) throws IllegalAccessException {
        ConstantState cs = dr.getConstantState();
        if (cs != null) {
            putDrawable(value, theme, isColorDrawable, cs, key);
        }
    }

    private Drawable loadDrawableForCookie(TypedValue value, int id, Theme theme) throws IllegalAccessException {
        if (value.string == null) {
            throw new NotFoundException("Resource \"" + getResourceName(id) + "\" (" + Integer.toHexString(id) + ")  is not a Drawable (color or path): " + value);
        }
        boolean traceForPreload;
        String file = value.string.toString();
        if (this.sfTraceForMissPreload != null) {
            traceForPreload = this.sfTraceForMissPreload.getBoolean(null);
        } else {
            traceForPreload = this.sfTraceForPreload.getBoolean(null);
        }
        if (traceForPreload && (id >>> 24) == 1) {
            String name = getResourceName(id);
            if (name != null) {
                Log.d("ExtendedResources", "Loading framework drawable #" + Integer.toHexString(id) + ": " + name + " at " + file);
            }
        }
        if (file.endsWith(".xml")) {
            try {
                XmlResourceParser rp = loadXmlResourceParserReflect(file, id, value.assetCookie, "drawable");
                Drawable dr = ExtendedDrawable.createFromXml(this, rp, theme);
                rp.close();
                return dr;
            } catch (Exception e) {
                NotFoundException rnf = new NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
                rnf.initCause(e);
                throw rnf;
            }
        }
        try {
            InputStream is = openNonAssetReflect(getAssets(), value.assetCookie, file, 2);
            dr = Drawable.createFromResourceStream(this, value, is, file, null);
            is.close();
            return dr;
        } catch (Exception e2) {
            rnf = new NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(e2);
            throw rnf;
        }
    }

    private InputStream openNonAssetReflect(AssetManager assetManager, int cookie, String fileName, int acessMode) throws Exception {
        Method method = AssetManager.class.getDeclaredMethod("openNonAsset", new Class[]{Integer.TYPE, String.class, Integer.TYPE});
        method.setAccessible(true);
        return (InputStream) method.invoke(assetManager, new Object[]{Integer.valueOf(cookie), fileName, Integer.valueOf(acessMode)});
    }

    private XmlResourceParser loadXmlResourceParserReflect(String file, int id, int assetCookie, String type) {
        try {
            Method method = Resources.class.getDeclaredMethod("loadXmlResourceParser", new Class[]{String.class, Integer.TYPE, Integer.TYPE, String.class});
            method.setAccessible(true);
            return (XmlResourceParser) method.invoke(this, new Object[]{file, Integer.valueOf(id), Integer.valueOf(assetCookie), type});
        } catch (Exception e) {
            NotFoundException rnf = new NotFoundException("File " + file + " from xml type " + type + " resource ID #0x" + Integer.toHexString(id));
            rnf.initCause(e);
            throw rnf;
        }
    }

    public TypedArray obtainTypedArray(int id) throws NotFoundException {
        if (this.mIsDalvik) {
            return super.obtainTypedArray(id);
        }
        TypedArray array = null;
        try {
            Field fAssets = Resources.class.getDeclaredField("mAssets");
            fAssets.setAccessible(true);
            Method method = AssetManager.class.getDeclaredMethod("getArraySize", new Class[]{Integer.TYPE});
            method.setAccessible(true);
            int len = ((Integer) method.invoke(fAssets.get(this), new Object[]{Integer.valueOf(id)})).intValue();
            if (len < 0) {
                throw new NotFoundException("Array resource ID #0x" + Integer.toHexString(id));
            }
            array = ExtendedTypedArray.obtain(this, len);
            method = AssetManager.class.getDeclaredMethod("retrieveArray", new Class[]{Integer.TYPE, int[].class});
            method.setAccessible(true);
            ExtendedTypedArray extendedTypedArray = (ExtendedTypedArray) array;
            Object obj = fAssets.get(this);
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(id);
            objArr[1] = ((ExtendedTypedArray) array).getData();
            extendedTypedArray.setLength(((Integer) method.invoke(obj, objArr)).intValue());
            ((ExtendedTypedArray) array).setIndices(0, 0);
            return array;
        } catch (Exception e) {
            CrashlyticsHelper.logError("ExtendedResources", e.getMessage());
            return array;
        }
    }

    public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
        if (this.mIsDalvik) {
            return super.obtainAttributes(set, attrs);
        }
        TypedArray array = retrieveTypedArray(this, attrs.length);
        try {
            Class.forName("android.content.res.XmlBlock$Parser").getDeclaredField("mParseState").setAccessible(true);
            Field fAssets = Resources.class.getDeclaredField("mAssets");
            fAssets.setAccessible(true);
            Method method = getAMRetrieveAttributes();
            method.setAccessible(true);
            Object obj = fAssets.get(this);
            r9 = new Object[4];
            r9[2] = ((ExtendedTypedArray) array).getData();
            r9[3] = ((ExtendedTypedArray) array).getIndices();
            method.invoke(obj, r9);
            ((ExtendedTypedArray) array).setXml(set);
            ((ExtendedTypedArray) array).setRsrcs(attrs);
            return array;
        } catch (Exception e) {
            CrashlyticsHelper.logError("ExtendedResources", e.getMessage());
            return array;
        }
    }
}
