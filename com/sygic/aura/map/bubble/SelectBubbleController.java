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
import com.sygic.aura.poi.detail.PoiDetailFragment;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.route.fragment.RouteSelectionFragment;

class SelectBubbleController extends MapBubbleController {
    private final RouteNavigateData mRouteNavigateData;

    /* renamed from: com.sygic.aura.map.bubble.SelectBubbleController.1 */
    class C13231 implements OnClickListener {
        final /* synthetic */ BubbleBaseInfo val$bubbleInfo;

        C13231(BubbleBaseInfo bubbleBaseInfo) {
            this.val$bubbleInfo = bubbleBaseInfo;
        }

        public void onClick(View v) {
            MapSelection mapSel = this.val$bubbleInfo.getSelection();
            if (PositionInfo.nativeHasNavSel(mapSel.getPosition())) {
                FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
                if (mngr != null) {
                    SelectBubbleController.this.mRouteNavigateData.changeStart(mapSel);
                    mngr.replaceFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true);
                }
            }
        }
    }

    protected SelectBubbleController() {
        this.mRouteNavigateData = SygicHelper.getFragmentActivityWrapper().getRouteData();
    }

    protected BubbleView createBubbleView(BubbleBaseInfo bubbleInfo) {
        BubbleView mapBubble = super.createBubbleView(bubbleInfo);
        mapBubble.setImageClickListener(new C13231(bubbleInfo));
        mapBubble.setImage(FontDrawable.inflate(this.mContext.getResources(), 2131034143));
        mapBubble.setupImageVisibility();
        return mapBubble;
    }

    public void createBubble(BubbleBaseInfo info) {
        if (info != null) {
            removeBubble();
            this.mViewMapPlaceBubble = createBubbleView(info);
        }
    }

    public void onClick(View view) {
        BubbleInfo info = (BubbleInfo) view.getTag();
        if (info != null) {
            Bundle poiData = new Bundle();
            poiData.putParcelable(PoiDetailFragment.POI_SEL, info.getSelection());
            poiData.putBoolean(PoiDetailFragment.POI_NO_NAVIGATE_OPTIONS, true);
            poiData.putInt(PoiDetailFragment.POI_MENU, 2131755032);
            FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
            if (manager != null) {
                manager.addFragment(PoiDetailFragment.class, "fragment_poi_detail_tag", true, (FragmentResultCallback) manager, poiData);
            }
        }
    }

    public MapSelection getSelection() {
        if (this.mViewMapPlaceBubble != null) {
            BubbleInfo info = (BubbleInfo) this.mViewMapPlaceBubble.getTag();
            if (info != null) {
                return info.getSelection();
            }
        }
        return MapSelection.Empty;
    }
}
