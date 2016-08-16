package com.sygic.aura.views.popup;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class PopupAction extends PopupWindowWrapper {
    private final List<ActionItem> actionItems;
    public boolean isDismissed;
    private int mAnimStyle;
    private int mChildPos;
    private boolean mDidAction;
    private long mDismissDelay;
    private final Handler mDismissHandler;
    private Runnable mDismissRunnable;
    private final LayoutInflater mInflater;
    private int mInsertPos;
    private OnActionItemClickListener mItemClickListener;
    private final int mOrientation;
    private View mRootView;
    private ScrollView mScroller;
    private ViewGroup mTrack;
    private int rootWidth;

    /* renamed from: com.sygic.aura.views.popup.PopupAction.1 */
    class C17961 implements OnClickListener {
        final /* synthetic */ int val$actionId;
        final /* synthetic */ int val$pos;

        C17961(int i, int i2) {
            this.val$pos = i;
            this.val$actionId = i2;
        }

        public void onClick(View v) {
            if (PopupAction.this.mItemClickListener != null) {
                PopupAction.this.mItemClickListener.onItemClick(PopupAction.this, this.val$pos, this.val$actionId);
            }
            if (!PopupAction.this.getActionItem(this.val$pos).isSticky()) {
                PopupAction.this.mDidAction = true;
                PopupAction.this.dismiss();
            }
        }
    }

    /* renamed from: com.sygic.aura.views.popup.PopupAction.2 */
    class C17972 implements Runnable {
        C17972() {
        }

        public void run() {
            PopupAction.this.dismiss();
        }
    }

    public interface OnActionItemClickListener {
        void onItemClick(PopupAction popupAction, int i, int i2);
    }

    public PopupAction(Context context, int orientation) {
        super(context);
        this.actionItems = new ArrayList();
        this.isDismissed = false;
        this.mDismissHandler = new Handler();
        this.mDismissDelay = 0;
        this.rootWidth = 0;
        this.mOrientation = orientation;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        if (this.mOrientation == 0) {
            setRootViewId(2130903258);
        } else {
            setRootViewId(2130903260);
        }
        this.mAnimStyle = 5;
        this.mChildPos = 0;
    }

    public ActionItem getActionItem(int index) {
        return (ActionItem) this.actionItems.get(index);
    }

    public void setRootViewId(int id) {
        this.mRootView = this.mInflater.inflate(id, null);
        this.mTrack = (ViewGroup) this.mRootView.findViewById(2131624556);
        this.mScroller = (ScrollView) this.mRootView.findViewById(2131624555);
        setContentView(this.mRootView);
    }

    public void setAnimStyle(int mAnimStyle) {
        this.mAnimStyle = mAnimStyle;
    }

    public void addActionItem(ActionItem action) {
        View container;
        this.actionItems.add(action);
        String title = action.getTitle();
        Drawable icon = action.getIcon();
        if (this.mOrientation == 0) {
            container = this.mInflater.inflate(2130903256, this.mTrack, false);
        } else {
            container = this.mInflater.inflate(2130903257, this.mTrack, false);
        }
        ImageView img = (ImageView) container.findViewById(2131624553);
        TextView text = (TextView) container.findViewById(2131624554);
        if (icon != null) {
            img.setImageDrawable(icon);
        }
        if (title != null) {
            text.setText(title);
        } else {
            text.setVisibility(8);
        }
        container.setOnClickListener(new C17961(this.mChildPos, action.getActionId()));
        container.setFocusable(true);
        container.setClickable(true);
        this.mTrack.addView(container, this.mInsertPos);
        this.mChildPos++;
        this.mInsertPos++;
    }

    protected void onShow() {
        super.onShow();
        this.mDismissHandler.postDelayed(this.mDismissRunnable, this.mDismissDelay);
    }

    public void show(View anchor) {
        int xPos;
        int yPos;
        preShow();
        this.mDidAction = false;
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] + anchor.getHeight());
        this.mRootView.measure(-2, -2);
        int rootHeight = this.mRootView.getMeasuredHeight();
        if (this.rootWidth == 0) {
            this.rootWidth = this.mRootView.getMeasuredWidth();
        }
        int screenWidth = this.mWindowManager.getDefaultDisplay().getWidth();
        int screenHeight = this.mWindowManager.getDefaultDisplay().getHeight();
        if (anchorRect.left + this.rootWidth > screenWidth) {
            xPos = anchorRect.left - (this.rootWidth - anchor.getWidth());
            if (xPos < 0) {
                xPos = 0;
            }
        } else if (anchor.getWidth() > this.rootWidth) {
            xPos = anchorRect.centerX() - (this.rootWidth / 2);
        } else {
            xPos = anchorRect.left;
        }
        int dyTop = anchorRect.top;
        int dyBottom = screenHeight - anchorRect.bottom;
        boolean onTop = dyTop > dyBottom;
        if (!onTop) {
            yPos = anchorRect.bottom + 20;
            if (rootHeight > dyBottom) {
                this.mScroller.getLayoutParams().height = dyBottom;
            }
        } else if (rootHeight > dyTop) {
            yPos = 15;
            this.mScroller.getLayoutParams().height = dyTop - anchor.getHeight();
        } else {
            yPos = (anchorRect.top - rootHeight) - 20;
        }
        setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);
        this.mWindow.showAtLocation(anchor, 0, xPos, yPos);
    }

    private void setAnimationStyle(int screenWidth, int requestedX, boolean onTop) {
        int i = 2131296394;
        int i2 = 2131296393;
        int i3 = 2131296391;
        PopupWindow popupWindow;
        switch (this.mAnimStyle) {
            case TTSConst.TTSMULTILINE /*1*/:
                popupWindow = this.mWindow;
                if (!onTop) {
                    i = 2131296389;
                }
                popupWindow.setAnimationStyle(i);
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mWindow.setAnimationStyle(onTop ? 2131296396 : 2131296391);
            case TTSConst.TTSUNICODE /*3*/:
                this.mWindow.setAnimationStyle(onTop ? 2131296393 : 2131296388);
            case TTSConst.TTSXML /*4*/:
                this.mWindow.setAnimationStyle(onTop ? 2131296395 : 2131296390);
            case TTSConst.TTSEVT_TEXT /*5*/:
                if (requestedX <= screenWidth / 4) {
                    popupWindow = this.mWindow;
                    if (!onTop) {
                        i = 2131296389;
                    }
                    popupWindow.setAnimationStyle(i);
                } else if (requestedX <= screenWidth / 4 || requestedX >= (screenWidth / 4) * 3) {
                    r0 = this.mWindow;
                    if (onTop) {
                        i3 = 2131296396;
                    }
                    r0.setAnimationStyle(i3);
                } else {
                    r0 = this.mWindow;
                    if (!onTop) {
                        i2 = 2131296388;
                    }
                    r0.setAnimationStyle(i2);
                }
            default:
        }
    }

    public void onDismiss() {
        this.mDismissHandler.removeCallbacks(this.mDismissRunnable);
        if (!this.mDidAction || this.mItemClickListener == null) {
            this.isDismissed = true;
            super.onDismiss();
        }
    }

    public void setDismissTimer(long millis) {
        this.mDismissRunnable = new C17972();
        this.mDismissDelay = millis;
    }
}
