package com.sygic.aura.route;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import com.sygic.aura.route.data.infobar_slots.Slot;
import java.util.Collection;

public class InfoSlotsUpdateTask extends AsyncTask<SlotHolder, Slot, Void> {
    private boolean mCanceled;
    private final Slot[] mSlotArray;
    private long mTimeCounter;

    public interface SlotHolder {
        Collection<Slot> getSlots();
    }

    public InfoSlotsUpdateTask() {
        this.mCanceled = false;
        this.mSlotArray = new Slot[4];
    }

    protected void onPreExecute() {
        this.mTimeCounter = 0;
    }

    protected synchronized Void doInBackground(SlotHolder... holders) {
        if (holders != null) {
            resetSlots(holders[0].getSlots());
            while (!this.mCanceled) {
                publishProgress(holders[0].getSlots().toArray(this.mSlotArray));
                try {
                    wait(1000);
                    this.mTimeCounter += 1000;
                } catch (InterruptedException e) {
                }
            }
        }
        return null;
    }

    private void resetSlots(Collection<Slot> infoSlots) {
        for (Slot slot : infoSlots) {
            slot.resetTimer();
        }
    }

    protected void onProgressUpdate(Slot... infoSlots) {
        for (Slot slot : infoSlots) {
            if (slot != null) {
                slot.execute(this.mTimeCounter);
            }
        }
    }

    public synchronized void finishTask() {
        this.mCanceled = true;
        notify();
    }

    public boolean isFinished() {
        return this.mCanceled || getStatus().equals(Status.FINISHED);
    }
}
