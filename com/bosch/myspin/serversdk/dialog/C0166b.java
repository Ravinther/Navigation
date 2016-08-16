package com.bosch.myspin.serversdk.dialog;

import android.app.Dialog;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.dialog.b */
class C0166b implements Callback {
    final /* synthetic */ C0165a f90a;

    C0166b(C0165a c0165a) {
        this.f90a = c0165a;
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case TTSConst.TTSMULTILINE /*1*/:
                View decorView = ((Dialog) message.obj).getWindow().getDecorView();
                decorView.setTag("myspin:dialog");
                this.f90a.f84b.m324a(decorView);
                return true;
            default:
                return false;
        }
    }
}
