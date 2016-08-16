package com.sygic.aura.poi.fragment;

import com.sygic.aura.poi.adapter.FsqPoiAdapter;
import com.sygic.aura.poi.adapter.PoiAdapter;

public class FsqPoiPagerFragment extends PoiPagerFragment {
    public int getSearchType() {
        return 14;
    }

    public PoiAdapter getAdapter() {
        return new FsqPoiAdapter(getActivity(), this.mLocationQuery, this.mSearchRef, this.mCallback.getSelectedLanguage(), this.mListener);
    }

    protected boolean isOnline() {
        return true;
    }
}
