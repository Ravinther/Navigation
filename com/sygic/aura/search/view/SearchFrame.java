package com.sygic.aura.search.view;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.favorites.fragment.FavoritesFragment;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.SearchInterface;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.search.LocationObserverIF;
import com.sygic.aura.search.model.RouteWaypointsAdapter;
import com.sygic.aura.search.model.RouteWaypointsAdapter.ViewHolder;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.SearchLocationData;
import com.sygic.aura.views.font_specials.SImageView;

public class SearchFrame extends LinearLayout implements FavoritesFragmentResultCallback, LocationObserverIF {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int BUBBLE_COUNT;
    private static final int[] BUBBLE_RES_IDS;
    private boolean mBoolBubbleToRemove;
    private final SearchBubble[] mBubbleTxtViews;
    private ImageView mClearBt;
    private SearchDataObserver mDataSetObserver;
    private boolean mDetached;
    private SearchBar mEditSearchBar;
    private SImageView mExpandButton;
    private final String mIconBookmark;
    private final String mIconDelete;
    private RouteWaypointsAdapter mParentAdapter;
    private int mPosition;
    private SearchInterface mSearchIF;
    private SearchLocationData mSrchLocationData;

    /* renamed from: com.sygic.aura.search.view.SearchFrame.1 */
    class C15981 implements OnClickListener {
        C15981() {
        }

        public void onClick(View view) {
            if (SearchFrame.this.mEditSearchBar.isEnabled()) {
                String name;
                if (SearchFrame.this.isEmpty() && SearchFrame.this.mEditSearchBar.isEmpty()) {
                    SearchFrame.this.showFavorites(view);
                    name = "favorites";
                } else {
                    SearchFrame.this.clearAllBubbles();
                    SearchFrame.this.mEditSearchBar.clearText();
                    SearchFrame.this.initCoreSearch();
                    SearchFrame.this.setSearchFinished(false);
                    SearchFrame.this.setSearchEditIcon(SearchFrame.this.mEditSearchBar.isEmpty());
                    name = "clear_bubbles";
                }
                SygicAnalyticsLogger.getAnalyticsEvent(SearchFrame.this.getContext(), EventType.SEARCH).setName("search").setValue("click", name).logAndRecycle();
            }
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchFrame.2 */
    class C15992 implements OnClickListener {
        C15992() {
        }

        public void onClick(View view) {
            if (VERSION.SDK_INT >= 21) {
                TransitionManager.beginDelayedTransition((ViewGroup) SearchFrame.this.getRootView().findViewById(2131624290), new AutoTransition().addListener(((ViewHolder) SearchFrame.this.getTag()).getTransactionListener()).setDuration(200));
            }
            int count = SearchFrame.this.mParentAdapter.getCount();
            if (count == 1 && SearchFrame.this.mPosition == 0) {
                SearchFrame.this.mParentAdapter.expand();
            } else if (SearchFrame.this.mPosition == 0) {
                SearchFrame.this.mParentAdapter.insertNewWaypoint();
            } else if (SearchFrame.this.mPosition == count - 1) {
                SearchFrame.this.mParentAdapter.collapse();
            } else {
                SearchFrame.this.mParentAdapter.removeWaypoint(SearchFrame.this.mPosition);
            }
            view.animate().rotationBy(180.0f).setDuration(300);
        }
    }

    private class SearchDataObserver extends DataSetObserver {
        private int mOldCount;

        private SearchDataObserver() {
            this.mOldCount = 0;
        }

        public void onChanged() {
            int count = SearchFrame.this.mParentAdapter.getCount();
            if (count != this.mOldCount && VERSION.SDK_INT < 21 && SearchFrame.this.mParentAdapter.getCurrentItemPosition() == SearchFrame.this.mPosition && SearchFrame.this.mEditSearchBar != null) {
                SearchFrame.this.initCoreSearch();
            }
            this.mOldCount = count;
            super.onChanged();
        }

        public void onInvalidated() {
            super.onInvalidated();
        }
    }

    static {
        $assertionsDisabled = !SearchFrame.class.desiredAssertionStatus();
        BUBBLE_RES_IDS = new int[]{2131624639, 2131624640, 2131624641, 2131624642};
        BUBBLE_COUNT = BUBBLE_RES_IDS.length;
    }

    public SearchFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBubbleTxtViews = new SearchBubble[BUBBLE_COUNT];
        this.mDetached = false;
        if (isInEditMode()) {
            this.mIconBookmark = null;
            this.mIconDelete = null;
            return;
        }
        this.mIconBookmark = context.getString(2131166243);
        this.mIconDelete = context.getString(2131166246);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mEditSearchBar = (SearchBar) findViewById(2131624643);
        this.mEditSearchBar.setParent(this);
        this.mExpandButton = (SImageView) findViewById(2131624645);
        for (int ind = 0; ind < BUBBLE_COUNT; ind++) {
            this.mBubbleTxtViews[ind] = (SearchBubble) findViewById(BUBBLE_RES_IDS[ind]);
            this.mBubbleTxtViews[ind].assignSearchBubble(this.mEditSearchBar);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if ($assertionsDisabled || getParent() != null) {
            ViewHolder tag = (ViewHolder) getTag();
            if (!isInEditMode()) {
                this.mSearchIF = tag.mSearchIF;
                this.mParentAdapter = tag.mAdapter;
                this.mDataSetObserver = new SearchDataObserver();
                this.mSrchLocationData.registerLocationObserver(this);
                this.mParentAdapter.registerDataSetObserver(this.mDataSetObserver);
                if (!this.mDetached) {
                    initClearButton();
                    initExpandButton();
                    if (isEmpty()) {
                        this.mEditSearchBar.setSearchBarHint(0);
                    }
                    if (this.mEditSearchBar != null && this.mParentAdapter.shouldShowKeyboardOnSearchFrameAdded()) {
                        NaviNativeActivity.showKeyboard(this.mEditSearchBar);
                    }
                }
            }
            this.mDetached = false;
            return;
        }
        throw new AssertionError();
    }

    private void showFavorites(View view) {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            Bundle bundle = new Bundle(1);
            bundle.putBoolean("route_filter", true);
            manager.addFragment(FavoritesFragment.class, "fragment_favorites_tag", true, this, bundle);
        }
        NaviNativeActivity.hideKeyboard(view.getWindowToken());
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mDetached = true;
        this.mParentAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        this.mSrchLocationData.unregisterLocationObserver(this);
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (this.mParentAdapter == null || this.mPosition != this.mParentAdapter.getCurrentItemPosition()) {
            return false;
        }
        return this.mEditSearchBar.requestFocus();
    }

    public void updateSearchFramePosition(int position, boolean requestFocus) {
        this.mPosition = position;
        if (requestFocus) {
            this.mEditSearchBar.requestFocus();
        } else {
            this.mEditSearchBar.clearFocus();
        }
    }

    private void initClearButton() {
        this.mClearBt = (ImageView) findViewById(2131624644);
        OnClickListener clickListener = new C15981();
        setSearchEditIcon(isEmpty());
        this.mClearBt.setOnClickListener(clickListener);
    }

    private void initExpandButton() {
        this.mExpandButton.setOnClickListener(new C15992());
        int count = this.mParentAdapter.getCount();
        Resources resources;
        if (this.mPosition == 0 && count > 1) {
            resources = getResources();
            this.mExpandButton.setFontDrawableChar(resources.getString(2131166241));
            this.mExpandButton.setFontDrawableColor(resources.getColor(2131558562));
        } else if (this.mPosition != count - 1) {
            resources = getResources();
            this.mExpandButton.setFontDrawableChar(resources.getString(2131166262));
            this.mExpandButton.setFontDrawableColor(resources.getColor(2131558515));
        } else if (this.mPosition != count - 1 || count <= 1) {
            this.mExpandButton.setRotation(90.0f);
        } else {
            this.mExpandButton.setRotation(270.0f);
        }
    }

    public void hideExpandButton() {
        findViewById(2131624645).setVisibility(8);
    }

    public void onFavoritesFragmentResult(FavoritesItem result) {
        int i = 0;
        if (result == null) {
            initCoreSearch();
        } else if (PositionInfo.nativeHasNavSel(result.getLongPosition())) {
            ListItem[] addressSegments = PositionInfo.nativeGetPositionSearchEntries(result.getMapSel());
            if (addressSegments != null) {
                this.mSrchLocationData.clearAllLocationData();
                setSearchFinished(true);
                int length = addressSegments.length;
                while (i < length) {
                    this.mSrchLocationData.addItem(addressSegments[i]);
                    i++;
                }
                return;
            }
            clearAllBubbles();
            setSearchEditIcon(true);
            this.mSearchIF.initCoreSearch(this.mEditSearchBar.getSearchText(), false);
        } else {
            SToast.makeText(getContext(), 2131165396, 1).show();
        }
    }

    public void setSearchLocationData(SearchLocationData locationData, boolean isCurrent) {
        if (locationData != null) {
            if (locationData != this.mSrchLocationData) {
                if (this.mSrchLocationData != null && this.mSrchLocationData.unregisterLocationObserver(this)) {
                    locationData.registerLocationObserver(this);
                }
                this.mSrchLocationData = locationData;
                onDataChanged();
            }
            setSearchFinished(this.mSrchLocationData.isTerminalBubble(), isCurrent);
        }
    }

    public boolean isEmpty() {
        return this.mSrchLocationData.isEmpty(0);
    }

    public void onDataChanged() {
        for (int i = 0; i < this.mBubbleTxtViews.length; i++) {
            if (this.mSrchLocationData.isNonEmpty(i)) {
                makeBubbleVisible(i, true);
            } else {
                makeBubbleInvisible(i, false);
            }
        }
    }

    public void notifyDataRemoved(int bubbleIndex, String removedText, boolean replaceWithText) {
        makeBubbleInvisible(bubbleIndex, true);
        if (replaceWithText) {
            this.mEditSearchBar.setText(removedText);
            this.mEditSearchBar.setSelection(removedText.length());
        }
    }

    public void notifyDataAdded(int bubbleIndex, boolean showBubble) {
        if (this.mSrchLocationData.isNonEmpty(bubbleIndex)) {
            if (this.mEditSearchBar.isEmpty()) {
                initCoreSearch(null);
            } else {
                this.mEditSearchBar.clearText();
            }
            if (showBubble) {
                makeBubbleVisible(bubbleIndex, true);
            }
        }
        setSearchEditIcon(isEmpty());
    }

    int getSearchSubtype() {
        return this.mSrchLocationData == null ? -1 : this.mSrchLocationData.getSearchSubtype();
    }

    public void setSearchBarState(boolean editable) {
        if (editable) {
            activateCurrentFrame();
        }
    }

    int shiftToPreviousSubtype() {
        return this.mSrchLocationData.shiftToPreviousSubtype();
    }

    int shiftToNextSubtype() {
        return this.mSrchLocationData.shiftToNextSubtype();
    }

    public void initCoreSearch() {
        if (this.mSearchIF != null) {
            this.mSearchIF.initCoreSearch(this.mEditSearchBar.getSearchText(), false);
        }
    }

    public void initCoreSearch(String searchQuery) {
        if (this.mSearchIF != null) {
            this.mSearchIF.initCoreSearch(searchQuery, false);
        }
    }

    public void initPreviousCoreSearch() {
        if (this.mSrchLocationData.getItem(getSearchSubtype()) != null) {
            this.mSrchLocationData.removeItem(getSearchSubtype(), false, false);
        }
        if (this.mSearchIF != null) {
            this.mSearchIF.initCoreSearch(null, true);
        }
    }

    public void queryCoreSearch(String searchQuery) {
        if (this.mSearchIF != null && !isTerminalBubble()) {
            this.mSearchIF.coreSearchForQuery(searchQuery);
        }
    }

    public int getPosition() {
        return this.mPosition;
    }

    public String getSearchText() {
        return this.mEditSearchBar.getSearchText();
    }

    public boolean isActive() {
        return this.mParentAdapter != null && this.mParentAdapter.getCurrentItemPosition() == this.mPosition;
    }

    public void activateCurrentFrame() {
        if (this.mParentAdapter != null && this.mParentAdapter.getCurrentItemPosition() != this.mPosition) {
            this.mParentAdapter.setCurrentItemPosition(getPosition());
            initCoreSearch();
        }
    }

    public boolean isStartFromCurrentLocation() {
        return this.mParentAdapter == null || this.mParentAdapter.isStartFromCurrentLocation();
    }

    public void disable() {
        this.mEditSearchBar.enable(false, false);
        setSearchBarState(false);
    }

    public void enable(boolean getFocus) {
        this.mEditSearchBar.enable(true, getFocus);
    }

    public void setSearchEditIcon(boolean isEmpty) {
        ((FontDrawable) this.mClearBt.getDrawable()).setText(isEmpty ? this.mIconBookmark : this.mIconDelete);
    }

    private boolean bubbleIndexOK(int bubbleIndex) {
        return bubbleIndex >= 0 && bubbleIndex < BUBBLE_COUNT && !this.mEditSearchBar.isAnimationRunning();
    }

    void makeBubbleInvisible() {
        makeBubbleInvisible(getSearchSubtype(), true);
    }

    void makeBubbleInvisible(int bubbleIndex, boolean withAnimation) {
        if (bubbleIndexOK(bubbleIndex)) {
            this.mSrchLocationData.removeItem(bubbleIndex, false);
            this.mBubbleTxtViews[bubbleIndex].makeBubbleVisible("", false, withAnimation);
        }
    }

    void makeBubbleVisible(int bubbleIndex, boolean withAnimation) {
        if (bubbleIndexOK(bubbleIndex)) {
            boolean toBeShown;
            String locationName = this.mSrchLocationData.getItemName(bubbleIndex);
            if (locationName != null) {
                toBeShown = true;
            } else {
                toBeShown = false;
            }
            if (toBeShown) {
                setBubbleViewStyle(bubbleIndex, 0);
                this.mEditSearchBar.setSearchBarHint(bubbleIndex + 1);
                if (getSearchSubtype() == 4) {
                    setSearchFinished(true);
                }
            }
            this.mBubbleTxtViews[bubbleIndex].makeBubbleVisible(locationName, toBeShown, withAnimation);
        }
    }

    void setBubbleViewStyle(int asSelected) {
        setBubbleViewStyle(getSearchSubtype() - 1, asSelected);
    }

    void setBubbleViewStyle(int bubbleIndex, int asSelected) {
        boolean z = true;
        if (asSelected == 2) {
            bubbleIndex = 0;
        }
        if (bubbleIndex >= 0) {
            this.mBubbleTxtViews[bubbleIndex].setBubbleViewStyle(asSelected);
        }
        if (asSelected != 1) {
            z = false;
        }
        this.mBoolBubbleToRemove = z;
    }

    boolean hasBubbleInRemoval() {
        return this.mBoolBubbleToRemove;
    }

    void unselectBubbleForRemoval() {
        this.mBoolBubbleToRemove = false;
    }

    public void clearAllBubbles() {
        for (int i = 0; i < this.mBubbleTxtViews.length; i++) {
            makeBubbleInvisible(i, false);
        }
        this.mSrchLocationData.clearAllLocationData();
        this.mEditSearchBar.setSearchBarHint(0);
    }

    public boolean isTerminalBubble() {
        return this.mSrchLocationData.isTerminalBubble();
    }

    public void setAsTerminalBubble(boolean isTerminalBubble) {
        this.mSrchLocationData.setAsTerminalBubble(isTerminalBubble);
        if (isTerminalBubble && this.mSearchIF != null) {
            this.mSearchIF.showResultsList(false);
        }
    }

    public void setSearchFinished(boolean isFinished) {
        setSearchFinished(isFinished, true);
    }

    public void setSearchFinished(boolean isFinished, boolean isCurrent) {
        this.mEditSearchBar.setSearchFinished(isFinished);
        if (isFinished && this.mSearchIF != null && isCurrent && this.mSrchLocationData.getCurrentItem() != null) {
            this.mSearchIF.zoomOnAddress(this.mSrchLocationData.getCurrentItem().getMapSel());
        }
    }

    public void onDelimiterPressed(String text) {
        this.mSearchIF.onDelimiterPressed(this.mSrchLocationData.getSearchSubtype(), text);
    }

    public int[] getStreetHouseNumbersMinMax() {
        if (this.mSearchIF != null) {
            return this.mSearchIF.getStreetHouseNumbersMinMax();
        }
        return PositionInfo.nativeGetStreetMinMaxHouseNum(this.mSrchLocationData.getSearchItemTreeEntry(1), this.mSrchLocationData.getSearchItemTreeEntry(2));
    }

    public boolean isHouseFirst() {
        return this.mSrchLocationData.isHouseFirst();
    }

    public void setLabel(int labelResId) {
        if (labelResId != 0) {
            ((ImageView) findViewById(2131624636)).setImageResource(labelResId);
        }
    }

    public View getExpandButton() {
        return this.mExpandButton;
    }
}
