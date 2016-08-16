package com.sygic.aura.route.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.sygic.aura.route.RouteManager;
import java.util.ArrayList;
import java.util.HashSet;

public class RouteAvoidsData implements Parcelable {
    public static final Creator<RouteAvoidsData> CREATOR;
    private static final String STR_PIPE_SPLIT = "\\|";
    protected transient boolean[] mAvoidsArray;
    protected transient int mAvoidsLength;
    protected transient int mCountryIndex;
    protected transient String mCountryIso;
    protected transient String mCountryName;

    /* renamed from: com.sygic.aura.route.data.RouteAvoidsData.1 */
    static class C15201 implements Creator<RouteAvoidsData> {
        C15201() {
        }

        public RouteAvoidsData createFromParcel(Parcel in) {
            return new RouteAvoidsData(null);
        }

        public RouteAvoidsData[] newArray(int size) {
            return new RouteAvoidsData[size];
        }
    }

    public RouteAvoidsData(String countryName) {
        this.mAvoidsLength = 8;
        this.mCountryName = countryName;
        this.mAvoidsArray = new boolean[this.mAvoidsLength];
    }

    public RouteAvoidsData(int countryIndex, String countryName, String countryIso) {
        this.mAvoidsLength = 8;
        this.mCountryIndex = countryIndex;
        this.mCountryName = countryName;
        this.mCountryIso = countryIso;
        this.mAvoidsArray = new boolean[this.mAvoidsLength];
    }

    private RouteAvoidsData(Parcel parcel) {
        this.mAvoidsLength = 8;
        readFromParcel(parcel);
    }

    public static RouteAvoidsData[] newArrayInstance(String nativeCountryList, String countryIsoList) {
        String[] arrCountries = TextUtils.split(nativeCountryList, STR_PIPE_SPLIT);
        String[] arrCountryISOs = TextUtils.split(countryIsoList, STR_PIPE_SPLIT);
        ArrayList<RouteAvoidsData> routeAvoidsData = new ArrayList(arrCountries.length);
        HashSet<String> alreadyCreated = new HashSet(arrCountries.length);
        for (int ind = 0; ind < arrCountries.length; ind++) {
            if (!alreadyCreated.contains(arrCountries[ind])) {
                alreadyCreated.add(arrCountries[ind]);
                RouteAvoidsData data = new RouteAvoidsData(ind, arrCountries[ind], arrCountryISOs[ind]);
                routeAvoidsData.add(data);
                loadCountryAvoids(data);
            }
        }
        return (RouteAvoidsData[]) routeAvoidsData.toArray(new RouteAvoidsData[routeAvoidsData.size()]);
    }

    private static void loadCountryAvoids(RouteAvoidsData avoidsData) {
        boolean z = true;
        int iRouteAvoids = RouteManager.nativeGetCountryRouteAvoids(avoidsData.getCountryIso());
        boolean[] zArr = new boolean[8];
        zArr[0] = (iRouteAvoids & 1) != 0;
        zArr[1] = (iRouteAvoids & 2) != 0;
        zArr[2] = (iRouteAvoids & 4) != 0;
        zArr[3] = (iRouteAvoids & 8) != 0;
        zArr[4] = (iRouteAvoids & 16) != 0;
        zArr[5] = (iRouteAvoids & 32) != 0;
        zArr[6] = (iRouteAvoids & 64) != 0;
        if ((iRouteAvoids & 128) == 0) {
            z = false;
        }
        zArr[7] = z;
        avoidsData.setAvoidsArray(zArr);
    }

    public boolean[] getAvoidsArray() {
        return this.mAvoidsArray;
    }

    public void setAvoidsArray(boolean[] avoidsArray) {
        this.mAvoidsLength = avoidsArray.length;
        this.mAvoidsArray = avoidsArray;
    }

    public void setAvoidEnabled(int avoidType, boolean enabled) {
        if (avoidType >= 0 && avoidType < this.mAvoidsLength) {
            this.mAvoidsArray[avoidType] = enabled;
        }
    }

    public String getCountryName() {
        return this.mCountryName;
    }

    public void setCountryName(String mCountryName) {
        this.mCountryName = mCountryName;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }

    public void setCountryIso(String mCountryIso) {
        this.mCountryIso = mCountryIso;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringArray(new String[]{this.mCountryIso, this.mCountryName});
        parcel.writeInt(this.mCountryIndex);
        parcel.writeInt(this.mAvoidsLength);
        parcel.writeBooleanArray(this.mAvoidsArray);
    }

    private void readFromParcel(Parcel parcel) {
        String[] stringArray = parcel.createStringArray();
        if (stringArray != null) {
            this.mCountryIso = stringArray[0];
            this.mCountryName = stringArray[1];
        }
        this.mCountryIndex = parcel.readInt();
        this.mAvoidsLength = parcel.readInt();
        this.mAvoidsArray = new boolean[this.mAvoidsLength];
        parcel.readBooleanArray(this.mAvoidsArray);
    }

    static {
        CREATOR = new C15201();
    }
}
