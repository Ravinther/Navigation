package com.sygic.aura.analytics;

import android.content.Context;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.tracker.TrackerUtils;
import loquendo.tts.engine.TTSConst;

public class SygicAnalyticsLogger {

    /* renamed from: com.sygic.aura.analytics.SygicAnalyticsLogger.1 */
    static /* synthetic */ class C11411 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType;

        static {
            $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType = new int[EventType.values().length];
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.FRW.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.CLICK.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.UNLOCK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.ACTIVATION.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.QUICK_MENU.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.QUICK_MENU_FLURRY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.WEBVIEW.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.IN_APP_PURCHASE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.SCOUT_COMPUTE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.CORE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.LIVE_SERVICES.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.NOTIFICATION.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.EVENT_NAME_PURCHASED.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.EVENT_NAME_ADDED_TO_CART.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.EVENT_NAME_ADDED_TO_WISHLIST.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[EventType.NOTIFICATION_CENTER.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
        }
    }

    public static AnalyticsEvent getAnalyticsEvent(Context context, EventType eventType) {
        return AnalyticsEvent.getAnalyticsEvent(context, eventType);
    }

    public static void logEvent(Context context, EventType type, Bundle params) {
        if (context == null) {
            Crashlytics.logException(new NullPointerException("Trying to log with null context"));
            return;
        }
        switch (C11411.$SwitchMap$com$sygic$aura$analytics$AnalyticsInterface$EventType[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                params.putSerializable("metrics", TrackerUtils.getConnectivityMetrics(context));
                SygicTrackerAnalyticsLogger.getInstance(context).logEvent(params);
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSUNICODE /*3*/:
                GoogleAnalyticsLogger.getInstance(context).logEvent(params);
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_TEXT /*5*/:
                GoogleAnalyticsLogger.getInstance(context).logEvent(params);
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                CoreAnalyticsLogger.getInstance().logEvent(params);
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
            case TTSConst.TTSEVT_TAG /*8*/:
                GoogleAnalyticsLogger.getInstance(context).logEvent(params);
                FacebookAnalyticsLogger.getInstance(context).logEvent(params);
            case TTSConst.TTSEVT_PAUSE /*9*/:
            case TTSConst.TTSEVT_RESUME /*10*/:
                CoreAnalyticsLogger.getInstance().logEvent(params);
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                FlurryAnalyticsLogger.getInstance(context).logEvent(params);
            case TTSConst.TTSEVT_NOTSENT /*12*/:
                InfinarioAnalyticsLogger.getInstance(context).logEvent(params);
            case TTSConst.TTSEVT_AUDIO /*13*/:
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                FacebookAnalyticsLogger.getInstance(context).logEvent(params);
            case TTSConst.TTSEVT_ERROR /*16*/:
                FlurryAnalyticsLogger.getInstance(context).logEvent(params);
                InfinarioAnalyticsLogger.getInstance(context).logEvent(params);
            default:
        }
    }
}
