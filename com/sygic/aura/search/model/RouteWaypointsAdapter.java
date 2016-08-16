package com.sygic.aura.search.model;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.transition.Transition.TransitionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.sygic.aura.fragments.interfaces.SearchInterface;
import com.sygic.aura.helper.TransitionManagerCompat.TransitionListenerProvider;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.SearchLocationData;
import com.sygic.aura.search.view.SearchFrame;
import java.util.ArrayList;
import java.util.List;

public class RouteWaypointsAdapter extends ArrayAdapter<SearchLocationData> {
    private final transient LayoutInflater inflater;
    private List<SearchLocationData> mCollapsedItems;
    private int mCurrentItemPos;
    private View mExpandButton;
    private int mIsListOptionsVisible;
    private final transient RouteNavigateData mRouteNaviData;
    private RoutePointChangeObserver mRouteObserver;
    private final transient SearchInterface mSearchIF;
    private boolean mShouldShowKeyboard;

    public class ViewHolder {
        public final RouteWaypointsAdapter mAdapter;
        public final SearchFrame mSearchFrame;
        public final SearchInterface mSearchIF;
        public final Object mTransitionProvider;

        public ViewHolder(RouteWaypointsAdapter this$0, View convertView, SearchInterface mSearchIF) {
            this(convertView, mSearchIF, null);
        }

        public ViewHolder(View convertView, SearchInterface mSearchIF, Object transitionProvider) {
            this.mSearchFrame = (SearchFrame) convertView;
            this.mAdapter = RouteWaypointsAdapter.this;
            this.mSearchIF = mSearchIF;
            this.mTransitionProvider = transitionProvider;
        }

        @TargetApi(19)
        public TransitionListener getTransactionListener() {
            return this.mTransitionProvider == null ? null : ((TransitionListenerProvider) this.mTransitionProvider).getTransitionListener();
        }
    }

    public RouteWaypointsAdapter(SearchInterface searchIF, RouteNavigateData routeNaviData) {
        super(searchIF.getContext(), 0, routeNaviData.getWaypointsList());
        this.mIsListOptionsVisible = 0;
        this.mShouldShowKeyboard = true;
        this.mCollapsedItems = new ArrayList();
        this.mRouteNaviData = routeNaviData;
        this.mSearchIF = searchIF;
        this.inflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
    }

    public void setCurrentItemPosition(int position) {
        if (position < getCount()) {
            this.mCurrentItemPos = position;
        }
    }

    public int getCurrentItemPosition() {
        return this.mCurrentItemPos;
    }

    public SearchLocationData getCurrentItem() {
        int currentItemPosition = getCurrentItemPosition();
        return (currentItemPosition < 0 || currentItemPosition >= getCount()) ? null : (SearchLocationData) getItem(currentItemPosition);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder row;
        boolean isCurrent;
        boolean z = false;
        if (convertView == null) {
            convertView = this.inflater.inflate(2130903296, parent, false);
            row = new ViewHolder(convertView, this.mSearchIF, parent.getParent());
            convertView.setTag(row);
        } else {
            row = (ViewHolder) convertView.getTag();
            if (row == null) {
                row = new ViewHolder(this, convertView, this.mSearchIF);
                convertView.setTag(row);
            }
        }
        if (getCurrentItemPosition() == position) {
            isCurrent = true;
        } else {
            isCurrent = false;
        }
        row.mSearchFrame.setTag(row);
        row.mSearchFrame.setSearchLocationData((SearchLocationData) getItem(position), isCurrent);
        SearchFrame searchFrame = row.mSearchFrame;
        if ((VERSION.SDK_INT < 21 || position != 0) && isCurrent) {
            z = true;
        }
        updateSearchFrame(searchFrame, position, z);
        if (position == getCount() - 1) {
            this.mExpandButton = row.mSearchFrame.getExpandButton();
        }
        return convertView;
    }

    public void updateSearchFrame(SearchFrame frame, int position, boolean requestFocus) {
        frame.setLabel(getWaypointLabel(this.mRouteNaviData.isStartFromCurrentLocation(), position, getCount() - 1));
        frame.updateSearchFramePosition(position, requestFocus);
    }

    protected int getWaypointLabel(boolean isStartFromCurrent, int position, int max) {
        if (position == 0) {
            if (isStartFromCurrent) {
                return max == position ? 2130838221 : 2130838222;
            } else {
                return 2130838224;
            }
        } else if (position == max) {
            return 2130838220;
        } else {
            return 2130838223;
        }
    }

    public void addItemToCurrentFrame(ListItem item) {
        addItemToCurrentFrame(item, false);
    }

    public void addItemToCurrentFrame(ListItem item, boolean isTerminal) {
        addItemToCurrentFrame(item, isTerminal, true);
    }

    public void addItemToCurrentFrame(ListItem item, boolean isTerminal, boolean showBubble) {
        SearchLocationData currentItem = getCurrentItem();
        if (currentItem != null) {
            currentItem.setAsTerminalBubble(isTerminal);
            currentItem.addItem(item, showBubble);
        }
    }

    public void changeStart() {
        if (this.mRouteNaviData.changeStart()) {
            setCurrentItemPosition(0);
            notifyWaypointAdded(0);
        } else if (getCurrentItemPosition() > 0) {
            setCurrentItemPosition(0);
            this.mSearchIF.reinitializeCurrentInput();
        }
    }

    public void insertNewWaypoint() {
        int position = this.mRouteNaviData.insertNewWaypoint();
        setCurrentItemPosition(position);
        notifyWaypointAdded(position);
    }

    public void clearSearch() {
        clearAllWaypoints();
        insertNewWaypoint();
    }

    private void clearAllWaypoints() {
        this.mRouteNaviData.clearRouteWaypoints();
        setCurrentItemPosition(0);
        notifyWaypointsCleared();
    }

    public boolean areOptionsVisible() {
        return this.mIsListOptionsVisible > 0;
    }

    public boolean setListOptionsVisible(boolean visible) {
        boolean z = true;
        this.mIsListOptionsVisible = (visible ? 1 : -1) + this.mIsListOptionsVisible;
        SearchInterface searchInterface = this.mSearchIF;
        if (this.mIsListOptionsVisible > 0) {
            z = false;
        }
        searchInterface.showResultsList(z);
        return areOptionsVisible();
    }

    public void removeWaypoint(int position) {
        if (getCount() == 1 && position == 0) {
            clearSearch();
            return;
        }
        this.mRouteNaviData.removeWaypoint(position);
        setCurrentItemPosition(Math.min(this.mRouteNaviData.getWaypointsCount() - 1, position));
        notifyWaypointRemoved(position);
    }

    private void notifyWaypointAdded(int position) {
        if (this.mRouteObserver != null) {
            this.mRouteObserver.onRoutePointAdded(position);
        }
        notifyDataSetChanged();
    }

    private void notifyWaypointRemoved(int position) {
        if (this.mRouteObserver != null) {
            this.mRouteObserver.onRoutePointRemoved(position);
        }
        notifyDataSetChanged();
    }

    private void notifyWaypointsCleared() {
        if (this.mRouteObserver != null) {
            this.mRouteObserver.onRoutePointCleared();
        }
        notifyDataSetChanged();
    }

    public void registerRoutePointChangeObserver(RoutePointChangeObserver observer) {
        this.mRouteObserver = observer;
    }

    public void setShouldShowKeyboardOnSearchFrameAdded(boolean should) {
        this.mShouldShowKeyboard = should;
    }

    public boolean shouldShowKeyboardOnSearchFrameAdded() {
        return this.mShouldShowKeyboard;
    }

    public boolean isStartFromCurrentLocation() {
        return this.mRouteNaviData.isStartFromCurrentLocation();
    }

    public void expand() {
        if (this.mCollapsedItems.isEmpty()) {
            changeStart();
            return;
        }
        int count = this.mCollapsedItems.size();
        for (int i = 0; i < count; i++) {
            this.mRouteNaviData.getWaypointsList().add(i, (SearchLocationData) this.mCollapsedItems.remove(0));
            if (i == 0) {
                this.mRouteNaviData.setStartNotFromCurrent();
            }
            notifyWaypointAdded(i);
        }
        this.mRouteNaviData.computeRouteReady();
    }

    public void collapse() {
        int count = getCount();
        for (int i = 0; i < count - 1; i++) {
            this.mCollapsedItems.add(this.mRouteNaviData.getWaypoint(0));
            this.mRouteNaviData.removeWaypoint(0);
            notifyWaypointRemoved(0);
        }
        setCurrentItemPosition(0);
    }

    public void rotateExpandButton() {
        this.mExpandButton.animate().rotationBy(180.0f).setDuration(300);
    }
}
