package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class ButtonEntry extends StoreEntry implements Parcelable {
    public static final Creator<ButtonEntry> CREATOR;
    private final EButtonAction mAction;
    private final String mUrl;

    /* renamed from: com.sygic.aura.store.data.ButtonEntry.1 */
    static class C16941 implements Creator<ButtonEntry> {
        C16941() {
        }

        public ButtonEntry createFromParcel(Parcel source) {
            return new ButtonEntry(source);
        }

        public ButtonEntry[] newArray(int size) {
            return new ButtonEntry[size];
        }
    }

    public enum EButtonAction {
        BtnNone,
        BtnList,
        BtnProduct,
        BtnActivate,
        BtnRestore,
        BtnActivateAndRestore,
        BtnUrl,
        BtnShowMap,
        BtnBuy
    }

    public ButtonEntry(String id, String title, String iconUrl, int action, String url, int index) {
        super(id, title, iconUrl, index);
        this.mAction = EButtonAction.values()[action];
        if (this.mAction.ordinal() != action) {
            throw new IllegalStateException("Enum value mismatch");
        } else if (this.mAction == EButtonAction.BtnUrl) {
            this.mUrl = ResourceManager.sanitizeUrl(url);
        } else {
            this.mUrl = url;
        }
    }

    public EButtonAction getAction() {
        return this.mAction;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public EViewType getType() {
        return EViewType.TYPE_BUTTON;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mAction == null ? -1 : this.mAction.ordinal());
        dest.writeString(this.mUrl);
    }

    protected ButtonEntry(Parcel in) {
        super(in);
        int tmpMAction = in.readInt();
        this.mAction = tmpMAction == -1 ? null : EButtonAction.values()[tmpMAction];
        this.mUrl = in.readString();
    }

    static {
        CREATOR = new C16941();
    }
}
