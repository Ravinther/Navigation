package com.infinario.android.infinariosdk;

import android.os.RemoteException;
import java.lang.reflect.InvocationTargetException;

public class IabProxy {
    private Object proxy;
    private Class<?> stub;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public IabProxy(android.os.IBinder r8) {
        /*
        r7 = this;
        r6 = 0;
        r7.<init>();
        r1 = "com.android.vending.billing.IInAppBillingService$Stub";
        r1 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r7.stub = r1;	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r1 = r7.stub;	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r2 = "asInterface";
        r3 = 1;
        r3 = new java.lang.Class[r3];	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r4 = 0;
        r5 = android.os.IBinder.class;
        r3[r4] = r5;	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r0 = r1.getMethod(r2, r3);	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r1 = 0;
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r3 = 0;
        r2[r3] = r8;	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r1 = r0.invoke(r1, r2);	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
        r7.proxy = r1;	 Catch:{ ClassNotFoundException -> 0x002c, NoSuchMethodException -> 0x0034, IllegalAccessException -> 0x0032, InvocationTargetException -> 0x0036 }
    L_0x002b:
        return;
    L_0x002c:
        r1 = move-exception;
    L_0x002d:
        r7.stub = r6;
        r7.proxy = r6;
        goto L_0x002b;
    L_0x0032:
        r1 = move-exception;
        goto L_0x002d;
    L_0x0034:
        r1 = move-exception;
        goto L_0x002d;
    L_0x0036:
        r1 = move-exception;
        goto L_0x002d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.infinario.android.infinariosdk.IabProxy.<init>(android.os.IBinder):void");
    }

    public boolean isLoaded() {
        return (this.stub == null || this.proxy == null) ? false : true;
    }

    public int isBillingSupported(int apiVersion, String packageName, String type) throws RemoteException {
        try {
            return ((Integer) this.stub.getMethod("isBillingSupported", new Class[]{Integer.TYPE, String.class, String.class}).invoke(this.proxy, new Object[]{Integer.valueOf(apiVersion), packageName, type})).intValue();
        } catch (NoSuchMethodException e) {
            return 0;
        } catch (IllegalAccessException e2) {
            return 0;
        } catch (InvocationTargetException e3) {
            return 0;
        }
    }
}
