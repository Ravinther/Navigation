package com.bosch.myspin.serversdk.service.client;

import android.os.Bundle;
import android.os.RemoteException;
import com.bosch.myspin.serversdk.service.C0181b.C0183a;
import com.bosch.myspin.serversdk.utils.Logger;

/* renamed from: com.bosch.myspin.serversdk.service.client.k */
class C0195k extends C0183a {
    final /* synthetic */ C0194j f233a;

    C0195k(C0194j c0194j) {
        this.f233a = c0194j;
    }

    public void m267a() throws RemoteException {
        this.f233a.m249v();
    }

    public void m270a(boolean z) throws RemoteException {
        if (z) {
            this.f233a.m240r();
        } else {
            this.f233a.m243s();
        }
    }

    public void m271a(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i) throws RemoteException {
        this.f233a.f230x.post(new C0196l(this, iArr, iArr2, iArr3, iArr4, i));
    }

    public void m272b() throws RemoteException {
        this.f233a.f230x.post(new C0197m(this));
    }

    public void m274c() throws RemoteException {
        this.f233a.f230x.post(new C0198n(this));
    }

    public void m269a(Bundle bundle) throws RemoteException {
        if (bundle != null) {
            Logger.logDebug(C0194j.f182a, "MySpinServiceClient/onMySpinClientDataChanged: " + bundle.size());
        } else {
            Logger.logDebug(C0194j.f182a, "MySpinServiceClient/onMySpinClientDataChanged : null");
        }
        if (this.f233a.f197P == null) {
            this.f233a.f197P = new Bundle();
        }
        if (bundle != null) {
            this.f233a.f197P.putAll(bundle);
            this.f233a.m211a(bundle);
        }
    }

    public void m268a(int i) throws RemoteException {
        this.f233a.f230x.post(new C0199o(this, i));
    }

    public void m273b(boolean z) throws RemoteException {
        Logger.logDebug(C0194j.f182a, "MySpinServiceClient/onRestrictionDidUpdate(" + z + ") ");
        if (this.f233a.f199R != null) {
            this.f233a.f199R.onReceiveAppIsRestricted(z);
        }
    }
}
