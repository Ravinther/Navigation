package com.bosch.myspin.serversdk;

import com.bosch.myspin.serversdk.vehicledata.MySpinVehicleData;

public interface VehicleDataListener {
    void onVehicleDataUpdate(long j, MySpinVehicleData mySpinVehicleData);
}
