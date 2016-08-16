package com.sygic.aura.poi.provider;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.poi.fragment.PoiPagerFragment;
import com.sygic.aura.poi.fragment.SygicPoiPagerFragment;
import com.sygic.aura.search.data.LocationQuery;

public class SygicPoiProvider extends PoiProvider {
    public static final Creator<SygicPoiProvider> CREATOR;

    /* renamed from: com.sygic.aura.poi.provider.SygicPoiProvider.1 */
    static class C14631 implements Creator<SygicPoiProvider> {
        C14631() {
        }

        public SygicPoiProvider createFromParcel(Parcel source) {
            return new SygicPoiProvider(null);
        }

        public SygicPoiProvider[] newArray(int size) {
            return new SygicPoiProvider[size];
        }
    }

    public int getIconRes() {
        return 2130838046;
    }

    public Fragment createFragment(LocationQuery query, Parcelable data, int groupId, boolean startLoading, PoiFragmentInterface callback) {
        return PoiPagerFragment.newInstance(SygicPoiPagerFragment.class, query, data, groupId, startLoading, callback);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    private SygicPoiProvider(Parcel in) {
    }

    static {
        CREATOR = new C14631();
    }
}
