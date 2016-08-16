package com.sygic.aura.helper;

import android.view.View.MeasureSpec;
import android.widget.EditText;
import com.sygic.aura.SygicMain;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.feature.automotive.InCarConnectionListener;
import com.sygic.aura.keyboard.FocusableKeyboard.OnKeyboardVisibilityListener;
import com.sygic.aura.keyboard.FocusableKeyboardView;
import com.sygic.aura.views.ButtonScrollListView;

public class InCarConnection {

    /* renamed from: com.sygic.aura.helper.InCarConnection.3 */
    static class C12733 implements OnKeyboardVisibilityListener {
        final /* synthetic */ ButtonScrollListView val$listView;

        C12733(ButtonScrollListView buttonScrollListView) {
            this.val$listView = buttonScrollListView;
        }

        public void onVisibilityChanged(boolean visible) {
            this.val$listView.showScrollButtons(!visible);
        }
    }

    public static boolean isInCarConnected() {
        return SygicMain.getInstance().getFeature().getAutomotiveFeature().isCarConnected();
    }

    public static void changeCarConnection(boolean enable) {
        SygicMain.getInstance().getFeature().getAutomotiveFeature().onConnectionChanged(enable);
    }

    public static float getPixelsRatio() {
        return SygicMain.getInstance().getFeature().getAutomotiveFeature().getPixelsRatio();
    }

    public static void registerOnConnectionListener(InCarConnectionListener listener) {
        SygicMain.getInstance().getFeature().getAutomotiveFeature().registerOnConnectionListener(listener);
    }

    public static void unregisterOnConnectionListener(InCarConnectionListener listener) {
        SygicMain.getInstance().getFeature().getAutomotiveFeature().unregisterOnConnectionListener(listener);
    }

    public static int updateViewMeasuredDimension(int measuredDimension) {
        return MeasureSpec.makeMeasureSpec((int) (((float) MeasureSpec.getSize(measuredDimension)) * getPixelsRatio()), MeasureSpec.getMode(measuredDimension));
    }

    public static int updateViewDrawingDimension(int drawingDimension) {
        if (-1 == drawingDimension || -2 == drawingDimension) {
            return drawingDimension;
        }
        return (int) (((float) drawingDimension) * getPixelsRatio());
    }

    public static void registerKeyboard(FocusableKeyboardView kbView, EditText editText) {
        if (isInCarConnected() && editText != null) {
            NaviNativeActivity.registerKeyboard(kbView);
            NaviNativeActivity.getKeyboard().registerEditText(editText);
        }
    }

    public static OnKeyboardVisibilityListener handleKeyboardChanges(ButtonScrollListView listView) {
        if (!isInCarConnected()) {
            return null;
        }
        OnKeyboardVisibilityListener listener = new C12733(listView);
        NaviNativeActivity.getKeyboard().addKeyboardVisibilityListener(listener);
        return listener;
    }

    public static void unhandleKeyboardChanges(OnKeyboardVisibilityListener listener) {
        if (isInCarConnected()) {
            NaviNativeActivity.getKeyboard().removeKeyboardVisibilityListener(listener);
        }
    }
}
