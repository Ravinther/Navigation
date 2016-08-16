package com.google.ads.conversiontracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/* renamed from: com.google.ads.conversiontracking.f */
public class C0545f {
    private static final String f1174a;
    private final C0544a f1175b;
    private final Object f1176c;

    /* renamed from: com.google.ads.conversiontracking.f.a */
    public class C0544a extends SQLiteOpenHelper {
        final /* synthetic */ C0545f f1173a;

        public C0544a(C0545f c0545f, Context context, String str) {
            this.f1173a = c0545f;
            super(context, str, null, 5);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(C0545f.f1174a);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("GoogleConversionReporter", "Database updated from version " + oldVersion + " to version " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS conversiontracking");
            onCreate(db);
        }
    }

    static {
        f1174a = String.format(Locale.US, "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT, %s INTEGER, %s INTEGER, %s TEXT, %s INTEGER, %s INTEGER,%s INTEGER);", new Object[]{"conversiontracking", "conversion_ping_id", "string_url", "preference_key", "is_repeatable", "parameter_is_null", "preference_name", "record_time", "retry_count", "last_retry_time"});
    }

    public C0545f(Context context) {
        this.f1176c = new Object();
        this.f1175b = new C0544a(this, context, "google_conversion_tracking.db");
    }

    public void m1346a(C0539d c0539d) {
        if (c0539d != null) {
            synchronized (this.f1176c) {
                SQLiteDatabase a = m1343a();
                if (a == null) {
                    return;
                }
                a.delete("conversiontracking", String.format(Locale.US, "%s = %d", new Object[]{"conversion_ping_id", Long.valueOf(c0539d.f1161h)}), null);
            }
        }
    }

    public SQLiteDatabase m1343a() {
        try {
            return this.f1175b.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.w("GoogleConversionReporter", "Error opening writable conversion tracking database");
            return null;
        }
    }

    public List<C0539d> m1345a(long j) {
        Cursor query;
        SQLiteException e;
        String str;
        String str2;
        String valueOf;
        Throwable th;
        synchronized (this.f1176c) {
            List<C0539d> linkedList = new LinkedList();
            if (j <= 0) {
                return linkedList;
            }
            SQLiteDatabase a = m1343a();
            if (a == null) {
                return linkedList;
            }
            try {
                query = a.query("conversiontracking", null, null, null, null, null, "last_retry_time ASC", String.valueOf(j));
                try {
                    if (query.moveToFirst()) {
                        do {
                            linkedList.add(m1344a(query));
                        } while (query.moveToNext());
                    }
                    if (query != null) {
                        query.close();
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    try {
                        str = "GoogleConversionReporter";
                        str2 = "Error extracing ping Info: ";
                        valueOf = String.valueOf(e.getMessage());
                        Log.w(str, valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                        if (query != null) {
                            query.close();
                        }
                        return linkedList;
                    } catch (Throwable th2) {
                        th = th2;
                        if (query != null) {
                            query.close();
                        }
                        throw th;
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                query = null;
                str = "GoogleConversionReporter";
                str2 = "Error extracing ping Info: ";
                valueOf = String.valueOf(e.getMessage());
                if (valueOf.length() == 0) {
                }
                Log.w(str, valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                if (query != null) {
                    query.close();
                }
                return linkedList;
            } catch (Throwable th3) {
                th = th3;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
            return linkedList;
        }
    }

    public void m1348b(C0539d c0539d) {
        int i = 1;
        if (c0539d != null) {
            synchronized (this.f1176c) {
                SQLiteDatabase a = m1343a();
                if (a == null) {
                    return;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("string_url", c0539d.f1160g);
                contentValues.put("preference_key", c0539d.f1159f);
                contentValues.put("is_repeatable", Integer.valueOf(c0539d.f1155b ? 1 : 0));
                String str = "parameter_is_null";
                if (!c0539d.f1154a) {
                    i = 0;
                }
                contentValues.put(str, Integer.valueOf(i));
                contentValues.put("preference_name", c0539d.f1158e);
                contentValues.put("record_time", Long.valueOf(c0539d.f1157d));
                contentValues.put("retry_count", Integer.valueOf(0));
                contentValues.put("last_retry_time", Long.valueOf(c0539d.f1157d));
                c0539d.f1161h = a.insert("conversiontracking", null, contentValues);
                m1347b();
                if (((long) m1349c()) > 20000) {
                    m1351d();
                }
            }
        }
    }

    public void m1347b() {
        synchronized (this.f1176c) {
            SQLiteDatabase a = m1343a();
            if (a == null) {
                return;
            }
            a.delete("conversiontracking", String.format(Locale.US, "(%s > %d) or (%s < %d and %s > 0)", new Object[]{"retry_count", Long.valueOf(9000), "record_time", Long.valueOf(C0552g.m1381a() - 43200000), "retry_count"}), null);
        }
    }

    public void m1350c(C0539d c0539d) {
        if (c0539d != null) {
            synchronized (this.f1176c) {
                SQLiteDatabase a = m1343a();
                if (a == null) {
                    return;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("last_retry_time", Long.valueOf(C0552g.m1381a()));
                contentValues.put("retry_count", Integer.valueOf(c0539d.f1156c + 1));
                a.update("conversiontracking", contentValues, String.format(Locale.US, "%s = %d", new Object[]{"conversion_ping_id", Long.valueOf(c0539d.f1161h)}), null);
                m1347b();
            }
        }
    }

    public int m1349c() {
        Cursor cursor = null;
        int i = 0;
        synchronized (this.f1176c) {
            SQLiteDatabase a = m1343a();
            if (a == null) {
            } else {
                try {
                    cursor = a.rawQuery("select count(*) from conversiontracking", null);
                    if (cursor.moveToFirst()) {
                        i = cursor.getInt(0);
                        if (cursor != null) {
                            cursor.close();
                        }
                    } else {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                } catch (SQLiteException e) {
                    String str = "GoogleConversionReporter";
                    String str2 = "Error getting record count";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
        return i;
    }

    public void m1351d() {
        SQLiteException e;
        String str;
        String str2;
        String valueOf;
        Throwable th;
        synchronized (this.f1176c) {
            SQLiteDatabase a = m1343a();
            if (a == null) {
                return;
            }
            Cursor query;
            try {
                query = a.query("conversiontracking", null, null, null, null, null, "record_time ASC", "1");
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            m1346a(m1344a(query));
                        }
                    } catch (SQLiteException e2) {
                        e = e2;
                        try {
                            str = "GoogleConversionReporter";
                            str2 = "Error remove oldest record";
                            valueOf = String.valueOf(e.getMessage());
                            Log.w(str, valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                            if (query != null) {
                                query.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (query != null) {
                                query.close();
                            }
                            throw th;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e3) {
                e = e3;
                query = null;
                str = "GoogleConversionReporter";
                str2 = "Error remove oldest record";
                valueOf = String.valueOf(e.getMessage());
                if (valueOf.length() == 0) {
                }
                Log.w(str, valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th3) {
                th = th3;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
    }

    public C0539d m1344a(Cursor cursor) {
        boolean z = true;
        if (cursor == null) {
            return null;
        }
        boolean z2;
        int i = cursor.getInt(7);
        String string = cursor.getString(1);
        if (i > 0) {
            string = Uri.parse(string).buildUpon().appendQueryParameter("retry", Integer.toString(i)).build().toString();
        }
        long j = cursor.getLong(0);
        String string2 = cursor.getString(2);
        if (cursor.getInt(3) > 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (cursor.getInt(4) <= 0) {
            z = false;
        }
        return new C0539d(j, string, string2, z2, z, cursor.getString(5), cursor.getLong(6), i);
    }
}
