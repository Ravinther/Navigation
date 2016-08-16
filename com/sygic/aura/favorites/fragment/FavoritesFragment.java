package com.sygic.aura.favorites.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.favorites.FavoritesAdapter;
import com.sygic.aura.favorites.FavoritesAdapter.Mode;
import com.sygic.aura.favorites.model.FavoritesPagerAdapter;
import com.sygic.aura.favorites.model.PagerFavoritesFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.GuiUtils;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.ParcelableStringSparseArray;
import com.sygic.aura.keyboard.FocusableKeyboard.OnKeyboardVisibilityListener;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.search.model.data.RouteListItem;
import com.sygic.aura.views.font_specials.SToolbar;

public class FavoritesFragment extends AbstractScreenFragment implements FavoritesFragmentInterface, OnKeyboardVisibilityListener {
    private ViewPager mFavoritesPager;
    private FavoritesPagerAdapter mFavsPagerAdapter;
    private FavoritesAdapter mLastAdapter;
    private int mLastTab;
    private int mPrevPosition;
    private FavoritesItem mResult;

    /* renamed from: com.sygic.aura.favorites.fragment.FavoritesFragment.1 */
    class C11981 extends SimpleOnPageChangeListener {
        final /* synthetic */ View val$view;

        C11981(View view) {
            this.val$view = view;
        }

        public void onPageSelected(int position) {
            PagerFavoritesFragment pagerFragment = FavoritesFragment.this.mFavsPagerAdapter.getFragmentAt(position);
            pagerFragment.onUpdateToolbar(FavoritesFragment.this.mToolbar);
            if (FavoritesFragment.this.mPrevPosition != -1) {
                FavoritesFragment.this.closeContextActionBar(FavoritesFragment.this.mPrevPosition);
            }
            NaviNativeActivity.hideKeyboard(this.val$view.getWindowToken());
            FavoritesAdapter adapter = FavoritesFragment.this.getFragmentListAdapter(position);
            if (!(FavoritesFragment.this.mLastAdapter == null || FavoritesFragment.this.mLastAdapter.equals(adapter))) {
                FavoritesFragment.this.mLastAdapter.cancelFavoritesLoading();
            }
            FavoritesFragment.this.mLastAdapter = adapter;
            adapter.reinitializeCoreSearch();
            FavoritesFragment.this.mPrevPosition = position;
            pagerFragment.setupKeyboard();
        }
    }

    /* renamed from: com.sygic.aura.favorites.fragment.FavoritesFragment.2 */
    class C11992 implements Runnable {
        final /* synthetic */ View val$view;

        C11992(View view) {
            this.val$view = view;
        }

        public void run() {
            NaviNativeActivity.hideKeyboard(this.val$view.getWindowToken());
        }
    }

    public FavoritesFragment() {
        this.mResult = null;
        this.mPrevPosition = -1;
        setWantsNavigationData(false);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        int titleRes = getArguments().getInt("action_title_mode_id");
        if (titleRes == 0) {
            titleRes = 2131165314;
        }
        toolbar.setTitle(titleRes);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int requestedTab = getArguments().getInt("page_index", -1);
        if (requestedTab != -1) {
            this.mLastTab = requestedTab;
        } else {
            this.mLastTab = prefs.getInt(getString(2131166304), 0);
        }
        this.mFavsPagerAdapter = new FavoritesPagerAdapter(getChildFragmentManager(), this, this.mLocationQuery, this.mLastTab);
        this.mFavsPagerAdapter.setRouteFilter(getArguments().getBoolean("route_filter", false));
        initialiseExclusionFilter();
    }

    public void initialiseExclusionFilter() {
        this.mFavsPagerAdapter.setExclusionSearchDataFilter((ParcelableStringSparseArray) getArguments().getParcelable("exclusion_filter"));
    }

    private FavoritesAdapter getFragmentListAdapter(int pos) {
        FavoritesPagerAdapter favoritesPagerAdapter = this.mFavsPagerAdapter;
        ViewGroup viewGroup = this.mFavoritesPager;
        if (this.mFavoritesPager.getChildCount() > 0) {
            pos = Math.min(this.mFavoritesPager.getChildCount() - 1, pos);
        }
        return (FavoritesAdapter) ((ListFragment) favoritesPagerAdapter.instantiateItem(viewGroup, pos)).getListAdapter();
    }

    private void closeContextActionBar(int pos) {
        FavoritesPagerAdapter favoritesPagerAdapter = this.mFavsPagerAdapter;
        ViewGroup viewGroup = this.mFavoritesPager;
        if (this.mFavoritesPager.getChildCount() > 0) {
            pos = Math.min(this.mFavoritesPager.getChildCount() - 1, pos);
        }
        ((PagerFavoritesFragment) favoritesPagerAdapter.instantiateItem(viewGroup, pos)).closeContextActionBar();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903135, container, false);
        this.mFavoritesPager = (ViewPager) view.findViewById(2131624309);
        this.mFavoritesPager.setAdapter(this.mFavsPagerAdapter);
        this.mFavoritesPager.setOffscreenPageLimit(3);
        GuiUtils.setupIconTabsWithViewPager((TabLayout) view.findViewById(2131624308), this.mFavoritesPager, 2130838051, 2130838052, 2130838050);
        this.mFavoritesPager.setCurrentItem(this.mLastTab);
        this.mFavoritesPager.addOnPageChangeListener(new C11981(view));
        if (InCarConnection.isInCarConnected()) {
            NaviNativeActivity.getKeyboard().addKeyboardVisibilityListener(this);
        }
        return view;
    }

    public boolean onDeleteItem(FavoritesItem selectedItem) {
        if (!(selectedItem instanceof RouteListItem)) {
            return MemoManager.nativeRemoveItem(getActivity(), selectedItem.getMemoId());
        }
        MemoManager.nativeRemoveRoute(((RouteListItem) selectedItem).getItinerarPath());
        return true;
    }

    public void onRenameItem(long memoId, String newName) {
        MemoManager.nativeRenameItem(getActivity(), memoId, newName);
    }

    public void onReorderItem(long memoIdFrom, long memoIdTo) {
        MemoManager.nativeReorderItem(memoIdFrom, memoIdTo);
    }

    public void requestRefresh(Mode mode) {
        FavoritesAdapter adapter = getFragmentListAdapter(mode.ordinal());
        adapter.invalidateDataSet();
        adapter.reinitializeCoreSearch();
    }

    public void finish(FavoritesItem result) {
        this.mResult = result;
        this.mLastAdapter.cancelFavoritesLoading();
        performHomeAction();
    }

    public void onFirstPageLoaded(FavoritesAdapter adapter) {
        this.mLastAdapter = adapter;
    }

    public void onFirstFragmentCreated(PagerFavoritesFragment fragment) {
        fragment.onUpdateToolbar(this.mToolbar);
        fragment.setupKeyboard();
    }

    public Context getContext() {
        return getActivity();
    }

    public void onDestroyView() {
        View view = getView();
        if (view != null) {
            view.post(new C11992(view));
        }
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mResultCallback != null) {
            ((FavoritesFragmentResultCallback) this.mResultCallback).onFavoritesFragmentResult(this.mResult);
        }
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt(getString(2131166304), this.mFavoritesPager.getCurrentItem()).apply();
        if (InCarConnection.isInCarConnected()) {
            NaviNativeActivity.getKeyboard().removeKeyboardVisibilityListener(this);
        }
    }

    public void onVisibilityChanged(boolean visible) {
        boolean z;
        boolean z2 = false;
        SToolbar toolbar = (SToolbar) getView().findViewById(2131624226);
        TabLayout tabs = (TabLayout) getView().findViewById(2131624308);
        if (visible) {
            z = false;
        } else {
            z = true;
        }
        ResourceManager.makeControlVisible(toolbar, z, true);
        if (!visible) {
            z2 = true;
        }
        ResourceManager.makeControlVisible(tabs, z2, true);
    }
}
