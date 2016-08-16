package com.sygic.aura.feature.phone;

import android.app.Activity;
import android.content.Context;
import com.sygic.aura.utils.Utils;

public class ContactsReader extends ContactsReaderInterface {
    ContactsReaderInterface delegate;

    ContactsReader(Context ctx) {
        if (Utils.getAndroidVersion() < 5) {
            this.delegate = new ContactsReaderBelowECLAIR((Activity) ctx);
        } else {
            this.delegate = new ContactsReaderECLAIRAndAbove(ctx);
        }
    }

    public int GetCount() {
        return this.delegate.GetCount();
    }

    public boolean Reset() {
        return this.delegate.Reset();
    }

    public String ReadNext() {
        return this.delegate.ReadNext();
    }

    public String ReadPhoto(int nPhotoId) {
        return this.delegate.ReadPhoto(nPhotoId);
    }

    public String GetContact(String strContactId) {
        return this.delegate.GetContact(strContactId);
    }
}
