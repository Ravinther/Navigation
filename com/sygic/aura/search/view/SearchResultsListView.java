package com.sygic.aura.search.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.favorites.FavoritesAdapter;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.poi.PoiFragmentResultCallback;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.RouteWaypointsAdapter;
import com.sygic.aura.search.view.PoiHolderView.OnMoreClickListener;
import com.sygic.aura.views.ButtonScrollListView;
import loquendo.tts.engine.TTSConst;

public class SearchResultsListView extends ButtonScrollListView {
    private int mHeaderHeight;
    private View mHeaderSpace;
    private boolean mHistoryEnabled;
    private View mHistoryHeader;
    private int mHistoryHeaderHeight;
    private HistorySection mHistorySection;
    private boolean mIsKeyboardVisible;
    private int mItemHeight;
    private LayoutParams mListParams;
    private PoiHolderView mPoiSection;
    private ScrollState mScrollState;
    private View mScrollView;

    /* renamed from: com.sygic.aura.search.view.SearchResultsListView.1 */
    class C16001 implements LoadingStateListener {
        private boolean loadingDone;
        final /* synthetic */ FavoritesFragmentResultCallback val$callback;
        final /* synthetic */ FavoritesAdapter val$listAdapter;

        C16001(FavoritesAdapter favoritesAdapter, FavoritesFragmentResultCallback favoritesFragmentResultCallback) {
            this.val$listAdapter = favoritesAdapter;
            this.val$callback = favoritesFragmentResultCallback;
        }

        public void onLoadingStarted() {
        }

        public void onFirstNonEmptyTick() {
            showHistory(this.val$listAdapter.getCount());
            if (this.loadingDone) {
                this.val$listAdapter.cancelFavoritesLoading();
            }
        }

        public void onLoadingFinished(boolean isEmpty) {
            if (!this.loadingDone) {
                showHistory(this.val$listAdapter.getCount());
            }
            this.val$listAdapter.destroySearchObjectReference();
        }

        public void onLoadingError() {
        }

        private void showHistory(int count) {
            SearchResultsListView.this.mHistorySection.showSections(this.val$callback, count);
            this.loadingDone = count > 2;
            SearchResultsListView.this.mHistoryHeader.measure(MeasureSpec.makeMeasureSpec(-1, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(-2, Integer.MIN_VALUE));
            SearchResultsListView.this.mHistoryHeaderHeight = SearchResultsListView.this.mHistoryHeader.getMeasuredHeight();
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchResultsListView.2 */
    class C16012 implements OnGlobalLayoutListener {
        private final transient Rect rect;
        private transient int windowHeight;

        C16012() {
            this.rect = new Rect();
        }

        public void onGlobalLayout() {
            if (SearchResultsListView.this.getVisibility() == 0) {
                boolean isKeyboardVisible;
                SearchResultsListView.this.getWindowVisibleDisplayFrame(this.rect);
                int visibleHeight = this.rect.bottom - this.rect.top;
                int listBottom = SearchResultsListView.this.getBottom();
                SearchResultsListView.logResultsMsg("CONTAINER BOTTOM = ".concat(Integer.toString(listBottom)));
                if (this.windowHeight == 0) {
                    this.windowHeight = SearchResultsListView.this.getRootView().getHeight();
                }
                if (this.windowHeight - visibleHeight > 100) {
                    isKeyboardVisible = true;
                } else {
                    isKeyboardVisible = false;
                }
                int itemsHeight = 0;
                if (SearchResultsListView.this.getCount() > SearchResultsListView.this.getHeaderViewsCount()) {
                    View childView = SearchResultsListView.this.getAdapter().getView(SearchResultsListView.this.getHeaderViewsCount(), null, SearchResultsListView.this);
                    if (childView.getLayoutParams().height >= 0) {
                        SearchResultsListView.this.mItemHeight = childView.getLayoutParams().height;
                    } else {
                        childView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
                        SearchResultsListView.this.mItemHeight = childView.getMeasuredHeight();
                    }
                    itemsHeight = SearchResultsListView.this.getCount() * SearchResultsListView.this.mItemHeight;
                }
                if (SearchResultsListView.this.mListParams != null && SearchResultsListView.this.mListParams.height > itemsHeight) {
                    SearchResultsListView.this.mListParams.height = -2;
                    SearchResultsListView.this.setLayoutParams(SearchResultsListView.this.mListParams);
                }
                if (SearchResultsListView.this.mIsKeyboardVisible != isKeyboardVisible || listBottom > visibleHeight) {
                    SearchResultsListView.this.mIsKeyboardVisible = isKeyboardVisible;
                    if (SearchResultsListView.this.mListParams == null) {
                        SearchResultsListView.this.mListParams = SearchResultsListView.this.getLayoutParams();
                    }
                }
            }
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchResultsListView.3 */
    class C16023 implements OnLayoutChangeListener {
        C16023() {
        }

        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            int newHeight = bottom - top;
            if (oldBottom - oldTop != newHeight) {
                SearchResultsListView.this.mHeaderHeight = newHeight;
                SearchResultsListView.this.mHeaderSpace.getLayoutParams().height = newHeight;
                SearchResultsListView.this.mHeaderSpace.requestLayout();
            }
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchResultsListView.4 */
    static /* synthetic */ class C16034 {
        static final /* synthetic */ int[] f1284x50c2819;

        static {
            f1284x50c2819 = new int[ScrollState.values().length];
            try {
                f1284x50c2819[ScrollState.SHOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1284x50c2819[ScrollState.OFF_SCREEN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1284x50c2819[ScrollState.RETURNING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @TargetApi(11)
    private abstract class ListScrollListener implements OnScrollListener {
        private int mScroll;
        private int mSkipOffset;
        private int mYOffset;

        protected abstract void animatorCancel();

        protected abstract void animatorTranslationY(float f);

        protected abstract boolean hasAnimator();

        protected abstract void initAnimator(View view);

        private ListScrollListener() {
            this.mYOffset = 0;
            this.mSkipOffset = 0;
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (SearchResultsListView.this.mScrollView != null) {
                if (!hasAnimator()) {
                    initAnimator(SearchResultsListView.this.mScrollView);
                }
                switch (scrollState) {
                    case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                        if (ScrollState.RETURNING.equals(SearchResultsListView.this.mScrollState)) {
                            boolean onTop = this.mYOffset - this.mSkipOffset < SearchResultsListView.this.mHeaderHeight;
                            if (onTop || this.mScroll >= (-SearchResultsListView.this.mHeaderHeight) / 2 || SearchResultsListView.this.mHeaderHeight + this.mScroll >= 90) {
                                this.mSkipOffset = (onTop ? SearchResultsListView.this.mHeaderHeight + this.mScroll : -this.mScroll) + this.mSkipOffset;
                                animatorTranslationY(0.0f);
                                SearchResultsListView.this.mScrollState = ScrollState.SHOWN;
                                return;
                            }
                            this.mSkipOffset += SearchResultsListView.this.mHeaderHeight - this.mScroll;
                            animatorTranslationY((float) (-SearchResultsListView.this.mHeaderHeight));
                        } else if (!ScrollState.SHOWN.equals(SearchResultsListView.this.mScrollState)) {
                        } else {
                            if (this.mScroll > SearchResultsListView.this.mHeaderHeight / 2) {
                                this.mSkipOffset += SearchResultsListView.this.mHeaderHeight - this.mScroll;
                                animatorTranslationY((float) (-SearchResultsListView.this.mHeaderHeight));
                                SearchResultsListView.this.mScrollState = ScrollState.OFF_SCREEN;
                            } else if (this.mScroll > 0) {
                                this.mSkipOffset += -this.mScroll;
                                animatorTranslationY(0.0f);
                            }
                        }
                    case TTSConst.TTSMULTILINE /*1*/:
                        NaviNativeActivity.hideKeyboard(view.getWindowToken());
                    default:
                }
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (SearchResultsListView.this.mScrollView != null) {
                if (!hasAnimator()) {
                    initAnimator(SearchResultsListView.this.mScrollView);
                }
                View firstChild = SearchResultsListView.this.getChildAt(0);
                int offset = this.mSkipOffset;
                if (firstChild != null) {
                    switch (firstVisibleItem) {
                        case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                            offset += -firstChild.getTop();
                            break;
                        case TTSConst.TTSMULTILINE /*1*/:
                            offset += SearchResultsListView.this.mHeaderHeight - firstChild.getTop();
                            break;
                        default:
                            boolean hasHistoryHeader;
                            if (SearchResultsListView.this.getHeaderViewsCount() == 2) {
                                hasHistoryHeader = true;
                            } else {
                                hasHistoryHeader = false;
                            }
                            offset += ((SearchResultsListView.this.mHeaderHeight + (hasHistoryHeader ? SearchResultsListView.this.mHistoryHeaderHeight - SearchResultsListView.this.mItemHeight : SearchResultsListView.this.mItemHeight)) + ((firstVisibleItem - 1) * SearchResultsListView.this.mItemHeight)) - firstChild.getTop();
                            break;
                    }
                }
                switch (C16034.f1284x50c2819[SearchResultsListView.this.mScrollState.ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        this.mScroll = Math.min(offset - this.mYOffset, SearchResultsListView.this.mHeaderHeight);
                        if (offset >= this.mYOffset) {
                            animatorCancel();
                            SearchResultsListView.this.mScrollView.setTranslationY((float) (-this.mScroll));
                            if (this.mScroll >= SearchResultsListView.this.mHeaderHeight) {
                                SearchResultsListView.this.mScrollState = ScrollState.OFF_SCREEN;
                                return;
                            }
                            return;
                        }
                        this.mYOffset = offset;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        if (offset < this.mYOffset) {
                            SearchResultsListView.this.mScrollState = ScrollState.RETURNING;
                        } else {
                            this.mYOffset = offset;
                        }
                    case TTSConst.TTSUNICODE /*3*/:
                        this.mScroll = (-SearchResultsListView.this.mHeaderHeight) + (this.mYOffset - offset);
                        if (this.mScroll >= 0) {
                            this.mScroll = 0;
                            SearchResultsListView.this.mScrollState = ScrollState.SHOWN;
                        } else if (this.mScroll < (-SearchResultsListView.this.mHeaderHeight)) {
                            this.mScroll = -SearchResultsListView.this.mHeaderHeight;
                            SearchResultsListView.this.mScrollState = ScrollState.OFF_SCREEN;
                        }
                        animatorCancel();
                        SearchResultsListView.this.mScrollView.setTranslationY((float) this.mScroll);
                    default:
                }
            }
        }
    }

    @TargetApi(14)
    private class ListScrollListenerImpl extends ListScrollListener {
        private ViewPropertyAnimator mAnimator;

        private ListScrollListenerImpl() {
            super(null);
        }

        protected void initAnimator(View view) {
            this.mAnimator = view.animate().setDuration(200);
        }

        protected boolean hasAnimator() {
            return this.mAnimator != null;
        }

        protected void animatorTranslationY(float value) {
            this.mAnimator = this.mAnimator.translationY(value);
        }

        protected void animatorCancel() {
            this.mAnimator.cancel();
        }
    }

    private class ListScrollListenerImplLegacy extends ListScrollListener {
        private com.nineoldandroids.view.ViewPropertyAnimator mAnimator;

        private ListScrollListenerImplLegacy() {
            super(null);
        }

        protected void initAnimator(View view) {
            this.mAnimator = com.nineoldandroids.view.ViewPropertyAnimator.animate(view).setDuration(200);
        }

        protected boolean hasAnimator() {
            return this.mAnimator != null;
        }

        protected void animatorTranslationY(float value) {
            this.mAnimator = this.mAnimator.translationY(value);
        }

        protected void animatorCancel() {
            this.mAnimator.cancel();
        }
    }

    private enum ScrollState {
        SHOWN,
        OFF_SCREEN,
        RETURNING
    }

    public SearchResultsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHeaderHeight = 0;
        this.mHistoryHeaderHeight = 0;
        this.mScrollState = ScrollState.SHOWN;
        this.mHistoryEnabled = true;
        init(context);
    }

    public SearchResultsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHeaderHeight = 0;
        this.mHistoryHeaderHeight = 0;
        this.mScrollState = ScrollState.SHOWN;
        this.mHistoryEnabled = true;
        init(context);
    }

    public void enableHistory(boolean enable) {
        if (VERSION.SDK_INT >= 11) {
            if (enable && !this.mHistoryEnabled) {
                addHeaderView(this.mHistoryHeader);
                this.mHistoryEnabled = true;
                smoothScrollToPosition(0);
            } else if (!enable && this.mHistoryEnabled) {
                removeHeaderView(this.mHistoryHeader);
                this.mHistoryEnabled = false;
                smoothScrollToPosition(0);
            }
        }
    }

    public void initPoiShortcuts(RouteWaypointsAdapter waypointsAdapter, PoiFragmentResultCallback callback, OnMoreClickListener moreListener) {
        this.mPoiSection.setData(waypointsAdapter, callback, moreListener);
    }

    public void initHistory(LocationQuery locationQuery, FavoritesFragmentResultCallback callback) {
        FavoritesAdapter listAdapter = new FavoritesAdapter(getContext(), locationQuery, 1);
        this.mHistorySection.setAdapter(listAdapter);
        listAdapter.registerFavouritieLoadedListener(new C16001(listAdapter, callback));
        listAdapter.query("");
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View headerView = inflater.inflate(2130903200, this, false);
        this.mHistoryHeader = inflater.inflate(2130903201, this, false);
        this.mHistorySection = (HistorySection) this.mHistoryHeader.findViewById(2131624472);
        this.mPoiSection = (PoiHolderView) this.mHistoryHeader.findViewById(2131624474);
        this.mHeaderSpace = headerView.findViewById(2131624159);
        addHeaderView(headerView, null, false);
        addHeaderView(this.mHistoryHeader, null, false);
        ListScrollListener scrollListener = VERSION.SDK_INT >= 14 ? new ListScrollListenerImpl() : VERSION.SDK_INT >= 11 ? new ListScrollListenerImplLegacy() : null;
        setOnScrollListener(scrollListener);
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new C16012());
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 8) {
            resetScroll();
        }
    }

    public void setScrollView(View view) {
        this.mScrollView = view;
        if (VERSION.SDK_INT >= 11) {
            this.mScrollView.addOnLayoutChangeListener(new C16023());
        }
    }

    public void resetScroll() {
        smoothScrollToPosition(0);
        this.mScrollView.setTranslationY(0.0f);
    }

    private static void logResultsMsg(String logMsg) {
    }
}
