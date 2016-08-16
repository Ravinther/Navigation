package com.sygic.aura.connectivity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectivityChangesDatabase extends SQLiteOpenHelper {
    public ConnectivityChangesDatabase(Context context) {
        super(context, "sgctrckr.db", null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS connectivity_changes (_id INTEGER PRIMARY KEY AUTOINCREMENT, type INTEGER DEFAULT 0, timestamp INTEGER DEFAULT 0);");
        db.execSQL("CREATE TABLE IF NOT EXISTS foreground_changes (_id INTEGER PRIMARY KEY AUTOINCREMENT, app_in_foreground INTEGER DEFAULT 0, timestamp INTEGER DEFAULT 0);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            db.execSQL("CREATE TABLE IF NOT EXISTS foreground_changes (_id INTEGER PRIMARY KEY AUTOINCREMENT, app_in_foreground INTEGER DEFAULT 0, timestamp INTEGER DEFAULT 0);");
        }
    }
}
