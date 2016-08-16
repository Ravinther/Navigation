package com.sygic.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsCore.Builder;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.widget.helpers.DrawableHelper;
import com.sygic.widget.helpers.NaviHelper;
import io.fabric.sdk.android.Fabric;
import loquendo.tts.engine.TTSConst;

public class TrafficWidgetProvider extends AppWidgetProvider {
    public static String EXTRA_PLACE_LAT;
    public static String EXTRA_PLACE_LON;
    public static String EXTRA_PLACE_TYPE;
    public static float LOCATION_INVALID;
    public static String PREFERENCE_HOME_KEY;
    public static String PREFERENCE_PARKING_KEY;
    public static String PREFERENCE_PLACES_KEY;
    public static String PREFERENCE_WORK_KEY;
    public static boolean TRAFFIC_MODE;

    /* renamed from: com.sygic.widget.TrafficWidgetProvider.1 */
    static /* synthetic */ class C18031 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType;

        static {
            $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType = new int[EMemoType.values().length];
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[EMemoType.memoParking.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static {
        TRAFFIC_MODE = ProjectsConsts.getBoolean(7);
        LOCATION_INVALID = -181.0f;
        PREFERENCE_PLACES_KEY = "com.sygic.widget.traffic.settings";
        PREFERENCE_HOME_KEY = "com.sygic.widget.traffic.home";
        PREFERENCE_WORK_KEY = "com.sygic.widget.traffic.work";
        PREFERENCE_PARKING_KEY = "com.sygic.widget.traffic.parking";
        EXTRA_PLACE_TYPE = "com.sygic.widget.trafficwidget.place.type";
        EXTRA_PLACE_LON = "com.sygic.widget.trafficwidget.place.lon";
        EXTRA_PLACE_LAT = "com.sygic.widget.trafficwidget.place.lat";
    }

    public void onEnabled(Context context) {
        super.onEnabled(context);
        try {
            CrashlyticsCore core = new Builder().disabled(false).build();
            Fabric.with(context, new Crashlytics.Builder().core(core).build(), new CrashlyticsNdk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SygicAnalyticsLogger.getAnalyticsEvent(context, EventType.WIDGET_LIFECYCLE_ACTION).setName("Widget -> Lifecycle action").setValue("action", "Add").logAndRecycle();
    }

    public void onDisabled(Context context) {
        super.onDisabled(context);
        SygicAnalyticsLogger.getAnalyticsEvent(context, EventType.WIDGET_LIFECYCLE_ACTION).setName("Widget -> Lifecycle action").setValue("action", "Remove").logAndRecycle();
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Object obj = -1;
        switch (action.hashCode()) {
            case -1172645946:
                if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    obj = 2;
                    break;
                }
                break;
            case -315183400:
                if (action.equals("com.sygic.widget.trafficwidget.NAVI_ACTION")) {
                    obj = 1;
                    break;
                }
                break;
            case 1619576947:
                if (action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    Integer id = Integer.valueOf(extras.getInt("action_view_id", 0));
                    if (id.intValue() != 0) {
                        if (onViewAction(context, AppWidgetManager.getInstance(context), extras.getInt("appWidgetId", 0), id.intValue())) {
                            return;
                        }
                    }
                }
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                String mode;
                float lat = intent.getFloatExtra(EXTRA_PLACE_LAT, -1.0f);
                float lon = intent.getFloatExtra(EXTRA_PLACE_LON, -1.0f);
                String currentTab = "not_defined";
                switch (C18031.$SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[EMemoType.createFromInt(intent.getIntExtra(EXTRA_PLACE_TYPE, EMemoType.memoUnknown.getValue())).ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        mode = "walk";
                        break;
                    default:
                        mode = "drive";
                        break;
                }
                int appWidgetId = intent.getIntExtra("appWidgetId", 0);
                if (appWidgetId != 0) {
                    int i = getSharedPreferences(context, appWidgetId).getInt("current_tab", 0);
                    if (i == 2131624651) {
                        currentTab = "home";
                    } else if (i == 2131624654) {
                        currentTab = "work";
                    } else if (i == 2131624657) {
                        currentTab = "favourites";
                    } else if (i == 2131624659) {
                        currentTab = "parking";
                    }
                }
                SygicAnalyticsLogger.getAnalyticsEvent(context, EventType.WIDGET_START_NAVI).setName("Widget -> Start navigation").setValue("Clicked tab", currentTab).logAndRecycle();
                NaviHelper.navigateSygic(context, lat, lon, mode);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                if (networkInfo != null && networkInfo.isConnected()) {
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(new ComponentName(context, TrafficWidgetProvider.class)), 2131624663);
                    break;
                }
        }
        super.onReceive(context, intent);
    }

    protected PendingIntent getActionViewIntent(Context context, int widgetId, int id) {
        Intent clickIntent = new Intent(context, TrafficWidgetProvider.class);
        clickIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        clickIntent.putExtra("appWidgetId", widgetId);
        clickIntent.putExtra("action_view_id", id);
        return PendingIntent.getBroadcast(context, (widgetId * 31) + id, clickIntent, 134217728);
    }

    private boolean onViewAction(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int actionViewId) {
        if (appWidgetId == 0) {
            return false;
        }
        RemoteViews rv = new RemoteViews(context.getPackageName(), 2130903299);
        if (actionViewId == 2131624661) {
            rv.showPrevious(2131624663);
        } else if (actionViewId == 2131624662) {
            rv.showNext(2131624663);
        } else if (actionViewId != 2131624651 && actionViewId != 2131624654 && actionViewId != 2131624657 && actionViewId != 2131624659) {
            return false;
        } else {
            changeTab(rv, context, actionViewId, appWidgetId);
        }
        appWidgetManager.updateAppWidget(appWidgetId, rv);
        return true;
    }

    private void changeTab(RemoteViews rv, Context context, int newTabId, int appWidgetId) {
        changeTab(rv, context, newTabId, appWidgetId, false);
    }

    private void changeTab(RemoteViews rv, Context context, int newTabId, int appWidgetId, boolean force) {
        SharedPreferences pref = getSharedPreferences(context, appWidgetId);
        int oldTabId = pref.getInt("current_tab", 2131624651);
        if (newTabId != oldTabId || force) {
            int drawableId;
            EMemoType memoType;
            int emptyTextId;
            pref.edit().putInt("current_tab", newTabId).apply();
            if (oldTabId == 2131624654) {
                rv.setViewVisibility(2131624655, 4);
                drawableId = 2131166130;
            } else if (oldTabId == 2131624657) {
                rv.setViewVisibility(2131624658, 4);
                drawableId = 2131166121;
            } else if (oldTabId == 2131624659) {
                rv.setViewVisibility(2131624660, 4);
                drawableId = 2131166124;
            } else {
                rv.setViewVisibility(2131624652, 4);
                drawableId = 2131166122;
            }
            rv.setImageViewBitmap(oldTabId, DrawableHelper.getBitmap(FontDrawable.inflate(context.getResources(), 2131034329, context.getString(drawableId))));
            if (newTabId == 2131624654) {
                rv.setViewVisibility(2131624655, 0);
                rv.setViewVisibility(2131624662, 8);
                rv.setViewVisibility(2131624661, 8);
                memoType = EMemoType.memoWork;
                drawableId = 2131166130;
                emptyTextId = 2131166021;
            } else if (newTabId == 2131624657) {
                rv.setViewVisibility(2131624658, 0);
                rv.setViewVisibility(2131624662, 0);
                rv.setViewVisibility(2131624661, 0);
                memoType = EMemoType.memoFavorites;
                drawableId = 2131166121;
                emptyTextId = 2131166018;
            } else if (newTabId == 2131624659) {
                rv.setViewVisibility(2131624660, 0);
                rv.setViewVisibility(2131624662, 8);
                rv.setViewVisibility(2131624661, 8);
                memoType = EMemoType.memoParking;
                drawableId = 2131166124;
                emptyTextId = 2131166020;
            } else {
                rv.setViewVisibility(2131624652, 0);
                rv.setViewVisibility(2131624662, 8);
                rv.setViewVisibility(2131624661, 8);
                memoType = EMemoType.memoHome;
                drawableId = 2131166122;
                emptyTextId = 2131166019;
            }
            Drawable drawable = FontDrawable.inflate(context.getResources(), 2131034329, context.getString(drawableId));
            if (drawable instanceof FontDrawable) {
                ((FontDrawable) drawable).setColor(context.getResources().getColor(2131558683));
            }
            rv.setImageViewBitmap(newTabId, DrawableHelper.getBitmap(drawable));
            rv.setTextViewText(16908292, context.getString(emptyTextId));
            Bundle bundle = new Bundle(2);
            bundle.putInt("appWidgetId", appWidgetId);
            bundle.putParcelable("tab", memoType);
            context.getContentResolver().call(WidgetDataProvider.getContentUri(context), "setWidgetTabType", null, bundle);
            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, 2131624663);
        }
    }

    private void setupTabs(RemoteViews rv, Context context, int appWidgetId) {
        Intent navigateIntent = new Intent(context, TrafficWidgetProvider.class);
        navigateIntent.setAction("com.sygic.widget.trafficwidget.NAVI_ACTION");
        navigateIntent.putExtra("appWidgetId", appWidgetId);
        navigateIntent.setData(Uri.parse(navigateIntent.toUri(1)));
        rv.setPendingIntentTemplate(2131624663, PendingIntent.getBroadcast(context, 0, navigateIntent, 134217728));
        Intent adapterIntent = new Intent(context, TrafficWidgetService.class);
        adapterIntent.putExtra("appWidgetId", appWidgetId);
        adapterIntent.setData(Uri.parse(adapterIntent.toUri(1)));
        rv.setRemoteAdapter(2131624663, adapterIntent);
        changeTab(rv, context, getSharedPreferences(context, appWidgetId).getInt("current_tab", 2131624651), appWidgetId, true);
    }

    static SharedPreferences getSharedPreferences(Context context, int appWidgetId) {
        return context.getSharedPreferences(context.getPackageName() + appWidgetId, 0);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), 2130903299);
            rv.setImageViewBitmap(2131624661, DrawableHelper.getBitmap(FontDrawable.inflate(context.getResources(), 2131034323)));
            rv.setImageViewBitmap(2131624662, DrawableHelper.getBitmap(FontDrawable.inflate(context.getResources(), 2131034323, context.getString(2131166123))));
            rv.setOnClickPendingIntent(2131624661, getActionViewIntent(context, appWidgetId, 2131624661));
            rv.setOnClickPendingIntent(2131624662, getActionViewIntent(context, appWidgetId, 2131624662));
            rv.setImageViewBitmap(2131624651, DrawableHelper.getBitmap(FontDrawable.inflate(context.getResources(), 2131034329, context.getString(2131166122))));
            rv.setViewVisibility(2131624652, 4);
            rv.setImageViewBitmap(2131624654, DrawableHelper.getBitmap(FontDrawable.inflate(context.getResources(), 2131034329, context.getString(2131166130))));
            rv.setViewVisibility(2131624655, 4);
            rv.setImageViewBitmap(2131624657, DrawableHelper.getBitmap(FontDrawable.inflate(context.getResources(), 2131034329, context.getString(2131166121))));
            rv.setViewVisibility(2131624658, 4);
            rv.setImageViewBitmap(2131624659, DrawableHelper.getBitmap(FontDrawable.inflate(context.getResources(), 2131034329, context.getString(2131166124))));
            rv.setViewVisibility(2131624660, 4);
            rv.setOnClickPendingIntent(2131624651, getActionViewIntent(context, appWidgetId, 2131624651));
            rv.setOnClickPendingIntent(2131624654, getActionViewIntent(context, appWidgetId, 2131624654));
            rv.setOnClickPendingIntent(2131624657, getActionViewIntent(context, appWidgetId, 2131624657));
            rv.setOnClickPendingIntent(2131624659, getActionViewIntent(context, appWidgetId, 2131624659));
            rv.setEmptyView(2131624663, 16908292);
            setupTabs(rv, context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
