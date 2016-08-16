package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.NewsListener;
import com.sygic.aura.news.NewsItem;
import java.util.ArrayList;

public class NewsEventReceiver extends NativeMethodsHelper {
    public static void registerNewsListener(NewsListener listener) {
        NativeMethodsHelper.registerListener(NewsListener.class, listener);
    }

    public static void unregisterNewsListener(NewsListener listener) {
        NativeMethodsHelper.unregisterListener(NewsListener.class, listener);
    }

    protected static void onNewsLoaded(NewsItem[] entries) {
        ArrayList<NewsItem> arr = new ArrayList();
        for (NewsItem entry : entries) {
            arr.add(entry);
        }
        NativeMethodsHelper.callMethodWhenReady(NewsListener.class, "onNewsLoaded", arr);
    }

    protected static void onNewLoadingFailed() {
        NativeMethodsHelper.callMethodWhenReady(NewsListener.class, "onNewLoadingFailed", new Object[0]);
    }
}
