package com.sygic.aura.poi.fragment;

import com.sygic.aura.poi.adapter.PoiAdapter;
import com.sygic.aura.poi.adapter.ViatorPoiAdapter;
import com.sygic.aura.views.font_specials.SToolbar;

public class ViatorPagerFragment extends PoiPagerFragment {
    public int getSearchType() {
        return 17;
    }

    public PoiAdapter getAdapter() {
        return new ViatorPoiAdapter(getActivity(), this.mLocationQuery, this.mSearchRef, this.mCallback.getSelectedLanguage(), this.mListener);
    }

    protected void inflateMenu(SToolbar toolbar) {
    }

    protected boolean isOnline() {
        return true;
    }
}
