package com.infinario.android.infinariosdk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.JSONException;

public class DbQueue {
    private String[] allColumns;
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    private Object lockAccess;
    private int openCounter;

    public DbQueue(Context context) {
        this.allColumns = new String[]{"id", "command", "retries"};
        this.dbHelper = new DbHelper(context);
        this.lockAccess = new Object();
        this.openCounter = 0;
    }

    public boolean schedule(Command command) {
        boolean z;
        synchronized (this.lockAccess) {
            openDatabase();
            try {
                ContentValues values = new ContentValues();
                values.put("command", command.toString());
                z = -1 < this.db.insert("commands", null, values);
                closeDatabase();
            } catch (Throwable th) {
                closeDatabase();
            }
        }
        return z;
    }

    public List<Request> pop(Integer limit) {
        Cursor cursor = this.db.query("commands", this.allColumns, null, null, null, null, "id ASC", limit.toString());
        List<Request> requests = new ArrayList();
        while (cursor.moveToNext()) {
            try {
                requests.add(new Request(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            } catch (JSONException e) {
                this.db.delete("commands", "id = " + cursor.getInt(0), null);
            }
        }
        cursor.close();
        return requests;
    }

    public List<Request> pop() {
        List<Request> pop;
        synchronized (this.lockAccess) {
            openDatabase();
            try {
                pop = pop(Integer.valueOf(50));
                closeDatabase();
            } catch (Throwable th) {
                closeDatabase();
            }
        }
        return pop;
    }

    public boolean isEmpty() {
        boolean result = false;
        synchronized (this.lockAccess) {
            openDatabase();
            try {
                Cursor cursor = this.db.rawQuery("SELECT COUNT(*) FROM commands", null);
                if (!cursor.moveToFirst() || cursor.getInt(0) <= 0) {
                    result = true;
                }
                cursor.close();
                closeDatabase();
            } catch (Throwable th) {
                closeDatabase();
            }
        }
        return result;
    }

    public void clear(Set<Integer> delete) {
        synchronized (this.lockAccess) {
            openDatabase();
            try {
                this.db.delete("commands", "id IN (" + TextUtils.join(", ", delete) + ")", null);
                closeDatabase();
            } catch (Throwable th) {
                closeDatabase();
            }
        }
    }

    private void openDatabase() {
        synchronized (this.lockAccess) {
            this.openCounter++;
            if (this.openCounter == 1) {
                this.db = this.dbHelper.getWritableDatabase();
            }
        }
    }

    private void closeDatabase() {
        synchronized (this.lockAccess) {
            this.openCounter--;
            if (this.openCounter == 0) {
                this.db.close();
            }
        }
    }
}
