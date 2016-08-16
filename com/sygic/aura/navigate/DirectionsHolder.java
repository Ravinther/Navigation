package com.sygic.aura.navigate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.data.DirectionStatus;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.views.animation.VerticalExpandingAnimator;
import loquendo.tts.engine.TTSConst;

public class DirectionsHolder {
    private ViewGroup mDirection;
    private DirectionStatus mDirectionItem;
    private View mDirectionPrimary;
    private View mDirectionSecondary;
    private TextView mDistanceCtrl;
    private final LayoutInflater mInflater;
    private boolean mIsNightModeOn;
    private int mOrientation;
    private TextView[] mPrimaryControls;
    private TextView[] mSecondaryControls;

    /* renamed from: com.sygic.aura.navigate.DirectionsHolder.1 */
    class C13831 implements OnClickListener {
        C13831() {
        }

        public void onClick(View v) {
            MapControlsManager.nativePlayLastInstruction();
        }
    }

    public DirectionsHolder(ViewGroup directionPlate, Context context) {
        this.mDirectionItem = null;
        this.mDirection = directionPlate;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        switch (((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mOrientation = 1;
                break;
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSUNICODE /*3*/:
                break;
        }
        this.mOrientation = 2;
        this.mOrientation = 0;
        this.mIsNightModeOn = SettingsManager.nativeIsNightModeOn();
        if (this.mIsNightModeOn) {
            resetView();
            return;
        }
        findViews();
        updateDirections();
    }

    public void onConfigurationChanged(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            resetView();
        }
    }

    public void onDayNightModeChanged(boolean isNightModeOn) {
        if (this.mIsNightModeOn != isNightModeOn) {
            this.mIsNightModeOn = isNightModeOn;
            resetView();
        }
    }

    private void resetView() {
        ViewGroup parent = (ViewGroup) this.mDirection.getParent();
        int index = parent.indexOfChild(this.mDirection);
        parent.removeView(this.mDirection);
        this.mDirection = (ViewGroup) this.mInflater.inflate(2130903162, parent, false);
        if (this.mDirection != null) {
            parent.addView(this.mDirection, index);
        }
        findViews();
        updateDirections();
    }

    private void findViews() {
        this.mDirectionPrimary = this.mDirection.findViewById(2131624390);
        this.mDirectionSecondary = this.mDirection.findViewById(2131624389);
        this.mPrimaryControls = new TextView[]{(TextView) this.mDirectionPrimary.findViewById(2131624396), (TextView) this.mDirectionPrimary.findViewById(2131624395), (TextView) this.mDirectionPrimary.findViewById(2131624394)};
        this.mSecondaryControls = new TextView[]{(TextView) this.mDirectionSecondary.findViewById(2131624393), (TextView) this.mDirectionSecondary.findViewById(2131624392), (TextView) this.mDirectionSecondary.findViewById(2131624391)};
        this.mDistanceCtrl = (TextView) this.mDirectionPrimary.findViewById(2131624397);
        this.mDirectionPrimary.setOnClickListener(new C13831());
    }

    public void setDirectionsControlsVisible(boolean visible) {
        ResourceManager.makeControlVisible(this.mDirectionPrimary, visible, true);
        VerticalExpandingAnimator.animateView(this.mDirectionSecondary, visible);
    }

    private void updateDirections() {
        updateDirections(this.mDirectionItem, true);
    }

    public void updateDirections(DirectionStatus direction) {
        updateDirections(direction, false);
    }

    private void updateDirections(DirectionStatus direction, boolean forceRedraw) {
        if (direction == null || !direction.bValidPrimary) {
            ResourceManager.makeControlVisible(this.mDirectionPrimary, false, false);
            ResourceManager.makeControlVisible(this.mDirectionSecondary, false, true);
            return;
        }
        ResourceManager.makeControlVisible(this.mDirectionPrimary, true);
        VerticalExpandingAnimator.animateView(this.mDirectionSecondary, direction.bValidSecondary);
        if (direction != null) {
            if (direction.bValidPrimary) {
                ResourceManager.drawMultiFontIcon(this.mInflater.getContext(), this.mPrimaryControls, direction.arrPrimaryChars, this.mDirectionItem != null ? this.mDirectionItem.arrPrimaryChars : null, true);
                if (isCharFinish(direction.arrPrimaryChars[0])) {
                    this.mPrimaryControls[0].setTextColor(this.mDirection.getResources().getColor(2131558437));
                } else {
                    this.mPrimaryControls[0].setTextColor(this.mDirection.getResources().getColor(this.mIsNightModeOn ? 2131558439 : 2131558438));
                }
                if ((forceRedraw || this.mDirectionItem == null || direction.nDistance != this.mDirectionItem.nDistance) && ResourceManager.nativeFormatDistance((long) direction.nDistance, true, true, false) != null) {
                    this.mDistanceCtrl.setText(String.format("%s%s", new Object[]{ResourceManager.nativeFormatDistance((long) direction.nDistance, true, true, false).getValue(), ResourceManager.nativeFormatDistance((long) direction.nDistance, true, true, false).getUnit()}));
                }
            }
            if (direction.bValidSecondary) {
                int[] refCodePoint;
                if (this.mDirectionItem != null) {
                    refCodePoint = this.mDirectionItem.arrSecondaryChars;
                } else {
                    refCodePoint = null;
                }
                ResourceManager.drawMultiFontIcon(this.mInflater.getContext(), this.mSecondaryControls, direction.arrSecondaryChars, refCodePoint, true);
            }
        }
        this.mDirectionItem = direction;
    }

    private boolean isCharFinish(int ch) {
        return ch == 57708;
    }
}
