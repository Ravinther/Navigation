package com.bosch.myspin.serversdk.service.client;

import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import com.bosch.myspin.serversdk.utils.C0237f;
import com.bosch.myspin.serversdk.utils.Logger;

/* renamed from: com.bosch.myspin.serversdk.service.client.g */
class C0190g implements OnTouchListener {
    final /* synthetic */ C0188e f170a;

    C0190g(C0188e c0188e) {
        this.f170a = c0188e;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.isFocusableInTouchMode()) {
            view.requestFocus();
            if (motionEvent.getAction() == 1 && (view instanceof EditText)) {
                this.f171a.f160g = (EditText) view;
                Logger.logDebug(C0188e.f154a, "KeyboardHandler/show keyboard on touch");
                this.f170a.m186f();
            }
        } else {
            this.f170a.m194b();
        }
        if (this.f170a.f160g != null) {
            Layout layout = this.f170a.f160g.getLayout();
            if (layout != null) {
                int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical((int) motionEvent.getY()), motionEvent.getX() - ((float) this.f170a.f160g.getCompoundPaddingLeft()));
                this.f170a.f160g.setSelection(offsetForHorizontal);
                if (offsetForHorizontal == 0 && this.f170a.f157d != null && this.f170a.f157d.getType() == 1001) {
                    this.f170a.f157d.setType(1002);
                }
            }
        }
        OnTouchListener a = C0237f.m369a().m370a(view);
        if (a != null) {
            a.onTouch(view, motionEvent);
        }
        return true;
    }
}
