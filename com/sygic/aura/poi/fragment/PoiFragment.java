package com.sygic.aura.poi.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.GuiUtils;
import com.sygic.aura.helper.loading.loadable.LazyPoiListItemWrapper;
import com.sygic.aura.poi.PoiFragmentInterface;
import com.sygic.aura.poi.PoiFragmentResultCallback;
import com.sygic.aura.poi.adapter.PoiPagerAdapter;
import com.sygic.aura.poi.provider.PoiProvider;
import com.sygic.aura.poi.provider.SygicPoiProvider;
import com.sygic.aura.search.data.SearchBox;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.views.ManagedViewPager;
import com.sygic.aura.views.font_specials.SToolbar;

public class PoiFragment extends AbstractScreenFragment implements PoiFragmentInterface {
    public static final String ARG_DATA = "data";
    public static final String ARG_DEFAULT = "default_provider";
    public static final String ARG_GROUP_ID = "group_id";
    public static final String ARG_PROVIDERS = "providers";
    public static final String ARG_SELECT_MODE = "select_mode";
    public static final String ARG_START_TASK = "start_task";
    protected Parcelable mData;
    private int mDefaultPage;
    private int mGroupId;
    private boolean mNavigationChanged;
    private ManagedViewPager mPager;
    private PoiProvider[] mProviders;
    private CharSequence mQuery;
    private boolean mSearchInitialised;
    private boolean mSelectMode;
    private String mSelectedLanguage;
    private String mTitle;

    /* renamed from: com.sygic.aura.poi.fragment.PoiFragment.1 */
    class C14541 extends SimpleOnPageChangeListener {
        private int mLastPage;
        final /* synthetic */ View val$view;

        C14541(View view) {
            this.val$view = view;
            this.mLastPage = -1;
        }

        public void onPageSelected(int position) {
            NaviNativeActivity.hideKeyboard(this.val$view.getWindowToken());
            if (this.mLastPage != -1 && Math.abs(this.mLastPage - position) <= 1) {
                ((PoiPagerFragment) PoiFragment.this.mPager.getAdapter().instantiateItem(PoiFragment.this.mPager, this.mLastPage)).cancelPoiLoading();
            }
            PoiPagerFragment frag = (PoiPagerFragment) PoiFragment.this.mPager.getAdapter().instantiateItem(PoiFragment.this.mPager, position);
            frag.onSetupToolbar(PoiFragment.this.mToolbar);
            frag.startSearchTask(PoiFragment.this.mQuery);
            this.mLastPage = position;
        }
    }

    public PoiFragment() {
        this.mNavigationChanged = false;
        this.mSearchInitialised = false;
        this.mSelectMode = false;
        this.mQuery = "";
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(this.mTitle);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mTitle = bundle.getString(AbstractFragment.ARG_TITLE);
            this.mData = bundle.getParcelable(ARG_DATA);
            this.mGroupId = bundle.getInt(ARG_GROUP_ID, 0);
            this.mSelectMode = bundle.getBoolean(ARG_SELECT_MODE, false);
            this.mProviders = (PoiProvider[]) bundle.getParcelableArray(ARG_PROVIDERS);
            this.mDefaultPage = bundle.getInt(ARG_DEFAULT, 0);
        }
        loadNavigationData();
        ensureProviders();
    }

    private void ensureProviders() {
        if (this.mProviders == null) {
            this.mProviders = new PoiProvider[]{new SygicPoiProvider()};
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903135, container, false);
        FragmentActivity activity = getActivity();
        if (NaviNativeActivity.isNavigationBarHidden(activity)) {
            this.mNavigationChanged = true;
            NaviNativeActivity.showNavigationBar(activity, false);
        }
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PoiPagerAdapter adapter = new PoiPagerAdapter(getChildFragmentManager(), this.mProviders, this.mDefaultPage, this.mLocationQuery, this.mData, this.mGroupId, this);
        this.mPager = (ManagedViewPager) view.findViewById(2131624309);
        this.mPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(2131624308);
        if (this.mProviders.length <= 1) {
            tabLayout.setVisibility(8);
        } else {
            int[] icons = new int[this.mProviders.length];
            for (int i = 0; i < this.mProviders.length; i++) {
                icons[i] = this.mProviders[i].getIconRes();
            }
            GuiUtils.setupIconTabsWithViewPager(tabLayout, this.mPager, icons);
        }
        this.mPager.setCurrentItem(this.mDefaultPage);
        this.mPager.addOnPageChangeListener(new C14541(view));
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mNavigationChanged) {
            NaviNativeActivity.hideNavigationBar(getActivity());
        }
        LazyPoiListItemWrapper.clearCache();
    }

    public void finish(PoiListItem result) {
        if (result == null) {
            return;
        }
        if (this.mSelectMode) {
            performHomeAction();
            SearchBox.nativeShowOnMap(result.getMapSel());
        } else if (this.mResultCallback != null) {
            if (((PoiFragmentResultCallback) this.mResultCallback).dismissOnFinish()) {
                performHomeAction();
            }
            ((PoiFragmentResultCallback) this.mResultCallback).onPoiFragmentResult(result);
        }
    }

    public SToolbar getToolbar() {
        return this.mToolbar;
    }

    public void onQueryChanged(CharSequence query) {
        this.mQuery = query;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mResultCallback != null) {
            ((PoiFragmentResultCallback) this.mResultCallback).onPoiFragmentResult(null);
        }
    }

    public void dismiss() {
        performHomeAction();
    }

    public String getSelectedLanguage() {
        if (this.mSelectedLanguage == null) {
            this.mSelectedLanguage = SettingsManager.nativeGetSelectedLanguage();
        }
        return this.mSelectedLanguage;
    }
}
