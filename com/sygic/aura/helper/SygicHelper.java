package com.sygic.aura.helper;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.SurfaceView;
import com.sygic.aura.SygicMain;
import com.sygic.aura.SygicProject;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.events.CoreEventsListener;
import com.sygic.aura.events.core.InitCoreListener;
import com.sygic.aura.feature.gl.LowGlFeature.GlSurfaceListener;
import com.sygic.aura.feature.tts.TtsAndroid;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.resources.ExtendedResources;
import com.sygic.aura.views.font_specials.SToolbar;

public class SygicHelper {
    private static NaviNativeActivity mFakeActivity;

    public static void registerCoreEventsListener(CoreEventsListener eventListener) {
        SygicMain.registerCoreEventsListener(eventListener);
    }

    public static Activity getActivity() {
        return SygicMain.getActivity();
    }

    private static void initResources(Context context) {
        if (context instanceof ContextWrapper) {
            try {
                ExtendedResources.init(context);
            } catch (Exception e) {
                throw new NullPointerException("Something is Wrong! We can't set resources!");
            }
        }
    }

    public static void enableGlMapTouchListener(boolean enable) {
        if (!SygicProject.IS_PROTOTYPE) {
            SygicMain.getInstance().getFeature().getGlFeature().enableTouchListener(enable);
        }
    }

    public static FragmentManager getFragmentManager() {
        Activity activity = SygicMain.getActivity();
        if (activity != null) {
            return ((FragmentActivity) activity).getSupportFragmentManager();
        }
        return null;
    }

    public static void setActivityWrapper(NaviNativeActivity activity) {
        mFakeActivity = activity;
        initResources(activity.getContext());
        registerCoreEventsListener(activity);
    }

    @Deprecated
    public static SToolbar getToolbar() {
        return mFakeActivity.getToolbar();
    }

    @Deprecated
    public static FragmentManagerInterface getFragmentActivityWrapper() {
        return mFakeActivity;
    }

    public static void registerInitCoreListener(InitCoreListener initCoreListener) {
        SygicMain.registerInitCoreListener(initCoreListener);
    }

    public static void unregisterInitCoreListener(InitCoreListener initCoreListener) {
        SygicMain.unregisterInitCoreListener(initCoreListener);
    }

    public static void setSurface(SurfaceView surface) {
        SygicMain.setSurface(surface);
    }

    public static SurfaceView getSurface() {
        return SygicMain.getSurface();
    }

    public static void setRotationLock(boolean lockRotation) {
        if (!SygicProject.IS_PROTOTYPE) {
            SygicMain.getInstance().SetRotationLock(lockRotation);
        }
    }

    public static void makeCall(String strPhoneNum, boolean needResult) {
        if (!SygicProject.IS_PROTOTYPE) {
            SygicMain.getInstance().getFeature().getPhoneFeature().makeCall(strPhoneNum, needResult);
        }
    }

    public static void registerGlSurfaceListener(GlSurfaceListener listener) {
        SygicMain.getInstance().getFeature().getGlFeature().registerGlSurfaceListener(listener);
    }

    public static TtsAndroid getAndroidTts() {
        return SygicMain.getInstance().getFeature().getTtsFeature().getTtsAndroid();
    }

    public static void setNativeLoopEnabled(boolean bEnable) {
        if (!SygicProject.IS_PROTOTYPE) {
            SygicMain.setNativeLoopEnabled(bEnable);
        }
    }

    public static long getFreeDiskSpaceInBytes(String path) {
        return SygicMain.getInstance().getFeature().getCommonFeature().getFreeDiskSpaceInBytes(path);
    }

    public static boolean isGPSEnabled() {
        return SygicMain.getInstance().getFeature().getGpsFeature().isEnabled();
    }

    public static Object getTime(long lTime) {
        return SygicMain.getInstance().getFeature().getTimeFeature().getTime(lTime);
    }

    public static boolean hasGlSurface() {
        SygicMain instance = SygicMain.getInstance();
        return instance != null && instance.GetIsRunning() && instance.getFeature().getGlFeature().hasSurface();
    }

    public static Handler getCoreHandler() {
        return SygicMain.getCoreHandler();
    }
}
