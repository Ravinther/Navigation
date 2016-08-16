package com.sygic.aura.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.TextUtils;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.resources.ResourceManager;

public class UserTTSDialogFragment extends InputDialogFragment {
    public static UserTTSDialogFragment newInstance(Context context, int titleResId, String preselectedName, String hintText, DialogFragmentClickListener callback, OnCancelListener cancelCallback) {
        Bundle args = new Bundle();
        args.putString("InputText", preselectedName);
        args.putString("HintText", hintText);
        String str = "DialogTitle";
        if (titleResId <= 0) {
            titleResId = 2131165491;
        }
        args.putString(str, ResourceManager.getCoreString(context, titleResId));
        UserTTSDialogFragment f = new UserTTSDialogFragment();
        f.setArguments(args);
        f.setCallback(callback);
        f.setCancelCallback(cancelCallback);
        return f;
    }

    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(-1).setEnabled(!TextUtils.isEmpty(getArguments().getString("InputText")));
    }
}
