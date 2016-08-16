package com.sygic.aura.feature.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.sygic.aura.SygicMain;

/* compiled from: LowPhoneFeature */
class LowPhoneFeatureBase extends LowPhoneFeature {
    protected LowPhoneFeatureBase() {
    }

    protected LowPhoneFeatureBase(Context context) {
        super(context);
    }

    public void makeCall(String strNumber) {
        makeCall(strNumber, true);
    }

    public void makeCall(String strNumber, boolean needResult) {
        if (strNumber != null) {
            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel: " + strNumber));
            Activity activity = SygicMain.getInstance().getFeature().getActivity();
            if (activity != null) {
                startActivity(activity, intent, needResult, 220);
            } else {
                startActivity(this.mCtx, intent, false, 0);
                throw new IllegalStateException("Activity doesn't exists in " + LowPhoneFeature.class.getName() + ".makeCall()");
            }
        }
    }

    private void startActivity(Context context, Intent intent, boolean needResult, int requestCode) {
        if (needResult) {
            ((Activity) context).startActivityForResult(intent, requestCode);
        } else {
            context.startActivity(intent);
        }
    }

    public void sendSms(String strNumber, String strText) {
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + strNumber));
        intent.putExtra("sms_body", strText);
        this.mCtx.startActivity(intent);
    }

    public boolean hasNetwork() {
        if (this.mTelephonyManager == null) {
            return false;
        }
        String strNetOp = this.mTelephonyManager.getNetworkOperator();
        if (strNetOp == null || strNetOp.length() <= 0) {
            return false;
        }
        return true;
    }

    public boolean isRoaming() {
        return this.mTelephonyManager.isNetworkRoaming();
    }

    public int getContactsCount() {
        if (this.mContacts != null) {
            return this.mContacts.GetCount();
        }
        return 0;
    }

    public boolean resetContacts() {
        if (this.mContacts != null) {
            return this.mContacts.Reset();
        }
        return false;
    }

    public String readNextContact() {
        if (this.mContacts != null) {
            return this.mContacts.ReadNext();
        }
        return null;
    }

    public String readContact(int nContactId) {
        if (this.mContacts != null) {
            return this.mContacts.GetContact(String.valueOf(nContactId));
        }
        return null;
    }

    public String readContactPhoto(int nPhotoId) {
        if (this.mContacts != null) {
            return this.mContacts.ReadPhoto(nPhotoId);
        }
        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        switch (requestCode) {
        }
    }
}
