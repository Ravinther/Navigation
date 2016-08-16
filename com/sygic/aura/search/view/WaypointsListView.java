package com.sygic.aura.search.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import com.sygic.aura.helper.TransitionManagerCompat.TransitionListenerProvider;
import com.sygic.aura.search.model.RoutePointChangeObserver;
import com.sygic.aura.search.model.RouteWaypointsAdapter;
import com.sygic.aura.search.model.RouteWaypointsAdapter.ViewHolder;
import com.sygic.aura.search.model.data.PoiListItem;
import loquendo.tts.engine.TTSConst;

public class WaypointsListView extends ScrollView implements TransitionListenerProvider, RoutePointChangeObserver {
    public static int SCROLL_OFFSET_LEFT;
    public static int SCROLL_OFFSET_RIGHT;
    private static final OnClickListener revertListener;
    private RouteWaypointsAdapter mAdapter;
    private final LinearLayout mContentLayout;
    private int mMaxHeightSpec;

    /* renamed from: com.sygic.aura.search.view.WaypointsListView.1 */
    static class C16051 implements OnClickListener {

        /* renamed from: com.sygic.aura.search.view.WaypointsListView.1.1 */
        class C16041 implements Runnable {
            final /* synthetic */ View val$parent;
            final /* synthetic */ View val$view;

            C16041(View view, View view2) {
                this.val$parent = view;
                this.val$view = view2;
            }

            public void run() {
                ViewHolder row = (ViewHolder) ((View) this.val$parent.getParent()).getTag();
                WaypointsListView listView = (WaypointsListView) ((View) this.val$view.getTag()).getParent();
                if (!row.mAdapter.setListOptionsVisible(false)) {
                    row.mSearchFrame.initCoreSearch();
                    row.mAdapter.setCurrentItemPosition(row.mSearchFrame.getPosition());
                    row.mSearchFrame.requestFocus();
                    listView.setRowsEnabled(true);
                }
            }
        }

        C16051() {
        }

        public void onClick(View view) {
            view.postDelayed(new C16041((View) view.getParent(), view), 350);
        }
    }

    /* renamed from: com.sygic.aura.search.view.WaypointsListView.2 */
    class C16062 implements Runnable {
        final /* synthetic */ int val$scrollTo;

        C16062(int i) {
            this.val$scrollTo = i;
        }

        public void run() {
            WaypointsListView.this.smoothScrollTo(0, this.val$scrollTo);
        }
    }

    /* renamed from: com.sygic.aura.search.view.WaypointsListView.3 */
    class C16073 implements TransitionListener {
        C16073() {
        }

        public void onTransitionStart(Transition transition) {
        }

        public void onTransitionEnd(Transition transition) {
            View childAt = WaypointsListView.this.mContentLayout.getChildAt(WaypointsListView.this.mAdapter.getCurrentItemPosition());
            childAt.requestFocus();
            ((ViewHolder) childAt.getTag()).mSearchFrame.initCoreSearch();
        }

        public void onTransitionCancel(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
        }

        public void onTransitionResume(Transition transition) {
        }
    }

    static {
        SCROLL_OFFSET_RIGHT = -1;
        SCROLL_OFFSET_LEFT = -1;
        revertListener = new C16051();
    }

    public WaypointsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(2);
        this.mMaxHeightSpec = getHeightMeasureSpec(context.getResources().getConfiguration());
        this.mContentLayout = new LinearLayout(context);
        this.mContentLayout.setOrientation(1);
        if (VERSION.SDK_INT >= 11) {
            this.mContentLayout.setDividerDrawable(getResources().getDrawable(2130837687));
            this.mContentLayout.setShowDividers(2);
        }
        addView(this.mContentLayout, new LayoutParams(-1, -2));
    }

    public void setAdapter(ListAdapter adapter) {
        this.mAdapter = (RouteWaypointsAdapter) adapter;
        this.mAdapter.registerRoutePointChangeObserver(this);
        onRoutePointCleared();
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            onRoutePointAdded(i);
        }
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        this.mMaxHeightSpec = getHeightMeasureSpec(newConfig);
        super.onConfigurationChanged(newConfig);
        smoothScrollToPosition(this.mAdapter.getCurrentItemPosition());
    }

    private int getHeightMeasureSpec(Configuration config) {
        int multiplier = 2;
        if (config.orientation != 2) {
            multiplier = 3;
        }
        return MeasureSpec.makeMeasureSpec((getResources().getDimensionPixelSize(2131231079) * multiplier) + 10, Integer.MIN_VALUE);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, this.mMaxHeightSpec);
    }

    private void setRowsEnabled(boolean enable) {
        int childCount = this.mContentLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewHolder row = (ViewHolder) this.mContentLayout.getChildAt(i).getTag();
            if (enable) {
                row.mSearchFrame.enable(false);
            } else {
                row.mSearchFrame.disable();
            }
        }
    }

    public View getViewAt(int position) {
        return this.mContentLayout.getChildAt(position);
    }

    public void onRoutePointAdded(int position) {
        int count = this.mContentLayout.getChildCount();
        this.mContentLayout.addView(this.mAdapter.getView(position, null, this.mContentLayout), position);
        int i = position + 1;
        while (i <= count) {
            ViewHolder row = (ViewHolder) this.mContentLayout.getChildAt(i).getTag();
            RouteWaypointsAdapter routeWaypointsAdapter = this.mAdapter;
            SearchFrame searchFrame = row.mSearchFrame;
            boolean z = VERSION.SDK_INT < 19 && i == this.mAdapter.getCurrentItemPosition();
            routeWaypointsAdapter.updateSearchFrame(searchFrame, i, z);
            i++;
        }
    }

    public void onRoutePointRemoved(int position) {
        int count = this.mContentLayout.getChildCount();
        this.mContentLayout.removeViewAt(position);
        int i = position;
        while (i < count - 1) {
            ViewHolder row = (ViewHolder) this.mContentLayout.getChildAt(i).getTag();
            RouteWaypointsAdapter routeWaypointsAdapter = this.mAdapter;
            SearchFrame searchFrame = row.mSearchFrame;
            boolean z = VERSION.SDK_INT < 19 && i == this.mAdapter.getCurrentItemPosition();
            routeWaypointsAdapter.updateSearchFrame(searchFrame, i, z);
            i++;
        }
    }

    public void onRoutePointCleared() {
        this.mContentLayout.removeAllViews();
    }

    public void smoothScrollToPosition(int position) {
        int scrollTo;
        switch (position) {
            case PoiListItem.ITEM_SPECIAL_NEARBY_POI /*-1*/:
                position = this.mContentLayout.getChildCount();
                break;
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                scrollTo = 0;
                break;
        }
        scrollTo = this.mContentLayout.getChildAt(0).getHeight() * position;
        postDelayed(new C16062(scrollTo), 200);
    }

    @TargetApi(19)
    public TransitionListener getTransitionListener() {
        return new C16073();
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return this.mContentLayout.requestFocus(direction, previouslyFocusedRect);
    }
}
