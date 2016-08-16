package com.sygic.aura.pluginmanager;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.WidgetManager;
import com.sygic.aura.dashboard.fragment.PluginInfoFragment;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.pluginmanager.plugin.Plugin;
import com.sygic.aura.pluginmanager.plugin.Plugin.PluginCallback;
import com.sygic.aura.pluginmanager.plugin.PluginWrapper;
import com.sygic.aura.pluginmanager.plugin.SectionWrapper;
import com.sygic.aura.pluginmanager.plugin.memo.HomePlugin;
import com.sygic.aura.pluginmanager.plugin.memo.ParkingPlugin;
import com.sygic.aura.pluginmanager.plugin.memo.WorkPlugin;
import com.sygic.aura.pluginmanager.plugin.product.BlackBoxPlugin;
import com.sygic.aura.pluginmanager.plugin.product.HUDPlugin;
import com.sygic.aura.pluginmanager.plugin.product.NavigateToPhotoPlugin;
import com.sygic.aura.pluginmanager.plugin.product.SosPlugin;
import com.sygic.aura.pluginmanager.plugin.product.TravelbookPlugin;
import com.sygic.aura.pluginmanager.plugin.shortcut.ContactsShortcutPlugin;
import com.sygic.aura.pluginmanager.plugin.shortcut.FavouritesShortcutPlugin;
import com.sygic.aura.pluginmanager.plugin.shortcut.RecentsShortcutPlugin;
import com.sygic.aura.resources.ResourceManager;
import java.util.ArrayList;

public class PluginManager {
    public static ArrayList<PluginWrapper> getPluginWrappers(Resources res, PluginCallback callback) {
        WidgetItem[] widgetsInDb = WidgetManager.nativeGetWidgets();
        ArrayList<PluginWrapper> pluginWrappers = new ArrayList();
        pluginWrappers.add(new SectionWrapper(ResourceManager.getCoreString(res, 2131165535)));
        pluginWrappers.add(new PluginWrapper(newHUDPlugin(res, widgetsInDb, callback)));
        pluginWrappers.add(new PluginWrapper(newBlackBoxPlugin(res, widgetsInDb, callback)));
        pluginWrappers.add(new PluginWrapper(newTravelbookPlugin(res, widgetsInDb, callback)));
        pluginWrappers.add(new SectionWrapper(ResourceManager.getCoreString(res, 2131165534)));
        pluginWrappers.add(new PluginWrapper(newHomePlugin(res, widgetsInDb, callback)));
        pluginWrappers.add(new PluginWrapper(newSosPlugin(res, widgetsInDb, callback)));
        pluginWrappers.add(new PluginWrapper(newWorkPlugin(res, widgetsInDb, callback)));
        pluginWrappers.add(new PluginWrapper(newParkingPlugin(res, widgetsInDb, callback)));
        if (!InCarConnection.isInCarConnected()) {
            pluginWrappers.add(new PluginWrapper(newNavigateToPhotoPlugin(res, widgetsInDb, callback)));
        }
        pluginWrappers.add(new SectionWrapper(ResourceManager.getCoreString(res, 2131165537)));
        pluginWrappers.add(new PluginWrapper(newFavouritesPlugin(res, callback)));
        pluginWrappers.add(new PluginWrapper(newRecentsPlugin(res, callback)));
        pluginWrappers.add(new PluginWrapper(newContactsPlugin(res, callback)));
        return pluginWrappers;
    }

    private static Plugin newNavigateToPhotoPlugin(Resources res, WidgetItem[] widgets, PluginCallback callback) {
        return new NavigateToPhotoPlugin(findInDashboardWidgets(widgets, EWidgetType.widgetTypeNavigateToPhoto), res, callback);
    }

    public static ContactsShortcutPlugin newContactsPlugin(Resources res, PluginCallback callback) {
        return new ContactsShortcutPlugin(res, callback);
    }

    public static RecentsShortcutPlugin newRecentsPlugin(Resources res, PluginCallback callback) {
        return new RecentsShortcutPlugin(res, callback);
    }

    public static FavouritesShortcutPlugin newFavouritesPlugin(Resources res, PluginCallback callback) {
        return new FavouritesShortcutPlugin(res, callback);
    }

    public static WorkPlugin newWorkPlugin(Resources res, WidgetItem[] widgets, PluginCallback callback) {
        return new WorkPlugin(findInDashboardWidgets(widgets, EWidgetType.widgetTypeWork), res, callback);
    }

    public static HomePlugin newHomePlugin(Resources res, WidgetItem[] widgets, PluginCallback callback) {
        return new HomePlugin(findInDashboardWidgets(widgets, EWidgetType.widgetTypeHome), res, callback);
    }

    public static ParkingPlugin newParkingPlugin(Resources res, WidgetItem[] widgets, PluginCallback callback) {
        return new ParkingPlugin(findInDashboardWidgets(widgets, EWidgetType.widgetTypeParking), res, callback);
    }

    public static BlackBoxPlugin newBlackBoxPlugin(Resources res, WidgetItem[] widgets, PluginCallback callback) {
        return new BlackBoxPlugin(findInDashboardWidgets(widgets, EWidgetType.widgetTypeBlackBox), res, callback);
    }

    public static HUDPlugin newHUDPlugin(Resources res, WidgetItem[] widgets, PluginCallback callback) {
        return new HUDPlugin(findInDashboardWidgets(widgets, EWidgetType.widgetTypeHUD), res, callback);
    }

    public static SosPlugin newSosPlugin(Resources res, WidgetItem[] widgets, PluginCallback callback) {
        return new SosPlugin(findInDashboardWidgets(widgets, EWidgetType.widgetTypeSOS), res, callback);
    }

    public static TravelbookPlugin newTravelbookPlugin(Resources res, WidgetItem[] widgets, PluginCallback callback) {
        return new TravelbookPlugin(findInDashboardWidgets(widgets, EWidgetType.widgetTypeTravelBook), res, callback);
    }

    private static WidgetItem findInDashboardWidgets(WidgetItem[] dashboardWidgets, EWidgetType widgetType) {
        if (dashboardWidgets == null) {
            return null;
        }
        for (WidgetItem dashboardWidget : dashboardWidgets) {
            if (dashboardWidget.getType() == widgetType) {
                return dashboardWidget;
            }
        }
        return null;
    }

    public static void showInfoScreen(Activity activity, int title, int text) {
        showInfoScreen(activity, title, text, 0);
    }

    public static void showInfoScreen(Activity activity, int title, int text, int secondText) {
        Bundle bundle = new Bundle();
        bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(SygicHelper.getActivity(), title));
        bundle.putInt("first_text", text);
        bundle.putInt("second_text", secondText);
        Fragments.add(activity, PluginInfoFragment.class, "fragment_plugin_info", bundle);
    }
}
