package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import io.fabric.sdk.android.services.persistence.PreferenceStore;

class AnswersPreferenceManager {
    private final PreferenceStore prefStore;

    public AnswersPreferenceManager(PreferenceStore prefStore) {
        this.prefStore = prefStore;
    }

    @SuppressLint({"CommitPrefEdits"})
    public void setAnalyticsLaunched() {
        this.prefStore.save(this.prefStore.edit().putBoolean("analytics_launched", true));
    }

    @SuppressLint({"CommitPrefEdits"})
    public boolean hasAnalyticsLaunched() {
        return this.prefStore.get().getBoolean("analytics_launched", false);
    }
}
