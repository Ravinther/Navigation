package com.sygic.aura.search.model.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.route.data.RouteNavigateDataCallback;
import com.sygic.aura.search.LocationObserverIF;
import java.util.HashSet;
import java.util.Set;

public class SearchLocationData implements Parcelable {
    public static final Creator<SearchLocationData> CREATOR;
    private int mIntSearchSubtype;
    private boolean mIsTerminalBubble;
    private final transient Set<LocationObserverIF> mObservers;
    private transient RouteNavigateDataCallback mRouteCallback;
    private final ListItem[] mSearchResultItems;

    /* renamed from: com.sygic.aura.search.model.data.SearchLocationData.1 */
    static class C15811 implements Creator<SearchLocationData> {
        C15811() {
        }

        public SearchLocationData createFromParcel(Parcel in) {
            return new SearchLocationData(null);
        }

        public SearchLocationData[] newArray(int size) {
            return new SearchLocationData[size];
        }
    }

    public SearchLocationData() {
        this.mIntSearchSubtype = 0;
        this.mSearchResultItems = new ListItem[4];
        this.mIsTerminalBubble = false;
        this.mObservers = new HashSet();
    }

    private SearchLocationData(Parcel parcel) {
        this.mIntSearchSubtype = 0;
        this.mSearchResultItems = new ListItem[4];
        this.mIsTerminalBubble = false;
        readFromParcel(parcel);
        this.mObservers = new HashSet();
    }

    public void setSelectedItem(ListItem selectedItem) {
        setSelectedItem(selectedItem, this.mIntSearchSubtype);
    }

    public void setSelectedItem(ListItem selectedItem, int position) {
        int bubbleIndex = Math.min(3, position);
        boolean replacePrevious = this.mSearchResultItems[bubbleIndex] != null;
        this.mSearchResultItems[bubbleIndex] = selectedItem;
        if (replacePrevious) {
            for (LocationObserverIF observer : this.mObservers) {
                observer.onDataChanged();
            }
        }
    }

    public void addItem(ListItem item) {
        addItem(item, true);
    }

    public void addItem(ListItem item, boolean showBubble) {
        setSelectedItem(item);
        int subtype = showBubble ? shiftToNextSubtype() : this.mIntSearchSubtype;
        for (LocationObserverIF observer : this.mObservers) {
            observer.notifyDataAdded(getPreviousSrchSubtype(), showBubble);
        }
        if (this.mRouteCallback == null) {
            return;
        }
        if (isSearchFinished() || subtype >= 1) {
            this.mRouteCallback.computeRouteReady();
        }
    }

    public void removeItem(int index) {
        removeItem(index, true);
    }

    public void removeItem(int index, boolean notifyObservers) {
        removeItem(index, notifyObservers, false);
    }

    public void removeItem(int index, boolean notifyObservers, boolean setItemText) {
        if (this.mSearchResultItems[index] != null) {
            String text = this.mSearchResultItems[index].getDisplayName();
            this.mSearchResultItems[index] = null;
            if (notifyObservers) {
                for (LocationObserverIF observer : this.mObservers) {
                    observer.notifyDataRemoved(index, text, setItemText);
                }
            }
            if (this.mRouteCallback == null) {
                return;
            }
            if (index == 1 || ((index == 0 && getNextItem(index) == null) || (getPreviousItem(index) != null && index == 2 && (getPreviousItem(index) instanceof CityPostalSearchItem) && ((CityPostalSearchItem) getPreviousItem(index)).isPostalAddress()))) {
                this.mRouteCallback.resetRouteReady(this);
            }
        }
    }

    public void notifyObservers(int searchSubtype) {
        for (LocationObserverIF observer : this.mObservers) {
            observer.onDataChanged();
            this.mIntSearchSubtype = searchSubtype;
        }
    }

    public int getSearchSubtype() {
        return this.mIntSearchSubtype;
    }

    public int getPreviousSrchSubtype() {
        return Math.max(0, this.mIntSearchSubtype - 1);
    }

    public String getItemName(int bubbleIndex) {
        return (bubbleIndex < 0 || this.mSearchResultItems[bubbleIndex] == null) ? null : this.mSearchResultItems[bubbleIndex].getDisplayName();
    }

    public boolean isNonEmpty(int bubbleIndex) {
        return this.mSearchResultItems[bubbleIndex] != null;
    }

    public boolean isEmpty(int bubbleIndex) {
        return this.mSearchResultItems[bubbleIndex] == null;
    }

    public ListItem getItem(int bubbleIndex) {
        if (bubbleIndex < 0 || bubbleIndex >= this.mSearchResultItems.length) {
            return null;
        }
        return this.mSearchResultItems[bubbleIndex];
    }

    public SearchItem getSearchItem(int bubbleIndex) {
        ListItem item = this.mSearchResultItems[Math.max(0, bubbleIndex)];
        return item instanceof SearchItem ? (SearchItem) item : null;
    }

    public ListItem getCurrentItem() {
        return this.mIntSearchSubtype <= this.mSearchResultItems.length ? getPreviousItem(this.mIntSearchSubtype) : null;
    }

    public SearchItem getCurrentSearchItem() {
        return this.mIntSearchSubtype <= this.mSearchResultItems.length ? getPreviousSearchItem(this.mIntSearchSubtype) : null;
    }

    public ListItem getPreviousItem(int bubbleIndex) {
        return getItem(bubbleIndex - 1);
    }

    public ListItem getNextItem(int bubbleIndex) {
        return getItem(bubbleIndex + 1);
    }

    public SearchItem getPreviousSearchItem(int bubbleIndex) {
        return bubbleIndex <= 0 ? null : getSearchItem(bubbleIndex - 1);
    }

    public long getSearchItemTreeEntry(int bubbleIndex) {
        SearchItem item = getSearchItem(bubbleIndex);
        return item == null ? 0 : item.getTreeEntry();
    }

    public MapSelection getItemMapSel(int bubbleIndex) {
        return this.mSearchResultItems[bubbleIndex] == null ? null : this.mSearchResultItems[bubbleIndex].getMapSel();
    }

    public void clearAllLocationData() {
        for (int i = 0; i < 4; i++) {
            this.mSearchResultItems[i] = null;
        }
        this.mIntSearchSubtype = 0;
        this.mIsTerminalBubble = false;
    }

    public int shiftToNextSubtype() {
        if (this.mIntSearchSubtype < 4) {
            this.mIntSearchSubtype++;
        }
        return this.mIntSearchSubtype;
    }

    public int shiftToPreviousSubtype() {
        if (this.mIntSearchSubtype > 0 && !isTerminalBubble()) {
            this.mIntSearchSubtype--;
        }
        return this.mIntSearchSubtype;
    }

    public boolean isTerminalBubble() {
        return this.mIsTerminalBubble;
    }

    public boolean isSearchFinished() {
        return this.mIsTerminalBubble || this.mIntSearchSubtype == 4;
    }

    public void registerLocationObserver(LocationObserverIF observer) {
        this.mObservers.add(observer);
    }

    public boolean unregisterLocationObserver(LocationObserverIF observer) {
        return this.mObservers.remove(observer);
    }

    public void setRouteCallback(RouteNavigateDataCallback routeCallback) {
        this.mRouteCallback = routeCallback;
    }

    public void setAsTerminalBubble(boolean isTerminalBubble) {
        this.mIsTerminalBubble = isTerminalBubble;
    }

    public MapSelection getMapSel() {
        return getItemMapSel(getPreviousSrchSubtype());
    }

    public ListItem[] getSearchItems() {
        return this.mSearchResultItems;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBooleanArray(new boolean[]{this.mIsTerminalBubble});
        parcel.writeIntArray(new int[]{this.mIntSearchSubtype});
        parcel.writeTypedArray(this.mSearchResultItems, flags);
    }

    private void readFromParcel(Parcel parcel) {
        boolean[] boolArray = parcel.createBooleanArray();
        if (boolArray != null) {
            this.mIsTerminalBubble = boolArray[0];
        }
        int[] intArray = parcel.createIntArray();
        if (intArray != null) {
            this.mIntSearchSubtype = intArray[0];
        }
        parcel.readTypedArray(this.mSearchResultItems, ListItem.CREATOR);
    }

    static {
        CREATOR = new C15811();
    }

    public boolean isHouseFirst() {
        return this.mSearchResultItems[Math.max(0, 2)] instanceof HouseNumberSearchItem;
    }
}
