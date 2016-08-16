package com.sygic.aura.route.data;

import com.sygic.aura.data.LongPosition;
import com.sygic.aura.resources.ResourceManager;
import loquendo.tts.engine.TTSConst;

public class SpeedCamItem {
    private final long INVALID_SPEED;
    String mCamDescription;
    LongPosition mCamPosition;
    LongPosition mCamPositionPaar;
    long mCamSpeed;
    ERadarType mCamType;

    /* renamed from: com.sygic.aura.route.data.SpeedCamItem.1 */
    static /* synthetic */ class C15221 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType;

        static {
            $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType = new int[ERadarType.values().length];
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_STATIC_SPEED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_SEMIMOBILE_SPEED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_MOBILE_SPEED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_STATIC_RED_LIGHT_SPEED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_STATIC_RED_LIGHT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_MOBILE_RED_LIGHT.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_STATIC_AVERAGE_SPEED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_MOBILE_AVERAGE_SPEED.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.FAV_COPS_PLACE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.DANGEROUS_PLACE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.CONGESTION.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.CLOSURE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.SCHOOLZONE.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.INFO_CAMERA.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.WEIGHT_CHECK.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.DISTANCE_CHECK.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.RADAR_UNKNOWN.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
        }
    }

    public enum ERadarType {
        RADAR_UNKNOWN(-1),
        RADAR_STATIC_SPEED(1),
        RADAR_STATIC_RED_LIGHT(2),
        RADAR_SEMIMOBILE_SPEED(3),
        RADAR_STATIC_AVERAGE_SPEED(4),
        RADAR_MOBILE_SPEED(5),
        RADAR_STATIC_RED_LIGHT_SPEED(6),
        RADAR_MOBILE_RED_LIGHT(7),
        RADAR_MOBILE_AVERAGE_SPEED(8),
        FAV_COPS_PLACE(9),
        INFO_CAMERA(10),
        DANGEROUS_PLACE(11),
        CONGESTION(12),
        WEIGHT_CHECK(13),
        DISTANCE_CHECK(14),
        CLOSURE(15),
        SCHOOLZONE(16);
        
        private final int mIntValue;

        private ERadarType(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        public static ERadarType fromInt(int value) {
            for (ERadarType eType : values()) {
                if (eType.getValue() == value) {
                    return eType;
                }
            }
            return RADAR_UNKNOWN;
        }
    }

    SpeedCamItem(int camType, long camPos, long camPaarPos, long camSpeed, String camDescription) {
        this.INVALID_SPEED = -1;
        this.mCamType = ERadarType.fromInt(camType);
        this.mCamPosition = new LongPosition(camPos);
        this.mCamPositionPaar = new LongPosition(camPaarPos);
        this.mCamSpeed = camSpeed;
        this.mCamDescription = camDescription;
    }

    public LongPosition getCamPosition() {
        return this.mCamPosition;
    }

    public LongPosition getCamPositionPaar() {
        return this.mCamPositionPaar;
    }

    public ERadarType getCamType() {
        return this.mCamType;
    }

    public String getCamSpeed() {
        return this.mCamSpeed == -1 ? "" : ResourceManager.nativeFormatSpeed((double) this.mCamSpeed, false, true);
    }

    public String getCamDescription() {
        return this.mCamDescription;
    }

    public static int getIconFromSubType(int subType) {
        switch (C15221.$SwitchMap$com$sygic$aura$route$data$SpeedCamItem$ERadarType[ERadarType.fromInt(subType).ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSUNICODE /*3*/:
                return 2131166163;
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_TEXT /*5*/:
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return 2131166173;
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
            case TTSConst.TTSEVT_TAG /*8*/:
                return 2131166164;
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return 2131166160;
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                return 2131166159;
            case TTSConst.TTSEVT_NOTSENT /*12*/:
                return 2131166158;
            case TTSConst.TTSEVT_AUDIO /*13*/:
                return 2131166166;
            default:
                return 2131166161;
        }
    }
}
