package com.sygic.aura.sos.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;

public class SosSectionView extends LinearLayout {
    private SImageView mImgIcon;
    private SImageView mImgNext;
    private STextView mTxtDistance;
    private STextView mTxtLabel;

    public SosSectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SosSectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public SosSectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(2130903210, this, true);
        this.mImgIcon = (SImageView) findViewById(2131624489);
        this.mTxtLabel = (STextView) findViewById(2131624490);
        this.mTxtDistance = (STextView) findViewById(2131624491);
        this.mImgNext = (SImageView) findViewById(2131624492);
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.SosSectionView);
        if (ta != null) {
            int textSrcId = ta.getResourceId(1, -1);
            int iconId = ta.getResourceId(0, -1);
            ta.recycle();
            this.mImgIcon.setFontDrawableResource(iconId);
            this.mTxtLabel.setCoreText(textSrcId);
        }
    }

    public void setDistance(int distanceInMeters) {
        this.mTxtDistance.setText(ResourceManager.nativeFormatDistance((long) distanceInMeters));
    }

    public void setEnabled(boolean enable) {
        super.setEnabled(enable);
        int newColor = getResources().getColor(enable ? 2131558753 : 2131558754);
        this.mImgIcon.setFontDrawableColor(newColor);
        this.mTxtLabel.setEnabled(enable);
        this.mImgNext.setFontDrawableColor(newColor);
    }
}
