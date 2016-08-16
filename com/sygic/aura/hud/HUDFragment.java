package com.sygic.aura.hud;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.HUDFragmentResultCallback;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.helper.interfaces.DirectionChangeListener;
import com.sygic.aura.helper.interfaces.SpeedLimitListener;
import com.sygic.aura.helper.interfaces.WarningChangeListener;
import com.sygic.aura.map.data.SpeedInfoItem;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.InfoSlotsUpdateTask;
import com.sygic.aura.route.InfoSlotsUpdateTask.SlotHolder;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.SpeedLimitControl;
import com.sygic.aura.route.data.DirectionStatus;
import com.sygic.aura.route.data.WarningItem;
import com.sygic.aura.route.data.WarningItem.Type;
import com.sygic.aura.route.data.infobar_slots.Slot;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import loquendo.tts.engine.TTSConst;

public class HUDFragment extends AbstractScreenFragment implements OnClickListener, DirectionChangeListener, SpeedLimitListener, WarningChangeListener, SlotHolder {
    private View mBackButton;
    private View mDirection;
    private DirectionStatus mDirectionStatus;
    private TextView mDistance;
    private TextView mETA;
    private View mFlipButton;
    private Map<HudSlotPosition, Slot> mHudSlots;
    private boolean mIsMirrored;
    private boolean mIsPopUpVisible;
    private ViewGroup mLayout;
    private View mPopUpView;
    private TextView[] mPrimaryControls;
    private InfoSlotsUpdateTask mRefreshTask;
    private TextView mSpeed;
    private SpeedLimitControl mSpeedLimitSign;
    private TextView mWarning;

    /* renamed from: com.sygic.aura.hud.HUDFragment.1 */
    class C12821 implements OnPreDrawListener {
        final /* synthetic */ ViewTreeObserver val$viewTreeObserver;

        C12821(ViewTreeObserver viewTreeObserver) {
            this.val$viewTreeObserver = viewTreeObserver;
        }

        public boolean onPreDraw() {
            this.val$viewTreeObserver.removeOnPreDrawListener(this);
            HUDFragment.this.mLayout.setCameraDistance((((float) HUDFragment.this.mLayout.getHeight()) * 1.2f) * HUDFragment.this.getResources().getDisplayMetrics().density);
            return true;
        }
    }

    /* renamed from: com.sygic.aura.hud.HUDFragment.2 */
    static /* synthetic */ class C12832 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$route$data$WarningItem$Type;

        static {
            $SwitchMap$com$sygic$aura$route$data$WarningItem$Type = new int[Type.values().length];
            try {
                $SwitchMap$com$sygic$aura$route$data$WarningItem$Type[Type.RAILWAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$WarningItem$Type[Type.SPEEDCAM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$route$data$WarningItem$Type[Type.SHARP_CURVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private enum HudSlotPosition {
        Speed,
        ETA,
        Gps
    }

    public HUDFragment() {
        this.mIsPopUpVisible = false;
        this.mIsMirrored = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHudSlots = new HashMap(2);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903108, container, false);
        this.mLayout = (ViewGroup) view.findViewById(2131624215);
        this.mDistance = (TextView) view.findViewById(2131624119);
        this.mSpeed = (TextView) view.findViewById(2131624216);
        this.mETA = (TextView) view.findViewById(2131624217);
        this.mWarning = (TextView) view.findViewById(2131624219);
        this.mDirection = view.findViewById(2131624218);
        initDirectionControl(this.mDirection);
        initSpeedLimitControl(view);
        addSlotControl(new SpeedSlot(this.mSpeed), HudSlotPosition.Speed);
        addSlotControl(new ETASlot(this.mETA), HudSlotPosition.ETA);
        addSlotControl(new GpsSlot(this.mWarning), HudSlotPosition.Gps);
        this.mPopUpView = view.findViewById(2131624220);
        this.mBackButton = this.mPopUpView.findViewById(2131624558);
        this.mFlipButton = this.mPopUpView.findViewById(2131624559);
        RouteEventsReceiver.registerDirectionChangeListener(this);
        MapEventsReceiver.registerSpeedLimitListener(this);
        MapEventsReceiver.registerWarningChangeListener(this);
        WndEventsReceiver.registerAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        this.mLayout.setOnClickListener(this);
        this.mBackButton.setOnClickListener(this);
        this.mFlipButton.setOnClickListener(this);
        SygicHelper.setRotationLock(true);
        startSlotsUpdate();
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new C12821(viewTreeObserver));
    }

    private void forceFullScreen(boolean bForceFullScreen) {
        if (VERSION.SDK_INT >= 19) {
            Window window = getActivity().getWindow();
            View decorView = window.getDecorView();
            int uiOptions = decorView.getSystemUiVisibility();
            if (bForceFullScreen) {
                uiOptions = ((((uiOptions | 512) | 2) | 1024) | 4) | 4096;
            } else {
                uiOptions = ((((uiOptions & -513) & -3) & -1025) & -5) & -4097;
            }
            if (bForceFullScreen) {
                window.addFlags(1024);
                window.clearFlags(2048);
            } else if (!PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(getString(2131166289), false)) {
                window.addFlags(2048);
                window.clearFlags(1024);
            }
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        forceFullScreen(true);
        if (this.mResultCallback != null) {
            ((HUDFragmentResultCallback) this.mResultCallback).onHUDFragmentAttached();
        }
    }

    public void onDetach() {
        super.onDetach();
        forceFullScreen(false);
        if (this.mResultCallback != null) {
            ((HUDFragmentResultCallback) this.mResultCallback).onHUDFragmentDetached();
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
        RouteEventsReceiver.unregisterDirectionChangeListener(this);
        MapEventsReceiver.unregisterSpeedLimitListener(this);
        MapEventsReceiver.unregisterWarningChangeListener(this);
        WndEventsReceiver.unregisterAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        for (Slot value : this.mHudSlots.values()) {
            value.removeView();
        }
        if (this.mRefreshTask != null) {
            this.mRefreshTask.finishTask();
        }
        SygicHelper.setRotationLock(false);
    }

    private void initDirectionControl(View view) {
        this.mPrimaryControls = new TextView[]{(TextView) view.findViewById(2131624396), (TextView) view.findViewById(2131624395), (TextView) view.findViewById(2131624394)};
    }

    private void initSpeedLimitControl(View view) {
        this.mSpeedLimitSign = new SpeedLimitControl((ViewSwitcher) view.findViewById(2131624355));
    }

    private void addSlotControl(Slot slot, HudSlotPosition position) {
        if (slot != null && this.mHudSlots.get(position) == null) {
            this.mHudSlots.put(position, slot);
        }
    }

    private void startSlotsUpdate() {
        if (this.mRefreshTask == null || this.mRefreshTask.isFinished()) {
            this.mRefreshTask = new InfoSlotsUpdateTask();
        }
        AsyncTaskHelper.execute(this.mRefreshTask, this);
        RouteManager.nativeUpdateDirection();
        RouteManager.nativeUpdateSpeedWarning();
    }

    public Collection<Slot> getSlots() {
        return this.mHudSlots.values();
    }

    public void onSpeedLimitChanged(SpeedInfoItem item) {
        this.mSpeedLimitSign.updateSpeedLimit(item);
    }

    public void onDirectionChange(DirectionStatus direction) {
        View view = this.mDirection;
        boolean z = direction != null && direction.bValidPrimary;
        ResourceManager.makeControlVisible(view, z);
        if (direction != null) {
            if (direction.bValidPrimary) {
                ResourceManager.drawMultiFontIcon(getActivity(), this.mPrimaryControls, direction.arrPrimaryChars, direction.arrPrimaryChars, true);
            }
            if (this.mDistance == null) {
                return;
            }
            if ((this.mDirectionStatus == null || direction.nDistance != this.mDirectionStatus.nDistance) && ResourceManager.nativeFormatDistance((long) direction.nDistance, true, true, false) != null) {
                this.mDistance.setText(String.format("%s%s", new Object[]{ResourceManager.nativeFormatDistance((long) direction.nDistance, true, true, false).getValue(), ResourceManager.nativeFormatDistance((long) direction.nDistance, true, true, false).getUnit()}));
            }
        }
    }

    public void onWarningChange() {
        onWarningChange(null);
    }

    public void onWarningChange(WarningItem warningItem) {
        if (warningItem == null) {
            this.mWarning.setVisibility(8);
            return;
        }
        this.mWarning.setVisibility(0);
        this.mWarning.setTextColor(getResources().getColor(2131558682));
        int iconId = getIcon(warningItem);
        if (iconId != -1) {
            this.mWarning.setText(iconId);
        } else {
            this.mWarning.setVisibility(8);
        }
    }

    private int getIcon(WarningItem warningItem) {
        switch (C12832.$SwitchMap$com$sygic$aura$route$data$WarningItem$Type[warningItem.getType().ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 2131166114;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return warningItem.getIconResId();
            case TTSConst.TTSUNICODE /*3*/:
                if (warningItem.getSubType() == 1) {
                    return 2131166115;
                }
                if (warningItem.getSubType() == 2) {
                    return 2131166116;
                }
                break;
        }
        return -1;
    }

    public void onClick(View v) {
        if (v.equals(this.mLayout)) {
            if (this.mIsPopUpVisible) {
                this.mPopUpView.setVisibility(8);
                this.mIsPopUpVisible = false;
                return;
            }
            this.mPopUpView.setVisibility(0);
            this.mIsPopUpVisible = true;
        } else if (v.equals(this.mBackButton)) {
            performHomeAction();
        } else if (v.equals(this.mFlipButton)) {
            if (this.mIsPopUpVisible) {
                this.mPopUpView.setVisibility(8);
                this.mIsPopUpVisible = false;
            }
            AnimatorSet set = new AnimatorSet();
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofInt(this.mLayout, "BackgroundColor", new int[]{-16777216, Color.argb(180, 180, 180, 180), Color.argb(220, 220, 220, 220), Color.argb(180, 180, 180, 180), -16777216});
            backgroundColorAnimator.setEvaluator(new ArgbEvaluator());
            Animator[] animatorArr = new Animator[3];
            ViewGroup viewGroup = this.mLayout;
            Property property = View.ROTATION_X;
            float[] fArr = new float[2];
            fArr[0] = this.mIsMirrored ? 180.0f : 0.0f;
            fArr[1] = this.mIsMirrored ? 0.0f : 180.0f;
            animatorArr[0] = ObjectAnimator.ofFloat(viewGroup, property, fArr);
            View view = this.mPopUpView;
            property = View.ROTATION_X;
            fArr = new float[2];
            fArr[0] = this.mIsMirrored ? 180.0f : 0.0f;
            fArr[1] = this.mIsMirrored ? 0.0f : 180.0f;
            animatorArr[1] = ObjectAnimator.ofFloat(view, property, fArr);
            animatorArr[2] = backgroundColorAnimator;
            set.playTogether(animatorArr);
            this.mIsMirrored ^= 1;
            set.setDuration(1000).start();
        }
    }
}
