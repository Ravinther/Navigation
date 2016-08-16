package com.sygic.aura.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.sygic.aura.SygicActivityWrapper;
import com.sygic.aura.dashboard.navigationDrawer.NavigationDrawer;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.search.data.LocationQuery;

public class FragmentMethodsActivity extends SygicActivityWrapper implements FragmentManagerInterface {
    private boolean mClearBackStackRunning;
    protected ViewGroup mFragmentsRoot;
    protected LocationQuery mLocationQuery;
    protected NavigationDrawer mNavigationDrawer;
    protected RouteNavigateData mRouteData;

    public Fragment findFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    public RouteNavigateData getRouteData() {
        return this.mRouteData;
    }

    private int getLastOccupiedPlacePosition() {
        int pos = this.mFragmentsRoot.getChildCount();
        if (pos <= 1) {
            return getNewFragmentPlace();
        }
        while (((ViewGroup) this.mFragmentsRoot.getChildAt(pos - 1)).getChildCount() <= 0 && pos > 2) {
            pos--;
        }
        return pos;
    }

    private int getLastFreePlacePosition() {
        int pos = this.mFragmentsRoot.getChildCount();
        if (pos <= 1) {
            return getNewFragmentPlace();
        }
        while (((ViewGroup) this.mFragmentsRoot.getChildAt(pos - 1)).getChildCount() <= 0) {
            pos--;
        }
        if (pos < this.mFragmentsRoot.getChildCount()) {
            return pos + 1;
        }
        return getNewFragmentPlace();
    }

    private int getNewFragmentPlace() {
        int fragmentsAddedCount = this.mFragmentsRoot.getChildCount();
        FrameLayout frame = new FrameLayout(this);
        frame.setLayoutParams(new LayoutParams(-1, -1));
        int newViewId = fragmentsAddedCount + 1;
        frame.setId(newViewId);
        this.mFragmentsRoot.addView(frame);
        return newViewId;
    }

    private void addFragment(Fragment fragment, String tag, boolean addToBackStack, int startAnimation, int endAnimation) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!(startAnimation == 0 || endAnimation == 0)) {
            transaction.setCustomAnimations(startAnimation, endAnimation, startAnimation, endAnimation);
        }
        transaction.add(getLastFreePlacePosition(), fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(getLastOccupiedPlacePosition(), fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    private Bundle packData(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putParcelable(AbstractFragment.LOCATION_QUERY, this.mLocationQuery);
        return bundle;
    }

    public void addFragment(Class<? extends AbstractScreenFragment> fragmentClass, String tag, boolean addToBackStack) {
        addFragment(fragmentClass, tag, addToBackStack, null);
    }

    public void addFragment(Class<? extends AbstractScreenFragment> fragmentClass, String tag, boolean addToBackStack, FragmentResultCallback callback) {
        addFragment((Class) fragmentClass, tag, addToBackStack, callback, null);
    }

    public void addFragment(Class<? extends AbstractScreenFragment> fragmentClass, String tag, boolean addToBackStack, FragmentResultCallback callback, Bundle additionalData) {
        addFragment(fragmentClass, tag, addToBackStack, callback, additionalData, 0, 0);
    }

    public void addFragment(Class<? extends AbstractScreenFragment> fragmentClass, String tag, boolean addToBackStack, FragmentResultCallback callback, Bundle additionalData, int startAnimation, int endAnimation) {
        try {
            Fragment fragment = (AbstractFragment) fragmentClass.newInstance();
            Bundle bundle = packData(additionalData);
            if (fragment.wantsNavigationData()) {
                bundle.putParcelable(AbstractFragment.NAVIGATE_DATA, this.mRouteData);
            }
            fragment.setArguments(bundle);
            fragment.registerResultCallback(callback);
            addFragment(fragment, tag, addToBackStack, startAnimation, endAnimation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(Class<? extends AbstractScreenFragment> fragmentClass, String tag, boolean addToBackStack) {
        replaceFragment(fragmentClass, tag, addToBackStack, null);
    }

    public void replaceFragment(Class<? extends AbstractScreenFragment> fragmentClass, String tag, boolean addToBackStack, Bundle additionalData) {
        replaceFragment(fragmentClass, tag, addToBackStack, additionalData, null);
    }

    public void replaceFragment(Class<? extends AbstractScreenFragment> fragmentClass, String tag, boolean addToBackStack, Bundle additionalData, FragmentResultCallback callback) {
        try {
            Fragment fragment = (AbstractFragment) fragmentClass.newInstance();
            Bundle bundle = packData(additionalData);
            if (fragment.wantsNavigationData()) {
                bundle.putParcelable(AbstractFragment.NAVIGATE_DATA, this.mRouteData);
            }
            fragment.setArguments(bundle);
            fragment.registerResultCallback(callback);
            replaceFragment(fragment, tag, addToBackStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean clearBackStackRunning() {
        return this.mClearBackStackRunning;
    }

    public boolean clearBackStack(boolean immediate) {
        return clearBackStack(null, immediate);
    }

    public boolean clearBackStack(String tag, boolean immediate) {
        return clearBackStack(tag, immediate, 0);
    }

    public boolean clearBackStack(String tag, boolean immediate, int flags) {
        if (clearBackStackRunning()) {
            return false;
        }
        this.mClearBackStackRunning = true;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (tag == null && fragmentManager.getBackStackEntryCount() > 0) {
            tag = fragmentManager.getBackStackEntryAt(0).getName();
            flags = 1;
        }
        if (immediate) {
            try {
                fragmentManager.popBackStackImmediate(tag, flags);
            } catch (IllegalStateException e) {
                CrashlyticsHelper.logException(getClass().getName(), "clearBackStack", e);
                this.mClearBackStackRunning = false;
                return false;
            }
        }
        fragmentManager.popBackStack(tag, flags);
        this.mClearBackStackRunning = false;
        return true;
    }
}
