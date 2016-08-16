package com.sygic.aura.map.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import java.util.ArrayList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class HorizontalLoadingView extends FrameLayout {
    private final View[] mAnimatedViews;
    private AnimatorSet mAnimatorSet;
    private RepeatingThread mInitThread;

    /* renamed from: com.sygic.aura.map.view.HorizontalLoadingView.1 */
    class C13651 implements ExecutionOrder {
        int mWidth;

        /* renamed from: com.sygic.aura.map.view.HorizontalLoadingView.1.1 */
        class C13641 implements Runnable {
            C13641() {
            }

            public void run() {
                HorizontalLoadingView.this.mAnimatorSet.start();
            }
        }

        C13651() {
        }

        public boolean runningCondition() {
            int width = HorizontalLoadingView.this.getWidth();
            this.mWidth = width;
            return width > 0;
        }

        public boolean onPositive() {
            HorizontalLoadingView.this.initAnimatorInternal(this.mWidth);
            ((Activity) HorizontalLoadingView.this.getContext()).runOnUiThread(new C13641());
            return false;
        }

        public boolean onNegative() {
            return true;
        }
    }

    /* renamed from: com.sygic.aura.map.view.HorizontalLoadingView.2 */
    class C13662 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$view;

        C13662(View view) {
            this.val$view = view;
        }

        public void onAnimationStart(Animator animation) {
            this.val$view.setVisibility(0);
        }

        public void onAnimationCancel(Animator animation) {
            this.val$view.setVisibility(8);
        }
    }

    /* renamed from: com.sygic.aura.map.view.HorizontalLoadingView.3 */
    class C13673 extends AnimatorListenerAdapter {
        C13673() {
        }

        public void onAnimationEnd(Animator animation) {
            HorizontalLoadingView.this.mAnimatorSet.start();
        }
    }

    public HorizontalLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAnimatedViews = new View[3];
        init(context);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        initAnimator();
    }

    private void init(Context context) {
        int i = 0;
        this.mAnimatedViews[0] = new View(context);
        this.mAnimatedViews[1] = new View(context);
        this.mAnimatedViews[2] = new View(context);
        int size = (int) getResources().getDimension(2131230898);
        LayoutParams params = new LayoutParams(size, size);
        View[] viewArr = this.mAnimatedViews;
        int length = viewArr.length;
        while (i < length) {
            View view = viewArr[i];
            view.setBackgroundColor(-1);
            view.setVisibility(8);
            addView(view, params);
            i++;
        }
    }

    protected void finalize() throws Throwable {
        if (this.mInitThread.isAlive()) {
            this.mInitThread.setFinished(true);
        }
        super.finalize();
    }

    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            super.setVisibility(visibility);
            switch (visibility) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    startAnimationInternal();
                case TTSConst.TTSXML /*4*/:
                case TTSConst.TTSEVT_TAG /*8*/:
                    stopAnimationInternal();
                default:
            }
        }
    }

    private void stopAnimationInternal() {
        if (this.mAnimatorSet != null) {
            this.mAnimatorSet.cancel();
        }
    }

    private void startAnimationInternal() {
        if (this.mAnimatorSet != null) {
            this.mAnimatorSet.start();
        }
    }

    private void initAnimator() {
        this.mInitThread = new RepeatingThread(new C13651(), 1000);
        this.mInitThread.start();
    }

    private void initAnimatorInternal(int viewWidth) {
        List<Animator> animators = new ArrayList(this.mAnimatedViews.length);
        for (int i = 0; i < this.mAnimatedViews.length; i++) {
            View view = this.mAnimatedViews[i];
            ValueAnimator animator = ObjectAnimator.ofFloat(view, "x", new float[]{view.getX() - 30.0f, ((float) viewWidth) / 4.0f, ((float) viewWidth) / 3.0f, ((float) (viewWidth * 2)) / 5.0f, ((float) viewWidth) / 2.0f, ((float) (viewWidth * 3)) / 5.0f, ((float) (viewWidth * 2)) / 3.0f, ((float) (viewWidth * 3)) / 4.0f, (float) (viewWidth + 50)});
            animator.setDuration(5000);
            animator.setRepeatCount(0);
            animator.setStartDelay((long) (i * 400));
            animator.setRepeatMode(2);
            animator.addListener(new C13662(view));
            animators.add(animator);
        }
        this.mAnimatorSet = new AnimatorSet();
        this.mAnimatorSet.playTogether(animators);
        this.mAnimatorSet.addListener(new C13673());
    }
}
