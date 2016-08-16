package com.sygic.aura.route.data.infobar_slots;

import android.view.LayoutInflater;
import android.view.View;

public interface Slot {
    void execute(long j);

    long getUpdateInterval();

    View getView(LayoutInflater layoutInflater);

    void removeView();

    void resetTimer();
}
