package com.sygic.aura.navigate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.ViewAnimator;
import com.sygic.aura.route.DemoManager;
import com.sygic.aura.views.font_specials.SButton;
import com.sygic.aura.views.font_specials.SToggleButton;
import loquendo.tts.engine.TTSConst;

public class DemoControlsHolder {
    private ViewGroup mDemoControls;
    private SButton mDemoSpeed;
    private SToggleButton mDemoStart;
    private final LayoutInflater mInflater;
    private int mOrientation;

    /* renamed from: com.sygic.aura.navigate.DemoControlsHolder.1 */
    class C13811 implements OnClickListener {
        C13811() {
        }

        public void onClick(View v) {
            if (DemoManager.nativeIsDemoRunning() || DemoManager.nativeIsDemoPaused()) {
                DemoManager.nativeSwapDemoPaused();
            } else {
                DemoManager.nativeDemonstrateStart(0);
            }
        }
    }

    /* renamed from: com.sygic.aura.navigate.DemoControlsHolder.2 */
    class C13822 implements OnClickListener {
        C13822() {
        }

        public void onClick(View v) {
            DemoControlsHolder.this.mDemoSpeed.setText((DemoManager.rotateSpeed() / 100) + "x");
        }
    }

    public DemoControlsHolder(ViewGroup demoControls, Context context) {
        this.mDemoControls = demoControls;
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
        findViews();
    }

    public void onConfigurationChanged(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            int originalVisibility = this.mDemoControls.getVisibility();
            CharSequence originalText = this.mDemoSpeed.getText();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mDemoControls.getLayoutParams();
            ViewGroup parent = (ViewGroup) this.mDemoControls.getParent();
            int index = parent.indexOfChild(this.mDemoControls);
            parent.removeView(this.mDemoControls);
            this.mDemoControls = (ViewGroup) this.mInflater.inflate(2130903099, parent, false);
            if (this.mDemoControls != null) {
                this.mDemoControls.setLayoutParams(this.mDemoControls.getLayoutParams());
                this.mDemoControls.setVisibility(originalVisibility);
                parent.addView(this.mDemoControls, index);
                if (!DemoManager.isStopped()) {
                    ((ViewAnimator) parent).setDisplayedChild(index);
                }
            }
            findViews();
            this.mDemoSpeed.setText(originalText);
        }
    }

    private void findViews() {
        this.mDemoStart = (SToggleButton) this.mDemoControls.findViewById(2131624175);
        this.mDemoStart.setChecked(DemoManager.nativeIsDemoRunning());
        this.mDemoStart.setOnClickListener(new C13811());
        this.mDemoSpeed = (SButton) this.mDemoControls.findViewById(2131624176);
        this.mDemoSpeed.setOnClickListener(new C13822());
    }

    public void cancelDemo() {
        this.mDemoStart.setChecked(false);
        setDemoControlVisible(false);
    }

    public void setDemoControlVisible(boolean visible) {
        this.mDemoStart.setChecked(visible);
        if (visible) {
            this.mDemoSpeed.setText(getDemoSpeed());
            this.mDemoControls.setVisibility(0);
            return;
        }
        this.mDemoControls.setVisibility(8);
    }

    private String getDemoSpeed() {
        return (DemoManager.nativeGetDemoSpeed() / 100) + "x";
    }

    public void setStartPauseButton(boolean started) {
        this.mDemoStart.setChecked(started);
        this.mDemoSpeed.setText(getDemoSpeed());
    }
}
