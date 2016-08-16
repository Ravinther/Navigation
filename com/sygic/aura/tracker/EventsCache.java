package com.sygic.aura.tracker;

import com.sygic.aura.tracker.model.Batch;
import com.sygic.aura.tracker.model.Event;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EventsCache {
    private final List<Batch> mBatches;
    private final DispatchListener mListener;

    public interface DispatchListener {
        void onTriggerDispatch();
    }

    protected EventsCache(DispatchListener listener) {
        this.mBatches = Collections.synchronizedList(new ArrayList());
        this.mListener = listener;
    }

    public void add(Event event) {
        if (getCurrentBatch() == null) {
            beginNewBatch();
        }
        getCurrentBatch().add(event);
        if (shouldTriggerDispatch()) {
            if (this.mListener != null) {
                this.mListener.onTriggerDispatch();
            }
            beginNewBatch();
        }
    }

    public void removeDispatchedBatches() {
        Iterator<Batch> iterator = this.mBatches.iterator();
        while (iterator.hasNext()) {
            if (((Batch) iterator.next()).isDispatched()) {
                iterator.remove();
            }
        }
    }

    public List<Batch> getAllBatchesCopy() {
        return getBatchesCopy(false);
    }

    public List<Batch> getUndispatchedBatchesCopy() {
        return getBatchesCopy(true);
    }

    private void beginNewBatch() {
        if (getCurrentBatch() != null) {
            getCurrentBatch().markFull();
        }
        this.mBatches.add(new Batch());
    }

    private boolean shouldTriggerDispatch() {
        return getCurrentBatch().getEvents().size() >= 10;
    }

    private ArrayList<Batch> getBatchesCopy(boolean undispatchedOnly) {
        ArrayList<Batch> list = new ArrayList();
        synchronized (this.mBatches) {
            for (Batch batch : this.mBatches) {
                if (!undispatchedOnly) {
                    list.add(batch);
                } else if (batch.isFull() && !batch.isDispatched()) {
                    list.add(batch);
                }
            }
        }
        return list;
    }

    private Batch getCurrentBatch() {
        if (this.mBatches.size() > 0) {
            return (Batch) this.mBatches.get(this.mBatches.size() - 1);
        }
        return null;
    }
}
