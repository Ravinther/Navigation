package android.content.res.impl;

import android.content.res.ExtendedTypedArray;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.resources.ExtendedResources;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LollipopTypedArray extends ExtendedTypedArray {
    protected Field fAssets;
    protected Field fMetrics;
    protected Field fRecycled;
    protected Field fTheme;

    public LollipopTypedArray(Resources resources, int[] data, int[] indices, int len) {
        super(resources, data, indices, len);
    }

    protected void initFields() {
        super.initFields();
        try {
            this.fMetrics = TypedArray.class.getDeclaredField("mMetrics");
            this.fMetrics.setAccessible(true);
            this.fAssets = TypedArray.class.getDeclaredField("mAssets");
            this.fAssets.setAccessible(true);
            this.fRecycled = TypedArray.class.getDeclaredField("mRecycled");
            this.fRecycled.setAccessible(true);
            this.fTheme = TypedArray.class.getDeclaredField("mTheme");
            this.fTheme.setAccessible(true);
        } catch (Exception e) {
            CrashlyticsHelper.logError("LollipopTypedArray", e.getMessage());
        }
    }

    protected void setFields(Resources resources, int[] data, int[] indices, int len) {
        super.setFields(resources, data, indices, len);
        try {
            this.fMetrics.set(this, resources.getDisplayMetrics());
            this.fAssets.set(this, resources.getAssets());
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("LollipopTypedArray", e.getMessage());
        }
    }

    protected void copyInternal(TypedArray attrs) throws IllegalAccessException {
        super.copyInternal(attrs);
        this.fRecycled.set(this, this.fRecycled.get(attrs));
        this.fTheme.set(this, this.fTheme.get(attrs));
    }

    public Drawable getDrawable(int index) {
        try {
            if (((Boolean) this.fRecycled.get(this)).booleanValue()) {
                throw new RuntimeException("Cannot make calls to a recycled instance!");
            }
            TypedValue value = (TypedValue) this.fValue.get(this);
            Method method = TypedArray.class.getDeclaredMethod("getValueAt", new Class[]{Integer.TYPE, TypedValue.class});
            method.setAccessible(true);
            if (((Boolean) method.invoke(this, new Object[]{Integer.valueOf(STYLE_NUM_ENTRIES * index), value})).booleanValue()) {
                if (value.type != 2) {
                    return ((ExtendedResources) this.fResources.get(this)).loadDrawable(value, value.resourceId, (Theme) this.fTheme.get(this));
                }
                throw new RuntimeException("Failed to resolve attribute at index " + index);
            }
            return null;
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("LollipopTypedArray", e.getMessage());
        } catch (NoSuchMethodException e2) {
            CrashlyticsHelper.logError("LollipopTypedArray", e2.getMessage());
        } catch (InvocationTargetException e3) {
            CrashlyticsHelper.logError("LollipopTypedArray", e3.getMessage());
        }
    }

    protected void setRecycled(boolean recycled) {
        try {
            this.fRecycled.set(this, Boolean.valueOf(recycled));
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("LollipopTypedArray", e.getMessage());
        }
    }
}
