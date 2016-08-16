package com.sygic.aura.poi.provider;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.poi.fragment.PoiPagerFragment;
import com.sygic.aura.poi.fragment.YelpPoiPagerFragment;
import com.sygic.aura.search.data.LocationQuery;

public class YelpPoiProvider extends PoiProvider {
    public static final Creator<YelpPoiProvider> CREATOR;

    /* renamed from: com.sygic.aura.poi.provider.YelpPoiProvider.1 */
    static class C14661 implements Creator<YelpPoiProvider> {
        C14661() {
        }

        public YelpPoiProvider createFromParcel(Parcel source) {
            return new YelpPoiProvider(null);
        }

        public YelpPoiProvider[] newArray(int size) {
            return new YelpPoiProvider[size];
        }
    }

    public int getIconRes() {
        return 2130838049;
    }

    public Fragment createFragment(LocationQuery query, Parcelable data, int groupId, boolean startLoading, PoiFragmentInterface callback) {
        return PoiPagerFragment.newInstance(YelpPoiPagerFragment.class, query, data, groupId, startLoading, callback);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    private YelpPoiProvider(Parcel in) {
    }

    static {
        CREATOR = new C14661();
    }
}
