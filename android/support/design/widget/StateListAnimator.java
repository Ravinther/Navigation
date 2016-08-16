package android.support.design.widget;

import android.util.StateSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

final class StateListAnimator {
    private AnimationListener mAnimationListener;
    private Tuple mLastMatch;
    private Animation mRunningAnimation;
    private final ArrayList<Tuple> mTuples;
    private WeakReference<View> mViewRef;

    /* renamed from: android.support.design.widget.StateListAnimator.1 */
    class C00221 implements AnimationListener {
        C00221() {
        }

        public void onAnimationEnd(Animation animation) {
            if (StateListAnimator.this.mRunningAnimation == animation) {
                StateListAnimator.this.mRunningAnimation = null;
            }
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    static class Tuple {
        final Animation mAnimation;
        final int[] mSpecs;

        private Tuple(int[] specs, Animation Animation) {
            this.mSpecs = specs;
            this.mAnimation = Animation;
        }
    }

    StateListAnimator() {
        this.mTuples = new ArrayList();
        this.mLastMatch = null;
        this.mRunningAnimation = null;
        this.mAnimationListener = new C00221();
    }

    public void addState(int[] specs, Animation animation) {
        Tuple tuple = new Tuple(animation, null);
        animation.setAnimationListener(this.mAnimationListener);
        this.mTuples.add(tuple);
    }

    View getTarget() {
        return this.mViewRef == null ? null : (View) this.mViewRef.get();
    }

    void setTarget(View view) {
        View current = getTarget();
        if (current != view) {
            if (current != null) {
                clearTarget();
            }
            if (view != null) {
                this.mViewRef = new WeakReference(view);
            }
        }
    }

    private void clearTarget() {
        View view = getTarget();
        int size = this.mTuples.size();
        for (int i = 0; i < size; i++) {
            if (view.getAnimation() == ((Tuple) this.mTuples.get(i)).mAnimation) {
                view.clearAnimation();
            }
        }
        this.mViewRef = null;
        this.mLastMatch = null;
        this.mRunningAnimation = null;
    }

    void setState(int[] state) {
        Tuple match = null;
        int count = this.mTuples.size();
        for (int i = 0; i < count; i++) {
            Tuple tuple = (Tuple) this.mTuples.get(i);
            if (StateSet.stateSetMatches(tuple.mSpecs, state)) {
                match = tuple;
                break;
            }
        }
        if (match != this.mLastMatch) {
            if (this.mLastMatch != null) {
                cancel();
            }
            this.mLastMatch = match;
            if (match != null) {
                start(match);
            }
        }
    }

    private void start(Tuple match) {
        this.mRunningAnimation = match.mAnimation;
        View view = getTarget();
        if (view != null) {
            view.startAnimation(this.mRunningAnimation);
        }
    }

    private void cancel() {
        if (this.mRunningAnimation != null) {
            View view = getTarget();
            if (view != null && view.getAnimation() == this.mRunningAnimation) {
                view.clearAnimation();
            }
            this.mRunningAnimation = null;
        }
    }

    public void jumpToCurrentState() {
        if (this.mRunningAnimation != null) {
            View view = getTarget();
            if (view != null && view.getAnimation() == this.mRunningAnimation) {
                view.clearAnimation();
            }
        }
    }
}
