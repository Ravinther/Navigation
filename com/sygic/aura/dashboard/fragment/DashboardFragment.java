package com.sygic.aura.dashboard.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.blackbox.fragment.BlackBoxFragment;
import com.sygic.aura.blackbox.interfaces.BlackBoxFragmentResultCallback;
import com.sygic.aura.dashboard.DashboardAction;
import com.sygic.aura.dashboard.DashboardDragAndDropGridView;
import com.sygic.aura.dashboard.DashboardDragAndDropGridView.DragAndDropListener;
import com.sygic.aura.dashboard.DashboardGridView;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.DashboardPluginAdapter;
import com.sygic.aura.dashboard.DashboardScrollingStrategy;
import com.sygic.aura.dashboard.WidgetManager;
import com.sygic.aura.dashboard.navigationDrawer.NavigationDrawer;
import com.sygic.aura.dashboard.plugins.BlackBoxDashPlugin;
import com.sygic.aura.dashboard.plugins.HomeDashPlugin;
import com.sygic.aura.dashboard.plugins.ParkingPlugin;
import com.sygic.aura.dashboard.plugins.WorkDashPlugin;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.feature.automotive.InCarConnectionListener;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.ConfigurationSettingsFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.fragments.interfaces.LoginFragmentResultCallback;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.AccountEventsReceiver;
import com.sygic.aura.helper.EventReceivers.ComponentEventsReceiver;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.WidgetEventsReceiver;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.ParcelableStringSparseArray;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.TransitionManagerCompat;
import com.sygic.aura.helper.interfaces.AccountListener;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.helper.interfaces.MapsAvailabilityListener;
import com.sygic.aura.helper.interfaces.MemoListener;
import com.sygic.aura.helper.interfaces.NavigationDrawerListener;
import com.sygic.aura.helper.interfaces.WidgetListener;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.map.MemoManager;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.network.AccountManager;
import com.sygic.aura.network.AccountManager.ELoginStatus;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.pluginmanager.PluginManagerFragment.PluginManagerCallback;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.first_run.fragments.FirstRunWizardStartFragment;
import com.sygic.aura.settings.fragments.LoginFragment;
import com.sygic.aura.settings.fragments.SettingsFragment;
import com.sygic.aura.settings.trial.fragments.PromotionFragment;
import com.sygic.aura.store.fragment.ComponentsFragment;
import com.sygic.aura.store.fragment.MarketPlaceFragment;
import com.sygic.aura.views.font_specials.STextView;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends AbstractFragment implements Callback, OnClickListener, OnItemClickListener, OnItemLongClickListener, DragAndDropListener, FavoritesFragmentResultCallback, InCarConnectionListener, ConfigurationSettingsFragmentResultCallback, LoginFragmentResultCallback, AccountListener, MapsAvailabilityListener, MemoListener, NavigationDrawerListener, WidgetListener, PluginManagerCallback {
    private STextView mAccountTextView;
    private DashboardPluginAdapter mAdapter;
    private View mContainerView;
    private Context mContext;
    private ActionMode mDashWidgetsContextActionBar;
    private DashboardAction mDashboardAction;
    private DashboardGridView mDragAndDropStaggeredGridView;
    protected NavigationDrawer mNavigationDrawer;
    private final List<DashboardPlugin> mPlugins;
    private boolean mResetFragment;
    private int mUpdateNumber;

    /* renamed from: com.sygic.aura.dashboard.fragment.DashboardFragment.1 */
    class C11641 implements OnClickListener {
        C11641() {
        }

        public void onClick(View v) {
            String category = LicenseInfo.nativeIsTrialExpired() ? "anui_dashboard_trial_expired" : "anui_dashboard_trial_left";
            Bundle params = new Bundle();
            params.putString("source", category);
            Fragments.add(DashboardFragment.this.getActivity(), PromotionFragment.class, "fragment_promotion_tag", params);
            DashboardFragment.this.mNavigationDrawer.closeDrawerDelayed();
            params.clear();
            params.putString("eventName", "click");
            params.putString("category", category);
            SygicAnalyticsLogger.logEvent(DashboardFragment.this.getActivity(), EventType.CLICK, params);
        }
    }

    /* renamed from: com.sygic.aura.dashboard.fragment.DashboardFragment.2 */
    class C11652 implements Runnable {
        C11652() {
        }

        public void run() {
            DashboardFragment.this.mNavigationDrawer.toggleDrawer();
        }
    }

    /* renamed from: com.sygic.aura.dashboard.fragment.DashboardFragment.3 */
    class C11663 implements BlackBoxFragmentResultCallback {
        final /* synthetic */ BlackBoxDashPlugin val$dashboardPlugin;

        C11663(BlackBoxDashPlugin blackBoxDashPlugin) {
            this.val$dashboardPlugin = blackBoxDashPlugin;
        }

        public void onBlackBoxRecordStarted() {
            this.val$dashboardPlugin.setRecording(true);
            DashboardFragment.this.refresh();
            DashboardFragment.this.mNavigationDrawer.closeDrawer();
        }

        public void onBlackBoxRecordFinished(boolean isFullscreen) {
            this.val$dashboardPlugin.setRecording(false);
            DashboardFragment.this.refresh();
            DashboardFragment.this.mNavigationDrawer.closeDrawer();
        }

        public void onBlackBoxMaximized() {
        }

        public void onBlackBoxMinimized() {
        }
    }

    /* renamed from: com.sygic.aura.dashboard.fragment.DashboardFragment.4 */
    class C11674 implements Runnable {
        C11674() {
        }

        public void run() {
            DashboardFragment.this.setLoginItem(AccountManager.nativeIsLoggedIn());
            DashboardFragment.this.checkUpdateNumber();
        }
    }

    /* renamed from: com.sygic.aura.dashboard.fragment.DashboardFragment.5 */
    class C11685 implements DialogInterface.OnClickListener {
        C11685() {
        }

        public void onClick(DialogInterface dialog, int which) {
            boolean wasRemoved = false;
            for (int pluginIndex = DashboardFragment.this.mPlugins.size() - 1; pluginIndex >= 0; pluginIndex--) {
                DashboardPlugin plugin = (DashboardPlugin) DashboardFragment.this.mPlugins.get(pluginIndex);
                if (DashboardFragment.this.mAdapter.isSelected(pluginIndex) && plugin.delete(DashboardFragment.this.getActivity())) {
                    DashboardFragment.this.mAdapter.remove(plugin);
                    wasRemoved = true;
                }
            }
            if (wasRemoved) {
                DashboardFragment.this.saveWidgets();
                DashboardFragment.this.refresh();
                DashboardFragment.this.setEditMode(false);
            }
        }
    }

    private static class DashboardFragmentAction implements DashboardAction {
        private final Activity mActivity;
        private final boolean mAddToBackStack;
        private final Bundle mArgs;
        private final FragmentResultCallback mCallback;
        private final Class<? extends AbstractScreenFragment> mFClass;
        private final String mTag;

        public DashboardFragmentAction(Activity activity, Class<? extends AbstractScreenFragment> fClass, String tag, Bundle args, boolean addToBackStack, FragmentResultCallback callback) {
            this.mActivity = activity;
            this.mFClass = fClass;
            this.mTag = tag;
            this.mArgs = args;
            this.mAddToBackStack = addToBackStack;
            this.mCallback = callback;
        }

        public DashboardFragmentAction(Activity activity, Class<? extends AbstractScreenFragment> fClass, String tag, Bundle args, boolean addToBackStack) {
            this(activity, fClass, tag, args, addToBackStack, null);
        }

        public void performAction(DashboardFragment dashboardFragment) {
            Fragments.add(this.mActivity, this.mFClass, this.mTag, this.mArgs, this.mAddToBackStack, this.mCallback, 17432576, 17432577);
        }
    }

    public static DashboardFragment newInstance(RouteNavigateData navigateData, int launchMode) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AbstractFragment.NAVIGATE_DATA, navigateData);
        bundle.putInt("arg_launch_mode", launchMode);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DashboardFragment() {
        this.mContext = null;
        this.mContainerView = null;
        this.mAdapter = null;
        this.mDragAndDropStaggeredGridView = null;
        this.mResetFragment = false;
        this.mUpdateNumber = 0;
        this.mPlugins = new ArrayList();
        this.mDashWidgetsContextActionBar = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadNavigationData();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903100, container, false);
        this.mContainerView = view.findViewById(2131624177);
        this.mDragAndDropStaggeredGridView = (DashboardGridView) this.mContainerView.findViewById(2131624187);
        this.mContext = inflater.getContext();
        this.mDragAndDropStaggeredGridView.setScrollContainer(false);
        this.mDragAndDropStaggeredGridView.setScrollingStrategy(new DashboardScrollingStrategy(this.mContainerView));
        this.mDragAndDropStaggeredGridView.setScrollButtons(view.findViewById(2131623941), view.findViewById(2131623940));
        this.mAdapter = new DashboardPluginAdapter(this.mContext, this.mPlugins);
        setupTrialBanner((STextView) this.mContainerView.findViewById(2131624178));
        View accountView = this.mContainerView.findViewById(2131624179);
        if (accountView != null) {
            accountView.setOnClickListener(this);
            this.mAccountTextView = (STextView) accountView.findViewById(2131624180);
            if (AccountManager.nativeIsLoggedIn()) {
                this.mAccountTextView.setText(AccountManager.nativeGetUserName());
            }
        }
        View marketPlaceView = this.mContainerView.findViewById(2131624181);
        if (marketPlaceView != null) {
            marketPlaceView.setOnClickListener(this);
        }
        View settingsView = this.mContainerView.findViewById(2131624185);
        if (settingsView != null) {
            settingsView.setOnClickListener(this);
        }
        View manageMapsView = this.mContainerView.findViewById(2131624183);
        if (manageMapsView != null) {
            manageMapsView.setOnClickListener(this);
        }
        this.mDragAndDropStaggeredGridView.setAdapter(this.mAdapter);
        this.mDragAndDropStaggeredGridView.setOnItemClickListener(this);
        this.mDragAndDropStaggeredGridView.setOnItemLongClickListener(this);
        this.mDragAndDropStaggeredGridView.addCalculateChildrenPositionListener(this.mAdapter);
        this.mDragAndDropStaggeredGridView.setEnabled(ComponentManager.nativeGetInstalledMapCount() > 0);
        if (this.mDragAndDropStaggeredGridView instanceof DashboardDragAndDropGridView) {
            ((DashboardDragAndDropGridView) this.mDragAndDropStaggeredGridView).setDragAndDropListener(this);
        }
        setEditMode(false);
        loadWidgets();
        MapEventsReceiver.registerMemoListener(this);
        ComponentEventsReceiver.registerMapsAvailabilityListener(this);
        if (this.mNavigationDrawer != null) {
            this.mNavigationDrawer.registerAnimationDrawerListener(this);
        }
        WndEventsReceiver.registerAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        WidgetEventsReceiver.registerWidgetListener(this);
        AccountEventsReceiver.registerAccountListener(this);
        InCarConnection.registerOnConnectionListener(this);
        return view;
    }

    private void setupTrialBanner(STextView trialView) {
        if (LicenseInfo.nativeIsTrial() && trialView != null) {
            trialView.setVisibility(0);
            trialView.setOnClickListener(new C11641());
            if (LicenseInfo.nativeIsTrialExpired()) {
                trialView.setBackgroundResource(2131558450);
                trialView.setText(ResourceManager.getCoreString(getActivity(), 2131165385).toUpperCase());
                return;
            }
            trialView.setText(((String) trialView.getText()).replace("%days%", Integer.toString(LicenseInfo.nativeGetTrialDays())));
        }
    }

    public void onStart() {
        super.onStart();
        autostartBlackbox();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mNavigationDrawer = (NavigationDrawer) activity.findViewById(2131624515);
        if (getArguments().getInt("arg_launch_mode", 0) == 1) {
            Bundle bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString((Context) activity, 2131165471));
            Fragments.add(getActivity(), FirstRunWizardStartFragment.class, "fragment_frw_start_tag", bundle, false, null);
        }
    }

    public NavigationDrawer getNavigationDrawer() {
        return this.mNavigationDrawer;
    }

    public void loadWidgets() {
        WidgetManager.getDashPlugins(this.mPlugins);
        refresh();
    }

    public void saveWidgets() {
        this.mDragAndDropStaggeredGridView.requestCalculateChildrenPositions();
        for (DashboardPlugin plugin : this.mPlugins) {
            plugin.persist();
        }
    }

    public void setEditMode(boolean editMode) {
        boolean z;
        if (editMode && this.mDashWidgetsContextActionBar == null) {
            this.mDashWidgetsContextActionBar = ((ActionBarActivity) getActivity()).startSupportActionMode(this);
        }
        if (!editMode) {
            this.mAdapter.setEditMode(false);
            if (this.mDashWidgetsContextActionBar != null) {
                this.mDashWidgetsContextActionBar.finish();
                this.mDashWidgetsContextActionBar = null;
            }
        }
        DashboardGridView dashboardGridView = this.mDragAndDropStaggeredGridView;
        if (editMode) {
            z = false;
        } else {
            z = true;
        }
        dashboardGridView.setIsTransitionsEnabled(z);
    }

    public boolean getEditMode() {
        return this.mAdapter.getEditMode();
    }

    public ParcelableStringSparseArray getSortedPluginNamesByType() {
        ParcelableStringSparseArray pluginNamesSparseArray = new ParcelableStringSparseArray();
        Resources resources = this.mContext.getResources();
        for (DashboardPlugin plugin : this.mPlugins) {
            plugin.addToSparseArray(resources, pluginNamesSparseArray);
        }
        return pluginNamesSparseArray;
    }

    public boolean addPlugin(DashboardPlugin plugin) {
        int count = this.mAdapter.getCount();
        if (count >= 1) {
            this.mAdapter.insertPlugin(plugin, count - 1);
            this.mDragAndDropStaggeredGridView.requestCalculateChildrenPositions();
        }
        boolean isAdded = plugin.persist();
        if (!isAdded) {
            this.mAdapter.remove(plugin);
        }
        return isAdded;
    }

    public void refresh() {
        this.mAdapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        boolean z = false;
        DashboardAction action = null;
        int id = view.getId();
        Bundle bundle;
        if (id == 2131624183) {
            bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(this.mContext, 2131165799));
            String str = "fragment_has_update";
            if (this.mUpdateNumber > 0) {
                z = true;
            }
            bundle.putBoolean(str, z);
            action = new DashboardFragmentAction(getActivity(), ComponentsFragment.class, "fragment_manage_maps", bundle, true);
        } else if (id == 2131624185) {
            bundle = new Bundle();
            bundle.putInt(SettingsFragment.ARG_XML, 2131034348);
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(this.mContext, 2131165381));
            bundle.putInt(SettingsFragment.ARG_MENU, 2131755030);
            bundle.putBoolean(SettingsFragment.ARG_CHANGE_SETTINGS, true);
            action = new DashboardFragmentAction(getActivity(), SettingsFragment.class, "fragment_settings_tag", bundle, true, this);
        } else if (id == 2131624179) {
            bundle = new Bundle();
            if (AccountManager.nativeIsLoggedIn()) {
                bundle.putInt(SettingsFragment.ARG_XML, 2131034112);
                bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(this.mContext, 2131165315));
                bundle.putInt(SettingsFragment.ARG_MENU, 2131755008);
                bundle.putBoolean(SettingsFragment.ARG_CHANGE_SETTINGS, true);
                action = new DashboardFragmentAction(getActivity(), SettingsFragment.class, "fragment_settings_tag", bundle, true, this);
            } else {
                bundle.putInt(SettingsFragment.ARG_XML, 2131034116);
                bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(this.mContext, 2131165382));
                bundle.putBoolean(SettingsFragment.ARG_LAST_DIVIDER, false);
                action = new DashboardFragmentAction(getActivity(), LoginFragment.class, "fragment_settings_tag", bundle, true, this);
            }
        } else if (id == 2131624181) {
            bundle = new Bundle();
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(this.mContext, 2131165377));
            bundle.putString("source", "DashboardFragment");
            action = new DashboardFragmentAction(getActivity(), MarketPlaceFragment.class, "fragment_sygic_store_tag", bundle, true, this);
        }
        if (action != null) {
            this.mDashboardAction = action;
        }
        this.mNavigationDrawer.closeDrawer();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (getEditMode()) {
            onHandleItemClickForEditMode(view, position);
        } else {
            onHandleItemClickDefaultMode(position);
        }
    }

    void onHandleItemClickForEditMode(View view, int position) {
        boolean newState = false;
        if (((DashboardPlugin) this.mAdapter.getItem(position)).isEditable()) {
            if (!this.mAdapter.isSelected(position)) {
                newState = true;
            }
            this.mAdapter.setSelected(position, newState);
            view.setSelected(newState);
            setEditMode(this.mAdapter.hasSelected());
            return;
        }
        this.mAdapter.setSelected(position, false);
        view.setSelected(false);
    }

    void onHandleItemClickDefaultMode(int position) {
        this.mDashboardAction = (DashboardPlugin) this.mAdapter.getItem(position);
        this.mNavigationDrawer.closeDrawer();
    }

    void onHandleLongItemClickDefaultMode(int position) {
        if (((DashboardPlugin) this.mAdapter.getItem(position)).isEditable()) {
            setEditMode(true);
            if (this.mDragAndDropStaggeredGridView instanceof DashboardDragAndDropGridView) {
                ((DashboardDragAndDropGridView) this.mDragAndDropStaggeredGridView).startDragAndDrop();
            }
        }
    }

    public void onDropItem(int startPosition, int endPosition) {
        if (!((DashboardPlugin) this.mPlugins.get(endPosition)).isEditable()) {
            return;
        }
        if (startPosition != endPosition) {
            this.mPlugins.add(endPosition, this.mPlugins.remove(startPosition));
            refresh();
            saveWidgets();
        } else if (this.mDashWidgetsContextActionBar != null) {
            this.mAdapter.setEditMode(true);
            this.mAdapter.setSelected(endPosition, true);
        }
    }

    public boolean isDragAndDropEnabled(int from) {
        return ((DashboardPlugin) this.mPlugins.get(from)).isEditable();
    }

    public void onDragItem(int from) {
    }

    public void onDraggingItem(int from, int to) {
        if (this.mDashWidgetsContextActionBar != null) {
            this.mDashWidgetsContextActionBar.finish();
            this.mDashWidgetsContextActionBar = null;
        }
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (!(getEditMode() || InCarConnection.isInCarConnected())) {
            onHandleLongItemClickDefaultMode(position);
        }
        return true;
    }

    public void onMemoRemoved(Integer widgetItemId) {
        for (DashboardPlugin plugin : this.mPlugins) {
            if (plugin.getWidgetId() == widgetItemId.intValue()) {
                if (plugin.memoRemoved(getActivity())) {
                    this.mAdapter.remove(plugin);
                }
                saveWidgets();
                refresh();
                return;
            }
        }
    }

    public void onFavoritesFragmentResult(FavoritesItem result) {
        this.mDragAndDropStaggeredGridView.requestCalculateChildrenPositions();
        refresh();
    }

    public void onAddWork(LongPosition longPosition, String strName) {
        WorkDashPlugin plugin = (WorkDashPlugin) this.mAdapter.getPluginByType(WorkDashPlugin.class);
        if (plugin != null) {
            updatePlugin(plugin, MemoManager.nativeGetWork());
        }
    }

    public void onAddHome(LongPosition longPosition, String strName) {
        HomeDashPlugin plugin = (HomeDashPlugin) this.mAdapter.getPluginByType(HomeDashPlugin.class);
        if (plugin != null) {
            updatePlugin(plugin, MemoManager.nativeGetHome());
        }
    }

    public void onAddParkingLocation(LongPosition longPosition, String strName) {
        ParkingPlugin plugin = (ParkingPlugin) this.mAdapter.getPluginByType(ParkingPlugin.class);
        if (plugin != null) {
            updatePlugin(plugin, MemoManager.nativeGetParking());
        }
    }

    private void updatePlugin(DashboardPlugin plugin, MemoItem memoItem) {
        plugin.fillPluginData(memoItem);
        this.mDragAndDropStaggeredGridView.requestCalculateChildrenPositions();
        plugin.persist();
        refresh();
    }

    public void onPluginsChanged() {
        loadWidgets();
    }

    public void onShorcutAdded(FavoritesItem item) {
        if (item != null && item.getLongPosition().isValid()) {
            DashboardPlugin newPlugin = item.createDashPlugin();
            newPlugin.fillPluginData(getActivity(), item);
            addPlugin(newPlugin);
            this.mDragAndDropStaggeredGridView.fullScroll(this.mContainerView);
        }
        toggleDrawer(item == null);
    }

    public void toggleDrawer(boolean immediate) {
        if (immediate) {
            this.mNavigationDrawer.toggleDrawer();
        } else {
            this.mNavigationDrawer.postDelayed(new C11652(), 300);
        }
    }

    public void startBlackboxRecording(FragmentManagerInterface manager, BlackBoxDashPlugin dashboardPlugin) {
        manager.addFragment(BlackBoxFragment.class, "fragment_black_box_tag", false, new C11663(dashboardPlugin));
    }

    private void autostartBlackbox() {
        if (WidgetManager.nativeIsBlackBoxAllowed() && PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(getString(2131166268), false) && BlackBoxFragment.getBlackBoxFragment(getFragmentManager()) == null) {
            BlackBoxDashPlugin blackBoxWidget = (BlackBoxDashPlugin) this.mAdapter.getPluginByType(BlackBoxDashPlugin.class);
            if (blackBoxWidget != null) {
                startBlackboxRecording(SygicHelper.getFragmentActivityWrapper(), blackBoxWidget);
            }
        }
    }

    public void onDestroyView() {
        if (this.mDashboardAction != null) {
            this.mDashboardAction = null;
        }
        if (this.mNavigationDrawer != null) {
            this.mNavigationDrawer.unregisterAnimationDrawerListener(this);
        }
        if (this.mDragAndDropStaggeredGridView != null) {
            this.mDragAndDropStaggeredGridView.removeCalculateChildrenPositionListener(this.mAdapter);
        }
        ComponentEventsReceiver.unregisterMapsAvailabilityListener(this);
        MapEventsReceiver.unregisterMemoListener(this);
        WndEventsReceiver.unregisterAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        WidgetEventsReceiver.unregisterWidgetListener(this);
        AccountEventsReceiver.unregisterAccountListener(this);
        InCarConnection.unregisterOnConnectionListener(this);
        super.onDestroyView();
    }

    public void onDrawerFinished(boolean isClosed) {
        if (isClosed) {
            setEditMode(false);
        } else {
            checkUpdateNumber();
        }
    }

    public void onDrawerStateIdle() {
        if (this.mDashboardAction != null) {
            this.mDashboardAction.performAction(this);
            this.mDashboardAction = null;
        }
    }

    public void onMapsAvailabilityChanged(Boolean mapsAvailable) {
        this.mDragAndDropStaggeredGridView.setEnabled(mapsAvailable.booleanValue());
    }

    private void setLoginItem(boolean bLogged) {
        if (bLogged) {
            String userName = AccountManager.nativeGetUserName();
            if (this.mAccountTextView.getPaint().measureText(userName) <= ((float) this.mAccountTextView.getWidth()) || !userName.contains("@")) {
                this.mAccountTextView.setText(userName);
                return;
            } else {
                this.mAccountTextView.setText(userName.substring(0, userName.indexOf("@")));
                return;
            }
        }
        this.mAccountTextView.setCoreText(2131165382);
    }

    public void onLoginFragmentResult(boolean result) {
        setLoginItem(result);
        if (result) {
            Bundle bundle = new Bundle();
            bundle.putInt(SettingsFragment.ARG_XML, 2131034112);
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(this.mContext, 2131165315));
            bundle.putInt(SettingsFragment.ARG_MENU, 2131755008);
            bundle.putBoolean(SettingsFragment.ARG_CHANGE_SETTINGS, true);
            Fragments.add(getActivity(), SettingsFragment.class, "fragment_account_tag", bundle, true, this);
        }
    }

    public void onDashboardFragmentFinished() {
        if (this.mResetFragment) {
            this.mResetFragment = false;
            resetFragment();
            return;
        }
        this.mAccountTextView.postDelayed(new C11674(), 300);
    }

    private void checkUpdateNumber() {
        int number = SettingsManager.nativeGetUpdateNumber();
        if (number != this.mUpdateNumber) {
            this.mUpdateNumber = number;
            ViewGroup manageMapsContainer = (ViewGroup) this.mContainerView.findViewById(2131624183);
            ImageView notifications = (ImageView) manageMapsContainer.findViewById(2131624184);
            TransitionManagerCompat.beginDelayedTransition(manageMapsContainer);
            notifications.setVisibility(number > 0 ? 0 : 8);
        }
    }

    public void onLanguageChanged() {
        this.mResetFragment = true;
    }

    private void resetFragment() {
        try {
            Fragment fragment = (Fragment) getClass().newInstance();
            Bundle arguments = getArguments();
            arguments.putInt("arg_launch_mode", 0);
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction().replace(2131624521, fragment, "fragment_dashboard_tag").commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAddHudPlugin() {
        loadWidgets();
    }

    public void onUpdateWidgets() {
        loadWidgets();
    }

    public void displayToast(String message) {
    }

    public void onLoginFinished(Boolean connected, ELoginStatus status) {
        setLoginItem(connected.booleanValue());
        InfinarioAnalyticsLogger infinarioLogger = InfinarioAnalyticsLogger.getInstance(getActivity());
        infinarioLogger.trackSignInOut(connected.booleanValue());
        infinarioLogger.identify();
        infinarioLogger.update();
    }

    public void onDownloadCompleted() {
    }

    public void showWaitingDialog() {
    }

    public void showWaitingDialogMessage(Integer result) {
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(2131755009, menu);
        return true;
    }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() != 2131624666 || !this.mAdapter.hasSelected()) {
            return false;
        }
        new Builder(this.mContext).title(2131165373).body(2131165372).negativeButton(2131165351, null).positiveButton(2131165355, new C11685()).build().showAllowingStateLoss("remove_dashboard_dialog");
        return true;
    }

    public void onDestroyActionMode(ActionMode mode) {
        this.mDashWidgetsContextActionBar = null;
        setEditMode(false);
    }

    public void onConnectionChanged(boolean connected) {
        if (connected) {
            DashboardFragment df = new DashboardFragmentInCar();
            df.setArguments(getArguments());
            getFragmentManager().beginTransaction().replace(2131624521, df, "fragment_dashboard_tag").commit();
        }
    }
}
