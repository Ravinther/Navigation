package com.sygic.aura.map.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapOverlayAnimator extends FrameLayout {
    private Map<Mode, Set<View>> animationMap;
    private OnChildChangeListener mListener;
    private Mode mWhichMode;

    public interface OnChildChangeListener {
        void onChildSwitched(Mode mode, Mode mode2);
    }

    /* renamed from: com.sygic.aura.map.view.MapOverlayAnimator.1 */
    class C13681 extends AnimatorListenerAdapter {
        final /* synthetic */ Mode val$actualChild;
        final /* synthetic */ Mode val$previousChild;

        C13681(Mode mode, Mode mode2) {
            this.val$previousChild = mode;
            this.val$actualChild = mode2;
        }

        public void onAnimationEnd(Animator animator) {
            MapOverlayAnimator.this.getChildForMode(this.val$previousChild).setVisibility(8);
            if (MapOverlayAnimator.this.getDisplayedMode().equals(this.val$actualChild)) {
                MapOverlayAnimator.this.getChildForMode(this.val$actualChild).setVisibility(0);
                MapOverlayAnimator.this.modernAnimateIn(this.val$actualChild);
            }
        }
    }

    /* renamed from: com.sygic.aura.map.view.MapOverlayAnimator.2 */
    class C13692 extends com.nineoldandroids.animation.AnimatorListenerAdapter {
        final /* synthetic */ Mode val$actualChild;
        final /* synthetic */ Mode val$previousChild;

        C13692(Mode mode, Mode mode2) {
            this.val$previousChild = mode;
            this.val$actualChild = mode2;
        }

        public void onAnimationEnd(com.nineoldandroids.animation.Animator animator) {
            MapOverlayAnimator.this.getChildForMode(this.val$previousChild).setVisibility(8);
            if (MapOverlayAnimator.this.getDisplayedMode().equals(this.val$actualChild)) {
                MapOverlayAnimator.this.getChildForMode(this.val$actualChild).setVisibility(0);
                MapOverlayAnimator.this.legacyAnimateIn(this.val$actualChild);
            }
        }
    }

    private class AnimationHolder {
        final String property;
        final float[] values;

        private AnimationHolder(String property, float... values) {
            this.property = property;
            this.values = values;
        }
    }

    public MapOverlayAnimator(Context context) {
        super(context);
        init();
    }

    public MapOverlayAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapOverlayAnimator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.mWhichMode = Mode.values()[0];
        this.animationMap = new HashMap();
    }

    public void addView(View child) {
        super.addView(child);
        if (getChildCount() > 1) {
            child.setVisibility(8);
        }
    }

    public void addView(View child, int index) {
        super.addView(child, index);
        if (index > 0) {
            child.setVisibility(8);
        }
    }

    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        if (getChildCount() > 1) {
            child.setVisibility(8);
        }
    }

    public void addView(View child, LayoutParams params) {
        super.addView(child, params);
        if (getChildCount() > 1) {
            child.setVisibility(8);
        }
    }

    public void addView(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        if (index > 0) {
            child.setVisibility(8);
        }
    }

    public void setOnChildChangeListener(OnChildChangeListener listener) {
        this.mListener = listener;
    }

    public View getChildForMode(Mode whichChild) {
        return super.getChildAt(whichChild.ordinal());
    }

    public synchronized void setDisplayedMode(Mode whichChild) {
        Mode previousChild = getDisplayedMode();
        if (this.mListener != null) {
            this.mListener.onChildSwitched(previousChild, whichChild);
        }
        boolean hasFocus = getFocusedChild() != null;
        whichChild = showOnly(whichChild);
        if (hasFocus) {
            requestFocus(2);
        }
        this.mWhichMode = whichChild;
    }

    private Mode showOnly(Mode mode) {
        if (mode.ordinal() < getChildCount() && !this.mWhichMode.equals(mode)) {
            View nextChild = getChildForMode(mode);
            if (!animateOut(this.mWhichMode, mode)) {
                getChildForMode(this.mWhichMode).setVisibility(8);
                nextChild.setVisibility(0);
                animateIn(mode);
            }
        }
        return mode;
    }

    public synchronized Mode getDisplayedMode() {
        return this.mWhichMode;
    }

    public View getCurrentView() {
        return getChildForMode(this.mWhichMode);
    }

    public void registerViewForTranslateAnimationByY(View view, Mode mode) {
        registerViewForTranslateAnimationByY(view, mode, false);
    }

    public void registerViewForTranslateAnimationByY(View view, Mode mode, boolean reverse) {
        int height = view.getHeight();
        if (height == 0) {
            LayoutParams params = view.getLayoutParams();
            if (params.height < 0) {
                view.measure(params.width < 0 ? params.width : -1, params.height);
                height = view.getMeasuredHeight();
            } else {
                height = params.height;
            }
        }
        View[] viewArr = new View[]{view};
        String str = "translationY";
        float[] fArr = new float[2];
        fArr[0] = reverse ? (float) (-height) : (float) height;
        fArr[1] = 0.0f;
        registerViewsForAnimation(viewArr, mode, str, fArr);
    }

    public void registerViewForAnimation(View view, Mode mode, String property, float... values) {
        registerViewsForAnimation(new View[]{view}, mode, property, values);
    }

    public void registerViewsForAnimation(View[] views, Mode mode, String property, float... values) {
        Set<View> set = (Set) this.animationMap.get(mode);
        if (set == null) {
            set = new HashSet();
            this.animationMap.put(mode, set);
        }
        for (View view : views) {
            view.setTag(new AnimationHolder(property, values, null));
            set.add(view);
        }
    }

    public int getBaseline() {
        return getCurrentView() != null ? getCurrentView().getBaseline() : super.getBaseline();
    }

    @TargetApi(14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(MapOverlayAnimator.class.getName());
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(MapOverlayAnimator.class.getName());
    }

    private void animateIn(Mode actualChild) {
        if (VERSION.SDK_INT >= 11) {
            modernAnimateIn(actualChild);
        } else {
            legacyAnimateIn(actualChild);
        }
    }

    private boolean animateOut(Mode previousChild, Mode actualChild) {
        if (VERSION.SDK_INT >= 11) {
            return modernAnimateOut(previousChild, actualChild);
        }
        return legacyAnimateOut(previousChild, actualChild);
    }

    @TargetApi(11)
    private void modernAnimateIn(Mode actualChild) {
        AnimatorSet animSet = new AnimatorSet();
        List<Animator> animators = (List) getInAnimationSet(actualChild, null);
        if (animators != null) {
            animSet.setDuration(250);
            animSet.playTogether(animators);
            animSet.start();
        }
    }

    @TargetApi(11)
    private boolean modernAnimateOut(Mode previousChild, Mode actualChild) {
        AnimatorSet animSet = new AnimatorSet();
        List<Animator> animators = (List) getOutAnimationSet(previousChild, null);
        if (animators == null) {
            return false;
        }
        animSet.addListener(new C13681(previousChild, actualChild));
        animSet.setDuration(250);
        animSet.playTogether(animators);
        animSet.start();
        return true;
    }

    private Collection<Animator> getInAnimationSet(Mode child, Collection<Animator> collection) {
        return getAnimationSet(child, true, collection);
    }

    private Collection<Animator> getOutAnimationSet(Mode child, Collection<Animator> collection) {
        return getAnimationSet(child, false, collection);
    }

    @TargetApi(11)
    private Collection<Animator> getAnimationSet(Mode child, boolean in, Collection<Animator> collection) {
        Set<View> viewSet = (Set) this.animationMap.get(child);
        if (viewSet == null) {
            return null;
        }
        if (collection == null) {
            collection = new ArrayList();
        } else {
            collection.clear();
        }
        for (View view : viewSet) {
            AnimationHolder holder = (AnimationHolder) view.getTag();
            if (holder != null) {
                float f;
                String str = holder.property;
                float[] fArr = new float[2];
                if (in) {
                    f = holder.values[0];
                } else {
                    f = holder.values[1];
                }
                fArr[0] = f;
                if (in) {
                    f = holder.values[1];
                } else {
                    f = holder.values[0];
                }
                fArr[1] = f;
                collection.add(ObjectAnimator.ofFloat(view, str, fArr));
            }
        }
        return collection;
    }

    @TargetApi(8)
    private void legacyAnimateIn(Mode actualChild) {
        com.nineoldandroids.animation.AnimatorSet animSet = new com.nineoldandroids.animation.AnimatorSet();
        List<com.nineoldandroids.animation.Animator> animators = (List) getLegacyInAnimationSet(actualChild, null);
        if (animators != null) {
            animSet.setDuration(250);
            animSet.playTogether(animators);
            animSet.start();
        }
    }

    @TargetApi(8)
    private boolean legacyAnimateOut(Mode previousChild, Mode actualChild) {
        com.nineoldandroids.animation.AnimatorSet animSet = new com.nineoldandroids.animation.AnimatorSet();
        List<com.nineoldandroids.animation.Animator> animators = (List) getLegacyOutAnimationSet(previousChild, null);
        if (animators == null) {
            return false;
        }
        animSet.addListener(new C13692(previousChild, actualChild));
        animSet.setDuration(250);
        animSet.playTogether(animators);
        animSet.start();
        return true;
    }

    private Collection<com.nineoldandroids.animation.Animator> getLegacyInAnimationSet(Mode child, Collection<com.nineoldandroids.animation.Animator> collection) {
        return getLegacyAnimationSet(child, true, collection);
    }

    private Collection<com.nineoldandroids.animation.Animator> getLegacyOutAnimationSet(Mode child, Collection<com.nineoldandroids.animation.Animator> collection) {
        return getLegacyAnimationSet(child, false, collection);
    }

    @TargetApi(8)
    private Collection<com.nineoldandroids.animation.Animator> getLegacyAnimationSet(Mode child, boolean in, Collection<com.nineoldandroids.animation.Animator> collection) {
        Set<View> viewSet = (Set) this.animationMap.get(child);
        if (viewSet == null) {
            return null;
        }
        if (collection == null) {
            collection = new ArrayList();
        } else {
            collection.clear();
        }
        for (View view : viewSet) {
            AnimationHolder holder = (AnimationHolder) view.getTag();
            if (holder != null) {
                float f;
                String str = holder.property;
                float[] fArr = new float[2];
                if (in) {
                    f = holder.values[0];
                } else {
                    f = holder.values[1];
                }
                fArr[0] = f;
                if (in) {
                    f = holder.values[1];
                } else {
                    f = holder.values[0];
                }
                fArr[1] = f;
                collection.add(com.nineoldandroids.animation.ObjectAnimator.ofFloat(view, str, fArr));
            }
        }
        return collection;
    }
}
