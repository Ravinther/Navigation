package com.sygic.aura.map.bubble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.data.BubbleBaseInfo;
import com.sygic.aura.map.view.BubbleView;

abstract class BubbleController implements BubbleInterface {
    protected final ViewGroup mBubbleParent;
    protected final Context mContext;

    protected BubbleController(Context context) {
        this.mContext = context;
        this.mBubbleParent = (ViewGroup) SygicHelper.getSurface().getParent();
    }

    protected BubbleView createBubbleView(BubbleBaseInfo bubbleInfo) {
        return createBubbleView(bubbleInfo, 2130903230);
    }

    protected BubbleView createBubbleView(BubbleBaseInfo bubbleInfo, int layout) {
        BubbleView mapBubble = (BubbleView) (bubbleInfo == null ? null : ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(layout, this.mBubbleParent, false));
        if (mapBubble != null) {
            mapBubble.setTag(bubbleInfo);
            mapBubble.setOnClickListener(this);
            mapBubble.setupImageVisibility();
            this.mBubbleParent.addView(mapBubble, 2);
        }
        return mapBubble;
    }

    protected void makeBubbleVisible(View mapBubble, boolean toShow) {
        int visibility = toShow ? 0 : 4;
        if (mapBubble != null && mapBubble.getVisibility() != visibility) {
            mapBubble.setVisibility(visibility);
        }
    }
}
