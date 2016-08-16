package com.google.android.vending.expansion.downloader.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

public class DownloadsDB {
    private static final String[] DC_PROJECTION;
    public static final String LOG_TAG;
    private static DownloadsDB mDownloadsDB;
    int mFlags;
    SQLiteStatement mGetDownloadByIndex;
    final SQLiteOpenHelper mHelper;
    long mMetadataRowID;
    int mStatus;
    SQLiteStatement mUpdateCurrentBytes;
    int mVersionCode;

    public static class DownloadColumns implements BaseColumns {
        public static final String[][] SCHEMA;

        static {
            String[][] strArr = new String[13][];
            strArr[0] = new String[]{"_id", "INTEGER PRIMARY KEY"};
            strArr[1] = new String[]{"FILEIDX", "INTEGER UNIQUE"};
            strArr[2] = new String[]{"URI", "TEXT"};
            strArr[3] = new String[]{"FN", "TEXT UNIQUE"};
            strArr[4] = new String[]{"ETAG", "TEXT"};
            strArr[5] = new String[]{"TOTALBYTES", "INTEGER"};
            strArr[6] = new String[]{"CURRENTBYTES", "INTEGER"};
            strArr[7] = new String[]{"LASTMOD", "INTEGER"};
            strArr[8] = new String[]{"STATUS", "INTEGER"};
            strArr[9] = new String[]{"CONTROL", "INTEGER"};
            strArr[10] = new String[]{"FAILCOUNT", "INTEGER"};
            strArr[11] = new String[]{"RETRYAFTER", "INTEGER"};
            strArr[12] = new String[]{"REDIRECTCOUNT", "INTEGER"};
            SCHEMA = strArr;
        }
    }

    protected static class DownloadsContentDBHelper extends SQLiteOpenHelper {
        private static final String[][][] sSchemas;
        private static final String[] sTables;

        DownloadsContentDBHelper(Context paramContext) {
            super(paramContext, "DownloadsDB", null, 7);
        }

        private String createTableQueryFromArray(String paramString, String[][] paramArrayOfString) {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("CREATE TABLE ");
            localStringBuilder.append(paramString);
            localStringBuilder.append(" (");
            for (String[] arrayOfString : paramArrayOfString) {
                localStringBuilder.append(' ');
                localStringBuilder.append(arrayOfString[0]);
                localStringBuilder.append(' ');
                localStringBuilder.append(arrayOfString[1]);
                localStringBuilder.append(',');
            }
            localStringBuilder.setLength(localStringBuilder.length() - 1);
            localStringBuilder.append(");");
            return localStringBuilder.toString();
        }

        static {
            sSchemas = new String[][][]{DownloadColumns.SCHEMA, MetadataColumns.SCHEMA};
            sTables = new String[]{"DownloadColumns", "MetadataColumns"};
        }

        private void dropTables(SQLiteDatabase paramSQLiteDatabase) {
            for (String table : sTables) {
                try {
                    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table);
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
            }
        }

        public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
            int numSchemas = sSchemas.length;
            int i = 0;
            while (i < numSchemas) {
                try {
                    paramSQLiteDatabase.execSQL(createTableQueryFromArray(sTables[i], sSchemas[i]));
                    i++;
                } catch (Exception localException) {
                    while (true) {
                        localException.printStackTrace();
                    }
                }
            }
        }

        public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
            Log.w(DownloadsContentDBHelper.class.getName(), "Upgrading database from version " + paramInt1 + " to " + paramInt2 + ", which will destroy all old data");
            dropTables(paramSQLiteDatabase);
            onCreate(paramSQLiteDatabase);
        }
    }

    public static class MetadataColumns implements BaseColumns {
        public static final String[][] SCHEMA;

        static {
            r0 = new String[4][];
            r0[0] = new String[]{"_id", "INTEGER PRIMARY KEY"};
            r0[1] = new String[]{"APKVERSION", "INTEGER"};
            r0[2] = new String[]{"DOWNLOADSTATUS", "INTEGER"};
            r0[3] = new String[]{"DOWNLOADFLAGS", "INTEGER"};
            SCHEMA = r0;
        }
    }

    static {
        LOG_TAG = DownloadsDB.class.getName();
        DC_PROJECTION = new String[]{"FN", "URI", "ETAG", "TOTALBYTES", "CURRENTBYTES", "LASTMOD", "STATUS", "CONTROL", "FAILCOUNT", "RETRYAFTER", "REDIRECTCOUNT", "FILEIDX"};
    }

    public static synchronized DownloadsDB getDB(Context paramContext) {
        DownloadsDB downloadsDB;
        synchronized (DownloadsDB.class) {
            if (mDownloadsDB == null) {
                downloadsDB = new DownloadsDB(paramContext);
            } else {
                downloadsDB = mDownloadsDB;
            }
        }
        return downloadsDB;
    }

    private SQLiteStatement getDownloadByIndexStatement() {
        if (this.mGetDownloadByIndex == null) {
            this.mGetDownloadByIndex = this.mHelper.getReadableDatabase().compileStatement("SELECT _id FROM DownloadColumns WHERE FILEIDX = ?");
        }
        return this.mGetDownloadByIndex;
    }

    private SQLiteStatement getUpdateCurrentBytesStatement() {
        if (this.mUpdateCurrentBytes == null) {
            this.mUpdateCurrentBytes = this.mHelper.getReadableDatabase().compileStatement("UPDATE DownloadColumns SET CURRENTBYTES = ? WHERE FILEIDX = ?");
        }
        return this.mUpdateCurrentBytes;
    }

    private DownloadsDB(Context paramContext) {
        this.mMetadataRowID = -1;
        this.mVersionCode = -1;
        this.mStatus = -1;
        this.mHelper = new DownloadsContentDBHelper(paramContext);
        Cursor cur = this.mHelper.getReadableDatabase().rawQuery("SELECT APKVERSION,_id,DOWNLOADSTATUS,DOWNLOADFLAGS FROM MetadataColumns LIMIT 1", null);
        if (cur != null && cur.moveToFirst()) {
            this.mVersionCode = cur.getInt(0);
            this.mMetadataRowID = cur.getLong(1);
            this.mStatus = cur.getInt(2);
            this.mFlags = cur.getInt(3);
            cur.close();
        }
        mDownloadsDB = this;
    }

    protected DownloadInfo getDownloadInfoByFileName(String fileName) {
        Cursor itemcur = null;
        try {
            itemcur = this.mHelper.getReadableDatabase().query("DownloadColumns", DC_PROJECTION, "FN = ?", new String[]{fileName}, null, null, null);
            if (itemcur == null || !itemcur.moveToFirst()) {
                if (itemcur != null) {
                    itemcur.close();
                }
                return null;
            }
            DownloadInfo downloadInfoFromCursor = getDownloadInfoFromCursor(itemcur);
            if (itemcur == null) {
                return downloadInfoFromCursor;
            }
            itemcur.close();
            return downloadInfoFromCursor;
        } catch (Throwable th) {
            if (itemcur != null) {
                itemcur.close();
            }
        }
    }

    public long getIDForDownloadInfo(DownloadInfo di) {
        return getIDByIndex(di.mIndex);
    }

    public long getIDByIndex(int index) {
        SQLiteStatement downloadByIndex = getDownloadByIndexStatement();
        downloadByIndex.clearBindings();
        downloadByIndex.bindLong(1, (long) index);
        try {
            return downloadByIndex.simpleQueryForLong();
        } catch (SQLiteDoneException e) {
            return -1;
        }
    }

    public void updateDownloadCurrentBytes(DownloadInfo di) {
        SQLiteStatement downloadCurrentBytes = getUpdateCurrentBytesStatement();
        downloadCurrentBytes.clearBindings();
        downloadCurrentBytes.bindLong(1, di.mCurrentBytes);
        downloadCurrentBytes.bindLong(2, (long) di.mIndex);
        downloadCurrentBytes.execute();
    }

    public boolean updateDownload(DownloadInfo di) {
        ContentValues cv = new ContentValues();
        cv.put("FILEIDX", Integer.valueOf(di.mIndex));
        cv.put("FN", di.mFileName);
        cv.put("URI", di.mUri);
        cv.put("ETAG", di.mETag);
        cv.put("TOTALBYTES", Long.valueOf(di.mTotalBytes));
        cv.put("CURRENTBYTES", Long.valueOf(di.mCurrentBytes));
        cv.put("LASTMOD", Long.valueOf(di.mLastMod));
        cv.put("STATUS", Integer.valueOf(di.mStatus));
        cv.put("CONTROL", Integer.valueOf(di.mControl));
        cv.put("FAILCOUNT", Integer.valueOf(di.mNumFailed));
        cv.put("RETRYAFTER", Integer.valueOf(di.mRetryAfter));
        cv.put("REDIRECTCOUNT", Integer.valueOf(di.mRedirectCount));
        return updateDownload(di, cv);
    }

    public boolean updateDownload(DownloadInfo di, ContentValues cv) {
        long id = di == null ? -1 : getIDForDownloadInfo(di);
        try {
            SQLiteDatabase sqldb = this.mHelper.getWritableDatabase();
            if (id != -1) {
                return 1 != sqldb.update("DownloadColumns", cv, new StringBuilder().append("DownloadColumns._id = ").append(id).toString(), null) ? false : false;
            } else {
                return -1 != sqldb.insert("DownloadColumns", "URI", cv);
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateFlags(int flags) {
        if (this.mFlags == flags) {
            return true;
        }
        ContentValues cv = new ContentValues();
        cv.put("DOWNLOADFLAGS", Integer.valueOf(flags));
        if (!updateMetadata(cv)) {
            return false;
        }
        this.mFlags = flags;
        return true;
    }

    public boolean updateStatus(int status) {
        if (this.mStatus == status) {
            return true;
        }
        ContentValues cv = new ContentValues();
        cv.put("DOWNLOADSTATUS", Integer.valueOf(status));
        if (!updateMetadata(cv)) {
            return false;
        }
        this.mStatus = status;
        return true;
    }

    public boolean updateMetadata(ContentValues cv) {
        SQLiteDatabase sqldb = this.mHelper.getWritableDatabase();
        if (-1 == this.mMetadataRowID) {
            long newID = sqldb.insert("MetadataColumns", "APKVERSION", cv);
            if (-1 == newID) {
                return false;
            }
            this.mMetadataRowID = newID;
        } else if (sqldb.update("MetadataColumns", cv, "_id = " + this.mMetadataRowID, null) == 0) {
            return false;
        }
        return true;
    }

    public boolean updateMetadata(int apkVersion, int downloadStatus) {
        ContentValues cv = new ContentValues();
        cv.put("APKVERSION", Integer.valueOf(apkVersion));
        cv.put("DOWNLOADSTATUS", Integer.valueOf(downloadStatus));
        if (!updateMetadata(cv)) {
            return false;
        }
        this.mVersionCode = apkVersion;
        this.mStatus = downloadStatus;
        return true;
    }

    public boolean updateFromDb(DownloadInfo di) {
        Cursor cur = null;
        try {
            cur = this.mHelper.getReadableDatabase().query("DownloadColumns", DC_PROJECTION, "FN= ?", new String[]{di.mFileName}, null, null, null);
            if (cur == null || !cur.moveToFirst()) {
                if (cur != null) {
                    cur.close();
                }
                return false;
            }
            setDownloadInfoFromCursor(di, cur);
            return true;
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
    }

    public void setDownloadInfoFromCursor(DownloadInfo di, Cursor cur) {
        di.mUri = cur.getString(1);
        di.mETag = cur.getString(2);
        di.mTotalBytes = cur.getLong(3);
        di.mCurrentBytes = cur.getLong(4);
        di.mLastMod = cur.getLong(5);
        di.mStatus = cur.getInt(6);
        di.mControl = cur.getInt(7);
        di.mNumFailed = cur.getInt(8);
        di.mRetryAfter = cur.getInt(9);
        di.mRedirectCount = cur.getInt(10);
    }

    public DownloadInfo getDownloadInfoFromCursor(Cursor cur) {
        DownloadInfo di = new DownloadInfo(cur.getInt(11), cur.getString(0), getClass().getPackage().getName());
        setDownloadInfoFromCursor(di, cur);
        return di;
    }

    public DownloadInfo[] getDownloads() {
        DownloadInfo[] retInfos = null;
        Cursor cur = null;
        try {
            cur = this.mHelper.getReadableDatabase().query("DownloadColumns", DC_PROJECTION, null, null, null, null, null);
            if (cur == null || !cur.moveToFirst()) {
                if (cur != null) {
                    cur.close();
                }
                return retInfos;
            }
            retInfos = new DownloadInfo[cur.getCount()];
            int idx = 0;
            while (true) {
                int idx2 = idx + 1;
                retInfos[idx] = getDownloadInfoFromCursor(cur);
                if (!cur.moveToNext()) {
                    break;
                }
                idx = idx2;
            }
            return retInfos;
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
    }
}
