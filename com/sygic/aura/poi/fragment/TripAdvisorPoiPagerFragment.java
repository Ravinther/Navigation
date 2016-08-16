package com.sygic.aura.poi.fragment;

import com.sygic.aura.poi.adapter.PoiAdapter;
import com.sygic.aura.poi.adapter.TripAdvisorPoiAdapter;

public class TripAdvisorPoiPagerFragment extends PoiPagerFragment {
    public int getSearchType() {
        return 16;
    }

    public PoiAdapter getAdapter() {
        return new TripAdvisorPoiAdapter(getActivity(), this.mLocationQuery, this.mSearchRef, this.mCallback.getSelectedLanguage(), this.mListener);
    }

    protected boolean isOnline() {
        return true;
    }
}
