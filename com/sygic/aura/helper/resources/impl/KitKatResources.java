package com.sygic.aura.helper.resources.impl;

import android.content.Context;
import com.sygic.aura.helper.resources.ExtendedResources;
import java.lang.reflect.Field;

public class KitKatResources extends JellyBeanMr2Resources {
    public KitKatResources(Context context) throws Exception {
        super(context);
    }

    protected Object getResourceHolder(Object activityThread, Object cObject) throws NoSuchFieldException, IllegalAccessException {
        Field field = cObject.getClass().getDeclaredField("mResourcesManager");
        field.setAccessible(true);
        ExtendedResources.initInternalManager(field.get(cObject), this);
        field = activityThread.getClass().getDeclaredField("mResourcesManager");
        field.setAccessible(true);
        return field.get(activityThread);
    }
}
