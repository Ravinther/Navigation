package com.sygic.aura.settings.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.lang.reflect.Field;

public class RoundedImageView extends ImageView {
    private final Paint mPaint;
    private boolean mRounded;

    public RoundedImageView(Context context) {
        super(context);
        this.mRounded = false;
        this.mPaint = initPaint();
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRounded = false;
        this.mPaint = initPaint();
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mRounded = false;
        this.mPaint = initPaint();
    }

    private Paint initPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(getResources().getColor(2131558645));
        return paint;
    }

    protected void onDraw(Canvas canvas) {
        if (!this.mRounded) {
            Drawable drawable = getBackground();
            if (drawable != null && getWidth() != 0 && getHeight() != 0) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                int w = getWidth();
                try {
                    Field bitmapField = drawable.getClass().getDeclaredField("mBitmap");
                    bitmapField.setAccessible(true);
                    bitmapField.set(drawable, getCroppedBitmap(bitmap, w, this.mPaint));
                    this.mRounded = true;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    return;
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            return;
        }
        super.onDraw(canvas);
    }

    private static Bitmap getCroppedBitmap(Bitmap bmp, int radius, Paint paint) {
        Bitmap sbmp;
        if (bmp.getWidth() == radius && bmp.getHeight() == radius) {
            sbmp = bmp;
        } else {
            float factor = ((float) Math.min(bmp.getWidth(), bmp.getHeight())) / ((float) radius);
            sbmp = Bitmap.createScaledBitmap(bmp, (int) (((float) bmp.getWidth()) / factor), (int) (((float) bmp.getHeight()) / factor), false);
        }
        Bitmap output = Bitmap.createBitmap(radius, radius, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Rect rect = new Rect(0, 0, radius, radius);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(((float) (radius / 2)) + 0.7f, ((float) (radius / 2)) + 0.7f, ((float) (radius / 2)) + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);
        return output;
    }
}
