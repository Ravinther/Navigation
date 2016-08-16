package com.sygic.aura.settings.first_run.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.C0001R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.MultiSelectComponentsResultCallback;
import com.sygic.aura.helper.CustomDialogFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.fragments.SettingsFragment;
import com.sygic.aura.store.fragment.MultiSelectComponentsFragment;

public class FirstRunWizardStartFragment extends AbstractScreenFragment implements MultiSelectComponentsResultCallback, BackPressedListener {
    private long mComponentsTotalSize;
    private Object[][] mInstallItems;
    private Object[][] mUninstallItems;

    /* renamed from: com.sygic.aura.settings.first_run.fragments.FirstRunWizardStartFragment.1 */
    class C16421 implements OnClickListener {
        C16421() {
        }

        public void onClick(View view) {
            if (SettingsManager.nativeCanShowMap()) {
                Bundle logParams = new Bundle();
                logParams.putString("screenName", "FRW - Skip map");
                SygicAnalyticsLogger.logEvent(FirstRunWizardStartFragment.this.getActivity(), EventType.FRW, logParams);
                FirstRunWizardStartFragment.this.proceed(null);
                return;
            }
            logParams = new Bundle();
            logParams.putString("screenName", "FRW - Download map");
            SygicAnalyticsLogger.logEvent(FirstRunWizardStartFragment.this.getActivity(), EventType.FRW, logParams);
            Bundle bundle = new Bundle();
            String title = ResourceManager.getCoreString(view.getContext(), 2131165799);
            bundle.putString(AbstractFragment.ARG_TITLE, title);
            bundle.putInt(SettingsFragment.ARG_MENU, 2131755012);
            bundle.putString("map_list_id", "-2");
            bundle.putBoolean("from_frw_start", true);
            Fragments.replace(FirstRunWizardStartFragment.this.getActivity(), MultiSelectComponentsFragment.class, title, bundle, true, FirstRunWizardStartFragment.this);
        }
    }

    public FirstRunWizardStartFragment() {
        this.mComponentsTotalSize = 0;
        setWantsNavigationData(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle logParams = new Bundle();
        logParams.putString("screenName", "FRW - Start");
        SygicAnalyticsLogger.logEvent(getActivity(), EventType.FRW, logParams);
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackFrwWelcome();
        SettingsManager.nativeStartOnlineServices();
    }

    public void onAttach(Activity activity) {
        activity.setRequestedOrientation(1);
        super.onAttach(activity);
    }

    public void onDestroyView() {
        super.onDestroyView();
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903106, container, false);
        ((Button) view.findViewById(C0001R.id.start)).setOnClickListener(new C16421());
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        return view;
    }

    public boolean onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            return false;
        }
        CustomDialogFragment.showExitDialog(getActivity());
        return true;
    }

    public void setComponentsSelected(Object[][] installItems, Object[][] uninstallItems) {
        this.mInstallItems = installItems;
        this.mUninstallItems = uninstallItems;
    }

    public void setTotalComponentsSize(long size) {
        this.mComponentsTotalSize = size;
    }

    public void onComponentsFragmentResult(boolean changed) {
        if (changed) {
            String[] installIds = null;
            if (!(this.mInstallItems == null || this.mUninstallItems == null)) {
                ComponentManager.nativeInstall(this.mInstallItems, this.mUninstallItems);
                installIds = new String[this.mInstallItems.length];
                for (int i = 0; i < this.mInstallItems.length; i++) {
                    installIds[i] = (String) this.mInstallItems[i][0];
                }
                ((NaviNativeActivity) getActivity()).registerDownloadProgressNotification(installIds);
            }
            proceed(installIds);
        }
    }

    public void onDashboardFragmentFinished() {
        onComponentsFragmentResult(false);
    }

    private void proceed(String[] installIds) {
        Bundle bundle = new Bundle();
        bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(getActivity(), 2131165471));
        bundle.putStringArray("ids_install", installIds);
        bundle.putLong("total_size", this.mComponentsTotalSize);
        Fragments.replace(getActivity(), FirstRunWizardDownloadFragment.class, "fragment_frw_downloading_tag", bundle, false, null);
    }
}
