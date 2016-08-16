package com.sygic.aura.map.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class InfoBarLayout extends LinearLayout {
    public InfoBarLayout(Context context) {
        super(context);
    }

    public InfoBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(11)
    public InfoBarLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int childWidth = getMeasuredWidth() / childCount;
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            View child = getChildAt(childIndex);
            if (child != null) {
                LayoutParams lp = child.getLayoutParams();
                if (lp != null) {
                    lp.width = childWidth;
                    child.setLayoutParams(lp);
                    child.forceLayout();
                }
            }
        }
    }
}
