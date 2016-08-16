package com.sygic.aura.tracker.model;

import com.sygic.aura.tracker.TrackerUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Batch {
    private boolean mDispatched;
    private final List<Event> mEvents;
    private boolean mFull;
    private final String mId;

    public Batch() {
        this.mId = TrackerUtils.getRandomId();
        this.mEvents = Collections.synchronizedList(new ArrayList());
    }

    public void add(Event event) {
        this.mEvents.add(event);
    }

    public void markDispatched() {
        this.mDispatched = true;
    }

    public void markFull() {
        this.mFull = true;
    }

    public String getId() {
        return this.mId;
    }

    public List<Event> getEvents() {
        return this.mEvents;
    }

    public boolean isDispatched() {
        return this.mDispatched;
    }

    public boolean isFull() {
        return this.mFull;
    }
}
