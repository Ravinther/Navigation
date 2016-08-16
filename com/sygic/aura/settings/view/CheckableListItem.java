package com.sygic.aura.settings.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sygic.aura.resources.FontDrawable;

public class CheckableListItem extends LinearLayout implements Checkable {
    private boolean mChecked;
    private ImageView mIcon;
    private Drawable mIconInstalled;

    public CheckableListItem(Context context) {
        super(context);
        this.mChecked = false;
        init(context);
    }

    public CheckableListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mChecked = false;
        init(context);
    }

    @TargetApi(11)
    public CheckableListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mChecked = false;
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            this.mIconInstalled = FontDrawable.inflate(context.getResources(), 2131034198);
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mIcon = (ImageView) findViewById(2131624480);
    }

    public void setChecked(boolean checked) {
        this.mChecked = checked;
        this.mIcon.setImageDrawable(checked ? this.mIconInstalled : null);
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public void toggle() {
        setChecked(!this.mChecked);
    }
}
