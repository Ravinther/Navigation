package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import loquendo.tts.engine.TTSConst;
import uk.co.senab.photoview.Compat;

@TargetApi(5)
public class EclairGestureDetector extends CupcakeGestureDetector {
    private int mActivePointerId;
    private int mActivePointerIndex;

    public EclairGestureDetector(Context context) {
        super(context);
        this.mActivePointerId = -1;
        this.mActivePointerIndex = 0;
    }

    float getActiveX(MotionEvent ev) {
        try {
            return ev.getX(this.mActivePointerIndex);
        } catch (Exception e) {
            return ev.getX();
        }
    }

    float getActiveY(MotionEvent ev) {
        try {
            return ev.getY(this.mActivePointerIndex);
        } catch (Exception e) {
            return ev.getY();
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int i = 0;
        switch (ev.getAction() & 255) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.mActivePointerId = ev.getPointerId(0);
                break;
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSUNICODE /*3*/:
                this.mActivePointerId = -1;
                break;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                int pointerIndex = Compat.getPointerIndex(ev.getAction());
                if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
                    int newPointerIndex;
                    if (pointerIndex == 0) {
                        newPointerIndex = 1;
                    } else {
                        newPointerIndex = 0;
                    }
                    this.mActivePointerId = ev.getPointerId(newPointerIndex);
                    this.mLastTouchX = ev.getX(newPointerIndex);
                    this.mLastTouchY = ev.getY(newPointerIndex);
                    break;
                }
                break;
        }
        if (this.mActivePointerId != -1) {
            i = this.mActivePointerId;
        }
        this.mActivePointerIndex = ev.findPointerIndex(i);
        return super.onTouchEvent(ev);
    }
}
