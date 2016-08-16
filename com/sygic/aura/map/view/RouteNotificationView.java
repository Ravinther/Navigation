package com.sygic.aura.map.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.sygic.aura.map.notification.data.NotificationCenterItem;
import com.sygic.aura.quickmenu.QuickMenuTimer;
import com.sygic.aura.views.font_specials.STextView;

public class RouteNotificationView extends LinearLayout {
    protected STextView mIconView;
    protected STextView mTextView;

    /* renamed from: com.sygic.aura.map.view.RouteNotificationView.1 */
    class C13711 implements OnClickListener {
        final /* synthetic */ View val$parent;

        C13711(View view) {
            this.val$parent = view;
        }

        public void onClick(View v) {
            QuickMenuTimer timer = (QuickMenuTimer) this.val$parent.getTag(2131624427);
            if (!(timer == null || timer.isFinished())) {
                timer.reset();
            }
            ((NotificationCenterItem) RouteNotificationView.this.getTag()).onClick(RouteNotificationView.this.getContext());
        }
    }

    public RouteNotificationView(Context context) {
        super(context);
    }

    public RouteNotificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RouteNotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public RouteNotificationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mIconView = (STextView) findViewById(2131624461);
        this.mTextView = (STextView) findViewById(2131624462);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnClickListener(new C13711((View) getParent()));
    }

    public void updateContent(NotificationCenterItem item) {
        setTag(item);
        String primaryText = item.getPrimaryText();
        if (TextUtils.isEmpty(primaryText)) {
            this.mIconView.setCoreText(2131166196);
        } else {
            this.mIconView.setText(primaryText);
        }
        this.mTextView.setText(item.getSecondaryText());
        setEnabled(item.isClickable());
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        dispatchEnabled(enabled);
    }

    private void dispatchEnabled(boolean enabled) {
        this.mIconView.setEnabled(enabled);
        this.mTextView.setEnabled(enabled);
    }
}
