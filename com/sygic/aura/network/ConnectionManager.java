package com.sygic.aura.network;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;

public class ConnectionManager {

    /* renamed from: com.sygic.aura.network.ConnectionManager.1 */
    static class C14091 implements Callback<Boolean> {
        C14091() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(ConnectionManager.IsConnected());
        }
    }

    private static native boolean IsConnected();

    public static boolean nativeIsConnected() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C14091()).execute().get(Boolean.valueOf(false))).booleanValue();
    }
}
