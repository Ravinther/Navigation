package com.sygic.aura.helper;

import android.content.Context;
import android.widget.Toast;
import com.sygic.aura.resources.ResourceManager;

public abstract class SToast extends Toast {
    public static Toast makeText(Context context, int resId, int duration) {
        return Toast.makeText(context, ResourceManager.getCoreString(context, resId), duration);
    }
}
