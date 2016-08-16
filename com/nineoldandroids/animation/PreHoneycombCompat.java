package com.nineoldandroids.animation;

import android.view.View;
import com.nineoldandroids.util.FloatProperty;
import com.nineoldandroids.util.IntProperty;
import com.nineoldandroids.util.Property;
import com.nineoldandroids.view.animation.AnimatorProxy;

final class PreHoneycombCompat {
    static Property<View, Float> ALPHA;
    static Property<View, Float> PIVOT_X;
    static Property<View, Float> PIVOT_Y;
    static Property<View, Float> ROTATION;
    static Property<View, Float> ROTATION_X;
    static Property<View, Float> ROTATION_Y;
    static Property<View, Float> SCALE_X;
    static Property<View, Float> SCALE_Y;
    static Property<View, Integer> SCROLL_X;
    static Property<View, Integer> SCROLL_Y;
    static Property<View, Float> TRANSLATION_X;
    static Property<View, Float> TRANSLATION_Y;
    static Property<View, Float> f1250X;
    static Property<View, Float> f1251Y;

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.10 */
    static class AnonymousClass10 extends FloatProperty<View> {
        AnonymousClass10(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleY());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.11 */
    static class AnonymousClass11 extends IntProperty<View> {
        AnonymousClass11(String x0) {
            super(x0);
        }

        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollX());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.12 */
    static class AnonymousClass12 extends IntProperty<View> {
        AnonymousClass12(String x0) {
            super(x0);
        }

        public Integer get(View object) {
            return Integer.valueOf(AnimatorProxy.wrap(object).getScrollY());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.13 */
    static class AnonymousClass13 extends FloatProperty<View> {
        AnonymousClass13(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getX());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.14 */
    static class AnonymousClass14 extends FloatProperty<View> {
        AnonymousClass14(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getY());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.1 */
    static class C10581 extends FloatProperty<View> {
        C10581(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setAlpha(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getAlpha());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.2 */
    static class C10592 extends FloatProperty<View> {
        C10592(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotX());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.3 */
    static class C10603 extends FloatProperty<View> {
        C10603(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setPivotY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getPivotY());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.4 */
    static class C10614 extends FloatProperty<View> {
        C10614(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationX());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.5 */
    static class C10625 extends FloatProperty<View> {
        C10625(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setTranslationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getTranslationY());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.6 */
    static class C10636 extends FloatProperty<View> {
        C10636(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotation(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotation());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.7 */
    static class C10647 extends FloatProperty<View> {
        C10647(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationX());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.8 */
    static class C10658 extends FloatProperty<View> {
        C10658(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setRotationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getRotationY());
        }
    }

    /* renamed from: com.nineoldandroids.animation.PreHoneycombCompat.9 */
    static class C10669 extends FloatProperty<View> {
        C10669(String x0) {
            super(x0);
        }

        public void setValue(View object, float value) {
            AnimatorProxy.wrap(object).setScaleX(value);
        }

        public Float get(View object) {
            return Float.valueOf(AnimatorProxy.wrap(object).getScaleX());
        }
    }

    static {
        ALPHA = new C10581("alpha");
        PIVOT_X = new C10592("pivotX");
        PIVOT_Y = new C10603("pivotY");
        TRANSLATION_X = new C10614("translationX");
        TRANSLATION_Y = new C10625("translationY");
        ROTATION = new C10636("rotation");
        ROTATION_X = new C10647("rotationX");
        ROTATION_Y = new C10658("rotationY");
        SCALE_X = new C10669("scaleX");
        SCALE_Y = new AnonymousClass10("scaleY");
        SCROLL_X = new AnonymousClass11("scrollX");
        SCROLL_Y = new AnonymousClass12("scrollY");
        f1250X = new AnonymousClass13("x");
        f1251Y = new AnonymousClass14("y");
    }
}
