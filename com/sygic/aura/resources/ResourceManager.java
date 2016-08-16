package com.sygic.aura.resources;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.LocaleHelper;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.sos.data.EmergencyNumbers;
import java.util.Locale;

public class ResourceManager {
    private static int[] TRAFFIC_COLORS;

    /* renamed from: com.sygic.aura.resources.ResourceManager.10 */
    static class AnonymousClass10 implements Callback<String> {
        final /* synthetic */ boolean val$bDynamicWidth;
        final /* synthetic */ boolean val$bSeconds;
        final /* synthetic */ boolean val$bSimpleText;
        final /* synthetic */ long val$iSpan;

        AnonymousClass10(long j, boolean z, boolean z2, boolean z3) {
            this.val$iSpan = j;
            this.val$bSeconds = z;
            this.val$bSimpleText = z2;
            this.val$bDynamicWidth = z3;
        }

        public String getMethod() {
            return ResourceManager.FormatTimeSpanToShortWords(this.val$iSpan, this.val$bSeconds, this.val$bSimpleText, this.val$bDynamicWidth);
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.11 */
    static class AnonymousClass11 implements Callback<String> {
        final /* synthetic */ long val$iSpan;

        AnonymousClass11(long j) {
            this.val$iSpan = j;
        }

        public String getMethod() {
            return ResourceManager.FormatETA(this.val$iSpan);
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.12 */
    static class AnonymousClass12 implements Callback<Double> {
        final /* synthetic */ boolean val$bRound;
        final /* synthetic */ int val$nAccuracy;
        final /* synthetic */ double val$speed;

        AnonymousClass12(double d, boolean z, int i) {
            this.val$speed = d;
            this.val$bRound = z;
            this.val$nAccuracy = i;
        }

        public Double getMethod() {
            return Double.valueOf(ResourceManager.FormatSpeed(this.val$speed, this.val$bRound, this.val$nAccuracy));
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.13 */
    static class AnonymousClass13 implements Callback<ValueUnitPair<String, String>> {
        final /* synthetic */ double val$speed;

        AnonymousClass13(double d) {
            this.val$speed = d;
        }

        public ValueUnitPair<String, String> getMethod() {
            return ResourceManager.FormatSpeedWithUnits(this.val$speed, false);
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.14 */
    static class AnonymousClass14 implements Callback<String> {
        final /* synthetic */ boolean val$bRound;
        final /* synthetic */ boolean val$bSimpleText;
        final /* synthetic */ double val$speed;

        AnonymousClass14(double d, boolean z, boolean z2) {
            this.val$speed = d;
            this.val$bRound = z;
            this.val$bSimpleText = z2;
        }

        public String getMethod() {
            return ResourceManager.FormatSpeed(this.val$speed, this.val$bRound, this.val$bSimpleText);
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.15 */
    static class AnonymousClass15 implements Callback<String> {
        final /* synthetic */ long val$bytes;

        AnonymousClass15(long j) {
            this.val$bytes = j;
        }

        public String getMethod() {
            return ResourceManager.FormatBytes(this.val$bytes, 0);
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.1 */
    static class C14711 implements Callback<String> {
        final /* synthetic */ int val$id;
        final /* synthetic */ Resources val$res;

        C14711(Resources resources, int i) {
            this.val$res = resources;
            this.val$id = i;
        }

        public String getMethod() {
            return ResourceManager.GetString(this.val$res.getString(this.val$id));
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.2 */
    static class C14722 implements Callback<Boolean> {
        final /* synthetic */ String val$source;
        final /* synthetic */ String val$what;

        C14722(String str, String str2) {
            this.val$source = str;
            this.val$what = str2;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(ResourceManager.FindMatch(this.val$source, this.val$what));
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.3 */
    static class C14733 implements Callback<String> {
        C14733() {
        }

        public String getMethod() {
            return ResourceManager.GetResourcePath();
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.4 */
    static class C14744 implements Callback<String> {
        C14744() {
        }

        public String getMethod() {
            return ResourceManager.GetMapPath();
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.5 */
    static class C14755 implements Callback<String> {
        final /* synthetic */ boolean val$getSimpleText;
        final /* synthetic */ long val$lDistance;

        C14755(long j, boolean z) {
            this.val$lDistance = j;
            this.val$getSimpleText = z;
        }

        public String getMethod() {
            return ResourceManager.FormatDistance(this.val$lDistance, this.val$getSimpleText);
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.6 */
    static class C14766 implements Callback<ValueUnitPair<String, String>> {
        final /* synthetic */ boolean val$bRound;
        final /* synthetic */ boolean val$bSimpleText;
        final /* synthetic */ boolean val$bSmallUnits;
        final /* synthetic */ long val$lDistance;

        C14766(long j, boolean z, boolean z2, boolean z3) {
            this.val$lDistance = j;
            this.val$bRound = z;
            this.val$bSmallUnits = z2;
            this.val$bSimpleText = z3;
        }

        public ValueUnitPair<String, String> getMethod() {
            return ResourceManager.FormatDistance(this.val$lDistance, this.val$bRound, this.val$bSmallUnits, this.val$bSimpleText);
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.7 */
    static class C14777 implements Callback<String> {
        final /* synthetic */ boolean val$bSeconds;

        C14777(boolean z) {
            this.val$bSeconds = z;
        }

        public String getMethod() {
            return ResourceManager.FormatCurrentTimeStampToDigits(this.val$bSeconds);
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.8 */
    static class C14788 implements Callback<String> {
        C14788() {
        }

        public String getMethod() {
            return ResourceManager.FormatCurrentDate();
        }
    }

    /* renamed from: com.sygic.aura.resources.ResourceManager.9 */
    static class C14799 implements Callback<String> {
        final /* synthetic */ int val$x;
        final /* synthetic */ int val$y;

        C14799(int i, int i2) {
            this.val$x = i;
            this.val$y = i2;
        }

        public String getMethod() {
            return ResourceManager.FormatPosition(this.val$x, this.val$y);
        }
    }

    public enum TrafficType {
        NOT_SET(-1),
        NONE(0),
        LOW(1),
        MEDIUM(2),
        HIGH(3);
        
        public final int id;

        private TrafficType(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }

        public static TrafficType createFromInt(int value) {
            for (TrafficType eItem : values()) {
                if (eItem.getValue() == value) {
                    return eItem;
                }
            }
            return NONE;
        }
    }

    private static native boolean FindMatch(String str, String str2);

    private static native String FormatAltitude(float f, boolean z);

    private static native String FormatBytes(long j, int i);

    private static native String FormatCurrentDate();

    private static native String FormatCurrentTimeStampToDigits(boolean z);

    private static native ValueUnitPair<String, String> FormatDistance(long j, boolean z, boolean z2, boolean z3);

    private static native String FormatDistance(long j, boolean z);

    private static native String FormatETA(long j);

    private static native String FormatPosition(int i, int i2);

    private static native double FormatSpeed(double d, boolean z, int i);

    private static native String FormatSpeed(double d, boolean z, boolean z2);

    private static native ValueUnitPair<String, String> FormatSpeedWithUnits(double d, boolean z);

    private static native String FormatTimeSpanToShortWords(long j, boolean z, boolean z2, boolean z3);

    private static native String GetInitializedOpenGlVersion();

    private static native EmergencyNumbers GetLocalEmergencyNumbers();

    private static native String GetMapPath();

    private static native String GetResourcePath();

    private static native String GetString(String str);

    static {
        TRAFFIC_COLORS = null;
    }

    public static String getCoreString(Context context, int id) {
        if (context == null) {
            return null;
        }
        return getCoreString(context.getResources(), id);
    }

    public static String getCoreString(Resources res, int id) {
        if (id <= 0 || res == null) {
            return null;
        }
        if (SygicProject.IS_PROTOTYPE) {
            return res.getString(id);
        }
        return (String) new ObjectHandler(new C14711(res, id)).execute().get(null);
    }

    public static String getCoreString(String strId) {
        return SygicProject.IS_PROTOTYPE ? "core_str" : GetString(strId);
    }

    public static String getCoreString(CharSequence strId) {
        return strId != null ? getCoreString(strId.toString()) : "";
    }

    public static String getResourceOrCoreString(Context context, View view, int iResId) {
        if (context == null || view == null) {
            return null;
        }
        if (view.isInEditMode()) {
            return context.getString(iResId);
        }
        return getCoreString(context.getString(iResId));
    }

    public static boolean nativeFindMatch(String source, String what) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C14722(source, what)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static String nativeGetResourcePath() {
        if (SygicProject.IS_PROTOTYPE) {
            return "/mnt/sdcard/Sygic/Res";
        }
        return (String) new ObjectHandler(new C14733()).execute().get(null);
    }

    public static String nativeGetMapPath() {
        if (SygicProject.IS_PROTOTYPE) {
            return "/mnt/sdcard/Sygic/Maps";
        }
        return (String) new ObjectHandler(new C14744()).execute().get(null);
    }

    public static String nativeFormatDistance(long lDistance) {
        return SygicProject.IS_PROTOTYPE ? "5 km" : nativeFormatDistance(lDistance, true);
    }

    public static String nativeFormatDistance(long lDistance, boolean getSimpleText) {
        if (SygicProject.IS_PROTOTYPE) {
            return "11";
        }
        return (String) new ObjectHandler(new C14755(lDistance, getSimpleText)).execute().get("0");
    }

    public static ValueUnitPair<String, String> nativeFormatDistance(long lDistance, boolean bRound, boolean bSmallUnits, boolean bSimpleText) {
        if (SygicProject.IS_PROTOTYPE) {
            return new ValueUnitPair("", "");
        }
        return (ValueUnitPair) new ObjectHandler(new C14766(lDistance, bRound, bSmallUnits, bSimpleText)).execute().get(new ValueUnitPair("", ""));
    }

    public static String nativeFormatTimeSpanToShortWords(long iSpan) {
        return nativeFormatTimeSpanToShortWords(iSpan, false, true, true);
    }

    public static String nativeFormatCurrentTimeStampToDigits(boolean bSeconds) {
        if (SygicProject.IS_PROTOTYPE) {
            return "11:00";
        }
        return (String) new ObjectHandler(new C14777(bSeconds)).execute().get(null);
    }

    public static String nativeFormatCurrentDate() {
        if (SygicProject.IS_PROTOTYPE) {
            return "16.09.2014";
        }
        return (String) new ObjectHandler(new C14788()).execute().get(null);
    }

    public static String nativeFormatPosition(int x, int y) {
        if (SygicProject.IS_PROTOTYPE) {
            return "N 38\u00b053\u203223\u2033 E 77\u00b0 00\u2032 32\u2033";
        }
        return (String) new ObjectHandler(new C14799(x, y)).execute().get(null);
    }

    public static String nativeFormatTimeSpanToShortWords(long iSpan, boolean bSeconds, boolean bSimpleText, boolean bDynamicWidth) {
        if (SygicProject.IS_PROTOTYPE) {
            return "100min";
        }
        return (String) new ObjectHandler(new AnonymousClass10(iSpan, bSeconds, bSimpleText, bDynamicWidth)).execute().get(null);
    }

    public static String nativeFormatETA(long iSpan) {
        if (SygicProject.IS_PROTOTYPE) {
            return "10:11";
        }
        return (String) new ObjectHandler(new AnonymousClass11(iSpan)).execute().get(null);
    }

    public static double nativeFormatSpeed(double speed, boolean bRound, int nAccuracy) {
        if (SygicProject.IS_PROTOTYPE) {
            return 123.0d;
        }
        return ((Double) new ObjectHandler(new AnonymousClass12(speed, bRound, nAccuracy)).execute().get(null)).doubleValue();
    }

    public static double nativeFormatSpeed(double speed, boolean bRound) {
        return nativeFormatSpeed(speed, bRound, 5);
    }

    public static String nativeFormatSpeed(double speed) {
        return nativeFormatSpeed(speed, false, false);
    }

    public static ValueUnitPair<String, String> nativeFormatSpeedWithUnits(double speed) {
        if (SygicProject.IS_PROTOTYPE) {
            return new ValueUnitPair("50", "kmh");
        }
        return (ValueUnitPair) new ObjectHandler(new AnonymousClass13(speed)).execute().get(new ValueUnitPair("", ""));
    }

    public static String nativeFormatSpeed(double speed, boolean bRound, boolean bSimpleText) {
        if (SygicProject.IS_PROTOTYPE) {
            return "129";
        }
        return (String) new ObjectHandler(new AnonymousClass14(speed, bRound, bSimpleText)).execute().get("0");
    }

    public static String nativeFormatAltitude(float altitude, boolean bSimpleText) {
        return SygicProject.IS_PROTOTYPE ? "485" : FormatAltitude(altitude, bSimpleText);
    }

    public static String nativeFormatBytes(long bytes) {
        if (SygicProject.IS_PROTOTYPE) {
            return "11kb";
        }
        return (String) new ObjectHandler(new AnonymousClass15(bytes)).execute().get(null);
    }

    public static void initTrafficColors(Context context) {
        TRAFFIC_COLORS = new int[]{0, 0, context.getResources().getColor(2131558682), context.getResources().getColor(2131558793), -65536};
    }

    public static int getTrafficColor(Context context, TrafficType trafficType) {
        if (TRAFFIC_COLORS == null) {
            initTrafficColors(context);
        }
        if (TRAFFIC_COLORS[0] == 0) {
            TRAFFIC_COLORS[0] = context.getResources().getColor(2131558470);
        }
        if (trafficType == TrafficType.NOT_SET) {
            return TRAFFIC_COLORS[0];
        }
        return TRAFFIC_COLORS[trafficType.ordinal()];
    }

    public static int getTrafficColor(Context context, int colorOrder) {
        if (TRAFFIC_COLORS == null) {
            initTrafficColors(context);
        }
        if (TRAFFIC_COLORS[0] == 0) {
            TRAFFIC_COLORS[0] = context.getResources().getColor(2131558470);
        }
        return TRAFFIC_COLORS[colorOrder];
    }

    public static void drawMultiFontIcon(Context context, TextView[] ctrls, int[] codePoint, int[] refCodePoint) {
        drawMultiFontIcon(context, ctrls, codePoint, refCodePoint, false);
    }

    public static void drawMultiFontIcon(Context context, TextView[] ctrls, int[] codePoint, int[] refCodePoint, boolean forceRedraw) {
        int i = 0;
        while (i < ctrls.length) {
            boolean isZeroCode;
            boolean z;
            TextView ctrl = ctrls[i];
            if (codePoint[i] == 0) {
                isZeroCode = true;
            } else {
                isZeroCode = false;
            }
            if (isZeroCode) {
                z = false;
            } else {
                z = true;
            }
            makeControlVisible(ctrl, z);
            if (!isZeroCode && (forceRedraw || refCodePoint == null || codePoint[i] != refCodePoint[i])) {
                ctrl.setText(String.valueOf(Character.toChars(remapDirection(context, codePoint[i]))));
            }
            i++;
        }
    }

    public static int remapDirection(Context context, int code) {
        int[] arrayIn = context.getResources().getIntArray(2131492865);
        int[] arrayOut = context.getResources().getIntArray(2131492866);
        for (int i = 0; i < arrayIn.length; i++) {
            if (arrayIn[i] == code) {
                return arrayOut[i];
            }
        }
        return code;
    }

    public static void makeControlVisible(View view, boolean toShow) {
        makeControlVisible(view, toShow, false);
    }

    public static void makeControlVisible(View view, boolean toShow, boolean useGone) {
        if (view != null) {
            int visibility = toShow ? 0 : useGone ? 8 : 4;
            view.setVisibility(visibility);
        }
    }

    public static String sanitizeUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return !url.contains("://") ? "http://" + url : url;
    }

    public static String getGooglePlayUrl(Context context) {
        return "market://details?id=" + context.getPackageName();
    }

    public static void setActionButtonIcon(Context context, MenuItem item, int iconRes) {
        if (item != null) {
            Drawable icon = item.getIcon();
            if (icon instanceof FontDrawable) {
                ((FontDrawable) icon).setText(context.getString(iconRes));
                item.setIcon(icon);
            }
        }
    }

    public static EmergencyNumbers nativeGetLocalEmergencyNumbers() {
        if (SygicProject.IS_PROTOTYPE) {
            return new EmergencyNumbers("155", "150", "158");
        }
        return (EmergencyNumbers) new ObjectHandler(new Callback<EmergencyNumbers>() {
            public EmergencyNumbers getMethod() {
                return ResourceManager.GetLocalEmergencyNumbers();
            }
        }).execute().get(null);
    }

    public static String nativeGetInitializedOpenGlVersion() {
        if (SygicProject.IS_PROTOTYPE) {
            return "9.9";
        }
        return (String) new ObjectHandler(new Callback<String>() {
            public String getMethod() {
                return ResourceManager.GetInitializedOpenGlVersion();
            }
        }).execute().get("-1");
    }

    public static String getQuantityString(Resources res, int id, int count, String language) {
        Configuration conf = res.getConfiguration();
        Locale savedLocale = conf.locale;
        conf.locale = LocaleHelper.getLocale(language);
        res.updateConfiguration(conf, null);
        String quantityString = res.getQuantityString(id, count);
        conf.locale = savedLocale;
        res.updateConfiguration(conf, null);
        return getCoreString(quantityString);
    }

    public static String getQuantityString(Resources res, int id, int count) {
        return getQuantityString(res, id, count, SettingsManager.nativeGetSelectedLanguage());
    }
}
