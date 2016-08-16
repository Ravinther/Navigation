package com.sygic.aura.connectivity;

import android.content.Context;
import android.database.Cursor;
import android.net.NetworkInfo;
import loquendo.tts.engine.TTSConst;

public class ConnectivityChangesManager {
    private static ConnectivityChangesManager sInstance;
    private final ConnectivityChangesCache mCache;

    /* renamed from: com.sygic.aura.connectivity.ConnectivityChangesManager.1 */
    static /* synthetic */ class C11491 {
        static final /* synthetic */ int[] f1253x6345db1;

        static {
            f1253x6345db1 = new int[ConnType.values().length];
            try {
                f1253x6345db1[ConnType.OFFLINE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1253x6345db1[ConnType.WIFI.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1253x6345db1[ConnType.MOBILE_2G.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1253x6345db1[ConnType.MOBILE_3G.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1253x6345db1[ConnType.MOBILE_4G.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1253x6345db1[ConnType.MOBILE_UNKNOWN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1253x6345db1[ConnType.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public enum ConnType {
        UNKNOWN,
        OFFLINE,
        WIFI,
        MOBILE_2G,
        MOBILE_3G,
        MOBILE_4G,
        MOBILE_UNKNOWN
    }

    public static ConnectivityChangesManager get(Context context) {
        if (sInstance == null) {
            sInstance = new ConnectivityChangesManager(context.getApplicationContext());
        }
        return sInstance;
    }

    private ConnectivityChangesManager(Context context) {
        this.mCache = new ConnectivityChangesCache(context);
    }

    public void addChange(ConnType connType) {
        this.mCache.addConnectivityChange(connType);
    }

    public void addForegroundChange(boolean inForeground) {
        this.mCache.addInForegroundChange(inForeground);
    }

    public Cursor getForegroundChangesIn(long startTimestamp, long endTimestamp) {
        return this.mCache.getForegroundChangesIn(startTimestamp, endTimestamp);
    }

    public Cursor getChangesIn(long startTimestamp, long endTimestamp) {
        return this.mCache.getConnectivityChangesIn(startTimestamp, endTimestamp);
    }

    public Cursor getChangesBefore(long timestamp) {
        return this.mCache.getConnectivityChangeBefore(timestamp);
    }

    public static String stringFromNetworkInfo(NetworkInfo ni) {
        switch (C11491.f1253x6345db1[typeFromNetworkInfo(ni).ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return "offline";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "wifi";
            case TTSConst.TTSUNICODE /*3*/:
                return "mobile_2g";
            case TTSConst.TTSXML /*4*/:
                return "mobile_3g";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "mobile_4g";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "mobile_unknown";
            default:
                return "unknown";
        }
    }

    public static ConnType typeFromNetworkInfo(NetworkInfo ni) {
        if (ni == null || !ni.isConnected()) {
            return ConnType.OFFLINE;
        }
        if (ni.getType() == 1) {
            return ConnType.WIFI;
        }
        if (ni.getType() != 0) {
            return ConnType.UNKNOWN;
        }
        switch (ni.getSubtype()) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                return ConnType.MOBILE_2G;
            case TTSConst.TTSUNICODE /*3*/:
            case TTSConst.TTSEVT_TEXT /*5*/:
            case TTSConst.TTSEVT_SENTENCE /*6*/:
            case TTSConst.TTSEVT_TAG /*8*/:
            case TTSConst.TTSEVT_PAUSE /*9*/:
            case TTSConst.TTSEVT_RESUME /*10*/:
            case TTSConst.TTSEVT_NOTSENT /*12*/:
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                return ConnType.MOBILE_3G;
            case TTSConst.TTSEVT_AUDIO /*13*/:
                return ConnType.MOBILE_4G;
            default:
                return ConnType.MOBILE_UNKNOWN;
        }
    }
}
