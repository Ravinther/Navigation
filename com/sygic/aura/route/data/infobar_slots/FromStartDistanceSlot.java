package com.sygic.aura.route.data.infobar_slots;

import com.sygic.aura.route.RouteSummary;

public class FromStartDistanceSlot extends DistanceSlot {
    protected long getDistance() {
        return RouteSummary.nativeGetFromStartDistance();
    }
}
