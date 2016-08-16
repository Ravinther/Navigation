package com.sygic.aura.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.resources.ResourceManager;
import java.util.HashSet;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

public class AccountManager {
    private static final Object LOCK;
    private static final Set<UserNameObserver> registerdObservers;
    private static AsyncTask<Void, Void, Bitmap> sTask;

    /* renamed from: com.sygic.aura.network.AccountManager.11 */
    static class AnonymousClass11 implements VoidCallback {
        final /* synthetic */ String val$name;

        AnonymousClass11(String str) {
            this.val$name = str;
        }

        public void getMethod() {
            AccountManager.ForgotPassword(this.val$name);
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.12 */
    static class AnonymousClass12 implements Callback<Boolean> {
        final /* synthetic */ String val$name;
        final /* synthetic */ String val$password;

        AnonymousClass12(String str, String str2) {
            this.val$name = str;
            this.val$password = str2;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(AccountManager.CreateUser(this.val$name, this.val$password));
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.14 */
    static /* synthetic */ class AnonymousClass14 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus;

        static {
            $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus = new int[ELoginStatus.values().length];
            try {
                $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[ELoginStatus.LSNoPassword.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[ELoginStatus.LSLoggedIn.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[ELoginStatus.LSTimeoutSession.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[ELoginStatus.LSError.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[ELoginStatus.LSServerError.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[ELoginStatus.LSFacebookError.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[ELoginStatus.LSGoogleError.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[ELoginStatus.LSNone.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.1 */
    static class C13911 implements Callback<Boolean> {
        final /* synthetic */ String val$name;
        final /* synthetic */ String val$password;

        C13911(String str, String str2) {
            this.val$name = str;
            this.val$password = str2;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(AccountManager.SygicLogin(this.val$name.trim(), this.val$password));
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.2 */
    static class C13922 implements Callback<String> {
        C13922() {
        }

        public String getMethod() {
            return AccountManager.GetSygicLogin();
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.3 */
    static class C13933 implements Callback<String> {
        C13933() {
        }

        public String getMethod() {
            return AccountManager.GetSygicPassStars();
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.4 */
    static class C13944 implements Callback<Boolean> {
        C13944() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(AccountManager.FacebookLogin());
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.5 */
    static class C13955 implements Callback<Boolean> {
        C13955() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(AccountManager.GoogleLogin());
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.6 */
    static class C13966 implements Callback<Boolean> {
        C13966() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(AccountManager.IsLoggedIn());
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.7 */
    static class C13977 implements Callback<String> {
        C13977() {
        }

        public String getMethod() {
            return AccountManager.GetUserName();
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.8 */
    static class C13988 implements Callback<Integer> {
        C13988() {
        }

        public Integer getMethod() {
            return Integer.valueOf(AccountManager.GetServiceLogged());
        }
    }

    /* renamed from: com.sygic.aura.network.AccountManager.9 */
    static class C13999 implements Callback<Boolean> {
        C13999() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(AccountManager.LogOut());
        }
    }

    public enum EChangePwdStatus {
        StatusOk(0),
        StatusBadPwd(1),
        StatusFailed(2);
        
        private final int mIntValue;

        private EChangePwdStatus(int value) {
            this.mIntValue = value;
        }

        public int getValue() {
            return this.mIntValue;
        }

        public static EChangePwdStatus createFromInt(int value) {
            for (EChangePwdStatus eStatus : values()) {
                if (eStatus.getValue() == value) {
                    return eStatus;
                }
            }
            return StatusFailed;
        }
    }

    public enum ELoginStatus {
        LSNone,
        LSNoPassword,
        LSLoggedIn,
        LSTimeoutSession,
        LSServerError,
        LSError,
        LSFacebookError,
        LSGoogleError
    }

    public interface UserNameObserver {
        void onUserNameChanged(String str);
    }

    private static native void ClearUserLoginData();

    private static native boolean CreateUser(String str, String str2);

    private static native boolean FacebookLogin();

    private static native void ForgotPassword(String str);

    private static native int GetServiceLogged();

    private static native String GetSygicLogin();

    private static native String GetSygicPassStars();

    private static native String GetUserName();

    private static native boolean GoogleLogin();

    private static native boolean IsLoggedIn();

    private static native boolean LogOut();

    private static native boolean SygicLogin(String str, String str2);

    static {
        LOCK = new Object();
        registerdObservers = new HashSet();
    }

    private static void notifyObservers(String userName) {
        for (UserNameObserver observer : registerdObservers) {
            observer.onUserNameChanged(userName);
        }
    }

    public static boolean nativeSygicLogin(String name, String password) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C13911(name, password)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static String nativeGetSygicLogin() {
        if (SygicProject.IS_PROTOTYPE) {
            return "test_login";
        }
        return (String) new ObjectHandler(new C13922()).execute().get(null);
    }

    public static String nativeGetSygicPassStars() {
        if (SygicProject.IS_PROTOTYPE) {
            return "*****";
        }
        return (String) new ObjectHandler(new C13933()).execute().get(null);
    }

    public static boolean nativeFacebookLogin() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C13944()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeGoogleLogin() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C13955()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsLoggedIn() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C13966()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static String nativeGetUserName() {
        if (SygicProject.IS_PROTOTYPE) {
            return "test_login";
        }
        String userName = (String) new ObjectHandler(new C13977()).execute().get(null);
        notifyObservers(userName);
        return userName;
    }

    public static int nativeGetServiceLogged() {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        return ((Integer) new ObjectHandler(new C13988()).execute().get(Integer.valueOf(0))).intValue();
    }

    public static boolean nativeLogOut() {
        notifyObservers(null);
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C13999()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeClearUserLoginData() {
        notifyObservers(null);
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    AccountManager.ClearUserLoginData();
                }
            });
        }
    }

    public static void nativeForgotPassword(String name) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass11(name));
        }
    }

    public static boolean nativeCreateAccount(String name, String password) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass12(name, password)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void cancelAvatarDownload() {
        sTask.cancel(true);
    }

    public static void onDownloadCompleted() {
        logMessage("Download completed");
        synchronized (LOCK) {
            LOCK.notify();
        }
    }

    public static String getMessage(Context context, ELoginStatus status) {
        switch (AnonymousClass14.$SwitchMap$com$sygic$aura$network$AccountManager$ELoginStatus[status.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return ResourceManager.getCoreString(context, 2131165494);
            case TTSConst.TTSUNICODE /*3*/:
                return ResourceManager.getCoreString(context, 2131165496);
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_TEXT /*5*/:
                return ResourceManager.getCoreString(context, 2131165497);
            case TTSConst.TTSEVT_SENTENCE /*6*/:
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return ResourceManager.getCoreString(context, 2131165495);
            default:
                return null;
        }
    }

    private static void logMessage(String message) {
    }
}
