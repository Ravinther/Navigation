package com.sygic.aura.poi.provider;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.poi.fragment.PoiPagerFragment;
import com.sygic.aura.poi.fragment.ViatorPagerFragment;
import com.sygic.aura.search.data.LocationQuery;

public class ViatorPoiProvider extends PoiProvider {
    public static final Creator<ViatorPoiProvider> CREATOR;

    /* renamed from: com.sygic.aura.poi.provider.ViatorPoiProvider.1 */
    static class C14651 implements Creator<ViatorPoiProvider> {
        C14651() {
        }

        public ViatorPoiProvider createFromParcel(Parcel source) {
            return new ViatorPoiProvider(null);
        }

        public ViatorPoiProvider[] newArray(int size) {
            return new ViatorPoiProvider[size];
        }
    }

    public int getIconRes() {
        return 2130838048;
    }

    public Fragment createFragment(LocationQuery query, Parcelable data, int groupId, boolean startLoading, PoiFragmentInterface callback) {
        return PoiPagerFragment.newInstance(ViatorPagerFragment.class, query, data, groupId, startLoading, callback);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    private ViatorPoiProvider(Parcel in) {
    }

    static {
        CREATOR = new C14651();
    }
}
