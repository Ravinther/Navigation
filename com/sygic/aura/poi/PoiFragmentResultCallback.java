package com.sygic.aura.poi;

import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.search.model.data.PoiListItem;

public interface PoiFragmentResultCallback extends FragmentResultCallback {
    boolean dismissOnFinish();

    void onPoiFragmentResult(PoiListItem poiListItem);
}
