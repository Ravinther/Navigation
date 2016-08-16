package com.sygic.aura.events.touch;

import android.view.MotionEvent;
import com.sygic.aura.SygicMain;

public class MultiTouchService extends SingleTouchService implements TouchEventService {
    private transient MotionEvent mLastEvent;
    private transient int mMultiCount;
    private transient boolean mWasSingle;

    public MultiTouchService() {
        this.mMultiCount = 0;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int nActionCode = event.getAction() & 255;
        if (event.getPointerCount() == 1) {
            this.mLastEvent = event;
            this.mWasSingle = true;
            this.mMultiCount = 0;
            super.onTouchEvent(event);
        } else {
            int x1 = (int) (event.getX(0) * mTouchScaleX);
            int y1 = (int) (event.getY(0) * mTouchScaleY);
            int x2 = (int) (event.getX(1) * mTouchScaleX);
            int y2 = (int) (event.getY(1) * mTouchScaleY);
            if (nActionCode == 5) {
                this.mMultiCount++;
                if (this.mWasSingle && this.mMultiCount == 1) {
                    this.mLastEvent.setAction(1);
                    super.onTouchEvent(this.mLastEvent, true);
                }
                SygicMain.getInstance().MultipleTouchMessage(x1, y1, x2, y2, 1);
            }
            if (nActionCode == 6) {
                this.mMultiCount--;
                if (this.mMultiCount == 0) {
                    SygicMain.getInstance().MultipleTouchMessage(x1, y1, x2, y2, 5);
                    this.mWasSingle = false;
                } else {
                    SygicMain.getInstance().MultipleTouchMessage(x1, y1, x2, y2, 3);
                }
            } else {
                SygicMain.getInstance().MultipleTouchMessage(x1, y1, x2, y2, 2);
            }
        }
        return true;
    }
}
