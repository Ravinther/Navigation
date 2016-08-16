package com.sygic.aura.map.bubble;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.BubbleBaseInfo;
import com.sygic.aura.map.data.BubbleInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.view.BubbleView;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.poi.detail.PoiDetailActions;
import com.sygic.aura.poi.detail.PoiDetailFragment;
import com.sygic.aura.poi.detail.PoiDetailFragmentResultCallback;
import com.sygic.aura.resources.FontDrawable;

class MapBubbleController extends BubbleController {
    private BubbleInfo mBubbleInfo;
    protected BubbleView mViewMapPlaceBubble;
    protected boolean mVisible;

    /* renamed from: com.sygic.aura.map.bubble.MapBubbleController.1 */
    class C13221 implements OnClickListener {
        final /* synthetic */ BubbleBaseInfo val$bubbleInfo;

        C13221(BubbleBaseInfo bubbleBaseInfo) {
            this.val$bubbleInfo = bubbleBaseInfo;
        }

        public void onClick(View v) {
            MapSelection mapSel = this.val$bubbleInfo.getSelection();
            if (PositionInfo.nativeHasNavSel(mapSel.getPosition())) {
                FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
                if (manager != null) {
                    ((PoiDetailFragmentResultCallback) manager).onPoiDetailFragmentResult(PoiDetailActions.ACTION_DRIVE_TO, mapSel);
                }
            }
        }
    }

    protected MapBubbleController() {
        super(SygicHelper.getActivity());
        this.mViewMapPlaceBubble = null;
        this.mBubbleInfo = null;
        this.mVisible = true;
    }

    protected BubbleView createBubbleView(BubbleBaseInfo bubbleInfo) {
        BubbleView mapBubble = super.createBubbleView(bubbleInfo);
        mapBubble.setText(((BubbleInfo) bubbleInfo).getLabel());
        mapBubble.setImageClickListener(new C13221(bubbleInfo));
        mapBubble.setImage(FontDrawable.inflate(this.mContext.getResources(), 2131034142));
        mapBubble.setupImageVisibility();
        makeBubbleVisible(mapBubble, this.mVisible);
        return mapBubble;
    }

    protected BubbleView createBubbleView(BubbleBaseInfo bubbleInfo, int layout) {
        BubbleView bubbleView = super.createBubbleView(bubbleInfo, layout);
        makeBubbleVisible(bubbleView, this.mVisible);
        return bubbleView;
    }

    public void onClick(View view) {
        BubbleInfo info = (BubbleInfo) view.getTag();
        if (info != null) {
            Bundle poiData = new Bundle();
            poiData.putLong(PoiDetailFragment.POI_ID, info.getId());
            poiData.putParcelable(PoiDetailFragment.POI_SEL, info.getSelection());
            poiData.putString(PoiDetailFragment.POI_TITLE, info.getLabel());
            FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
            if (manager != null) {
                manager.addFragment(PoiDetailFragment.class, "fragment_poi_detail_tag", true, (FragmentResultCallback) manager, poiData);
            }
        }
    }

    public void removeBubble() {
        if (this.mViewMapPlaceBubble != null) {
            this.mBubbleParent.removeView(this.mViewMapPlaceBubble);
            this.mViewMapPlaceBubble = null;
        }
    }

    public void moveBubble() {
        if (this.mViewMapPlaceBubble != null) {
            this.mViewMapPlaceBubble.moveBubble();
        }
    }

    public void createBubble(BubbleBaseInfo info) {
        if (ComponentManager.nativeGetInstalledMapCount() > 0 && info != null && !info.equals(this.mBubbleInfo)) {
            removeBubble();
            this.mBubbleInfo = (BubbleInfo) info;
            this.mViewMapPlaceBubble = createBubbleView(info);
        }
    }

    public void setVisible(boolean visible) {
        this.mVisible = visible;
        if (this.mViewMapPlaceBubble != null) {
            makeBubbleVisible(this.mViewMapPlaceBubble, visible);
        } else if (this.mBubbleInfo != null) {
            createBubble(this.mBubbleInfo);
        }
    }

    public boolean isVisible() {
        return this.mViewMapPlaceBubble != null;
    }

    public void checkHighlighting(int value) {
    }
}
