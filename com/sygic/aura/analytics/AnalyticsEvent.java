package com.sygic.aura.analytics;

import android.content.Context;
import android.support.v4.util.Pools.SynchronizedPool;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import java.util.HashMap;
import loquendo.tts.engine.TTSConst;

public class AnalyticsEvent {
    private static SynchronizedPool<AnalyticsEvent> sPool;
    private Context mContext;
    private EventType mEventType;
    private StringBuilder mName;
    private HashMap<String, String> mParams;

    /* renamed from: com.sygic.aura.analytics.AnalyticsEvent.1 */
    static /* synthetic */ class C11401 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType;

        static {
            $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType = new int[EventType.values().length];
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.SETTINGS_POI.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.SETTINGS_CATEGORY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.SETTINGS_CHANGE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.NOTIFICATION_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.SEARCH.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.BOOKING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.STORE_PAYMENT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.OFFER_PARKING.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.WIDGET_START_NAVI.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.WIDGET_LIFECYCLE_ACTION.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.WIDGET_TRAFFIC_REQUEST.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.NOTIFICATION.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    static {
        sPool = new SynchronizedPool(10);
    }

    static AnalyticsEvent getAnalyticsEvent(Context context, EventType eventType) {
        AnalyticsEvent event = (AnalyticsEvent) sPool.acquire();
        if (event == null) {
            return new AnalyticsEvent(context, eventType);
        }
        event.reset(context, eventType);
        return event;
    }

    private AnalyticsEvent(Context context, EventType eventType) {
        this.mParams = new HashMap();
        this.mName = new StringBuilder();
        this.mEventType = eventType;
        this.mContext = context;
    }

    public AnalyticsEvent setName(String name) {
        this.mName.setLength(0);
        this.mName.append(name);
        return this;
    }

    public String getName() {
        return this.mName.toString();
    }

    public AnalyticsEvent setValue(String key, Object value) {
        setValue(key, value.toString());
        return this;
    }

    public AnalyticsEvent setValue(String key, String value) {
        this.mParams.put(key, value);
        return this;
    }

    public HashMap getParams() {
        return this.mParams;
    }

    public AnalyticsEvent clearParams() {
        this.mParams = new HashMap();
        return this;
    }

    public boolean logAndRecycle() {
        return logAndKeep().recycle();
    }

    public AnalyticsEvent logAndKeep() {
        if (this.mContext != null) {
            switch (C11401.$SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[this.mEventType.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                case TTSConst.TTSPARAGRAPH /*2*/:
                case TTSConst.TTSUNICODE /*3*/:
                case TTSConst.TTSXML /*4*/:
                    FlurryAnalyticsLogger.getInstance(this.mContext).logEvent(this);
                    InfinarioAnalyticsLogger.getInstance(this.mContext).logEvent(this);
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    FlurryAnalyticsLogger.getInstance(this.mContext).logEvent(this);
                    break;
                case TTSConst.TTSEVT_TAG /*8*/:
                    FlurryAnalyticsLogger.getInstance(this.mContext).logEvent(this);
                    break;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                case TTSConst.TTSEVT_RESUME /*10*/:
                case TTSConst.TTSEVT_FREESPACE /*11*/:
                    FlurryAnalyticsLogger.getInstance(this.mContext).logEvent(this);
                    break;
                case TTSConst.TTSEVT_NOTSENT /*12*/:
                    InfinarioAnalyticsLogger.getInstance(this.mContext).logEvent(this);
                    break;
                default:
                    break;
            }
        }
        Crashlytics.logException(new NullPointerException("Trying to log with null context"));
        return this;
    }

    public boolean recycle() {
        boolean result = sPool.release(this);
        if (!result) {
            String msg = "Releasing AnalyticsEvent to the pool failed!";
            Log.w("AnalyticsEvent", "Releasing AnalyticsEvent to the pool failed!");
            Crashlytics.log(5, "AnalyticsEvent", "Releasing AnalyticsEvent to the pool failed!");
        }
        return result;
    }

    private void reset(Context context, EventType eventType) {
        this.mContext = context;
        this.mEventType = eventType;
        this.mName.setLength(0);
        clearParams();
    }
}
