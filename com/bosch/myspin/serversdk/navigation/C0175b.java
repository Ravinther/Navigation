package com.bosch.myspin.serversdk.navigation;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import com.bosch.myspin.serversdk.NavigationManager;
import com.bosch.myspin.serversdk.utils.C0236e;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

/* renamed from: com.bosch.myspin.serversdk.navigation.b */
public class C0175b extends NavigationManager {
    private static final LogComponent f135a;
    private static C0175b f136b;
    private C0172a f137c;
    private boolean f138d;
    private Context f139e;
    private ServiceConnection f140f;

    static {
        f135a = LogComponent.NavigateTo;
    }

    private C0175b(Context context) {
        this.f140f = new C0176c(this);
        if (context == null) {
            throw new IllegalArgumentException("MySpinNavigationManager: Context must not be null");
        }
        this.f139e = context;
    }

    public static C0175b m135a(Context context) {
        if (f136b == null) {
            f136b = new C0175b(context);
        }
        return f136b;
    }

    public void m138a() {
        if (this.f137c == null || !this.f138d) {
            m137c();
        }
    }

    private void m137c() {
        try {
            if (this.f139e.bindService(C0236e.m368a(this.f139e, new Intent("com.bosch.myspin.ACTION_BIND_NAVIGATION_INTERFACE")), this.f140f, 1)) {
                this.f138d = true;
            } else {
                this.f138d = false;
            }
        } catch (Throwable e) {
            this.f138d = false;
            Logger.logWarning(f135a, "MySpinNavigationManager/Cant bind navigate to service, make sure that only one launcher app is installed", e);
        } catch (Throwable e2) {
            Logger.logWarning(f135a, "MySpinNavigationManager/Cant bind mySPIN service, make sure that a launcher app is installed.", e2);
            this.f138d = false;
        }
    }
}
