package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.map.data.TrafficBubbleInfo;

public interface TrafficBubbleListener extends CoreEventListener {
    void hideTrafficBubble();

    void onTrafficBubble(TrafficBubbleInfo trafficBubbleInfo);
}
