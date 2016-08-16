package com.sygic.aura.route.data.infobar_slots;

import android.view.View;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.resources.ResourceManager;

public class ElevationSlot extends SingleValueSlot {
    private String mIcon;
    private boolean mInvalid;
    private float mLastValue;

    public ElevationSlot() {
        this.mLastValue = -1.0f;
        this.mInvalid = false;
    }

    protected void findSubViews(View rootView) {
        super.findSubViews(rootView);
        this.mIcon = getContext().getString(2131166132);
        setupViewValue(this.mIcon + ResourceManager.nativeFormatAltitude(0.0f, false));
    }

    protected void executeImpl() {
        boolean z;
        float altitude = PositionInfo.nativeGetActualVehicleAltitude();
        if (this.mLastValue != altitude) {
            setupViewValue(this.mIcon + ResourceManager.nativeFormatAltitude(altitude > -9999999.0f ? altitude : 0.0f, false));
            this.mLastValue = altitude;
        }
        boolean z2 = this.mInvalid;
        if (altitude <= -9999999.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z2 != z) {
            this.mInvalid ^= 1;
            setTextColor(this.mInvalid ? getColor(2131558717) : getColor(17170443));
        }
    }

    public long getUpdateInterval() {
        return 2000;
    }
}
