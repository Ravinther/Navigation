package com.sygic.aura.helper.interfaces;

import com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener;
import com.sygic.aura.news.NewsItem;
import java.util.ArrayList;

public interface NewsListener extends CoreEventListener {
    void onNewLoadingFailed();

    void onNewsLoaded(ArrayList<NewsItem> arrayList);
}
