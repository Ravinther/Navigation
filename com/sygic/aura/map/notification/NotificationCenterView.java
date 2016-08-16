package com.sygic.aura.map.notification;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.sygic.aura.C1090R;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.interfaces.NotifyCenterListener;
import com.sygic.aura.map.notification.data.NotificationCenterItem;
import com.sygic.aura.map.view.RouteNotificationView;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import loquendo.tts.engine.TTSConst;

public class NotificationCenterView extends LinearLayout implements NotifyCenterListener {
    private int mAcceptsMask;
    private final Queue<View>[] mCachedViews;
    private boolean mIsActive;
    private int mMaxItemsCount;
    private int mOrientationIndex;

    public NotificationCenterView(Context context) {
        super(context);
        this.mIsActive = false;
        this.mCachedViews = new Queue[2];
    }

    public NotificationCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsActive = false;
        this.mCachedViews = new Queue[2];
        init(context, attrs, 0, 0);
    }

    public NotificationCenterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIsActive = false;
        this.mCachedViews = new Queue[2];
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public NotificationCenterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mIsActive = false;
        this.mCachedViews = new Queue[2];
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.NotificationCenterView, defStyleAttr, defStyleRes);
        this.mAcceptsMask = a.getInt(0, 255);
        a.recycle();
        this.mOrientationIndex = getIndexFromOrientation(getResources().getConfiguration());
        this.mCachedViews[0] = new ArrayDeque();
        this.mCachedViews[1] = new ArrayDeque();
    }

    public int getMaxItemsCount() {
        return this.mMaxItemsCount;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            int size;
            int dimen;
            switch (getOrientation()) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    size = getResources().getDimensionPixelSize(2131230738);
                    dimen = getWidth();
                    break;
                case TTSConst.TTSMULTILINE /*1*/:
                    size = getResources().getDimensionPixelSize(2131230736);
                    dimen = getHeight();
                    break;
                default:
                    CrashlyticsHelper.logWarning("NotificationCenterView", "unsuported Notification view orientation!");
                    this.mMaxItemsCount = 0;
                    return;
            }
            int oldCount = this.mMaxItemsCount;
            this.mMaxItemsCount = (dimen / size) - 1;
            if (oldCount != this.mMaxItemsCount) {
                onNotifyCenterChange(Integer.valueOf(this.mAcceptsMask));
            }
        }
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        for (int i = 0; i < getChildCount(); i++) {
            this.mCachedViews[this.mOrientationIndex].offer(getChildAt(i));
        }
        removeAllViews();
        this.mOrientationIndex = getIndexFromOrientation(newConfig);
        onNotifyCenterChange(Integer.valueOf(this.mAcceptsMask));
    }

    private int getIndexFromOrientation(Configuration config) {
        return config.orientation == 2 ? 1 : 0;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MapEventsReceiver.registerNotificationCenterListener(this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MapEventsReceiver.unregisterNotificationCenterListener(this);
    }

    public void setActive(boolean active) {
        this.mIsActive = active;
        if (active) {
            onNotifyCenterChange(Integer.valueOf(this.mAcceptsMask));
        }
    }

    public void onNotifyCenterChange(Integer mask) {
        if (this.mIsActive) {
            int changedMask = this.mAcceptsMask & mask.intValue();
            if (changedMask != 0) {
                int i;
                NotificationCenterItem item;
                for (i = getChildCount(); i > this.mMaxItemsCount; i--) {
                    removeNotificationItem(i - 1);
                }
                List<Integer> notificationsShown = new ArrayList();
                List<NotificationCenterItem> notifications = new ArrayList(Arrays.asList(NotificationManager.nativeGetNotifications(changedMask)));
                List<Integer> notificationTypes = new ArrayList();
                i = 0;
                while (changedMask > 0 && i < 15) {
                    if ((changedMask & 1) == 1) {
                        notificationTypes.add(Integer.valueOf(i));
                    }
                    changedMask >>= 1;
                    i++;
                }
                if (VERSION.SDK_INT >= 19) {
                    TransitionManager.beginDelayedTransition(this, new AutoTransition().excludeTarget(2131624462, true));
                }
                i = 0;
                while (i < getChildCount()) {
                    View child = (RouteNotificationView) getChildAt(i);
                    item = (NotificationCenterItem) child.getTag();
                    if (notificationTypes.contains(Integer.valueOf(item.getNotificationType()))) {
                        int index = notifications.indexOf(item);
                        if (index >= 0) {
                            child.updateContent((NotificationCenterItem) notifications.remove(index));
                            notificationsShown.add(Integer.valueOf(item.getId()));
                        } else {
                            i--;
                            removeNotificationItem(child);
                        }
                    } else {
                        notificationsShown.add(Integer.valueOf(item.getId()));
                    }
                    i++;
                }
                for (i = 0; i < notifications.size(); i++) {
                    item = (NotificationCenterItem) notifications.get(i);
                    if (item != null) {
                        RouteNotificationView notificationView = (RouteNotificationView) this.mCachedViews[this.mOrientationIndex].poll();
                        if (notificationView == null) {
                            notificationView = (RouteNotificationView) LayoutInflater.from(getContext()).inflate(2130903194, this, false);
                            if (InCarConnection.isInCarConnected()) {
                                notificationView.getLayoutParams().width = InCarConnection.updateViewDrawingDimension(notificationView.getLayoutParams().width);
                                notificationView.getLayoutParams().height = InCarConnection.updateViewDrawingDimension(notificationView.getLayoutParams().height);
                            }
                        }
                        notificationView.updateContent(item);
                        if (item.addViewToHierarchy(this, notificationView)) {
                            notificationsShown.add(Integer.valueOf(item.getId()));
                        }
                    }
                }
                int[] array = new int[notificationsShown.size()];
                for (i = 0; i < notificationsShown.size(); i++) {
                    array[i] = ((Integer) notificationsShown.get(i)).intValue();
                }
                NotificationManager.nativeSetDisplayedNotifications(array);
            }
        }
    }

    public void removeNotificationItem(int position) {
        removeNotificationItem(getChildAt(position));
    }

    public void removeNotificationItem(View child) {
        if (child != null) {
            removeView(child);
            this.mCachedViews[this.mOrientationIndex].offer(child);
        }
    }

    public void setAcceptance(int acceptance) {
        this.mAcceptsMask = acceptance;
    }

    public void addAcceptance(int acceptance) {
        this.mAcceptsMask |= acceptance;
        onNotifyCenterChange(Integer.valueOf(this.mAcceptsMask));
    }

    public void removeAcceptance(int acceptance) {
        if ((this.mAcceptsMask & acceptance) == acceptance) {
            this.mAcceptsMask ^= acceptance;
            List<Integer> notificationTypes = new ArrayList();
            int i = 0;
            while (acceptance > 0 && i < 15) {
                if ((acceptance & 1) == 1) {
                    notificationTypes.add(Integer.valueOf(i));
                }
                acceptance >>= 1;
                i++;
            }
            i = 0;
            while (i < getChildCount()) {
                View child = (RouteNotificationView) getChildAt(i);
                if (notificationTypes.contains(Integer.valueOf(((NotificationCenterItem) child.getTag()).getNotificationType()))) {
                    i--;
                    removeNotificationItem(child);
                }
                i++;
            }
        }
    }
}
