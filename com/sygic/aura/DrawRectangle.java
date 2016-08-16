package com.sygic.aura;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;
import android.widget.LinearLayout;
import com.sygic.base.C1799R;

/* compiled from: CameraActivity */
class DrawRectangle extends View {
    private Activity m_activity;

    public DrawRectangle(Context context) {
        super(context);
        this.m_activity = (Activity) context;
    }

    protected void onDraw(Canvas canvas) {
        int nLeft;
        int nRight;
        int nTop;
        int nBottom;
        Paint paint = new Paint();
        paint.setStyle(Style.STROKE);
        paint.setColor(-16777216);
        paint.setStrokeWidth(3.0f);
        int nWidth = ((LinearLayout) this.m_activity.findViewById(C1799R.id.preview)).getWidth();
        int nHeight = ((LinearLayout) this.m_activity.findViewById(C1799R.id.preview)).getHeight();
        if (nWidth < nHeight) {
            nLeft = 3;
            nRight = nWidth - 6;
            nTop = ((nHeight - nWidth) / 2) + 3;
            nBottom = (nTop + nWidth) - 6;
        } else {
            nLeft = ((nWidth - nHeight) / 2) + 3;
            nRight = (nLeft + nHeight) - 6;
            nTop = 3;
            nBottom = nHeight - 6;
        }
        canvas.drawRect((float) nLeft, (float) nTop, (float) nRight, (float) nBottom, paint);
        super.onDraw(canvas);
    }
}
