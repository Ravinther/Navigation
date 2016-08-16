package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.internal.zzlm;
import com.google.android.gms.internal.zzlo;
import com.sygic.aura.settings.fragments.SettingsFragment;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class zzw implements zzc {
    private static final String zzaPJ;
    private final Context mContext;
    private final Executor zzaPK;
    private zza zzaPL;
    private int zzaPM;
    private zzlm zzpO;

    /* renamed from: com.google.android.gms.tagmanager.zzw.1 */
    class C10271 implements Runnable {
        final /* synthetic */ List zzaPN;
        final /* synthetic */ long zzaPO;
        final /* synthetic */ zzw zzaPP;

        C10271(zzw com_google_android_gms_tagmanager_zzw, List list, long j) {
            this.zzaPP = com_google_android_gms_tagmanager_zzw;
            this.zzaPN = list;
            this.zzaPO = j;
        }

        public void run() {
            this.zzaPP.zzb(this.zzaPN, this.zzaPO);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzw.2 */
    class C10282 implements Runnable {
        final /* synthetic */ zzw zzaPP;
        final /* synthetic */ com.google.android.gms.tagmanager.DataLayer.zzc.zza zzaPQ;

        C10282(zzw com_google_android_gms_tagmanager_zzw, com.google.android.gms.tagmanager.DataLayer.zzc.zza com_google_android_gms_tagmanager_DataLayer_zzc_zza) {
            this.zzaPP = com_google_android_gms_tagmanager_zzw;
            this.zzaPQ = com_google_android_gms_tagmanager_DataLayer_zzc_zza;
        }

        public void run() {
            this.zzaPQ.zzo(this.zzaPP.zzzS());
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzw.3 */
    class C10293 implements Runnable {
        final /* synthetic */ zzw zzaPP;
        final /* synthetic */ String zzaPR;

        C10293(zzw com_google_android_gms_tagmanager_zzw, String str) {
            this.zzaPP = com_google_android_gms_tagmanager_zzw;
            this.zzaPR = str;
        }

        public void run() {
            this.zzaPP.zzeF(this.zzaPR);
        }
    }

    class zza extends SQLiteOpenHelper {
        final /* synthetic */ zzw zzaPP;

        zza(zzw com_google_android_gms_tagmanager_zzw, Context context, String str) {
            this.zzaPP = com_google_android_gms_tagmanager_zzw;
            super(context, str, null, 1);
        }

        private boolean zza(String str, SQLiteDatabase sQLiteDatabase) {
            Cursor cursor;
            Throwable th;
            Cursor cursor2 = null;
            try {
                SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
                Cursor query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
                try {
                    boolean moveToFirst = query.moveToFirst();
                    if (query == null) {
                        return moveToFirst;
                    }
                    query.close();
                    return moveToFirst;
                } catch (SQLiteException e) {
                    cursor = query;
                    try {
                        zzbg.zzaE("Error querying for table " + str);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    } catch (Throwable th2) {
                        cursor2 = cursor;
                        th = th2;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor2 = query;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                cursor = null;
                zzbg.zzaE("Error querying for table " + str);
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            } catch (Throwable th4) {
                th = th4;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        }

        private void zzc(SQLiteDatabase sQLiteDatabase) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (!hashSet.remove(SettingsFragment.ARG_KEY) || !hashSet.remove("value") || !hashSet.remove("ID") || !hashSet.remove("expires")) {
                    throw new SQLiteException("Database column missing");
                } else if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } finally {
                rawQuery.close();
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                this.zzaPP.mContext.getDatabasePath("google_tagmanager.db").delete();
            }
            return sQLiteDatabase == null ? super.getWritableDatabase() : sQLiteDatabase;
        }

        public void onCreate(SQLiteDatabase db) {
            zzal.zzbj(db.getPath());
        }

        public void onOpen(SQLiteDatabase db) {
            if (VERSION.SDK_INT < 15) {
                Cursor rawQuery = db.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                } finally {
                    rawQuery.close();
                }
            }
            if (zza("datalayer", db)) {
                zzc(db);
            } else {
                db.execSQL(zzw.zzaPJ);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    private static class zzb {
        final byte[] zzaPS;
        final String zztP;

        zzb(String str, byte[] bArr) {
            this.zztP = str;
            this.zzaPS = bArr;
        }

        public String toString() {
            return "KeyAndSerialized: key = " + this.zztP + " serialized hash = " + Arrays.hashCode(this.zzaPS);
        }
    }

    static {
        zzaPJ = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", new Object[]{"datalayer", "ID", SettingsFragment.ARG_KEY, "value", "expires"});
    }

    public zzw(Context context) {
        this(context, zzlo.zzpN(), "google_tagmanager.db", 2000, Executors.newSingleThreadExecutor());
    }

    zzw(Context context, zzlm com_google_android_gms_internal_zzlm, String str, int i, Executor executor) {
        this.mContext = context;
        this.zzpO = com_google_android_gms_internal_zzlm;
        this.zzaPM = i;
        this.zzaPK = executor;
        this.zzaPL = new zza(this, this.mContext, str);
    }

    private byte[] zzC(Object obj) {
        ObjectOutputStream objectOutputStream;
        Throwable th;
        byte[] bArr = null;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(obj);
                bArr = byteArrayOutputStream.toByteArray();
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                    }
                }
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayOutputStream.close();
                return bArr;
            } catch (Throwable th2) {
                th = th2;
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e4) {
                        throw th;
                    }
                }
                byteArrayOutputStream.close();
                throw th;
            }
        } catch (IOException e5) {
            objectOutputStream = bArr;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            return bArr;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            objectOutputStream = bArr;
            th = th4;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            throw th;
        }
        return bArr;
    }

    private void zzS(long j) {
        SQLiteDatabase zzeG = zzeG("Error opening database for deleteOlderThan.");
        if (zzeG != null) {
            try {
                zzbg.m1448v("Deleted " + zzeG.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)}) + " expired items");
            } catch (SQLiteException e) {
                zzbg.zzaE("Error deleting old entries.");
            }
        }
    }

    private synchronized void zzb(List<zzb> list, long j) {
        try {
            long currentTimeMillis = this.zzpO.currentTimeMillis();
            zzS(currentTimeMillis);
            zzjd(list.size());
            zzc(list, currentTimeMillis + j);
            zzzV();
        } catch (Throwable th) {
            zzzV();
        }
    }

    private void zzc(List<zzb> list, long j) {
        SQLiteDatabase zzeG = zzeG("Error opening database for writeEntryToDatabase.");
        if (zzeG != null) {
            for (zzb com_google_android_gms_tagmanager_zzw_zzb : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("expires", Long.valueOf(j));
                contentValues.put(SettingsFragment.ARG_KEY, com_google_android_gms_tagmanager_zzw_zzb.zztP);
                contentValues.put("value", com_google_android_gms_tagmanager_zzw_zzb.zzaPS);
                zzeG.insert("datalayer", null, contentValues);
            }
        }
    }

    private void zze(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase zzeG = zzeG("Error opening database for deleteEntries.");
            if (zzeG != null) {
                try {
                    zzeG.delete("datalayer", String.format("%s in (%s)", new Object[]{"ID", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                } catch (SQLiteException e) {
                    zzbg.zzaE("Error deleting entries " + Arrays.toString(strArr));
                }
            }
        }
    }

    private void zzeF(String str) {
        SQLiteDatabase zzeG = zzeG("Error opening database for clearKeysWithPrefix.");
        if (zzeG != null) {
            try {
                zzbg.m1448v("Cleared " + zzeG.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, str + ".%"}) + " items");
            } catch (SQLiteException e) {
                zzbg.zzaE("Error deleting entries with key prefix: " + str + " (" + e + ").");
            } finally {
                zzzV();
            }
        }
    }

    private SQLiteDatabase zzeG(String str) {
        try {
            return this.zzaPL.getWritableDatabase();
        } catch (SQLiteException e) {
            zzbg.zzaE(str);
            return null;
        }
    }

    private void zzjd(int i) {
        int zzzU = (zzzU() - this.zzaPM) + i;
        if (zzzU > 0) {
            List zzje = zzje(zzzU);
            zzbg.zzaD("DataLayer store full, deleting " + zzje.size() + " entries to make room.");
            zze((String[]) zzje.toArray(new String[0]));
        }
    }

    private List<String> zzje(int i) {
        Cursor query;
        SQLiteException e;
        Throwable th;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            zzbg.zzaE("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase zzeG = zzeG("Error opening database for peekEntryIds.");
        if (zzeG == null) {
            return arrayList;
        }
        try {
            query = zzeG.query("datalayer", new String[]{"ID"}, null, null, null, null, String.format("%s ASC", new Object[]{"ID"}), Integer.toString(i));
            try {
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(query.getLong(0)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    zzbg.zzaE("Error in peekEntries fetching entryIds: " + e.getMessage());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
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
            zzbg.zzaE("Error in peekEntries fetching entryIds: " + e.getMessage());
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return arrayList;
    }

    private List<zza> zzp(List<zzb> list) {
        List<zza> arrayList = new ArrayList();
        for (zzb com_google_android_gms_tagmanager_zzw_zzb : list) {
            arrayList.add(new zza(com_google_android_gms_tagmanager_zzw_zzb.zztP, zzq(com_google_android_gms_tagmanager_zzw_zzb.zzaPS)));
        }
        return arrayList;
    }

    private Object zzq(byte[] bArr) {
        Object readObject;
        Throwable th;
        ObjectInputStream objectInputStream = null;
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ObjectInputStream objectInputStream2;
        try {
            objectInputStream2 = new ObjectInputStream(byteArrayInputStream);
            try {
                readObject = objectInputStream2.readObject();
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e) {
                    }
                }
                byteArrayInputStream.close();
            } catch (IOException e2) {
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayInputStream.close();
                return readObject;
            } catch (ClassNotFoundException e4) {
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e5) {
                    }
                }
                byteArrayInputStream.close();
                return readObject;
            } catch (Throwable th2) {
                th = th2;
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e6) {
                        throw th;
                    }
                }
                byteArrayInputStream.close();
                throw th;
            }
        } catch (IOException e7) {
            objectInputStream2 = objectInputStream;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            return readObject;
        } catch (ClassNotFoundException e8) {
            objectInputStream2 = objectInputStream;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            return readObject;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            objectInputStream2 = objectInputStream;
            th = th4;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            throw th;
        }
        return readObject;
    }

    private List<zzb> zzq(List<zza> list) {
        List<zzb> arrayList = new ArrayList();
        for (zza com_google_android_gms_tagmanager_DataLayer_zza : list) {
            arrayList.add(new zzb(com_google_android_gms_tagmanager_DataLayer_zza.zztP, zzC(com_google_android_gms_tagmanager_DataLayer_zza.zzID)));
        }
        return arrayList;
    }

    private List<zza> zzzS() {
        try {
            zzS(this.zzpO.currentTimeMillis());
            List<zza> zzp = zzp(zzzT());
            return zzp;
        } finally {
            zzzV();
        }
    }

    private List<zzb> zzzT() {
        SQLiteDatabase zzeG = zzeG("Error opening database for loadSerialized.");
        List<zzb> arrayList = new ArrayList();
        if (zzeG == null) {
            return arrayList;
        }
        Cursor query = zzeG.query("datalayer", new String[]{SettingsFragment.ARG_KEY, "value"}, null, null, null, null, "ID", null);
        while (query.moveToNext()) {
            try {
                arrayList.add(new zzb(query.getString(0), query.getBlob(1)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    private int zzzU() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase zzeG = zzeG("Error opening database for getNumStoredEntries.");
        if (zzeG != null) {
            try {
                cursor = zzeG.rawQuery("SELECT COUNT(*) from datalayer", null);
                if (cursor.moveToFirst()) {
                    i = (int) cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                zzbg.zzaE("Error getting numStoredEntries");
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return i;
    }

    private void zzzV() {
        try {
            this.zzaPL.close();
        } catch (SQLiteException e) {
        }
    }

    public void zza(com.google.android.gms.tagmanager.DataLayer.zzc.zza com_google_android_gms_tagmanager_DataLayer_zzc_zza) {
        this.zzaPK.execute(new C10282(this, com_google_android_gms_tagmanager_DataLayer_zzc_zza));
    }

    public void zza(List<zza> list, long j) {
        this.zzaPK.execute(new C10271(this, zzq((List) list), j));
    }

    public void zzeE(String str) {
        this.zzaPK.execute(new C10293(this, str));
    }
}
