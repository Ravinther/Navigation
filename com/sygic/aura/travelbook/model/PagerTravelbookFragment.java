package com.sygic.aura.travelbook.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.poi.fragment.PoiFragment;
import com.sygic.aura.travelbook.TravelBookManager;
import com.sygic.aura.travelbook.TravelbookAdapter;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem;
import com.sygic.aura.travelbook.fragment.TripDetailFragment;
import com.sygic.aura.travelbook.fragment.TripDetailFragment.TripDetailCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class PagerTravelbookFragment extends ListFragment {
    private TripDetailCallback mCallback;
    private ActionMode mContextActionMode;
    private ListView mListView;

    /* renamed from: com.sygic.aura.travelbook.model.PagerTravelbookFragment.1 */
    class C17691 implements Comparator<TravelbookTrackLogItem> {
        C17691() {
        }

        public int compare(TravelbookTrackLogItem lLogItem, TravelbookTrackLogItem rLogItem) {
            return rLogItem.getTimeStamp() - lLogItem.getTimeStamp();
        }
    }

    /* renamed from: com.sygic.aura.travelbook.model.PagerTravelbookFragment.2 */
    class C17702 implements MultiChoiceModeListener {
        private final TreeSet<Integer> mSelectedItems;

        C17702() {
            this.mSelectedItems = new TreeSet();
        }

        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            if (checked) {
                this.mSelectedItems.add(Integer.valueOf(position));
            } else {
                this.mSelectedItems.remove(Integer.valueOf(position));
            }
            mode.invalidate();
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            PagerTravelbookFragment.this.mContextActionMode = mode;
            PagerTravelbookFragment.this.getActivity().getMenuInflater().inflate(2131755033, menu);
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() != 2131624698) {
                return false;
            }
            for (Integer intValue : this.mSelectedItems.descendingSet()) {
                PagerTravelbookFragment.this.delete(intValue.intValue());
            }
            this.mSelectedItems.clear();
            mode.finish();
            return true;
        }

        public void onDestroyActionMode(ActionMode mode) {
            this.mSelectedItems.clear();
            PagerTravelbookFragment.this.mContextActionMode = null;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    }

    public static PagerTravelbookFragment newInstance(int position, ArrayList<TravelbookTrackLogItem> arrItems, TripDetailCallback callback) {
        PagerTravelbookFragment travelbookFragment = new PagerTravelbookFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        args.putParcelableArrayList(PoiFragment.ARG_DATA, arrItems);
        travelbookFragment.setArguments(args);
        travelbookFragment.setCallback(callback);
        return travelbookFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903292, container, false);
        this.mListView = (ListView) view.findViewById(16908298);
        if (VERSION.SDK_INT >= 11) {
            initMultichoiceList();
        } else {
            registerForContextMenu(this.mListView);
        }
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        List list;
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        int position = arguments.getInt("POSITION");
        ArrayList<TravelbookTrackLogItem> arrItems = arguments.getParcelableArrayList(PoiFragment.ARG_DATA);
        ArrayList<TravelbookTrackLogItem> arrItemsFavs = new ArrayList();
        if (position > 0) {
            for (int i = 0; i < arrItems.size(); i++) {
                if (((TravelbookTrackLogItem) arrItems.get(i)).isFavourite()) {
                    arrItemsFavs.add(arrItems.get(i));
                }
            }
        }
        if (position > 0) {
            list = arrItemsFavs;
        } else {
            Object obj = arrItems;
        }
        Collections.sort(list, new C17691());
        Context activity = getActivity();
        if (position <= 0) {
            arrItemsFavs = arrItems;
        }
        setListAdapter(new TravelbookAdapter(activity, arrItemsFavs, position));
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        TravelbookTrackLogItem selectedItem = (TravelbookTrackLogItem) listView.getItemAtPosition(position);
        Bundle args = new Bundle();
        args.putParcelable("log_item", selectedItem);
        Fragments.replace(getActivity(), TripDetailFragment.class, "fragment_trip_detail_tag", args, true, this.mCallback);
    }

    @TargetApi(11)
    private void initMultichoiceList() {
        this.mListView.setChoiceMode(3);
        this.mListView.setMultiChoiceModeListener(new C17702());
    }

    private boolean delete(int position) {
        TravelbookTrackLogItem selectedItem = (TravelbookTrackLogItem) this.mListView.getItemAtPosition(position);
        if (selectedItem == null) {
            return false;
        }
        ((TravelbookAdapter) this.mListView.getAdapter()).deleteAtPosition(position);
        TravelBookManager.nativeDeleteTrackLog(selectedItem.getIndex());
        return true;
    }

    public void setLogs(ArrayList<TravelbookTrackLogItem> logs) {
        TravelbookAdapter adapter = (TravelbookAdapter) this.mListView.getAdapter();
        adapter.clear();
        adapter.addAll(logs);
    }

    public void setCallback(TripDetailCallback callback) {
        this.mCallback = callback;
    }
}
