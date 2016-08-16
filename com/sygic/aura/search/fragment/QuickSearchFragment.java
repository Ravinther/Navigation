package com.sygic.aura.search.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.interfaces.LoadingStateListener;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.search.QuickSearchListAdapter;
import com.sygic.aura.search.model.data.QuickSearchItem;
import com.sygic.aura.search.model.data.QuickSearchItem.ItemType;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.font_specials.SToolbar;

public class QuickSearchFragment extends AbstractScreenFragment implements OnItemClickListener, LoadingStateListener {
    private QuickSearchListAdapter mAdapter;
    private ListView mList;
    private SmartProgressBar mSmartProgressBar;

    public QuickSearchFragment() {
        setWantsNavigationData(false);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(getArguments().getString(AbstractFragment.ARG_TITLE));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903124, container, false);
        this.mSmartProgressBar = (SmartProgressBar) view.findViewById(2131624166);
        this.mAdapter = new QuickSearchListAdapter(getActivity(), this.mLocationQuery);
        this.mAdapter.registerLoadingListener(this);
        Bundle arguments = getArguments();
        this.mAdapter.setItemData((ItemType) arguments.getSerializable("item_type"), arguments.getLong("item_object"));
        initList(view);
        return view;
    }

    private void initList(View fragmentView) {
        this.mList = (ListView) fragmentView.findViewById(2131624373);
        this.mList.setAdapter(this.mAdapter);
        this.mList.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.mAdapter.cancelLoading();
        QuickSearchItem item = (QuickSearchItem) this.mAdapter.getItem(position);
        if (item.getQuickItemType() != ItemType.ITEM_NEARBY_POI) {
            performHomeAction();
        }
        if (this.mResultCallback != null) {
            ((QuickSearchFragmentResultCallback) this.mResultCallback).onQuickSearchFragmentResult(item);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mAdapter.unregisterLoadingListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mLocationQuery.setQuickSearchItem(ItemType.ITEM_NONE, 0);
        if (this.mResultCallback != null) {
            ((QuickSearchFragmentResultCallback) this.mResultCallback).onQuickSearchFragmentResult((MapSelection) null);
        }
    }

    public void onLoadingStarted() {
        this.mSmartProgressBar.start();
    }

    public void onFirstNonEmptyTick() {
        this.mSmartProgressBar.stopAndCrossfadeWith(this.mList);
    }

    public void onLoadingFinished(boolean isEmpty) {
        if (isEmpty) {
            this.mSmartProgressBar.stopAndShowEmpty();
        }
    }

    public void onLoadingError() {
        this.mSmartProgressBar.stopAndShowError();
    }
}
