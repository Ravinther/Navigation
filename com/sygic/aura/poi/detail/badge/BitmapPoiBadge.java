package com.sygic.aura.poi.detail.badge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.views.font_specials.SImageView;

public class BitmapPoiBadge extends StarsPoiBadge {
    private final String mBitmapPath;

    public BitmapPoiBadge(String title, String url, int rating, int ratingMax, String bitmapPath) {
        super(title, url, rating, ratingMax, 0, 2131034227);
        this.mBitmapPath = bitmapPath;
    }

    public void onViewCreated(View view, ViewGroup container) {
        super.onViewCreated(view, container);
        if (this.mBitmapPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(this.mBitmapPath);
            if (bitmap != null) {
                ((SImageView) view.findViewById(2131624208)).setImageBitmap(bitmap);
            }
        }
    }
}
