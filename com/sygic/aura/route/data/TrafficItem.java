package com.sygic.aura.route.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.resources.ResourceManager;
import loquendo.tts.engine.TTSConst;

public class TrafficItem implements Parcelable {
    public static final Creator<TrafficItem> CREATOR;
    private final AlternativeTraffic mAlternativeTraffic;
    private final int mDelay;
    private final TrafficLevel mLevel;
    private final boolean mLoading;
    private final String mMessage;

    /* renamed from: com.sygic.aura.route.data.TrafficItem.1 */
    static class C15231 implements Creator<TrafficItem> {
        C15231() {
        }

        public TrafficItem createFromParcel(Parcel source) {
            return new TrafficItem(null);
        }

        public TrafficItem[] newArray(int size) {
            return new TrafficItem[size];
        }
    }

    /* renamed from: com.sygic.aura.route.data.TrafficItem.2 */
    static /* synthetic */ class C15242 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel;

        static {
            $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel = new int[TrafficLevel.values().length];
            try {
                $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[TrafficLevel.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[TrafficLevel.LOW.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[TrafficLevel.MEDIUM.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[TrafficLevel.HIGH.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static class AlternativeTraffic implements Parcelable {
        public static final Creator<AlternativeTraffic> CREATOR;
        private int distanceDiff;
        private int timeDiff;

        /* renamed from: com.sygic.aura.route.data.TrafficItem.AlternativeTraffic.1 */
        static class C15251 implements Creator<AlternativeTraffic> {
            C15251() {
            }

            public AlternativeTraffic createFromParcel(Parcel source) {
                return new AlternativeTraffic(null);
            }

            public AlternativeTraffic[] newArray(int size) {
                return new AlternativeTraffic[size];
            }
        }

        public int getTimeDiff() {
            return this.timeDiff;
        }

        public int getDistanceDiff() {
            return this.distanceDiff;
        }

        public AlternativeTraffic setTimeDiff(int timeDiff) {
            this.timeDiff = timeDiff;
            return this;
        }

        public AlternativeTraffic setDistanceDiff(int distanceDiff) {
            this.distanceDiff = distanceDiff;
            return this;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.timeDiff);
            dest.writeInt(this.distanceDiff);
        }

        private AlternativeTraffic(Parcel in) {
            this.timeDiff = in.readInt();
            this.distanceDiff = in.readInt();
        }

        static {
            CREATOR = new C15251();
        }
    }

    public enum TrafficLevel {
        NONE,
        LOW,
        MEDIUM,
        HIGH
    }

    TrafficItem(int delay, int level, boolean loading, String message) {
        this.mDelay = delay;
        this.mLevel = TrafficLevel.values()[level];
        this.mMessage = message;
        this.mLoading = loading;
        this.mAlternativeTraffic = new AlternativeTraffic();
    }

    public void setAlternativeTraffic(AlternativeTraffic altTraffic) {
        setAlternativeTraffic(altTraffic.getTimeDiff(), altTraffic.getDistanceDiff());
    }

    public void setAlternativeTraffic(int timeDiff, int distanceDiff) {
        this.mAlternativeTraffic.setTimeDiff(timeDiff);
        this.mAlternativeTraffic.setDistanceDiff(distanceDiff);
    }

    public int getDelay() {
        return this.mDelay;
    }

    public CharSequence getDelayText() {
        return (this.mDelay > 0 ? "+" : "") + ResourceManager.nativeFormatTimeSpanToShortWords((long) this.mDelay);
    }

    public TrafficLevel getLevel() {
        return this.mLevel;
    }

    public boolean isLoading() {
        return this.mLoading;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public AlternativeTraffic getAlternativeTraffic() {
        return this.mAlternativeTraffic;
    }

    public int getLevelColor() {
        switch (C15242.$SwitchMap$com$sygic$aura$route$data$TrafficItem$TrafficLevel[this.mLevel.ordinal()]) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 2131558479;
            case TTSConst.TTSUNICODE /*3*/:
                return 2131558480;
            case TTSConst.TTSXML /*4*/:
                return 2131558478;
            default:
                return 2131558481;
        }
    }

    public int hashCode() {
        return ((((this.mDelay + 31) * 31) + this.mLevel.ordinal()) * 31) + (this.mMessage == null ? 0 : this.mMessage.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TrafficItem other = (TrafficItem) obj;
        if (this.mDelay != other.mDelay || this.mLevel != other.mLevel || this.mLoading != other.mLoading) {
            return false;
        }
        if (this.mMessage == null) {
            if (other.mMessage != null) {
                return false;
            }
            return true;
        } else if (this.mMessage.equals(other.mMessage)) {
            return true;
        } else {
            return false;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mDelay);
        dest.writeInt(this.mLevel == null ? -1 : this.mLevel.ordinal());
        dest.writeByte(this.mLoading ? (byte) 1 : (byte) 0);
        dest.writeString(this.mMessage);
        dest.writeParcelable(this.mAlternativeTraffic, flags);
    }

    private TrafficItem(Parcel in) {
        this.mDelay = in.readInt();
        int tmpMLevel = in.readInt();
        this.mLevel = tmpMLevel == -1 ? null : TrafficLevel.values()[tmpMLevel];
        this.mLoading = in.readByte() != null;
        this.mMessage = in.readString();
        this.mAlternativeTraffic = (AlternativeTraffic) in.readParcelable(AlternativeTraffic.class.getClassLoader());
    }

    static {
        CREATOR = new C15231();
    }
}
