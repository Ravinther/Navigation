package com.sygic.aura.map.screen;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.interfaces.SpeedLimitListener;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.data.SpeedInfoItem;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.notification.NotificationCenterView;
import com.sygic.aura.map.screen.NavigationInfoBarScreen.SlotPosition;
import com.sygic.aura.map.screen.intefaces.InfoBarInterface;
import com.sygic.aura.map.screen.intefaces.SoundMutedListener;
import com.sygic.aura.map.view.MapOverlayAnimator;
import com.sygic.aura.map.view.ThreeStateButton;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.InfoSlotsUpdateTask.SlotHolder;
import com.sygic.aura.route.SpeedLimitControl;
import com.sygic.aura.route.data.infobar_slots.CurrentSpeedSlot;
import com.sygic.aura.route.data.infobar_slots.DayTimeSlot;
import com.sygic.aura.route.data.infobar_slots.Slot;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.STextView;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FreeDriveInfoBarScreen extends OverlayScreen implements SpeedLimitListener, InfoBarInterface, SoundMutedListener, SlotHolder {
    private STextView mBtnCurrStreet;
    private View mInfoBar;
    private ViewGroup mInfoBarSlots;
    private Map<SlotPosition, Slot> mInfoSlots;
    private SImageButton mMutedView;
    private NotificationCenterView mNotificationCenter;
    private SpeedLimitControl mSpeedLimitSign;
    private String mStrRecentStreetName;
    private STextView mTxtInaccurateSignal;
    private int mnLastPartIndex;

    public static SlotHolder getInstance() {
        return (SlotHolder) OverlayScreen.getInstance(Mode.FREEDRIVE_INFO_BAR);
    }

    protected void setupChildScreen(View rootView) {
        if (rootView != null) {
            int i;
            ThreeStateButton btnVehicleLock = (ThreeStateButton) rootView.findViewById(2131624412);
            btnVehicleLock.setOnClickListener(MapControlsManager.getOnClickListenerVehicleLock(btnVehicleLock));
            this.mBtnCurrStreet = (STextView) rootView.findViewById(2131624418);
            STextView sTextView = this.mBtnCurrStreet;
            if (this.mFragment.getOrientation() == 1) {
                i = 2;
            } else {
                i = 1;
            }
            sTextView.setMaxLines(i);
            this.mInfoBar = rootView.findViewById(2131624419);
            this.mInfoBarSlots = (ViewGroup) this.mInfoBar.findViewById(2131624423);
            this.mTxtInaccurateSignal = (STextView) this.mInfoBar.findViewById(2131624422);
            this.mTxtInaccurateSignal.setText(ResourceManager.getCoreString(this.mContext, 2131165490));
            this.mInfoSlots = new HashMap(2);
            setupSlotsInternal();
            initSpeedLimitControl((ViewSwitcher) rootView.findViewById(2131624599));
            MapEventsReceiver.registerSpeedLimitListener(this);
            MapOverlayAnimator animator = this.mFragment.getAnimator();
            animator.registerViewForTranslateAnimationByY(this.mInfoBar, Mode.FREEDRIVE_INFO_BAR);
            animator.registerViewForAnimation(this.mBtnCurrStreet, Mode.FREEDRIVE_INFO_BAR, "alpha", 0.0f, 1.0f);
            animator.registerViewForAnimation(this.mSpeedLimitSign.getView(), Mode.FREEDRIVE_INFO_BAR, "alpha", 0.0f, 1.0f);
            View[] views = new View[1];
            setupBlackboxMaximize(rootView, views);
            animator.registerViewsForAnimation(views, Mode.FREEDRIVE_INFO_BAR, "alpha", 0.0f, 1.0f);
            setupReportIncident(rootView);
            setupMutedControl(rootView, views);
            animator.registerViewForAnimation(views[0], Mode.FREEDRIVE_INFO_BAR, "alpha", 0.0f, 1.0f);
            setupBottomLeftControls(rootView);
            this.mMutedView = (SImageButton) rootView.findViewById(2131624386);
            this.mNotificationCenter = (NotificationCenterView) rootView.findViewById(2131624420);
            animator.registerViewForAnimation(this.mNotificationCenter, Mode.FREEDRIVE_INFO_BAR, "alpha", 0.0f, 1.0f);
        }
    }

    protected void onScreenEntered() {
        getToolbar().hide();
        MapControlsManager.nativeSetMapViewMode(EMapView.MVNavScreen);
        this.mNotificationCenter.setActive(true);
    }

    protected void onscreenLeft() {
        getToolbar().show();
        this.mNotificationCenter.setActive(false);
    }

    protected void onDestroy() {
        super.onDestroy();
        MapEventsReceiver.unregisterSpeedLimitListener(this);
    }

    public static void onConfigurationChanged(int orientation) {
        int i = 1;
        FreeDriveInfoBarScreen screen = (FreeDriveInfoBarScreen) OverlayScreen.getInstance(Mode.FREEDRIVE_INFO_BAR);
        if (screen != null) {
            STextView sTextView = screen.mBtnCurrStreet;
            if (orientation == 1) {
                i = 2;
            }
            sTextView.setMaxLines(i);
            screen.setCurrentStreet(screen.mStrRecentStreetName);
            screen.updateBottomLeftControls(orientation);
        }
    }

    public static void onLanguageChanged(String newLang) {
        FreeDriveInfoBarScreen screen = (FreeDriveInfoBarScreen) OverlayScreen.getInstance(Mode.FREEDRIVE_INFO_BAR);
        if (screen != null && screen.mTxtInaccurateSignal != null) {
            screen.mTxtInaccurateSignal.setText(ResourceManager.getCoreString(screen.mContext, 2131165490));
        }
    }

    public static void onRoutePartChange(int index) {
        boolean isStreetValid = false;
        FreeDriveInfoBarScreen screen = (FreeDriveInfoBarScreen) OverlayScreen.getInstance(Mode.FREEDRIVE_INFO_BAR);
        if (screen != null && screen.mnLastPartIndex != index) {
            screen.mnLastPartIndex = index;
            if (SettingsManager.nativeGetSettings(ESettingsType.eStreets) == 0) {
                ResourceManager.makeControlVisible(screen.mBtnCurrStreet, false, true);
                return;
            }
            String strStreetName = MapControlsManager.nativeGetCurrentStreetName();
            if (!TextUtils.isEmpty(strStreetName)) {
                isStreetValid = true;
            }
            ResourceManager.makeControlVisible(screen.mBtnCurrStreet, isStreetValid, true);
            if (isStreetValid && !strStreetName.equals(screen.mStrRecentStreetName)) {
                screen.mStrRecentStreetName = strStreetName;
                screen.setCurrentStreet(strStreetName);
            }
        }
    }

    private void setupSlotsInternal() {
        if (this.mInfoBarSlots != null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            putSlotAtPosition(this.mInfoBarSlots, inflater, new DayTimeSlot(), SlotPosition.FIRST);
            putSlotAtPosition(this.mInfoBarSlots, inflater, new CurrentSpeedSlot(), SlotPosition.SECOND);
        }
    }

    private void setCurrentStreet(String strStreetName) {
        NavigationInfoBarScreen.configureCurrentStreet(this.mBtnCurrStreet, strStreetName, this.mFragment.getOrientation());
    }

    private synchronized void putSlotAtPosition(View parent, LayoutInflater inflater, Slot slot, SlotPosition position) {
        if (this.mInfoSlots.get(position) != null) {
            ((Slot) this.mInfoSlots.get(position)).removeView();
        }
        if (slot != null) {
            this.mInfoSlots.put(position, slot);
            NavigationInfoBarScreen.addViewToSlot(parent, slot.getView(inflater), position.getId());
        }
    }

    private void initSpeedLimitControl(ViewSwitcher view) {
        this.mSpeedLimitSign = new SpeedLimitControl(view);
    }

    public synchronized Collection<Slot> getSlots() {
        return this.mInfoSlots.values();
    }

    public View getInfoBar() {
        return this.mInfoBar;
    }

    public View getProgress() {
        return null;
    }

    public View getInfoBarSlots() {
        return this.mInfoBarSlots;
    }

    public View getSignalView() {
        return this.mTxtInaccurateSignal;
    }

    public void onSpeedLimitChanged(SpeedInfoItem item) {
        if (this.mSpeedLimitSign != null) {
            this.mSpeedLimitSign.updateSpeedLimit(item);
        }
    }

    public void onMuteChanged(boolean isMuted) {
        this.mMutedView.setVisibility(!isMuted ? 8 : 0);
    }
}
