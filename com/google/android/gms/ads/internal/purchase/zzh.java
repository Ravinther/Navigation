package com.google.android.gms.ads.internal.purchase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzgk;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@zzgk
public class zzh {
    private static final String zzCj;
    private static zzh zzCl;
    private static final Object zzpc;
    private final zza zzCk;

    public class zza extends SQLiteOpenHelper {
        final /* synthetic */ zzh zzCm;

        public zza(zzh com_google_android_gms_ads_internal_purchase_zzh, Context context, String str) {
            this.zzCm = com_google_android_gms_ads_internal_purchase_zzh;
            super(context, str, null, 4);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(zzh.zzCj);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            zzb.zzaD("Database updated from version " + oldVersion + " to version " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS InAppPurchase");
            onCreate(db);
        }
    }

    static {
        zzCj = String.format(Locale.US, "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER)", new Object[]{"InAppPurchase", "purchase_id", "product_id", "developer_payload", "record_time"});
        zzpc = new Object();
    }

    zzh(Context context) {
        this.zzCk = new zza(this, context, "google_inapp_purchase.db");
    }

    public static zzh zzx(Context context) {
        zzh com_google_android_gms_ads_internal_purchase_zzh;
        synchronized (zzpc) {
            if (zzCl == null) {
                zzCl = new zzh(context);
            }
            com_google_android_gms_ads_internal_purchase_zzh = zzCl;
        }
        return com_google_android_gms_ads_internal_purchase_zzh;
    }

    public int getRecordCount() {
        Cursor cursor = null;
        int i = 0;
        synchronized (zzpc) {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null) {
            } else {
                try {
                    cursor = writableDatabase.rawQuery("select count(*) from InAppPurchase", null);
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
                    zzb.zzaE("Error getting record count" + e.getMessage());
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

    public SQLiteDatabase getWritableDatabase() {
        try {
            return this.zzCk.getWritableDatabase();
        } catch (SQLiteException e) {
            zzb.zzaE("Error opening writable conversion tracking database");
            return null;
        }
    }

    public zzf zza(Cursor cursor) {
        return cursor == null ? null : new zzf(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
    }

    public void zza(zzf com_google_android_gms_ads_internal_purchase_zzf) {
        if (com_google_android_gms_ads_internal_purchase_zzf != null) {
            synchronized (zzpc) {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                if (writableDatabase == null) {
                    return;
                }
                writableDatabase.delete("InAppPurchase", String.format(Locale.US, "%s = %d", new Object[]{"purchase_id", Long.valueOf(com_google_android_gms_ads_internal_purchase_zzf.zzCd)}), null);
            }
        }
    }

    public void zzb(zzf com_google_android_gms_ads_internal_purchase_zzf) {
        if (com_google_android_gms_ads_internal_purchase_zzf != null) {
            synchronized (zzpc) {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                if (writableDatabase == null) {
                    return;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("product_id", com_google_android_gms_ads_internal_purchase_zzf.zzCf);
                contentValues.put("developer_payload", com_google_android_gms_ads_internal_purchase_zzf.zzCe);
                contentValues.put("record_time", Long.valueOf(SystemClock.elapsedRealtime()));
                com_google_android_gms_ads_internal_purchase_zzf.zzCd = writableDatabase.insert("InAppPurchase", null, contentValues);
                if (((long) getRecordCount()) > 20000) {
                    zzfi();
                }
            }
        }
    }

    public List<zzf> zzf(long j) {
        SQLiteException e;
        Throwable th;
        synchronized (zzpc) {
            List<zzf> linkedList = new LinkedList();
            if (j <= 0) {
                return linkedList;
            }
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null) {
                return linkedList;
            }
            Cursor query;
            try {
                query = writableDatabase.query("InAppPurchase", null, null, null, null, null, "record_time ASC", String.valueOf(j));
                try {
                    if (query.moveToFirst()) {
                        do {
                            linkedList.add(zza(query));
                        } while (query.moveToNext());
                    }
                    if (query != null) {
                        query.close();
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    try {
                        zzb.zzaE("Error extracing purchase info: " + e.getMessage());
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
                zzb.zzaE("Error extracing purchase info: " + e.getMessage());
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

    public void zzfi() {
        SQLiteException e;
        synchronized (zzpc) {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null) {
                return;
            }
            Cursor query;
            try {
                query = writableDatabase.query("InAppPurchase", null, null, null, null, null, "record_time ASC", "1");
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            zza(zza(query));
                        }
                    } catch (SQLiteException e2) {
                        e = e2;
                        try {
                            zzb.zzaE("Error remove oldest record" + e.getMessage());
                            if (query != null) {
                                query.close();
                            }
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            if (query != null) {
                                query.close();
                            }
                            throw th2;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e3) {
                e = e3;
                query = null;
                zzb.zzaE("Error remove oldest record" + e.getMessage());
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th3) {
                th2 = th3;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th2;
            }
        }
    }
}
