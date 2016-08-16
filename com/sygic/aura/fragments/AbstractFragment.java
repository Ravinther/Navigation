package com.sygic.aura.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.views.font_specials.SToolbar;

public abstract class AbstractFragment extends Fragment {
    public static final String ARG_TITLE = "title";
    public static final String LOCATION_QUERY = "location_query";
    public static final String NAVIGATE_DATA = "navi_data";
    protected LocationQuery mLocationQuery;
    protected FragmentResultCallback mResultCallback;
    protected RouteNavigateData mRouteNavigateData;
    private boolean mSaveInstanceStateCalled;
    protected SToolbar mToolbar;
    protected boolean mWantsNavigationData;

    /* renamed from: com.sygic.aura.fragments.AbstractFragment.1 */
    class C12451 implements OnClickListener {
        C12451() {
        }

        public void onClick(View v) {
            AbstractFragment.this.performHomeAction();
        }
    }

    public AbstractFragment() {
        this.mWantsNavigationData = true;
        this.mSaveInstanceStateCalled = false;
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        if (toolbar != null) {
            toolbar.setNavigationIconAsUp();
            toolbar.setNavigationOnClickListener(new C12451());
        }
    }

    public void registerResultCallback(FragmentResultCallback callback) {
        this.mResultCallback = callback;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mLocationQuery = (LocationQuery) getArguments().getParcelable(LOCATION_QUERY);
        this.mSaveInstanceStateCalled = false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mSaveInstanceStateCalled = false;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mToolbar = (SToolbar) view.findViewById(2131624226);
        onSetupToolbar(this.mToolbar);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mSaveInstanceStateCalled = false;
    }

    public void onResume() {
        super.onResume();
        this.mSaveInstanceStateCalled = false;
    }

    public void onPause() {
        super.onPause();
        this.mSaveInstanceStateCalled = true;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mSaveInstanceStateCalled = true;
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        this.mSaveInstanceStateCalled = false;
    }

    public void performHomeAction() {
        if (getFragmentManager() != null && !this.mSaveInstanceStateCalled) {
            getFragmentManager().popBackStack();
        }
    }

    protected void setWantsNavigationData(boolean wantsData) {
        this.mWantsNavigationData = wantsData;
    }

    public final boolean wantsNavigationData() {
        return this.mWantsNavigationData;
    }

    protected void loadNavigationData() {
        this.mRouteNavigateData = (RouteNavigateData) getArguments().getParcelable(NAVIGATE_DATA);
    }

    public RouteNavigateData getRouteNavigateData() {
        return this.mRouteNavigateData;
    }
}
