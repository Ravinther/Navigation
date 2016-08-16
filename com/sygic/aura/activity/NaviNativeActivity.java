package com.sygic.aura.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsCore.Builder;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders.AppViewBuilder;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.sygic.aura.C1090R;
import com.sygic.aura.actionbar.DrawerIF;
import com.sygic.aura.activity.CoreListenersActivity.OnRouteRestoredListener;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.GoogleAnalyticsLogger;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger.BackportInterface;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.dashboard.navigationDrawer.NavigationDrawer;
import com.sygic.aura.events.CoreEventsListener;
import com.sygic.aura.favorites.fragment.FavoritesFragment;
import com.sygic.aura.fcd.FloatingCarDataService;
import com.sygic.aura.feature.gl.LowGlFeature.GlSurfaceListener;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.WebViewFragment;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.CustomDialogFragment;
import com.sygic.aura.helper.EventReceivers.ComponentEventsReceiver;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.NewsEventReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.NavigateToPhotoHelper;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import com.sygic.aura.helper.StringHelper;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.SygicTrackerHelper;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.helper.interfaces.MapUpdateListener;
import com.sygic.aura.helper.interfaces.NewsListener;
import com.sygic.aura.keyboard.FocusableKeyboard;
import com.sygic.aura.keyboard.FocusableKeyboardView;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.bubble.BubbleManager;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.network.AccountManager;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.news.NewsFragment;
import com.sygic.aura.news.NewsItem;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.search.data.LocationQuery;
import com.sygic.aura.search.fragment.SearchFragment;
import com.sygic.aura.search.fragment.SearchOnRouteFragment;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.OnLanguageChangeListener;
import com.sygic.aura.showcase.ShowcaseView;
import com.sygic.aura.showcase.targets.ToolbarViewTarget;
import com.sygic.aura.showcase.targets.ToolbarViewTarget.ToolbarViewType;
import com.sygic.aura.store.fragment.ComponentsFragment;
import com.sygic.aura.utils.AlarmUtils;
import com.sygic.aura.utils.DownloadProgressNotification;
import com.sygic.aura.utils.NotificationReceiver.RetentionNotificationUtils;
import com.sygic.aura.views.SplashScreenFragment;
import com.sygic.aura.views.WhiteSurfaceView;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.base.C1799R;
import com.sygic.widget.WidgetContentObserver;
import com.sygic.widget.WidgetDataProvider;
import io.fabric.sdk.android.Fabric;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class NaviNativeActivity extends CoreListenersActivity implements OnBackStackChangedListener, DrawerIF, CoreEventsListener, MapUpdateListener, NewsListener, OnLanguageChangeListener {
    private static InputMethodManager imm;
    private static FocusableKeyboard mKeyboard;
    private static boolean sInitialized;
    private final List<BackPressedListener> mBackPressedListeners;
    private ContentObserver mContentObserver;
    private DownloadProgressNotification mDownloadProgressNotificationListener;
    private BroadcastReceiver mFrwReceiver;
    private GoogleApiClient mGoogleApiClient;
    private Configuration mOldConfig;
    private int mSessionSearchCount;
    private boolean mShouldAddDefaultMode;
    private SplashScreenFragment mSplash;
    private SToolbar mToolbar;
    private boolean mWasCrashFree;

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.11 */
    class AnonymousClass11 implements Runnable {
        final /* synthetic */ int val$newType;

        AnonymousClass11(int i) {
            this.val$newType = i;
        }

        public void run() {
            InCarConnection.changeCarConnection(this.val$newType == 3);
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.12 */
    static class AnonymousClass12 implements Runnable {
        final /* synthetic */ View val$viewRequesting;

        AnonymousClass12(View view) {
            this.val$viewRequesting = view;
        }

        public void run() {
            NaviNativeActivity.imm.toggleSoftInputFromWindow(this.val$viewRequesting.getWindowToken(), 0, 1);
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.13 */
    static class AnonymousClass13 implements Runnable {
        final /* synthetic */ Activity val$activity;

        AnonymousClass13(Activity activity) {
            this.val$activity = activity;
        }

        public void run() {
            View decorView = this.val$activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & -2563);
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.14 */
    class AnonymousClass14 implements OnRouteRestoredListener {
        final /* synthetic */ String val$tag;
        final /* synthetic */ String val$uri;

        AnonymousClass14(String str, String str2) {
            this.val$uri = str;
            this.val$tag = str2;
        }

        public void onRouteRestored() {
            NaviNativeActivity.this.unregisterRouteRestoredListener();
            NaviNativeActivity.this.goToWebView(this.val$uri, this.val$tag);
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.15 */
    class AnonymousClass15 implements ExecutionOrder {
        final /* synthetic */ String val$tag;
        final /* synthetic */ String val$uri;

        AnonymousClass15(String str, String str2) {
            this.val$uri = str;
            this.val$tag = str2;
        }

        public boolean runningCondition() {
            return NaviNativeActivity.this.mFragmentsRoot != null;
        }

        public boolean onPositive() {
            NaviNativeActivity.this.goToWebView(this.val$uri, this.val$tag);
            return false;
        }

        public boolean onNegative() {
            return true;
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.16 */
    class AnonymousClass16 implements Runnable {
        final /* synthetic */ String val$tag;
        final /* synthetic */ String val$uri;

        AnonymousClass16(String str, String str2) {
            this.val$uri = str;
            this.val$tag = str2;
        }

        public void run() {
            Bundle args = new Bundle();
            args.putString("uri", this.val$uri);
            args.putParcelable("mode", WebViewFragment.tagToMode(this.val$tag));
            NaviNativeActivity.this.addFragment(WebViewFragment.class, "fragment_promo_webview", true, null, args);
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.1 */
    class C11301 implements BackportInterface {
        C11301() {
        }

        public boolean isValid() {
            return LicenseInfo.isValid();
        }

        public boolean isTrial() {
            return LicenseInfo.nativeIsTrial();
        }

        public boolean isTrialExpired() {
            return LicenseInfo.nativeIsTrialExpired();
        }

        public boolean isLoggedIn() {
            return AccountManager.nativeIsLoggedIn();
        }

        public String getAccountUserName() {
            return AccountManager.nativeGetUserName();
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.2 */
    class C11312 implements OnClickListener {
        C11312() {
        }

        public void onClick(View v) {
            NaviNativeActivity.this.toggleDrawer();
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.3 */
    class C11323 implements OnMenuItemClickListener {
        C11323() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return NaviNativeActivity.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.4 */
    class C11334 implements OnClickListener {
        C11334() {
        }

        public void onClick(View view) {
            if (RouteManager.nativeExistValidRoute()) {
                NaviNativeActivity.this.goToSearchOnRoute();
            } else {
                NaviNativeActivity.this.goToSearch();
            }
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.5 */
    class C11355 implements GlSurfaceListener {
        final /* synthetic */ boolean val$isFirstRun;

        /* renamed from: com.sygic.aura.activity.NaviNativeActivity.5.1 */
        class C11341 implements Runnable {
            C11341() {
            }

            public void run() {
                SygicHelper.setNativeLoopEnabled(true);
                PositionInfo.nativeEnableMapView();
                if (NaviNativeActivity.this.mSplash != null) {
                    NaviNativeActivity.this.mSplash.dismissAllowingStateLoss();
                    NaviNativeActivity.this.mSplash = null;
                }
                SurfaceView surface = SygicHelper.getSurface();
                if (surface != null && (surface instanceof WhiteSurfaceView)) {
                    ((WhiteSurfaceView) surface).setInitialised();
                }
                BubbleManager.start();
                NaviNativeActivity.sInitialized = true;
                if (!C11355.this.val$isFirstRun) {
                    NaviNativeActivity.this.checkGPSEnabled();
                    MapControlsManager.nativeSetZoomDistance(11111.0f);
                    SettingsManager.setupRatingDlg(NaviNativeActivity.this);
                }
                if (!RouteManager.nativeExistValidRoute()) {
                    NaviNativeActivity.this.mToolbar.show();
                }
            }
        }

        C11355(boolean z) {
            this.val$isFirstRun = z;
        }

        public void surfaceCreated() {
            new Handler().post(new C11341());
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.6 */
    class C11366 implements DialogInterface.OnClickListener {
        final /* synthetic */ String val$appVersion;

        C11366(String str) {
            this.val$appVersion = str;
        }

        public void onClick(DialogInterface dialog, int which) {
            NaviNativeActivity.this.setPreviousVersion(this.val$appVersion, "previous_version");
            if (!NaviNativeActivity.this.mNavigationDrawer.isDrawerOpen()) {
                NaviNativeActivity.this.mNavigationDrawer.openDrawer();
            }
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(NaviNativeActivity.this.getContext(), 2131165799));
            bundle.putBoolean("fragment_has_update", true);
            NaviNativeActivity.this.addFragment(ComponentsFragment.class, "fragment_manage_maps", true, null, bundle);
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.7 */
    class C11377 implements DialogInterface.OnClickListener {
        final /* synthetic */ String val$appVersion;

        C11377(String str) {
            this.val$appVersion = str;
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            NaviNativeActivity.this.setPreviousVersion(this.val$appVersion, "previous_version");
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.8 */
    class C11388 implements DialogInterface.OnClickListener {
        C11388() {
        }

        public void onClick(DialogInterface dialog, int which) {
            NaviNativeActivity.this.getContext().startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }
    }

    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.9 */
    class C11399 implements OnConnectionFailedListener {
        C11399() {
        }

        public void onConnectionFailed(ConnectionResult connectionResult) {
            NaviNativeActivity.this.showOldGpsDialog();
        }
    }

    public NaviNativeActivity() {
        this.mSessionSearchCount = 0;
        this.mWasCrashFree = false;
        this.mBackPressedListeners = Collections.synchronizedList(new LinkedList());
        this.mShouldAddDefaultMode = true;
        this.mFrwReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if ("com.sygic.aura.ACTION_FRW_FINISHED".equals(intent.getAction())) {
                    NaviNativeActivity.this.showShowcase();
                }
            }
        };
    }

    static {
        sInitialized = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAnalytics();
        SygicHelper.setActivityWrapper(this);
        this.mOldConfig = new Configuration(getResources().getConfiguration());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String prefKey = getString(2131166137);
        this.mWasCrashFree = prefs.getBoolean(prefKey, false);
        prefs.edit().putBoolean(prefKey, false).apply();
        ContentResolver contentResolver = getContentResolver();
        Uri contentUri = WidgetDataProvider.getContentUri(this);
        ContentObserver widgetContentObserver = new WidgetContentObserver(this, new Handler());
        this.mContentObserver = widgetContentObserver;
        contentResolver.registerContentObserver(contentUri, true, widgetContentObserver);
        this.mLocationQuery = new LocationQuery();
        this.mRouteData = new RouteNavigateData();
        this.mSplash = new SplashScreenFragment();
        this.mSplash.show(getSupportFragmentManager(), "fragment_splash_tag");
        MapEventsReceiver.registerInvokeCommandListener(this);
        MapEventsReceiver.registerRouteFinishListener(this);
        MapEventsReceiver.registerRouteCancelListener(this);
        MapEventsReceiver.registerOnMapUpdateListener(this);
        RouteEventsReceiver.registerRouteCommandListener(this);
        SettingsManager.registerOnLanguageChangeListener(this);
        NewsEventReceiver.registerNewsListener(this);
        try {
            ViewConfiguration config = ViewConfiguration.get(getContext());
            if (VERSION.SDK_INT >= 14 && config.hasPermanentMenuKey()) {
                Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
                if (menuKeyField != null) {
                    menuKeyField.setAccessible(true);
                    menuKeyField.setBoolean(config, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SygicTrackerHelper.logOnCreate(this);
        AlarmUtils.setupNightlyAlarm(this);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(2131165290), true)) {
            startService(new Intent(this, FloatingCarDataService.class));
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mFrwReceiver, new IntentFilter("com.sygic.aura.ACTION_FRW_FINISHED"));
    }

    private void initAnalytics() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                dismissPendingNotification(intent);
                Uri uri = intent.getData();
                if (!(uri == null || uri.getQueryParameter("utm_source") == null)) {
                    GoogleAnalyticsLogger.getInstance(this).getTracker().send(((AppViewBuilder) new AppViewBuilder().setCampaignParamsFromUrl(uri.toString())).build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CrashlyticsCore core = new Builder().disabled(false).build();
            Fabric.with(this, new Crashlytics.Builder().core(core).build(), new CrashlyticsNdk());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        InfinarioAnalyticsLogger.getInstance(this).setBackportInfo(new C11301());
        GoogleAnalyticsLogger.getInstance(this).getTracker().send(new EventBuilder().setAction("sygic_start").setCategory("sygic_lifecycle").build());
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.out.println("SYGIC: onLowMemory()");
    }

    public void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(this.mContentObserver);
        MapEventsReceiver.unregisterInvokeCommandListener(this);
        MapEventsReceiver.unregisterRouteFinishListener(this);
        MapEventsReceiver.unregisterRouteCancelListener(this);
        RouteEventsReceiver.unregisterRouteCommandListener(this);
        MapEventsReceiver.unregisterOnMapUpdateListener(this);
        NewsEventReceiver.unregisterNewsListener(this);
        SettingsManager.unregisterOnLanguageChangeListener(this);
        if (this.mDownloadProgressNotificationListener != null) {
            ComponentEventsReceiver.unregisterEventsListener(this.mDownloadProgressNotificationListener);
            this.mDownloadProgressNotificationListener.clearNotification();
            this.mDownloadProgressNotificationListener = null;
        }
        this.mFragmentsRoot = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mFrwReceiver);
        Context context = getContext();
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(context.getString(2131166137), true).apply();
        SygicTrackerHelper.logOnDestroy(getContext());
        sInitialized = false;
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.mFragmentsRoot = (ViewGroup) findViewById(2131624520);
        if (this.mFragmentsRoot != null) {
            boolean z;
            imm = (InputMethodManager) getSystemService("input_method");
            if (ComponentManager.nativeGetInstalledMapCount() > 0) {
                z = true;
            } else {
                z = false;
            }
            this.mMapsAvailable = z;
            addFragment(MapOverlayFragment.class, "fragment_browse_controls_tag", false);
            this.mToolbar = (SToolbar) findViewById(2131624226);
            this.mToolbar.setTitle(2131165323);
            this.mToolbar.inflateMenu(2131755015);
            this.mToolbar.setNavigationIcon(2131034195);
            this.mToolbar.setNavigationOnClickListener(new C11312());
            this.mToolbar.setOnMenuItemClickListener(new C11323());
            this.mToolbar.hideImmediate();
            this.mToolbar.findViewById(2131624479).setOnClickListener(new C11334());
            boolean isFirstRun = SettingsManager.nativeShowFirstRunWizard();
            this.mNavigationDrawer = (NavigationDrawer) findViewById(2131624515);
            this.mNavigationDrawer.init(getSupportFragmentManager(), this.mRouteData, getDasboardLaunchMode(isFirstRun));
            SygicHelper.registerGlSurfaceListener(new C11355(isFirstRun));
        }
    }

    public void setDayNightMode(boolean isNightModeOn) {
        setTheme(isNightModeOn ? 2131296497 : 2131296397);
    }

    private void goToSearchOnRoute() {
        showNavigationBar(this, false);
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("can_show_tooltip", false);
        replaceFragment(SearchOnRouteFragment.class, "fragment_search_on_route_tag", true, bundle);
    }

    private void goToSearch() {
        if (this.mMapsAvailable && !this.mRestoringRoute) {
            if (this.mRouteData.getWaypointsCount() <= 0) {
                this.mRouteData.insertNewWaypoint();
            }
            Bundle bundle = new Bundle(1);
            String str = "can_show_tooltip";
            int i = this.mSessionSearchCount;
            this.mSessionSearchCount = i + 1;
            bundle.putBoolean(str, i == 0);
            addFragment(SearchFragment.class, "fragment_search_tag", true, null, bundle);
        }
    }

    private void goToFavourites() {
        if (!this.mMapsAvailable) {
            return;
        }
        if (RouteManager.nativeExistValidRoute()) {
            showNavigationBar(this, false);
            SygicAnalyticsLogger.getAnalyticsEvent(getContext(), EventType.SEARCH).setName("search").setValue("click", "favorites_from_map").logAndRecycle();
            Bundle bundle = new Bundle();
            bundle.putBoolean("route_filter", true);
            addFragment(FavoritesFragment.class, "fragment_favorites_tag", true, (FragmentResultCallback) this, bundle);
            return;
        }
        addFragment(FavoritesFragment.class, "fragment_favorites_tag", true, this);
    }

    private int getDasboardLaunchMode(boolean isFirstRun) {
        return isFirstRun ? 1 : 0;
    }

    private void setPreviousVersion(String version, String key) {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString(key, version).apply();
    }

    private String getPreviousVersion(String key) {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getString(key, "");
    }

    private void showMapUpdateDialog() {
        String appVersion = SettingsManager.nativeGetVersion();
        String VERSION_154 = "15.4";
        boolean isVersion154 = appVersion.startsWith("15.4");
        boolean wasUpdated = !getPreviousVersion("previous_version").equals(appVersion);
        CustomDialogFragment dialog = new CustomDialogFragment.Builder(getContext()).title(ResourceManager.getCoreString(getContext(), 2131165500)).body(2131165498).negativeButton(2131165499, new C11377(appVersion)).positiveButton(2131165501, new C11366(appVersion)).build();
        if (wasUpdated || isVersion154) {
            dialog.showAllowingStateLoss("");
        }
    }

    private void showOldGpsDialog() {
        new CustomDialogFragment.Builder(this).title((int) C1799R.string.title_gps_warning).body((int) C1799R.string.message_gps_warning).negativeButton((int) C1799R.string.button_cancel, null).positiveButton((int) C1799R.string.button_settings, new C11388()).build().showAllowingStateLoss("dialog_gps");
    }

    public void checkGPSEnabled() {
        if (!SygicHelper.isGPSEnabled()) {
            if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) != 0) {
                showOldGpsDialog();
            } else if (this.mGoogleApiClient != null) {
                if (this.mGoogleApiClient.isConnected() || this.mGoogleApiClient.isConnecting()) {
                    this.mGoogleApiClient.disconnect();
                }
                this.mGoogleApiClient.connect();
            } else {
                this.mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(new ConnectionCallbacks() {

                    /* renamed from: com.sygic.aura.activity.NaviNativeActivity.10.1 */
                    class C11291 implements ResultCallback<LocationSettingsResult> {
                        C11291() {
                        }

                        public void onResult(LocationSettingsResult result) {
                            Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case TTSConst.TTSEVT_SENTENCE /*6*/:
                                    try {
                                        status.startResolutionForResult(NaviNativeActivity.this, 0);
                                    } catch (SendIntentException e) {
                                    }
                                default:
                            }
                        }
                    }

                    public void onConnected(Bundle bundle) {
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setPriority(100);
                        LocationServices.SettingsApi.checkLocationSettings(NaviNativeActivity.this.mGoogleApiClient, new LocationSettingsRequest.Builder().setAlwaysShow(true).addLocationRequest(locationRequest).build()).setResultCallback(new C11291());
                    }

                    public void onConnectionSuspended(int i) {
                    }
                }).addOnConnectionFailedListener(new C11399()).build();
                this.mGoogleApiClient.connect();
            }
        }
    }

    public static boolean isInitialized() {
        return sInitialized;
    }

    public void onResume() {
        super.onResume();
        ComponentEventsReceiver.registerMapsAvailabilityListener(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (SygicHelper.hasGlSurface()) {
            PositionInfo.nativeEnableMapView();
        }
        SygicTrackerHelper.logOnResume(getContext());
        try {
            AppEventsLogger.activateApp(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RetentionNotificationUtils.resetDismissCounter(getContext());
        AlarmUtils.setupRetentionNotification(this);
    }

    protected void onStart() {
        super.onStart();
        InfinarioAnalyticsLogger.getInstance(this).startSession();
    }

    protected void onStop() {
        super.onStop();
        InfinarioAnalyticsLogger.getInstance(this).endSession();
    }

    public void onPause() {
        super.onPause();
        if (SygicHelper.hasGlSurface()) {
            PositionInfo.nativeDisableMapView();
        }
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
        ComponentEventsReceiver.unregisterMapsAvailabilityListener(this);
        SygicTrackerHelper.logOnPause(this);
        try {
            AppEventsLogger.deactivateApp(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (SettingsManager.nativeIsFirstRun() && !isFinishing()) {
            InfinarioAnalyticsLogger.getInstance(this).trackFrwWentToBackground();
        }
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            dismissPendingNotification(intent);
        }
    }

    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0 && this.mShouldAddDefaultMode) {
            Mode mode;
            if (this.mRouteFinished) {
                this.mRouteFinished = false;
                this.mRouteData.clearRouteWaypoints();
                mode = Mode.FREEDRIVE_INFO_BAR;
            } else {
                mode = Mode.FREEDRIVE_BROWSE;
            }
            MapOverlayFragment.setMode(getContext(), mode);
        }
        this.mShouldAddDefaultMode = true;
    }

    public void setShouldAddDefaultMode(boolean should) {
        this.mShouldAddDefaultMode = should;
    }

    public void toggleDrawer() {
        if (this.mNavigationDrawer != null) {
            this.mNavigationDrawer.toggleDrawer();
        }
    }

    public boolean isDrawerVisible() {
        return this.mNavigationDrawer != null && this.mNavigationDrawer.isDrawerOpen();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (super.onKeyUp(keyCode, event)) {
            return true;
        }
        switch (keyCode) {
            case C1090R.styleable.Theme_colorPrimaryDark /*82*/:
                if (!sInitialized) {
                    return false;
                }
                this.mNavigationDrawer.toggleDrawer();
                return true;
            default:
                return false;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        if (this.mNavigationDrawer.isDrawerOpen()) {
            this.mNavigationDrawer.closeDrawer();
            return;
        }
        if (!this.mBackPressedListeners.isEmpty()) {
            for (BackPressedListener listener : this.mBackPressedListeners) {
                if (listener.onBackPressed()) {
                    return;
                }
            }
        }
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            CustomDialogFragment.showExitDialog(this);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624676) {
            return false;
        }
        goToFavourites();
        return true;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mToolbar != null) {
            LayoutParams lp = this.mToolbar.getLayoutParams();
            if (lp != null) {
                int height;
                if (newConfig.orientation == 2) {
                    height = (int) getResources().getDimension(2131230758);
                    int width = (int) getResources().getDimension(2131230735);
                    this.mToolbar.setMinimumHeight(height);
                    lp.height = height;
                    lp.width = width;
                } else {
                    height = (int) getResources().getDimension(2131230758);
                    this.mToolbar.setMinimumHeight(height);
                    lp.height = height;
                    lp.width = -1;
                }
                STextView text = (STextView) this.mToolbar.findViewById(2131624470);
                SImageView icon = (SImageView) this.mToolbar.findViewById(2131624518);
                View leftDivider = this.mToolbar.findViewById(2131624517);
                View rightDivider = this.mToolbar.findViewById(2131624519);
                text.setTextSize(0, getResources().getDimension(2131231010));
                icon.resetImageDrawable();
                leftDivider.setVisibility(InCarConnection.isInCarConnected() ? 0 : 8);
                rightDivider.setVisibility(InCarConnection.isInCarConnected() ? 0 : 8);
                Menu menu = this.mToolbar.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    ((FontDrawable) menu.getItem(i).getIcon()).setTextSize(getResources().getDimension(2131230812));
                }
            }
        }
        if (!(this.mOldConfig == null || this.mOldConfig.uiMode == newConfig.uiMode)) {
            int oldType = this.mOldConfig.uiMode & 15;
            int newType = newConfig.uiMode & 15;
            if ((oldType == 3 && newType != 3) || (oldType != 3 && newType == 3)) {
                clearBackStack(false);
                new Handler(Looper.getMainLooper()).post(new AnonymousClass11(newType));
            }
        }
        this.mOldConfig = new Configuration(newConfig);
    }

    public Context getContext() {
        return this;
    }

    public SToolbar getToolbar() {
        return this.mToolbar;
    }

    public static void showKeyboard(View viewRequesting) {
        if (InCarConnection.isInCarConnected()) {
            if (mKeyboard != null) {
                mKeyboard.show();
            }
        } else if (imm != null) {
            viewRequesting.post(new AnonymousClass12(viewRequesting));
        }
    }

    public static void hideKeyboard(IBinder windowToken) {
        if (InCarConnection.isInCarConnected()) {
            if (mKeyboard != null) {
                mKeyboard.hide();
            }
        } else if (imm != null) {
            imm.hideSoftInputFromWindow(windowToken, 2);
        }
    }

    @TargetApi(19)
    public static boolean isNavigationBarHidden(Activity activity) {
        if (VERSION.SDK_INT >= 19 && (activity.getWindow().getDecorView().getSystemUiVisibility() & 2) != 2) {
            return false;
        }
        return true;
    }

    @TargetApi(19)
    public static void hideNavigationBar(Activity activity) {
        if (activity != null && VERSION.SDK_INT >= 19 && PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(activity.getString(2131166289), true)) {
            PositionInfo.nativeEnableMapView();
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 2562);
        }
    }

    @TargetApi(19)
    public static void showNavigationBar(Activity activity, boolean disableRendering) {
        if (activity != null && VERSION.SDK_INT >= 19 && PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(activity.getString(2131166289), true)) {
            if (disableRendering) {
                PositionInfo.nativeDisableMapView();
            }
            new Handler().postDelayed(new AnonymousClass13(activity), 700);
        }
    }

    public boolean registerBackPressedListener(BackPressedListener listener) {
        try {
            this.mBackPressedListeners.add(0, listener);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean unregisterBackPressedListener(BackPressedListener listener) {
        return this.mBackPressedListeners.remove(listener);
    }

    public void registerDownloadProgressNotification(String[] installIds) {
        if (this.mDownloadProgressNotificationListener != null) {
            this.mDownloadProgressNotificationListener.add(installIds);
            return;
        }
        this.mDownloadProgressNotificationListener = new DownloadProgressNotification(getContext(), installIds, ResourceManager.getCoreString(getContext(), 2131165452));
        ComponentEventsReceiver.registerEventsListener(this.mDownloadProgressNotificationListener);
    }

    public String onPhotoChosen(Uri uri) {
        return NavigateToPhotoHelper.handlePhotoChosen(getContext(), uri);
    }

    public boolean onWebViewOpenUri(String uri, String tag) {
        if (RouteManager.nativeWillRouteRestore()) {
            registerRouteRestoredListener(new AnonymousClass14(uri, tag));
        } else {
            new RepeatingThread(new AnonymousClass15(uri, tag), 300).start();
        }
        return true;
    }

    private void goToWebView(String uri, String tag) {
        runOnUiThread(new AnonymousClass16(uri, tag));
    }

    public boolean wasCrashFree() {
        return this.mWasCrashFree;
    }

    private void showShowcase() {
        String showcaseId = getString(2131166714);
        if (ShowcaseView.shouldShow(this, showcaseId)) {
            new ShowcaseView(this, showcaseId).setContentTitle(2131165897).setContentText(2131165896).setTarget(new ToolbarViewTarget(this.mToolbar, ToolbarViewType.NAV)).show();
        }
    }

    public void onMapUpdateCount(Integer count) {
        if (count.intValue() > 0 && !SettingsManager.nativeShowFirstRunWizard()) {
            showMapUpdateDialog();
        }
    }

    public void onLanguageChanged(String value) {
        ((STextView) this.mToolbar.findViewById(2131624470)).recreateCoreTextFromResId();
    }

    public void onNewsLoaded(ArrayList<NewsItem> entries) {
        if (entries != null && !entries.isEmpty()) {
            String appVersion = StringHelper.cutVersionString(SettingsManager.nativeGetVersion(), 2);
            String previousVersion = StringHelper.cutVersionString(getPreviousVersion("news_previous_version"), 2);
            boolean isFirstRun = SettingsManager.nativeShowFirstRunWizard();
            if ((!previousVersion.equals(appVersion)) && !isFirstRun) {
                Fragments.add(this, NewsFragment.class, "fragment_whats_new_tag", null);
                setPreviousVersion(appVersion, "news_previous_version");
                NewsEventReceiver.unregisterNewsListener(this);
            }
        }
    }

    public void onNewLoadingFailed() {
        CrashlyticsHelper.logWarning(NewsFragment.TAG, "loading news failed");
    }

    public static void registerKeyboard(FocusableKeyboardView view) {
        if (mKeyboard == null) {
            mKeyboard = new FocusableKeyboard(SygicHelper.getActivity(), view);
        } else {
            mKeyboard.setKeyboardView(view);
        }
    }

    public static FocusableKeyboard getKeyboard() {
        if (mKeyboard == null) {
            registerKeyboard(null);
        }
        return mKeyboard;
    }
}
