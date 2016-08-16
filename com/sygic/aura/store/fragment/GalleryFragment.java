package com.sygic.aura.store.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.views.GalleryViewPager;
import com.sygic.aura.views.PageIndicator;
import uk.co.senab.photoview.PhotoView;

public class GalleryFragment extends AbstractScreenFragment implements BackPressedListener {
    private boolean mAnimationRun;
    private ColorDrawable mContainerBackground;

    /* renamed from: com.sygic.aura.store.fragment.GalleryFragment.1 */
    class C17181 implements OnPreDrawListener {
        final /* synthetic */ ImageView val$imageView;

        C17181(ImageView imageView) {
            this.val$imageView = imageView;
        }

        public boolean onPreDraw() {
            this.val$imageView.getViewTreeObserver().removeOnPreDrawListener(this);
            GalleryFragment.this.zoomImageFromThumb(this.val$imageView);
            return true;
        }
    }

    /* renamed from: com.sygic.aura.store.fragment.GalleryFragment.2 */
    class C17192 extends AnimatorListenerAdapter {
        C17192() {
        }

        public void onAnimationEnd(Animator animation) {
            GalleryFragment.this.performHomeAction();
        }
    }

    private class GalleryPagerAdapter extends PagerAdapter {
        private final LayoutInflater mInflater;
        private final String[] mUrls;

        /* renamed from: com.sygic.aura.store.fragment.GalleryFragment.GalleryPagerAdapter.1 */
        class C17201 implements Callback {
            final /* synthetic */ PhotoView val$imageView;

            C17201(PhotoView photoView) {
                this.val$imageView = photoView;
            }

            public void onSuccess() {
                GalleryFragment.this.runEnterAnimation(this.val$imageView);
            }

            public void onError() {
            }
        }

        public GalleryPagerAdapter(Context context, String[] urls) {
            this.mInflater = LayoutInflater.from(context);
            this.mUrls = urls;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView imageView = (PhotoView) this.mInflater.inflate(2130903146, container, false);
            Picasso.with(container.getContext()).load(this.mUrls[position]).into(imageView, new C17201(imageView));
            container.addView(imageView);
            return imageView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public int getCount() {
            return this.mUrls != null ? this.mUrls.length : 0;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public GalleryFragment() {
        setWantsNavigationData(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasHoneycomb()) {
            ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (hasHoneycomb()) {
            ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(2130903107, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args == null || args.getStringArray("urls") == null) {
            performHomeAction();
            return;
        }
        String[] urls = args.getStringArray("urls");
        int position = args.getInt("position", 0);
        FrameLayout container = (FrameLayout) view.findViewById(2131624111);
        this.mContainerBackground = new ColorDrawable(getResources().getColor(2131558514));
        container.setBackgroundDrawable(this.mContainerBackground);
        if (hasHoneycomb()) {
            this.mContainerBackground.setAlpha(0);
        }
        GalleryPagerAdapter adapter = new GalleryPagerAdapter(getActivity(), urls);
        GalleryViewPager viewPager = (GalleryViewPager) view.findViewById(2131624213);
        viewPager.setPageMargin((int) getResources().getDimension(2131230896));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        ((PageIndicator) view.findViewById(2131624214)).setViewPager(viewPager);
    }

    private boolean hasHoneycomb() {
        return VERSION.SDK_INT >= 11;
    }

    public boolean onBackPressed() {
        runExitAnimation();
        return true;
    }

    private void runEnterAnimation(ImageView imageView) {
        if (this.mAnimationRun || !hasHoneycomb()) {
            imageView.setVisibility(0);
            return;
        }
        this.mAnimationRun = true;
        imageView.getViewTreeObserver().addOnPreDrawListener(new C17181(imageView));
    }

    private void runExitAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(getView(), View.ALPHA, new float[]{1.0f, 0.0f});
        anim.setDuration(150);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.addListener(new C17192());
        anim.start();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zoomImageFromThumb(android.view.View r23) {
        /*
        r22 = this;
        r17 = r22.getArguments();
        r18 = "thumb_rect";
        r11 = r17.getParcelable(r18);
        r11 = (android.graphics.Rect) r11;
        r7 = new android.graphics.Rect;
        r7.<init>();
        r8 = new android.graphics.Point;
        r8.<init>();
        r0 = r23;
        r0.getGlobalVisibleRect(r7, r8);
        r0 = r8.x;
        r17 = r0;
        r0 = r17;
        r0 = -r0;
        r17 = r0;
        r0 = r8.y;
        r18 = r0;
        r0 = r18;
        r0 = -r0;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r11.offset(r0, r1);
        r0 = r8.x;
        r17 = r0;
        r0 = r17;
        r0 = -r0;
        r17 = r0;
        r0 = r8.y;
        r18 = r0;
        r0 = r18;
        r0 = -r0;
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r7.offset(r0, r1);
        r17 = r22.getArguments();
        r18 = "thumb_width";
        r17 = r17.getInt(r18);
        r0 = r17;
        r0 = (float) r0;
        r16 = r0;
        r17 = r22.getArguments();
        r18 = "thumb_height";
        r17 = r17.getInt(r18);
        r0 = r17;
        r15 = (float) r0;
        r17 = r7.width();
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r18 = r7.height();
        r0 = r18;
        r0 = (float) r0;
        r18 = r0;
        r6 = r17 / r18;
        r10 = r16 / r15;
        r17 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
        if (r17 <= 0) goto L_0x01c4;
    L_0x0086:
        r17 = r7.height();
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r13 = r15 / r17;
        r17 = r7.width();
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r14 = r13 * r17;
        r17 = r14 - r16;
        r18 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = r17 / r18;
        r0 = r11.left;
        r17 = r0;
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r17 = r17 - r5;
        r0 = r17;
        r0 = (int) r0;
        r17 = r0;
        r0 = r17;
        r11.left = r0;
        r0 = r11.right;
        r17 = r0;
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r17 = r17 + r5;
        r0 = r17;
        r0 = (int) r0;
        r17 = r0;
        r0 = r17;
        r11.right = r0;
    L_0x00ca:
        r17 = 0;
        r0 = r23;
        r1 = r17;
        r0.setVisibility(r1);
        r17 = 0;
        r0 = r23;
        r1 = r17;
        r0.setPivotX(r1);
        r17 = 0;
        r0 = r23;
        r1 = r17;
        r0.setPivotY(r1);
        r9 = new android.animation.AnimatorSet;
        r9.<init>();
        r17 = android.view.View.X;
        r18 = 2;
        r0 = r18;
        r0 = new float[r0];
        r18 = r0;
        r19 = 0;
        r0 = r11.left;
        r20 = r0;
        r0 = r20;
        r0 = (float) r0;
        r20 = r0;
        r18[r19] = r20;
        r19 = 1;
        r0 = r7.left;
        r20 = r0;
        r0 = r20;
        r0 = (float) r0;
        r20 = r0;
        r18[r19] = r20;
        r0 = r23;
        r1 = r17;
        r2 = r18;
        r17 = android.animation.ObjectAnimator.ofFloat(r0, r1, r2);
        r0 = r17;
        r17 = r9.play(r0);
        r18 = android.view.View.Y;
        r19 = 2;
        r0 = r19;
        r0 = new float[r0];
        r19 = r0;
        r20 = 0;
        r0 = r11.top;
        r21 = r0;
        r0 = r21;
        r0 = (float) r0;
        r21 = r0;
        r19[r20] = r21;
        r20 = 1;
        r0 = r7.top;
        r21 = r0;
        r0 = r21;
        r0 = (float) r0;
        r21 = r0;
        r19[r20] = r21;
        r0 = r23;
        r1 = r18;
        r2 = r19;
        r18 = android.animation.ObjectAnimator.ofFloat(r0, r1, r2);
        r17 = r17.with(r18);
        r18 = android.view.View.SCALE_X;
        r19 = 2;
        r0 = r19;
        r0 = new float[r0];
        r19 = r0;
        r20 = 0;
        r19[r20] = r13;
        r20 = 1;
        r21 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r19[r20] = r21;
        r0 = r23;
        r1 = r18;
        r2 = r19;
        r18 = android.animation.ObjectAnimator.ofFloat(r0, r1, r2);
        r17 = r17.with(r18);
        r18 = android.view.View.SCALE_Y;
        r19 = 2;
        r0 = r19;
        r0 = new float[r0];
        r19 = r0;
        r20 = 0;
        r19[r20] = r13;
        r20 = 1;
        r21 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r19[r20] = r21;
        r0 = r23;
        r1 = r18;
        r2 = r19;
        r18 = android.animation.ObjectAnimator.ofFloat(r0, r1, r2);
        r17 = r17.with(r18);
        r0 = r22;
        r0 = r0.mContainerBackground;
        r18 = r0;
        r19 = "alpha";
        r20 = 2;
        r0 = r20;
        r0 = new int[r0];
        r20 = r0;
        r20 = {0, 255};
        r18 = android.animation.ObjectAnimator.ofInt(r18, r19, r20);
        r17.with(r18);
        r18 = 150; // 0x96 float:2.1E-43 double:7.4E-322;
        r0 = r18;
        r9.setDuration(r0);
        r17 = new android.view.animation.DecelerateInterpolator;
        r17.<init>();
        r0 = r17;
        r9.setInterpolator(r0);
        r9.start();
        return;
    L_0x01c4:
        r17 = r7.width();
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r13 = r16 / r17;
        r17 = r7.height();
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r12 = r13 * r17;
        r17 = r12 - r15;
        r18 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r4 = r17 / r18;
        r0 = r11.top;
        r17 = r0;
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r17 = r17 - r4;
        r0 = r17;
        r0 = (int) r0;
        r17 = r0;
        r0 = r17;
        r11.top = r0;
        r0 = r11.bottom;
        r17 = r0;
        r0 = r17;
        r0 = (float) r0;
        r17 = r0;
        r17 = r17 + r4;
        r0 = r17;
        r0 = (int) r0;
        r17 = r0;
        r0 = r17;
        r11.bottom = r0;
        goto L_0x00ca;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.store.fragment.GalleryFragment.zoomImageFromThumb(android.view.View):void");
    }
}
