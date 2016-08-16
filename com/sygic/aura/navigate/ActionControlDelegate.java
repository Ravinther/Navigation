package com.sygic.aura.navigate;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.navigate.ActionControlFragment.Mode;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.views.CircleProgressBar;
import com.sygic.aura.views.UndoBar;
import com.sygic.aura.views.UndoBar.OnUndoListener;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import loquendo.tts.engine.TTSConst;

public abstract class ActionControlDelegate {
    protected View mContainer;
    protected final ActionControlFragment mFragment;
    protected String mIcon;
    protected SImageView mIconImageView;
    protected MapSelection mMapSel;

    /* renamed from: com.sygic.aura.navigate.ActionControlDelegate.1 */
    class C13731 implements OnClickListener {
        C13731() {
        }

        public void onClick(View v) {
            ActionControlDelegate.this.handleOnClick();
        }
    }

    /* renamed from: com.sygic.aura.navigate.ActionControlDelegate.2 */
    class C13742 implements OnClickListener {
        C13742() {
        }

        public void onClick(View v) {
            ActionControlDelegate.this.handleCancel();
        }
    }

    /* renamed from: com.sygic.aura.navigate.ActionControlDelegate.3 */
    class C13753 implements OnUndoListener {
        C13753() {
        }

        public void onUndo(Context context) {
            ActionControlDelegate.this.handleUndo(context);
        }
    }

    /* renamed from: com.sygic.aura.navigate.ActionControlDelegate.4 */
    static /* synthetic */ class C13764 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$navigate$ActionControlFragment$Mode;

        static {
            $SwitchMap$com$sygic$aura$navigate$ActionControlFragment$Mode = new int[Mode.values().length];
            try {
                $SwitchMap$com$sygic$aura$navigate$ActionControlFragment$Mode[Mode.POR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$navigate$ActionControlFragment$Mode[Mode.NEARBY_POI.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$navigate$ActionControlFragment$Mode[Mode.PARKING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    protected ActionControlDelegate(ActionControlFragment fragment) {
        this.mFragment = fragment;
    }

    public static ActionControlDelegate from(ActionControlFragment fragment, Mode mode) {
        switch (C13764.$SwitchMap$com$sygic$aura$navigate$ActionControlFragment$Mode[mode.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return new PorActionControlDelegate(fragment);
            case TTSConst.TTSPARAGRAPH /*2*/:
                return new NearbyPoiActionControlDelegate(fragment);
            default:
                return new ParkingActionControlDelegate(fragment);
        }
    }

    public void onCreate(Bundle arguments) {
        this.mMapSel = (MapSelection) arguments.getParcelable("mapsel");
        this.mIcon = arguments.getString("icon_char");
    }

    public int getLayoutRes() {
        return 2130903069;
    }

    protected void setupCancelLayout(View rootView) {
        ((CircleProgressBar) rootView.findViewById(2131624116)).setCircleProgress(100.0f);
    }

    public void onViewCreated(View view) {
        this.mContainer = view.findViewById(2131624111);
        this.mContainer.setOnClickListener(new C13731());
        View cancelContainer = this.mContainer.findViewById(2131624114);
        cancelContainer.setOnClickListener(new C13742());
        setupCancelLayout(cancelContainer);
        setTitle((STextView) this.mContainer.findViewById(2131624113));
        this.mIconImageView = (SImageView) this.mContainer.findViewById(2131624112);
        setIcon();
    }

    protected void setIcon() {
        if (TextUtils.isEmpty(this.mIcon)) {
            this.mIconImageView.setVisibility(8);
            return;
        }
        this.mIconImageView.setVisibility(0);
        this.mIconImageView.setFontDrawableChar(this.mIcon);
    }

    protected void setTitle(STextView titleTextView) {
        titleTextView.setCoreText(2131165326);
    }

    protected void handleOnClick() {
        this.mFragment.performHomeAction();
    }

    protected void handleCancel() {
        cancel();
        this.mFragment.performHomeAction();
    }

    public boolean cancel() {
        MapOverlayFragment.setMode(this.mFragment.getActivity(), MapOverlayFragment.Mode.NAVIGATE_INFO_BAR);
        return false;
    }

    public void onPoiSelectionChanged(MapSelection mapSel, String title, String icon) {
        this.mMapSel = mapSel;
        this.mIcon = icon;
        setIcon();
        runBounceAnimation();
    }

    protected void addWaypoint(int messageId) {
        if (this.mMapSel != null) {
            RouteManager.nativeTravelVia(this.mMapSel);
            UndoBar undoBar = UndoBar.newInstance(this.mFragment.getActivity(), messageId);
            undoBar.setOnUndoListener(new C13753());
            undoBar.show();
        }
    }

    protected void handleUndo(Context context) {
        RouteSummary.nativeSkipViaPoint();
    }

    protected void runBounceAnimation() {
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X, new Keyframe[]{Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(0.5f, 0.975f), Keyframe.ofFloat(1.0f, 1.0f)});
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, new Keyframe[]{Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(0.5f, 0.975f), Keyframe.ofFloat(1.0f, 1.0f)});
        ObjectAnimator a = ObjectAnimator.ofPropertyValuesHolder(this.mContainer, new PropertyValuesHolder[]{pvhScaleX, pvhScaleY});
        a.setDuration(200);
        a.start();
    }

    public void onConfigurationChanged() {
        if (this.mContainer != null) {
            Resources res = this.mFragment.getResources();
            int marginSides = (int) res.getDimension(2131230728);
            ((LayoutParams) this.mContainer.getLayoutParams()).setMargins(marginSides, 0, marginSides, (int) res.getDimension(2131230727));
        }
    }
}
