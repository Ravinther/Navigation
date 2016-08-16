package com.sygic.aura.poi.fragment;

import com.sygic.aura.poi.adapter.PoiAdapter;
import com.sygic.aura.poi.adapter.YelpPoiAdapter;

public class YelpPoiPagerFragment extends PoiPagerFragment {
    public int getSearchType() {
        return 15;
    }

    public PoiAdapter getAdapter() {
        return new YelpPoiAdapter(getActivity(), this.mLocationQuery, this.mSearchRef, this.mCallback.getSelectedLanguage(), this.mListener);
    }

    protected boolean isOnline() {
        return true;
    }
}
