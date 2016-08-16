package com.google.android.gms.analytics.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Command implements Parcelable {
    @Deprecated
    public static final Creator<Command> CREATOR;
    private String mValue;
    private String zzMI;
    private String zzwj;

    /* renamed from: com.google.android.gms.analytics.internal.Command.1 */
    static class C06141 implements Creator<Command> {
        C06141() {
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return zzr(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return zzab(x0);
        }

        @Deprecated
        public Command[] zzab(int i) {
            return new Command[i];
        }

        @Deprecated
        public Command zzr(Parcel parcel) {
            return new Command(parcel);
        }
    }

    static {
        CREATOR = new C06141();
    }

    @Deprecated
    Command(Parcel in) {
        readFromParcel(in);
    }

    @Deprecated
    private void readFromParcel(Parcel in) {
        this.zzwj = in.readString();
        this.zzMI = in.readString();
        this.mValue = in.readString();
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.zzwj;
    }

    public String getValue() {
        return this.mValue;
    }

    @Deprecated
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.zzwj);
        out.writeString(this.zzMI);
        out.writeString(this.mValue);
    }
}
