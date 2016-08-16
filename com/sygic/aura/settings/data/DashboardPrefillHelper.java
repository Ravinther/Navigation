package com.sygic.aura.settings.data;

import android.content.SharedPreferences;
import android.util.SparseArray;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.WidgetManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DashboardPrefillHelper {
    private static final String PREFS_PREFILL_VERSION = "dashboardprefill_version";
    private static final int VERSION = 3;

    private DashboardPrefillHelper() {
    }

    public static void prefill(SharedPreferences prefs) {
        int writtenVersion = prefs.getInt(PREFS_PREFILL_VERSION, -1);
        if (VERSION > writtenVersion) {
            doPrefill(writtenVersion, VERSION);
            prefs.edit().putInt(PREFS_PREFILL_VERSION, VERSION).apply();
        }
    }

    private static void doPrefill(int oldVersion, int newVersion) {
        SparseArray<List<WidgetItem>> versions = initVersions();
        ArrayList<WidgetItem> prefillWidgets = new ArrayList();
        for (int i = oldVersion + 1; i <= newVersion; i++) {
            List<WidgetItem> version = (List) versions.get(i);
            if (version != null) {
                prefillWidgets.addAll(version);
            }
        }
        WidgetItem[] dbWidgets = WidgetManager.nativeGetWidgets();
        Iterator it = prefillWidgets.iterator();
        while (it.hasNext()) {
            WidgetItem prefillWidget = (WidgetItem) it.next();
            if (!contains(dbWidgets, prefillWidget)) {
                WidgetManager.nativeAddWidgetItem(prefillWidget);
            }
        }
    }

    private static boolean contains(WidgetItem[] widgetsToPrefill, WidgetItem widget) {
        for (WidgetItem prefillWidget : widgetsToPrefill) {
            if (prefillWidget.getType() == widget.getType()) {
                return true;
            }
        }
        return false;
    }

    private static SparseArray<List<WidgetItem>> initVersions() {
        SparseArray<List<WidgetItem>> sparseArray = new SparseArray();
        sparseArray.put(0, Arrays.asList(new WidgetItem[]{new WidgetItem(EWidgetType.widgetTypeHome, EWidgetSize.widgetSizeHalfRow), new WidgetItem(EWidgetType.widgetTypeBlackBox, EWidgetSize.widgetSizeHalfRow), new WidgetItem(EWidgetType.widgetTypeHUD, EWidgetSize.widgetSizeHalfRow), new WidgetItem(EWidgetType.widgetTypeTravelBook, EWidgetSize.widgetSizeHalfRow), new WidgetItem(EWidgetType.widgetTypeSOS, EWidgetSize.widgetSizeHalfRow)}));
        sparseArray.put(1, Arrays.asList(new WidgetItem[]{new WidgetItem(EWidgetType.widgetTypeNavigateToPhoto, EWidgetSize.widgetSizeHalfRow)}));
        sparseArray.put(2, Arrays.asList(new WidgetItem[]{new WidgetItem(EWidgetType.widgetTypeWork, EWidgetSize.widgetSizeHalfRow)}));
        sparseArray.put(VERSION, Arrays.asList(new WidgetItem[]{new WidgetItem(EWidgetType.widgetTypeParking, EWidgetSize.widgetSizeHalfRow)}));
        return sparseArray;
    }
}
