package com.sygic.aura.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.SearchCountDownTimerExecutor.CountDownTimerListenerIF;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.QuickSearchItem;
import com.sygic.aura.search.model.data.QuickSearchItem.ItemType;
import com.sygic.aura.search.model.holder.ViewHolder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QuickSearchListAdapter extends ArrayAdapter<QuickSearchItem> implements CountDownTimerListenerIF {
    private int mIntLastCount;
    private boolean mIsLoadCancelled;
    private final List<LoadingStateListener> mLoadingListeners;
    private final LocationQuery mLocationQuery;
    private long mTicketNr;
    private long mTreeEntry;
    private ItemType mType;

    public QuickSearchListAdapter(Context context, LocationQuery locationQuery) {
        super(context, 2130903271, 2131624363);
        this.mIntLastCount = 0;
        this.mTicketNr = -1;
        this.mLoadingListeners = Collections.synchronizedList(new LinkedList());
        this.mLocationQuery = locationQuery;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        QuickSearchItem item = (QuickSearchItem) getItem(position);
        if (convertView == null) {
            convertView = super.getView(position, convertView, parent);
            convertView.setTag(item.newViewHolderInstance(convertView));
        }
        ((ViewHolder) convertView.getTag()).updateContent(item, position);
        return convertView;
    }

    public boolean registerLoadingListener(LoadingStateListener listener) {
        return this.mLoadingListeners.add(listener);
    }

    public boolean unregisterLoadingListener(LoadingStateListener listener) {
        return this.mLoadingListeners.remove(listener);
    }

    public void setItemData(ItemType type, long object) {
        this.mType = type;
        this.mTreeEntry = object;
        doDetailsLoading();
    }

    private void doDetailsLoading() {
        cancelLoading();
        this.mTicketNr = this.mLocationQuery.doSearchInitialization(this);
        this.mIsLoadCancelled = false;
    }

    public void cancelLoading() {
        this.mIsLoadCancelled = this.mLocationQuery.cancelSearchInitialization(this.mTicketNr);
    }

    private ListItem nativeGetItem(int position) {
        return this.mLocationQuery.getCoreItemAt(position);
    }

    public void onStartSearch() {
        for (LoadingStateListener listener : this.mLoadingListeners) {
            listener.onLoadingStarted();
        }
        if (this.mLocationQuery.getSearchObjectRef() != 0 && !SygicProject.IS_PROTOTYPE) {
            this.mLocationQuery.setQuickSearchItem(this.mType, this.mTreeEntry);
        }
    }

    public void onCancelSearch(boolean wasFinished) {
        for (LoadingStateListener listener : this.mLoadingListeners) {
            listener.onLoadingFinished(getCount() <= 0);
        }
    }

    public void onFinishSearch() {
        for (LoadingStateListener listener : this.mLoadingListeners) {
            listener.onLoadingFinished(getCount() <= 0);
        }
    }

    public void onTickSearch(long millisUntilFinished) {
        int count;
        if (SygicProject.IS_PROTOTYPE) {
            count = this.mIntLastCount + 10;
        } else {
            count = this.mLocationQuery.getCoreResultsCount(0);
        }
        if (count > 0 && count > this.mIntLastCount && !this.mIsLoadCancelled) {
            int ind = this.mIntLastCount;
            while (ind < count) {
                if (this.mIsLoadCancelled) {
                    this.mIntLastCount = ind;
                    break;
                } else {
                    add((QuickSearchItem) nativeGetItem(ind));
                    ind++;
                }
            }
            if (!this.mIsLoadCancelled) {
                this.mIntLastCount = count;
                for (LoadingStateListener listener : this.mLoadingListeners) {
                    listener.onFirstNonEmptyTick();
                }
            }
        }
    }
}
