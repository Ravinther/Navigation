package com.bosch.myspin.serversdk;

import android.location.Location;

@Deprecated
public interface OnCarDataChangeListener {
    @Deprecated
    void onCarStationaryStatusChanged(boolean z);

    @Deprecated
    void onDayNightModeChanged(boolean z);

    @Deprecated
    void onLocationUpdate(Location location);
}
