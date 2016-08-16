package com.bosch.myspin.serversdk.uielements;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.bosch.myspin.serversdk.resource.ResourceLoader;
import com.bosch.myspin.serversdk.resource.bitmaps.C0177a;
import com.bosch.myspin.serversdk.uielements.keyboardinterface.KeyboardRegister;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import loquendo.tts.engine.TTSConst;

public abstract class MySpinKeyboardBaseView extends View {
    private static final LogComponent f310a;
    private Timer f311A;
    private C0218a f312B;
    private Runnable f313C;
    private Runnable f314D;
    private int f315b;
    private int f316c;
    private int f317d;
    private Context f318e;
    private MySpinKeyboardButton f319f;
    private MySpinKeyboardButton f320g;
    private MySpinKeyboardButton f321h;
    private MySpinKeyboardButton f322i;
    private MySpinKeyboardButton f323j;
    private MySpinKeyboardButton f324k;
    private MySpinKeyboardButton f325l;
    private MySpinKeyboardButton f326m;
    protected Drawable mBackground;
    protected int mBarPos;
    protected Drawable mBtnBackgroundFlyin;
    protected Drawable mBtnBackgroundFlyinPressed;
    protected MySpinKeyboardButton mButtonFlyin;
    protected int mButtonHeight;
    protected MySpinKeyboardButton mButtonLanguage;
    protected MySpinKeyboardButton mButtonLeftArrow;
    protected int mButtonPadding;
    protected MySpinKeyboardButton mButtonRightArrow;
    protected ArrayList<MySpinKeyboardButton> mButtons;
    protected EditText mEditText;
    protected int mKeyboardHeight;
    protected int mKeyboardPadding;
    protected Typeface mKeyboardTypeface;
    protected Rect[] mRowsRect;
    protected int mScreenHeight;
    protected int mScreenWidth;
    protected boolean mShowFlyin;
    protected float mTextSize;
    protected int mType;
    private boolean f327n;
    private Drawable f328o;
    private Drawable f329p;
    private String f330q;
    private String f331r;
    private String f332s;
    private int f333t;
    private int f334u;
    private int f335v;
    private float f336w;
    private String f337x;
    private boolean f338y;
    private Handler f339z;

    /* renamed from: com.bosch.myspin.serversdk.uielements.MySpinKeyboardBaseView.a */
    class C0218a extends TimerTask {
        final /* synthetic */ MySpinKeyboardBaseView f309a;

        private C0218a(MySpinKeyboardBaseView mySpinKeyboardBaseView) {
            this.f309a = mySpinKeyboardBaseView;
        }

        public void run() {
            this.f309a.f339z.post(this.f309a.f313C);
        }
    }

    protected abstract boolean checkForSpecialDelete(int i, int i2);

    protected abstract boolean checkForSpecialFunction(String str, int i, int i2);

    protected abstract boolean checkForSpecialKeys(MySpinKeyboardButton mySpinKeyboardButton, int i, int i2);

    protected abstract String getImeText(int i);

    protected abstract String getLabel(String str);

    protected abstract String[] getLayout(int i);

    protected abstract int getResourceId(String str);

    protected abstract void languageButtonPressed();

    protected abstract void loadLayouts();

    protected abstract void loadSpecialLabels();

    protected abstract void prepareDrawing();

    static {
        f310a = LogComponent.Keyboard;
    }

    @Deprecated
    public MySpinKeyboardBaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f327n = true;
        this.mRowsRect = new Rect[5];
        this.f311A = new Timer();
        this.f313C = new C0228c(this);
        this.f314D = new C0229d(this);
        this.f318e = context;
        m334a();
    }

    public MySpinKeyboardBaseView(Context context, int i, int i2) {
        super(context);
        this.f327n = true;
        this.mRowsRect = new Rect[5];
        this.f311A = new Timer();
        this.f313C = new C0228c(this);
        this.f314D = new C0229d(this);
        Logger.logDebug(f310a, "MySpinKeyboardBase/(" + i + ", " + i2 + ")");
        this.mScreenHeight = i;
        this.mScreenWidth = i2;
        this.f318e = context;
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        this.mKeyboardHeight = (int) (((double) this.mScreenHeight) / 1.3d);
        this.mButtons = new ArrayList();
        this.mRowsRect[0] = new Rect();
        this.mRowsRect[1] = new Rect();
        this.mRowsRect[2] = new Rect();
        this.mRowsRect[3] = new Rect();
        this.mRowsRect[4] = new Rect();
        this.f339z = new Handler();
        m334a();
        setButtonResources();
        m343d();
        setType(1002);
        setVisibility(4);
    }

    public void enableLanguageButton(boolean z) {
        m337a(this.mButtonLanguage, z);
        this.f327n = z;
    }

    private void m334a() {
        Logger.logDebug(f310a, "MySpinKeyboardBase/loadLabels");
        Resources resources = this.f318e.getResources();
        this.mKeyboardPadding = (int) TypedValue.applyDimension(1, 4.0f, resources.getDisplayMetrics());
        this.mButtonPadding = (int) TypedValue.applyDimension(1, 1.0f, resources.getDisplayMetrics());
        this.mBackground = getIcon("*background");
        this.f328o = getIcon("*flyin");
        this.f331r = getLabel("*enter");
        if (this.f331r == null || this.f331r.isEmpty()) {
            this.f331r = "enter";
        }
        this.f330q = getLabel("*space");
        if (this.f330q == null || this.f330q.isEmpty()) {
            this.f330q = "space";
        }
        this.f332s = getLabel("*abc");
        if (this.f332s == null || this.f332s.isEmpty()) {
            this.f332s = "ABC";
        }
        this.f329p = getIcon("*pushed");
        this.mBtnBackgroundFlyin = getIcon("*flyinbutton");
        this.mBtnBackgroundFlyinPressed = getIcon("*flyinpushed");
        m339b();
        this.f321h = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.f322i = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.f323j = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.f324k = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.f325l = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.f326m = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.mButtonLeftArrow = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.mButtonRightArrow = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.mButtonFlyin = new MySpinKeyboardButton(this.mKeyboardTypeface);
        this.mButtonLanguage = new MySpinKeyboardButton(this.mKeyboardTypeface);
        loadSpecialLabels();
        loadLayouts();
    }

    protected void languageUpdated() {
        this.f326m.setText("?!&\n123");
        this.f323j.setText("*shift");
        this.f322i.setText(this.f331r);
        this.f322i.fitLabel();
        this.f330q = getLabel("*space");
        this.f325l.setText(this.f330q);
        this.f325l.fitLabel();
        if (this.mType == 1002) {
            setType(1002);
        } else {
            setType(1001);
        }
    }

    protected Drawable getIcon(String str) {
        int i;
        if (str.equals("*background")) {
            i = 22;
        } else if (str.equals("*flyin")) {
            i = 23;
        } else if (str.equals("*pushed")) {
            i = 19;
        } else if (str.equals("*flyinbutton")) {
            i = 11;
        } else if (str.equals("*flyinpushed")) {
            i = 12;
        } else {
            i = 0;
        }
        if (i > 0) {
            return new BitmapDrawable(getResources(), C0177a.m140a(getResources(), i));
        }
        return null;
    }

    private void m339b() {
        File file = new File(this.f318e.getCacheDir() + "/typeface.ttf");
        if (file.exists()) {
            this.mKeyboardTypeface = Typeface.createFromFile(file);
            return;
        }
        try {
            OutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(ResourceLoader.m139a(getResourceId("*flyinpushed")));
            fileOutputStream.close();
            this.mKeyboardTypeface = Typeface.createFromFile(file);
        } catch (Throwable e) {
            Logger.logError(f310a, "MySpinKeyboardBase/can't load typeface", e);
        } catch (RuntimeException e2) {
            Logger.logError(f310a, "MySpinKeyboardBase/can't load typeface: " + e2);
        }
    }

    protected void setButtonResources() {
        this.f331r = getImeText(1);
        if (this.f331r == null) {
            this.f331r = "enter";
        }
        configureButton(this.f321h, "*hide", true, false, 0);
        configureButton(this.f322i, this.f331r, true, false, -1);
        configureButton(this.f323j, "*shift", true, false, 1002);
        configureButton(this.f324k, "*del", true, false, 0);
        configureButton(this.f325l, this.f330q, true, false, -1);
        configureButton(this.f326m, "?!&\n123", true, false, -1);
        configureButton(this.mButtonLeftArrow, "*previous", true, true, 1);
        configureButton(this.mButtonRightArrow, "*next", true, true, 1);
        configureButton(this.mButtonLanguage, "*lang", true, false, 0);
    }

    protected void configureButton(MySpinKeyboardButton mySpinKeyboardButton, String str, boolean z, boolean z2, int i) {
        if (!z2) {
            mySpinKeyboardButton.setBackground(new ColorDrawable(str.equals(this.f331r) ? -11445670 : -12632257));
            mySpinKeyboardButton.setBackgroundPressed(new ColorDrawable(str.equals(this.f331r) ? -15525604 : -15132391));
        }
        mySpinKeyboardButton.setText(str);
        if (i != -1) {
            setButtonIcon(mySpinKeyboardButton, i);
        }
        mySpinKeyboardButton.setSpecialKey(z);
        mySpinKeyboardButton.setFlyinButton(z2);
    }

    protected void setButtonIcon(MySpinKeyboardButton mySpinKeyboardButton, int i) {
        Bitmap bitmap = null;
        String text = mySpinKeyboardButton.getText();
        if (text.equals("*hide")) {
            mySpinKeyboardButton.setIcon(C0177a.m140a(getResources(), 1));
        } else if (text.equals("*shift")) {
            switch (i) {
                case 1001:
                    bitmap = C0177a.m140a(getResources(), 4);
                    break;
                case 1002:
                    bitmap = C0177a.m140a(getResources(), 5);
                    break;
                case 1003:
                    bitmap = C0177a.m140a(getResources(), 6);
                    break;
            }
            if (bitmap != null) {
                mySpinKeyboardButton.setIcon(bitmap);
            }
        } else if (text.equals("*123alt")) {
            switch (i) {
                case 1004:
                    bitmap = C0177a.m140a(getResources(), 9);
                    break;
                case 1005:
                    bitmap = C0177a.m140a(getResources(), 10);
                    break;
            }
            if (bitmap != null) {
                mySpinKeyboardButton.setIcon(bitmap);
            }
        } else if (text.equals("*del")) {
            mySpinKeyboardButton.setIcon(C0177a.m140a(getResources(), 2));
        } else if (!text.equals("*lang")) {
        } else {
            if (i == 1) {
                mySpinKeyboardButton.setIcon(C0177a.m140a(getResources(), 7));
            } else if (i == 2) {
                mySpinKeyboardButton.setIcon(C0177a.m140a(getResources(), 8));
            }
        }
    }

    private void m337a(MySpinKeyboardButton mySpinKeyboardButton, boolean z) {
        if (mySpinKeyboardButton.getText().equals("*lang") && !z) {
            this.f327n = false;
            setButtonIcon(this.mButtonLanguage, 2);
        } else if (mySpinKeyboardButton.getText().equals("*lang") && z) {
            this.f327n = true;
            setButtonIcon(this.mButtonLanguage, 1);
        }
    }

    protected void generateKeyboardLayout() {
        String[] layout = getLayout(this.mType);
        m341c();
        this.f336w = (float) (((double) this.mRowsRect[0].width()) * 0.13d);
        placeKey(this.f321h, (int) (((float) this.mRowsRect[0].right) - this.f336w), this.mRowsRect[0].bottom, (int) this.f336w, this.mRowsRect[0].height());
        placeArrowKeys();
        int i = 1;
        for (String split : layout) {
            m335a(i, split.split(" "));
            i++;
        }
        setButtonTextSize();
        m344e();
    }

    private void m335a(int i, String[] strArr) {
        int length = strArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4;
            String str = strArr[i2];
            this.f319f = new MySpinKeyboardButton(this.mKeyboardTypeface);
            this.f319f.setBackground(new ColorDrawable(-12632257));
            this.f319f.setBackgroundPressed(this.f329p);
            this.f319f.setText(str);
            if (i == 3) {
                i4 = strArr[0].startsWith("*") ? 2 : 1;
                this.f336w = (float) ((int) (((double) this.mRowsRect[i].width()) / (((double) (strArr.length - i4)) + (1.5d * ((double) i4)))));
                if (str.equals("*shift") || str.equals("*123alt")) {
                    placeKey(this.f323j, this.mRowsRect[3].left, this.mRowsRect[3].bottom, (int) (((double) this.f336w) * 1.5d), this.mButtonHeight);
                    i4 = i3 + 1;
                } else if (str.equals("*del")) {
                    int i5 = ((MySpinKeyboardButton) this.mButtons.get(this.mButtons.size() - 1)).getPosition().right;
                    placeKey(this.f324k, i5, this.mRowsRect[3].bottom, this.mRowsRect[3].right - i5, this.mButtonHeight);
                    i4 = i3 + 1;
                } else if (checkForSpecialKeys(this.f319f, (int) this.f336w, this.mButtonHeight)) {
                    i4 = i3 + 1;
                } else {
                    if (i4 == 2) {
                        this.f319f.setPosition((int) (((float) this.f323j.getPosition().right) + (((float) (i3 - 1)) * this.f336w)), this.mRowsRect[i].bottom, this.mButtonHeight, (int) this.f336w, true);
                    } else {
                        this.f319f.setPosition((int) (((float) this.mRowsRect[i].left) + (((float) i3) * this.f336w)), this.mRowsRect[i].bottom, this.mButtonHeight, (int) this.f336w, true);
                    }
                    if (((float) this.f319f.getPosition().right) + (this.f336w / 2.0f) > ((float) this.mRowsRect[i].right)) {
                        this.f319f.setPosition(this.f319f.getPosition().left, this.f319f.getPosition().bottom, this.f319f.getPosition().top, this.mRowsRect[i].right, false);
                    }
                    this.f319f.setTopBottomPadding(this.mButtonPadding);
                    this.f319f.setRightPadding(this.mButtonPadding);
                    this.mButtons.add(this.f319f);
                    i4 = i3 + 1;
                }
            } else {
                if (i == 4) {
                    this.f336w = (float) ((int) (((double) this.mRowsRect[i].width()) / ((((double) (strArr.length - 2)) + 4.0d) + 2.0d)));
                    if (str.equals("*123") || str.equals("*abc")) {
                        placeKey(this.f326m, this.mRowsRect[4].left, this.mRowsRect[4].bottom, (int) this.f336w, this.mButtonHeight);
                        i4 = i3 + 1;
                    } else if (str.equals("*space")) {
                        placeKey(this.f325l, ((MySpinKeyboardButton) this.mButtons.get(this.mButtons.size() - 1)).getPosition().right, this.mRowsRect[4].bottom, (int) (((double) this.f336w) * 4.0d), this.mButtonHeight);
                        i4 = i3 + 1;
                    } else if (str.equals("*enter")) {
                        placeKey(this.f322i, (int) (((float) this.f325l.getPosition().right) + this.f336w), this.mRowsRect[4].bottom, this.mRowsRect[4].right - ((int) (((float) this.f325l.getPosition().right) + this.f336w)), this.mButtonHeight);
                        i4 = i3 + 1;
                    } else if (str.equals("*lang")) {
                        placeKey(this.mButtonLanguage, this.f326m.getPosition().right, this.mRowsRect[4].bottom, (int) this.f336w, this.mButtonHeight);
                        i4 = i3 + 1;
                    } else if (checkForSpecialKeys(this.f319f, (int) this.f336w, this.mButtonHeight)) {
                        i4 = i3 + 1;
                    } else if (i3 > 0 && strArr[i3 - 1].equals("*lang")) {
                        this.f319f.setPosition(this.mButtonLanguage.getPosition().right, this.mRowsRect[i].bottom, this.mButtonHeight, (int) this.f336w, true);
                    } else if (i3 < strArr.length && strArr[i3 + 1].equals("*enter")) {
                        this.f319f.setPosition(this.f325l.getPosition().right, this.mRowsRect[i].bottom, this.mButtonHeight, (int) this.f336w, true);
                    }
                } else {
                    this.f336w = (float) (this.mRowsRect[i].width() / strArr.length);
                    this.f319f.setPosition((int) (((float) this.mRowsRect[i].left) + (((float) i3) * this.f336w)), this.mRowsRect[i].bottom, this.mButtonHeight, (int) this.f336w, true);
                }
                if (((float) this.f319f.getPosition().right) + (this.f336w / 2.0f) > ((float) this.mRowsRect[i].right)) {
                    this.f319f.setPosition(this.f319f.getPosition().left, this.f319f.getPosition().bottom, this.f319f.getPosition().top, this.mRowsRect[i].right, false);
                }
                this.f319f.setTopBottomPadding(this.mButtonPadding);
                this.f319f.setRightPadding(this.mButtonPadding);
                this.mButtons.add(this.f319f);
                i4 = i3 + 1;
            }
            i2++;
            i3 = i4;
        }
    }

    private void m341c() {
        Collection arrayList = new ArrayList();
        if (this.mShowFlyin) {
            Iterator it = this.mButtons.iterator();
            while (it.hasNext()) {
                MySpinKeyboardButton mySpinKeyboardButton = (MySpinKeyboardButton) it.next();
                if (mySpinKeyboardButton.isFlyinButton()) {
                    arrayList.add(mySpinKeyboardButton);
                }
            }
        }
        this.mButtons.clear();
        if (arrayList.size() > 0) {
            this.mButtons.addAll(arrayList);
        }
    }

    protected void placeKey(MySpinKeyboardButton mySpinKeyboardButton, int i, int i2, int i3, int i4) {
        mySpinKeyboardButton.setPosition(i, i2, i4, i3, true);
        mySpinKeyboardButton.setTopBottomPadding(this.mButtonPadding);
        this.mButtons.add(mySpinKeyboardButton);
    }

    protected void placeArrowKeys() {
        int width = (int) (((double) this.mRowsRect[0].width()) * 0.1d);
        int i = ((int) ((((double) this.mScreenWidth) * 0.8d) - ((double) (width * 5)))) / 2;
        int i2 = i + (width * 5);
        this.mButtonLeftArrow.setPosition(this.mKeyboardPadding, this.mRowsRect[0].bottom, this.mButtonHeight - this.mKeyboardPadding, i, true);
        this.mButtonRightArrow.setPosition(i2, this.mRowsRect[0].bottom, this.mButtonHeight - this.mKeyboardPadding, ((int) (((double) this.mScreenWidth) * 0.8d)) - i2, true);
    }

    private void m343d() {
        int i = 0;
        this.mRowsRect[0].top = (int) (((float) this.mKeyboardPadding) * 1.5f);
        this.f335v = ((this.mKeyboardHeight - this.mRowsRect[0].top) - this.mKeyboardPadding) / 5;
        this.mRowsRect[0].bottom = this.f335v + this.mRowsRect[0].top;
        this.mButtonHeight = this.f335v - this.mButtonPadding;
        Logger.logDebug(f310a, "MySpinKeyboardBase/calculateMeasure mButtonHeight: " + this.mButtonHeight);
        Logger.logDebug(f310a, "MySpinKeyboardBase/calculateMeasure mButtonPadding: " + this.mButtonPadding);
        while (i < 5) {
            this.mRowsRect[i].left = this.mKeyboardPadding;
            this.mRowsRect[i].right = this.mScreenWidth - this.mKeyboardPadding;
            i++;
        }
        for (i = 1; i < 5; i++) {
            this.mRowsRect[i].top = this.mRowsRect[i - 1].bottom + this.mButtonPadding;
            this.mRowsRect[i].bottom = this.mRowsRect[i - 1].bottom + this.f335v;
        }
    }

    private void m344e() {
        if (this.mButtons != null && !this.mButtons.isEmpty()) {
            Iterator it = this.mButtons.iterator();
            while (it.hasNext()) {
                ((MySpinKeyboardButton) it.next()).setRightPadding(this.mButtonPadding);
            }
        }
    }

    protected void setButtonTextSize() {
        this.mTextSize = TypedValue.applyDimension(2, 24.0f, getResources().getDisplayMetrics());
        if (this.mButtons != null && !this.mButtons.isEmpty()) {
            Iterator it = this.mButtons.iterator();
            while (it.hasNext()) {
                MySpinKeyboardButton mySpinKeyboardButton = (MySpinKeyboardButton) it.next();
                if (!mySpinKeyboardButton.isSpecialKey() || mySpinKeyboardButton.isFlyinButton()) {
                    mySpinKeyboardButton.setTextSize(this.mTextSize);
                } else {
                    mySpinKeyboardButton.setTextSize(this.mTextSize / 1.3f);
                }
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        this.mBackground.draw(canvas);
        if (this.mShowFlyin) {
            this.f328o.draw(canvas);
        }
        this.f326m.fitLabel();
        this.f322i.fitLabel();
        this.f325l.fitLabel();
        prepareDrawing();
        Iterator it = this.mButtons.iterator();
        while (it.hasNext()) {
            ((MySpinKeyboardButton) it.next()).draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        long uptimeMillis = SystemClock.uptimeMillis();
        this.f315b = (int) motionEvent.getX();
        this.f316c = (int) motionEvent.getY();
        this.f317d = motionEvent.getAction();
        Iterator it;
        MySpinKeyboardButton mySpinKeyboardButton;
        switch (this.f317d) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                it = this.mButtons.iterator();
                while (it.hasNext()) {
                    mySpinKeyboardButton = (MySpinKeyboardButton) it.next();
                    if (mySpinKeyboardButton.isPressed(this.f315b, this.f316c)) {
                        if (this.mShowFlyin && !mySpinKeyboardButton.isFlyinButton()) {
                            removeFlyin();
                        }
                        if (!mySpinKeyboardButton.getText().equals("*lang") || this.f327n) {
                            mySpinKeyboardButton.setButtonPressed(true);
                            this.f320g = mySpinKeyboardButton;
                            m336a(this.f320g);
                            invalidate();
                            Logger.logDebug(f310a, "MySpinKeyboardBase/onTouch type: DOWN, time: " + (SystemClock.uptimeMillis() - uptimeMillis));
                            return true;
                        }
                        Logger.logDebug(f310a, "MySpinKeyboardBase/onTouch language button not allowed!");
                        return true;
                    }
                    mySpinKeyboardButton.setButtonPressed(false);
                }
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                m345f();
                if (this.f320g != null && (!this.f320g.getText().equals("*lang") || this.f327n)) {
                    Logger.logDebug(f310a, "Button text: " + this.f320g.getText() + " language button allowed: " + this.f327n);
                    this.f320g.setButtonPressed(false);
                    if (this.f320g.isFlyinButton()) {
                        handleButtonEventFlyin(this.f320g);
                    } else {
                        m340b(this.f320g);
                    }
                    invalidate();
                    Logger.logDebug(f310a, "MySpinKeyboardBase/onTouch type UP, time: " + (SystemClock.uptimeMillis() - uptimeMillis));
                    this.f320g = null;
                    return true;
                }
            case TTSConst.TTSPARAGRAPH /*2*/:
                it = this.mButtons.iterator();
                while (it.hasNext()) {
                    mySpinKeyboardButton = (MySpinKeyboardButton) it.next();
                    if (mySpinKeyboardButton.isPressed(this.f315b, this.f316c) && !mySpinKeyboardButton.equals(this.f320g)) {
                        m345f();
                        if (this.f320g != null) {
                            this.f320g.setButtonPressed(false);
                        }
                        if (!mySpinKeyboardButton.getText().equals("*lang") || this.f327n) {
                            mySpinKeyboardButton.setButtonPressed(true);
                            this.f320g = mySpinKeyboardButton;
                            invalidate();
                        } else {
                            Logger.logDebug(f310a, "MySpinKeyboardBase/onTouch language button not allowed!");
                            return true;
                        }
                    }
                }
                break;
            case TTSConst.TTSUNICODE /*3*/:
                if (this.f320g != null) {
                    this.f320g.setButtonPressed(false);
                }
                invalidate();
                Logger.logDebug(f310a, "MySpinKeyboardBase/onTouch type: CANCEL, time: " + (SystemClock.uptimeMillis() - uptimeMillis));
                return true;
            default:
                Logger.logWarning(f310a, "MySpinKeyboardBase/Unknown event type");
                break;
        }
        return false;
    }

    private void m345f() {
        if (this.f338y) {
            Logger.logDebug(f310a, "MySpinKeyboardBase/MySpinKeyboardBase/Reset long delete.");
            if (this.f312B != null) {
                this.f312B.cancel();
            }
            this.f311A.purge();
            this.f338y = false;
        }
    }

    protected void handleButtonEventFlyin(MySpinKeyboardButton mySpinKeyboardButton) {
        int i = 1;
        if (mySpinKeyboardButton != null && mySpinKeyboardButton.getText() != null) {
            String text = mySpinKeyboardButton.getText();
            Logger.logDebug(f310a, "MySpinKeyboardBase/handleButtonEvent(" + text + ")");
            int selectionStart = this.mEditText.getSelectionStart();
            int selectionEnd = this.mEditText.getSelectionEnd();
            if (!checkForSpecialFunction(text, selectionStart, selectionEnd)) {
                this.f339z.removeCallbacks(this.f314D);
                this.f339z.postDelayed(this.f314D, 5000);
                if (text.equals("*next") || text.equals("*previous")) {
                    selectionStart = this.mBarPos;
                    if (text.equals("*previous")) {
                        i = -1;
                    }
                    this.mBarPos = i + selectionStart;
                    if (this.mBarPos < 0) {
                        this.mBarPos = 0;
                    }
                    if (this.mBarPos > this.f337x.length() - 5) {
                        this.mBarPos = this.f337x.length() - 5;
                    }
                    removeFlyin();
                    addFlyin();
                    return;
                }
                if (":;,?!".contains(text) && (this.mType == 1001 || this.mType == 1002 || this.mType == 1003)) {
                    this.mEditText.setText(new StringBuffer(this.mEditText.getText().toString()).replace(selectionStart - 1, selectionEnd, text.substring(0, 1).concat(" ")).toString());
                    this.mEditText.setSelection(selectionEnd);
                } else {
                    this.mEditText.setText(new StringBuffer(this.mEditText.getText().toString()).replace(selectionStart - 1, selectionEnd, text.substring(0, 1)).toString());
                    this.mEditText.setSelection(selectionEnd);
                    if (this.mType == 1002) {
                        setType(1001);
                    }
                }
                removeFlyin();
            }
        }
    }

    private void m336a(MySpinKeyboardButton mySpinKeyboardButton) {
        if (mySpinKeyboardButton != null && mySpinKeyboardButton.getText() != null && mySpinKeyboardButton.getText().equals("*del")) {
            Logger.logDebug(f310a, "MySpinKeyboardBase/Delete button pressed.");
            if (this.f317d == 0 && !this.f338y) {
                this.f338y = true;
                this.f312B = new C0218a();
                this.f311A.scheduleAtFixedRate(this.f312B, 1500, 300);
            }
            m346g();
        }
    }

    private void m340b(MySpinKeyboardButton mySpinKeyboardButton) {
        if (mySpinKeyboardButton != null && mySpinKeyboardButton.getText() != null) {
            String text = mySpinKeyboardButton.getText();
            if (text.equals("*lang")) {
                languageButtonPressed();
                return;
            }
            int selectionStart = this.mEditText.getSelectionStart();
            int selectionEnd = this.mEditText.getSelectionEnd();
            if (text.equals("*hide")) {
                KeyboardRegister.getInstance().onHideRequest();
            } else if (checkForSpecialFunction(text, selectionStart, selectionEnd)) {
                invalidate();
            } else if (text.equals(this.f331r)) {
                int imeOptions = this.mEditText.getImeOptions();
                if (imeOptions == 0 || imeOptions == 1) {
                    this.mEditText.setText(new StringBuffer(this.mEditText.getText().toString()).replace(selectionStart, selectionEnd, "\n"));
                    this.mEditText.setSelection(selectionStart + 1);
                } else {
                    this.mEditText.onEditorAction(imeOptions);
                }
                if (imeOptions == 6 || imeOptions == 3) {
                    KeyboardRegister.getInstance().onHideRequest();
                }
            } else if (text.equals("*shift")) {
                if (this.mType == 1001) {
                    setType(1002);
                } else if (this.mType == 1002) {
                    setType(1003);
                } else {
                    setType(1001);
                }
            } else if (text.equals(this.f330q)) {
                this.mEditText.setText(new StringBuffer(this.mEditText.getText().toString()).replace(selectionStart, selectionEnd, " ").toString());
                this.mEditText.setSelection(selectionStart + 1);
            } else if (text.equals("?!&\n123")) {
                this.f332s = getLabel("*abc");
                if (this.f332s == null || this.f332s.isEmpty()) {
                    this.f332s = "ABC";
                }
                this.f326m.setText(this.f332s);
                this.f323j.setText("*123alt");
                setType(1004);
            } else if (text.equals(this.f332s)) {
                this.f326m.setText("?!&\n123");
                this.f323j.setText("*shift");
                setType(1002);
            } else if (text.equals("*123alt")) {
                if (this.mType == 1004) {
                    setType(1005);
                } else if (this.mType == 1005) {
                    setType(1004);
                }
            } else if (!text.startsWith("*") || text.length() <= 1) {
                selectionStart = this.mEditText.getSelectionStart();
                selectionEnd = this.mEditText.getSelectionEnd();
                if (text.length() > 1) {
                    this.f337x = text.substring(1);
                    this.mBarPos = 0;
                    addFlyin();
                }
                this.mEditText.setText(new StringBuffer(this.mEditText.getText().toString()).replace(selectionStart, selectionEnd, text.substring(0, 1)).toString());
                this.mEditText.setSelection(selectionStart + 1);
                if (this.mType == 1002) {
                    setType(1001);
                }
            }
        }
    }

    public void show() {
        setVisibility(0);
    }

    public void hide() {
        resetEventStates();
        setVisibility(8);
    }

    public void setEditText(EditText editText) {
        this.mEditText = editText;
        this.f322i.setText(getImeText(editText.getImeOptions()));
        this.f331r = getImeText(editText.getImeOptions());
    }

    public void setType(int i) {
        if (i != this.mType) {
            this.mType = i;
            setButtonIcon(this.f323j, this.mType);
            setButtonIcon(this.f326m, this.mType);
            generateKeyboardLayout();
        } else {
            this.f326m.setText("?!&\n123");
            setButtonIcon(this.f326m, this.mType);
            this.f323j.setText("*shift");
            setButtonIcon(this.f323j, this.mType);
        }
        invalidate();
    }

    public int getType() {
        return this.mType;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            this.f333t = getWidth();
            this.f334u = getHeight();
            this.mBackground.setBounds(0, 0, this.f333t, this.f334u);
            this.f328o.setBounds(0, 0, this.f333t, this.f334u);
            if (getVisibility() == 4) {
                setVisibility(8);
            }
        }
    }

    private void m346g() {
        int selectionStart = this.mEditText.getSelectionStart();
        int selectionEnd = this.mEditText.getSelectionEnd();
        if (selectionStart > 0 || selectionEnd > 0) {
            if (selectionStart < 0) {
                selectionStart = 0;
            }
            if (!checkForSpecialDelete(selectionStart, selectionEnd)) {
                selectionStart = this.mEditText.getSelectionStart();
                selectionEnd = this.mEditText.getSelectionEnd();
                if (selectionStart > 0 || selectionEnd > 0) {
                    if (selectionStart == selectionEnd) {
                        selectionStart--;
                    }
                    this.mEditText.setText(new StringBuffer(this.mEditText.getText().toString()).replace(selectionStart, selectionEnd, "").toString());
                    this.mEditText.setSelection(selectionStart);
                    if (selectionStart == 0 && this.mType == 1001) {
                        setType(1002);
                    }
                }
            }
        }
    }

    protected void addFlyin() {
        if (!this.mShowFlyin) {
            int i;
            int width = (int) (((double) this.mRowsRect[0].width()) * 0.1d);
            int length = this.f337x.length();
            this.mShowFlyin = true;
            if (length > 7) {
                setButtonIcon(this.mButtonLeftArrow, this.mBarPos == 0 ? 0 : 1);
                MySpinKeyboardButton mySpinKeyboardButton = this.mButtonRightArrow;
                if (this.mBarPos == this.f337x.length() - 5) {
                    length = 0;
                } else {
                    length = 1;
                }
                setButtonIcon(mySpinKeyboardButton, length);
                this.mButtons.add(this.mButtonLeftArrow);
                this.mButtons.add(this.mButtonRightArrow);
                i = 5;
            } else {
                i = length;
            }
            int i2 = (int) (((float) this.mButtonLeftArrow.getPosition().right) + ((((float) (5 - i)) * this.f336w) / 2.0f));
            for (int i3 = 0; i3 < i; i3++) {
                this.mButtonFlyin = new MySpinKeyboardButton(this.mKeyboardTypeface);
                this.mButtonFlyin.setBackground(this.mBtnBackgroundFlyin.getConstantState().newDrawable());
                this.mButtonFlyin.setBackgroundPressed(this.mBtnBackgroundFlyinPressed);
                this.mButtonFlyin.setText(this.f337x.substring(this.mBarPos + i3, (this.mBarPos + i3) + 1));
                this.mButtonFlyin.setTextSize(this.mTextSize);
                this.mButtonFlyin.setFlyinButton(true);
                this.mButtonFlyin.setPosition((width * i3) + i2, this.mRowsRect[0].bottom, this.mButtonHeight, width, true);
                this.mButtons.add(this.mButtonFlyin);
            }
            this.f339z.postDelayed(this.f314D, 5000);
        }
    }

    protected void removeFlyin() {
        this.f339z.removeCallbacks(this.f314D);
        this.mShowFlyin = false;
        Collection arrayList = new ArrayList();
        Iterator it = this.mButtons.iterator();
        while (it.hasNext()) {
            MySpinKeyboardButton mySpinKeyboardButton = (MySpinKeyboardButton) it.next();
            if (mySpinKeyboardButton.isFlyinButton()) {
                arrayList.add(mySpinKeyboardButton);
            }
        }
        this.mButtons.removeAll(arrayList);
    }

    public void resetEventStates() {
        this.f315b = -1;
        this.f316c = -1;
        this.f317d = -1;
    }
}
