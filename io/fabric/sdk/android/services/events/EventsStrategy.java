package io.fabric.sdk.android.services.events;

public interface EventsStrategy<T> extends EventsManager<T>, FileRollOverManager {
    FilesSender getFilesSender();
}
