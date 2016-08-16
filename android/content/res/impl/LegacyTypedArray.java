package android.content.res.impl;

import android.content.res.ExtendedTypedArray;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.resources.ExtendedResources;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LegacyTypedArray extends ExtendedTypedArray {
    protected Field fRsrcs;

    public LegacyTypedArray(Resources resources, int[] data, int[] indices, int len) {
        super(resources, data, indices, len);
    }

    protected void initFields() {
        super.initFields();
        try {
            this.fRsrcs = TypedArray.class.getDeclaredField("mRsrcs");
            this.fRsrcs.setAccessible(true);
        } catch (Exception e) {
            CrashlyticsHelper.logError("LegacyTypedArray", e.getMessage());
        }
    }

    protected void setFields(Resources resources, int[] data, int[] indices, int len) {
        super.setFields(resources, data, indices, len);
    }

    protected void copyInternal(TypedArray attrs) throws IllegalAccessException {
        super.copyInternal(attrs);
        this.fRsrcs.set(this, this.fRsrcs.get(attrs));
    }

    public void setRsrcs(int[] rsrcs) {
        try {
            this.fRsrcs.set(this, rsrcs);
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("LegacyTypedArray", e.getMessage());
        }
    }

    public Drawable getDrawable(int index) {
        try {
            TypedValue value = (TypedValue) this.fValue.get(this);
            Method method = TypedArray.class.getDeclaredMethod("getValueAt", new Class[]{Integer.TYPE, TypedValue.class});
            method.setAccessible(true);
            if (((Boolean) method.invoke(this, new Object[]{Integer.valueOf(STYLE_NUM_ENTRIES * index), value})).booleanValue()) {
                return ((ExtendedResources) this.fResources.get(this)).loadDrawable(value, value.resourceId);
            }
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("LegacyTypedArray", e.getMessage());
        } catch (NoSuchMethodException e2) {
            CrashlyticsHelper.logError("LegacyTypedArray", e2.getMessage());
        } catch (InvocationTargetException e3) {
            CrashlyticsHelper.logError("LegacyTypedArray", e3.getMessage());
        }
        return null;
    }
}
