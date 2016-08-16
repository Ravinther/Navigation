package com.sygic.aura.map.screen;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.view.CompassView;
import com.sygic.aura.map.view.MapOverlayAnimator;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToggleButton;
import com.sygic.aura.views.font_specials.SToggleButton.OnCheckedChangeListener;

public class NavigationBrowseScreen extends AutoCloseableOverlayScreen {
    private static boolean mIsPedestrian;
    private static boolean mRouteComputeHappened;
    private SToggleButton mBtn2D3DBrowse;
    private SToggleButton mBtnTraffic;
    private CompassView mCompassView;
    private ViewGroup mCountdownContainer;
    private STextView mCountdownTextView;
    protected OnCheckedChangeListener mTrafficOnCheckedChangeListener;

    /* renamed from: com.sygic.aura.map.screen.NavigationBrowseScreen.1 */
    class C13391 implements OnClickListener {
        C13391() {
        }

        public void onClick(View view) {
            NavigationBrowseScreen.this.mBtnTraffic.setChecked(false);
            NavigationBrowseScreen.resumeNavigation();
        }
    }

    /* renamed from: com.sygic.aura.map.screen.NavigationBrowseScreen.2 */
    class C13402 implements OnCheckedChangeListener {
        private boolean bIsMapView3D;

        C13402() {
        }

        public void onCheckedChanged(SImageButton buttonView, boolean isChecked) {
            if (isChecked) {
                this.bIsMapView3D = NavigationBrowseScreen.this.mBtn2D3DBrowse.isChecked();
            }
            OverlayScreen.performTrafficChange(NavigationBrowseScreen.this.mBtn2D3DBrowse, isChecked, this.bIsMapView3D);
            if (!isChecked) {
                NavigationBrowseScreen.resumeNavigation();
            }
        }
    }

    public NavigationBrowseScreen() {
        this.mTrafficOnCheckedChangeListener = new C13402();
    }

    static {
        mIsPedestrian = false;
        mRouteComputeHappened = false;
    }

    protected void setupChildScreen(View rootView) {
        if (rootView != null) {
            this.mBtn2D3DBrowse = (SToggleButton) rootView.findViewById(2131624414);
            this.mBtn2D3DBrowse.setChecked(!MapControlsManager.nativeIsMapView2D());
            this.mBtn2D3DBrowse.setOnCheckedChangeListener(MapControlsManager.getOnClickListener2D3D(this.mBtn2D3DBrowse));
            this.mCompassView = (CompassView) rootView.findViewById(2131624387);
            this.mBtnTraffic = (SToggleButton) rootView.findViewById(2131624413);
            this.mBtnTraffic.setChecked(false);
            this.mBtnTraffic.setOnCheckedChangeListener(this.mTrafficOnCheckedChangeListener);
            this.mCountdownContainer = (ViewGroup) rootView.findViewById(2131624429);
            this.mCountdownContainer.setOnClickListener(new C13391());
            STextView resumeTextView = (STextView) this.mCountdownContainer.findViewById(2131624430);
            resumeTextView.setCompoundDrawables(FontDrawable.inflate(this.mContext.getResources(), 2131034270), null, null, null);
            resumeTextView.setCompoundDrawablePadding(15);
            this.mCountdownTextView = (STextView) this.mCountdownContainer.findViewById(2131624411);
            MapOverlayAnimator animator = this.mFragment.getAnimator();
            View[] views = new View[2];
            setupZoomControls(rootView, views);
            animator.registerViewsForAnimation(views, Mode.NAVIGATE_BROWSE, "alpha", 0.0f, 1.0f);
            setupRotationLock(rootView, views);
            animator.registerViewForAnimation(views[0], Mode.NAVIGATE_BROWSE, "alpha", 0.0f, 1.0f);
            animator.registerViewForTranslateAnimationByY((View) this.mBtn2D3DBrowse.getParent(), Mode.NAVIGATE_BROWSE);
        }
    }

    protected boolean isZoomControlVisibilityFromSettings() {
        return true;
    }

    protected void onScreenEntered() {
        super.onScreenEntered();
        if (RouteSummary.nativeGetRouteCount() <= 1) {
            EMapView eMapView = MapControlsManager.nativeGetMapViewMode();
            if (eMapView != EMapView.MVPoiDetail && eMapView != EMapView.MVCustomPois) {
                MapControlsManager.nativeSetMapViewMode(EMapView.MVDefault);
            }
        }
    }

    protected void onscreenLeft() {
        super.onscreenLeft();
    }

    public static void resumeNavigation() {
        if (MapControlsManager.nativeIsTrafficView()) {
            MapControlsManager.nativeLeaveTrafficView();
            NavigationBrowseScreen screen = (NavigationBrowseScreen) OverlayScreen.getInstance(Mode.NAVIGATE_BROWSE);
            if (screen != null) {
                screen.mBtnTraffic.setChecked(false);
            }
        }
        if (mRouteComputeHappened) {
            RouteManager.nativeStartNavigate();
            mRouteComputeHappened = false;
            return;
        }
        RouteManager.nativeResumeNavigation();
    }

    public static void onTrafficReceived() {
        if (!mIsPedestrian) {
            NavigationBrowseScreen screen = (NavigationBrowseScreen) OverlayScreen.getInstance(Mode.NAVIGATE_BROWSE);
            if (screen != null && screen.mBtnTraffic != null) {
                ResourceManager.makeControlVisible(screen.mBtnTraffic, true);
            }
        }
    }

    public static void onRouteCanceled() {
        NavigationBrowseScreen screen = (NavigationBrowseScreen) OverlayScreen.getInstance(Mode.NAVIGATE_BROWSE);
        if (screen != null && MapControlsManager.nativeIsTrafficView()) {
            MapControlsManager.nativeLeaveTrafficView();
            screen.mBtnTraffic.setChecked(false);
        }
    }

    public static void onStartComputingProgress() {
        NavigationBrowseScreen screen = (NavigationBrowseScreen) OverlayScreen.getInstance(Mode.NAVIGATE_BROWSE);
        if (screen != null) {
            screen.mCountdownContainer.setEnabled(false);
            screen.mBtn2D3DBrowse.setEnabled(false);
            screen.mBtnTraffic.setEnabled(false);
        }
        mRouteComputeHappened = true;
    }

    public static void onRouteComputeFinishedAll() {
        boolean z = true;
        NavigationBrowseScreen screen = (NavigationBrowseScreen) OverlayScreen.getInstance(Mode.NAVIGATE_BROWSE);
        if (screen != null) {
            screen.mCountdownContainer.setEnabled(true);
            screen.mBtn2D3DBrowse.setEnabled(true);
            screen.mBtnTraffic.setEnabled(true);
            mIsPedestrian = RouteSummary.nativeIsPedestrian();
            View view = screen.mBtnTraffic;
            if (mIsPedestrian) {
                z = false;
            }
            ResourceManager.makeControlVisible(view, z);
        }
    }

    public static void onConfigurationChanged(Context context, int orientation) {
        NavigationBrowseScreen screen = (NavigationBrowseScreen) OverlayScreen.getInstance(Mode.NAVIGATE_BROWSE);
        if (screen != null) {
            ((LayoutParams) screen.mCompassView.getLayoutParams()).setMargins(0, context.getResources().getDimensionPixelSize(2131230729), 0, 0);
        }
    }

    protected ViewGroup getCountdownContainer() {
        return this.mCountdownContainer;
    }

    public STextView getCountdownTextView() {
        return this.mCountdownTextView;
    }

    protected boolean isMovementRequired() {
        return false;
    }

    protected void onAutoClosed() {
        resumeNavigation();
    }
}
