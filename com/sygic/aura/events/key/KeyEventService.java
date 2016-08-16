package com.sygic.aura.events.key;

import android.view.KeyEvent;

public interface KeyEventService {
    boolean onBackPressed();

    boolean onKeyDown(int i, KeyEvent keyEvent);

    boolean onKeyUp(int i, KeyEvent keyEvent);
}
