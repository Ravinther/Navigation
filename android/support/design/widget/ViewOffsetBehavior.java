package android.support.design.widget;

import android.support.design.widget.CoordinatorLayout.Behavior;
import android.view.View;

class ViewOffsetBehavior<V extends View> extends Behavior<V> {
    private int mTempLeftRightOffset;
    private int mTempTopBottomOffset;
    private ViewOffsetHelper mViewOffsetHelper;

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        if (this.mViewOffsetHelper == null) {
            this.mViewOffsetHelper = new ViewOffsetHelper(child);
        }
        this.mViewOffsetHelper.onViewLayout();
        if (this.mTempTopBottomOffset != 0) {
            this.mViewOffsetHelper.setTopAndBottomOffset(this.mTempTopBottomOffset);
            this.mTempTopBottomOffset = 0;
        }
        if (this.mTempLeftRightOffset != 0) {
            this.mViewOffsetHelper.setLeftAndRightOffset(this.mTempLeftRightOffset);
            this.mTempLeftRightOffset = 0;
        }
        return true;
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (this.mViewOffsetHelper != null) {
            return this.mViewOffsetHelper.setTopAndBottomOffset(offset);
        }
        this.mTempTopBottomOffset = offset;
        return false;
    }

    public int getTopAndBottomOffset() {
        return this.mViewOffsetHelper != null ? this.mViewOffsetHelper.getTopAndBottomOffset() : 0;
    }
}
