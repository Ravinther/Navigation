package com.sygic.aura.map.bubble;

import android.view.View;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.data.BubbleBaseInfo;
import com.sygic.aura.map.data.TrafficBubbleInfo;
import com.sygic.aura.map.view.BubbleView;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.base.C1799R;

class TrafficBubbleController extends MapBubbleController {
    protected TrafficBubbleController() {
    }

    protected BubbleView createBubbleView(BubbleBaseInfo info) {
        TrafficBubbleInfo bubbleInfo = (TrafficBubbleInfo) info;
        BubbleView view = super.createBubbleView(bubbleInfo, 2130903231);
        STextView time = (STextView) view.findViewById(C1799R.id.time);
        ((STextView) view.findViewById(2131624119)).setText(ResourceManager.nativeFormatDistance((long) bubbleInfo.getTrafficDistance(), true));
        time.setText(ResourceManager.nativeFormatTimeSpanToShortWords((long) bubbleInfo.getTrafficTime(), false, true, true));
        return view;
    }

    public void createBubble(BubbleBaseInfo info) {
        if (info != null) {
            removeBubble();
            this.mViewMapPlaceBubble = createBubbleView(info);
        }
    }

    public void onClick(View view) {
        if (!MapControlsManager.nativeIsTrafficView()) {
            MapControlsManager.nativeEnterTrafficView();
        }
    }
}
