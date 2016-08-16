package com.sygic.aura.settings.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fcd.FloatingCarDataService;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.BaseDashboardFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.ConfigurationSettingsFragmentResultCallback;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.bubble.BubbleManager;
import com.sygic.aura.network.AccountManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.settings.StyleablePreferenceGroupAdapter;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.EInfoShowType;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import com.sygic.aura.settings.data.SettingsManager.ESoundSettingsType;
import com.sygic.aura.settings.preferences.FragmentPreference;
import com.sygic.aura.settings.preferences.SettingsFragmentPreference;
import com.sygic.aura.views.ButtonScrollListView;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.base.C1799R;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SettingsFragment extends AbstractScreenFragment implements OnSharedPreferenceChangeListener {
    public static final String ARG_CHANGE_SETTINGS = "change_pref";
    public static final String ARG_KEY = "key";
    public static final String ARG_LAST_DIVIDER = "last_divider";
    public static final String ARG_MENU = "menu";
    public static final String ARG_XML = "xml";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final String LOG_NAME = "SettingsFragment";
    private static final int MSG_BIND_PREFERENCES = 0;
    private final Handler mHandler;
    private boolean mHasLastDivider;
    private boolean mHasSelector;
    private int mMenuId;
    private boolean mNavigationBarChanged;
    private PreferenceManager mPreferenceManager;
    private View mRootView;
    private String mTitle;
    private int mXmlId;

    /* renamed from: com.sygic.aura.settings.fragments.SettingsFragment.1 */
    class C16491 implements OnMenuItemClickListener {
        C16491() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return SettingsFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.settings.fragments.SettingsFragment.2 */
    class C16502 implements OnClickListener {
        C16502() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SettingsManager.nativeResetToDefault();
            PreferenceManager.getDefaultSharedPreferences(SettingsFragment.this.getActivity()).edit().clear().commit();
            SettingsManager.initFromCore(SettingsFragment.this.getActivity());
            if (RouteManager.nativeExistValidRoute()) {
                FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
                if (mngr != null && !mngr.clearBackStackRunning()) {
                    RouteSummary.nativeCancelRoute();
                    mngr.clearBackStack(true);
                }
            }
        }
    }

    private static class MessageHandler extends Handler {
        private final SettingsFragment mFragContext;

        public MessageHandler(SettingsFragment fragContext) {
            this.mFragContext = fragContext;
        }

        public void handleMessage(Message message) {
            if (message.what == 0) {
                this.mFragContext.bindPreferences();
            }
        }
    }

    public SettingsFragment() {
        this.mHandler = new MessageHandler(this);
        this.mMenuId = 0;
        this.mHasLastDivider = true;
        this.mHasSelector = true;
        this.mNavigationBarChanged = false;
    }

    protected void setTitle(String title) {
        this.mTitle = title;
    }

    protected void setXmlId(int xmlId) {
        this.mXmlId = xmlId;
    }

    protected void setMenuId(int menuId) {
        this.mMenuId = menuId;
    }

    public void setHasLastDivider(boolean enable) {
        this.mHasLastDivider = enable;
    }

    public void setHasSelector(boolean enable) {
        this.mHasSelector = enable;
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(this.mTitle);
        if (this.mMenuId > 0) {
            toolbar.inflateMenu(this.mMenuId);
            toolbar.setOnMenuItemClickListener(new C16491());
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 2131624664) {
            if (!AccountManager.nativeLogOut()) {
                return false;
            }
            getActivity().onBackPressed();
            return true;
        } else if (id == 2131624665) {
            AccountManager.nativeClearUserLoginData();
            getActivity().onBackPressed();
            return true;
        } else if (id != 2131624695) {
            return super.onOptionsItemSelected(item);
        } else {
            new Builder(getActivity()).title(2131165829).body(2131165830).negativeButton(2131165354, null).positiveButton(2131165364, new C16502()).build().showAllowingStateLoss("reset_dialog");
            return true;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        postBindPreferences();
        this.mRootView = inflater.inflate(2130903278, container, false);
        ButtonScrollListView listView = (ButtonScrollListView) this.mRootView.findViewById(16908298);
        View buttonListView = this.mRootView.findViewById(2131624135);
        if (buttonListView != null) {
            Drawable background = this.mRootView.getBackground();
            if (background instanceof ColorDrawable) {
                buttonListView.setBackgroundColor(((ColorDrawable) background).getColor());
            }
            listView.initButtonScroll(this.mRootView, 2131624136, 2131624138);
        }
        SharedPreferences preferences = getSharedPreferences();
        if (preferences != null && getArguments().getBoolean(ARG_CHANGE_SETTINGS, false)) {
            preferences.registerOnSharedPreferenceChangeListener(this);
        }
        return this.mRootView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences preferences = getSharedPreferences();
        if (preferences != null) {
            preferences.unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    private SharedPreferences getSharedPreferences() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            return preferenceScreen.getSharedPreferences();
        }
        return null;
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z = true;
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mXmlId = bundle.getInt(ARG_XML);
            this.mTitle = bundle.getString(AbstractFragment.ARG_TITLE);
            this.mMenuId = bundle.getInt(ARG_MENU);
            this.mHasLastDivider = bundle.getBoolean(ARG_LAST_DIVIDER, true);
        }
        Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        String string = getString(2131165289);
        if (SettingsManager.nativeGetSettings(ESettingsType.eLoginStart) <= 0) {
            z = false;
        }
        edit.putBoolean(string, z).commit();
        this.mPreferenceManager = onCreatePreferenceManager();
        initPreferencesFromResource();
        if (this.mXmlId == 2131034348) {
            InfinarioAnalyticsLogger.getInstance(getActivity()).trackAppActionOpenSettings();
        }
        setupRoadNumberDependency();
    }

    private void initPreferencesFromResource() {
        addPreferencesFromResource(this.mXmlId);
        postBindPreferences();
    }

    public void onStop() {
        super.onStop();
        try {
            Method m = PreferenceManager.class.getDeclaredMethod("dispatchActivityStop", new Class[0]);
            m.setAccessible(true);
            m.invoke(this.mPreferenceManager, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mRootView = null;
        try {
            Method m = PreferenceManager.class.getDeclaredMethod("dispatchActivityDestroy", new Class[0]);
            m.setAccessible(true);
            m.invoke(this.mPreferenceManager, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mResultCallback != null) {
            ((BaseDashboardFragmentResultCallback) this.mResultCallback).onDashboardFragmentFinished();
        }
    }

    @SuppressLint({"InlinedApi"})
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (sharedPreferences.contains(key)) {
            Object value = handlePreferenceKey(sharedPreferences, key);
            if (value != null) {
                SygicAnalyticsLogger.getAnalyticsEvent(getActivity(), EventType.SETTINGS_CHANGE).setName("Settings changed").setValue(key, value).logAndRecycle();
            }
        }
    }

    private Object handlePreferenceKey(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(2131166344))) {
            SettingsFragmentPreference pref = (SettingsFragmentPreference) findPreference(getString(2131166318));
            if (pref == null) {
                return null;
            }
            String text;
            if (sharedPreferences.getBoolean(key, false)) {
                text = ResourceManager.getCoreString(pref.getContext(), 2131165692);
            } else {
                text = ResourceManager.getCoreString(pref.getContext(), 2131165690);
            }
            pref.setSummary(text);
            return text;
        }
        if (key.equals(getString(2131166746))) {
            boolean bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eRailway, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166755))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eSpeedcam, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166748))) {
            int nValue = sharedPreferences.getInt(key, -1) * FIRST_REQUEST_CODE;
            SettingsManager.nativeSetSettings(ESettingsType.eRailwayDistance, nValue);
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166760))) {
            nValue = sharedPreferences.getInt(key, -1) * FIRST_REQUEST_CODE;
            SettingsManager.nativeSetSettings(ESettingsType.eSpeedcamIn, nValue);
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166761))) {
            nValue = sharedPreferences.getInt(key, -1) * FIRST_REQUEST_CODE;
            SettingsManager.nativeSetSettings(ESettingsType.eSpeedcamOut, nValue);
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166762))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eSpeedLimit, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166766))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eWeatherSpeedLimit, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166764))) {
            nValue = sharedPreferences.getInt(key, -1);
            SettingsManager.nativeSetSettings(ESettingsType.eSpeedLimitIn, nValue);
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166765))) {
            nValue = sharedPreferences.getInt(key, -1);
            SettingsManager.nativeSetSettings(ESettingsType.eSpeedLimitOut, nValue);
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166767))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eNotifyTraffic, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166769))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eSpeakerOutput, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(C1799R.string.settings_sound_bluetooth_hfp))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eUseBluetoothHfp, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        int value;
        if (key.equals(getString(2131166737))) {
            value = strSettingsToInt(sharedPreferences, key);
            Editor editor = sharedPreferences.edit();
            if (value == 0) {
                editor.putBoolean(getString(2131166769), true);
                editor.putBoolean(getString(C1799R.string.settings_sound_bluetooth_hfp), false);
                editor.apply();
            } else if (value == 1) {
                editor.putBoolean(getString(2131166769), false);
                editor.putBoolean(getString(C1799R.string.settings_sound_bluetooth_hfp), false);
                editor.apply();
            } else if (value == 2) {
                editor.putBoolean(getString(2131166769), false);
                editor.putBoolean(getString(C1799R.string.settings_sound_bluetooth_hfp), true);
                editor.apply();
            }
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166772))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eVoiceInstr, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166770))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eRoadNum, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131165289))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eLoginStart, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131165285))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eConnectionAsk, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131165290))) {
            Activity activity = getActivity();
            boolean allowed = sharedPreferences.getBoolean(key, true);
            SettingsManager.nativeSetSettings(ESettingsType.eAccountTraffic, allowed);
            if (allowed) {
                activity.startService(new Intent(activity, FloatingCarDataService.class));
            } else {
                activity.stopService(new Intent(activity, FloatingCarDataService.class));
            }
            return Boolean.valueOf(allowed);
        }
        if (key.equals(getString(2131165288))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eAccountNewUpdates, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166288))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eDayNight, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166298))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eLaneAssist, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166297))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eJunctionView, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166300))) {
            nValue = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eSecondaryInstruction, SettingsManager.secondaryDirectionToCore(nValue));
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166299))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eRotationLock, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166289))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eFullScreen, bOnOff);
            if (VERSION.SDK_INT >= 19) {
                View decorView = getActivity().getWindow().getDecorView();
                int uiOptions = decorView.getSystemUiVisibility();
                decorView.setSystemUiVisibility(bOnOff ? ((uiOptions | 512) | 2) | 2048 : ((uiOptions & -513) & -3) & -2049);
            }
            getActivity().getWindow().addFlags(bOnOff ? 1024 : 2048);
            getActivity().getWindow().clearFlags(bOnOff ? 2048 : 1024);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166709))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eZoomControls, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166710))) {
            bOnOff = sharedPreferences.getBoolean(key, true);
            SettingsManager.nativeSetSettings(ESettingsType.eOfferParking, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166312))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eAutoZoom, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166317))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.ePhotos, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166290))) {
            nValue = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eCarSlot1, EInfoShowType.infobarToCore(nValue));
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166291))) {
            nValue = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eCarSlot2, EInfoShowType.infobarToCore(nValue));
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166292))) {
            nValue = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eCarSlot3, EInfoShowType.infobarToCore(nValue));
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166293))) {
            nValue = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.ePedSlot1, EInfoShowType.infobarToCore(nValue));
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166294))) {
            nValue = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.ePedSlot2, EInfoShowType.infobarToCore(nValue));
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166295))) {
            nValue = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.ePedSlot3, EInfoShowType.infobarToCore(nValue));
            return Integer.valueOf(nValue);
        }
        if (key.equals(getString(2131166302))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eDisplayTraffic, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166303))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eAvoidTraffic, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166313))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eBuildings, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166315))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eLandmarks, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166316))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eSignposts, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166314))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eStreets, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166735))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eTollRoads, !bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166736))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eUnpavedRoads, !bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166734))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eMotorway, !bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166733))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eFerries, !bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166732))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eCompute, value);
            BubbleManager.getInstance().checkHighlighting(value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166265))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eBatteryBack, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166267))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.ePower, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166266))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eBattery, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166725))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eDistanceUnits, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166726))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eTempUnits, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166727))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eTimeUnits, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166724))) {
            value = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.eCoordsUnits, value);
            return Integer.valueOf(value);
        }
        Object strValue;
        if (key.equals(getString(2131166722))) {
            strValue = sharedPreferences.getString(key, "");
            SettingsManager.nativeSetLanguage(strValue);
            setTitle(ResourceManager.getCoreString(getActivity(), 2131165381));
            this.mToolbar.getMenu().clear();
            onSetupToolbar(this.mToolbar);
            initPreferencesFromResource();
            if (this.mResultCallback == null) {
                return strValue;
            }
            ((ConfigurationSettingsFragmentResultCallback) this.mResultCallback).onLanguageChanged();
            return strValue;
        }
        if (key.equals(getString(2131166728))) {
            strValue = sharedPreferences.getString(key, "");
            setPrefSummary(strValue, key);
            return strValue;
        }
        if (key.equals(getString(2131166768))) {
            strValue = sharedPreferences.getString(key, "");
            setPrefSummary(strValue, key);
            setParentPrefSummary(strValue, 2131166745);
            return strValue;
        }
        if (key.equals(getString(2131166763))) {
            strValue = sharedPreferences.getString(key, "");
            setPrefSummary(strValue, key);
            setParentPrefSummary(strValue, 2131166744);
            return strValue;
        }
        if (key.equals(getString(2131166757))) {
            strValue = sharedPreferences.getString(key, "");
            setPrefSummary(strValue, key);
            setParentPrefSummary(strValue, 2131166743);
            return strValue;
        }
        if (key.equals(getString(2131166749))) {
            strValue = sharedPreferences.getString(key, "");
            setPrefSummary(strValue, key);
            setParentPrefSummary(strValue, 2131166749);
            return strValue;
        }
        if (key.equals(getString(2131166754))) {
            strValue = sharedPreferences.getString(key, "");
            setPrefSummary(strValue, key);
            setParentPrefSummary(strValue, 2131166742);
            return strValue;
        }
        if (key.equals(getString(2131166747))) {
            strValue = sharedPreferences.getString(key, "");
            setPrefSummary(strValue, key);
            setParentPrefSummary(strValue, 2131166741);
            return strValue;
        }
        if (key.equals(getString(2131166747))) {
            strValue = sharedPreferences.getString(key, "");
            SettingsManager.nativeSetSound(strValue, ESoundSettingsType.eWarnNearRailSound);
            return strValue;
        }
        if (key.equals(getString(2131166757))) {
            strValue = sharedPreferences.getString(key, "");
            SettingsManager.nativeSetSound(strValue, ESoundSettingsType.eSpeedCamWarnSound);
            return strValue;
        }
        if (key.equals(getString(2131166763))) {
            strValue = sharedPreferences.getString(key, "");
            SettingsManager.nativeSetSound(strValue, ESoundSettingsType.eSpeedLimWarnSound);
            return strValue;
        }
        if (key.equals(getString(2131166768))) {
            strValue = sharedPreferences.getString(key, "");
            SettingsManager.nativeSetSound(strValue, ESoundSettingsType.eTmcNotifSound);
            return strValue;
        }
        if (key.equals(getString(2131166514))) {
            int index = strSettingsToInt(sharedPreferences, key);
            SettingsManager.nativeSetSettings(ESettingsType.ePoiOnRouteOnOff, index);
            String[] values = getResources().getStringArray(2131492902);
            int length = values.length;
            SygicAnalyticsLogger.getAnalyticsEvent(getActivity(), EventType.NOTIFICATION_CENTER).setName("poi_settings_changed").setValue("mode", r0 >= index ? ResourceManager.getCoreString(values[index]) : "unknown").logAndRecycle();
            return Integer.valueOf(index);
        }
        if (key.equals(getString(2131166751))) {
            value = sharedPreferences.getInt(key, -1);
            SettingsManager.nativeSetSettings(ESettingsType.eSharpCurveEasy, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166753))) {
            value = sharedPreferences.getInt(key, -1);
            SettingsManager.nativeSetSettings(ESettingsType.eSharpCurveMedium, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166752))) {
            value = sharedPreferences.getInt(key, -1);
            SettingsManager.nativeSetSettings(ESettingsType.eSharpCurveHard, value);
            return Integer.valueOf(value);
        }
        if (key.equals(getString(2131166750))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eSharpCurveOnOff, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166754))) {
            strValue = sharedPreferences.getString(key, "");
            SettingsManager.nativeSetSound(strValue, ESoundSettingsType.eDangerTurnNotifSound);
            return strValue;
        }
        if (key.equals(getString(2131166285))) {
            bOnOff = sharedPreferences.getBoolean(key, false);
            SettingsManager.nativeSetSettings(ESettingsType.eAutoCloseOnOff, bOnOff);
            return Boolean.valueOf(bOnOff);
        }
        if (key.equals(getString(2131166287))) {
            value = sharedPreferences.getInt(key, -1);
            SettingsManager.nativeSetSettings(ESettingsType.eAutoCloseDriving, value);
            return Integer.valueOf(value);
        }
        if (!key.equals(getString(2131166286))) {
            return null;
        }
        value = sharedPreferences.getInt(key, -1);
        SettingsManager.nativeSetSettings(ESettingsType.eAutoCloseAlways, value);
        return Integer.valueOf(value);
    }

    protected Preference findPreferenceByKey(int prefId) {
        PreferenceScreen screen = getPreferenceScreen();
        if (screen != null) {
            return screen.findPreference(getString(prefId));
        }
        return null;
    }

    private void setupRoadNumberDependency() {
        PreferenceScreen screen = getPreferenceScreen();
        if (screen != null) {
            Preference roadNumPref = screen.findPreference(getString(2131166770));
            if (roadNumPref != null) {
                roadNumPref.setDependency(getString(2131166772));
            }
        }
    }

    private void setPrefSummary(String strValue, String strKey) {
        FragmentPreference pref = (FragmentPreference) findPreference(strKey);
        if (pref != null) {
            pref.setSummary(strValue);
        }
    }

    private void setParentPrefSummary(String strValue, int strParentPrefID) {
        String strParentPrefName = getString(strParentPrefID);
        SettingsFragmentPreference pref = (SettingsFragmentPreference) findPreference(strParentPrefName);
        if (pref != null) {
            pref.setSummary(strValue);
            getSharedPreferences().edit().putString(strParentPrefName, strValue).commit();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_XML, this.mXmlId);
        outState.putString(AbstractFragment.ARG_TITLE, this.mTitle);
        outState.putInt(ARG_MENU, this.mMenuId);
        outState.putBoolean(ARG_LAST_DIVIDER, this.mHasLastDivider);
        super.onSaveInstanceState(outState);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Method m = PreferenceManager.class.getDeclaredMethod("dispatchActivityResult", new Class[]{Integer.TYPE, Integer.TYPE, Intent.class});
            m.setAccessible(true);
            m.invoke(this.mPreferenceManager, new Object[]{Integer.valueOf(requestCode), Integer.valueOf(resultCode), data});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int strSettingsToInt(SharedPreferences sharedPreferences, String key) {
        String strValue = sharedPreferences.getString(key, null);
        if (strValue != null) {
            return Integer.parseInt(strValue);
        }
        return -1;
    }

    private void postBindPreferences() {
        if (!this.mHandler.hasMessages(0)) {
            this.mHandler.obtainMessage(0).sendToTarget();
        }
    }

    private void bindPreferences() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null && getActivity() != null) {
            Preference debugPreference = findPreference(getString(2131166284));
            if (debugPreference != null) {
                if (SettingsManager.nativeIsDebugEnabled()) {
                    preferenceScreen.addPreference(debugPreference);
                } else {
                    preferenceScreen.removePreference(debugPreference);
                }
            }
            ListView listView = getList();
            listView.setScrollBarStyle(0);
            if (!this.mHasLastDivider) {
                listView.getLayoutParams().height = -2;
            }
            if (!this.mHasSelector) {
                listView.setSelector(new ColorDrawable(getResources().getColor(17170445)));
            }
            listView.setHeaderDividersEnabled(false);
            listView.setFooterDividersEnabled(false);
            preferenceScreen.bind(listView);
        }
    }

    private PreferenceManager onCreatePreferenceManager() {
        try {
            Constructor<PreferenceManager> constructor = PreferenceManager.class.getDeclaredConstructor(new Class[]{Activity.class, Integer.TYPE});
            constructor.setAccessible(true);
            return (PreferenceManager) constructor.newInstance(new Object[]{getActivity(), Integer.valueOf(FIRST_REQUEST_CODE)});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PreferenceScreen getPreferenceScreen() {
        try {
            Method m = PreferenceManager.class.getDeclaredMethod("getPreferenceScreen", new Class[0]);
            m.setAccessible(true);
            return (PreferenceScreen) m.invoke(this.mPreferenceManager, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        try {
            Method m = PreferenceManager.class.getDeclaredMethod("setPreferences", new Class[]{PreferenceScreen.class});
            m.setAccessible(true);
            if (((Boolean) m.invoke(this.mPreferenceManager, new Object[]{preferenceScreen})).booleanValue() && preferenceScreen != null) {
                postBindPreferences();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPreferencesFromResource(int preferencesResId) {
        try {
            Method m = PreferenceManager.class.getDeclaredMethod("inflateFromResource", new Class[]{Context.class, Integer.TYPE, PreferenceScreen.class});
            m.setAccessible(true);
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            if (preferenceScreen != null) {
                preferenceScreen.removeAll();
            }
            PreferenceScreen prefScreen = (PreferenceScreen) m.invoke(this.mPreferenceManager, new Object[]{getActivity(), Integer.valueOf(preferencesResId), preferenceScreen});
            Field adapter = prefScreen.getClass().getDeclaredField("mRootAdapter");
            adapter.setAccessible(true);
            adapter.set(prefScreen, new StyleablePreferenceGroupAdapter(getActivity(), prefScreen));
            setPreferenceScreen(prefScreen);
        } catch (InvocationTargetException e) {
            Log.w(LOG_NAME, "Invoke target exception - " + e.getTargetException());
        } catch (Exception e2) {
            Log.w(LOG_NAME, "No preference XML provided [attribute:preferences] - " + e2.toString());
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

    public Preference findPreference(CharSequence key) {
        if (this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(key);
    }

    protected void languageChanged(SharedPreferences sp, String key) {
        SettingsManager.nativeSetLanguage(sp.getString(key, ""));
        setTitle(ResourceManager.getCoreString(getActivity(), 2131165381));
        getActivity().supportInvalidateOptionsMenu();
        initPreferencesFromResource();
        if (this.mResultCallback != null) {
            ((ConfigurationSettingsFragmentResultCallback) this.mResultCallback).onLanguageChanged();
        }
    }

    protected ListView getList() {
        return (ListView) this.mRootView.findViewById(16908298);
    }
}
