package com.sygic.aura.map.screen;

import android.view.ViewGroup;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.TransitionManagerCompat;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.views.WndManager;
import com.sygic.aura.views.font_specials.STextView;

public abstract class AutoCloseableOverlayScreen extends OverlayScreen implements AutoCloseListener {
    protected abstract ViewGroup getCountdownContainer();

    protected abstract STextView getCountdownTextView();

    protected abstract boolean isMovementRequired();

    protected abstract void onAutoClosed();

    protected void onScreenEntered() {
        WndEventsReceiver.registerAutoCloseListener(this);
    }

    protected void onscreenLeft() {
        WndEventsReceiver.unregisterAutoCloseListener(this);
        if (getCountdownTextView() != null) {
            getCountdownTextView().setVisibility(8);
        }
    }

    public void onAutoClose(int tick, boolean inMovement) {
        if (inMovement || !isMovementRequired()) {
            if (WndManager.shouldShowCountdown(tick)) {
                if (getCountdownTextView().getVisibility() != 0) {
                    TransitionManagerCompat.beginDelayedTransition(getCountdownContainer());
                    getCountdownTextView().setVisibility(0);
                }
                getCountdownTextView().setText("(" + tick + ")");
            } else {
                getCountdownTextView().setVisibility(8);
            }
            if (tick <= 0) {
                onAutoClosed();
            }
        }
    }
}
