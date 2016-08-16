package com.sygic.aura.dashboard;

import android.view.View;
import com.sygic.aura.dashboard.interfaces.ScrollingStrategyInterface;

public class DashboardScrollingStrategy implements ScrollingStrategyInterface {
    View mScrollViewContainer;

    public DashboardScrollingStrategy(View scrollViewContainer) {
        this.mScrollViewContainer = scrollViewContainer;
    }

    public boolean performScrolling(int x, int y, DashboardDragAndDropGridView view) {
        if (this.mScrollViewContainer != null) {
            int delta = this.mScrollViewContainer.getScrollY() - view.getTop();
            int maxDelta = Math.max(delta, 0);
            int dy = y - delta;
            int height = view.getHeight();
            int containerHeight = this.mScrollViewContainer.getHeight();
            int topThresshold = containerHeight / 5;
            int bottomThresshold = (containerHeight * 4) / 5;
            if (dy < topThresshold && maxDelta > 0) {
                this.mScrollViewContainer.scrollBy(0, (-topThresshold) / 8);
                return true;
            } else if (dy > bottomThresshold && delta + containerHeight < height) {
                this.mScrollViewContainer.scrollBy(0, topThresshold / 8);
                return true;
            }
        }
        return false;
    }
}
