package com.sygic.aura.map.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.sygic.aura.C1090R;
import com.sygic.aura.helper.interfaces.RouteBubbleMoveListener;
import com.sygic.aura.map.data.BubbleBaseInfo;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@TargetApi(16)
public class BubbleView extends LinearLayout {
    private static RunnableHandler mHandler;
    private boolean isNewerOS;
    private int mBubbleHeight;
    private ImageView mBubbleImage;
    private TextView mBubbleText;
    private int mBubbleWidth;
    private DisplayMetrics mDisplaymetrics;
    private boolean mIsAlreadyMeasured;
    private transient MoveRunnable mMoveRunnable;
    private final List<RouteBubbleMoveListener> mRouteBubbleMoveListeners;
    private LayoutParams params;

    /* renamed from: com.sygic.aura.map.view.BubbleView.1 */
    class C13611 implements Runnable {
        C13611() {
        }

        public void run() {
            BubbleView.this.moveBubble();
        }
    }

    private class MoveRunnable implements Runnable {
        private int mPosX;
        private int mPosY;

        private MoveRunnable() {
        }

        MoveRunnable setDims(int posX, int posY) {
            this.mPosX = posX;
            this.mPosY = posY;
            return this;
        }

        public void run() {
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BubbleView.this.setOnMoveParams(this.mPosX, this.mPosY);
        }
    }

    private static class RunnableHandler extends Handler {
        private final SoftReference<BubbleView> mbubbleView;

        RunnableHandler(BubbleView bubble) {
            this.mbubbleView = new SoftReference(bubble);
        }

        public void handleMessage(Message message) {
            ((BubbleView) this.mbubbleView.get()).setOnMoveParams(message.arg1, message.arg2);
        }
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMoveRunnable = new MoveRunnable();
        this.mRouteBubbleMoveListeners = Collections.synchronizedList(new LinkedList());
        init(context, attrs);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mMoveRunnable = new MoveRunnable();
        this.mRouteBubbleMoveListeners = Collections.synchronizedList(new LinkedList());
        init(context, attrs);
    }

    public void setText(CharSequence text) {
        this.mBubbleText.setText(text);
    }

    public CharSequence getText() {
        return this.mBubbleText.getText();
    }

    public void setTextColor(int color) {
        this.mBubbleText.setTextColor(color);
    }

    public void setImage(Drawable drawable) {
        this.mBubbleImage.setImageDrawable(drawable);
    }

    public void setupImageVisibility() {
        this.mBubbleImage.setVisibility(this.mBubbleImage.getDrawable() != null ? 0 : 8);
    }

    public void setImageClickListener(OnClickListener listener) {
        this.mBubbleImage.setOnClickListener(listener);
    }

    private void init(Context context, AttributeSet attrs) {
        boolean z;
        if (VERSION.SDK_INT > 10) {
            z = true;
        } else {
            z = false;
        }
        this.isNewerOS = z;
        if (!this.isNewerOS) {
            mHandler = new RunnableHandler(this);
        }
        setOrientation(0);
        setGravity(16);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        TypedArray ta = getResources().obtainAttributes(attrs, C1090R.styleable.BubbleView);
        inflater.inflate(ta.getResourceId(0, 2130903077), this, true);
        ta.recycle();
        this.mBubbleImage = (ImageView) findViewById(2131624132);
        this.mBubbleText = (TextView) findViewById(2131624133);
        this.mDisplaymetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getMetrics(this.mDisplaymetrics);
    }

    public void moveBubble() {
        BubbleBaseInfo bubbleInfo = (BubbleBaseInfo) getTag();
        if (bubbleInfo != null) {
            bubbleInfo.remapCoords();
            if (this.isNewerOS) {
                setOnMoveParams(bubbleInfo.getX(), bubbleInfo.getY());
                return;
            }
            this.mMoveRunnable.setDims(bubbleInfo.getX(), bubbleInfo.getY());
            mHandler.postAtFrontOfQueue(this.mMoveRunnable);
        }
    }

    private final void setOnMoveParams(int posX, int posY) {
        if (this.params == null) {
            this.params = (LayoutParams) getLayoutParams();
            int specWidth = MeasureSpec.makeMeasureSpec(0, 0);
            measure(specWidth, specWidth);
            this.mBubbleWidth = getMeasuredWidth();
            this.mBubbleHeight = getMeasuredHeight();
            this.params.width = this.mBubbleWidth;
            this.params.height = this.mBubbleHeight;
        }
        int marginLeft = posX - (this.mBubbleWidth / 2);
        int marginTop = posY - this.mBubbleHeight;
        if (this.isNewerOS) {
            setX((float) marginLeft);
            setY((float) marginTop);
        }
        if (!this.isNewerOS) {
            this.params.setMargins(marginLeft, marginTop, -this.params.width, -this.params.height);
            setLayoutParams(this.params);
        }
    }

    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        measure(MeasureSpec.makeMeasureSpec(0, 0), 0);
        if (getMeasuredWidth() > this.mDisplaymetrics.widthPixels) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBubbleText.getLayoutParams();
            layoutParams.width = width - this.mBubbleImage.getMeasuredWidth();
            this.mBubbleText.setLayoutParams(layoutParams);
        }
        if (!this.mIsAlreadyMeasured) {
            this.mIsAlreadyMeasured = true;
            this.mBubbleWidth = width;
            this.mBubbleHeight = height;
            if (this.isNewerOS) {
                moveBubble();
            } else {
                post(new C13611());
            }
            for (RouteBubbleMoveListener listener : this.mRouteBubbleMoveListeners) {
                listener.onRouteBubbleMoved();
            }
        }
    }

    public boolean registerRouteBubbleMoveListener(RouteBubbleMoveListener bubbleMoved) {
        return this.mRouteBubbleMoveListeners.add(bubbleMoved);
    }

    public boolean unregisterRouteBubbleMoveListener(RouteBubbleMoveListener bubbleMoved) {
        return this.mRouteBubbleMoveListeners.remove(bubbleMoved);
    }
}
