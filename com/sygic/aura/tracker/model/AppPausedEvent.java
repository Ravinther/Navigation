package com.sygic.aura.tracker.model;

public class AppPausedEvent extends AppLifecycleEvent {
    protected String getJsonTypeName() {
        return "app_paused";
    }
}
