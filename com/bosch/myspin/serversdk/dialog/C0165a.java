package com.bosch.myspin.serversdk.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Handler;
import android.os.Message;
import com.bosch.myspin.serversdk.service.client.C0215x;
import com.bosch.myspin.serversdk.uielements.keyboardinterface.KeyboardRegister;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.bosch.myspin.serversdk.window.C0265a;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* renamed from: com.bosch.myspin.serversdk.dialog.a */
public class C0165a implements OnDismissListener, OnShowListener {
    private static final LogComponent f83a;
    private C0215x f84b;
    private ArrayList<Dialog> f85c;
    private C0265a f86d;
    private C0167c f87e;
    private ArrayList<C0164a> f88f;
    private Handler f89g;

    /* renamed from: com.bosch.myspin.serversdk.dialog.a.a */
    class C0164a {
        private WeakReference<Dialog> f80b;
        private WeakReference<OnShowListener> f81c;
        private WeakReference<OnDismissListener> f82d;

        public WeakReference<Dialog> m98a() {
            return this.f80b;
        }
    }

    static {
        f83a = LogComponent.UI;
    }

    public C0165a(int i, int i2) {
        this.f85c = new ArrayList();
        this.f88f = new ArrayList();
        this.f89g = new Handler(new C0166b(this));
        this.f84b = C0215x.m320a();
        this.f87e = new C0167c(i, i2);
        this.f86d = new C0265a();
        this.f86d.m472a(this.f87e);
    }

    public void m104a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("preferred width can't be < 0");
        } else if (i2 < 0) {
            throw new IllegalArgumentException("preferred height can't be < 0");
        } else {
            this.f87e.m108a(i);
            this.f87e.m110b(i2);
        }
    }

    public void m103a() {
        this.f86d.m470a();
    }

    public void m105b() {
        if (this.f85c.size() > 0) {
            ((Dialog) this.f85c.get(this.f85c.size() - 1)).dismiss();
        }
    }

    public boolean m106c() {
        return this.f85c.size() > 0;
    }

    public void onShow(DialogInterface dialogInterface) {
        if (dialogInterface instanceof Dialog) {
            Dialog dialog = (Dialog) dialogInterface;
            m100a(dialog);
            if (!this.f85c.contains(dialog)) {
                this.f85c.add(dialog);
            }
            KeyboardRegister.getInstance().onHideRequest();
            Iterator it = m102c(dialog).iterator();
            while (it.hasNext()) {
                OnShowListener onShowListener = (OnShowListener) ((C0164a) it.next()).f81c.get();
                if (onShowListener != null) {
                    onShowListener.onShow(dialog);
                }
            }
        }
    }

    private void m100a(Dialog dialog) {
        if (dialog != null) {
            dialog.setCancelable(false);
            this.f86d.m471a(dialog.getWindow(), dialog.hashCode());
            Message obtainMessage = this.f89g.obtainMessage(1);
            obtainMessage.obj = dialog;
            this.f89g.sendMessage(obtainMessage);
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (dialogInterface instanceof Dialog) {
            Dialog dialog = (Dialog) dialogInterface;
            this.f84b.m327c(dialog.getWindow().getDecorView().getRootView());
            this.f86d.m474c(dialog.getWindow(), dialogInterface.hashCode());
            this.f85c.remove(dialog);
            Iterator it = m102c(dialog).iterator();
            while (it.hasNext()) {
                OnDismissListener onDismissListener = (OnDismissListener) ((C0164a) it.next()).f82d.get();
                if (onDismissListener != null) {
                    onDismissListener.onDismiss(dialog);
                }
            }
        }
    }

    private void m101b(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(this);
        dialog.setOnDismissListener(this);
        if (this.f85c.contains(dialog)) {
            m100a(dialog);
        }
    }

    private ArrayList<C0164a> m102c(Dialog dialog) {
        ArrayList<C0164a> arrayList = new ArrayList();
        Iterator it = this.f88f.iterator();
        while (it.hasNext()) {
            C0164a c0164a = (C0164a) it.next();
            WeakReference a = c0164a.m98a();
            if (a.get() != null && ((Dialog) a.get()).equals(dialog)) {
                arrayList.add(c0164a);
            }
        }
        return arrayList;
    }

    public void m107d() {
        Logger.logDebug(f83a, "DialogHandler/handleDialogsOnConnection: registered dialogs = " + this.f88f.size());
        Collection arrayList = new ArrayList();
        Iterator it = this.f88f.iterator();
        while (it.hasNext()) {
            C0164a c0164a = (C0164a) it.next();
            Dialog dialog = (Dialog) c0164a.m98a().get();
            if (dialog != null) {
                m101b(dialog);
            } else {
                arrayList.add(c0164a);
            }
        }
        this.f88f.removeAll(arrayList);
    }
}
