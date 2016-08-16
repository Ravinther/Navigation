package com.sygic.aura.feature.phone;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.sygic.aura.feature.ResultListener;

public abstract class LowPhoneFeature implements ResultListener {
    protected ContactsReader mContacts;
    protected Context mCtx;
    protected TelephonyManager mTelephonyManager;

    public abstract int getContactsCount();

    public abstract boolean hasNetwork();

    public abstract boolean isRoaming();

    public abstract void makeCall(String str);

    public abstract void makeCall(String str, boolean z);

    public abstract String readContact(int i);

    public abstract String readContactPhoto(int i);

    public abstract String readNextContact();

    public abstract boolean resetContacts();

    public abstract void sendSms(String str, String str2);

    protected LowPhoneFeature() {
    }

    protected LowPhoneFeature(Context context) {
        this.mCtx = context;
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
        this.mContacts = new ContactsReader(context);
    }

    public static LowPhoneFeature createInstance(Context context) {
        return new LowPhoneFeatureBase(context);
    }
}
