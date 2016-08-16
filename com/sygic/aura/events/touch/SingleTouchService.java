package com.sygic.aura.events.touch;

import android.view.MotionEvent;
import com.sygic.aura.SygicMain;

public class SingleTouchService implements TouchEventService {
    public static float mTouchScaleX;
    public static float mTouchScaleY;
    private boolean mWasIgnore;

    public SingleTouchService() {
        this.mWasIgnore = false;
    }

    static {
        mTouchScaleX = 1.0f;
        mTouchScaleY = 1.0f;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return onTouchEvent(event, false);
    }

    public boolean onTouchEvent(MotionEvent event, boolean bIgnoreEvent) {
        int nAction = event.getAction();
        if (nAction == 0 || nAction == 1 || nAction == 2) {
            float x = event.getX() * mTouchScaleX;
            float y = event.getY() * mTouchScaleY;
            if ((this.mWasIgnore || bIgnoreEvent) && (nAction == 0 || nAction == 1)) {
                nAction += 3;
                this.mWasIgnore ^= 1;
            }
            SygicMain.getInstance().MouseMessage(x, y, (float) nAction);
        }
        return true;
    }
}
