package com.sygic.aura.blackbox.interfaces;

import com.sygic.aura.fragments.interfaces.FragmentResultCallback;

public interface BlackBoxFragmentResultCallback extends FragmentResultCallback {
    void onBlackBoxMaximized();

    void onBlackBoxMinimized();

    void onBlackBoxRecordFinished(boolean z);

    void onBlackBoxRecordStarted();
}
