package com.sygic.aura.poi.fragment;

import com.sygic.aura.poi.adapter.PoiAdapter;

public class SygicPoiPagerFragment extends PoiPagerFragment {
    public int getSearchType() {
        return 5;
    }

    public PoiAdapter getAdapter() {
        return new PoiAdapter(getActivity(), this.mLocationQuery, this.mSearchRef, this.mListener);
    }

    protected boolean isOnline() {
        return false;
    }
}
