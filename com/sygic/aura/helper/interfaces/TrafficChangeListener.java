package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.route.data.TrafficItem;

public interface TrafficChangeListener extends CoreEventListener {
    void onTrafficChange(TrafficItem trafficItem);
}
