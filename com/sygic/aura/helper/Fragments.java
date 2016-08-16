package com.sygic.aura.helper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;

public class Fragments {
    public static void add(Activity activity, Class<? extends AbstractScreenFragment> fClass, String tag, int startAnim, int endAnim) {
        add(activity, fClass, tag, null, true, null, startAnim, endAnim);
    }

    public static void add(Activity activity, Class<? extends AbstractScreenFragment> fClass, String tag, Bundle args) {
        add(activity, fClass, tag, args, true, null);
    }

    public static void add(Activity activity, Class<? extends AbstractScreenFragment> fClass, String tag, Bundle args, boolean addToBackStack, FragmentResultCallback callback) {
        if (activity != null && (activity instanceof NaviNativeActivity)) {
            ((NaviNativeActivity) activity).addFragment((Class) fClass, tag, addToBackStack, callback, args);
        }
    }

    public static void add(Activity activity, Class<? extends AbstractScreenFragment> fClass, String tag, Bundle args, boolean addToBackStack, FragmentResultCallback callback, int startAnim, int endAnim) {
        if (activity != null && (activity instanceof NaviNativeActivity)) {
            ((NaviNativeActivity) activity).addFragment(fClass, tag, addToBackStack, callback, args, startAnim, endAnim);
        }
    }

    public static void replace(Activity activity, Class<? extends AbstractScreenFragment> fClass, String tag, Bundle args) {
        replace(activity, fClass, tag, args, true, null);
    }

    public static void replace(Activity activity, Class<? extends AbstractScreenFragment> fClass, String tag, Bundle args, boolean addToBackStack, FragmentResultCallback callback) {
        if (activity != null && (activity instanceof NaviNativeActivity)) {
            ((NaviNativeActivity) activity).replaceFragment(fClass, tag, addToBackStack, args, callback);
        }
    }

    public static Fragment findFragmentByTag(Activity activity, String tag) {
        if (activity == null || !(activity instanceof NaviNativeActivity)) {
            return null;
        }
        return ((NaviNativeActivity) activity).findFragmentByTag(tag);
    }

    public static void clearBackStack(Activity activity, boolean immediate) {
        if (activity != null && (activity instanceof NaviNativeActivity)) {
            ((NaviNativeActivity) activity).clearBackStack(immediate);
        }
    }
}
