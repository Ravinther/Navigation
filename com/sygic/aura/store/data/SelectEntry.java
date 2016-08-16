package com.sygic.aura.store.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.sygic.aura.store.data.StoreEntry.EViewType;

public class SelectEntry extends StoreEntry {
    public static final Creator<SelectEntry> CREATOR;
    private String mName;
    private OptionItem[] mOptions;
    private OptionItem mSelectedOption;

    /* renamed from: com.sygic.aura.store.data.SelectEntry.1 */
    static class C17031 implements Creator<SelectEntry> {
        C17031() {
        }

        public SelectEntry createFromParcel(Parcel source) {
            return new SelectEntry(source);
        }

        public SelectEntry[] newArray(int size) {
            return new SelectEntry[size];
        }
    }

    public static class OptionItem implements Parcelable {
        public static final Creator<OptionItem> CREATOR;
        private String mName;
        private String mTitle;

        /* renamed from: com.sygic.aura.store.data.SelectEntry.OptionItem.1 */
        static class C17041 implements Creator<OptionItem> {
            C17041() {
            }

            public OptionItem createFromParcel(Parcel source) {
                return new OptionItem(null);
            }

            public OptionItem[] newArray(int size) {
                return new OptionItem[size];
            }
        }

        public OptionItem(String name, String title) {
            this.mName = name;
            this.mTitle = title;
        }

        public String getName() {
            return this.mName;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public String toString() {
            return this.mTitle;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mName);
            dest.writeString(this.mTitle);
        }

        private OptionItem(Parcel in) {
            this.mName = in.readString();
            this.mTitle = in.readString();
        }

        static {
            CREATOR = new C17041();
        }
    }

    protected SelectEntry(String id, String title, String iconUrl, int index, String name, OptionItem[] options, String selectedOptionName) {
        super(id, title, iconUrl, index);
        this.mName = name;
        this.mOptions = options;
        int optionIndex = findSelectedOptionIndex(selectedOptionName);
        if (optionIndex != -1) {
            this.mSelectedOption = this.mOptions[optionIndex];
        } else {
            this.mSelectedOption = new OptionItem(selectedOptionName, null);
        }
    }

    public OptionItem[] getOptions() {
        return this.mOptions;
    }

    public void setSelectedOption(OptionItem selectedOption) {
        this.mSelectedOption = selectedOption;
    }

    public OptionItem getSelectedOption() {
        return this.mSelectedOption;
    }

    public int getSelectedOptionIndex() {
        return findSelectedOptionIndex(this.mSelectedOption.getName());
    }

    public String getName() {
        return this.mName;
    }

    public EViewType getType() {
        return EViewType.TYPE_SELECT;
    }

    private int findSelectedOptionIndex(String optionName) {
        if (!(this.mOptions == null || TextUtils.isEmpty(optionName))) {
            for (int i = 0; i < this.mOptions.length; i++) {
                if (optionName.equals(this.mOptions[i].getName())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mName);
        dest.writeParcelableArray(this.mOptions, flags);
        dest.writeParcelable(this.mSelectedOption, flags);
    }

    protected SelectEntry(Parcel in) {
        super(in);
        this.mName = in.readString();
        this.mOptions = (OptionItem[]) in.readParcelableArray(OptionItem[].class.getClassLoader());
        this.mSelectedOption = (OptionItem) in.readParcelable(OptionItem.class.getClassLoader());
    }

    static {
        CREATOR = new C17031();
    }
}
