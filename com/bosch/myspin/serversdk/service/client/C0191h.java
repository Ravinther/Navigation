package com.bosch.myspin.serversdk.service.client;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import com.bosch.myspin.serversdk.utils.C0237f;
import com.bosch.myspin.serversdk.utils.Logger;

/* renamed from: com.bosch.myspin.serversdk.service.client.h */
class C0191h implements OnFocusChangeListener {
    final /* synthetic */ C0188e f171a;

    C0191h(C0188e c0188e) {
        this.f171a = c0188e;
    }

    public void onFocusChange(View view, boolean z) {
        if (!z) {
            this.f171a.m194b();
        } else if (view instanceof EditText) {
            this.f171a.f160g = (EditText) view;
            Logger.logDebug(C0188e.f154a, "KeyboardHandler/show keyboard on focus");
            this.f170a.m186f();
        }
        OnFocusChangeListener b = C0237f.m369a().m371b(view);
        if (b != null) {
            b.onFocusChange(view, z);
        }
    }
}
