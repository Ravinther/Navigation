package com.sygic.aura.poi.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.helper.loading.LazyLoading;
import com.sygic.aura.helper.loading.loadable.LazyPoiListItemWrapper;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.search.model.data.SearchItem;
import com.sygic.aura.search.model.holder.ViewHolder;
import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class PoiAdapter extends ArrayAdapter<PoiListItem> {
    private final DataSetObservable mDataSetObservable;
    private int mIntLastCount;
    private volatile boolean mIsLoadCancelled;
    private volatile boolean mIsLoadRunning;
    protected final LoadingStateListener mListener;
    private volatile boolean mLoadFinished;
    private final LocationQuery mLocationQuery;
    protected final String mNoItemsLabel;
    private final long mSearchRef;
    protected CharSequence mSearchStr;
    private Thread mSyncThread;

    /* renamed from: com.sygic.aura.poi.adapter.PoiAdapter.1 */
    class C14361 implements ExecutionOrder {
        final /* synthetic */ LoadTask val$asyncTask;

        /* renamed from: com.sygic.aura.poi.adapter.PoiAdapter.1.1 */
        class C14351 implements Runnable {
            C14351() {
            }

            public void run() {
                AsyncTaskHelper.execute(C14361.this.val$asyncTask);
            }
        }

        C14361(LoadTask loadTask) {
            this.val$asyncTask = loadTask;
        }

        public boolean runningCondition() {
            return PoiAdapter.this.mIsLoadRunning;
        }

        public boolean onPositive() {
            return true;
        }

        public boolean onNegative() {
            synchronized (PoiAdapter.this) {
                PoiAdapter.this.mSyncThread = null;
            }
            ((Activity) PoiAdapter.this.getContext()).runOnUiThread(new C14351());
            return false;
        }
    }

    private class LoadTask extends AsyncTask<Void, PoiListItem, Boolean> {
        private Set<PoiListItem> mPoiItems;

        private LoadTask() {
        }

        protected void onPreExecute() {
            PoiAdapter.this.mLocationQuery.startSearch(PoiAdapter.this.mSearchRef);
            if (PoiAdapter.this.mListener != null) {
                PoiAdapter.this.mListener.onLoadingStarted();
            }
            PoiAdapter.this.mIsLoadRunning = true;
            PoiAdapter.this.mIsLoadCancelled = false;
            PoiAdapter.this.mLoadFinished = false;
            PoiAdapter.this.mIntLastCount = 0;
            this.mPoiItems = new TreeSet(PoiListItem.getComparator());
        }

        protected Boolean doInBackground(Void... params) {
            boolean z;
            synchronized (PoiAdapter.this) {
                PoiAdapter.this.mLocationQuery.queryCoreSearch(PoiAdapter.this.mSearchRef, PoiAdapter.this.mSearchStr.toString());
            }
            int count = 0;
            boolean isProcessing = true;
            while (!PoiAdapter.this.mIsLoadCancelled) {
                boolean firstCycle;
                if (PoiAdapter.this.mIntLastCount == 0) {
                    firstCycle = true;
                } else {
                    firstCycle = false;
                }
                if (!firstCycle) {
                    synchronized (this) {
                        try {
                            wait(1500);
                            Thread.sleep(150);
                        } catch (InterruptedException e) {
                            CrashlyticsHelper.logWarning("POI_ADAPTER", e.getMessage());
                        }
                    }
                }
                isProcessing = PoiAdapter.this.mLocationQuery.isSearchProcessing(PoiAdapter.this.mSearchRef);
                count = PoiAdapter.this.mLocationQuery.getCoreResultsCount(PoiAdapter.this.mSearchRef);
                if (count > 0 && count > PoiAdapter.this.mIntLastCount && !PoiAdapter.this.mIsLoadCancelled) {
                    ListItem[] items = PoiAdapter.this.mLocationQuery.getCoreItems(PoiAdapter.this.mSearchRef, PoiAdapter.this.mIntLastCount, firstCycle ? 100 : 1000);
                    if (items != null) {
                        for (ListItem item : items) {
                            if (PoiAdapter.this.mIsLoadCancelled) {
                                break;
                            }
                            this.mPoiItems.add((PoiListItem) item);
                        }
                        PoiAdapter.this.mIntLastCount = this.mPoiItems.size();
                        publishProgress(this.mPoiItems.toArray(new PoiListItem[this.mPoiItems.size()]));
                    }
                } else if (!isProcessing) {
                    if (count == 0) {
                        publishProgress(new PoiListItem[]{PoiAdapter.this.getEmptyItem()});
                    } else {
                        publishProgress(new PoiListItem[0]);
                    }
                    z = isProcessing && count <= PoiAdapter.this.mIntLastCount;
                    return Boolean.valueOf(z);
                }
            }
            if (isProcessing) {
            }
            return Boolean.valueOf(z);
        }

        protected void onProgressUpdate(PoiListItem... values) {
            if (values != null && values.length > 0) {
                boolean wasEmpty = PoiAdapter.this.isEmpty();
                PoiAdapter.this.setNotifyOnChange(false);
                PoiAdapter.this.clear();
                PoiAdapter.this.addAll(values);
                PoiAdapter.this.notifyDataSetChanged();
                if (!PoiAdapter.this.mIsLoadCancelled && values[0].getGroupId() == -1) {
                    PoiAdapter.this.cancelPoiLoading();
                }
                if (wasEmpty && PoiAdapter.this.mListener != null) {
                    PoiAdapter.this.mListener.onFirstNonEmptyTick();
                }
            } else if (!PoiAdapter.this.mIsLoadCancelled) {
                PoiAdapter.this.cancelPoiLoading();
            }
            synchronized (this) {
                notify();
            }
        }

        protected void onPostExecute(Boolean isFinished) {
            PoiAdapter.this.mIsLoadRunning = false;
            PoiAdapter.this.mLoadFinished = isFinished.booleanValue();
            if (PoiAdapter.this.mListener != null) {
                PoiAdapter.this.mListener.onLoadingFinished(PoiAdapter.this.isEmpty());
            }
        }
    }

    private class ViewHolderPoi extends ViewHolder {
        private final TextView mDistance;
        private final HashMap<String, FontDrawable> mDrawableCache;

        public ViewHolderPoi(View view) {
            super(view);
            this.mDistance = (TextView) view.findViewById(2131624119);
            this.mDrawableCache = new HashMap();
            this.mDrawableCache.put(null, (FontDrawable) FontDrawable.inflate(view.getResources(), 2131034283));
        }

        public void updateContent(SearchItem item, int position) {
        }

        public void updateContent(PoiListItem item) {
            if (item != null) {
                this.mTextView.setText(item.getDisplayName());
                if (item.getGroupId() == -1) {
                    this.mExtTextView.setVisibility(8);
                } else {
                    PoiAdapter.getDescriptionForItem(item, this.mExtTextView);
                }
                String strIcon = item.getIcon();
                if (TextUtils.isEmpty(strIcon)) {
                    String strIconFile = item.getIconFile();
                    if (TextUtils.isEmpty(strIconFile) || !new File(strIconFile).exists()) {
                        this.mIconView.setImageDrawable(getCachedDrawable(null));
                    } else {
                        Picasso.with(this.mContext).load("file://" + strIconFile).resizeDimen(2131230934, 2131230934).error(getCachedDrawable(null)).into(this.mIconView);
                    }
                } else {
                    this.mIconView.setImageDrawable(getCachedDrawable(strIcon));
                }
                if (((long) item.getPoiDistance()) > 0) {
                    this.mDistance.setVisibility(0);
                    this.mDistance.setText(item.getFormattedDistance());
                    return;
                }
                this.mDistance.setVisibility(8);
            }
        }

        private Drawable getCachedDrawable(String strIcon) {
            if (this.mDrawableCache.containsKey(strIcon)) {
                return (Drawable) this.mDrawableCache.get(strIcon);
            }
            Drawable newDrawable = new FontDrawable((FontDrawable) this.mDrawableCache.get(null), strIcon);
            this.mDrawableCache.put(strIcon, newDrawable);
            return newDrawable;
        }
    }

    public PoiAdapter(Context context, LocationQuery locationQuery, long searchRef, LoadingStateListener listener) {
        super(context, 2130903271, 2131624363);
        this.mDataSetObservable = new DataSetObservable();
        this.mIsLoadRunning = false;
        this.mSearchStr = "";
        this.mIntLastCount = 0;
        this.mLocationQuery = locationQuery;
        this.mSearchRef = searchRef;
        this.mNoItemsLabel = ResourceManager.getCoreString(context, 2131165420);
        this.mListener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getView(position, null, parent);
            convertView.setTag(new ViewHolderPoi(convertView));
        }
        ((ViewHolderPoi) convertView.getTag()).updateContent((PoiListItem) getItem(position));
        return convertView;
    }

    public synchronized void query(CharSequence query) {
        this.mSearchStr = query;
        doPoiLoading();
    }

    protected void doPoiLoading() {
        if (this.mSearchRef != 0) {
            cancelPoiLoading();
            executePoiLoading();
        }
    }

    protected void executePoiLoading() {
        LoadTask asyncTask = new LoadTask();
        if (this.mIsLoadRunning) {
            boolean syncCheck;
            synchronized (this) {
                syncCheck = this.mSyncThread == null;
            }
            if (syncCheck) {
                startSyncThread(asyncTask);
                return;
            }
            return;
        }
        AsyncTaskHelper.execute(asyncTask);
    }

    private void startSyncThread(LoadTask asyncTask) {
        this.mSyncThread = new RepeatingThread(new C14361(asyncTask), 250);
        this.mSyncThread.start();
    }

    protected PoiListItem getEmptyItem() {
        return PoiListItem.getSpecialItemInstance(this.mNoItemsLabel);
    }

    public void cancelPoiLoading() {
        if (this.mIsLoadRunning) {
            this.mLocationQuery.cancelSearchTask(this.mSearchRef);
            this.mIsLoadCancelled = true;
        }
    }

    public boolean isFinished() {
        return this.mLoadFinished;
    }

    public boolean isCanceled() {
        return this.mIsLoadCancelled;
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int position) {
        if (position < 0 || position >= getCount()) {
            return false;
        }
        PoiListItem item = (PoiListItem) getItem(position);
        if (item == null || item.getGroupId() == -1) {
            return false;
        }
        return true;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    public long getItemId(int position) {
        return ((PoiListItem) getItem(position)).getPoiId();
    }

    public boolean hasStableIds() {
        return false;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        this.mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyDataSetChanged() {
        this.mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        this.mDataSetObservable.notifyInvalidated();
    }

    public void resumeLoading() {
        if (!this.mLoadFinished) {
            doPoiLoading();
        }
    }

    private static void getDescriptionForItem(PoiListItem item, TextView receiver) {
        String address = LazyPoiListItemWrapper.getCachedAddress(item.getPoiId());
        if (address != null) {
            receiver.setText(address);
            receiver.setVisibility(0);
            return;
        }
        receiver.setVisibility(4);
        LazyLoading.loadFor(item).into(receiver);
    }

    public long[] getShowOnMapData(int items) {
        long[] pois = new long[items];
        for (int i = 0; i < pois.length; i++) {
            pois[i] = getItemId(i);
        }
        return pois;
    }
}
