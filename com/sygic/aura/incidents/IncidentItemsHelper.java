package com.sygic.aura.incidents;

import com.sygic.aura.map.MapControlsManager;
import java.util.Arrays;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class IncidentItemsHelper {
    public static List<String> sCountriesWithPoliceForbidden;
    public static List<String> sCountriesWithSpeedcamsForbidden;

    /* renamed from: com.sygic.aura.incidents.IncidentItemsHelper.1 */
    static /* synthetic */ class C12841 {
        static final /* synthetic */ int[] f1260xef6b87df;

        static {
            f1260xef6b87df = new int[IncidentItemType.values().length];
            try {
                f1260xef6b87df[IncidentItemType.POLICE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1260xef6b87df[IncidentItemType.CAMERA.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1260xef6b87df[IncidentItemType.TRAFFIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1260xef6b87df[IncidentItemType.ACCIDENT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1260xef6b87df[IncidentItemType.CLOSURE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1260xef6b87df[IncidentItemType.SCHOOL.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public enum IncidentItemType {
        CAMERA(1),
        POLICE(9),
        TRAFFIC(12),
        ACCIDENT(11),
        CLOSURE(15),
        SCHOOL(16);
        
        private final int id;

        private IncidentItemType(int id) {
            this.id = id;
        }

        public int getValue() {
            return this.id;
        }
    }

    public static class ReportResult {
        private boolean mCanReport;
        private int mReason;

        public ReportResult(boolean canReport, int reason) {
            this.mCanReport = true;
            this.mReason = 0;
            this.mCanReport = canReport;
            this.mReason = reason;
        }

        public ReportResult() {
            this.mCanReport = true;
            this.mReason = 0;
        }

        public boolean canReport() {
            return this.mCanReport;
        }

        public int reason() {
            return this.mReason;
        }
    }

    private static ReportResult reportProhibited(List<String> prohibitedCountries, int reason) {
        String strIso = MapControlsManager.nativeGetActualPositionCountryIso();
        if (strIso == null || !prohibitedCountries.contains(strIso.toLowerCase())) {
            return new ReportResult();
        }
        return new ReportResult(false, reason);
    }

    public static ReportResult canReportType(IncidentItemType type) {
        switch (C12841.f1260xef6b87df[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return reportProhibited(sCountriesWithPoliceForbidden, 2131165578);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return reportProhibited(sCountriesWithSpeedcamsForbidden, 2131165926);
            default:
                return new ReportResult();
        }
    }

    static {
        sCountriesWithSpeedcamsForbidden = Arrays.asList(new String[]{"che", "lie"});
        sCountriesWithPoliceForbidden = Arrays.asList(new String[]{"che", "lie"});
    }

    public static int getTitleResId(IncidentItemType type) {
        switch (C12841.f1260xef6b87df[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2131165957;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131165953;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131165962;
            case TTSConst.TTSXML /*4*/:
                return 2131165951;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return 2131165955;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return 2131165960;
            default:
                return 0;
        }
    }

    public static int getReportMessage(IncidentItemType type) {
        switch (C12841.f1260xef6b87df[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2131165958;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131165954;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131165963;
            case TTSConst.TTSXML /*4*/:
                return 2131165952;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return 2131165956;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return 2131165961;
            default:
                return 0;
        }
    }

    public static int getIconResId(IncidentItemType type) {
        switch (C12841.f1260xef6b87df[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2131034335;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131034333;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131034337;
            case TTSConst.TTSXML /*4*/:
                return 2131034332;
            case TTSConst.TTSEVT_TEXT /*5*/:
                return 2131034334;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return 2131034336;
            default:
                return 0;
        }
    }
}
