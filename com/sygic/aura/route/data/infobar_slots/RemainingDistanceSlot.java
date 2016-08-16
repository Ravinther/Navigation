package com.sygic.aura.route.data.infobar_slots;

import com.sygic.aura.route.RouteSummary;

public class RemainingDistanceSlot extends DistanceSlot {
    protected long getDistance() {
        return RouteSummary.nativeGetRemainingDistance();
    }
}
