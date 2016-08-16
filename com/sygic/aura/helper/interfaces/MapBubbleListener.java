package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.map.data.BubbleInfo;

public interface MapBubbleListener extends CoreEventListener {
    void onHideBubble();

    void onMoveBubble();

    void onShowBubble(BubbleInfo bubbleInfo);
}
