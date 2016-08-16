package com.nineoldandroids.animation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AndroidRuntimeException;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import loquendo.tts.engine.TTSConst;

public class ValueAnimator extends Animator {
    private static ThreadLocal<AnimationHandler> sAnimationHandler;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sAnimations;
    private static final Interpolator sDefaultInterpolator;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sDelayedAnims;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sEndingAnims;
    private static final TypeEvaluator sFloatEvaluator;
    private static long sFrameDelay;
    private static final TypeEvaluator sIntEvaluator;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sPendingAnimations;
    private static final ThreadLocal<ArrayList<ValueAnimator>> sReadyAnims;
    private float mCurrentFraction;
    private int mCurrentIteration;
    private long mDelayStartTime;
    private long mDuration;
    boolean mInitialized;
    private Interpolator mInterpolator;
    private boolean mPlayingBackwards;
    int mPlayingState;
    private int mRepeatCount;
    private int mRepeatMode;
    private boolean mRunning;
    long mSeekTime;
    private long mStartDelay;
    long mStartTime;
    private boolean mStarted;
    private boolean mStartedDelay;
    private ArrayList<AnimatorUpdateListener> mUpdateListeners;
    PropertyValuesHolder[] mValues;
    HashMap<String, PropertyValuesHolder> mValuesMap;

    /* renamed from: com.nineoldandroids.animation.ValueAnimator.1 */
    static class C10681 extends ThreadLocal<ArrayList<ValueAnimator>> {
        C10681() {
        }

        protected ArrayList<ValueAnimator> initialValue() {
            return new ArrayList();
        }
    }

    /* renamed from: com.nineoldandroids.animation.ValueAnimator.2 */
    static class C10692 extends ThreadLocal<ArrayList<ValueAnimator>> {
        C10692() {
        }

        protected ArrayList<ValueAnimator> initialValue() {
            return new ArrayList();
        }
    }

    /* renamed from: com.nineoldandroids.animation.ValueAnimator.3 */
    static class C10703 extends ThreadLocal<ArrayList<ValueAnimator>> {
        C10703() {
        }

        protected ArrayList<ValueAnimator> initialValue() {
            return new ArrayList();
        }
    }

    /* renamed from: com.nineoldandroids.animation.ValueAnimator.4 */
    static class C10714 extends ThreadLocal<ArrayList<ValueAnimator>> {
        C10714() {
        }

        protected ArrayList<ValueAnimator> initialValue() {
            return new ArrayList();
        }
    }

    /* renamed from: com.nineoldandroids.animation.ValueAnimator.5 */
    static class C10725 extends ThreadLocal<ArrayList<ValueAnimator>> {
        C10725() {
        }

        protected ArrayList<ValueAnimator> initialValue() {
            return new ArrayList();
        }
    }

    private static class AnimationHandler extends Handler {
        private AnimationHandler() {
        }

        public void handleMessage(Message msg) {
            int i;
            ValueAnimator anim;
            boolean callAgain = true;
            ArrayList<ValueAnimator> animations = (ArrayList) ValueAnimator.sAnimations.get();
            ArrayList<ValueAnimator> delayedAnims = (ArrayList) ValueAnimator.sDelayedAnims.get();
            switch (msg.what) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    ArrayList<ValueAnimator> pendingAnimations = (ArrayList) ValueAnimator.sPendingAnimations.get();
                    if (animations.size() > 0 || delayedAnims.size() > 0) {
                        callAgain = false;
                    }
                    while (pendingAnimations.size() > 0) {
                        ArrayList<ValueAnimator> pendingCopy = (ArrayList) pendingAnimations.clone();
                        pendingAnimations.clear();
                        int count = pendingCopy.size();
                        for (i = 0; i < count; i++) {
                            anim = (ValueAnimator) pendingCopy.get(i);
                            if (anim.mStartDelay == 0) {
                                anim.startAnimation();
                            } else {
                                delayedAnims.add(anim);
                            }
                        }
                    }
                    break;
                case TTSConst.TTSMULTILINE /*1*/:
                    break;
                default:
                    return;
            }
            long currentTime = AnimationUtils.currentAnimationTimeMillis();
            ArrayList<ValueAnimator> readyAnims = (ArrayList) ValueAnimator.sReadyAnims.get();
            ArrayList<ValueAnimator> endingAnims = (ArrayList) ValueAnimator.sEndingAnims.get();
            int numDelayedAnims = delayedAnims.size();
            for (i = 0; i < numDelayedAnims; i++) {
                anim = (ValueAnimator) delayedAnims.get(i);
                if (anim.delayedAnimationFrame(currentTime)) {
                    readyAnims.add(anim);
                }
            }
            int numReadyAnims = readyAnims.size();
            if (numReadyAnims > 0) {
                for (i = 0; i < numReadyAnims; i++) {
                    anim = (ValueAnimator) readyAnims.get(i);
                    anim.startAnimation();
                    anim.mRunning = true;
                    delayedAnims.remove(anim);
                }
                readyAnims.clear();
            }
            int numAnims = animations.size();
            i = 0;
            while (i < numAnims) {
                anim = (ValueAnimator) animations.get(i);
                if (anim.animationFrame(currentTime)) {
                    endingAnims.add(anim);
                }
                if (animations.size() == numAnims) {
                    i++;
                } else {
                    numAnims--;
                    endingAnims.remove(anim);
                }
            }
            if (endingAnims.size() > 0) {
                for (i = 0; i < endingAnims.size(); i++) {
                    ((ValueAnimator) endingAnims.get(i)).endAnimation();
                }
                endingAnims.clear();
            }
            if (!callAgain) {
                return;
            }
            if (!animations.isEmpty() || !delayedAnims.isEmpty()) {
                sendEmptyMessageDelayed(1, Math.max(0, ValueAnimator.sFrameDelay - (AnimationUtils.currentAnimationTimeMillis() - currentTime)));
            }
        }
    }

    public interface AnimatorUpdateListener {
        void onAnimationUpdate(ValueAnimator valueAnimator);
    }

    static {
        sAnimationHandler = new ThreadLocal();
        sAnimations = new C10681();
        sPendingAnimations = new C10692();
        sDelayedAnims = new C10703();
        sEndingAnims = new C10714();
        sReadyAnims = new C10725();
        sDefaultInterpolator = new AccelerateDecelerateInterpolator();
        sIntEvaluator = new IntEvaluator();
        sFloatEvaluator = new FloatEvaluator();
        sFrameDelay = 10;
    }

    public ValueAnimator() {
        this.mSeekTime = -1;
        this.mPlayingBackwards = false;
        this.mCurrentIteration = 0;
        this.mCurrentFraction = 0.0f;
        this.mStartedDelay = false;
        this.mPlayingState = 0;
        this.mRunning = false;
        this.mStarted = false;
        this.mInitialized = false;
        this.mDuration = 300;
        this.mStartDelay = 0;
        this.mRepeatCount = 0;
        this.mRepeatMode = 1;
        this.mInterpolator = sDefaultInterpolator;
        this.mUpdateListeners = null;
    }

    public static ValueAnimator ofFloat(float... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setFloatValues(values);
        return anim;
    }

    public void setFloatValues(float... values) {
        if (values != null && values.length != 0) {
            if (this.mValues == null || this.mValues.length == 0) {
                setValues(PropertyValuesHolder.ofFloat("", values));
            } else {
                this.mValues[0].setFloatValues(values);
            }
            this.mInitialized = false;
        }
    }

    public void setValues(PropertyValuesHolder... values) {
        this.mValues = values;
        this.mValuesMap = new HashMap(numValues);
        for (PropertyValuesHolder valuesHolder : values) {
            this.mValuesMap.put(valuesHolder.getPropertyName(), valuesHolder);
        }
        this.mInitialized = false;
    }

    void initAnimation() {
        if (!this.mInitialized) {
            for (PropertyValuesHolder init : this.mValues) {
                init.init();
            }
            this.mInitialized = true;
        }
    }

    public ValueAnimator setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + duration);
        }
        this.mDuration = duration;
        return this;
    }

    public void setCurrentPlayTime(long playTime) {
        initAnimation();
        long currentTime = AnimationUtils.currentAnimationTimeMillis();
        if (this.mPlayingState != 1) {
            this.mSeekTime = playTime;
            this.mPlayingState = 2;
        }
        this.mStartTime = currentTime - playTime;
        animationFrame(currentTime);
    }

    public long getCurrentPlayTime() {
        if (!this.mInitialized || this.mPlayingState == 0) {
            return 0;
        }
        return AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
    }

    public void setStartDelay(long startDelay) {
        this.mStartDelay = startDelay;
    }

    public void addUpdateListener(AnimatorUpdateListener listener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList();
        }
        this.mUpdateListeners.add(listener);
    }

    public void setInterpolator(Interpolator value) {
        if (value != null) {
            this.mInterpolator = value;
        } else {
            this.mInterpolator = new LinearInterpolator();
        }
    }

    private void start(boolean playBackwards) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mPlayingBackwards = playBackwards;
        this.mCurrentIteration = 0;
        this.mPlayingState = 0;
        this.mStarted = true;
        this.mStartedDelay = false;
        ((ArrayList) sPendingAnimations.get()).add(this);
        if (this.mStartDelay == 0) {
            setCurrentPlayTime(getCurrentPlayTime());
            this.mPlayingState = 0;
            this.mRunning = true;
            if (this.mListeners != null) {
                ArrayList<AnimatorListener> tmpListeners = (ArrayList) this.mListeners.clone();
                int numListeners = tmpListeners.size();
                for (int i = 0; i < numListeners; i++) {
                    ((AnimatorListener) tmpListeners.get(i)).onAnimationStart(this);
                }
            }
        }
        AnimationHandler animationHandler = (AnimationHandler) sAnimationHandler.get();
        if (animationHandler == null) {
            animationHandler = new AnimationHandler();
            sAnimationHandler.set(animationHandler);
        }
        animationHandler.sendEmptyMessage(0);
    }

    public void start() {
        start(false);
    }

    public void cancel() {
        if (this.mPlayingState != 0 || ((ArrayList) sPendingAnimations.get()).contains(this) || ((ArrayList) sDelayedAnims.get()).contains(this)) {
            if (this.mRunning && this.mListeners != null) {
                Iterator i$ = ((ArrayList) this.mListeners.clone()).iterator();
                while (i$.hasNext()) {
                    ((AnimatorListener) i$.next()).onAnimationCancel(this);
                }
            }
            endAnimation();
        }
    }

    public boolean isRunning() {
        return this.mPlayingState == 1 || this.mRunning;
    }

    public boolean isStarted() {
        return this.mStarted;
    }

    private void endAnimation() {
        ((ArrayList) sAnimations.get()).remove(this);
        ((ArrayList) sPendingAnimations.get()).remove(this);
        ((ArrayList) sDelayedAnims.get()).remove(this);
        this.mPlayingState = 0;
        if (this.mRunning && this.mListeners != null) {
            ArrayList<AnimatorListener> tmpListeners = (ArrayList) this.mListeners.clone();
            int numListeners = tmpListeners.size();
            for (int i = 0; i < numListeners; i++) {
                ((AnimatorListener) tmpListeners.get(i)).onAnimationEnd(this);
            }
        }
        this.mRunning = false;
        this.mStarted = false;
    }

    private void startAnimation() {
        initAnimation();
        ((ArrayList) sAnimations.get()).add(this);
        if (this.mStartDelay > 0 && this.mListeners != null) {
            ArrayList<AnimatorListener> tmpListeners = (ArrayList) this.mListeners.clone();
            int numListeners = tmpListeners.size();
            for (int i = 0; i < numListeners; i++) {
                ((AnimatorListener) tmpListeners.get(i)).onAnimationStart(this);
            }
        }
    }

    private boolean delayedAnimationFrame(long currentTime) {
        if (this.mStartedDelay) {
            long deltaTime = currentTime - this.mDelayStartTime;
            if (deltaTime > this.mStartDelay) {
                this.mStartTime = currentTime - (deltaTime - this.mStartDelay);
                this.mPlayingState = 1;
                return true;
            }
        }
        this.mStartedDelay = true;
        this.mDelayStartTime = currentTime;
        return false;
    }

    boolean animationFrame(long currentTime) {
        boolean done = false;
        if (this.mPlayingState == 0) {
            this.mPlayingState = 1;
            if (this.mSeekTime < 0) {
                this.mStartTime = currentTime;
            } else {
                this.mStartTime = currentTime - this.mSeekTime;
                this.mSeekTime = -1;
            }
        }
        switch (this.mPlayingState) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                float fraction;
                if (this.mDuration > 0) {
                    fraction = ((float) (currentTime - this.mStartTime)) / ((float) this.mDuration);
                } else {
                    fraction = 1.0f;
                }
                if (fraction >= 1.0f) {
                    if (this.mCurrentIteration < this.mRepeatCount || this.mRepeatCount == -1) {
                        if (this.mListeners != null) {
                            int numListeners = this.mListeners.size();
                            for (int i = 0; i < numListeners; i++) {
                                ((AnimatorListener) this.mListeners.get(i)).onAnimationRepeat(this);
                            }
                        }
                        if (this.mRepeatMode == 2) {
                            this.mPlayingBackwards = !this.mPlayingBackwards;
                        }
                        this.mCurrentIteration += (int) fraction;
                        fraction %= 1.0f;
                        this.mStartTime += this.mDuration;
                    } else {
                        done = true;
                        fraction = Math.min(fraction, 1.0f);
                    }
                }
                if (this.mPlayingBackwards) {
                    fraction = 1.0f - fraction;
                }
                animateValue(fraction);
                break;
        }
        return done;
    }

    public float getAnimatedFraction() {
        return this.mCurrentFraction;
    }

    void animateValue(float fraction) {
        int i;
        fraction = this.mInterpolator.getInterpolation(fraction);
        this.mCurrentFraction = fraction;
        for (PropertyValuesHolder calculateValue : this.mValues) {
            calculateValue.calculateValue(fraction);
        }
        if (this.mUpdateListeners != null) {
            int numListeners = this.mUpdateListeners.size();
            for (i = 0; i < numListeners; i++) {
                ((AnimatorUpdateListener) this.mUpdateListeners.get(i)).onAnimationUpdate(this);
            }
        }
    }

    public ValueAnimator clone() {
        int i;
        ValueAnimator anim = (ValueAnimator) super.clone();
        if (this.mUpdateListeners != null) {
            ArrayList<AnimatorUpdateListener> oldListeners = this.mUpdateListeners;
            anim.mUpdateListeners = new ArrayList();
            int numListeners = oldListeners.size();
            for (i = 0; i < numListeners; i++) {
                anim.mUpdateListeners.add(oldListeners.get(i));
            }
        }
        anim.mSeekTime = -1;
        anim.mPlayingBackwards = false;
        anim.mCurrentIteration = 0;
        anim.mInitialized = false;
        anim.mPlayingState = 0;
        anim.mStartedDelay = false;
        PropertyValuesHolder[] oldValues = this.mValues;
        if (oldValues != null) {
            int numValues = oldValues.length;
            anim.mValues = new PropertyValuesHolder[numValues];
            anim.mValuesMap = new HashMap(numValues);
            for (i = 0; i < numValues; i++) {
                PropertyValuesHolder newValuesHolder = oldValues[i].clone();
                anim.mValues[i] = newValuesHolder;
                anim.mValuesMap.put(newValuesHolder.getPropertyName(), newValuesHolder);
            }
        }
        return anim;
    }

    public String toString() {
        String returnVal = "ValueAnimator@" + Integer.toHexString(hashCode());
        if (this.mValues != null) {
            for (PropertyValuesHolder propertyValuesHolder : this.mValues) {
                returnVal = returnVal + "\n    " + propertyValuesHolder.toString();
            }
        }
        return returnVal;
    }
}
