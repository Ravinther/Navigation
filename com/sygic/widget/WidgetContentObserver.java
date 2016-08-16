package com.sygic.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

public class WidgetContentObserver extends ContentObserver {
    private final AppWidgetManager mAppWidgetManager;
    private final ComponentName mComponentName;
    private final Context mContext;

    public WidgetContentObserver(Context context, Handler handler) {
        super(handler);
        this.mContext = context;
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
        this.mComponentName = new ComponentName(context, TrafficWidgetProvider.class);
    }

    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    public void onChange(boolean selfChange, Uri uri) {
        this.mContext.getContentResolver().call(WidgetDataProvider.getContentUri(this.mContext), "updatePlacesData", null, null);
        this.mAppWidgetManager.notifyAppWidgetViewDataChanged(this.mAppWidgetManager.getAppWidgetIds(this.mComponentName), 2131624663);
    }
}
