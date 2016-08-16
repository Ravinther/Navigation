package io.fabric.sdk.android.services.events;

public class DisabledEventsStrategy<T> implements EventsStrategy<T> {
    public void sendEvents() {
    }

    public void deleteAllEvents() {
    }

    public void cancelTimeBasedFileRollOver() {
    }

    public boolean rollFileOver() {
        return false;
    }

    public FilesSender getFilesSender() {
        return null;
    }
}
