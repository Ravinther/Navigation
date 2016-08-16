package android.content.res;

import android.content.res.impl.LegacyTypedArray;
import android.content.res.impl.LollipopTypedArray;
import android.os.Build.VERSION;
import android.util.TypedValue;
import com.sygic.aura.helper.CrashlyticsHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ExtendedTypedArray extends TypedArray {
    protected static final int STYLE_NUM_ENTRIES;
    protected Field fData;
    protected Field fIndices;
    protected Field fLength;
    protected Field fResources;
    protected Field fValue;
    protected Field fXml;

    static {
        STYLE_NUM_ENTRIES = getConst();
    }

    private static int getConst() {
        try {
            Field fConst = AssetManager.class.getDeclaredField("STYLE_NUM_ENTRIES");
            fConst.setAccessible(true);
            return ((Integer) fConst.get(null)).intValue();
        } catch (Exception e) {
            return 6;
        }
    }

    public static TypedArray copy(TypedArray attrs) {
        try {
            ExtendedTypedArray copy = getInstance(attrs);
            copy.copyInternal(attrs);
            return copy;
        } catch (Exception e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
            return null;
        }
    }

    private static ExtendedTypedArray getInstance(Resources resources, int[] data, int[] indices, int len) {
        if (VERSION.SDK_INT >= 21) {
            return new LollipopTypedArray(resources, data, indices, len);
        }
        return new LegacyTypedArray(resources, data, indices, len);
    }

    private static ExtendedTypedArray getInstance(TypedArray attrs) throws NoSuchFieldException, IllegalAccessException {
        Field resources = TypedArray.class.getDeclaredField("mResources");
        resources.setAccessible(true);
        Field data = TypedArray.class.getDeclaredField("mData");
        data.setAccessible(true);
        Field indices = TypedArray.class.getDeclaredField("mIndices");
        indices.setAccessible(true);
        Field length = TypedArray.class.getDeclaredField("mLength");
        length.setAccessible(true);
        return getInstance((Resources) resources.get(attrs), (int[]) data.get(attrs), (int[]) indices.get(attrs), ((Integer) length.get(attrs)).intValue());
    }

    protected ExtendedTypedArray(Resources resources, int[] data, int[] indices, int len) {
        initFields();
        setFields(resources, data, indices, len);
    }

    protected void initFields() {
        try {
            this.fResources = TypedArray.class.getDeclaredField("mResources");
            this.fResources.setAccessible(true);
            this.fData = TypedArray.class.getDeclaredField("mData");
            this.fData.setAccessible(true);
            this.fIndices = TypedArray.class.getDeclaredField("mIndices");
            this.fIndices.setAccessible(true);
            this.fLength = TypedArray.class.getDeclaredField("mLength");
            this.fLength.setAccessible(true);
            this.fXml = TypedArray.class.getDeclaredField("mXml");
            this.fXml.setAccessible(true);
            this.fValue = TypedArray.class.getDeclaredField("mValue");
            this.fValue.setAccessible(true);
        } catch (Exception e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
        }
    }

    protected void setFields(Resources resources, int[] data, int[] indices, int len) {
        try {
            this.fResources.set(this, resources);
            this.fData.set(this, data);
            this.fIndices.set(this, indices);
            this.fLength.set(this, Integer.valueOf(len));
            this.fValue.set(this, new TypedValue());
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
        }
    }

    protected void copyInternal(TypedArray attrs) throws IllegalAccessException {
        this.fXml.set(this, this.fXml.get(attrs));
        this.fValue.set(this, this.fValue.get(attrs));
    }

    public static TypedArray obtain(Resources res, int len) {
        return obtain(null, res, len);
    }

    public static TypedArray obtain(Field fCachedTypedArray, Resources res, int len) {
        TypedArray attrs;
        if (fCachedTypedArray == null) {
            try {
                Field fTypedArrayPool = Resources.class.getDeclaredField("mTypedArrayPool");
                fTypedArrayPool.setAccessible(true);
                Method method = Class.forName("android.util.Pools$SynchronizedPool").getDeclaredMethod("acquire", new Class[0]);
                method.setAccessible(true);
                attrs = (TypedArray) method.invoke(fTypedArrayPool.get(res), new Object[0]);
            } catch (Exception e) {
                CrashlyticsHelper.logInfo("ExtendedTypedArray", e.getMessage());
                attrs = null;
            }
        } else {
            try {
                attrs = (TypedArray) fCachedTypedArray.get(res);
            } catch (Exception e2) {
                CrashlyticsHelper.logInfo("ExtendedTypedArray", e2.getMessage());
                attrs = null;
            }
        }
        if (attrs == null) {
            return getInstance(res, new int[(STYLE_NUM_ENTRIES * len)], new int[(len + 1)], len);
        }
        if (fCachedTypedArray != null) {
            try {
                fCachedTypedArray.set(res, null);
            } catch (Exception e22) {
                CrashlyticsHelper.logInfo("ExtendedTypedArray", e22.getMessage());
            }
        }
        if (!(attrs instanceof ExtendedTypedArray)) {
            attrs = copy(attrs);
            if (attrs == null) {
                return getInstance(res, new int[(STYLE_NUM_ENTRIES * len)], new int[(len + 1)], len);
            }
        }
        ((ExtendedTypedArray) attrs).setLength(len);
        ((ExtendedTypedArray) attrs).setRecycled(false);
        int fullLen = len * STYLE_NUM_ENTRIES;
        if (((ExtendedTypedArray) attrs).getData().length >= fullLen) {
            return attrs;
        }
        ((ExtendedTypedArray) attrs).setData(new int[fullLen]);
        ((ExtendedTypedArray) attrs).setIndices(new int[(len + 1)]);
        return attrs;
    }

    public int[] getData() {
        try {
            return (int[]) this.fData.get(this);
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
            return new int[0];
        }
    }

    public int[] getIndices() {
        try {
            return (int[]) this.fIndices.get(this);
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
            return new int[0];
        }
    }

    private void setIndices(int[] indices) {
        try {
            this.fIndices.set(this, indices);
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
        }
    }

    public void setIndices(int index, int value) {
        try {
            ((int[]) this.fIndices.get(this))[index] = value;
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
        }
    }

    private void setData(int[] data) {
        try {
            this.fData.set(this, data);
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
        }
    }

    protected void setRecycled(boolean recycled) {
    }

    public void setRsrcs(int[] rsrcs) {
    }

    public void setLength(int len) {
        try {
            this.fLength.set(this, Integer.valueOf(len));
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
        }
    }

    public void setXml(Object parser) {
        try {
            this.fXml.set(this, parser);
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("ExtendedTypedArray", e.getMessage());
        }
    }
}
