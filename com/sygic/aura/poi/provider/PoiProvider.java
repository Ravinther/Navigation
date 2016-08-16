package com.sygic.aura.poi.provider;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.search.data.LocationQuery;

public abstract class PoiProvider implements Parcelable {
    public abstract Fragment createFragment(LocationQuery locationQuery, Parcelable parcelable, int i, boolean z, PoiFragmentInterface poiFragmentInterface);

    public abstract int getIconRes();
}
