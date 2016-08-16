package com.sygic.aura.news;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;

public class NewsManager {

    /* renamed from: com.sygic.aura.news.NewsManager.1 */
    static class C14161 implements VoidCallback {
        C14161() {
        }

        public void getMethod() {
            NewsManager.RequestNews();
        }
    }

    private static native void RequestNews();

    public static void nativeRequestNews() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C14161());
        }
    }
}
