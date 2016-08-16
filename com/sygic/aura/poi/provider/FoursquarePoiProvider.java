package com.sygic.aura.poi.provider;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.poi.fragment.FsqPoiPagerFragment;
import com.sygic.aura.poi.fragment.PoiPagerFragment;
import com.sygic.aura.search.data.LocationQuery;

public class FoursquarePoiProvider extends PoiProvider {
    public static final Creator<FoursquarePoiProvider> CREATOR;

    /* renamed from: com.sygic.aura.poi.provider.FoursquarePoiProvider.1 */
    static class C14621 implements Creator<FoursquarePoiProvider> {
        C14621() {
        }

        public FoursquarePoiProvider createFromParcel(Parcel source) {
            return new FoursquarePoiProvider(null);
        }

        public FoursquarePoiProvider[] newArray(int size) {
            return new FoursquarePoiProvider[size];
        }
    }

    public int getIconRes() {
        return 2130838045;
    }

    public Fragment createFragment(LocationQuery query, Parcelable data, int groupId, boolean startLoading, PoiFragmentInterface callback) {
        return PoiPagerFragment.newInstance(FsqPoiPagerFragment.class, query, data, groupId, startLoading, callback);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    private FoursquarePoiProvider(Parcel in) {
    }

    static {
        CREATOR = new C14621();
    }
}
