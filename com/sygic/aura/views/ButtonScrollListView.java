package com.sygic.aura.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.sygic.aura.views.font_specials.SImageButton;

public class ButtonScrollListView extends ListView {
    private SImageButton mButtonDown;
    private SImageButton mButtonUp;

    /* renamed from: com.sygic.aura.views.ButtonScrollListView.1 */
    class C17761 implements OnClickListener {
        C17761() {
        }

        public void onClick(View v) {
            ButtonScrollListView.this.scrollButtonUpClick(v);
        }
    }

    /* renamed from: com.sygic.aura.views.ButtonScrollListView.2 */
    class C17772 implements OnClickListener {
        C17772() {
        }

        public void onClick(View v) {
            ButtonScrollListView.this.scrollButtonDownClick(v);
        }
    }

    public ButtonScrollListView(Context context) {
        super(context);
    }

    public ButtonScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void scrollButtonUpClick(View v) {
        smoothScrollBy(-getHeight(), 50);
    }

    public void scrollButtonDownClick(View v) {
        smoothScrollBy(getHeight(), 50);
    }

    public void initButtonScroll(View view, int idButtonUp, int idButtonDown) {
        this.mButtonUp = (SImageButton) view.findViewById(idButtonUp);
        if (this.mButtonUp != null) {
            this.mButtonUp.setOnClickListener(new C17761());
        }
        this.mButtonDown = (SImageButton) view.findViewById(idButtonDown);
        if (this.mButtonDown != null) {
            this.mButtonDown.setOnClickListener(new C17772());
        }
    }

    public void showScrollButtons(boolean visible) {
        int i = 0;
        if (this.mButtonUp != null) {
            this.mButtonUp.setVisibility(visible ? 0 : 8);
        }
        if (this.mButtonDown != null) {
            SImageButton sImageButton = this.mButtonDown;
            if (!visible) {
                i = 8;
            }
            sImageButton.setVisibility(i);
        }
    }
}
