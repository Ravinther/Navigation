package com.sygic.aura.helper.resources.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@TargetApi(15)
public class JellyBeanMr1Resources extends HoneyCombResources {
    public JellyBeanMr1Resources(Context context) throws Exception {
        super(context);
    }

    protected void setup(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        super.setup(context);
        Field field = Class.forName("android.view.ContextThemeWrapper").getDeclaredField("mResources");
        field.setAccessible(true);
        field.set(context, this);
        setActionBarResources(context, this);
    }

    public Drawable getDrawableForDensity(int id, int density) throws NotFoundException {
        return getDrawableForDensity(id, density, null);
    }

    public Drawable getDrawableForDensity(int id, int density, Theme theme) {
        try {
            TypedValue value;
            synchronized (this.mfAccessLock.get(this)) {
                value = (TypedValue) this.mfTmpValue.get(this);
                if (value == null) {
                    value = new TypedValue();
                } else {
                    this.mfTmpValue.set(this, null);
                }
                getValueForDensity(id, density, value, true);
                if (value.density > 0 && value.density != 65535) {
                    if (value.density == density) {
                        value.density = getDisplayMetrics().densityDpi;
                    } else {
                        value.density = density == 0 ? 0 : (value.density * getDisplayMetrics().densityDpi) / density;
                    }
                }
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
}
