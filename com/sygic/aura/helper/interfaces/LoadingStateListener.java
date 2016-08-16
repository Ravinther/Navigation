package com.sygic.aura.helper.interfaces;

public interface LoadingStateListener {
    void onFirstNonEmptyTick();

    void onLoadingError();

    void onLoadingFinished(boolean z);

    void onLoadingStarted();
}
