package com.sygic.aura.keyboard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.sygic.aura.C1090R;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import loquendo.tts.engine.TTSConst;

public class FocusableKeyboardView extends View implements OnClickListener {
    private static final int[] KEY_DELETE;
    private static final int LONGPRESS_TIMEOUT;
    private static int MAX_NEARBY_KEYS;
    private boolean mAbortKey;
    private Bitmap mBuffer;
    private Canvas mCanvas;
    private final int[] mCoordinates;
    private int mCurrentKey;
    private int mCurrentKeyIndex;
    private long mCurrentKeyTime;
    private Rect mDirtyRect;
    private int[] mDistances;
    private int mDownKey;
    private long mDownTime;
    Handler mHandler;
    private Key mInvalidatedKey;
    protected boolean mIsKeyFocused;
    private Drawable mKeyBackground;
    private Drawable mKeyFocusBackground;
    protected int mKeyFocusIdx;
    private int[] mKeyIndices;
    private int mKeyTextColor;
    private int mKeyTextSize;
    private Keyboard mKeyboard;
    private OnKeyboardActionListener mKeyboardActionListener;
    private Key[] mKeys;
    private int mLabelTextSize;
    private int mLastCodeX;
    private int mLastCodeY;
    private int mLastKey;
    private long mLastKeyTime;
    private long mLastMoveTime;
    private int mLastX;
    private int mLastY;
    private Map<Key, View> mMiniKeyboardCache;
    private int mMiniKeyboardOffsetX;
    private int mMiniKeyboardOffsetY;
    private boolean mMiniKeyboardOnScreen;
    private int mOldPointerCount;
    private float mOldPointerX;
    private float mOldPointerY;
    private Rect mPadding;
    private Paint mPaint;
    private PopupWindow mPopupKeyboard;
    private View mPopupParent;
    private int mPopupPreviewX;
    private int mPopupPreviewY;
    private boolean mPreviewCentered;
    private int mPreviewHeight;
    private int mPreviewOffset;
    private PopupWindow mPreviewPopup;
    private TextView mPreviewText;
    private int mPreviewTextSizeLarge;
    private boolean mProximityCorrectOn;
    private int mProximityThreshold;
    private int mRepeatKeyIndex;
    private int mShadowColor;
    private float mShadowRadius;
    private boolean mShowPreview;
    private int mStartX;
    private int mStartY;
    private int mVerticalCorrection;

    public interface OnKeyboardActionListener {
        void onKey(int i, int[] iArr);

        void onPress(int i);

        void onRelease(int i);

        void onText(CharSequence charSequence);
    }

    /* renamed from: com.sygic.aura.keyboard.FocusableKeyboardView.1 */
    class C12891 extends Handler {
        C12891() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TTSConst.TTSUNICODE /*3*/:
                    if (FocusableKeyboardView.this.repeatKey()) {
                        sendMessageDelayed(Message.obtain(this, 3), 50);
                    }
                default:
            }
        }
    }

    static {
        KEY_DELETE = new int[]{-5};
        LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
        MAX_NEARBY_KEYS = 12;
    }

    public FocusableKeyboardView(Context context) {
        this(context, null, 2130772101);
    }

    public FocusableKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 2130772101);
    }

    public FocusableKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentKeyIndex = -1;
        this.mCoordinates = new int[2];
        this.mPreviewCentered = false;
        this.mShowPreview = true;
        this.mCurrentKey = -1;
        this.mDownKey = -1;
        this.mKeyIndices = new int[12];
        this.mRepeatKeyIndex = -1;
        this.mOldPointerCount = 1;
        this.mDistances = new int[MAX_NEARBY_KEYS];
        this.mDirtyRect = new Rect();
        this.mHandler = new C12891();
        setWillNotDraw(false);
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.FocusableKeyboardView, defStyleAttr, 0);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService("layout_inflater");
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == 1) {
                this.mKeyBackground = a.getDrawable(attr);
            } else if (attr == 2) {
                this.mKeyFocusBackground = a.getDrawable(attr);
            } else if (attr == 3) {
                this.mKeyTextColor = a.getColor(attr, -16777216);
            } else if (attr == 8) {
                this.mVerticalCorrection = a.getDimensionPixelOffset(attr, 0);
            } else if (attr == 5) {
                this.mKeyTextSize = a.getDimensionPixelSize(attr, 18);
            } else if (attr == 6) {
                this.mLabelTextSize = a.getDimensionPixelSize(attr, 14);
            } else if (attr == 4) {
                this.mShadowColor = a.getColor(attr, 0);
            } else if (attr == 7) {
                this.mShadowRadius = a.getFloat(attr, 0.0f);
            }
        }
        this.mPreviewPopup = new PopupWindow(context);
        if (null != null) {
            this.mPreviewText = (TextView) inflate.inflate(0, null);
            this.mPreviewTextSizeLarge = (int) this.mPreviewText.getTextSize();
            this.mPreviewPopup.setContentView(this.mPreviewText);
            this.mPreviewPopup.setBackgroundDrawable(null);
        } else {
            this.mShowPreview = false;
        }
        this.mPreviewPopup.setTouchable(false);
        this.mPopupKeyboard = new PopupWindow(context);
        this.mPopupKeyboard.setBackgroundDrawable(null);
        this.mPopupParent = this;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize((float) 0);
        this.mPaint.setTextAlign(Align.CENTER);
        this.mPaint.setAlpha(255);
        this.mPaint.setShadowLayer(this.mShadowRadius, 0.0f, 0.0f, this.mShadowColor);
        this.mPadding = new Rect(0, 0, 0, 0);
        this.mMiniKeyboardCache = new HashMap();
        this.mKeyBackground.getPadding(this.mPadding);
        this.mKeyFocusIdx = -1;
        hideKeyFocus();
        a.recycle();
    }

    public void setOnKeyboardActionListener(OnKeyboardActionListener listener) {
        this.mKeyboardActionListener = listener;
    }

    protected OnKeyboardActionListener getOnKeyboardActionListener() {
        return this.mKeyboardActionListener;
    }

    public void setKeyboard(Keyboard keyboard) {
        if (this.mKeyboard != null) {
            showPreview(-1);
        }
        removeMessages();
        this.mKeyboard = keyboard;
        if (this.mKeyboard != null) {
            List<Key> keys = this.mKeyboard.getKeys();
            this.mKeys = (Key[]) keys.toArray(new Key[keys.size()]);
        }
        requestLayout();
        if (this.mBuffer == null || (this.mBuffer.getWidth() == getWidth() && this.mBuffer.getHeight() == getHeight())) {
            invalidateAllKeys();
        } else {
            this.mBuffer = null;
        }
        computeProximityThreshold(keyboard);
        this.mMiniKeyboardCache.clear();
        this.mAbortKey = true;
        resetKeyFocus();
    }

    public Keyboard getKeyboard() {
        return this.mKeyboard;
    }

    public boolean setShifted(boolean shifted) {
        if (this.mKeyboard == null || !this.mKeyboard.setShifted(shifted)) {
            return false;
        }
        invalidateAllKeys();
        return true;
    }

    public boolean isShifted() {
        if (this.mKeyboard != null) {
            return this.mKeyboard.isShifted();
        }
        return false;
    }

    public void setPreviewEnabled(boolean previewEnabled) {
        this.mShowPreview = previewEnabled;
    }

    public void setVerticalCorrection(int verticalOffset) {
    }

    public void setPopupParent(View view) {
        this.mPopupParent = view;
    }

    public void setProximityCorrectionEnabled(boolean enabled) {
        this.mProximityCorrectOn = enabled;
    }

    public void onClick(View view) {
        dismissPopupKeyboard();
    }

    private CharSequence adjustCase(CharSequence label) {
        if (this.mKeyboard == null || !this.mKeyboard.isShifted() || label == null || label.length() >= 3 || !Character.isLowerCase(label.charAt(0))) {
            return label;
        }
        return label.toString().toUpperCase();
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mKeyboard == null) {
            setMeasuredDimension(getPaddingLeft() + getPaddingRight(), getPaddingTop() + getPaddingBottom());
            return;
        }
        int width = (this.mKeyboard.getMinWidth() + getPaddingLeft()) + getPaddingRight();
        if (MeasureSpec.getSize(widthMeasureSpec) < width + 10) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        setMeasuredDimension(width, (this.mKeyboard.getHeight() + getPaddingTop()) + getPaddingBottom());
    }

    private void computeProximityThreshold(Keyboard keyboard) {
        if (keyboard != null) {
            Key[] keys = this.mKeys;
            if (keys != null) {
                int dimensionSum = 0;
                for (Key key : keys) {
                    dimensionSum += Math.min(key.width, key.height) + key.gap;
                }
                if (dimensionSum >= 0 && length != 0) {
                    this.mProximityThreshold = (int) ((((float) dimensionSum) * 1.4f) / ((float) length));
                    this.mProximityThreshold *= this.mProximityThreshold;
                }
            }
        }
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mBuffer = null;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mDirtyRect.isEmpty() || this.mBuffer == null) {
            onBufferDraw();
        }
        canvas.drawBitmap(this.mBuffer, 0.0f, 0.0f, null);
    }

    private void onBufferDraw() {
        if (this.mKeyboard != null) {
            int max;
            if (this.mBuffer == null) {
                max = Math.max(1, getWidth());
                this.mBuffer = Bitmap.createBitmap(width, Math.max(1, getHeight()), Config.ARGB_8888);
                this.mCanvas = new Canvas(this.mBuffer);
                invalidateAllKeys();
            }
            int kbdPaddingLeft = getPaddingLeft();
            int kbdPaddingTop = getPaddingTop();
            ColorStateList csl = getContext().getResources().getColorStateList(2131558827);
            ColorStateList cslFocused = getContext().getResources().getColorStateList(2131558828);
            this.mCanvas.clipRect(this.mDirtyRect, Op.REPLACE);
            this.mCanvas.drawColor(0, Mode.CLEAR);
            boolean drawSingleKey = false;
            if (this.mInvalidatedKey != null) {
                if ((this.mInvalidatedKey.x + kbdPaddingLeft) - 1 <= this.mDirtyRect.left) {
                    if ((this.mInvalidatedKey.y + kbdPaddingTop) - 1 <= this.mDirtyRect.top) {
                        if (((this.mInvalidatedKey.x + this.mInvalidatedKey.width) + kbdPaddingLeft) + 1 >= this.mDirtyRect.right) {
                            if (((this.mInvalidatedKey.y + this.mInvalidatedKey.height) + kbdPaddingTop) + 1 >= this.mDirtyRect.bottom) {
                                drawSingleKey = true;
                            }
                        }
                    }
                }
            }
            int i = 0;
            while (true) {
                int length = this.mKeys.length;
                if (i >= max) {
                    break;
                }
                String label;
                Key key = this.mKeys[i];
                if (drawSingleKey) {
                    Key key2 = this.mInvalidatedKey;
                    if (r0 != key) {
                        continue;
                        i++;
                    }
                }
                int[] drawableState = key.getCurrentDrawableState();
                this.mCanvas.translate((float) (key.x + kbdPaddingLeft), (float) (key.y + kbdPaddingTop));
                length = this.mKeyFocusIdx;
                Rect bounds;
                if (max == i && isKeyFocused()) {
                    this.mKeyFocusBackground.setState(drawableState);
                    bounds = this.mKeyFocusBackground.getBounds();
                    if (!(key.width == bounds.right && key.height == bounds.bottom)) {
                        this.mKeyFocusBackground.setBounds(0, 0, key.width, key.height);
                    }
                    this.mKeyFocusBackground.draw(this.mCanvas);
                } else {
                    this.mKeyBackground.setState(drawableState);
                    bounds = this.mKeyBackground.getBounds();
                    if (!(key.width == bounds.right && key.height == bounds.bottom)) {
                        this.mKeyBackground.setBounds(0, 0, key.width, key.height);
                    }
                    this.mKeyBackground.draw(this.mCanvas);
                }
                if (key.label == null) {
                    label = null;
                } else {
                    label = adjustCase(key.label).toString();
                }
                int i2;
                if (label != null) {
                    Canvas canvas;
                    int i3;
                    float f;
                    int i4;
                    float f2;
                    Paint paint;
                    Paint paint2;
                    if (label.length() > 1) {
                        length = key.codes.length;
                        if (max < 2) {
                            this.mPaint.setTextSize((float) this.mLabelTextSize);
                            this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
                            length = this.mKeyFocusIdx;
                            if (max == i || !isKeyFocused()) {
                                this.mPaint.setColor(csl.getColorForState(key.getCurrentDrawableState(), 0));
                            } else {
                                this.mPaint.setColor(cslFocused.getColorForState(key.getCurrentDrawableState(), 0));
                            }
                            canvas = this.mCanvas;
                            i2 = key.width;
                            i3 = this.mPadding.left;
                            i3 = this.mPadding.right;
                            f = (float) ((((max - max) - max) / 2) + this.mPadding.left);
                            i3 = key.height;
                            i4 = this.mPadding.top;
                            f2 = (float) (((max - max) - this.mPadding.bottom) / 2);
                            paint = this.mPaint;
                            paint2 = this.mPaint;
                            canvas.drawText(label, f, (r0 + ((r0.getTextSize() - r0.descent()) / 2.0f)) + ((float) this.mPadding.top), this.mPaint);
                        }
                    }
                    this.mPaint.setTextSize((float) this.mKeyTextSize);
                    this.mPaint.setTypeface(Typeface.DEFAULT);
                    length = this.mKeyFocusIdx;
                    if (max == i) {
                    }
                    this.mPaint.setColor(csl.getColorForState(key.getCurrentDrawableState(), 0));
                    canvas = this.mCanvas;
                    i2 = key.width;
                    i3 = this.mPadding.left;
                    i3 = this.mPadding.right;
                    f = (float) ((((max - max) - max) / 2) + this.mPadding.left);
                    i3 = key.height;
                    i4 = this.mPadding.top;
                    f2 = (float) (((max - max) - this.mPadding.bottom) / 2);
                    paint = this.mPaint;
                    paint2 = this.mPaint;
                    canvas.drawText(label, f, (r0 + ((r0.getTextSize() - r0.descent()) / 2.0f)) + ((float) this.mPadding.top), this.mPaint);
                } else if (key.icon != null) {
                    length = key.width;
                    i2 = this.mPadding.left;
                    i2 = this.mPadding.right;
                    Drawable drawable = key.icon;
                    int drawableX = ((((max - max) - max) - r0.getIntrinsicWidth()) / 2) + this.mPadding.left;
                    length = key.height;
                    i2 = this.mPadding.top;
                    i2 = this.mPadding.bottom;
                    drawable = key.icon;
                    int drawableY = ((((max - max) - max) - r0.getIntrinsicHeight()) / 2) + this.mPadding.top;
                    this.mCanvas.translate((float) drawableX, (float) drawableY);
                    key.icon.setBounds(0, 0, key.icon.getIntrinsicWidth(), key.icon.getIntrinsicHeight());
                    key.icon.draw(this.mCanvas);
                    this.mCanvas.translate((float) (-drawableX), (float) (-drawableY));
                }
                this.mCanvas.translate((float) ((-key.x) - kbdPaddingLeft), (float) ((-key.y) - kbdPaddingTop));
                if (drawSingleKey) {
                    break;
                }
                i++;
            }
            this.mInvalidatedKey = null;
            this.mDirtyRect.setEmpty();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getKeyIndices(int r19, int r20, int[] r21) {
        /*
        r18 = this;
        r13 = -1;
        r3 = -1;
        r0 = r18;
        r14 = r0.mProximityThreshold;
        r4 = r14 + 1;
        r0 = r18;
        r14 = r0.mDistances;
        r15 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        java.util.Arrays.fill(r14, r15);
        r0 = r18;
        r14 = r0.mKeyboard;
        r0 = r19;
        r1 = r20;
        r12 = r14.getNearestKeys(r0, r1);
        r10 = r12.length;
        r6 = 0;
    L_0x0020:
        if (r6 >= r10) goto L_0x00c2;
    L_0x0022:
        r14 = r18.getKeyboard();
        r14 = r14.getKeys();
        r15 = r12[r6];
        r9 = r14.get(r15);
        r9 = (android.inputmethodservice.Keyboard.Key) r9;
        r5 = 0;
        r0 = r19;
        r1 = r20;
        r7 = r9.isInside(r0, r1);
        if (r7 == 0) goto L_0x003f;
    L_0x003d:
        r13 = r12[r6];
    L_0x003f:
        r0 = r18;
        r14 = r0.mProximityCorrectOn;
        if (r14 == 0) goto L_0x0053;
    L_0x0045:
        r0 = r19;
        r1 = r20;
        r5 = r9.squaredDistanceFrom(r0, r1);
        r0 = r18;
        r14 = r0.mProximityThreshold;
        if (r5 < r14) goto L_0x0055;
    L_0x0053:
        if (r7 == 0) goto L_0x0068;
    L_0x0055:
        r14 = r9.codes;
        r15 = 0;
        r14 = r14[r15];
        r15 = 32;
        if (r14 <= r15) goto L_0x0068;
    L_0x005e:
        r14 = r9.codes;
        r11 = r14.length;
        if (r5 >= r4) goto L_0x0066;
    L_0x0063:
        r4 = r5;
        r3 = r12[r6];
    L_0x0066:
        if (r21 != 0) goto L_0x006b;
    L_0x0068:
        r6 = r6 + 1;
        goto L_0x0020;
    L_0x006b:
        r8 = 0;
    L_0x006c:
        r0 = r18;
        r14 = r0.mDistances;
        r14 = r14.length;
        if (r8 >= r14) goto L_0x0068;
    L_0x0073:
        r0 = r18;
        r14 = r0.mDistances;
        r14 = r14[r8];
        if (r14 <= r5) goto L_0x00bf;
    L_0x007b:
        r0 = r18;
        r14 = r0.mDistances;
        r0 = r18;
        r15 = r0.mDistances;
        r16 = r8 + r11;
        r0 = r18;
        r0 = r0.mDistances;
        r17 = r0;
        r0 = r17;
        r0 = r0.length;
        r17 = r0;
        r17 = r17 - r8;
        r17 = r17 - r11;
        r0 = r16;
        r1 = r17;
        java.lang.System.arraycopy(r14, r8, r15, r0, r1);
        r14 = r8 + r11;
        r0 = r21;
        r15 = r0.length;
        r15 = r15 - r8;
        r15 = r15 - r11;
        r0 = r21;
        r1 = r21;
        java.lang.System.arraycopy(r0, r8, r1, r14, r15);
        r2 = 0;
    L_0x00aa:
        if (r2 >= r11) goto L_0x0068;
    L_0x00ac:
        r14 = r8 + r2;
        r15 = r9.codes;
        r15 = r15[r2];
        r21[r14] = r15;
        r0 = r18;
        r14 = r0.mDistances;
        r15 = r8 + r2;
        r14[r15] = r5;
        r2 = r2 + 1;
        goto L_0x00aa;
    L_0x00bf:
        r8 = r8 + 1;
        goto L_0x006c;
    L_0x00c2:
        r14 = -1;
        if (r13 != r14) goto L_0x00c6;
    L_0x00c5:
        r13 = r3;
    L_0x00c6:
        return r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.keyboard.FocusableKeyboardView.getKeyIndices(int, int, int[]):int");
    }

    private void detectAndSendKey(int index, int x, int y) {
        if (index != -1 && index < this.mKeys.length) {
            Key key = this.mKeys[index];
            if (key.text != null) {
                this.mKeyboardActionListener.onText(key.text);
                this.mKeyboardActionListener.onRelease(-1);
                return;
            }
            int code = key.codes[0];
            int[] codes = new int[MAX_NEARBY_KEYS];
            Arrays.fill(codes, -1);
            getKeyIndices(x, y, codes);
            this.mKeyboardActionListener.onKey(code, codes);
            this.mKeyboardActionListener.onRelease(code);
        }
    }

    private CharSequence getPreviewText(Key key) {
        return adjustCase(key.label);
    }

    private void showPreview(int keyIndex) {
        int oldKeyIndex = this.mCurrentKeyIndex;
        PopupWindow previewPopup = this.mPreviewPopup;
        this.mCurrentKeyIndex = keyIndex;
        if (oldKeyIndex != this.mCurrentKeyIndex) {
            if (oldKeyIndex != -1 && this.mKeys.length > oldKeyIndex) {
                this.mKeys[oldKeyIndex].onReleased(this.mCurrentKeyIndex == -1);
                invalidateKey(oldKeyIndex);
            }
            if (this.mCurrentKeyIndex != -1 && this.mKeys.length > this.mCurrentKeyIndex) {
                this.mKeys[this.mCurrentKeyIndex].onPressed();
                invalidateKey(this.mCurrentKeyIndex);
            }
        }
        if (oldKeyIndex != this.mCurrentKeyIndex && this.mShowPreview) {
            this.mHandler.removeMessages(1);
            if (previewPopup.isShowing() && keyIndex == -1) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2), 70);
            }
            if (keyIndex == -1) {
                return;
            }
            if (previewPopup.isShowing() && this.mPreviewText.getVisibility() == 0) {
                showKey(keyIndex);
            } else {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, keyIndex, 0), 0);
            }
        }
    }

    private void showKey(int keyIndex) {
        PopupWindow previewPopup = this.mPreviewPopup;
        if (keyIndex >= 0 && keyIndex < this.mKeys.length) {
            Key key = this.mKeys[keyIndex];
            if (key.icon != null) {
                this.mPreviewText.setCompoundDrawables(null, null, null, key.iconPreview != null ? key.iconPreview : key.icon);
                this.mPreviewText.setText(null);
            } else {
                this.mPreviewText.setCompoundDrawables(null, null, null, null);
                this.mPreviewText.setText(getPreviewText(key));
                if (key.label.length() <= 1 || key.codes.length >= 2) {
                    this.mPreviewText.setTextSize(0, (float) this.mPreviewTextSizeLarge);
                    this.mPreviewText.setTypeface(Typeface.DEFAULT);
                } else {
                    this.mPreviewText.setTextSize(0, (float) this.mKeyTextSize);
                    this.mPreviewText.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }
            this.mPreviewText.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
            int popupWidth = Math.max(this.mPreviewText.getMeasuredWidth(), (key.width + this.mPreviewText.getPaddingLeft()) + this.mPreviewText.getPaddingRight());
            int popupHeight = this.mPreviewHeight;
            LayoutParams lp = this.mPreviewText.getLayoutParams();
            if (lp != null) {
                lp.width = popupWidth;
                lp.height = popupHeight;
            }
            if (this.mPreviewCentered) {
                this.mPopupPreviewX = 160 - (this.mPreviewText.getMeasuredWidth() / 2);
                this.mPopupPreviewY = -this.mPreviewText.getMeasuredHeight();
            } else {
                this.mPopupPreviewX = (key.x - this.mPreviewText.getPaddingLeft()) + getPaddingLeft();
                this.mPopupPreviewY = (key.y - popupHeight) + this.mPreviewOffset;
            }
            this.mHandler.removeMessages(2);
            getLocationInWindow(this.mCoordinates);
            int[] iArr = this.mCoordinates;
            iArr[0] = iArr[0] + this.mMiniKeyboardOffsetX;
            iArr = this.mCoordinates;
            iArr[1] = iArr[1] + this.mMiniKeyboardOffsetY;
            this.mPreviewText.getBackground().setState(EMPTY_STATE_SET);
            this.mPopupPreviewX += this.mCoordinates[0];
            this.mPopupPreviewY += this.mCoordinates[1];
            getLocationOnScreen(this.mCoordinates);
            if (this.mPopupPreviewY + this.mCoordinates[1] < 0) {
                if (key.x + key.width <= getWidth() / 2) {
                    this.mPopupPreviewX += (int) (((double) key.width) * 2.5d);
                } else {
                    this.mPopupPreviewX -= (int) (((double) key.width) * 2.5d);
                }
                this.mPopupPreviewY += popupHeight;
            }
            if (previewPopup.isShowing()) {
                previewPopup.update(this.mPopupPreviewX, this.mPopupPreviewY, popupWidth, popupHeight);
            } else {
                previewPopup.setWidth(popupWidth);
                previewPopup.setHeight(popupHeight);
                previewPopup.showAtLocation(this.mPopupParent, 0, this.mPopupPreviewX, this.mPopupPreviewY);
            }
            this.mPreviewText.setVisibility(0);
        }
    }

    public void invalidateAllKeys() {
        this.mDirtyRect.union(0, 0, getWidth(), getHeight());
        invalidate();
    }

    public void invalidateKey(int keyIndex) {
        if (this.mKeys != null && keyIndex >= 0 && keyIndex < this.mKeys.length) {
            Key key = this.mKeys[keyIndex];
            this.mInvalidatedKey = key;
            this.mDirtyRect.union(key.x + getPaddingLeft(), key.y + getPaddingTop(), (key.x + key.width) + getPaddingLeft(), (key.y + key.height) + getPaddingTop());
            invalidate(key.x + getPaddingLeft(), key.y + getPaddingTop(), (key.x + key.width) + getPaddingLeft(), (key.y + key.height) + getPaddingTop());
        }
    }

    public boolean onTouchEvent(MotionEvent me) {
        boolean result;
        int pointerCount = me.getPointerCount();
        int action = me.getAction();
        long now = me.getEventTime();
        if (pointerCount != this.mOldPointerCount) {
            if (pointerCount == 1) {
                MotionEvent down = MotionEvent.obtain(now, now, 0, me.getX(), me.getY(), me.getMetaState());
                result = onModifiedTouchEvent(down, false);
                down.recycle();
                if (action == 1) {
                    result = onModifiedTouchEvent(me, true);
                }
            } else {
                MotionEvent up = MotionEvent.obtain(now, now, 1, this.mOldPointerX, this.mOldPointerY, me.getMetaState());
                result = onModifiedTouchEvent(up, true);
                up.recycle();
            }
        } else if (pointerCount == 1) {
            result = onModifiedTouchEvent(me, false);
            this.mOldPointerX = me.getX();
            this.mOldPointerY = me.getY();
        } else {
            result = true;
        }
        this.mOldPointerCount = pointerCount;
        return result;
    }

    private boolean onModifiedTouchEvent(MotionEvent me, boolean possiblePoly) {
        int touchX = ((int) me.getX()) - getPaddingLeft();
        int touchY = ((int) me.getY()) - getPaddingTop();
        if (touchY >= (-this.mVerticalCorrection)) {
            touchY += this.mVerticalCorrection;
        }
        int action = me.getAction();
        long eventTime = me.getEventTime();
        int keyIndex = getKeyIndices(touchX, touchY, null);
        if (this.mAbortKey && action != 0 && action != 3) {
            return true;
        }
        if (this.mMiniKeyboardOnScreen && action != 3) {
            return true;
        }
        switch (action) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.mAbortKey = false;
                this.mStartX = touchX;
                this.mStartY = touchY;
                this.mLastCodeX = touchX;
                this.mLastCodeY = touchY;
                this.mLastKeyTime = 0;
                this.mCurrentKeyTime = 0;
                this.mLastKey = -1;
                this.mCurrentKey = keyIndex;
                this.mDownKey = keyIndex;
                this.mDownTime = me.getEventTime();
                this.mLastMoveTime = this.mDownTime;
                this.mKeyboardActionListener.onPress(keyIndex != -1 ? this.mKeys[keyIndex].codes[0] : 0);
                if (this.mCurrentKey >= 0 && this.mKeys[this.mCurrentKey].repeatable) {
                    this.mRepeatKeyIndex = this.mCurrentKey;
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(3), 400);
                    repeatKey();
                    if (this.mAbortKey) {
                        this.mRepeatKeyIndex = -1;
                        break;
                    }
                }
                if (this.mCurrentKey != -1) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(4, me), (long) LONGPRESS_TIMEOUT);
                }
                showPreview(keyIndex);
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                removeMessages();
                if (keyIndex == this.mCurrentKey) {
                    this.mCurrentKeyTime += eventTime - this.mLastMoveTime;
                } else {
                    this.mLastKey = this.mCurrentKey;
                    this.mLastKeyTime = (this.mCurrentKeyTime + eventTime) - this.mLastMoveTime;
                    this.mCurrentKey = keyIndex;
                    this.mCurrentKeyTime = 0;
                }
                if (this.mCurrentKeyTime < this.mLastKeyTime && this.mCurrentKeyTime < 70 && this.mLastKey != -1) {
                    this.mCurrentKey = this.mLastKey;
                    touchX = this.mLastCodeX;
                    touchY = this.mLastCodeY;
                }
                showPreview(-1);
                Arrays.fill(this.mKeyIndices, -1);
                if (!(this.mRepeatKeyIndex != -1 || this.mMiniKeyboardOnScreen || this.mAbortKey)) {
                    detectAndSendKey(this.mCurrentKey, touchX, touchY);
                }
                this.mRepeatKeyIndex = -1;
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                boolean continueLongPress = false;
                if (keyIndex != -1) {
                    if (this.mCurrentKey == -1) {
                        this.mCurrentKey = keyIndex;
                        this.mCurrentKeyTime = eventTime - this.mDownTime;
                    } else if (keyIndex == this.mCurrentKey) {
                        this.mCurrentKeyTime += eventTime - this.mLastMoveTime;
                        continueLongPress = true;
                    } else if (this.mRepeatKeyIndex == -1) {
                        this.mLastKey = this.mCurrentKey;
                        this.mLastCodeX = this.mLastX;
                        this.mLastCodeY = this.mLastY;
                        this.mLastKeyTime = (this.mCurrentKeyTime + eventTime) - this.mLastMoveTime;
                        this.mCurrentKey = keyIndex;
                        this.mCurrentKeyTime = 0;
                    }
                }
                if (!continueLongPress) {
                    this.mHandler.removeMessages(4);
                    if (keyIndex != -1) {
                        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(4, me), (long) LONGPRESS_TIMEOUT);
                    }
                }
                showPreview(this.mCurrentKey);
                this.mLastMoveTime = eventTime;
                break;
            case TTSConst.TTSUNICODE /*3*/:
                removeMessages();
                dismissPopupKeyboard();
                this.mAbortKey = true;
                showPreview(-1);
                invalidateKey(this.mCurrentKey);
                break;
        }
        this.mLastX = touchX;
        this.mLastY = touchY;
        return true;
    }

    private boolean repeatKey() {
        Key key = this.mKeys[this.mRepeatKeyIndex];
        detectAndSendKey(this.mCurrentKey, key.x, key.y);
        return true;
    }

    public void closing() {
        if (this.mPreviewPopup.isShowing()) {
            this.mPreviewPopup.dismiss();
        }
        removeMessages();
        dismissPopupKeyboard();
        this.mBuffer = null;
        this.mCanvas = null;
        this.mMiniKeyboardCache.clear();
    }

    private void removeMessages() {
        this.mHandler.removeMessages(3);
        this.mHandler.removeMessages(4);
        this.mHandler.removeMessages(1);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        closing();
    }

    private void dismissPopupKeyboard() {
        if (this.mPopupKeyboard.isShowing()) {
            this.mPopupKeyboard.dismiss();
            this.mMiniKeyboardOnScreen = false;
            invalidateAllKeys();
        }
    }

    public void showKeyFocus() {
        this.mIsKeyFocused = true;
    }

    public void hideKeyFocus() {
        this.mIsKeyFocused = false;
    }

    public boolean isKeyFocused() {
        return this.mIsKeyFocused;
    }

    public void resetKeyFocus() {
        this.mKeyFocusIdx = -1;
    }

    public void rotateLeftKeyFocus() {
        if (this.mKeyboard != null) {
            List<Key> keys = this.mKeyboard.getKeys();
            if (keys.size() > 0) {
                int nextIdx;
                if (this.mKeyFocusIdx != -1) {
                    nextIdx = this.mKeyFocusIdx;
                    while (nextIdx - 1 >= 0) {
                        nextIdx--;
                        if (((Key) keys.get(nextIdx)).height != 0) {
                            break;
                        }
                    }
                }
                this.mKeyFocusIdx = keys.size() - 1;
                nextIdx = this.mKeyFocusIdx;
                setKeyFocus(nextIdx);
            }
        }
    }

    public void rotateRightKeyFocus() {
        if (this.mKeyboard != null) {
            List<Key> keys = this.mKeyboard.getKeys();
            if (keys.size() > 0) {
                int nextIdx;
                if (this.mKeyFocusIdx != -1) {
                    nextIdx = this.mKeyFocusIdx;
                    while (nextIdx + 1 < keys.size()) {
                        nextIdx++;
                        if (((Key) keys.get(nextIdx)).height != 0) {
                            break;
                        }
                    }
                }
                this.mKeyFocusIdx = 0;
                nextIdx = this.mKeyFocusIdx;
                setKeyFocus(nextIdx);
            }
        }
    }

    public void releaseKeyFocus() {
        if (this.mKeyboard != null && this.mKeyFocusIdx != -1) {
            this.mKeys[this.mKeyFocusIdx].onReleased(false);
            invalidateKey(this.mKeyFocusIdx);
            detectAndSendKey(this.mKeyFocusIdx, ((Key) this.mKeyboard.getKeys().get(this.mKeyFocusIdx)).x, ((Key) this.mKeyboard.getKeys().get(this.mKeyFocusIdx)).y);
        }
    }

    public void pressKeyFocus() {
        if (this.mKeyboard != null && this.mKeyFocusIdx != -1) {
            this.mKeys[this.mKeyFocusIdx].onReleased(false);
            invalidateKey(this.mKeyFocusIdx);
        }
    }

    public void setKeyFocus(int keyIndex) {
        int oldKeyFocusIdx = this.mKeyFocusIdx;
        this.mKeyFocusIdx = keyIndex;
        invalidateKey(oldKeyFocusIdx);
        invalidateKey(this.mKeyFocusIdx);
    }
}
