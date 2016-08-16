package com.sygic.aura.sos.data;

import android.text.TextUtils;

public class EmergencyNumbers {
    private String mAmbulance;
    private String mFiremen;
    private String mPolice;

    public static class Holder {
        private int mIconId;
        private String mPhoneNumber;
        private int mTextId;

        public int getTextId() {
            return this.mTextId;
        }

        public void setTextId(int textId) {
            this.mTextId = textId;
        }

        public int getIconId() {
            return this.mIconId;
        }

        public void setIconId(int iconId) {
            this.mIconId = iconId;
        }

        public String getPhoneNumber() {
            return this.mPhoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.mPhoneNumber = phoneNumber;
        }
    }

    public EmergencyNumbers(String ambulance, String firemen, String police) {
        this.mAmbulance = null;
        this.mFiremen = null;
        this.mPolice = null;
        this.mAmbulance = ambulance;
        this.mFiremen = firemen;
        this.mPolice = police;
    }

    public String getAmbulance() {
        return this.mAmbulance;
    }

    public String getFiremen() {
        return this.mFiremen;
    }

    public String getPolice() {
        return this.mPolice;
    }

    public boolean hasNumber() {
        return (TextUtils.isEmpty(this.mAmbulance) && TextUtils.isEmpty(this.mFiremen) && TextUtils.isEmpty(this.mPolice)) ? false : true;
    }

    public boolean onlyOneNumber() {
        return isSameOrNull(this.mAmbulance, this.mFiremen) && isSameOrNull(this.mAmbulance, this.mPolice) && isSameOrNull(this.mFiremen, this.mPolice);
    }

    public String getOnlyNumber() {
        if (!TextUtils.isEmpty(this.mAmbulance)) {
            return this.mAmbulance;
        }
        if (!TextUtils.isEmpty(this.mFiremen)) {
            return this.mFiremen;
        }
        if (TextUtils.isEmpty(this.mPolice)) {
            return "";
        }
        return this.mPolice;
    }

    private boolean isSameOrNull(String first, String second) {
        return TextUtils.isEmpty(first) || TextUtils.isEmpty(second) || TextUtils.equals(first, second);
    }
}
