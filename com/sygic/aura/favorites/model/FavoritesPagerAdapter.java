package com.sygic.aura.favorites.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.favorites.FavoritesAdapter;
import com.sygic.aura.favorites.FavoritesAdapter.Mode;
import com.sygic.aura.favorites.fragment.FavoritesFragmentInterface;
import com.sygic.aura.search.data.LocationQuery;
import java.util.List;

public class FavoritesPagerAdapter extends FragmentPagerAdapter {
    private final SparseArray<List<String>> mExclusionSearchDataFilterMap;
    private final FavoritesFragmentInterface mFragmentInterface;
    private PagerFavoritesFragment[] mFragments;
    private int mLastPage;
    private final LocationQuery mLocationQuery;
    private boolean mRouteFilter;

    public FavoritesPagerAdapter(FragmentManager fm, FavoritesFragmentInterface fragmentInterface, LocationQuery locationQuery, int lastPage) {
        super(fm);
        this.mExclusionSearchDataFilterMap = new SparseArray();
        this.mFragmentInterface = fragmentInterface;
        this.mLocationQuery = locationQuery;
        this.mLastPage = lastPage;
        this.mFragments = new PagerFavoritesFragment[getCount()];
    }

    public Fragment getItem(int position) {
        PagerFavoritesFragment fragment = PagerFavoritesFragment.newInstance(this.mFragmentInterface, this.mLocationQuery, position, this.mLastPage == position);
        if (position == this.mLastPage) {
            this.mFragmentInterface.onFirstPageLoaded((FavoritesAdapter) fragment.getListAdapter());
        }
        return fragment;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        PagerFavoritesFragment fragment = (PagerFavoritesFragment) super.instantiateItem(container, position);
        FavoritesAdapter favouritesAdapter = (FavoritesAdapter) fragment.getListAdapter();
        favouritesAdapter.setExclusionSearchDataFilter((List) this.mExclusionSearchDataFilterMap.get(position));
        favouritesAdapter.setRouteFilter(this.mRouteFilter);
        this.mFragments[position] = fragment;
        return fragment;
    }

    public int getCount() {
        return 3;
    }

    public PagerFavoritesFragment getFragmentAt(int position) {
        return this.mFragments[position];
    }

    public void setExclusionSearchDataFilter(SparseArray<List<String>> exclusionSearchDataFilterMap) {
        if (exclusionSearchDataFilterMap != null) {
            this.mExclusionSearchDataFilterMap.put(Mode.MODE_BOOKMARKS.ordinal(), joinLists((List) exclusionSearchDataFilterMap.get(EWidgetType.widgetTypeFavorite.ordinal()), (List) exclusionSearchDataFilterMap.get(EWidgetType.widgetTypeFavoriteRoute.ordinal())));
            this.mExclusionSearchDataFilterMap.put(Mode.MODE_RECENT.ordinal(), exclusionSearchDataFilterMap.get(EWidgetType.widgetTypeRecent.ordinal()));
            this.mExclusionSearchDataFilterMap.put(Mode.MODE_CONTACTS.ordinal(), exclusionSearchDataFilterMap.get(EWidgetType.widgetTypeContact.ordinal()));
            return;
        }
        this.mExclusionSearchDataFilterMap.clear();
    }

    private List<String> joinLists(List<String> list1, List<String> list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        list1.addAll(list2);
        return list1;
    }

    public void setRouteFilter(boolean doFilter) {
        this.mRouteFilter = doFilter;
    }
}
