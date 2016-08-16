package com.sygic.aura.navigate;

import android.text.TextUtils;
import android.view.View;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.poi.PoiManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.views.font_specials.STextView;

public class PorActionControlDelegate extends ActionControlDelegate {
    private STextView mSubtitleView;

    public PorActionControlDelegate(ActionControlFragment fragment) {
        super(fragment);
    }

    protected void handleOnClick() {
        if (RouteManager.nativeExistValidRoute()) {
            addWaypoint(2131165333);
            super.handleOnClick();
            return;
        }
        getDirections();
    }

    public boolean cancel() {
        if (RouteManager.nativeExistValidRoute()) {
            return super.cancel();
        }
        ((NaviNativeActivity) this.mFragment.getActivity()).setShouldAddDefaultMode(false);
        MapOverlayFragment.setMode(this.mFragment.getActivity(), Mode.FREEDRIVE_INFO_BAR);
        return false;
    }

    protected void setTitle(STextView titleTextView) {
        if (RouteManager.nativeExistValidRoute()) {
            super.setTitle(titleTextView);
        } else {
            titleTextView.setCoreText(2131165328);
        }
    }

    public void onViewCreated(View view) {
        super.onViewCreated(view);
        this.mSubtitleView = (STextView) this.mContainer.findViewById(2131624117);
        setSubtitle(PoiManager.nativeGetSelectedPoiTitle());
    }

    public int getLayoutRes() {
        return 2130903071;
    }

    public void onPoiSelectionChanged(MapSelection mapSel, String title, String icon) {
        super.onPoiSelectionChanged(mapSel, title, icon);
        setSubtitle(title);
    }

    private void setSubtitle(String poiTitle) {
        ResourceManager.makeControlVisible(this.mSubtitleView, !TextUtils.isEmpty(poiTitle), true);
        this.mSubtitleView.setCoreText(poiTitle);
    }

    private void getDirections() {
        if (this.mMapSel != null) {
            if (PositionInfo.nativeHasNavSel(this.mMapSel.getPosition())) {
                this.mFragment.getRouteNavigateData().updateRouteNaviData(new MapSelection[]{this.mMapSel});
                FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
                if (manager != null) {
                    this.mFragment.performHomeAction();
                    manager.addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true);
                    return;
                }
                return;
            }
            SToast.makeText(this.mFragment.getActivity(), 2131165396, 1).show();
        }
    }
}
