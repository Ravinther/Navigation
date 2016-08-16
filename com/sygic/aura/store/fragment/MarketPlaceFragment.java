package com.sygic.aura.store.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.feature.system.LowSystemFeature.EEventType;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.InputDialogFragment.DialogFragmentClickListener;
import com.sygic.aura.fragments.ProductCodeFragment;
import com.sygic.aura.fragments.WebViewFragment;
import com.sygic.aura.fragments.interfaces.BaseDashboardFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.ProductDetailFragmentResultCallback;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.MarketPlaceEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.interfaces.MarketPlaceListener;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.first_run.fragments.FirstRunWizardStartFragment;
import com.sygic.aura.settings.fragments.SettingsFragment;
import com.sygic.aura.store.MarketPlaceManager;
import com.sygic.aura.store.data.ButtonEntry;
import com.sygic.aura.store.data.ButtonEntry.EButtonAction;
import com.sygic.aura.store.data.FolderEntry;
import com.sygic.aura.store.data.ImageEntry;
import com.sygic.aura.store.data.ProductDetailEntry;
import com.sygic.aura.store.data.SelectEntry;
import com.sygic.aura.store.data.SelectEntry.OptionItem;
import com.sygic.aura.store.data.StoreEntry;
import com.sygic.aura.store.data.StoreEntry.EViewType;
import com.sygic.aura.store.model.MarketPlaceAdapter;
import com.sygic.aura.views.SmartProgressBar;
import com.sygic.aura.views.SmartProgressBar.OnRetryCallback;
import com.sygic.aura.views.WndManager;
import com.sygic.aura.views.font_specials.SEditText;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.base.C1799R;
import java.util.ArrayList;
import java.util.HashMap;
import loquendo.tts.engine.TTSConst;

public class MarketPlaceFragment extends AbstractScreenFragment implements OnScrollListener, OnItemClickListener, BaseDashboardFragmentResultCallback, ProductDetailFragmentResultCallback, MarketPlaceListener {
    private static boolean mAwaitsActivationCompleted;
    private ArrayList<StoreEntry> mEntries;
    private boolean mForceMultiselect;
    private int mInvokeCommand;
    private ListView mList;
    private boolean mLoading;
    private boolean mNavigationBarChanged;
    private boolean mNextPage;
    private int mPage;
    private boolean mPositionRequired;
    private EditText mSearchEdit;
    private ViewGroup mSearchView;
    private final HashMap<String, OptionItem> mSelectedOptions;
    private SmartProgressBar mSmartProgressBar;
    private String mSource;
    private String mStrProductId;
    private String mTitle;

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.1 */
    class C17211 implements OnRetryCallback {
        C17211() {
        }

        public void onRetry() {
            if (!MarketPlaceFragment.this.mLoading) {
                MarketPlaceFragment.this.loadList(true);
            }
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.2 */
    class C17222 implements OnEditorActionListener {
        C17222() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 2) {
                return false;
            }
            MarketPlaceFragment.this.mPage = 0;
            MarketPlaceFragment.this.loadList(false, MarketPlaceFragment.this.mSearchEdit.getText().toString());
            NaviNativeActivity.hideKeyboard(v.getWindowToken());
            return true;
        }
    }

    private interface InvokeCallback {
        boolean execute();
    }

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.3 */
    class C17233 implements InvokeCallback {
        final /* synthetic */ String val$searchQuery;

        C17233(String str) {
            this.val$searchQuery = str;
        }

        public boolean execute() {
            if (MarketPlaceFragment.this.mStrProductId == null) {
                return MarketPlaceManager.nativeInvokeSygicStore(MarketPlaceFragment.this.getOptionNameForEntry("region"), MarketPlaceFragment.this.getOptionNameForEntry("currency"));
            }
            if (MarketPlaceFragment.this.mPositionRequired) {
                return MarketPlaceManager.nativeRequestSearch(MarketPlaceFragment.this.mStrProductId, this.val$searchQuery, MarketPlaceFragment.access$204(MarketPlaceFragment.this));
            }
            return MarketPlaceManager.nativeRequestList(MarketPlaceFragment.this.mStrProductId, MarketPlaceFragment.this.getOptionNameForEntry("region"), MarketPlaceFragment.this.getOptionNameForEntry("currency"));
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.4 */
    class C17244 implements InvokeCallback {
        C17244() {
        }

        public boolean execute() {
            return WndManager.nativeInvokeCommand(MarketPlaceFragment.this.mInvokeCommand, MarketPlaceFragment.this.mStrProductId, MarketPlaceFragment.this.getOptionNameForEntry("region"), MarketPlaceFragment.this.getOptionNameForEntry("currency"));
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.5 */
    class C17255 implements OnClickListener {
        C17255() {
        }

        public void onClick(DialogInterface dialog, int which) {
            MarketPlaceManager.nativeContinueProcess();
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.6 */
    class C17266 implements Runnable {
        final /* synthetic */ String val$strId;

        C17266(String str) {
            this.val$strId = str;
        }

        public void run() {
            Bundle bundle = new Bundle();
            String title = ResourceManager.getCoreString(MarketPlaceFragment.this.getActivity(), 2131165799);
            bundle.putString(AbstractFragment.ARG_TITLE, title);
            bundle.putString("map_list_id", this.val$strId);
            MarketPlaceEventsReceiver.unregisterEventsListener(MarketPlaceFragment.this);
            if (MarketPlaceFragment.this.mForceMultiselect) {
                bundle.putInt(SettingsFragment.ARG_MENU, 2131755012);
                Fragments.add(MarketPlaceFragment.this.getActivity(), MultiSelectComponentsFragment.class, title, bundle, true, (FirstRunWizardStartFragment) Fragments.findFragmentByTag(MarketPlaceFragment.this.getActivity(), "fragment_frw_start_tag"));
                return;
            }
            Fragments.add(MarketPlaceFragment.this.getActivity(), ComponentsFragment.class, title, bundle);
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.7 */
    static class C17277 implements DialogFragmentClickListener {
        final /* synthetic */ Activity val$activity;

        C17277(Activity activity) {
            this.val$activity = activity;
        }

        public void onPositiveButtonClicked(Editable text) {
            MarketPlaceFragment.mAwaitsActivationCompleted = MarketPlaceManager.nativeEnterProductCode(text.toString());
            InfinarioAnalyticsLogger.getInstance(this.val$activity).trackProductCodeInsert();
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.8 */
    class C17288 implements OnClickListener {
        final /* synthetic */ SelectEntry val$selectEntry;

        C17288(SelectEntry selectEntry) {
            this.val$selectEntry = selectEntry;
        }

        public void onClick(DialogInterface dialog, int which) {
            OptionItem option = this.val$selectEntry.getOptions()[which];
            this.val$selectEntry.setSelectedOption(option);
            MarketPlaceFragment.this.mSelectedOptions.put(this.val$selectEntry.getName(), option);
            MarketPlaceFragment.this.loadList(false);
            dialog.dismiss();
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.MarketPlaceFragment.9 */
    static /* synthetic */ class C17299 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction;
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType;

        static {
            $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction = new int[EButtonAction.values().length];
            try {
                $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[EButtonAction.BtnActivate.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[EButtonAction.BtnRestore.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[EButtonAction.BtnUrl.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[EButtonAction.BtnList.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[EButtonAction.BtnBuy.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[EButtonAction.BtnNone.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[EButtonAction.BtnProduct.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType = new int[EViewType.values().length];
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_FOLDER.ordinal()] = 1;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_PRODUCT.ordinal()] = 2;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_BUTTON.ordinal()] = 3;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_EDIT.ordinal()] = 4;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_PAGINATOR.ordinal()] = 5;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_SELECT.ordinal()] = 6;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_IMAGE.ordinal()] = 7;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_COMPONENT.ordinal()] = 8;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[EViewType.TYPE_TEXT.ordinal()] = 9;
            } catch (NoSuchFieldError e16) {
            }
        }
    }

    private class LoadingRunnable implements Runnable {
        private final InvokeCallback mCallback;
        private int mCounter;

        private LoadingRunnable(InvokeCallback callback) {
            this.mCounter = 0;
            this.mCallback = callback;
        }

        public void run() {
            boolean success;
            if (this.mCallback == null || !this.mCallback.execute()) {
                success = false;
            } else {
                success = true;
            }
            if (!success) {
                int i = this.mCounter + 1;
                this.mCounter = i;
                if (i < 6) {
                    MarketPlaceFragment.this.mSmartProgressBar.postDelayed(this, 1000);
                    return;
                }
                MarketPlaceFragment.this.mLoading = false;
                MarketPlaceFragment.this.mSmartProgressBar.stopAndShowError();
            }
        }
    }

    static /* synthetic */ int access$204(MarketPlaceFragment x0) {
        int i = x0.mPage + 1;
        x0.mPage = i;
        return i;
    }

    public MarketPlaceFragment() {
        this.mLoading = false;
        this.mNavigationBarChanged = false;
        this.mSelectedOptions = new HashMap();
        setWantsNavigationData(false);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(this.mTitle);
        toolbar.setSubtitle(getOptionTitleForEntry("region"));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mTitle = bundle.getString(AbstractFragment.ARG_TITLE);
            this.mStrProductId = bundle.getString("product_id");
            this.mInvokeCommand = bundle.getInt("invoke_command", -1);
            this.mPositionRequired = bundle.getBoolean("position_required", false);
            this.mForceMultiselect = bundle.getBoolean("force_multiselect", false);
            this.mEntries = bundle.getParcelableArrayList("entries");
            this.mSource = bundle.getString("source");
        }
        this.mPage = 0;
        MarketPlaceEventsReceiver.registerEventsListener(this);
        if (TextUtils.isEmpty(this.mStrProductId)) {
            InfinarioAnalyticsLogger.getInstance(getActivity()).trackStoreViewMainList(this.mSource);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903134, container, false);
        this.mList = (ListView) view.findViewById(2131624221);
        this.mList.setAdapter(new MarketPlaceAdapter(inflater.getContext()));
        this.mList.setOnItemClickListener(this);
        this.mList.setOnScrollListener(this);
        this.mSmartProgressBar = (SmartProgressBar) view.findViewById(2131624166);
        this.mSmartProgressBar.setRetryCallback(new C17211());
        this.mSearchView = (ViewGroup) view.findViewById(2131624307);
        this.mSearchEdit = (SEditText) view.findViewById(2131624470);
        this.mSearchEdit.requestFocus();
        this.mSearchEdit.setOnEditorActionListener(new C17222());
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.mEntries == null) {
            loadList(false);
        } else {
            onListLoaded(this.mEntries, Boolean.valueOf(false));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        MarketPlaceEventsReceiver.unregisterEventsListener(this);
        if (this.mResultCallback != null && (this.mResultCallback instanceof BaseDashboardFragmentResultCallback)) {
            ((BaseDashboardFragmentResultCallback) this.mResultCallback).onDashboardFragmentFinished();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mSearchEdit != null) {
            NaviNativeActivity.hideKeyboard(this.mSearchEdit.getWindowToken());
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (NaviNativeActivity.isNavigationBarHidden(activity)) {
            NaviNativeActivity.showNavigationBar(activity, true);
            this.mNavigationBarChanged = true;
        }
    }

    public void onDetach() {
        if (this.mNavigationBarChanged) {
            NaviNativeActivity.hideNavigationBar(getActivity());
            this.mNavigationBarChanged = false;
        }
        super.onDetach();
    }

    private void loadList(boolean reloadAfterError) {
        loadList(reloadAfterError, "");
    }

    public void loadList(boolean reloadAfterError, String searchQuery) {
        Runnable r;
        if (this.mInvokeCommand == -1 || this.mStrProductId == null) {
            r = new LoadingRunnable(new C17233(searchQuery), null);
        } else {
            r = new LoadingRunnable(new C17244(), null);
        }
        this.mLoading = true;
        if (reloadAfterError) {
            this.mSmartProgressBar.startWithCrossfade();
        } else {
            this.mSmartProgressBar.startWithFadeIn();
        }
        this.mSmartProgressBar.post(r);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mList.setAdapter(this.mList.getAdapter());
    }

    public void onListLoaded(ArrayList<StoreEntry> entriesArray, Boolean isUpdateRequired) {
        this.mLoading = false;
        StoreEntry[] entries = (StoreEntry[]) entriesArray.toArray(new StoreEntry[entriesArray.size()]);
        MarketPlaceAdapter adapter = (MarketPlaceAdapter) this.mList.getAdapter();
        adapter.setNotifyOnChange(false);
        if (!this.mPositionRequired || this.mPage == 1) {
            adapter.clear();
        }
        int length = entries.length;
        int i = 0;
        int position = 0;
        while (i < length) {
            int position2;
            StoreEntry entry = entries[i];
            if (entry.getType() == EViewType.TYPE_PAGINATOR) {
                position2 = position;
            } else if (entry.getType() == EViewType.TYPE_EDIT) {
                this.mSearchView.setVisibility(0);
                position2 = position;
            } else {
                if (entry.getType() == EViewType.TYPE_SELECT) {
                    SelectEntry selectEntry = (SelectEntry) entry;
                    if ("region".equals(selectEntry.getName())) {
                        this.mSelectedOptions.put("region", selectEntry.getSelectedOption());
                        this.mToolbar.setSubtitle(getOptionTitleForEntry("region"));
                    }
                }
                position2 = position + 1;
                entry.setPosition(position);
                adapter.add(entry);
            }
            i++;
            position = position2;
        }
        adapter.notifyDataSetChanged();
        if (entries.length > 0 && entries[entries.length - 1].getType() == EViewType.TYPE_PAGINATOR) {
            this.mNextPage = true;
        }
        this.mSmartProgressBar.stopAndCrossfadeWith(this.mList);
    }

    public void onStoreMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            new Builder(getActivity()).title((int) C1799R.string.app_name).body(message).negativeButton(2131165351, null).positiveButton(2131165355, new C17255()).build().showAllowingStateLoss("show_message");
        }
    }

    public void onConnectionChanged(Boolean connected) {
        if (!connected.booleanValue()) {
            if (this.mList.getAdapter().getCount() <= 0) {
                this.mSmartProgressBar.stopAndShowError();
            }
            this.mLoading = false;
        } else if (!this.mLoading) {
            loadList(true);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StoreEntry item = (StoreEntry) parent.getItemAtPosition(position);
        if (item != null) {
            switch (C17299.$SwitchMap$com$sygic$aura$store$data$StoreEntry$EViewType[item.getType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    onFolderClick(item);
                case TTSConst.TTSPARAGRAPH /*2*/:
                    onProductClick(item);
                case TTSConst.TTSUNICODE /*3*/:
                    onButtonClick(getActivity(), item);
                case TTSConst.TTSXML /*4*/:
                case TTSConst.TTSEVT_TEXT /*5*/:
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    onSelectClick(item);
                case TTSConst.TTSEVT_BOOKMARK /*7*/:
                    onImageClick(item);
                default:
                    Log.w("MarketPlaceFragment", "Unsupported call of type: " + item.getType());
            }
        }
    }

    public void onProductDetail(ProductDetailEntry product) {
        Bundle bundle = new Bundle();
        bundle.putString(AbstractFragment.ARG_TITLE, product.getTitle());
        bundle.putString("product_id", product.getId());
        bundle.putParcelable("product", product);
        MarketPlaceEventsReceiver.unregisterEventsListener(this);
        Fragments.add(getActivity(), ProductDetailFragment.class, product.getTitle(), bundle, true, this);
    }

    public void onShowError(Integer iErrorState, String strMessage) {
        if (!TextUtils.isEmpty(strMessage)) {
            new Builder(getActivity()).title((int) C1799R.string.title_warning).body(ResourceManager.getCoreString(strMessage)).negativeButton(2131165351, null).build().showAllowingStateLoss("show_error");
        }
    }

    public void onEnterActivationCode() {
    }

    public void onShowComponents(String strId) {
        if (mAwaitsActivationCompleted) {
            mAwaitsActivationCompleted = false;
            Bundle params = new Bundle();
            params.putString("category", "mysygic");
            params.putString("eventName", "licence_activated/restored");
            params.putString("label", "from_store");
            params.putString("coreParams", "category:mysygic:from_store:true");
            params.putSerializable("eventType", EEventType.ETNone);
            SygicAnalyticsLogger.logEvent(getActivity(), EventType.ACTIVATION, params);
        }
        new Handler().postDelayed(new C17266(strId), 500);
    }

    public void onInstallRequired() {
    }

    public void onProductDetailFragmentResult(boolean forceRefresh) {
        MarketPlaceEventsReceiver.registerEventsListener(this);
        if (forceRefresh) {
            this.mList.setVisibility(4);
            loadList(false);
        } else if (this.mInvokeCommand != -1) {
            performHomeAction();
        }
    }

    public void onDashboardFragmentFinished() {
        MarketPlaceEventsReceiver.registerEventsListener(this);
    }

    private void onFolderClick(StoreEntry item) {
        goToFolder(item.getTitle(), item.getId(), ((FolderEntry) item).isPositionRequired());
    }

    private void onProductClick(StoreEntry item) {
        goToProductDetail(item.getTitle(), item.getId());
    }

    public static boolean onButtonClick(Activity activity, StoreEntry item) {
        if (item instanceof ButtonEntry) {
            ButtonEntry entry = (ButtonEntry) item;
            switch (C17299.$SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[entry.getAction().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    ProductCodeFragment.newInstance(activity, 2131165932, null, new C17277(activity)).showDialog();
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if (!MarketPlaceManager.nativeRestoreProducts()) {
                        SToast.makeText(activity, 2131165463, 0).show();
                        break;
                    }
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    String strUrl = entry.getUrl();
                    if (!TextUtils.isEmpty(strUrl)) {
                        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(strUrl));
                        if (browserIntent != null) {
                            activity.startActivity(browserIntent);
                            break;
                        }
                    }
                    break;
                case TTSConst.TTSXML /*4*/:
                    MarketPlaceManager.nativeRequestList(item.getId());
                    break;
                case TTSConst.TTSEVT_TEXT /*5*/:
                    String strUri = entry.getUrl();
                    if (!TextUtils.isEmpty(strUri)) {
                        Uri uri = Uri.parse(strUri.replace("&", "?"));
                        MarketPlaceManager.nativeRequestBuyPrepare(uri.getQueryParameter("id"), uri.getQueryParameter("activate"), false);
                        break;
                    }
                    break;
                default:
                    Log.d("MarketPlaceFragment", "Unknown button action: " + entry.getAction());
                    break;
            }
            return true;
        }
        Log.w("MarketPlaceFragment", "Invalid StoreEntry type. Expected 'TYPE_BUTTON', current type: " + item.getType());
        return false;
    }

    private void onSelectClick(StoreEntry item) {
        SelectEntry selectEntry = (SelectEntry) item;
        new Builder(getActivity()).title(selectEntry.getTitle()).negativeButton(2131165351, null).simpleCheckedAdapter(selectEntry.getOptions(), selectEntry.getSelectedOptionIndex(), new C17288(selectEntry)).build().show(getFragmentManager(), "dialog_select");
    }

    private void onImageClick(StoreEntry entry) {
        ImageEntry imageEntry = (ImageEntry) entry;
        switch (C17299.$SwitchMap$com$sygic$aura$store$data$ButtonEntry$EButtonAction[imageEntry.getAction().ordinal()]) {
            case TTSConst.TTSUNICODE /*3*/:
                goToWebView(imageEntry.getTitle(), imageEntry.getUrl());
            case TTSConst.TTSXML /*4*/:
                goToFolder(imageEntry.getTitle(), imageEntry.getUrl(), false);
            case TTSConst.TTSEVT_SENTENCE /*6*/:
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                goToProductDetail(imageEntry.getTitle(), imageEntry.getUrl());
            default:
                Log.d("MarketPlaceFragment", "Unknown image action: " + imageEntry.getAction());
        }
    }

    private void goToWebView(String title, String url) {
        Bundle args = new Bundle();
        args.putString(AbstractFragment.ARG_TITLE, title);
        args.putString("uri", url);
        Fragments.add(getActivity(), WebViewFragment.class, "fragment_webview", args);
    }

    private void goToFolder(String title, String productId, boolean positionRequired) {
        Bundle args = new Bundle();
        args.putString(AbstractFragment.ARG_TITLE, title);
        args.putString("product_id", productId);
        args.putBoolean("position_required", positionRequired);
        args.putString("source", "market place fragment folder");
        MarketPlaceEventsReceiver.unregisterEventsListener(this);
        Fragments.add(getActivity(), MarketPlaceFragment.class, title, args, true, this);
    }

    private void goToProductDetail(String title, String productId) {
        if (!TextUtils.isEmpty(productId)) {
            Bundle args = new Bundle();
            args.putString(AbstractFragment.ARG_TITLE, title);
            args.putString("product_id", productId);
            args.putString("product_id_parent", this.mStrProductId);
            MarketPlaceEventsReceiver.unregisterEventsListener(this);
            Fragments.add(getActivity(), ProductDetailFragment.class, title, args, true, this);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mNextPage && view.getLastVisiblePosition() > totalItemCount - 3) {
            loadList(false);
            this.mNextPage = false;
        }
    }

    public String getOptionTitleForEntry(String selectEntryName) {
        OptionItem option = (OptionItem) this.mSelectedOptions.get(selectEntryName);
        return option != null ? option.getTitle() : null;
    }

    private String getOptionNameForEntry(String selectEntryName) {
        OptionItem option = (OptionItem) this.mSelectedOptions.get(selectEntryName);
        return option != null ? option.getName() : null;
    }
}
