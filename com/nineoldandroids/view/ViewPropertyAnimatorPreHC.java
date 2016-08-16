package com.nineoldandroids.view;

import android.view.View;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.sygic.aura.C1090R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import loquendo.tts.engine.TTSConst;

class ViewPropertyAnimatorPreHC extends ViewPropertyAnimator {
    private Runnable mAnimationStarter;
    private AnimatorEventListener mAnimatorEventListener;
    private HashMap<Animator, PropertyBundle> mAnimatorMap;
    private long mDuration;
    private boolean mDurationSet;
    private Interpolator mInterpolator;
    private boolean mInterpolatorSet;
    private AnimatorListener mListener;
    ArrayList<NameValuesHolder> mPendingAnimations;
    private final AnimatorProxy mProxy;
    private long mStartDelay;
    private boolean mStartDelaySet;
    private final WeakReference<View> mView;

    /* renamed from: com.nineoldandroids.view.ViewPropertyAnimatorPreHC.1 */
    class C10751 implements Runnable {
        C10751() {
        }

        public void run() {
            ViewPropertyAnimatorPreHC.this.startAnimation();
        }
    }

    private class AnimatorEventListener implements AnimatorListener, AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        public void onAnimationStart(Animator animation) {
            if (ViewPropertyAnimatorPreHC.this.mListener != null) {
                ViewPropertyAnimatorPreHC.this.mListener.onAnimationStart(animation);
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (ViewPropertyAnimatorPreHC.this.mListener != null) {
                ViewPropertyAnimatorPreHC.this.mListener.onAnimationCancel(animation);
            }
        }

        public void onAnimationRepeat(Animator animation) {
            if (ViewPropertyAnimatorPreHC.this.mListener != null) {
                ViewPropertyAnimatorPreHC.this.mListener.onAnimationRepeat(animation);
            }
        }

        public void onAnimationEnd(Animator animation) {
            if (ViewPropertyAnimatorPreHC.this.mListener != null) {
                ViewPropertyAnimatorPreHC.this.mListener.onAnimationEnd(animation);
            }
            ViewPropertyAnimatorPreHC.this.mAnimatorMap.remove(animation);
            if (ViewPropertyAnimatorPreHC.this.mAnimatorMap.isEmpty()) {
                ViewPropertyAnimatorPreHC.this.mListener = null;
            }
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            View v;
            float fraction = animation.getAnimatedFraction();
            PropertyBundle propertyBundle = (PropertyBundle) ViewPropertyAnimatorPreHC.this.mAnimatorMap.get(animation);
            if ((propertyBundle.mPropertyMask & 511) != 0) {
                v = (View) ViewPropertyAnimatorPreHC.this.mView.get();
                if (v != null) {
                    v.invalidate();
                }
            }
            ArrayList<NameValuesHolder> valueList = propertyBundle.mNameValuesHolder;
            if (valueList != null) {
                int count = valueList.size();
                for (int i = 0; i < count; i++) {
                    NameValuesHolder values = (NameValuesHolder) valueList.get(i);
                    ViewPropertyAnimatorPreHC.this.setValue(values.mNameConstant, values.mFromValue + (values.mDeltaValue * fraction));
                }
            }
            v = (View) ViewPropertyAnimatorPreHC.this.mView.get();
            if (v != null) {
                v.invalidate();
            }
        }
    }

    private static class NameValuesHolder {
        float mDeltaValue;
        float mFromValue;
        int mNameConstant;

        NameValuesHolder(int nameConstant, float fromValue, float deltaValue) {
            this.mNameConstant = nameConstant;
            this.mFromValue = fromValue;
            this.mDeltaValue = deltaValue;
        }
    }

    private static class PropertyBundle {
        ArrayList<NameValuesHolder> mNameValuesHolder;
        int mPropertyMask;

        PropertyBundle(int propertyMask, ArrayList<NameValuesHolder> nameValuesHolder) {
            this.mPropertyMask = propertyMask;
            this.mNameValuesHolder = nameValuesHolder;
        }

        boolean cancel(int propertyConstant) {
            if (!((this.mPropertyMask & propertyConstant) == 0 || this.mNameValuesHolder == null)) {
                int count = this.mNameValuesHolder.size();
                for (int i = 0; i < count; i++) {
                    if (((NameValuesHolder) this.mNameValuesHolder.get(i)).mNameConstant == propertyConstant) {
                        this.mNameValuesHolder.remove(i);
                        this.mPropertyMask &= propertyConstant ^ -1;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    ViewPropertyAnimatorPreHC(View view) {
        this.mDurationSet = false;
        this.mStartDelay = 0;
        this.mStartDelaySet = false;
        this.mInterpolatorSet = false;
        this.mListener = null;
        this.mAnimatorEventListener = new AnimatorEventListener();
        this.mPendingAnimations = new ArrayList();
        this.mAnimationStarter = new C10751();
        this.mAnimatorMap = new HashMap();
        this.mView = new WeakReference(view);
        this.mProxy = AnimatorProxy.wrap(view);
    }

    public ViewPropertyAnimator setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + duration);
        }
        this.mDurationSet = true;
        this.mDuration = duration;
        return this;
    }

    public ViewPropertyAnimator setStartDelay(long startDelay) {
        if (startDelay < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + startDelay);
        }
        this.mStartDelaySet = true;
        this.mStartDelay = startDelay;
        return this;
    }

    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        this.mInterpolatorSet = true;
        this.mInterpolator = interpolator;
        return this;
    }

    public ViewPropertyAnimator setListener(AnimatorListener listener) {
        this.mListener = listener;
        return this;
    }

    public void cancel() {
        if (this.mAnimatorMap.size() > 0) {
            for (Animator runningAnim : ((HashMap) this.mAnimatorMap.clone()).keySet()) {
                runningAnim.cancel();
            }
        }
        this.mPendingAnimations.clear();
        View v = (View) this.mView.get();
        if (v != null) {
            v.removeCallbacks(this.mAnimationStarter);
        }
    }

    public ViewPropertyAnimator translationY(float value) {
        animateProperty(2, value);
        return this;
    }

    public ViewPropertyAnimator alpha(float value) {
        animateProperty(512, value);
        return this;
    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f);
        ArrayList<NameValuesHolder> nameValueList = (ArrayList) this.mPendingAnimations.clone();
        this.mPendingAnimations.clear();
        int propertyMask = 0;
        for (int i = 0; i < nameValueList.size(); i++) {
            propertyMask |= ((NameValuesHolder) nameValueList.get(i)).mNameConstant;
        }
        this.mAnimatorMap.put(animator, new PropertyBundle(propertyMask, nameValueList));
        animator.addUpdateListener(this.mAnimatorEventListener);
        animator.addListener(this.mAnimatorEventListener);
        if (this.mStartDelaySet) {
            animator.setStartDelay(this.mStartDelay);
        }
        if (this.mDurationSet) {
            animator.setDuration(this.mDuration);
        }
        if (this.mInterpolatorSet) {
            animator.setInterpolator(this.mInterpolator);
        }
        animator.start();
    }

    private void animateProperty(int constantName, float toValue) {
        float fromValue = getValue(constantName);
        animatePropertyBy(constantName, fromValue, toValue - fromValue);
    }

    private void animatePropertyBy(int constantName, float startValue, float byValue) {
        if (this.mAnimatorMap.size() > 0) {
            Animator animatorToCancel = null;
            for (Animator runningAnim : this.mAnimatorMap.keySet()) {
                PropertyBundle bundle = (PropertyBundle) this.mAnimatorMap.get(runningAnim);
                if (bundle.cancel(constantName) && bundle.mPropertyMask == 0) {
                    animatorToCancel = runningAnim;
                    break;
                }
            }
            if (animatorToCancel != null) {
                animatorToCancel.cancel();
            }
        }
        this.mPendingAnimations.add(new NameValuesHolder(constantName, startValue, byValue));
        View v = (View) this.mView.get();
        if (v != null) {
            v.removeCallbacks(this.mAnimationStarter);
            v.post(this.mAnimationStarter);
        }
    }

    private void setValue(int propertyConstant, float value) {
        switch (propertyConstant) {
            case TTSConst.TTSMULTILINE /*1*/:
                this.mProxy.setTranslationX(value);
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mProxy.setTranslationY(value);
            case TTSConst.TTSXML /*4*/:
                this.mProxy.setScaleX(value);
            case TTSConst.TTSEVT_TAG /*8*/:
                this.mProxy.setScaleY(value);
            case TTSConst.TTSEVT_ERROR /*16*/:
                this.mProxy.setRotation(value);
            case C1090R.styleable.Theme_actionModeCutDrawable /*32*/:
                this.mProxy.setRotationX(value);
            case C1090R.styleable.Theme_textAppearanceSearchResultTitle /*64*/:
                this.mProxy.setRotationY(value);
            case 128:
                this.mProxy.setX(value);
            case 256:
                this.mProxy.setY(value);
            case 512:
                this.mProxy.setAlpha(value);
            default:
        }
    }

    private float getValue(int propertyConstant) {
        switch (propertyConstant) {
            case TTSConst.TTSMULTILINE /*1*/:
                return this.mProxy.getTranslationX();
            case TTSConst.TTSPARAGRAPH /*2*/:
                return this.mProxy.getTranslationY();
            case TTSConst.TTSXML /*4*/:
                return this.mProxy.getScaleX();
            case TTSConst.TTSEVT_TAG /*8*/:
                return this.mProxy.getScaleY();
            case TTSConst.TTSEVT_ERROR /*16*/:
                return this.mProxy.getRotation();
            case C1090R.styleable.Theme_actionModeCutDrawable /*32*/:
                return this.mProxy.getRotationX();
            case C1090R.styleable.Theme_textAppearanceSearchResultTitle /*64*/:
                return this.mProxy.getRotationY();
            case 128:
                return this.mProxy.getX();
            case 256:
                return this.mProxy.getY();
            case 512:
                return this.mProxy.getAlpha();
            default:
                return 0.0f;
        }
    }
}
