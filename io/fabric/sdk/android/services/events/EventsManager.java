package io.fabric.sdk.android.services.events;

public interface EventsManager<T> {
    void deleteAllEvents();

    void sendEvents();
}
