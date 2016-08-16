package com.sygic.aura.fragments.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.route.data.RouteNavigateData;

public interface FragmentManagerInterface {
    void addFragment(Class<? extends AbstractScreenFragment> cls, String str, boolean z);

    void addFragment(Class<? extends AbstractScreenFragment> cls, String str, boolean z, FragmentResultCallback fragmentResultCallback);

    void addFragment(Class<? extends AbstractScreenFragment> cls, String str, boolean z, FragmentResultCallback fragmentResultCallback, Bundle bundle);

    void addFragment(Class<? extends AbstractScreenFragment> cls, String str, boolean z, FragmentResultCallback fragmentResultCallback, Bundle bundle, int i, int i2);

    boolean clearBackStack(String str, boolean z);

    boolean clearBackStack(String str, boolean z, int i);

    boolean clearBackStack(boolean z);

    boolean clearBackStackRunning();

    Fragment findFragmentByTag(String str);

    RouteNavigateData getRouteData();

    void replaceFragment(Class<? extends AbstractScreenFragment> cls, String str, boolean z);

    void replaceFragment(Class<? extends AbstractScreenFragment> cls, String str, boolean z, Bundle bundle);

    void replaceFragment(Class<? extends AbstractScreenFragment> cls, String str, boolean z, Bundle bundle, FragmentResultCallback fragmentResultCallback);
}
