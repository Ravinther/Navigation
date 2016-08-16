package com.sygic.aura.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.sygic.aura.C1090R;

public class LazyImageView extends FrameLayout {
    private ImageView thumbnail;

    public LazyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addViews(context, attrs);
    }

    private void addViews(Context context, AttributeSet attrs) {
        View insideView = LayoutInflater.from(context).inflate(2130903177, this, false);
        this.thumbnail = (ImageView) insideView.findViewById(2131624407);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.LazyImageView);
            int index = a.getInt(0, -1);
            if (index >= 0 && this.thumbnail != null) {
                this.thumbnail.setScaleType(ScaleType.values()[index]);
            }
            a.recycle();
        }
        addView(insideView, new LayoutParams(-1, -1));
    }

    public void setImageBitmap(Bitmap thumbnailBitmap) {
        this.thumbnail.setImageBitmap(thumbnailBitmap);
    }

    public Drawable getDrawable() {
        return this.thumbnail.getDrawable();
    }
}
