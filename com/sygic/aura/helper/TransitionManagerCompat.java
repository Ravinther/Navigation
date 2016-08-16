package com.sygic.aura.helper;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionManager;
import android.view.ViewGroup;

public class TransitionManagerCompat {

    public interface TransitionListenerProvider {
        @TargetApi(19)
        TransitionListener getTransitionListener();
    }

    public static void beginDelayedTransition(ViewGroup sceneRoot) {
        beginDelayedTransition(sceneRoot, null);
    }

    public static void beginDelayedTransition(ViewGroup sceneRoot, Transition transition) {
        if (VERSION.SDK_INT >= 19 && sceneRoot != null) {
            TransitionManager.beginDelayedTransition(sceneRoot, transition);
        }
    }
}
