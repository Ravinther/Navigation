package com.sygic.aura.search.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.sygic.aura.helper.CrashlyticsHelper;
import java.lang.reflect.Field;

public class RevertOrderRelativeLayout extends RelativeLayout {
    private static Field sBottom;
    private static Field sLeft;
    private static Field sRight;
    private static Field sTop;

    public RevertOrderRelativeLayout(Context context) {
        super(context);
        init();
    }

    public RevertOrderRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RevertOrderRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public RevertOrderRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        try {
            sLeft = LayoutParams.class.getDeclaredField("mLeft");
            sTop = LayoutParams.class.getDeclaredField("mTop");
            sRight = LayoutParams.class.getDeclaredField("mRight");
            sBottom = LayoutParams.class.getDeclaredField("mBottom");
            sLeft.setAccessible(true);
            sTop.setAccessible(true);
            sRight.setAccessible(true);
            sBottom.setAccessible(true);
        } catch (NoSuchFieldException e) {
            CrashlyticsHelper.logException(getClass().getName(), "init", e);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams st = (LayoutParams) child.getLayoutParams();
                try {
                    child.layout(sLeft.getInt(st), sTop.getInt(st), sRight.getInt(st), sBottom.getInt(st));
                } catch (Exception e) {
                    CrashlyticsHelper.logException(getClass().getName(), "onLayout", e);
                }
            }
        }
    }
}
