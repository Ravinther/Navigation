package com.sygic.aura.helper.resources.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import com.sygic.aura.helper.CrashlyticsHelper;
import java.lang.reflect.Method;

@TargetApi(22)
public class LollipopMr1Resources extends LollipopResources {
    public LollipopMr1Resources(Context context) throws Exception {
        super(context);
    }

    protected Drawable newConstantStateDrawable(ConstantState cs, Theme theme) {
        Drawable clonedDr = super.newConstantStateDrawable(cs, theme);
        if (theme == null) {
            return clonedDr;
        }
        Drawable dr = clonedDr.mutate();
        dr.applyTheme(theme);
        try {
            Method method = dr.getClass().getDeclaredMethod("clearMutated", new Class[0]);
            method.setAccessible(true);
            method.invoke(dr, new Object[0]);
            return dr;
        } catch (Exception e) {
            CrashlyticsHelper.logWarning("LollipopMr1Resources", e.getMessage());
            return dr;
        }
    }
}
