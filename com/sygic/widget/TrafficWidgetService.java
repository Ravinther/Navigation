package com.sygic.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.base.C1799R;
import com.sygic.widget.helpers.DrawableHelper;
import com.sygic.widget.helpers.FormatHelper;
import com.sygic.widget.tomtom.TomTomTrafficService;

public class TrafficWidgetService extends RemoteViewsService {

    class StackRemoteViewsFactory implements RemoteViewsFactory {
        private final int mAppWidgetId;
        private Cursor mCursor;

        public StackRemoteViewsFactory(Intent intent) {
            this.mAppWidgetId = intent.getIntExtra("appWidgetId", 0);
        }

        public void onCreate() {
        }

        public void onDestroy() {
            if (this.mCursor != null) {
                this.mCursor.close();
            }
        }

        public int getCount() {
            return this.mCursor.getCount();
        }

        public RemoteViews getViewAt(int position) {
            String iconText;
            boolean isPedestrian;
            String strPlace = "Error";
            int time = -1;
            int traffic_time = -1;
            EMemoType placeType = EMemoType.memoUnknown;
            float placeLat = TrafficWidgetProvider.LOCATION_INVALID;
            float placeLon = TrafficWidgetProvider.LOCATION_INVALID;
            if (this.mCursor.moveToPosition(position)) {
                int placeColIndex = this.mCursor.getColumnIndex("fav");
                int timeColIndex = this.mCursor.getColumnIndex("time");
                int trafficColIndex = this.mCursor.getColumnIndex("traffic");
                int placeLatColIndex = this.mCursor.getColumnIndex("lat");
                int placeLonColIndex = this.mCursor.getColumnIndex("lon");
                int placeTypeColIndex = this.mCursor.getColumnIndex("memo_type");
                strPlace = this.mCursor.getString(placeColIndex);
                time = this.mCursor.getInt(timeColIndex);
                traffic_time = this.mCursor.getInt(trafficColIndex);
                placeLat = this.mCursor.getFloat(placeLatColIndex);
                placeLon = this.mCursor.getFloat(placeLonColIndex);
                placeType = EMemoType.createFromInt(this.mCursor.getInt(placeTypeColIndex));
                if (TrafficWidgetProvider.TRAFFIC_MODE) {
                    TrafficWidgetService.computeRouteSummary(TrafficWidgetService.this.getBaseContext(), placeLat, placeLon, placeType);
                }
            }
            int layoutID = TrafficWidgetProvider.TRAFFIC_MODE ? 2130903297 : 2130903300;
            if (EMemoType.memoParking.equals(placeType)) {
                iconText = TrafficWidgetService.this.getString(2131166049);
                isPedestrian = true;
            } else {
                iconText = TrafficWidgetService.this.getString(2131166045);
                isPedestrian = false;
            }
            RemoteViews remoteViews = new RemoteViews(TrafficWidgetService.this.getPackageName(), layoutID);
            remoteViews.setTextViewText(2131624649, strPlace);
            remoteViews.setImageViewBitmap(C1799R.id.icon, DrawableHelper.getBitmap(FontDrawable.inflate(TrafficWidgetService.this.getResources(), 2131034318, iconText)));
            if (TrafficWidgetProvider.TRAFFIC_MODE) {
                remoteViews.setTextViewText(C1799R.id.time_remaining, FormatHelper.formatTime((long) time, false));
                remoteViews.setTextViewText(2131624648, FormatHelper.formatTime((long) traffic_time, false));
                remoteViews.setTextColor(2131624648, TrafficWidgetService.this.getResources().getColor(FormatHelper.getTrafficColor(traffic_time, time)));
                remoteViews.setViewVisibility(2131624648, isPedestrian ? 8 : 0);
            }
            Intent fillInIntent = new Intent();
            Bundle extras = new Bundle();
            extras.putFloat(TrafficWidgetProvider.EXTRA_PLACE_LAT, placeLat);
            extras.putFloat(TrafficWidgetProvider.EXTRA_PLACE_LON, placeLon);
            extras.putInt(TrafficWidgetProvider.EXTRA_PLACE_TYPE, placeType.getValue());
            fillInIntent.putExtras(extras);
            remoteViews.setOnClickFillInIntent(2131624649, fillInIntent);
            remoteViews.setOnClickFillInIntent(C1799R.id.icon, fillInIntent);
            remoteViews.setOnClickFillInIntent(2131624647, fillInIntent);
            return remoteViews;
        }

        public RemoteViews getLoadingView() {
            return new RemoteViews(TrafficWidgetService.this.getPackageName(), 2130903298);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public boolean hasStableIds() {
            return true;
        }

        public void onDataSetChanged() {
            if (this.mCursor != null) {
                this.mCursor.close();
            }
            ContentResolver contentResolver = TrafficWidgetService.this.getContentResolver();
            Bundle bundle = new Bundle(1);
            bundle.putInt("appWidgetId", this.mAppWidgetId);
            EMemoType memoType = (EMemoType) contentResolver.call(WidgetDataProvider.getContentUri(TrafficWidgetService.this), "getWidgetTabType", null, bundle).getParcelable("tab");
            this.mCursor = contentResolver.query(WidgetDataProvider.getContentUri(TrafficWidgetService.this), null, "memo_type=?", new String[]{String.valueOf(memoType.getValue())}, null);
        }
    }

    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(intent);
    }

    private static void computeRouteSummary(Context context, float placeLat, float placeLon, EMemoType placeType) {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            context.startService(new Intent(context, TomTomTrafficService.class).setAction("com.sygic.aura.trafficService.ACTION_GET_TRAFFIC").putExtra("latitude", placeLat).putExtra("longitude", placeLon).putExtra("place_type", placeType));
        }
    }
}
