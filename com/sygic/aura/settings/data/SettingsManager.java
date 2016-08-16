package com.sygic.aura.settings.data;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import com.sygic.aura.SygicProject;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.WidgetManager;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.fragments.RateDialogFragment;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.widget.places.data.PlaceEntry;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import loquendo.tts.engine.TTSConst;

public class SettingsManager {
    private static final String FEEDBACK_EMAIL = "androidfeedback@sygic.com";
    private static final String HELP_URL = "http://help.sygic.com/hc/en-us/categories/200364127-GPS-Navigation-Maps-Sygic-for-Android";
    private static final String SECONDARY_INSTRUCTION_FIXED = "secondary_instruction_fixed";
    private static final Set<OnDebugChangeListener> mDebugListeners;
    private static final Set<OnLanguageChangeListener> mLangListeners;
    private static final Set<OnCoreSettingsChangeListener> mListeners;

    public interface OnLanguageChangeListener {
        void onLanguageChanged(String str);
    }

    public interface OnCoreSettingsChangeListener {
        void onCoreSettingsChanged(ESettingsType eSettingsType, int i);
    }

    public interface OnDebugChangeListener {
        void onDebugChanged();
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.10 */
    static class AnonymousClass10 implements Callback<Integer> {
        final /* synthetic */ ESettingsType val$eSettingsType;

        AnonymousClass10(ESettingsType eSettingsType) {
            this.val$eSettingsType = eSettingsType;
        }

        public Integer getMethod() {
            return Integer.valueOf(SettingsManager.GetSettings(this.val$eSettingsType.getValue()));
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.11 */
    static class AnonymousClass11 implements VoidCallback {
        final /* synthetic */ ESettingsType val$eSettingsType;
        final /* synthetic */ int val$value;

        AnonymousClass11(ESettingsType eSettingsType, int i) {
            this.val$eSettingsType = eSettingsType;
            this.val$value = i;
        }

        public void getMethod() {
            SettingsManager.SetSettings(this.val$eSettingsType.getValue(), this.val$value);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.13 */
    static class AnonymousClass13 implements VoidCallback {
        final /* synthetic */ String val$strLangFile;

        AnonymousClass13(String str) {
            this.val$strLangFile = str;
        }

        public void getMethod() {
            SettingsManager.SetLanguage(this.val$strLangFile);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.16 */
    static class AnonymousClass16 implements VoidCallback {
        final /* synthetic */ boolean val$bTTS;
        final /* synthetic */ String val$strFolder;
        final /* synthetic */ String val$strVoice;

        AnonymousClass16(String str, String str2, boolean z) {
            this.val$strFolder = str;
            this.val$strVoice = str2;
            this.val$bTTS = z;
        }

        public void getMethod() {
            SettingsManager.SetVoice(this.val$strFolder, this.val$strVoice, this.val$bTTS);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.18 */
    static class AnonymousClass18 implements Callback<Boolean> {
        final /* synthetic */ ESoundSettingsType val$eWarningType;

        AnonymousClass18(ESoundSettingsType eSoundSettingsType) {
            this.val$eWarningType = eSoundSettingsType;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(SettingsManager.IsTTSEnabled(this.val$eWarningType.getValue()));
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.19 */
    static class AnonymousClass19 implements Callback<Boolean> {
        final /* synthetic */ ESoundSettingsType val$eWarningType;

        AnonymousClass19(ESoundSettingsType eSoundSettingsType) {
            this.val$eWarningType = eSoundSettingsType;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(SettingsManager.IsDefaultTTSEnabled(this.val$eWarningType.getValue()));
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.1 */
    static class C16181 implements ExecutionOrder {
        final /* synthetic */ NaviNativeActivity val$activity;

        C16181(NaviNativeActivity naviNativeActivity) {
            this.val$activity = naviNativeActivity;
        }

        public boolean runningCondition() {
            boolean bResult;
            int i = 1;
            if (PositionInfo.nativeGetCurrentVehicleSpeed() <= 0.0d) {
                bResult = true;
            } else {
                bResult = false;
            }
            if (RouteManager.nativeExistValidRoute()) {
                int i2;
                if (RouteSummary.nativeGetRemainingDistance() < 0 || RouteSummary.nativeGetRemainingDistance() >= 500) {
                    i2 = 0;
                } else {
                    i2 = 1;
                }
                bResult &= i2;
            }
            if (this.val$activity.isDrawerVisible()) {
                i = 0;
            }
            return (bResult & i) & this.val$activity.wasCrashFree();
        }

        public boolean onPositive() {
            try {
                RateDialogFragment.newInstance().show(this.val$activity.getSupportFragmentManager(), "RateDialogFragment");
            } catch (IllegalStateException e) {
                CrashlyticsHelper.logException(SettingsManager.class.getName(), "setupRatingDlg", e);
            }
            return false;
        }

        public boolean onNegative() {
            return true;
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.22 */
    static class AnonymousClass22 implements VoidCallback {
        final /* synthetic */ ESoundSettingsType val$eWarningType;
        final /* synthetic */ String val$strFolder;

        AnonymousClass22(String str, ESoundSettingsType eSoundSettingsType) {
            this.val$strFolder = str;
            this.val$eWarningType = eSoundSettingsType;
        }

        public void getMethod() {
            SettingsManager.SetSound(this.val$strFolder, this.val$eWarningType.getValue());
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.23 */
    static class AnonymousClass23 implements VoidCallback {
        final /* synthetic */ ESoundSettingsType val$eWarningType;
        final /* synthetic */ String val$strMsg;

        AnonymousClass23(String str, ESoundSettingsType eSoundSettingsType) {
            this.val$strMsg = str;
            this.val$eWarningType = eSoundSettingsType;
        }

        public void getMethod() {
            SettingsManager.SetTTSSound(this.val$strMsg, this.val$eWarningType.getValue());
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.24 */
    static class AnonymousClass24 implements Callback<String> {
        final /* synthetic */ ESoundSettingsType val$eWarningType;

        AnonymousClass24(ESoundSettingsType eSoundSettingsType) {
            this.val$eWarningType = eSoundSettingsType;
        }

        public String getMethod() {
            return SettingsManager.GetSelectedSound(this.val$eWarningType.getValue());
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.26 */
    static class AnonymousClass26 implements VoidCallback {
        final /* synthetic */ boolean val$bValue;

        AnonymousClass26(boolean z) {
            this.val$bValue = z;
        }

        public void getMethod() {
            SettingsManager.SetFirstRun(this.val$bValue);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.2 */
    static class C16192 implements Callback<VoiceEntry[]> {
        C16192() {
        }

        public VoiceEntry[] getMethod() {
            return SettingsManager.GetVoices(true);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.33 */
    static class AnonymousClass33 implements VoidCallback {
        final /* synthetic */ boolean val$bTTS;
        final /* synthetic */ String val$strData;

        AnonymousClass33(String str, boolean z) {
            this.val$strData = str;
            this.val$bTTS = z;
        }

        public void getMethod() {
            SettingsManager.PlayWarnSample(this.val$strData, this.val$bTTS);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.34 */
    static class AnonymousClass34 implements VoidCallback {
        final /* synthetic */ boolean val$bTTS;
        final /* synthetic */ String val$strSamplePath;

        AnonymousClass34(String str, boolean z) {
            this.val$strSamplePath = str;
            this.val$bTTS = z;
        }

        public void getMethod() {
            SettingsManager.PlaySample(this.val$strSamplePath, this.val$bTTS);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.35 */
    static class AnonymousClass35 implements VoidCallback {
        final /* synthetic */ String val$strSoundPath;

        AnonymousClass35(String str) {
            this.val$strSoundPath = str;
        }

        public void getMethod() {
            SettingsManager.PlaySoundFile(this.val$strSoundPath);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.3 */
    static class C16203 implements Callback<VoiceEntry[]> {
        C16203() {
        }

        public VoiceEntry[] getMethod() {
            return SettingsManager.GetVoices(false);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.40 */
    static class AnonymousClass40 implements VoidCallback {
        final /* synthetic */ String val$strMail;

        AnonymousClass40(String str) {
            this.val$strMail = str;
        }

        public void getMethod() {
            SettingsManager.SetUserMail(this.val$strMail);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.41 */
    static class AnonymousClass41 implements Callback<Boolean> {
        final /* synthetic */ boolean val$bSwitchOn;

        AnonymousClass41(boolean z) {
            this.val$bSwitchOn = z;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(SettingsManager.SwitchDropbox(this.val$bSwitchOn));
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.43 */
    static class AnonymousClass43 implements Callback<Boolean> {
        final /* synthetic */ String val$strText;

        AnonymousClass43(String str) {
            this.val$strText = str;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(SettingsManager.EnableDebugMode(this.val$strText));
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.47 */
    static class AnonymousClass47 implements Callback<Boolean> {
        final /* synthetic */ LongPosition val$position;

        AnonymousClass47(LongPosition longPosition) {
            this.val$position = longPosition;
        }

        public Boolean getMethod() {
            if (!this.val$position.isValid()) {
                return Boolean.valueOf(false);
            }
            SettingsManager.SetSavedCarPosition(this.val$position.toNativeLong());
            return Boolean.valueOf(true);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.49 */
    static /* synthetic */ class AnonymousClass49 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType;
        static final /* synthetic */ int[] f1285xd5a24482;

        static {
            $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType = new int[EWidgetType.values().length];
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeHome.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeWork.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeBlackBox.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeHUD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeSOS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[EWidgetType.widgetTypeTravelBook.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            f1285xd5a24482 = new int[EInfoShowType.values().length];
            try {
                f1285xd5a24482[EInfoShowType.eInfoShowTimeOfDay.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1285xd5a24482[EInfoShowType.eInfoShowDistFromStart.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1285xd5a24482[EInfoShowType.eInfoShowDistToEnd.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f1285xd5a24482[EInfoShowType.eInfoShowElevation.ordinal()] = 4;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f1285xd5a24482[EInfoShowType.eInfoShowCurrentSpeed.ordinal()] = 5;
            } catch (NoSuchFieldError e11) {
            }
            try {
                f1285xd5a24482[EInfoShowType.eInfoShowTimeOfArrival.ordinal()] = 6;
            } catch (NoSuchFieldError e12) {
            }
            try {
                f1285xd5a24482[EInfoShowType.eInfoShowDistAll.ordinal()] = 7;
            } catch (NoSuchFieldError e13) {
            }
            try {
                f1285xd5a24482[EInfoShowType.eInfoShowTimeToEnd.ordinal()] = 8;
            } catch (NoSuchFieldError e14) {
            }
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.4 */
    static class C16214 implements Callback<LangEntry[]> {
        C16214() {
        }

        public LangEntry[] getMethod() {
            return SettingsManager.GetLangFiles();
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.5 */
    static class C16225 implements Callback<SoundEntry[]> {
        C16225() {
        }

        public SoundEntry[] getMethod() {
            return SettingsManager.GetSounds();
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.6 */
    static class C16236 implements Callback<String> {
        final /* synthetic */ ESoundSettingsType val$eWarningType;

        C16236(ESoundSettingsType eSoundSettingsType) {
            this.val$eWarningType = eSoundSettingsType;
        }

        public String getMethod() {
            return SettingsManager.GetDefaultTTS(this.val$eWarningType.getValue());
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.7 */
    static class C16247 implements Callback<String> {
        final /* synthetic */ ESoundSettingsType val$eWarningType;

        C16247(ESoundSettingsType eSoundSettingsType) {
            this.val$eWarningType = eSoundSettingsType;
        }

        public String getMethod() {
            return SettingsManager.GetCustomTTS(this.val$eWarningType.getValue());
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.8 */
    static class C16258 implements Callback<Integer> {
        C16258() {
        }

        public Integer getMethod() {
            return Integer.valueOf(SettingsManager.GetUpdateNumber());
        }
    }

    /* renamed from: com.sygic.aura.settings.data.SettingsManager.9 */
    static class C16269 implements VoidCallback {
        C16269() {
        }

        public void getMethod() {
            SettingsManager.ResetUpdateNumber();
        }
    }

    public enum EColorScheme {
        Day(0),
        Night(1),
        Automatic(2);
        
        final int nValue;

        private EColorScheme(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    public enum EInfoShowType {
        eInfoShowNone(0),
        eInfoShowTimeOfDay(2),
        eInfoShowDistAll(3),
        eInfoShowDistToEnd(4),
        eInfoShowElevation(5),
        eInfoShowCurrentSpeed(6),
        eInfoShowTimeOfArrival(7),
        eInfoShowDistFromStart(8),
        eInfoShowTimeToEnd(9);
        
        final int nValue;

        private EInfoShowType(int val) {
            this.nValue = val;
        }

        public static EInfoShowType fromInt(int infoTypeVal) {
            switch (infoTypeVal) {
                case TTSConst.TTSPARAGRAPH /*2*/:
                    return eInfoShowTimeOfDay;
                case TTSConst.TTSUNICODE /*3*/:
                    return eInfoShowDistAll;
                case TTSConst.TTSXML /*4*/:
                    return eInfoShowDistToEnd;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    return eInfoShowElevation;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    return eInfoShowCurrentSpeed;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    return eInfoShowTimeOfArrival;
                case TTSConst.TTSEVT_TAG /*8*/:
                    return eInfoShowDistFromStart;
                case TTSConst.TTSEVT_PAUSE /*9*/:
                    return eInfoShowTimeToEnd;
                default:
                    return eInfoShowNone;
            }
        }

        public static int infobarToCore(int nValue) {
            switch (nValue) {
                case TTSConst.TTSMULTILINE /*1*/:
                    return eInfoShowTimeOfDay.getValue();
                case TTSConst.TTSPARAGRAPH /*2*/:
                    return eInfoShowDistFromStart.getValue();
                case TTSConst.TTSUNICODE /*3*/:
                    return eInfoShowDistToEnd.getValue();
                case TTSConst.TTSXML /*4*/:
                    return eInfoShowElevation.getValue();
                case TTSConst.TTSEVT_TEXT /*5*/:
                    return eInfoShowCurrentSpeed.getValue();
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    return eInfoShowTimeOfArrival.getValue();
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    return eInfoShowDistAll.getValue();
                case TTSConst.TTSEVT_TAG /*8*/:
                    return eInfoShowTimeToEnd.getValue();
                default:
                    return eInfoShowNone.getValue();
            }
        }

        public static int infobarFromCore(int nValue) {
            switch (AnonymousClass49.f1285xd5a24482[fromInt(nValue).ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    return 1;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    return 2;
                case TTSConst.TTSUNICODE /*3*/:
                    return 3;
                case TTSConst.TTSXML /*4*/:
                    return 4;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    return 5;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    return 6;
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    return 7;
                case TTSConst.TTSEVT_TAG /*8*/:
                    return 8;
                default:
                    return eInfoShowNone.getValue();
            }
        }

        public int getValue() {
            return this.nValue;
        }
    }

    public enum EPoiOnRoute {
        Hidden(0),
        Visible(1),
        Auto(2);
        
        final int nValue;

        private EPoiOnRoute(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    public enum ESettingsType {
        eDayNight(0),
        eTollRoads(1),
        eUnpavedRoads(2),
        eMotorway(3),
        eFerries(4),
        eCompute(5),
        eLaneAssist(6),
        eAutoZoom(7),
        eDisplayTraffic(8),
        eAvoidTraffic(9),
        eRotationLock(10),
        eZoomControls(11),
        ePower(12),
        eBattery(13),
        eBatteryBack(14),
        eDistanceUnits(15),
        eTempUnits(16),
        eTimeUnits(17),
        eCoordsUnits(18),
        eBuildings(19),
        eLandmarks(20),
        eSignposts(21),
        eStreets(22),
        eVoiceInstr(23),
        eRoadNum(24),
        eStreetName(25),
        eStreetNum(26),
        eSpeakerOutput(27),
        eFadeMusic(28),
        eSpeedcam(29),
        eSpeedcamIn(30),
        eSpeedcamOut(31),
        eNotifyTraffic(32),
        eWeatherSpeedLimit(33),
        eSpeedLimit(34),
        eSpeedLimitIn(35),
        eSpeedLimitOut(36),
        eCarSlot1(37),
        eCarSlot2(38),
        eCarSlot3(39),
        ePedSlot1(40),
        ePedSlot2(41),
        ePedSlot3(42),
        eHideTeaser(43),
        eSessionTeaser(44),
        eLoginStart(45),
        eConnectionAsk(46),
        eAccountTraffic(47),
        eAccountNewUpdates(48),
        ePoiOnRouteOnOff(49),
        eExitPoiOnRoute(50),
        eDropbox(51),
        eJunctionView(52),
        eReportIncident(53),
        eSharpCurveOnOff(54),
        eSharpCurveEasy(55),
        eSharpCurveMedium(56),
        eSharpCurveHard(57),
        eFullScreen(58),
        eAutoCloseOnOff(59),
        eAutoCloseDriving(60),
        eAutoCloseAlways(61),
        eRailway(62),
        eRailwayDistance(63),
        ePhotos(64),
        eSecondaryInstruction(65),
        eUseBluetoothHfp(66),
        eOfferParking(67),
        eNone(9999);
        
        int nValue;

        private ESettingsType(int val) {
            this.nValue = val;
        }

        public void setValue(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    public enum ESoundSettingsType {
        eNone(0),
        eSpeedCamWarnSound(1),
        eDangerTurnNotifSound(2),
        eSpeedLimWarnSound(3),
        eFriendNotifSound(4),
        eMessageNotifSound(5),
        eWarnNearRailSound(6),
        eWarnOffRoadSound(7),
        eWarnNearPosSound(8),
        eTmcNotifSound(9),
        eFriendNearNotifSound(10),
        eScoutRouteNotifSound(11);
        
        final int nValue;

        private ESoundSettingsType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    private static native boolean CanShowMap();

    private static native void ChangeMute();

    private static native void CheckLicence();

    private static native boolean EnableDebugMode(String str);

    private static native void FlushSettings();

    private static native AboutEntry GetAboutInfo();

    private static native String GetCustomTTS(int i);

    private static native String GetDefaultTTS(int i);

    private static native String GetEulaText();

    private static native LangEntry[] GetLangFiles();

    private static native long GetSavedCarPosition();

    private static native String GetSelectedLanguage();

    private static native String GetSelectedSound(int i);

    private static native VoiceEntry GetSelectedVoice();

    private static native int GetSettings(int i);

    private static native SoundEntry[] GetSounds();

    private static native int[] GetTripKilometers();

    private static native int GetUpdateNumber();

    private static native String GetVersion();

    private static native VoiceEntry[] GetVoices(boolean z);

    private static native PlaceEntry[] GetWidgetPlaces();

    private static native boolean HasDropboxSession();

    private static native boolean IsDebugEnabled();

    private static native boolean IsDefaultTTSEnabled(int i);

    private static native boolean IsFirstRun();

    private static native boolean IsMuted();

    private static native boolean IsNightModeOn();

    private static native boolean IsTTSEnabled(int i);

    private static native boolean IsTTSVoiceSelected();

    private static native void PlaySample(String str, boolean z);

    private static native void PlaySoundFile(String str);

    private static native void PlayWarnSample(String str, boolean z);

    private static native int ResetToDefault();

    private static native void ResetUpdateNumber();

    private static native void SetFirstRun(boolean z);

    private static native void SetLanguage(String str);

    private static native void SetSavedCarPosition(long j);

    private static native void SetSettings(int i, int i2);

    private static native void SetSound(String str, int i);

    private static native void SetTTSSound(String str, int i);

    private static native void SetUserMail(String str);

    private static native void SetVoice(String str, String str2, boolean z);

    private static native boolean ShowFirstRunWizard();

    private static native void StartOnlineServices();

    private static native boolean SwitchDropbox(boolean z);

    private static native void UninstallSpeedCams();

    static {
        mListeners = new HashSet();
        mLangListeners = new HashSet();
        mDebugListeners = new HashSet();
    }

    public static void registerOnCoreSettingsChangeListener(OnCoreSettingsChangeListener listener) {
        mListeners.add(listener);
    }

    public static void unregisterOnCoreSettingsChangeListener(OnCoreSettingsChangeListener listener) {
        mListeners.remove(listener);
    }

    public static void registerOnLanguageChangeListener(OnLanguageChangeListener listener) {
        mLangListeners.add(listener);
    }

    public static void unregisterOnLanguageChangeListener(OnLanguageChangeListener listener) {
        mLangListeners.remove(listener);
    }

    public static void registerOnDebugChangeListener(OnDebugChangeListener listener) {
        mDebugListeners.add(listener);
    }

    public static void unregisterOnDebugChangeListener(OnDebugChangeListener listener) {
        mDebugListeners.remove(listener);
    }

    public static int secondaryDirectionToCore(int mode) {
        switch (mode) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 500;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 0;
            default:
                return 200;
        }
    }

    public static int secondaryDirectionFromCore(int distance) {
        if (distance == 0) {
            return 2;
        }
        if (distance <= 200) {
            return 0;
        }
        return 1;
    }

    public static void initFromCore(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Resources res = context.getResources();
        DashboardPrefillHelper.prefill(prefs);
        InfobarSlotsHack.fixInfobarSettings(prefs, res);
        fixSecondaryInstruction(prefs, res);
        AvoidRouteHack.fixIt(prefs, res);
        String strFirstKey = res.getString(2131166305);
        if (nativeIsFirstRun() || prefs.getBoolean(strFirstKey, true)) {
            boolean bValue;
            boolean bluetoothHfp;
            boolean z;
            Editor editor = prefs.edit();
            editor.clear();
            editor.putBoolean(strFirstKey, false);
            if (nativeGetSettings(ESettingsType.eSpeedcam) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166755), bValue);
            if (nativeGetSettings(ESettingsType.eRailway) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166746), bValue);
            if (nativeGetSettings(ESettingsType.eSpeedLimit) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166762), bValue);
            if (nativeGetSettings(ESettingsType.eWeatherSpeedLimit) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166766), bValue);
            if (nativeGetSettings(ESettingsType.eNotifyTraffic) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166767), bValue);
            bValue = nativeGetSettings(ESettingsType.eSpeakerOutput) == 1;
            if (nativeGetSettings(ESettingsType.eUseBluetoothHfp) == 1) {
                bluetoothHfp = true;
            } else {
                bluetoothHfp = false;
            }
            String string = res.getString(2131166737);
            String str = bValue ? "0" : bluetoothHfp ? "2" : "1";
            editor.putString(string, str);
            if (nativeGetSettings(ESettingsType.eVoiceInstr) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166772), bValue);
            if (nativeGetSettings(ESettingsType.eRoadNum) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166770), bValue);
            if (nativeGetSettings(ESettingsType.eDisplayTraffic) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166302), bValue);
            if (nativeGetSettings(ESettingsType.eAvoidTraffic) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166303), bValue);
            if (nativeGetSettings(ESettingsType.eLandmarks) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166315), bValue);
            if (nativeGetSettings(ESettingsType.eSignposts) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166316), bValue);
            if (nativeGetSettings(ESettingsType.eStreets) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166314), bValue);
            if (nativeGetSettings(ESettingsType.eTollRoads) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            string = res.getString(2131166735);
            if (bValue) {
                z = false;
            } else {
                z = true;
            }
            editor.putBoolean(string, z);
            if (nativeGetSettings(ESettingsType.eUnpavedRoads) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            string = res.getString(2131166736);
            if (bValue) {
                z = false;
            } else {
                z = true;
            }
            editor.putBoolean(string, z);
            if (nativeGetSettings(ESettingsType.eMotorway) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            string = res.getString(2131166734);
            if (bValue) {
                z = false;
            } else {
                z = true;
            }
            editor.putBoolean(string, z);
            if (nativeGetSettings(ESettingsType.eFerries) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            string = res.getString(2131166733);
            if (bValue) {
                z = false;
            } else {
                z = true;
            }
            editor.putBoolean(string, z);
            if (nativeGetSettings(ESettingsType.eBatteryBack) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166265), bValue);
            if (nativeGetSettings(ESettingsType.eLaneAssist) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166298), bValue);
            if (nativeGetSettings(ESettingsType.eJunctionView) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166297), bValue);
            if (nativeGetSettings(ESettingsType.eRotationLock) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166299), bValue);
            if (nativeGetSettings(ESettingsType.eZoomControls) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166709), bValue);
            if (nativeGetSettings(ESettingsType.eFullScreen) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166289), bValue);
            if (nativeGetSettings(ESettingsType.eOfferParking) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166710), bValue);
            if (nativeGetSettings(ESettingsType.eAutoZoom) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166312), bValue);
            if (nativeGetSettings(ESettingsType.ePhotos) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166317), bValue);
            if (nativeGetSettings(ESettingsType.eLoginStart) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131165289), bValue);
            if (nativeGetSettings(ESettingsType.eConnectionAsk) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131165285), bValue);
            if (nativeGetSettings(ESettingsType.eAccountTraffic) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131165290), bValue);
            if (nativeGetSettings(ESettingsType.eAccountNewUpdates) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131165288), bValue);
            if (nativeGetSettings(ESettingsType.eDropbox) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166264), bValue);
            if (nativeGetSettings(ESettingsType.eReportIncident) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166720), bValue);
            if (nativeGetSettings(ESettingsType.eAutoCloseOnOff) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166285), bValue);
            editor.putInt(res.getString(2131166761), nativeGetSettings(ESettingsType.eSpeedcamOut) / 100);
            editor.putInt(res.getString(2131166760), nativeGetSettings(ESettingsType.eSpeedcamIn) / 100);
            editor.putInt(res.getString(2131166748), nativeGetSettings(ESettingsType.eRailwayDistance) / 100);
            editor.putInt(res.getString(2131166765), nativeGetSettings(ESettingsType.eSpeedLimitOut));
            editor.putInt(res.getString(2131166764), nativeGetSettings(ESettingsType.eSpeedLimitIn));
            editor.putString(res.getString(2131166288), Integer.toString(nativeGetSettings(ESettingsType.eDayNight)));
            editor.putString(res.getString(2131166313), Integer.toString(nativeGetSettings(ESettingsType.eBuildings)));
            editor.putString(res.getString(2131166732), Integer.toString(nativeGetSettings(ESettingsType.eCompute)));
            editor.putString(res.getString(2131166267), Integer.toString(nativeGetSettings(ESettingsType.ePower)));
            editor.putString(res.getString(2131166266), Integer.toString(nativeGetSettings(ESettingsType.eBattery)));
            editor.putString(res.getString(2131166725), Integer.toString(nativeGetSettings(ESettingsType.eDistanceUnits)));
            editor.putString(res.getString(2131166726), Integer.toString(nativeGetSettings(ESettingsType.eTempUnits)));
            editor.putString(res.getString(2131166727), Integer.toString(nativeGetSettings(ESettingsType.eTimeUnits)));
            editor.putString(res.getString(2131166724), Integer.toString(nativeGetSettings(ESettingsType.eCoordsUnits)));
            editor.putString(res.getString(2131166293), Integer.toString(EInfoShowType.infobarFromCore(nativeGetSettings(ESettingsType.ePedSlot1))));
            editor.putString(res.getString(2131166294), Integer.toString(EInfoShowType.infobarFromCore(nativeGetSettings(ESettingsType.ePedSlot2))));
            editor.putString(res.getString(2131166295), Integer.toString(EInfoShowType.infobarFromCore(nativeGetSettings(ESettingsType.ePedSlot3))));
            editor.putString(res.getString(2131166290), Integer.toString(EInfoShowType.infobarFromCore(nativeGetSettings(ESettingsType.eCarSlot1))));
            editor.putString(res.getString(2131166291), Integer.toString(EInfoShowType.infobarFromCore(nativeGetSettings(ESettingsType.eCarSlot2))));
            editor.putString(res.getString(2131166292), Integer.toString(EInfoShowType.infobarFromCore(nativeGetSettings(ESettingsType.eCarSlot3))));
            editor.putString(res.getString(2131166514), Integer.toString(nativeGetSettings(ESettingsType.ePoiOnRouteOnOff)));
            editor.putString(res.getString(2131166722), nativeGetSelectedLanguage());
            editor.putString(res.getString(2131166300), Integer.toString(secondaryDirectionFromCore(nativeGetSettings(ESettingsType.eSecondaryInstruction))));
            VoiceEntry voiceEntry = nativeGetSelectedVoice();
            editor.putString(res.getString(2131166728), voiceEntry.getLanguage());
            editor.putInt(res.getString(2131166729), voiceEntry.hashCode());
            editor.putBoolean(res.getString(2131166730), voiceEntry.isTTS());
            String strValue = SoundEntry.getSoundName(nativeGetSelectedSound(ESoundSettingsType.eSpeedCamWarnSound));
            editor.putString(res.getString(2131166757), strValue);
            editor.putString(res.getString(2131166743), strValue);
            editor.putString(res.getString(2131166749), SoundEntry.getSoundName(nativeGetSelectedSound(ESoundSettingsType.eScoutRouteNotifSound)));
            strValue = SoundEntry.getSoundName(nativeGetSelectedSound(ESoundSettingsType.eWarnNearRailSound));
            editor.putString(res.getString(2131166747), strValue);
            editor.putString(res.getString(2131166741), strValue);
            strValue = SoundEntry.getSoundName(nativeGetSelectedSound(ESoundSettingsType.eSpeedLimWarnSound));
            editor.putString(res.getString(2131166763), strValue);
            editor.putString(res.getString(2131166744), strValue);
            strValue = SoundEntry.getSoundName(nativeGetSelectedSound(ESoundSettingsType.eTmcNotifSound));
            editor.putString(res.getString(2131166768), strValue);
            editor.putString(res.getString(2131166745), strValue);
            if (nativeGetSettings(ESettingsType.eSharpCurveOnOff) == 1) {
                bValue = true;
            } else {
                bValue = false;
            }
            editor.putBoolean(res.getString(2131166750), bValue);
            editor.putInt(res.getString(2131166751), nativeGetSettings(ESettingsType.eSharpCurveEasy));
            editor.putInt(res.getString(2131166753), nativeGetSettings(ESettingsType.eSharpCurveMedium));
            editor.putInt(res.getString(2131166752), nativeGetSettings(ESettingsType.eSharpCurveHard));
            strValue = SoundEntry.getSoundName(nativeGetSelectedSound(ESoundSettingsType.eDangerTurnNotifSound));
            editor.putString(res.getString(2131166754), strValue);
            editor.putString(res.getString(2131166742), strValue);
            editor.putInt(res.getString(2131166287), nativeGetSettings(ESettingsType.eAutoCloseDriving));
            editor.putInt(res.getString(2131166286), nativeGetSettings(ESettingsType.eAutoCloseAlways));
            editor.commit();
            nativeFlushSettings();
        }
    }

    private static void fixSecondaryInstruction(SharedPreferences prefs, Resources res) {
        if (!prefs.getBoolean(SECONDARY_INSTRUCTION_FIXED, false)) {
            prefs.edit().putString(res.getString(2131166300), Integer.toString(secondaryDirectionFromCore(nativeGetSettings(ESettingsType.eSecondaryInstruction)))).putBoolean(SECONDARY_INSTRUCTION_FIXED, true).apply();
        }
    }

    private static void prefillDashboard() {
        boolean hasHome = false;
        boolean hasWork = false;
        boolean hasBlackBox = false;
        boolean hasHud = false;
        boolean hasSOS = false;
        boolean hasTravelbook = false;
        for (WidgetItem widget : WidgetManager.nativeGetWidgets()) {
            switch (AnonymousClass49.$SwitchMap$com$sygic$aura$dashboard$WidgetItem$EWidgetType[widget.getType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    hasHome = true;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    hasWork = true;
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    hasBlackBox = true;
                    break;
                case TTSConst.TTSXML /*4*/:
                    hasHud = true;
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    hasSOS = true;
                    break;
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    hasTravelbook = true;
                    break;
                default:
                    break;
            }
        }
        if (!hasHome) {
            WidgetManager.nativeAddWidgetItem(new WidgetItem(EWidgetType.widgetTypeHome, EWidgetSize.widgetSizeHalfRow));
        }
        if (!hasWork) {
            WidgetManager.nativeAddWidgetItem(new WidgetItem(EWidgetType.widgetTypeWork, EWidgetSize.widgetSizeHalfRow));
        }
        if (!hasBlackBox) {
            WidgetManager.nativeAddWidgetItem(new WidgetItem(EWidgetType.widgetTypeBlackBox, EWidgetSize.widgetSizeHalfRow));
        }
        if (!hasHud) {
            WidgetManager.nativeAddWidgetItem(new WidgetItem(EWidgetType.widgetTypeHUD, EWidgetSize.widgetSizeHalfRow));
        }
        if (!hasSOS) {
            WidgetManager.nativeAddWidgetItem(new WidgetItem(EWidgetType.widgetTypeSOS, EWidgetSize.widgetSizeHalfRow));
        }
        if (!hasTravelbook) {
            WidgetManager.nativeAddWidgetItem(new WidgetItem(EWidgetType.widgetTypeTravelBook, EWidgetSize.widgetSizeHalfRow));
        }
    }

    public static void rateApp(Context context) {
        Intent myAppLinkToMarket = new Intent("android.intent.action.VIEW", Uri.parse(ResourceManager.getGooglePlayUrl(context)));
        myAppLinkToMarket.addFlags(268435456);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            SToast.makeText(context, 2131165783, 1).show();
        }
    }

    public static void feedback(Context context) {
        Intent mailIntent = new Intent("android.intent.action.SEND");
        mailIntent.setType("message/rfc822");
        mailIntent.putExtra("android.intent.extra.SUBJECT", nativeGetAboutInfo().getSwVersion() + " - " + ResourceManager.getCoreString(context, 2131165779));
        mailIntent.putExtra("android.intent.extra.EMAIL", new String[]{FEEDBACK_EMAIL});
        mailIntent.addFlags(268435456);
        File infoFile = createInfoFile(context);
        if (infoFile != null && infoFile.exists()) {
            mailIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(infoFile));
        }
        try {
            context.startActivity(Intent.createChooser(mailIntent, ResourceManager.getCoreString(context, 2131165789)));
        } catch (ActivityNotFoundException e) {
            SToast.makeText(context, 2131165782, 1).show();
        }
    }

    private static File createInfoFile(Context ctx) {
        try {
            File result = new File(ctx.getExternalCacheDir(), "support.info");
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(result));
            byte[] data = new byte[1024];
            addFileToZip(zos, "content.info", data);
            addFileToZip(zos, "footprint.info", data);
            zos.close();
            return result;
        } catch (IOException e) {
            CrashlyticsHelper.logException(SettingsManager.class.getName(), "createInfoFile", e);
            return null;
        }
    }

    private static void addFileToZip(ZipOutputStream zipOutputStream, String fileName, byte[] buffer) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(ResourceManager.nativeGetMapPath(), fileName)), buffer.length);
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        while (true) {
            int count = bis.read(buffer, 0, buffer.length);
            if (count != -1) {
                zipOutputStream.write(buffer, 0, count);
            } else {
                zipOutputStream.closeEntry();
                bis.close();
                return;
            }
        }
    }

    public static void help(Context context) {
        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(HELP_URL));
        browserIntent.addFlags(268435456);
        try {
            context.startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            SToast.makeText(context, 2131165781, 1).show();
        }
    }

    public static void setupRatingDlg(NaviNativeActivity activity) {
        if (nativeGetSettings(ESettingsType.eSessionTeaser) != 0) {
            new RepeatingThread(new C16181(activity), 3000).start();
        }
    }

    public static VoiceEntry[] nativeGetInstalledVoices() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (VoiceEntry[]) new ObjectHandler(new C16192()).execute().get(new VoiceEntry[0]);
    }

    public static VoiceEntry[] nativeGetAvailableVoices() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (VoiceEntry[]) new ObjectHandler(new C16203()).execute().get(new VoiceEntry[0]);
    }

    public static LangEntry[] nativeGetLangFiles() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (LangEntry[]) new ObjectHandler(new C16214()).execute().get(new LangEntry[0]);
    }

    public static SoundEntry[] nativeGetSounds() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (SoundEntry[]) new ObjectHandler(new C16225()).execute().get(new SoundEntry[0]);
    }

    public static String nativeGetDefaultTTS(ESoundSettingsType eWarningType) {
        if (SygicProject.IS_PROTOTYPE) {
            return "default_tts";
        }
        return (String) new ObjectHandler(new C16236(eWarningType)).execute().get(null);
    }

    public static String nativeGetCustomTTS(ESoundSettingsType eWarningType) {
        if (SygicProject.IS_PROTOTYPE) {
            return "custom_tts";
        }
        return (String) new ObjectHandler(new C16247(eWarningType)).execute().get(null);
    }

    public static int nativeGetUpdateNumber() {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        return ((Integer) new ObjectHandler(new C16258()).execute().get(Integer.valueOf(0))).intValue();
    }

    public static void nativeResetUpdateNumber() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C16269());
        }
    }

    public static int nativeGetSettings(ESettingsType eSettingsType) {
        if (SygicProject.IS_PROTOTYPE) {
            return 1;
        }
        return ((Integer) new ObjectHandler(new AnonymousClass10(eSettingsType)).execute().get(Integer.valueOf(0))).intValue();
    }

    public static void nativeSetSettings(ESettingsType eSettingsType, boolean bValue) {
        nativeSetSettings(eSettingsType, bValue ? 1 : 0);
    }

    public static void nativeSetSettings(ESettingsType eSettingsType, int value) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass11(eSettingsType, value));
        }
        for (OnCoreSettingsChangeListener listener : mListeners) {
            listener.onCoreSettingsChanged(eSettingsType, value);
        }
    }

    public static void nativeResetToDefault() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    SettingsManager.ResetToDefault();
                }
            });
        }
    }

    public static void nativeSetLanguage(String strLangFile) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass13(strLangFile));
        }
        for (OnLanguageChangeListener listener : mLangListeners) {
            listener.onLanguageChanged(strLangFile);
        }
    }

    public static String nativeGetSelectedLanguage() {
        if (SygicProject.IS_PROTOTYPE) {
            return "english.lang";
        }
        return (String) new ObjectHandler(new Callback<String>() {
            public String getMethod() {
                return SettingsManager.GetSelectedLanguage();
            }
        }).execute().get(null);
    }

    public static String nativeGetVersion() {
        return (String) new ObjectHandler(new Callback<String>() {
            public String getMethod() {
                return SettingsManager.GetVersion();
            }
        }).execute().get(null);
    }

    public static String nativeGetSelectedLanguageName(String strLang) {
        LangEntry[] items = nativeGetLangFiles();
        if (items != null) {
            for (LangEntry item : items) {
                if (item.getFileName().equalsIgnoreCase(strLang)) {
                    return item.getLanguage();
                }
            }
        }
        return "";
    }

    public static void nativeSetVoice(String strFolder, String strVoice, boolean bTTS) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass16(strFolder, strVoice, bTTS));
        }
    }

    public static boolean nativeIsTTSVoiceSelected() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SettingsManager.IsTTSVoiceSelected());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsTTSEnabled(ESoundSettingsType eWarningType) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass18(eWarningType)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsDefaultTTSEnabled(ESoundSettingsType eWarningType) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass19(eWarningType)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static int[] nativeGetTripKilometers() {
        return SygicProject.IS_PROTOTYPE ? new int[]{100, 200} : (int[]) new ObjectHandler(new Callback<int[]>() {
            public int[] getMethod() {
                return SettingsManager.GetTripKilometers();
            }
        }).execute().get(new int[0]);
    }

    public static VoiceEntry nativeGetSelectedVoice() {
        if (SygicProject.IS_PROTOTYPE) {
            return new VoiceEntry("SVK", "SLO", "Slovak", "Jana", "Female", "", false);
        }
        return (VoiceEntry) new ObjectHandler(new Callback<VoiceEntry>() {
            public VoiceEntry getMethod() {
                return SettingsManager.GetSelectedVoice();
            }
        }).execute().get(null);
    }

    public static void nativeSetSound(String strFolder, ESoundSettingsType eWarningType) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass22(strFolder, eWarningType));
        }
    }

    public static void nativeSetTTSSound(String strMsg, ESoundSettingsType eWarningType) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass23(strMsg, eWarningType));
        }
    }

    public static String nativeGetSelectedSound(ESoundSettingsType eWarningType) {
        if (SygicProject.IS_PROTOTYPE) {
            return "beep";
        }
        return (String) new ObjectHandler(new AnonymousClass24(eWarningType)).execute().get(null);
    }

    public static void nativeUninstallSpeedCams() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    SettingsManager.UninstallSpeedCams();
                }
            });
        }
    }

    public static void nativeSetFirstRun(boolean bValue) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass26(bValue));
        }
    }

    public static String nativeGetEulaText() {
        if (SygicProject.IS_PROTOTYPE) {
            return "bla bla bla";
        }
        return (String) new ObjectHandler(new Callback<String>() {
            public String getMethod() {
                return SettingsManager.GetEulaText();
            }
        }).execute().get(null);
    }

    public static AboutEntry nativeGetAboutInfo() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (AboutEntry) new ObjectHandler(new Callback<AboutEntry>() {
            public AboutEntry getMethod() {
                return SettingsManager.GetAboutInfo();
            }
        }).execute().get(null);
    }

    public static boolean nativeIsFirstRun() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SettingsManager.IsFirstRun());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeCanShowMap() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SettingsManager.CanShowMap());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeShowFirstRunWizard() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SettingsManager.ShowFirstRunWizard());
            }
        }).execute().get(Boolean.valueOf(true))).booleanValue();
    }

    public static void nativeFlushSettings() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    SettingsManager.FlushSettings();
                }
            });
        }
    }

    public static void nativePlayWarnSample(String strData, boolean bTTS) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass33(strData, bTTS));
        }
    }

    public static void nativePlaySample(String strSamplePath, boolean bTTS) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass34(strSamplePath, bTTS));
        }
    }

    public static void nativePlaySoundFile(String strSoundPath) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass35(strSoundPath));
        }
    }

    public static void nativeStartOnlineServices() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    SettingsManager.StartOnlineServices();
                }
            });
        }
    }

    public static void nativeCheckLicence() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    SettingsManager.CheckLicence();
                }
            });
        }
    }

    public static boolean nativeIsMuted() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SettingsManager.IsMuted());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeChangeMute() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    SettingsManager.ChangeMute();
                }
            });
        }
    }

    public static void nativeSetUserMail(String strMail) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass40(strMail));
        }
    }

    public static boolean nativeSwitchDropbox(boolean bSwitchOn) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass41(bSwitchOn)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeHasDropboxSession() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SettingsManager.HasDropboxSession());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeEnableDebugMode(String strText) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        if (!((Boolean) new ObjectHandler(new AnonymousClass43(strText)).execute().get(Boolean.valueOf(false))).booleanValue()) {
            return false;
        }
        for (OnDebugChangeListener listener : mDebugListeners) {
            listener.onDebugChanged();
        }
        return true;
    }

    public static boolean nativeIsDebugEnabled() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SettingsManager.IsDebugEnabled());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static PlaceEntry[] nativeGetWidgetPlaces() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (PlaceEntry[]) new ObjectHandler(new Callback<PlaceEntry[]>() {
            public PlaceEntry[] getMethod() {
                return SettingsManager.GetWidgetPlaces();
            }
        }).execute().get(new PlaceEntry[0]);
    }

    public static LongPosition nativeGetSavedCarPosition() {
        if (SygicProject.IS_PROTOTYPE) {
            return LongPosition.Invalid;
        }
        return (LongPosition) new ObjectHandler(new Callback<LongPosition>() {
            public LongPosition getMethod() {
                return new LongPosition(SettingsManager.GetSavedCarPosition());
            }
        }).execute().get(LongPosition.Invalid);
    }

    public static boolean nativeSetSavedCarPosition(LongPosition position) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass47(position)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsNightModeOn(EColorScheme scheme) {
        if (scheme == EColorScheme.Automatic) {
            return nativeIsNightModeOn();
        }
        return scheme == EColorScheme.Night;
    }

    public static boolean nativeIsNightModeOn() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(SettingsManager.IsNightModeOn());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }
}
