package com.sygic.aura.poi.nearbypoi.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.poi.PoiFragmentResultCallback;
import com.sygic.aura.poi.fragment.PoiFragment;
import com.sygic.aura.poi.nearbypoi.adapter.NearbyPoiDashGroupAdapter;
import com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup;
import com.sygic.aura.poi.provider.PoiProvider;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.search.model.data.SearchLocationData;
import com.sygic.aura.views.ButtonScrollListView;
import com.sygic.aura.views.font_specials.SToolbar;

public class NearbyPoiCategoryFragment extends AbstractScreenFragment implements PoiFragmentResultCallback {
    private NearbyPoiDashGroupAdapter mAdapter;
    private Parcelable mData;
    private final OnItemClickListener mGroupClickListener;
    private boolean mNavigationChanged;
    private boolean mSelectMode;

    /* renamed from: com.sygic.aura.poi.nearbypoi.fragment.NearbyPoiCategoryFragment.1 */
    class C14601 implements OnItemClickListener {
        C14601() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            LongPosition longPos;
            NearbyPoiGroup group = (NearbyPoiGroup) NearbyPoiCategoryFragment.this.mAdapter.getItem(position);
            if (NearbyPoiCategoryFragment.this.mData instanceof SearchLocationData) {
                longPos = ((SearchLocationData) NearbyPoiCategoryFragment.this.mData).getSearchItem(1).getLongPosition();
            } else {
                longPos = (LongPosition) NearbyPoiCategoryFragment.this.mData;
            }
            NearbyPoiCategoryFragment.this.goToNearbyPoiFragment(group.getName(), group.getType(), NearbyPoiGroup.providersFromGroupType(group.getType(), longPos), NearbyPoiGroup.getDefaultProviderFromGroupType(group.getType(), longPos));
            SygicAnalyticsLogger.getAnalyticsEvent(NearbyPoiCategoryFragment.this.getActivity(), EventType.SEARCH).setName("search").setValue(group.getStringCode(), "all").logAndKeep().clearParams().setValue("poi_global", group.getStringCode()).logAndRecycle();
        }
    }

    public NearbyPoiCategoryFragment() {
        this.mSelectMode = false;
        this.mNavigationChanged = false;
        this.mGroupClickListener = new C14601();
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165511);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mData = bundle.getParcelable(PoiFragment.ARG_DATA);
            this.mSelectMode = bundle.getBoolean(PoiFragment.ARG_SELECT_MODE, false);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(2130903114, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();
        if (NaviNativeActivity.isNavigationBarHidden(activity)) {
            this.mNavigationChanged = true;
            NaviNativeActivity.showNavigationBar(activity, false);
        }
        this.mAdapter = new NearbyPoiDashGroupAdapter(getActivity(), loadGroupsWithCategories());
        ButtonScrollListView listView = (ButtonScrollListView) view.findViewById(2131624221);
        listView.setOnItemClickListener(this.mGroupClickListener);
        listView.setAdapter(this.mAdapter);
        listView.initButtonScroll(view, 2131624136, 2131624138);
    }

    private NearbyPoiGroup[] loadGroupsWithCategories() {
        NearbyPoiGroup[] nativeGroups = this.mLocationQuery.getPoiGroups();
        NearbyPoiGroup[] groups = new NearbyPoiGroup[(nativeGroups.length + 1)];
        groups[0] = new NearbyPoiGroup(ResourceManager.getCoreString(getResources(), 2131165576), getString(2131166194).charAt(0), getResources().getColor(2131558522), 0, "_poi.All");
        System.arraycopy(nativeGroups, 0, groups, 1, nativeGroups.length);
        for (int i = 1; i < groups.length; i++) {
            groups[i].setCategories(this.mLocationQuery.getPoiCategories(groups[i].getType()));
        }
        return groups;
    }

    private void goToNearbyPoiFragment(String poiName, int poiType, PoiProvider[] poiProviders, Class<? extends PoiProvider> defaultProvider) {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            Bundle args = new Bundle();
            args.putString(AbstractFragment.ARG_TITLE, poiName);
            args.putInt(PoiFragment.ARG_GROUP_ID, poiType);
            args.putBoolean(PoiFragment.ARG_SELECT_MODE, this.mSelectMode);
            args.putParcelable(PoiFragment.ARG_DATA, this.mData);
            args.putParcelableArray(PoiFragment.ARG_PROVIDERS, poiProviders);
            args.putInt(PoiFragment.ARG_DEFAULT, getDefaultProviderIndex(defaultProvider, poiProviders));
            manager.replaceFragment(PoiFragment.class, poiName, true, args, this);
        }
    }

    public static int getDefaultProviderIndex(Class<? extends PoiProvider> defaultProvider, PoiProvider[] poiProviders) {
        if (poiProviders == null || defaultProvider == null) {
            return 0;
        }
        for (int i = 0; i < poiProviders.length; i++) {
            if (defaultProvider.isInstance(poiProviders[i])) {
                return i;
            }
        }
        return 0;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mResultCallback != null) {
            ((PoiFragmentResultCallback) this.mResultCallback).onPoiFragmentResult(null);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mNavigationChanged) {
            NaviNativeActivity.hideNavigationBar(getActivity());
        }
    }

    public void onPoiFragmentResult(PoiListItem result) {
        if (result != null && this.mResultCallback != null) {
            ((PoiFragmentResultCallback) this.mResultCallback).onPoiFragmentResult(result);
        }
    }

    public boolean dismissOnFinish() {
        return false;
    }
}
