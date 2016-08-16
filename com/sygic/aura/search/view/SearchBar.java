package com.sygic.aura.search.view;

import android.annotation.TargetApi;
import android.app.UiModeManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.HorizontalScrollView;
import com.sygic.aura.SygicMain;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.Typefaces;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.view.ExtendedEditText;
import java.util.Arrays;
import java.util.List;

public class SearchBar extends ExtendedEditText implements AnimationListener {
    private static final List<Character> mBubbleSearchDelimiters;
    private final InputFilter[] NO_INPUT_FILTER;
    private final InputFilter[] STANDARD_INPUT_FILTER;
    private boolean mBoolAnimationInRun;
    private final String[] mBubbleHints;
    private int mDefaultWidth;
    private final GestureDetector mDoubleTapDetector;
    private LayoutParams mParams;
    private PopupMenu mPopupMenu;
    private HorizontalScrollView mScrollView;
    private SearchFrame mSearchFrame;

    /* renamed from: com.sygic.aura.search.view.SearchBar.1 */
    class C15911 implements InputFilter {
        C15911() {
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.length() > 0) {
                if (SearchBar.mBubbleSearchDelimiters.contains(Character.valueOf(source.charAt(end - 1)))) {
                    if (SearchBar.this.mSearchFrame.hasBubbleInRemoval()) {
                        SearchBar.this.mSearchFrame.shiftToNextSubtype();
                    }
                    SearchBar.this.mSearchFrame.setBubbleViewStyle(0);
                    SearchBar.this.mSearchFrame.onDelimiterPressed("");
                } else if (SearchBar.this.mSearchFrame.hasBubbleInRemoval()) {
                    SearchBar.this.mSearchFrame.makeBubbleInvisible();
                    return null;
                }
            }
            return "";
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchBar.2 */
    class C15922 implements InputFilter {
        C15922() {
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.length() <= 0 || !SearchBar.mBubbleSearchDelimiters.contains(Character.valueOf(source.charAt(end - 1)))) {
                return null;
            }
            if (SearchBar.this.mSearchFrame.hasBubbleInRemoval()) {
                SearchBar.this.mSearchFrame.shiftToNextSubtype();
                SearchBar.this.mSearchFrame.setBubbleViewStyle(0);
            }
            SearchBar.this.mSearchFrame.onDelimiterPressed(SearchBar.this.getSearchText());
            if (end > 1) {
                return source.subSequence(start, end - 2);
            }
            return "";
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchBar.3 */
    class C15933 extends SimpleOnGestureListener {
        C15933() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchBar.4 */
    class C15944 implements OnClickListener {
        C15944() {
        }

        public void onClick(View view) {
            NaviNativeActivity.showKeyboard(view);
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchBar.5 */
    class C15955 implements Runnable {
        C15955() {
        }

        public void run() {
            SearchBar.this.mScrollView.smoothScrollTo(SearchBar.this.mScrollView.getRight(), 0);
        }
    }

    /* renamed from: com.sygic.aura.search.view.SearchBar.6 */
    class C15966 implements OnMenuItemClickListener {
        C15966() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            Context context = SearchBar.this.getContext();
            if (context != null) {
                SearchBar.this.paste(context);
            }
            return true;
        }
    }

    static {
        mBubbleSearchDelimiters = Arrays.asList(new Character[]{Character.valueOf('\n')});
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.NO_INPUT_FILTER = new InputFilter[]{new C15911()};
        this.STANDARD_INPUT_FILTER = new InputFilter[]{new C15922()};
        this.mBoolAnimationInRun = false;
        this.mBubbleHints = isInEditMode() ? null : context.getResources().getStringArray(2131492887);
        this.mDoubleTapDetector = new GestureDetector(context, new C15933());
        setTypeface(Typefaces.getFont(getContext(), 2131166101));
        setFilters(this.STANDARD_INPUT_FILTER);
        setOnClickListener(new C15944());
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mParams = getLayoutParams();
    }

    public void setParent(SearchFrame parent) {
        this.mSearchFrame = parent;
    }

    public void setSearchBarHint(int bubbleIndex) {
        int hintIndex = bubbleIndex;
        if (this.mSearchFrame != null) {
            if (this.mSearchFrame.isTerminalBubble()) {
                removeSearchBarHint();
                return;
            } else if (bubbleIndex == 3) {
                int[] numbers = this.mSearchFrame.getStreetHouseNumbersMinMax();
                if (numbers != null && numbers.length >= 2) {
                    setHint(ResourceManager.getCoreString(getResources().getString(2131165674)).concat(" " + Integer.toString(numbers[0]) + " - " + Integer.toString(numbers[1])));
                    return;
                } else if (this.mSearchFrame.isHouseFirst()) {
                    hintIndex--;
                }
            } else if (bubbleIndex == 0 && this.mSearchFrame.getPosition() == 0 && !this.mSearchFrame.isStartFromCurrentLocation()) {
                setHint(ResourceManager.getCoreString(getResources(), 2131165660));
                return;
            }
        }
        setHint(this.mBubbleHints[Math.min(hintIndex, 3)]);
    }

    public void removeSearchBarHint() {
        setHint("");
    }

    private void setHint(String hint) {
        String hintText = ResourceManager.getCoreString(hint);
        super.setHint(hintText);
        if (this.mScrollView == null) {
            this.mScrollView = (HorizontalScrollView) this.mSearchFrame.findViewById(2131624637);
        }
        if (isScrollViewScrollable()) {
            this.mDefaultWidth = ((int) getPaint().measureText(hintText)) + 10;
        } else {
            this.mDefaultWidth = -1;
        }
        if (!(this.mParams == null || this.mParams.width == this.mDefaultWidth)) {
            this.mParams.width = this.mDefaultWidth;
            requestLayout();
        }
        scrollRow();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            this.mDefaultWidth = (right - left) + 10;
        }
    }

    public void scrollRow() {
        if (this.mScrollView == null) {
            this.mScrollView = (HorizontalScrollView) this.mSearchFrame.findViewById(2131624637);
        }
        this.mScrollView.postDelayed(new C15955(), 100);
    }

    public void setSearchFinished(boolean toFinish) {
        if (toFinish) {
            removeSearchBarHint();
            setFilters(this.NO_INPUT_FILTER);
            NaviNativeActivity.hideKeyboard(getWindowToken());
        } else {
            setFilters(this.STANDARD_INPUT_FILTER);
        }
        this.mSearchFrame.setAsTerminalBubble(toFinish);
    }

    public String getSearchText() {
        return String.valueOf(getText());
    }

    public void clearText() {
        if (!isEmpty()) {
            setText("");
        }
        requestFocus();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.mDoubleTapDetector.onTouchEvent(event)) {
            return super.onTouchEvent(event);
        }
        if (!isEmpty() || this.mSearchFrame.isTerminalBubble()) {
            return super.onTouchEvent(event);
        }
        if (this.mPopupMenu == null) {
            this.mPopupMenu = new PopupMenu(getContext(), this);
            this.mPopupMenu.getMenuInflater().inflate(2131755019, this.mPopupMenu.getMenu());
            this.mPopupMenu.setOnMenuItemClickListener(new C15966());
        }
        if (isSomethingToPaste(getContext())) {
            this.mPopupMenu.show();
        }
        return true;
    }

    public void paste(Context context) {
        if (VERSION.SDK_INT >= 11) {
            pasteNew(context);
        } else {
            pasteLegacy(context);
        }
    }

    @TargetApi(11)
    private void pasteNew(Context context) {
        ClipData clip = ((ClipboardManager) context.getSystemService("clipboard")).getPrimaryClip();
        if (clip != null) {
            setText(clip.getItemAt(0).coerceToText(context));
            setSelection(length());
        }
    }

    @TargetApi(8)
    private void pasteLegacy(Context context) {
        setText(((android.text.ClipboardManager) context.getSystemService("clipboard")).getText());
        setSelection(length());
    }

    public static boolean isSomethingToPaste(Context context) {
        if (VERSION.SDK_INT < 11) {
            return ((android.text.ClipboardManager) context.getSystemService("clipboard")).hasText();
        }
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clip = clipboardManager.getPrimaryClip();
            if (clip != null && clip.getItemCount() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getSearchText());
    }

    public void enable(boolean enable, boolean getFocus) {
        setEnabled(enable);
        if (enable) {
            setSearchBarHint(this.mSearchFrame.getSearchSubtype());
            if (getFocus) {
                requestFocus();
                return;
            }
            return;
        }
        removeSearchBarHint();
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        this.mSearchFrame.setSearchBarState(focused);
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 67) {
            CharSequence searchBarText = getText();
            boolean isEmptySearch = TextUtils.isEmpty(searchBarText);
            boolean wasEnter;
            if (isEmptySearch || searchBarText.charAt(searchBarText.length() - 1) != '\n') {
                wasEnter = false;
            } else {
                wasEnter = true;
            }
            if (this.mSearchFrame.hasBubbleInRemoval() && (isEmptySearch || wasEnter)) {
                this.mSearchFrame.makeBubbleInvisible();
                this.mSearchFrame.setSearchEditIcon(this.mSearchFrame.isEmpty());
            } else if (!isEmptySearch) {
                this.mSearchFrame.unselectBubbleForRemoval();
            } else if (this.mSearchFrame.getSearchSubtype() > 0) {
                this.mSearchFrame.setBubbleViewStyle(1);
                if (this.mSearchFrame.isTerminalBubble()) {
                    this.mSearchFrame.setAsTerminalBubble(false);
                }
                this.mSearchFrame.initPreviousCoreSearch();
                this.mSearchFrame.shiftToPreviousSubtype();
            } else {
                this.mSearchFrame.setSearchEditIcon(this.mSearchFrame.isEmpty());
                this.mSearchFrame.initCoreSearch();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 66) {
            append("\n");
        }
        return super.onKeyUp(keyCode, event);
    }

    private boolean isScrollViewScrollable() {
        if (this.mScrollView.getChildCount() == 1 && this.mScrollView.getWidth() >= this.mScrollView.getChildAt(0).getWidth()) {
            return false;
        }
        return true;
    }

    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (this.mSearchFrame != null) {
            boolean hasText;
            boolean z;
            if (text.length() > 0) {
                hasText = true;
            } else {
                hasText = false;
            }
            SearchFrame searchFrame = this.mSearchFrame;
            if (hasText || !this.mSearchFrame.isEmpty()) {
                z = false;
            } else {
                z = true;
            }
            searchFrame.setSearchEditIcon(z);
            if (hasText) {
                int newWidth = ((int) getPaint().measureText(getSearchText())) + 10;
                if (newWidth > this.mDefaultWidth) {
                    this.mParams.width = newWidth;
                    requestLayout();
                }
            } else {
                this.mParams.width = isScrollViewScrollable() ? this.mDefaultWidth : -1;
                requestLayout();
            }
            String searchQuery = String.valueOf(text);
            if (SettingsManager.nativeIsDebugEnabled() && searchQuery.equals("ccc")) {
                ((UiModeManager) SygicMain.getActivity().getSystemService("uimode")).enableCarMode(0);
            }
            if (lengthBefore > lengthAfter) {
                this.mSearchFrame.initCoreSearch(searchQuery);
            } else {
                if (text.length() == 1) {
                    if (this.mSearchFrame.hasBubbleInRemoval()) {
                        this.mSearchFrame.makeBubbleInvisible();
                    }
                    this.mSearchFrame.initCoreSearch(searchQuery);
                } else if (this.mSearchFrame.isActive()) {
                    this.mSearchFrame.queryCoreSearch(searchQuery);
                }
                if (this.mSearchFrame != null && this.mSearchFrame.hasBubbleInRemoval()) {
                    this.mSearchFrame.setBubbleViewStyle(0);
                }
            }
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    public synchronized boolean isAnimationRunning() {
        return this.mBoolAnimationInRun;
    }

    private synchronized void setAnimationRunning(boolean isRunning) {
        this.mBoolAnimationInRun = isRunning;
    }

    public void onAnimationStart(Animation animation) {
        setAnimationRunning(true);
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        boolean wasDelAnim;
        clearAnimation();
        setAnimationRunning(false);
        if (animation.getStartOffset() == 5) {
            wasDelAnim = true;
        } else {
            wasDelAnim = false;
        }
        if (wasDelAnim) {
            setSearchBarHint(this.mSearchFrame.getSearchSubtype());
            setSearchFinished(false);
            this.mSearchFrame.unselectBubbleForRemoval();
        }
    }
}
