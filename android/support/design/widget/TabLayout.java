package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.design.C0001R;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.internal.widget.TintManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import loquendo.tts.engine.TTSConst;

public class TabLayout extends HorizontalScrollView {
    private int mContentInsetStart;
    private ValueAnimatorCompat mIndicatorAnimator;
    private int mMode;
    private OnTabSelectedListener mOnTabSelectedListener;
    private final int mRequestedTabMaxWidth;
    private ValueAnimatorCompat mScrollAnimator;
    private Tab mSelectedTab;
    private final int mTabBackgroundResId;
    private OnClickListener mTabClickListener;
    private int mTabGravity;
    private int mTabMaxWidth;
    private final int mTabMinWidth;
    private int mTabPaddingBottom;
    private int mTabPaddingEnd;
    private int mTabPaddingStart;
    private int mTabPaddingTop;
    private final SlidingTabStrip mTabStrip;
    private int mTabTextAppearance;
    private ColorStateList mTabTextColors;
    private final ArrayList<Tab> mTabs;

    /* renamed from: android.support.design.widget.TabLayout.1 */
    class C00241 implements OnClickListener {
        C00241() {
        }

        public void onClick(View view) {
            ((TabView) view).getTab().select();
        }
    }

    /* renamed from: android.support.design.widget.TabLayout.2 */
    class C00252 implements AnimatorUpdateListener {
        C00252() {
        }

        public void onAnimationUpdate(ValueAnimatorCompat animator) {
            TabLayout.this.scrollTo(animator.getAnimatedIntValue(), 0);
        }
    }

    public interface OnTabSelectedListener {
        void onTabReselected(Tab tab);

        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);
    }

    private class SlidingTabStrip extends LinearLayout {
        private int mIndicatorLeft;
        private int mIndicatorRight;
        private int mSelectedIndicatorHeight;
        private final Paint mSelectedIndicatorPaint;
        private int mSelectedPosition;
        private float mSelectionOffset;

        /* renamed from: android.support.design.widget.TabLayout.SlidingTabStrip.1 */
        class C00261 implements AnimatorUpdateListener {
            final /* synthetic */ int val$startLeft;
            final /* synthetic */ int val$startRight;
            final /* synthetic */ int val$targetLeft;
            final /* synthetic */ int val$targetRight;

            C00261(int i, int i2, int i3, int i4) {
                this.val$startLeft = i;
                this.val$targetLeft = i2;
                this.val$startRight = i3;
                this.val$targetRight = i4;
            }

            public void onAnimationUpdate(ValueAnimatorCompat animator) {
                float fraction = animator.getAnimatedFraction();
                SlidingTabStrip.this.setIndicatorPosition(AnimationUtils.lerp(this.val$startLeft, this.val$targetLeft, fraction), AnimationUtils.lerp(this.val$startRight, this.val$targetRight, fraction));
            }
        }

        /* renamed from: android.support.design.widget.TabLayout.SlidingTabStrip.2 */
        class C00272 extends AnimatorListenerAdapter {
            final /* synthetic */ int val$position;

            C00272(int i) {
                this.val$position = i;
            }

            public void onAnimationEnd(ValueAnimatorCompat animator) {
                SlidingTabStrip.this.mSelectedPosition = this.val$position;
                SlidingTabStrip.this.mSelectionOffset = 0.0f;
            }

            public void onAnimationCancel(ValueAnimatorCompat animator) {
                SlidingTabStrip.this.mSelectedPosition = this.val$position;
                SlidingTabStrip.this.mSelectionOffset = 0.0f;
            }
        }

        SlidingTabStrip(Context context) {
            super(context);
            this.mSelectedPosition = -1;
            this.mIndicatorLeft = -1;
            this.mIndicatorRight = -1;
            setWillNotDraw(false);
            this.mSelectedIndicatorPaint = new Paint();
        }

        void setSelectedIndicatorColor(int color) {
            this.mSelectedIndicatorPaint.setColor(color);
            ViewCompat.postInvalidateOnAnimation(this);
        }

        void setSelectedIndicatorHeight(int height) {
            this.mSelectedIndicatorHeight = height;
            ViewCompat.postInvalidateOnAnimation(this);
        }

        boolean childrenNeedLayout() {
            int z = getChildCount();
            for (int i = 0; i < z; i++) {
                if (getChildAt(i).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
            if (TabLayout.this.mIndicatorAnimator == null || !TabLayout.this.mIndicatorAnimator.isRunning()) {
                this.mSelectedPosition = position;
                this.mSelectionOffset = positionOffset;
                updateIndicatorPosition();
            }
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (MeasureSpec.getMode(widthMeasureSpec) == 1073741824 && TabLayout.this.mMode == 1 && TabLayout.this.mTabGravity == 1) {
                int i;
                int count = getChildCount();
                int unspecifiedSpec = MeasureSpec.makeMeasureSpec(0, 0);
                int largestTabWidth = 0;
                int z = count;
                for (i = 0; i < z; i++) {
                    View child = getChildAt(i);
                    child.measure(unspecifiedSpec, heightMeasureSpec);
                    largestTabWidth = Math.max(largestTabWidth, child.getMeasuredWidth());
                }
                if (largestTabWidth > 0) {
                    if (largestTabWidth * count <= getMeasuredWidth() - (TabLayout.this.dpToPx(16) * 2)) {
                        for (i = 0; i < count; i++) {
                            LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                            lp.width = largestTabWidth;
                            lp.weight = 0.0f;
                        }
                    } else {
                        TabLayout.this.mTabGravity = 0;
                        TabLayout.this.updateTabViewsLayoutParams();
                    }
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            updateIndicatorPosition();
        }

        private void updateIndicatorPosition() {
            int right;
            int left;
            View selectedTitle = getChildAt(this.mSelectedPosition);
            if (selectedTitle == null || selectedTitle.getWidth() <= 0) {
                right = -1;
                left = -1;
            } else {
                left = selectedTitle.getLeft();
                right = selectedTitle.getRight();
                if (this.mSelectionOffset > 0.0f && this.mSelectedPosition < getChildCount() - 1) {
                    View nextTitle = getChildAt(this.mSelectedPosition + 1);
                    left = (int) ((this.mSelectionOffset * ((float) nextTitle.getLeft())) + ((1.0f - this.mSelectionOffset) * ((float) left)));
                    right = (int) ((this.mSelectionOffset * ((float) nextTitle.getRight())) + ((1.0f - this.mSelectionOffset) * ((float) right)));
                }
            }
            setIndicatorPosition(left, right);
        }

        private void setIndicatorPosition(int left, int right) {
            if (left != this.mIndicatorLeft || right != this.mIndicatorRight) {
                this.mIndicatorLeft = left;
                this.mIndicatorRight = right;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        void animateIndicatorToPosition(int position, int duration) {
            int startLeft;
            int startRight;
            boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
            View targetView = getChildAt(position);
            int targetLeft = targetView.getLeft();
            int targetRight = targetView.getRight();
            if (Math.abs(position - this.mSelectedPosition) <= 1) {
                startLeft = this.mIndicatorLeft;
                startRight = this.mIndicatorRight;
            } else {
                int offset = TabLayout.this.dpToPx(24);
                if (position < this.mSelectedPosition) {
                    if (isRtl) {
                        startRight = targetLeft - offset;
                        startLeft = startRight;
                    } else {
                        startRight = targetRight + offset;
                        startLeft = startRight;
                    }
                } else if (isRtl) {
                    startRight = targetRight + offset;
                    startLeft = startRight;
                } else {
                    startRight = targetLeft - offset;
                    startLeft = startRight;
                }
            }
            if (startLeft != targetLeft || startRight != targetRight) {
                ValueAnimatorCompat animator = TabLayout.this.mIndicatorAnimator = ViewUtils.createAnimator();
                animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                animator.setDuration(duration);
                animator.setFloatValues(0.0f, 1.0f);
                animator.setUpdateListener(new C00261(startLeft, targetLeft, startRight, targetRight));
                animator.setListener(new C00272(position));
                animator.start();
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
                canvas.drawRect((float) this.mIndicatorLeft, (float) (getHeight() - this.mSelectedIndicatorHeight), (float) this.mIndicatorRight, (float) getHeight(), this.mSelectedIndicatorPaint);
            }
        }
    }

    public static final class Tab {
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private final TabLayout mParent;
        private int mPosition;
        private CharSequence mText;

        Tab(TabLayout parent) {
            this.mPosition = -1;
            this.mParent = parent;
        }

        View getCustomView() {
            return this.mCustomView;
        }

        public Drawable getIcon() {
            return this.mIcon;
        }

        public int getPosition() {
            return this.mPosition;
        }

        void setPosition(int position) {
            this.mPosition = position;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public Tab setIcon(Drawable icon) {
            this.mIcon = icon;
            if (this.mPosition >= 0) {
                this.mParent.updateTab(this.mPosition);
            }
            return this;
        }

        public Tab setIcon(int resId) {
            return setIcon(TintManager.getDrawable(this.mParent.getContext(), resId));
        }

        public Tab setText(CharSequence text) {
            this.mText = text;
            if (this.mPosition >= 0) {
                this.mParent.updateTab(this.mPosition);
            }
            return this;
        }

        public void select() {
            this.mParent.selectTab(this);
        }

        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }
    }

    public static class TabLayoutOnPageChangeListener implements OnPageChangeListener {
        private int mPreviousScrollState;
        private int mScrollState;
        private final WeakReference<TabLayout> mTabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.mTabLayoutRef = new WeakReference(tabLayout);
        }

        public void onPageScrollStateChanged(int state) {
            this.mPreviousScrollState = this.mScrollState;
            this.mScrollState = state;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            boolean updateText = true;
            TabLayout tabLayout = (TabLayout) this.mTabLayoutRef.get();
            if (tabLayout != null) {
                if (!(this.mScrollState == 1 || (this.mScrollState == 2 && this.mPreviousScrollState == 1))) {
                    updateText = false;
                }
                tabLayout.setScrollPosition(position, positionOffset, updateText);
            }
        }

        public void onPageSelected(int position) {
            TabLayout tabLayout = (TabLayout) this.mTabLayoutRef.get();
            if (tabLayout != null) {
                tabLayout.selectTab(tabLayout.getTabAt(position), this.mScrollState == 0);
            }
        }
    }

    class TabView extends LinearLayout implements OnLongClickListener {
        private ImageView mCustomIconView;
        private TextView mCustomTextView;
        private View mCustomView;
        private ImageView mIconView;
        private final Tab mTab;
        private TextView mTextView;

        public TabView(Context context, Tab tab) {
            super(context);
            this.mTab = tab;
            if (TabLayout.this.mTabBackgroundResId != 0) {
                setBackgroundDrawable(TintManager.getDrawable(context, TabLayout.this.mTabBackgroundResId));
            }
            ViewCompat.setPaddingRelative(this, TabLayout.this.mTabPaddingStart, TabLayout.this.mTabPaddingTop, TabLayout.this.mTabPaddingEnd, TabLayout.this.mTabPaddingBottom);
            setGravity(17);
            update();
        }

        public void setSelected(boolean selected) {
            boolean changed = isSelected() != selected;
            super.setSelected(selected);
            if (changed && selected) {
                sendAccessibilityEvent(4);
                if (this.mTextView != null) {
                    this.mTextView.setSelected(selected);
                }
                if (this.mIconView != null) {
                    this.mIconView.setSelected(selected);
                }
            }
        }

        @TargetApi(14)
        public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(android.support.v7.app.ActionBar.Tab.class.getName());
        }

        @TargetApi(14)
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(android.support.v7.app.ActionBar.Tab.class.getName());
        }

        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int measuredWidth = getMeasuredWidth();
            if (measuredWidth < TabLayout.this.mTabMinWidth || measuredWidth > TabLayout.this.mTabMaxWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(MathUtils.constrain(measuredWidth, TabLayout.this.mTabMinWidth, TabLayout.this.mTabMaxWidth), 1073741824), heightMeasureSpec);
            }
        }

        final void update() {
            Tab tab = this.mTab;
            View custom = tab.getCustomView();
            if (custom != null) {
                TabView customParent = custom.getParent();
                if (customParent != this) {
                    if (customParent != null) {
                        customParent.removeView(custom);
                    }
                    addView(custom);
                }
                this.mCustomView = custom;
                if (this.mTextView != null) {
                    this.mTextView.setVisibility(8);
                }
                if (this.mIconView != null) {
                    this.mIconView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
                this.mCustomTextView = (TextView) custom.findViewById(16908308);
                this.mCustomIconView = (ImageView) custom.findViewById(16908294);
            } else {
                if (this.mCustomView != null) {
                    removeView(this.mCustomView);
                    this.mCustomView = null;
                }
                this.mCustomTextView = null;
                this.mCustomIconView = null;
            }
            if (this.mCustomView == null) {
                if (this.mIconView == null) {
                    ImageView iconView = (ImageView) LayoutInflater.from(getContext()).inflate(C0001R.layout.layout_tab_icon, this, false);
                    addView(iconView, 0);
                    this.mIconView = iconView;
                }
                if (this.mTextView == null) {
                    TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(C0001R.layout.layout_tab_text, this, false);
                    addView(textView);
                    this.mTextView = textView;
                }
                this.mTextView.setTextAppearance(getContext(), TabLayout.this.mTabTextAppearance);
                if (TabLayout.this.mTabTextColors != null) {
                    this.mTextView.setTextColor(TabLayout.this.mTabTextColors);
                }
                updateTextAndIcon(tab, this.mTextView, this.mIconView);
            } else if (this.mCustomTextView != null || this.mCustomIconView != null) {
                updateTextAndIcon(tab, this.mCustomTextView, this.mCustomIconView);
            }
        }

        private void updateTextAndIcon(Tab tab, TextView textView, ImageView iconView) {
            boolean hasText;
            Drawable icon = tab.getIcon();
            CharSequence text = tab.getText();
            if (iconView != null) {
                if (icon != null) {
                    iconView.setImageDrawable(icon);
                    iconView.setVisibility(0);
                    setVisibility(0);
                } else {
                    iconView.setVisibility(8);
                    iconView.setImageDrawable(null);
                }
                iconView.setContentDescription(tab.getContentDescription());
            }
            if (TextUtils.isEmpty(text)) {
                hasText = false;
            } else {
                hasText = true;
            }
            if (textView != null) {
                if (hasText) {
                    textView.setText(text);
                    textView.setContentDescription(tab.getContentDescription());
                    textView.setVisibility(0);
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText(null);
                }
            }
            if (hasText || TextUtils.isEmpty(tab.getContentDescription())) {
                setOnLongClickListener(null);
                setLongClickable(false);
                return;
            }
            setOnLongClickListener(this);
        }

        public boolean onLongClick(View v) {
            int[] screenPos = new int[2];
            getLocationOnScreen(screenPos);
            Context context = getContext();
            int width = getWidth();
            int height = getHeight();
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, this.mTab.getContentDescription(), 0);
            cheatSheet.setGravity(49, (screenPos[0] + (width / 2)) - (screenWidth / 2), height);
            cheatSheet.show();
            return true;
        }

        public Tab getTab() {
            return this.mTab;
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.mViewPager = viewPager;
        }

        public void onTabSelected(Tab tab) {
            this.mViewPager.setCurrentItem(tab.getPosition());
        }

        public void onTabUnselected(Tab tab) {
        }

        public void onTabReselected(Tab tab) {
        }
    }

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTabs = new ArrayList();
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        this.mTabStrip = new SlidingTabStrip(context);
        addView(this.mTabStrip, -2, -1);
        TypedArray a = context.obtainStyledAttributes(attrs, C0001R.styleable.TabLayout, defStyleAttr, C0001R.style.Widget_Design_TabLayout);
        this.mTabStrip.setSelectedIndicatorHeight(a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabIndicatorHeight, 0));
        this.mTabStrip.setSelectedIndicatorColor(a.getColor(C0001R.styleable.TabLayout_tabIndicatorColor, 0));
        this.mTabTextAppearance = a.getResourceId(C0001R.styleable.TabLayout_tabTextAppearance, C0001R.style.TextAppearance_Design_Tab);
        int dimensionPixelSize = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPadding, 0);
        this.mTabPaddingBottom = dimensionPixelSize;
        this.mTabPaddingEnd = dimensionPixelSize;
        this.mTabPaddingTop = dimensionPixelSize;
        this.mTabPaddingStart = dimensionPixelSize;
        this.mTabPaddingStart = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPaddingStart, this.mTabPaddingStart);
        this.mTabPaddingTop = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPaddingTop, this.mTabPaddingTop);
        this.mTabPaddingEnd = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPaddingEnd, this.mTabPaddingEnd);
        this.mTabPaddingBottom = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabPaddingBottom, this.mTabPaddingBottom);
        this.mTabTextColors = loadTextColorFromTextAppearance(this.mTabTextAppearance);
        if (a.hasValue(C0001R.styleable.TabLayout_tabTextColor)) {
            this.mTabTextColors = a.getColorStateList(C0001R.styleable.TabLayout_tabTextColor);
        }
        if (a.hasValue(C0001R.styleable.TabLayout_tabSelectedTextColor)) {
            this.mTabTextColors = createColorStateList(this.mTabTextColors.getDefaultColor(), a.getColor(C0001R.styleable.TabLayout_tabSelectedTextColor, 0));
        }
        this.mTabMinWidth = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabMinWidth, 0);
        this.mRequestedTabMaxWidth = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabMaxWidth, 0);
        this.mTabBackgroundResId = a.getResourceId(C0001R.styleable.TabLayout_tabBackground, 0);
        this.mContentInsetStart = a.getDimensionPixelSize(C0001R.styleable.TabLayout_tabContentStart, 0);
        this.mMode = a.getInt(C0001R.styleable.TabLayout_tabMode, 1);
        this.mTabGravity = a.getInt(C0001R.styleable.TabLayout_tabGravity, 0);
        a.recycle();
        applyModeAndGravity();
    }

    public void setScrollPosition(int position, float positionOffset, boolean updateSelectedText) {
        if ((this.mIndicatorAnimator == null || !this.mIndicatorAnimator.isRunning()) && position >= 0 && position < this.mTabStrip.getChildCount()) {
            this.mTabStrip.setIndicatorPositionFromTabPosition(position, positionOffset);
            scrollTo(calculateScrollXForTab(position, positionOffset), 0);
            if (updateSelectedText) {
                setSelectedTabView(Math.round(((float) position) + positionOffset));
            }
        }
    }

    public void addTab(Tab tab) {
        addTab(tab, this.mTabs.isEmpty());
    }

    public void addTab(Tab tab, boolean setSelected) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        addTabView(tab, setSelected);
        configureTab(tab, this.mTabs.size());
        if (setSelected) {
            tab.select();
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mOnTabSelectedListener = onTabSelectedListener;
    }

    public Tab newTab() {
        return new Tab(this);
    }

    public int getTabCount() {
        return this.mTabs.size();
    }

    public Tab getTabAt(int index) {
        return (Tab) this.mTabs.get(index);
    }

    public int getSelectedTabPosition() {
        return this.mSelectedTab != null ? this.mSelectedTab.getPosition() : -1;
    }

    public void removeAllTabs() {
        this.mTabStrip.removeAllViews();
        Iterator<Tab> i = this.mTabs.iterator();
        while (i.hasNext()) {
            ((Tab) i.next()).setPosition(-1);
            i.remove();
        }
        this.mSelectedTab = null;
    }

    public void setTabMode(int mode) {
        if (mode != this.mMode) {
            this.mMode = mode;
            applyModeAndGravity();
        }
    }

    public int getTabMode() {
        return this.mMode;
    }

    public void setTabGravity(int gravity) {
        if (this.mTabGravity != gravity) {
            this.mTabGravity = gravity;
            applyModeAndGravity();
        }
    }

    public int getTabGravity() {
        return this.mTabGravity;
    }

    public void setTabTextColors(ColorStateList textColor) {
        if (this.mTabTextColors != textColor) {
            this.mTabTextColors = textColor;
            updateAllTabs();
        }
    }

    public ColorStateList getTabTextColors() {
        return this.mTabTextColors;
    }

    public void setupWithViewPager(ViewPager viewPager) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }
        setTabsFromPagerAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this));
        setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
        if (this.mSelectedTab == null || this.mSelectedTab.getPosition() != viewPager.getCurrentItem()) {
            getTabAt(viewPager.getCurrentItem()).select();
        }
    }

    public void setTabsFromPagerAdapter(PagerAdapter adapter) {
        removeAllTabs();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            addTab(newTab().setText(adapter.getPageTitle(i)));
        }
    }

    private void updateAllTabs() {
        int z = this.mTabStrip.getChildCount();
        for (int i = 0; i < z; i++) {
            updateTab(i);
        }
    }

    private TabView createTabView(Tab tab) {
        TabView tabView = new TabView(getContext(), tab);
        tabView.setFocusable(true);
        if (this.mTabClickListener == null) {
            this.mTabClickListener = new C00241();
        }
        tabView.setOnClickListener(this.mTabClickListener);
        return tabView;
    }

    private void configureTab(Tab tab, int position) {
        tab.setPosition(position);
        this.mTabs.add(position, tab);
        int count = this.mTabs.size();
        for (int i = position + 1; i < count; i++) {
            ((Tab) this.mTabs.get(i)).setPosition(i);
        }
    }

    private void updateTab(int position) {
        TabView view = (TabView) this.mTabStrip.getChildAt(position);
        if (view != null) {
            view.update();
        }
    }

    private void addTabView(Tab tab, boolean setSelected) {
        TabView tabView = createTabView(tab);
        this.mTabStrip.addView(tabView, createLayoutParamsForTabs());
        if (setSelected) {
            tabView.setSelected(true);
        }
    }

    private LayoutParams createLayoutParamsForTabs() {
        LayoutParams lp = new LayoutParams(-2, -1);
        updateTabViewLayoutParams(lp);
        return lp;
    }

    private void updateTabViewLayoutParams(LayoutParams lp) {
        if (this.mMode == 1 && this.mTabGravity == 0) {
            lp.width = 0;
            lp.weight = 1.0f;
            return;
        }
        lp.width = -2;
        lp.weight = 0.0f;
    }

    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * ((float) dps));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int idealHeight = (dpToPx(48) + getPaddingTop()) + getPaddingBottom();
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case Integer.MIN_VALUE:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(idealHeight, MeasureSpec.getSize(heightMeasureSpec)), 1073741824);
                break;
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(idealHeight, 1073741824);
                break;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mMode == 1 && getChildCount() == 1) {
            View child = getChildAt(0);
            int width = getMeasuredWidth();
            if (child.getMeasuredWidth() > width) {
                child.measure(MeasureSpec.makeMeasureSpec(width, 1073741824), getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), child.getLayoutParams().height));
            }
        }
        int maxTabWidth = this.mRequestedTabMaxWidth;
        int defaultTabMaxWidth = getMeasuredWidth() - dpToPx(56);
        if (maxTabWidth == 0 || maxTabWidth > defaultTabMaxWidth) {
            maxTabWidth = defaultTabMaxWidth;
        }
        this.mTabMaxWidth = maxTabWidth;
    }

    private void animateToTab(int newPosition) {
        if (newPosition != -1) {
            if (getWindowToken() == null || !ViewCompat.isLaidOut(this) || this.mTabStrip.childrenNeedLayout()) {
                setScrollPosition(newPosition, 0.0f, true);
                return;
            }
            int startScrollX = getScrollX();
            int targetScrollX = calculateScrollXForTab(newPosition, 0.0f);
            if (startScrollX != targetScrollX) {
                if (this.mScrollAnimator == null) {
                    this.mScrollAnimator = ViewUtils.createAnimator();
                    this.mScrollAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                    this.mScrollAnimator.setDuration(300);
                    this.mScrollAnimator.setUpdateListener(new C00252());
                }
                this.mScrollAnimator.setIntValues(startScrollX, targetScrollX);
                this.mScrollAnimator.start();
            }
            this.mTabStrip.animateIndicatorToPosition(newPosition, 300);
        }
    }

    private void setSelectedTabView(int position) {
        int tabCount = this.mTabStrip.getChildCount();
        int i = 0;
        while (i < tabCount) {
            this.mTabStrip.getChildAt(i).setSelected(i == position);
            i++;
        }
    }

    void selectTab(Tab tab) {
        selectTab(tab, true);
    }

    void selectTab(Tab tab, boolean updateIndicator) {
        if (this.mSelectedTab != tab) {
            int newPosition;
            if (tab != null) {
                newPosition = tab.getPosition();
            } else {
                newPosition = -1;
            }
            setSelectedTabView(newPosition);
            if (updateIndicator) {
                if ((this.mSelectedTab == null || this.mSelectedTab.getPosition() == -1) && newPosition != -1) {
                    setScrollPosition(newPosition, 0.0f, true);
                } else {
                    animateToTab(newPosition);
                }
            }
            if (!(this.mSelectedTab == null || this.mOnTabSelectedListener == null)) {
                this.mOnTabSelectedListener.onTabUnselected(this.mSelectedTab);
            }
            this.mSelectedTab = tab;
            if (this.mSelectedTab != null && this.mOnTabSelectedListener != null) {
                this.mOnTabSelectedListener.onTabSelected(this.mSelectedTab);
            }
        } else if (this.mSelectedTab != null) {
            if (this.mOnTabSelectedListener != null) {
                this.mOnTabSelectedListener.onTabReselected(this.mSelectedTab);
            }
            animateToTab(tab.getPosition());
        }
    }

    private int calculateScrollXForTab(int position, float positionOffset) {
        int nextWidth = 0;
        if (this.mMode != 0) {
            return 0;
        }
        int selectedWidth;
        View selectedChild = this.mTabStrip.getChildAt(position);
        View nextChild = position + 1 < this.mTabStrip.getChildCount() ? this.mTabStrip.getChildAt(position + 1) : null;
        if (selectedChild != null) {
            selectedWidth = selectedChild.getWidth();
        } else {
            selectedWidth = 0;
        }
        if (nextChild != null) {
            nextWidth = nextChild.getWidth();
        }
        return ((selectedChild.getLeft() + ((int) ((((float) (selectedWidth + nextWidth)) * positionOffset) * 0.5f))) + (selectedChild.getWidth() / 2)) - (getWidth() / 2);
    }

    private void applyModeAndGravity() {
        int paddingStart = 0;
        if (this.mMode == 0) {
            paddingStart = Math.max(0, this.mContentInsetStart - this.mTabPaddingStart);
        }
        ViewCompat.setPaddingRelative(this.mTabStrip, paddingStart, 0, 0, 0);
        switch (this.mMode) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.mTabStrip.setGravity(8388611);
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                this.mTabStrip.setGravity(1);
                break;
        }
        updateTabViewsLayoutParams();
    }

    private void updateTabViewsLayoutParams() {
        for (int i = 0; i < this.mTabStrip.getChildCount(); i++) {
            View child = this.mTabStrip.getChildAt(i);
            updateTabViewLayoutParams((LayoutParams) child.getLayoutParams());
            child.requestLayout();
        }
    }

    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        states = new int[2][];
        int[] colors = new int[]{SELECTED_STATE_SET, selectedColor};
        int i = 0 + 1;
        states[i] = EMPTY_STATE_SET;
        colors[i] = defaultColor;
        i++;
        return new ColorStateList(states, colors);
    }

    private ColorStateList loadTextColorFromTextAppearance(int textAppearanceResId) {
        TypedArray a = getContext().obtainStyledAttributes(textAppearanceResId, C0001R.styleable.TextAppearance);
        try {
            ColorStateList colorStateList = a.getColorStateList(C0001R.styleable.TextAppearance_android_textColor);
            return colorStateList;
        } finally {
            a.recycle();
        }
    }
}
