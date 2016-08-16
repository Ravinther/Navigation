package com.bosch.myspin.serversdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import java.util.List;

/* renamed from: com.bosch.myspin.serversdk.utils.e */
public class C0236e {

    /* renamed from: com.bosch.myspin.serversdk.utils.e.a */
    public static class C0234a extends Exception {
        public C0234a(String str) {
            super(str);
        }
    }

    /* renamed from: com.bosch.myspin.serversdk.utils.e.b */
    public static class C0235b extends Exception {
        public C0235b(String str) {
            super(str);
        }
    }

    public static Intent m368a(Context context, Intent intent) throws C0235b, C0234a {
        if (context == null) {
            throw new IllegalArgumentException("Context must be not null!");
        } else if (intent == null) {
            throw new IllegalArgumentException("Intent must be not null!");
        } else if (intent.getAction() == null) {
            throw new IllegalArgumentException("Intent must have an action!");
        } else {
            List queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
            if (queryIntentServices == null || queryIntentServices.size() == 0) {
                throw new C0234a("No service found for " + intent.getAction() + " action.");
            } else if (queryIntentServices.size() > 1) {
                throw new C0235b("Can't be bind service, more then one service is found for " + intent.getAction() + " action. Propably multiple launcher apps are installed.");
            } else {
                ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServices.get(0)).serviceInfo;
                Intent intent2 = new Intent(intent.getAction());
                intent2.setClassName(serviceInfo.packageName, serviceInfo.name);
                return intent2;
            }
        }
    }
}
