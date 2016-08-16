package com.google.android.gms.analytics.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

class zzj extends zzd implements Closeable {
    private static final String zzMg;
    private static final String zzMh;
    private final zza zzMi;
    private final zzaj zzMj;
    private final zzaj zzMk;

    class zza extends SQLiteOpenHelper {
        final /* synthetic */ zzj zzMl;

        zza(zzj com_google_android_gms_analytics_internal_zzj, Context context, String str) {
            this.zzMl = com_google_android_gms_analytics_internal_zzj;
            super(context, str, null, 1);
        }

        private void zza(SQLiteDatabase sQLiteDatabase) {
            int i = 1;
            Set zzb = zzb(sQLiteDatabase, "hits2");
            String[] strArr = new String[]{"hit_id", "hit_string", "hit_time", "hit_url"};
            int length = strArr.length;
            int i2 = 0;
            while (i2 < length) {
                String str = strArr[i2];
                if (zzb.remove(str)) {
                    i2++;
                } else {
                    throw new SQLiteException("Database hits2 is missing required column: " + str);
                }
            }
            if (zzb.remove("hit_app_id")) {
                i = 0;
            }
            if (!zzb.isEmpty()) {
                throw new SQLiteException("Database hits2 has extra columns");
            } else if (i != 0) {
                sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id INTEGER");
            }
        }

        private boolean zza(SQLiteDatabase sQLiteDatabase, String str) {
            Cursor query;
            Object e;
            Throwable th;
            Cursor cursor = null;
            try {
                SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
                query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
                try {
                    boolean moveToFirst = query.moveToFirst();
                    if (query == null) {
                        return moveToFirst;
                    }
                    query.close();
                    return moveToFirst;
                } catch (SQLiteException e2) {
                    e = e2;
                    try {
                        this.zzMl.zzc("Error querying for table", str, e);
                        if (query != null) {
                            query.close();
                        }
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = query;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                query = null;
                this.zzMl.zzc("Error querying for table", str, e);
                if (query != null) {
                    query.close();
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }

        private Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
            Set<String> hashSet = new HashSet();
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM " + str + " LIMIT 0", null);
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                return hashSet;
            } finally {
                rawQuery.close();
            }
        }

        private void zzb(SQLiteDatabase sQLiteDatabase) {
            int i = 0;
            Set zzb = zzb(sQLiteDatabase, "properties");
            String[] strArr = new String[]{"app_uid", "cid", "tid", "params", "adid", "hits_count"};
            int length = strArr.length;
            while (i < length) {
                String str = strArr[i];
                if (zzb.remove(str)) {
                    i++;
                } else {
                    throw new SQLiteException("Database properties is missing required column: " + str);
                }
            }
            if (!zzb.isEmpty()) {
                throw new SQLiteException("Database properties table has extra columns");
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            if (this.zzMl.zzMk.zzv(3600000)) {
                SQLiteDatabase writableDatabase;
                try {
                    writableDatabase = super.getWritableDatabase();
                } catch (SQLiteException e) {
                    this.zzMl.zzMk.start();
                    this.zzMl.zzbc("Opening the database failed, dropping the table and recreating it");
                    this.zzMl.getContext().getDatabasePath(this.zzMl.zziJ()).delete();
                    try {
                        writableDatabase = super.getWritableDatabase();
                        this.zzMl.zzMk.clear();
                    } catch (SQLiteException e2) {
                        this.zzMl.zze("Failed to open freshly created database", e2);
                        throw e2;
                    }
                }
                return writableDatabase;
            }
            throw new SQLiteException("Database open failed");
        }

        public void onCreate(SQLiteDatabase database) {
            zzx.zzbj(database.getPath());
        }

        public void onOpen(SQLiteDatabase database) {
            if (VERSION.SDK_INT < 15) {
                Cursor rawQuery = database.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                } finally {
                    rawQuery.close();
                }
            }
            if (zza(database, "hits2")) {
                zza(database);
            } else {
                database.execSQL(zzj.zzMg);
            }
            if (zza(database, "properties")) {
                zzb(database);
            } else {
                database.execSQL("CREATE TABLE IF NOT EXISTS properties ( app_uid INTEGER NOT NULL, cid TEXT NOT NULL, tid TEXT NOT NULL, params TEXT NOT NULL, adid INTEGER NOT NULL, hits_count INTEGER NOT NULL, PRIMARY KEY (app_uid, cid, tid)) ;");
            }
        }

        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        }
    }

    static {
        zzMg = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{"hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id"});
        zzMh = String.format("SELECT MAX(%s) FROM %s WHERE 1;", new Object[]{"hit_time", "hits2"});
    }

    zzj(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzMj = new zzaj(zzid());
        this.zzMk = new zzaj(zzid());
        this.zzMi = new zza(this, com_google_android_gms_analytics_internal_zzf.getContext(), zziJ());
    }

    private long zza(String str, String[] strArr, long j) {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery(str, strArr);
            if (cursor.moveToFirst()) {
                j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
            } else if (cursor != null) {
                cursor.close();
            }
            return j;
        } catch (SQLiteException e) {
            zzd("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private long zzb(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery(str, strArr);
            if (cursor.moveToFirst()) {
                long j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            }
            throw new SQLiteException("Database returned empty set");
        } catch (SQLiteException e) {
            zzd("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private String zzd(zzab com_google_android_gms_analytics_internal_zzab) {
        return com_google_android_gms_analytics_internal_zzab.zzkm() ? zzif().zzjy() : zzif().zzjz();
    }

    private static String zze(zzab com_google_android_gms_analytics_internal_zzab) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzab);
        Builder builder = new Builder();
        for (Entry entry : com_google_android_gms_analytics_internal_zzab.zzn().entrySet()) {
            String str = (String) entry.getKey();
            if (!("ht".equals(str) || "qt".equals(str) || "AppUID".equals(str))) {
                builder.appendQueryParameter(str, (String) entry.getValue());
            }
        }
        String encodedQuery = builder.build().getEncodedQuery();
        return encodedQuery == null ? "" : encodedQuery;
    }

    private void zziI() {
        int zzjI = zzif().zzjI();
        long zziz = zziz();
        if (zziz > ((long) (zzjI - 1))) {
            List zzo = zzo((zziz - ((long) zzjI)) + 1);
            zzd("Store full, deleting hits to make room, count", Integer.valueOf(zzo.size()));
            zzd(zzo);
        }
    }

    private String zziJ() {
        return !zzif().zzjk() ? zzif().zzjK() : zzif().zzjl() ? zzif().zzjK() : zzif().zzjL();
    }

    private static String zzz(Map<String, String> map) {
        zzx.zzv(map);
        Builder builder = new Builder();
        for (Entry entry : map.entrySet()) {
            builder.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        String encodedQuery = builder.build().getEncodedQuery();
        return encodedQuery == null ? "" : encodedQuery;
    }

    public void beginTransaction() {
        zzio();
        getWritableDatabase().beginTransaction();
    }

    public void close() {
        try {
            this.zzMi.close();
        } catch (SQLiteException e) {
            zze("Sql error closing database", e);
        } catch (IllegalStateException e2) {
            zze("Error closing database", e2);
        }
    }

    public void endTransaction() {
        zzio();
        getWritableDatabase().endTransaction();
    }

    SQLiteDatabase getWritableDatabase() {
        try {
            return this.zzMi.getWritableDatabase();
        } catch (SQLiteException e) {
            zzd("Error opening database", e);
            throw e;
        }
    }

    boolean isEmpty() {
        return zziz() == 0;
    }

    public void setTransactionSuccessful() {
        zzio();
        getWritableDatabase().setTransactionSuccessful();
    }

    public long zza(long j, String str, String str2) {
        zzx.zzcs(str);
        zzx.zzcs(str2);
        zzio();
        zzic();
        return zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(j), str, str2}, 0);
    }

    public void zza(long j, String str) {
        zzx.zzcs(str);
        zzio();
        zzic();
        int delete = getWritableDatabase().delete("properties", "app_uid=? AND cid<>?", new String[]{String.valueOf(j), str});
        if (delete > 0) {
            zza("Deleted property records", Integer.valueOf(delete));
        }
    }

    public void zzb(zzh com_google_android_gms_analytics_internal_zzh) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzh);
        zzio();
        zzic();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String zzz = zzz(com_google_android_gms_analytics_internal_zzh.zzn());
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_uid", Long.valueOf(com_google_android_gms_analytics_internal_zzh.zziw()));
        contentValues.put("cid", com_google_android_gms_analytics_internal_zzh.getClientId());
        contentValues.put("tid", com_google_android_gms_analytics_internal_zzh.zzix());
        contentValues.put("adid", Integer.valueOf(com_google_android_gms_analytics_internal_zzh.zziy() ? 1 : 0));
        contentValues.put("hits_count", Long.valueOf(com_google_android_gms_analytics_internal_zzh.zziz()));
        contentValues.put("params", zzz);
        try {
            if (writableDatabase.insertWithOnConflict("properties", null, contentValues, 5) == -1) {
                zzbc("Failed to insert/update a property (got -1)");
            }
        } catch (SQLiteException e) {
            zze("Error storing a property", e);
        }
    }

    Map<String, String> zzbd(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith("?")) {
                str = "?" + str;
            }
            List<NameValuePair> parse = URLEncodedUtils.parse(new URI(str), "UTF-8");
            Map<String, String> hashMap = new HashMap(parse.size());
            for (NameValuePair nameValuePair : parse) {
                hashMap.put(nameValuePair.getName(), nameValuePair.getValue());
            }
            return hashMap;
        } catch (URISyntaxException e) {
            zze("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    Map<String, String> zzbe(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            List<NameValuePair> parse = URLEncodedUtils.parse(new URI("?" + str), "UTF-8");
            Map<String, String> hashMap = new HashMap(parse.size());
            for (NameValuePair nameValuePair : parse) {
                hashMap.put(nameValuePair.getName(), nameValuePair.getValue());
            }
            return hashMap;
        } catch (URISyntaxException e) {
            zze("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    public void zzc(zzab com_google_android_gms_analytics_internal_zzab) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzab);
        zzic();
        zzio();
        String zze = zze(com_google_android_gms_analytics_internal_zzab);
        if (zze.length() > 8192) {
            zzie().zza(com_google_android_gms_analytics_internal_zzab, "Hit length exceeds the maximum allowed size");
            return;
        }
        zziI();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", zze);
        contentValues.put("hit_time", Long.valueOf(com_google_android_gms_analytics_internal_zzab.zzkk()));
        contentValues.put("hit_app_id", Integer.valueOf(com_google_android_gms_analytics_internal_zzab.zzki()));
        contentValues.put("hit_url", zzd(com_google_android_gms_analytics_internal_zzab));
        try {
            long insert = writableDatabase.insert("hits2", null, contentValues);
            if (insert == -1) {
                zzbc("Failed to insert a hit (got -1)");
            } else {
                zzb("Hit saved to database. db-id, hit", Long.valueOf(insert), com_google_android_gms_analytics_internal_zzab);
            }
        } catch (SQLiteException e) {
            zze("Error storing a hit", e);
        }
    }

    public void zzd(List<Long> list) {
        zzx.zzv(list);
        zzic();
        zzio();
        if (!list.isEmpty()) {
            int i;
            StringBuilder stringBuilder = new StringBuilder("hit_id");
            stringBuilder.append(" in (");
            for (i = 0; i < list.size(); i++) {
                Long l = (Long) list.get(i);
                if (l == null || l.longValue() == 0) {
                    throw new SQLiteException("Invalid hit id");
                }
                if (i > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(l);
            }
            stringBuilder.append(")");
            String stringBuilder2 = stringBuilder.toString();
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                zza("Deleting dispatched hits. count", Integer.valueOf(list.size()));
                i = writableDatabase.delete("hits2", stringBuilder2, null);
                if (i != list.size()) {
                    zzb("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(i), stringBuilder2);
                }
            } catch (SQLiteException e) {
                zze("Error deleting hits", e);
                throw e;
            }
        }
    }

    protected void zzhB() {
    }

    public int zziG() {
        zzic();
        zzio();
        if (!this.zzMj.zzv(86400000)) {
            return 0;
        }
        this.zzMj.start();
        zzaY("Deleting stale hits (if any)");
        int delete = getWritableDatabase().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzid().currentTimeMillis() - 2592000000L)});
        zza("Deleted stale hits, count", Integer.valueOf(delete));
        return delete;
    }

    public long zziH() {
        zzic();
        zzio();
        return zza(zzMh, null, 0);
    }

    public long zziz() {
        zzic();
        zzio();
        return zzb("SELECT COUNT(*) FROM hits2", null);
    }

    public List<Long> zzo(long j) {
        Cursor query;
        Object e;
        Throwable th;
        Cursor cursor = null;
        zzic();
        zzio();
        if (j <= 0) {
            return Collections.emptyList();
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        List<Long> arrayList = new ArrayList();
        try {
            query = writableDatabase.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", new Object[]{"hit_id"}), Long.toString(j));
            try {
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(Long.valueOf(query.getLong(0)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzd("Error selecting hit ids", e);
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            zzd("Error selecting hit ids", e);
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return arrayList;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.gms.analytics.internal.zzab> zzp(long r14) {
        /*
        r13 = this;
        r0 = 1;
        r1 = 0;
        r9 = 0;
        r2 = 0;
        r2 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r2 < 0) goto L_0x0097;
    L_0x0009:
        com.google.android.gms.common.internal.zzx.zzZ(r0);
        r13.zzic();
        r13.zzio();
        r0 = r13.getWritableDatabase();
        r1 = "hits2";
        r2 = 5;
        r2 = new java.lang.String[r2];	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r3 = 0;
        r4 = "hit_id";
        r2[r3] = r4;	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r3 = 1;
        r4 = "hit_time";
        r2[r3] = r4;	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r3 = 2;
        r4 = "hit_string";
        r2[r3] = r4;	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r3 = 3;
        r4 = "hit_url";
        r2[r3] = r4;	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r3 = 4;
        r4 = "hit_app_id";
        r2[r3] = r4;	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = "%s ASC";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r10 = 0;
        r11 = "hit_id";
        r8[r10] = r11;	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r7 = java.lang.String.format(r7, r8);	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r8 = java.lang.Long.toString(r14);	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r9 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ SQLiteException -> 0x009a, all -> 0x00ab }
        r10 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r10.<init>();	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r0 = r9.moveToFirst();	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        if (r0 == 0) goto L_0x0091;
    L_0x0061:
        r0 = 0;
        r6 = r9.getLong(r0);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r0 = 1;
        r3 = r9.getLong(r0);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r0 = 2;
        r0 = r9.getString(r0);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r1 = 3;
        r1 = r9.getString(r1);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r2 = 4;
        r8 = r9.getInt(r2);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r2 = r13.zzbd(r0);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r5 = com.google.android.gms.analytics.internal.zzam.zzbs(r1);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r0 = new com.google.android.gms.analytics.internal.zzab;	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r1 = r13;
        r0.<init>(r1, r2, r3, r5, r6, r8);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r10.add(r0);	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        r0 = r9.moveToNext();	 Catch:{ SQLiteException -> 0x00ad, all -> 0x00ab }
        if (r0 != 0) goto L_0x0061;
    L_0x0091:
        if (r9 == 0) goto L_0x0096;
    L_0x0093:
        r9.close();
    L_0x0096:
        return r10;
    L_0x0097:
        r0 = r1;
        goto L_0x0009;
    L_0x009a:
        r0 = move-exception;
        r1 = r9;
    L_0x009c:
        r2 = "Error loading hits from the database";
        r13.zze(r2, r0);	 Catch:{ all -> 0x00a3 }
        throw r0;	 Catch:{ all -> 0x00a3 }
    L_0x00a3:
        r0 = move-exception;
        r9 = r1;
    L_0x00a5:
        if (r9 == 0) goto L_0x00aa;
    L_0x00a7:
        r9.close();
    L_0x00aa:
        throw r0;
    L_0x00ab:
        r0 = move-exception;
        goto L_0x00a5;
    L_0x00ad:
        r0 = move-exception;
        r1 = r9;
        goto L_0x009c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.zzp(long):java.util.List<com.google.android.gms.analytics.internal.zzab>");
    }

    public void zzq(long j) {
        zzic();
        zzio();
        List arrayList = new ArrayList(1);
        arrayList.add(Long.valueOf(j));
        zza("Deleting hit, id", Long.valueOf(j));
        zzd(arrayList);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.android.gms.analytics.internal.zzh> zzr(long r14) {
        /*
        r13 = this;
        r13.zzio();
        r13.zzic();
        r0 = r13.getWritableDatabase();
        r9 = 0;
        r1 = 5;
        r2 = new java.lang.String[r1];	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r1 = 0;
        r3 = "cid";
        r2[r1] = r3;	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r1 = 1;
        r3 = "tid";
        r2[r1] = r3;	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r1 = 2;
        r3 = "adid";
        r2[r1] = r3;	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r1 = 3;
        r3 = "hits_count";
        r2[r1] = r3;	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r1 = 4;
        r3 = "params";
        r2[r1] = r3;	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r1 = r13.zzif();	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r10 = r1.zzjJ();	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r8 = java.lang.String.valueOf(r10);	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r3 = "app_uid=?";
        r1 = 1;
        r4 = new java.lang.String[r1];	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r1 = 0;
        r5 = java.lang.String.valueOf(r14);	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r4[r1] = r5;	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r1 = "properties";
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r9 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ SQLiteException -> 0x00c4, all -> 0x00c2 }
        r11 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r11.<init>();	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r0 = r9.moveToFirst();	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        if (r0 == 0) goto L_0x0093;
    L_0x005a:
        r0 = 0;
        r3 = r9.getString(r0);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r0 = 1;
        r4 = r9.getString(r0);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r0 = 2;
        r0 = r9.getInt(r0);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        if (r0 == 0) goto L_0x00a5;
    L_0x006b:
        r5 = 1;
    L_0x006c:
        r0 = 3;
        r0 = r9.getInt(r0);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r6 = (long) r0;	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r0 = 4;
        r0 = r9.getString(r0);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r8 = r13.zzbe(r0);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r0 = android.text.TextUtils.isEmpty(r3);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        if (r0 != 0) goto L_0x0087;
    L_0x0081:
        r0 = android.text.TextUtils.isEmpty(r4);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        if (r0 == 0) goto L_0x00a7;
    L_0x0087:
        r0 = "Read property with empty client id or tracker id";
        r13.zzc(r0, r3, r4);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
    L_0x008d:
        r0 = r9.moveToNext();	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        if (r0 != 0) goto L_0x005a;
    L_0x0093:
        r0 = r11.size();	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        if (r0 < r10) goto L_0x009f;
    L_0x0099:
        r0 = "Sending hits to too many properties. Campaign report might be incorrect";
        r13.zzbb(r0);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
    L_0x009f:
        if (r9 == 0) goto L_0x00a4;
    L_0x00a1:
        r9.close();
    L_0x00a4:
        return r11;
    L_0x00a5:
        r5 = 0;
        goto L_0x006c;
    L_0x00a7:
        r0 = new com.google.android.gms.analytics.internal.zzh;	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r1 = r14;
        r0.<init>(r1, r3, r4, r5, r6, r8);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        r11.add(r0);	 Catch:{ SQLiteException -> 0x00b1, all -> 0x00c2 }
        goto L_0x008d;
    L_0x00b1:
        r0 = move-exception;
        r1 = r9;
    L_0x00b3:
        r2 = "Error loading hits from the database";
        r13.zze(r2, r0);	 Catch:{ all -> 0x00ba }
        throw r0;	 Catch:{ all -> 0x00ba }
    L_0x00ba:
        r0 = move-exception;
        r9 = r1;
    L_0x00bc:
        if (r9 == 0) goto L_0x00c1;
    L_0x00be:
        r9.close();
    L_0x00c1:
        throw r0;
    L_0x00c2:
        r0 = move-exception;
        goto L_0x00bc;
    L_0x00c4:
        r0 = move-exception;
        r1 = r9;
        goto L_0x00b3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzj.zzr(long):java.util.List<com.google.android.gms.analytics.internal.zzh>");
    }
}
