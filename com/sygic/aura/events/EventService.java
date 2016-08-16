package com.sygic.aura.events;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.sygic.aura.events.key.KeyEventService;
import com.sygic.aura.events.key.KeyEventServiceHelper;
import com.sygic.aura.events.touch.TouchEventService;
import com.sygic.aura.events.touch.TouchEventServiceHelper;

public class EventService implements KeyEventService, TouchEventService {
    private KeyEventService mKeyEventService;
    private TouchEventService mTouchEventService;

    private EventService() {
    }

    public EventService(Context context) {
        this.mKeyEventService = KeyEventServiceHelper.createInstance(context);
        this.mTouchEventService = TouchEventServiceHelper.createInstance();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mKeyEventService.onKeyUp(keyCode, event);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.mKeyEventService.onKeyDown(keyCode, event);
    }

    public boolean onBackPressed() {
        return this.mKeyEventService.onBackPressed();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.mTouchEventService.onTouchEvent(event);
    }
}
