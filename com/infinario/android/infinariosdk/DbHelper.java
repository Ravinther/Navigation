package com.infinario.android.infinariosdk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "commands.db", null, 1);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table commands(id integer primary key autoincrement, command text not null, retries integer not null default 0);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS commands");
        onCreate(db);
    }
}
