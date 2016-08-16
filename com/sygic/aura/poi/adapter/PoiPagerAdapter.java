package com.sygic.aura.poi.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.poi.provider.PoiProvider;
import com.sygic.aura.search.data.LocationQuery;

public class PoiPagerAdapter extends FragmentPagerAdapter {
    private final PoiFragmentInterface mCallback;
    private final Parcelable mData;
    private final int mDefaultPage;
    private final int mGroupId;
    private final LocationQuery mLocationQuery;
    private final PoiProvider[] mPoiProviders;

    public PoiPagerAdapter(FragmentManager fm, PoiProvider[] poiProviders, int defaultPage, LocationQuery locationQuery, Parcelable data, int groupId, PoiFragmentInterface callback) {
        super(fm);
        this.mPoiProviders = poiProviders;
        this.mDefaultPage = defaultPage;
        this.mLocationQuery = locationQuery;
        this.mData = data;
        this.mGroupId = groupId;
        this.mCallback = callback;
    }

    public Fragment getItem(int position) {
        return this.mPoiProviders[position].createFragment(this.mLocationQuery, this.mData, this.mGroupId, position == this.mDefaultPage, this.mCallback);
    }

    public int getCount() {
        return this.mPoiProviders.length;
    }
}
