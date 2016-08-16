package com.bosch.myspin.serversdk.service.client;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.SearchView;

/* renamed from: com.bosch.myspin.serversdk.service.client.f */
class C0189f implements OnClickListener {
    final /* synthetic */ View f167a;
    final /* synthetic */ EditText f168b;
    final /* synthetic */ C0188e f169c;

    C0189f(C0188e c0188e, View view, EditText editText) {
        this.f169c = c0188e;
        this.f167a = view;
        this.f168b = editText;
    }

    public void onClick(View view) {
        ((SearchView) this.f167a).clearFocus();
        this.f168b.requestFocus();
    }
}
