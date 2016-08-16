package com.sygic.widget.helpers;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class DrawableHelper {
    public static Bitmap getBitmap(Drawable drawable) {
        Rect bounds = drawable.getBounds();
        Bitmap bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
