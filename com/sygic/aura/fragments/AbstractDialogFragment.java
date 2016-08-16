package com.sygic.aura.fragments;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.support.v4.app.DialogFragment;

public class AbstractDialogFragment extends DialogFragment {
    public void onStart() {
        super.onStart();
        if (VERSION.SDK_INT >= 19 && isFullscreen()) {
            toggleFullscreen();
        }
    }

    @TargetApi(19)
    protected void toggleFullscreen() {
        getDialog().getWindow().getDecorView().setSystemUiVisibility(getDialog().getWindow().getDecorView().getSystemUiVisibility() ^ 4);
    }

    @TargetApi(19)
    protected boolean isFullscreen() {
        return (getActivity().getWindow().getAttributes().flags & 1024) != 0;
    }
}
