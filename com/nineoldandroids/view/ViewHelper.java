package com.nineoldandroids.view;

import android.view.View;
import com.nineoldandroids.view.animation.AnimatorProxy;

public final class ViewHelper {

    private static final class Honeycomb {
        static void setAlpha(View view, float alpha) {
            view.setAlpha(alpha);
        }
    }

    public static void setAlpha(View view, float alpha) {
        if (AnimatorProxy.NEEDS_PROXY) {
            AnimatorProxy.wrap(view).setAlpha(alpha);
        } else {
            Honeycomb.setAlpha(view, alpha);
        }
    }
}
