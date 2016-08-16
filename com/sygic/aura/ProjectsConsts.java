package com.sygic.aura;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

public class ProjectsConsts {
    private static SparseBooleanArray mMapFlags;
    private static SparseIntArray mMapInts;
    private static SparseArray<String> mMapStrings;

    static {
        initValues();
    }

    private static void initValues() {
        if (mMapFlags == null) {
            mMapFlags = new SparseBooleanArray();
        }
        if (mMapStrings == null) {
            mMapStrings = new SparseArray();
        }
        if (mMapInts == null) {
            mMapInts = new SparseIntArray(1);
        }
        mMapFlags.put(0, false);
        mMapFlags.put(1, false);
        mMapFlags.put(2, false);
        mMapFlags.put(3, true);
        mMapFlags.put(4, false);
        mMapFlags.put(5, false);
        mMapFlags.put(6, false);
        mMapFlags.put(7, false);
        mMapStrings.put(10, null);
        mMapStrings.put(11, null);
        mMapStrings.put(12, null);
        mMapStrings.put(13, null);
        mMapStrings.put(14, null);
        mMapStrings.put(15, null);
        mMapStrings.put(16, null);
        mMapStrings.put(17, "nav");
        mMapInts.put(20, 3);
        mMapInts.put(21, 0);
        mMapInts.put(22, 0);
    }

    public static boolean getBoolean(int key) {
        if (mMapFlags == null) {
            initValues();
        }
        return mMapFlags.get(key);
    }

    public static void setBoolean(int key, boolean value) {
        if (mMapFlags == null) {
            initValues();
        }
        mMapFlags.put(key, value);
    }

    public static String getString(int key) {
        if (mMapStrings == null) {
            initValues();
        }
        return (String) mMapStrings.get(key);
    }

    public static void setString(int key, String value) {
        if (mMapStrings == null) {
            initValues();
        }
        mMapStrings.put(key, value);
    }

    public static int getInt(int key) {
        if (mMapInts == null) {
            initValues();
        }
        return mMapInts.get(key);
    }
}
