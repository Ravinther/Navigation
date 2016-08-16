package com.sygic.aura.poi.provider;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.poi.fragment.PoiPagerFragment;
import com.sygic.aura.poi.fragment.TripAdvisorPoiPagerFragment;
import com.sygic.aura.search.data.LocationQuery;

public class TripAdvisorPoiProvider extends PoiProvider {
    public static final Creator<TripAdvisorPoiProvider> CREATOR;

    /* renamed from: com.sygic.aura.poi.provider.TripAdvisorPoiProvider.1 */
    static class C14641 implements Creator<TripAdvisorPoiProvider> {
        C14641() {
        }

        public TripAdvisorPoiProvider createFromParcel(Parcel source) {
            return new TripAdvisorPoiProvider(null);
        }

        public TripAdvisorPoiProvider[] newArray(int size) {
            return new TripAdvisorPoiProvider[size];
        }
    }

    public int getIconRes() {
        return 2130838047;
    }

    public Fragment createFragment(LocationQuery query, Parcelable data, int groupId, boolean startLoading, PoiFragmentInterface callback) {
        return PoiPagerFragment.newInstance(TripAdvisorPoiPagerFragment.class, query, data, groupId, startLoading, callback);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    private TripAdvisorPoiProvider(Parcel in) {
    }

    static {
        CREATOR = new C14641();
    }
}
