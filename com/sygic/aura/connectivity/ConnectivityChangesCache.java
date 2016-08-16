package com.sygic.aura.connectivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.sygic.aura.connectivity.ConnectivityChangesManager.ConnType;
import com.sygic.aura.connectivity.db.ConnectivityChangesDatabase;
import com.sygic.aura.tracker.TrackerUtils;

public class ConnectivityChangesCache {
    private final ConnectivityChangesDatabase mDbHelper;

    protected ConnectivityChangesCache(Context context) {
        this.mDbHelper = new ConnectivityChangesDatabase(context);
    }

    protected void addConnectivityChange(ConnType connType) {
        SQLiteDatabase db = this.mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", Integer.valueOf(connType.ordinal()));
        values.put("timestamp", Long.valueOf(TrackerUtils.getCurrentTime()));
        db.insert("connectivity_changes", null, values);
    }

    protected void addInForegroundChange(boolean inForeground) {
        SQLiteDatabase db = this.mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("app_in_foreground", Integer.valueOf(inForeground ? 1 : 0));
        values.put("timestamp", Long.valueOf(TrackerUtils.getCurrentTime()));
        db.insert("foreground_changes", null, values);
    }

    protected Cursor getConnectivityChangesIn(long startTimestamp, long endTimestamp) {
        return this.mDbHelper.getReadableDatabase().query("connectivity_changes", null, "timestamp >= ? AND timestamp <= ?", new String[]{String.valueOf(startTimestamp), String.valueOf(endTimestamp)}, null, null, "timestamp ASC");
    }

    protected Cursor getConnectivityChangeBefore(long timestamp) {
        return this.mDbHelper.getReadableDatabase().query("connectivity_changes", null, "timestamp < ?", new String[]{String.valueOf(timestamp)}, null, null, "timestamp DESC LIMIT 1");
    }

    protected Cursor getForegroundChangesIn(long startTimestamp, long endTimestamp) {
        return this.mDbHelper.getReadableDatabase().query("foreground_changes", null, "timestamp >= ? AND timestamp <= ?", new String[]{String.valueOf(startTimestamp), String.valueOf(endTimestamp)}, null, null, "timestamp ASC");
    }
}
