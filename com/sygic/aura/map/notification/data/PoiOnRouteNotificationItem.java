package com.sygic.aura.map.notification.data;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.notification.NotificationCenterView;
import com.sygic.aura.navigate.ActionControlFragment;
import com.sygic.aura.navigate.ActionControlFragment.Mode;
import com.sygic.aura.poi.PoiManager;

public class PoiOnRouteNotificationItem extends NotificationCenterItem {
    protected PoiOnRouteNotificationItem(int id, int notificationType, int type, String primaryText, String secondaryText) {
        super(id, notificationType, type, primaryText, secondaryText);
    }

    public void onClick(Context context) {
        super.onClick(context);
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            MapSelection mapSel = PoiManager.nativeGetNavselFromPorNotifItem(this.mId);
            if (mapSel != null) {
                MapOverlayFragment mapOverlayFragment = (MapOverlayFragment) manager.findFragmentByTag("fragment_browse_controls_tag");
                if (mapOverlayFragment != null) {
                    mapOverlayFragment.setShouldIgnoreUnlock(true);
                }
                Bundle args = new Bundle();
                args.putParcelable("mapsel", mapSel);
                args.putString("icon_char", this.mPrimaryText);
                args.putParcelable("mode", Mode.POR);
                manager.replaceFragment(ActionControlFragment.class, "action_control", true, args);
            }
        }
    }

    public boolean addViewToHierarchy(NotificationCenterView notificationCenterView, View notificationItemView) {
        if (notificationCenterView.getChildCount() >= notificationCenterView.getMaxItemsCount()) {
            return false;
        }
        notificationCenterView.addView(notificationItemView);
        return true;
    }
}
