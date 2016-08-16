package com.sygic.aura.settings.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.fragments.WorkingDialogFragment;
import com.sygic.aura.fragments.interfaces.LoginFragmentResultCallback;
import com.sygic.aura.helper.EventReceivers.AccountEventsReceiver;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.interfaces.AccountListener;
import com.sygic.aura.network.AccountManager;
import com.sygic.aura.network.AccountManager.EChangePwdStatus;
import com.sygic.aura.network.AccountManager.ELoginStatus;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.preferences.DependencyStatePreference;
import com.sygic.aura.views.SmartProgressBar;

public class LoginFragment extends SettingsFragment implements OnCheckedChangeListener, DialogFragmentClickListener, AccountListener {
    private CheckBoxPreference mDependencyPreference;
    private WorkingDialogFragment mDialog;
    private final Runnable mResetRunnable;
    private SmartProgressBar mSmartProgressBar;

    /* renamed from: com.sygic.aura.settings.fragments.LoginFragment.1 */
    class C16441 implements Runnable {
        C16441() {
        }

        public void run() {
            LoginFragment.this.reset();
            Context context = LoginFragment.this.getActivity();
            if (context != null) {
                LoginFragment.this.displayToast(ResourceManager.getCoreString(context, 2131165495));
            }
        }
    }

    public LoginFragment() {
        this.mResetRunnable = new C16441();
    }

    public void displayToast(String message) {
        if (message != null) {
            SToast.makeText(getActivity(), message, 0).show();
        }
    }

    public void onLoginFinished(Boolean connected, ELoginStatus status) {
        displayToast(AccountManager.getMessage(getActivity(), status));
        if (connected.booleanValue()) {
            performHomeAction();
        } else {
            reset();
        }
        if (this.mResultCallback != null) {
            ((LoginFragmentResultCallback) this.mResultCallback).onLoginFragmentResult(connected.booleanValue());
            if (connected.booleanValue()) {
                this.mResultCallback = null;
            }
        }
    }

    public void onDownloadCompleted() {
        AccountManager.onDownloadCompleted();
    }

    public void showWaitingDialog() {
        ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(getView().getWindowToken(), 0);
        this.mDialog = WorkingDialogFragment.newInstance(this);
        this.mDialog.show(getFragmentManager(), "working_dialog");
    }

    public void showWaitingDialogMessage(Integer iStatus) {
        EChangePwdStatus eStatus = EChangePwdStatus.createFromInt(iStatus.intValue());
        if (this.mDialog == null) {
            return;
        }
        if (eStatus == EChangePwdStatus.StatusOk) {
            this.mDialog.setMessage(true, ResourceManager.getCoreString(this.mDialog.getActivity(), 2131165522));
        } else if (eStatus != EChangePwdStatus.StatusBadPwd) {
            this.mDialog.setMessage(false, ResourceManager.getCoreString(this.mDialog.getActivity(), 2131165521));
        }
    }

    public void onPositiveButtonClicked(Editable text) {
        getActivity().onBackPressed();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountEventsReceiver.registerAccountListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        AccountEventsReceiver.unregisterAccountListener(this);
    }

    public void onResume() {
        super.onResume();
        if (this.mDependencyPreference != null && !this.mDependencyPreference.isChecked()) {
            this.mSmartProgressBar.postDelayed(this.mResetRunnable, 20000);
        }
    }

    public void onPause() {
        super.onPause();
        this.mSmartProgressBar.removeCallbacks(this.mResetRunnable);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        this.mSmartProgressBar = new SmartProgressBar(getActivity());
        view.addView(this.mSmartProgressBar);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mDependencyPreference = (CheckBoxPreference) findPreference(getString(2131165286));
        ((DependencyStatePreference) this.mDependencyPreference).setOnCheckedChangedListener(this);
        reset();
        super.onViewCreated(view, savedInstanceState);
    }

    private void reset() {
        if (this.mDependencyPreference != null) {
            this.mDependencyPreference.setChecked(true);
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            this.mSmartProgressBar.stopWithFadeOut();
            this.mSmartProgressBar.removeCallbacks(this.mResetRunnable);
            return;
        }
        this.mSmartProgressBar.startWithFadeIn();
        this.mSmartProgressBar.postDelayed(this.mResetRunnable, 20000);
    }
}
