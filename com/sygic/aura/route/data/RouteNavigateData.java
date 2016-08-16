package com.sygic.aura.route.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.fragments.interfaces.SearchInterface;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.interfaces.RouteCancelListener;
import com.sygic.aura.helper.interfaces.RouteDataChangeListener;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.data.map_selection.MapSelection.ENavSelType;
import com.sygic.aura.route.overview.AvoidsAdapter;
import com.sygic.aura.search.model.data.CityPostalSearchItem;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.SearchItem;
import com.sygic.aura.search.model.data.SearchLocationData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RouteNavigateData implements Parcelable, RouteCancelListener, RouteDataChangeListener, RouteNavigateDataCallback {
    public static final Creator<RouteNavigateData> CREATOR;
    private boolean hasEntryCurrentLocation;
    private String mAvoidCountryISO;
    private int mAvoidCountryIndex;
    private boolean mAvoidFlag;
    private int mAvoidFlagIndex;
    private final transient Set<AvoidsAdapter> mAvoidsObservers;
    private ArrayList<RouteAvoidsData> mCountryAvoidsArray;
    private boolean mIsRouteReady;
    private boolean mIsStartFromCurrentLocation;
    private final transient Set<SearchInterface> mObservers;
    private int mWaypointIndex;
    private final ArrayList<SearchLocationData> mWaypointsArray;

    /* renamed from: com.sygic.aura.route.data.RouteNavigateData.1 */
    static class C15211 implements Creator<RouteNavigateData> {
        C15211() {
        }

        public RouteNavigateData createFromParcel(Parcel in) {
            return new RouteNavigateData(null);
        }

        public RouteNavigateData[] newArray(int size) {
            return new RouteNavigateData[size];
        }
    }

    public RouteNavigateData() {
        this.mWaypointsArray = new ArrayList();
        this.mCountryAvoidsArray = new ArrayList();
        this.mIsStartFromCurrentLocation = true;
        this.mIsRouteReady = false;
        this.mObservers = new HashSet(1);
        this.mAvoidsObservers = new HashSet(1);
        RouteEventsReceiver.registerRouteDataChangeListener(this);
        MapEventsReceiver.registerRouteCancelListener(this);
    }

    private RouteNavigateData(Parcel parcel) {
        this.mWaypointsArray = new ArrayList();
        this.mCountryAvoidsArray = new ArrayList();
        readFromParcel(parcel);
        this.mObservers = new HashSet(1);
        this.mAvoidsObservers = new HashSet(1);
        RouteEventsReceiver.registerRouteDataChangeListener(this);
        MapEventsReceiver.registerRouteCancelListener(this);
    }

    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    public void destroy() {
        RouteEventsReceiver.unregisterRouteDataChangeListener(this);
        MapEventsReceiver.unregisterRouteCancelListener(this);
        this.mObservers.clear();
        this.mAvoidsObservers.clear();
        this.mCountryAvoidsArray.clear();
        this.mWaypointsArray.clear();
    }

    public void changeStart(MapSelection mapSelection) {
        if (!this.mIsStartFromCurrentLocation) {
            removeWaypoint(0);
        }
        insertNewWaypoint(0, mapSelection);
        this.mIsStartFromCurrentLocation = false;
    }

    public boolean changeStart() {
        if (!this.mIsStartFromCurrentLocation) {
            return false;
        }
        this.mIsStartFromCurrentLocation = false;
        insertNewWaypoint(0, false);
        return true;
    }

    public void setStartNotFromCurrent() {
        this.mIsStartFromCurrentLocation = false;
    }

    public int insertNewWaypoint() {
        return insertNewWaypoint(getDestinationWaypointIndex(), true);
    }

    public int insertEmptyWaypoint(int position) {
        return insertNewWaypoint(position, false);
    }

    public int insertNewWaypoint(int insertAt, MapSelection mapSel) {
        SearchLocationData locationData = prepareLocationData(mapSel);
        locationData.setRouteCallback(this);
        this.mWaypointsArray.add(insertAt, locationData);
        computeRouteReady();
        return insertAt;
    }

    private int insertNewWaypoint(int insertAt, boolean preloadCountry) {
        SearchLocationData locationData = new SearchLocationData();
        if (preloadCountry && PositionInfo.nativeIsVehicleOnMap()) {
            ListItem[] items = PositionInfo.nativeGetActualPositionSearchEntries();
            if (items != null && items.length > 0 && 0 < items.length) {
                locationData.setSelectedItem(items[0]);
                locationData.shiftToNextSubtype();
            }
        }
        locationData.setRouteCallback(this);
        this.mWaypointsArray.add(insertAt, locationData);
        resetRouteReady(locationData);
        return insertAt;
    }

    private void insertNewWaypoint(int insertAt, ListItem[] items) {
        if (insertAt >= 0) {
            SearchLocationData locationData = new SearchLocationData();
            if (items != null && items.length > 0) {
                for (ListItem listItem : items) {
                    locationData.setSelectedItem(listItem);
                    locationData.shiftToNextSubtype();
                }
            }
            locationData.setRouteCallback(this);
            this.mWaypointsArray.add(insertAt, locationData);
            resetRouteReady(locationData);
        }
    }

    private int getDestinationWaypointIndex() {
        return Math.max(0, this.mWaypointsArray.size() - 1);
    }

    public SearchLocationData getDestinationLocation() {
        return getWaypoint(getDestinationWaypointIndex());
    }

    public void removeWaypoint(int waypointIndex) {
        int waypointsCount = this.mWaypointsArray.size();
        if (waypointsCount > 0 && waypointIndex < waypointsCount) {
            if (waypointIndex == 0 && !this.mIsStartFromCurrentLocation) {
                this.mIsStartFromCurrentLocation = true;
            }
            this.mWaypointsArray.remove(waypointIndex);
            computeRouteReady();
        }
    }

    public void updateRouteNaviData(MapSelection[] mapSelections) {
        if (mapSelections != null && mapSelections.length != 0) {
            ArrayList<SearchLocationData> waypointsArray = new ArrayList(mapSelections.length);
            for (MapSelection prepareLocationData : mapSelections) {
                waypointsArray.add(prepareLocationData(prepareLocationData));
            }
            updateWaypointsArray(waypointsArray);
        }
    }

    private SearchLocationData prepareLocationData(MapSelection mapSelection) {
        ListItem[] items = PositionInfo.nativeGetPositionSearchEntries(mapSelection);
        SearchLocationData locationData = new SearchLocationData();
        if (items != null && items.length > 0) {
            for (ListItem listItem : items) {
                locationData.setSelectedItem(listItem);
                locationData.shiftToNextSubtype();
            }
        }
        locationData.setRouteCallback(this);
        return locationData;
    }

    public ArrayList<SearchLocationData> getWaypointsList() {
        return this.mWaypointsArray;
    }

    public int getWaypointsCount() {
        return this.mWaypointsArray.size();
    }

    public SearchLocationData getWaypoint(int waypointIndex) {
        return waypointIndex >= this.mWaypointsArray.size() ? null : (SearchLocationData) this.mWaypointsArray.get(Math.max(0, waypointIndex));
    }

    public void updateWaypointsArray(ArrayList<SearchLocationData> waypointsArray) {
        clearRouteWaypoints();
        this.mWaypointsArray.addAll(waypointsArray);
        Iterator it = this.mWaypointsArray.iterator();
        while (it.hasNext()) {
            ((SearchLocationData) it.next()).setRouteCallback(this);
        }
    }

    public void clearRouteWaypoints() {
        if (!this.mWaypointsArray.isEmpty()) {
            this.mWaypointsArray.clear();
            this.mIsStartFromCurrentLocation = true;
        }
    }

    public boolean isStartFromCurrentLocation() {
        return this.mIsStartFromCurrentLocation;
    }

    public MapSelection getWaypointMapSel(int waypointIndex) {
        return getLocationMapSel((SearchLocationData) this.mWaypointsArray.get(waypointIndex));
    }

    public MapSelection[] getWaypointsNavSel() {
        int iCount;
        int size = this.mWaypointsArray.size();
        if (size <= 0 || !((SearchLocationData) this.mWaypointsArray.get(0)).isEmpty(0)) {
            iCount = size;
        } else {
            iCount = size - 1;
        }
        MapSelection[] arrNavSel = new MapSelection[iCount];
        for (int i = 0; i < iCount; i++) {
            arrNavSel[(iCount - 1) - i] = getWaypointMapSel((size - 1) - i);
        }
        return arrNavSel;
    }

    private MapSelection getLocationMapSel(SearchLocationData location) {
        return location == null ? null : location.getMapSel();
    }

    public void registerObserver(SearchInterface observer) {
        this.mObservers.add(observer);
    }

    public void unregisterObserver(SearchInterface observer) {
        this.mObservers.remove(observer);
    }

    public void registerObserver(AvoidsAdapter observer) {
        this.mAvoidsObservers.add(observer);
    }

    public void unregisterObserver(AvoidsAdapter observer) {
        this.mAvoidsObservers.remove(observer);
    }

    private void notifyAvoidsObservers() {
        for (AvoidsAdapter observer : this.mAvoidsObservers) {
            observer.setEnabled(true);
            observer.notifyDataSetChanged();
        }
    }

    public void computeRouteReady() {
        int i = 0;
        while (i < this.mWaypointsArray.size()) {
            SearchLocationData item = (SearchLocationData) this.mWaypointsArray.get(i);
            if ((i == 0 && item.isEmpty(0) && !isStartFromCurrentLocation()) || isWaypointReady(item)) {
                i++;
            } else {
                this.mIsRouteReady = false;
                notifyObservers();
                return;
            }
        }
        this.mIsRouteReady = true;
        notifyObservers();
    }

    public void resetRouteReady(SearchLocationData changedData) {
        if (!isStartFromCurrentLocation() && changedData.equals(this.mWaypointsArray.get(0)) && changedData.isEmpty(0)) {
            if (!this.mIsRouteReady) {
                int i = 1;
                while (i < this.mWaypointsArray.size()) {
                    if (isWaypointReady((SearchLocationData) this.mWaypointsArray.get(i))) {
                        i++;
                    } else {
                        return;
                    }
                }
                this.mIsRouteReady = true;
                notifyObservers();
            }
        } else if (this.mIsRouteReady) {
            this.mIsRouteReady = false;
            notifyObservers();
        }
    }

    public boolean isWaypointReady(SearchLocationData data) {
        return data.isSearchFinished() || (data.isNonEmpty(1) && !(data.getPreviousSrchSubtype() == 1 && (data.getCurrentSearchItem() instanceof CityPostalSearchItem) && ((CityPostalSearchItem) data.getCurrentSearchItem()).isPostalAddress()));
    }

    private void notifyObservers() {
        for (SearchInterface observer : this.mObservers) {
            observer.setRouteReady(this.mIsRouteReady);
        }
    }

    public void onAvoidChanged(int countryIndex, String countryISO, int avoidIndex, boolean checked) {
        this.mAvoidCountryIndex = countryIndex;
        this.mAvoidCountryISO = countryISO;
        this.mAvoidFlagIndex = avoidIndex;
        this.mAvoidFlag = checked;
    }

    public void setCountryAvoidsArray(RouteAvoidsData[] countryAvoidsArray) {
        if (this.mCountryAvoidsArray == null) {
            this.mCountryAvoidsArray = new ArrayList(Arrays.asList(countryAvoidsArray));
        } else {
            this.mCountryAvoidsArray.clear();
            this.mCountryAvoidsArray.addAll(Arrays.asList(countryAvoidsArray));
        }
        notifyAvoidsObservers();
    }

    public RouteAvoidsData getCountryAvoids(int avoidsCountryIndex) {
        return this.mCountryAvoidsArray.isEmpty() ? null : (RouteAvoidsData) this.mCountryAvoidsArray.get(avoidsCountryIndex);
    }

    public int getCountryAvoidsCount() {
        return this.mCountryAvoidsArray == null ? 1 : this.mCountryAvoidsArray.size();
    }

    public String getCountryName(int avoidsCountryIndex) {
        RouteAvoidsData avoidsData = (RouteAvoidsData) this.mCountryAvoidsArray.get(avoidsCountryIndex);
        return avoidsData == null ? "Empty" : avoidsData.getCountryName();
    }

    public void onWaypointInserted(Integer position, ArrayList<ListItem> itemsArray) {
        if (position.intValue() == 0 && getWaypointsCount() == 0) {
            this.mIsStartFromCurrentLocation = false;
        }
        insertNewWaypoint(this.mIsStartFromCurrentLocation ? position.intValue() - 1 : position.intValue(), (ListItem[]) itemsArray.toArray(new ListItem[itemsArray.size()]));
    }

    public void onWaypointRemoved(Integer position) {
        removeWaypoint(position.intValue());
    }

    public void onFinishChanged(ArrayList<SearchItem> itemsArray) {
        SearchLocationData locationData = new SearchLocationData();
        SearchItem[] items = (SearchItem[]) itemsArray.toArray(new SearchItem[itemsArray.size()]);
        if (items.length > 0) {
            for (SearchItem listItem : items) {
                locationData.setSelectedItem(listItem);
                locationData.shiftToNextSubtype();
            }
        }
        locationData.setRouteCallback(this);
        this.mWaypointsArray.remove(this.mWaypointsArray.size() - 1);
        this.mWaypointsArray.add(locationData);
    }

    public void onRouteCanceled(Boolean user) {
        this.mCountryAvoidsArray.clear();
        notifyAvoidsObservers();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.mAvoidCountryISO);
        parcel.writeIntArray(new int[]{this.mWaypointIndex, this.mAvoidCountryIndex, this.mAvoidFlagIndex});
        parcel.writeBooleanArray(new boolean[]{this.hasEntryCurrentLocation, this.mAvoidFlag, this.mIsStartFromCurrentLocation, this.mIsRouteReady});
        parcel.writeList(this.mWaypointsArray);
        parcel.writeList(this.mCountryAvoidsArray);
    }

    private void readFromParcel(Parcel parcel) {
        this.mAvoidCountryISO = parcel.readString();
        int[] intArray = parcel.createIntArray();
        if (intArray != null) {
            this.mWaypointIndex = intArray[0];
            this.mAvoidCountryIndex = intArray[1];
            this.mAvoidFlagIndex = intArray[2];
        }
        boolean[] boolArray = parcel.createBooleanArray();
        if (boolArray != null) {
            this.hasEntryCurrentLocation = boolArray[0];
            this.mAvoidFlag = boolArray[1];
            this.mIsStartFromCurrentLocation = boolArray[2];
            this.mIsRouteReady = boolArray[3];
        }
        parcel.readList(this.mWaypointsArray, SearchLocationData.class.getClassLoader());
        parcel.readList(this.mCountryAvoidsArray, RouteAvoidsData.class.getClassLoader());
        Iterator it = this.mWaypointsArray.iterator();
        while (it.hasNext()) {
            ((SearchLocationData) it.next()).setRouteCallback(this);
        }
    }

    static {
        CREATOR = new C15211();
    }

    public boolean isDestinationParking() {
        SearchLocationData destinationLocation = getDestinationLocation();
        if (destinationLocation == null) {
            return false;
        }
        ListItem item = destinationLocation.getCurrentItem();
        if (item != null) {
            return item.getMapSel().getNavSelType().equals(ENavSelType.NTParking);
        }
        return false;
    }
}
