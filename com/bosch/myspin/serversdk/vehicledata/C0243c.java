package com.bosch.myspin.serversdk.vehicledata;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.bosch.myspin.serversdk.OnCarDataChangeListener;
import com.bosch.myspin.serversdk.VehicleDataListener;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.bosch.myspin.serversdk.vehicledata.nmea.MySpinLocationFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* renamed from: com.bosch.myspin.serversdk.vehicledata.c */
public class C0243c extends Handler {
    private static final LogComponent f440a;
    private Map<VehicleDataListener, Set<Long>> f441b;
    private Set<OnCarDataChangeListener> f442c;
    private HashMap<Long, Bundle> f443d;
    private ArrayList<VehicleDataContainer> f444e;
    private boolean f445f;

    static {
        f440a = LogComponent.EventListener;
    }

    public C0243c() {
        this.f441b = new HashMap();
        this.f442c = new HashSet();
        this.f443d = new HashMap();
        this.f444e = new ArrayList();
    }

    public void handleMessage(Message message) {
        if (message == null) {
            Logger.logWarning(f440a, "VehicleDataHandler/Message is null and is not being handled!!");
            return;
        }
        Logger.logDebug(f440a, "VehicleDataHandler/handleMessage with type:" + message.what);
        if (message.what == 65347) {
            Bundle data = message.getData();
            if (data == null) {
                Logger.logError(f440a, "VehicleDataHandler/received msg without data");
                return;
            }
            long j = data.getLong("com.bosch.myspin.EXTRA_VEHICLE_DATA_KEY");
            data.remove("com.bosch.myspin.EXTRA_VEHICLE_DATA_KEY");
            this.f443d.put(Long.valueOf(j), data);
            for (VehicleDataListener vehicleDataListener : this.f441b.keySet()) {
                if (!((Set) this.f441b.get(vehicleDataListener)).contains(Long.valueOf(j))) {
                    Logger.logDebug(f440a, "VehicleDataHandler/VehicleDataListener not registered for key: " + j);
                } else if (this.f445f && m388b(j)) {
                    Logger.logDebug(f440a, "VehicleDataHandler/Notifying VehicleDataListener with key: " + j);
                    vehicleDataListener.onVehicleDataUpdate(j, new MySpinVehicleData(j, data));
                } else {
                    Logger.logDebug(f440a, "VehicleDataHandler/vehicle data will be not forwarded , mAreFilterSet = " + this.f445f);
                }
            }
            for (OnCarDataChangeListener onCarDataChangeListener : this.f442c) {
                if (!message.getData().containsKey("status") && message.getData().containsKey("value")) {
                    m382a(onCarDataChangeListener, j, new MySpinVehicleData(j, message.getData()));
                }
            }
        } else if (message.what == 65348) {
            Bundle data2 = message.getData();
            data2.setClassLoader(VehicleDataContainer.class.getClassLoader());
            if (data2.containsKey("com.bosch.myspin.KEY_VEHICLE_DATA_FILTER")) {
                this.f444e = message.getData().getParcelableArrayList("com.bosch.myspin.KEY_VEHICLE_DATA_FILTER");
                this.f445f = true;
                return;
            }
            Logger.logError(f440a, "VehicleDataHandler/No key for vehicle data filter found!");
        } else {
            Logger.logWarning(f440a, "VehicleDataHandler/Unknown message type!");
        }
    }

    public void m386a(VehicleDataListener vehicleDataListener, long j) {
        if (this.f441b.containsKey(vehicleDataListener)) {
            ((Set) this.f441b.get(vehicleDataListener)).add(Long.valueOf(j));
        } else {
            Set hashSet = new HashSet();
            hashSet.add(Long.valueOf(j));
            this.f441b.put(vehicleDataListener, hashSet);
        }
        vehicleDataListener.onVehicleDataUpdate(j, m383a(j));
    }

    public MySpinVehicleData m383a(long j) {
        if (!this.f445f || m388b(j)) {
            Bundle bundle = (Bundle) this.f443d.get(Long.valueOf(j));
            if (bundle == null) {
                bundle = new Bundle();
                bundle.putString("status", "unknown");
            }
            return new MySpinVehicleData(j, bundle);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("status", "access denied");
        return new MySpinVehicleData(j, bundle2);
    }

    public void m385a(VehicleDataListener vehicleDataListener) {
        this.f441b.remove(vehicleDataListener);
    }

    public boolean m388b(long j) {
        Iterator it = this.f444e.iterator();
        while (it.hasNext()) {
            if (((VehicleDataContainer) it.next()).m374a() == j) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public void m384a(OnCarDataChangeListener onCarDataChangeListener) {
        if (onCarDataChangeListener != null) {
            this.f442c.add(onCarDataChangeListener);
            MySpinVehicleData a = m383a(2);
            if (!a.containsKey("status") && a.containsKey("value")) {
                m382a(onCarDataChangeListener, 2, a);
            }
            a = m383a(3);
            if (!a.containsKey("status") && a.containsKey("value")) {
                m382a(onCarDataChangeListener, 3, a);
            }
            a = m383a(1);
            if (!a.containsKey("status") && a.containsKey("value")) {
                m382a(onCarDataChangeListener, 1, a);
            }
        }
    }

    @Deprecated
    public void m387b(OnCarDataChangeListener onCarDataChangeListener) {
        this.f442c.remove(onCarDataChangeListener);
    }

    private void m382a(OnCarDataChangeListener onCarDataChangeListener, long j, MySpinVehicleData mySpinVehicleData) {
        Logger.logDebug(f440a, "VehicleDataHandler/notifyDeprecatedListener key: " + j + " value: " + mySpinVehicleData);
        if (j == 2) {
            boolean z;
            if (((Boolean) mySpinVehicleData.get("value")).booleanValue()) {
                z = false;
            } else {
                z = true;
            }
            onCarDataChangeListener.onCarStationaryStatusChanged(z);
        } else if (j == 3) {
            onCarDataChangeListener.onDayNightModeChanged(((Boolean) mySpinVehicleData.get("value")).booleanValue());
        } else if (j == 1) {
            try {
                Location parseNmea = MySpinLocationFactory.parseNmea((String) mySpinVehicleData.get("value"));
                if (parseNmea != null) {
                    onCarDataChangeListener.onLocationUpdate(parseNmea);
                }
            } catch (Throwable e) {
                Logger.logError(f440a, "VehicleDataHandler/Could not parse vehicle data [nmea]", e);
            }
        }
    }
}
