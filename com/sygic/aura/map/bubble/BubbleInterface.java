package com.sygic.aura.map.bubble;

import android.view.View.OnClickListener;
import com.sygic.aura.map.data.BubbleBaseInfo;

public interface BubbleInterface extends OnClickListener {
    void checkHighlighting(int i);

    void createBubble(BubbleBaseInfo bubbleBaseInfo);

    boolean isVisible();

    void moveBubble();

    void removeBubble();

    void setVisible(boolean z);
}
