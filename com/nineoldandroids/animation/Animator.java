package com.nineoldandroids.animation;

import java.util.ArrayList;

public abstract class Animator implements Cloneable {
    ArrayList<AnimatorListener> mListeners;

    public interface AnimatorListener {
        void onAnimationCancel(Animator animator);

        void onAnimationEnd(Animator animator);

        void onAnimationRepeat(Animator animator);

        void onAnimationStart(Animator animator);
    }

    public abstract boolean isRunning();

    public abstract Animator setDuration(long j);

    public Animator() {
        this.mListeners = null;
    }

    public void start() {
    }

    public void cancel() {
    }

    public boolean isStarted() {
        return isRunning();
    }

    public void addListener(AnimatorListener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(listener);
    }

    public void removeListener(AnimatorListener listener) {
        if (this.mListeners != null) {
            this.mListeners.remove(listener);
            if (this.mListeners.size() == 0) {
                this.mListeners = null;
            }
        }
    }

    public ArrayList<AnimatorListener> getListeners() {
        return this.mListeners;
    }

    public Animator clone() {
        try {
            Animator anim = (Animator) super.clone();
            if (this.mListeners != null) {
                ArrayList<AnimatorListener> oldListeners = this.mListeners;
                anim.mListeners = new ArrayList();
                int numListeners = oldListeners.size();
                for (int i = 0; i < numListeners; i++) {
                    anim.mListeners.add(oldListeners.get(i));
                }
            }
            return anim;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
