package com.sygic.aura.news;

import android.os.Bundle;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.NewsResultCallback;
import com.sygic.aura.helper.EventReceivers.NewsEventReceiver;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.helper.interfaces.NewsListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.SmartProgressBar.OnRetryCallback;
import com.sygic.aura.views.font_specials.SToolbar;
import java.util.ArrayList;
import java.util.HashMap;

public class NewsFragment extends AbstractScreenFragment implements BackPressedListener, NewsListener {
    public static final String BUNDLEKEY_NEW_LIST = "com.sygic.aura.fragments.NewsFragment.BUNDLEKEY_NEW_LIST";
    public static final String TAG = "NEWS_FRAGMENT";
    private Exit mExit;
    private ListView mListView;
    private ArrayList<NewsItem> mNewsList;
    private SmartProgressBar mProgressBar;

    /* renamed from: com.sygic.aura.news.NewsFragment.1 */
    class C14111 implements OnMenuItemClickListener {
        C14111() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return NewsFragment.this.onOptionsItemSelected(menuItem);
        }
    }

    /* renamed from: com.sygic.aura.news.NewsFragment.2 */
    class C14122 implements OnRetryCallback {
        C14122() {
        }

        public void onRetry() {
            NewsManager.nativeRequestNews();
        }
    }

    /* renamed from: com.sygic.aura.news.NewsFragment.3 */
    class C14133 extends HashMap<String, String> {
        C14133() {
            put("Exit type", NewsFragment.this.mExit.name());
        }
    }

    /* renamed from: com.sygic.aura.news.NewsFragment.4 */
    class C14144 implements Runnable {
        C14144() {
        }

        public void run() {
            NewsFragment.this.mProgressBar.setVisibility(4);
        }
    }

    private enum Exit {
        BACK,
        UP,
        MENU
    }

    public NewsFragment() {
        this.mNewsList = new ArrayList();
        this.mExit = Exit.UP;
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(ResourceManager.getCoreString(getActivity(), 2131166013));
        toolbar.inflateMenu(2131755018);
        toolbar.setOnMenuItemClickListener(new C14111());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624678) {
            return super.onOptionsItemSelected(item);
        }
        this.mExit = Exit.MENU;
        performHomeAction();
        return true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle;
        View result = inflater.inflate(2130903115, container, false);
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        this.mListView = (ListView) result.findViewById(2131624221);
        this.mProgressBar = (SmartProgressBar) result.findViewById(2131624166);
        this.mProgressBar.setRetryCallback(new C14122());
        if (savedInstanceState == null) {
            bundle = getArguments();
        } else {
            bundle = savedInstanceState;
        }
        if (bundle.containsKey(BUNDLEKEY_NEW_LIST)) {
            this.mNewsList = bundle.getParcelableArrayList(BUNDLEKEY_NEW_LIST);
            this.mProgressBar.setVisibility(8);
            this.mListView.setVisibility(0);
        } else {
            NewsEventReceiver.registerNewsListener(this);
            NewsManager.nativeRequestNews();
            this.mProgressBar.startWithFadeIn();
        }
        this.mListView.setAdapter(new NewsAdapter(getActivity(), this.mNewsList));
        return result;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mNewsList != null && !this.mNewsList.isEmpty()) {
            outState.putParcelableArrayList(BUNDLEKEY_NEW_LIST, this.mNewsList);
        }
    }

    public boolean onBackPressed() {
        this.mExit = Exit.BACK;
        return false;
    }

    public void onDestroy() {
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
        NewsEventReceiver.unregisterNewsListener(this);
        if (this.mResultCallback != null) {
            ((NewsResultCallback) this.mResultCallback).onNewsFragmentFinished();
        }
        Bundle bundle = new Bundle();
        bundle.putString("eventName", "What's new exit");
        bundle.putSerializable("attributes", new C14133());
        SygicAnalyticsLogger.logEvent(getActivity(), EventType.WHATS_NEW, bundle);
        super.onDestroy();
    }

    public void onNewsLoaded(ArrayList<NewsItem> entries) {
        if (this.mProgressBar != null && this.mListView != null) {
            if (entries == null || entries.isEmpty()) {
                this.mProgressBar.stopAndShowError();
                return;
            }
            ArrayAdapter adapter = (ArrayAdapter) this.mListView.getAdapter();
            adapter.setNotifyOnChange(false);
            this.mNewsList.clear();
            this.mNewsList.addAll(entries);
            adapter.notifyDataSetChanged();
            this.mProgressBar.stopAndCrossfadeWith(this.mListView);
            this.mProgressBar.post(new C14144());
        }
    }

    public void onNewLoadingFailed() {
        if (this.mProgressBar != null) {
            this.mProgressBar.stopAndShowError();
        }
    }
}
