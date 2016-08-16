package com.sygic.sdk;

import android.util.Log;
import com.sygic.aura.SygicService;
import com.sygic.sdk.api.Api;
import com.sygic.sdk.api.ApiCallback;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ApiInitializer {
    protected static final String LOG_TAG;
    private ArrayList<ApiCallback> mCallbackList;
    private SygicService mService;

    /* renamed from: com.sygic.sdk.ApiInitializer.1 */
    class C18001 implements ApiCallback {
        final /* synthetic */ boolean val$bHasRemote;

        C18001(boolean z) {
            this.val$bHasRemote = z;
        }
    }

    /* renamed from: com.sygic.sdk.ApiInitializer.2 */
    class C18012 implements Runnable {
        C18012() {
        }

        public void run() {
            Log.d(ApiInitializer.LOG_TAG, "Init Api " + (ApiInitializer.this.initApi() ? "successful" : "failed"));
        }
    }

    static {
        LOG_TAG = ApiInitializer.class.getCanonicalName();
    }

    public ApiInitializer(SygicService service) {
        this.mService = service;
        this.mCallbackList = new ArrayList();
    }

    public void initSdk(boolean bHasRemote) {
        setApiCallback(new C18001(bHasRemote));
        new Thread(new C18012()).start();
    }

    public void closeApi() {
        try {
            Method closeApi = Api.class.getDeclaredMethod("nCloseApi", new Class[0]);
            closeApi.setAccessible(true);
            closeApi.invoke(null, (Object[]) null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }

    private boolean initApi() {
        try {
            Method initApi = Api.class.getDeclaredMethod("nInitApi", new Class[]{String.class, Boolean.TYPE});
            initApi.setAccessible(true);
            if (((Integer) initApi.invoke(null, new Object[]{"/data/data/" + this.mService.getPackageName() + "/lib", Boolean.valueOf(false)})).intValue() > 0) {
                return true;
            }
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return false;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    private static void setApiCallback(ApiCallback callback) {
        try {
            Method startNavi = Api.class.getDeclaredMethod("nInitJavaObjects", new Class[]{ApiCallback.class});
            startNavi.setAccessible(true);
            startNavi.invoke(null, new Object[]{callback});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }
}
