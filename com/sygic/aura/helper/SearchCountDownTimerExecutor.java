package com.sygic.aura.helper;

import android.os.CountDownTimer;
import android.os.Handler;
import com.sygic.aura.search.data.NativeScheduler;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

@Deprecated
public class SearchCountDownTimerExecutor {
    private final ConcurrentLinkedQueue<SearchCountDownTimer> mQueue;

    @Deprecated
    public interface CountDownTimerListenerIF {
        @Deprecated
        void onCancelSearch(boolean z);

        @Deprecated
        void onFinishSearch();

        @Deprecated
        void onStartSearch();

        @Deprecated
        void onTickSearch(long j);
    }

    public class SearchCountDownTimer extends CountDownTimer {
        private CountDownTimerListenerIF mCountDownTimerListener;
        private boolean mIsRunning;
        private long mTicketNr;

        /* renamed from: com.sygic.aura.helper.SearchCountDownTimerExecutor.SearchCountDownTimer.1 */
        class C12791 implements Runnable {
            C12791() {
            }

            public void run() {
                SearchCountDownTimer.this.cancel();
                SearchCountDownTimer.this.onFinish();
            }
        }

        public SearchCountDownTimer(long ticketNr, CountDownTimerListenerIF countDownTimerListener, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.mTicketNr = 0;
            this.mIsRunning = false;
            this.mCountDownTimerListener = null;
            this.mTicketNr = ticketNr;
            this.mCountDownTimerListener = countDownTimerListener;
        }

        public long getTicketNr() {
            return this.mTicketNr;
        }

        public boolean isRunning() {
            return this.mIsRunning;
        }

        public void doStart() {
            this.mIsRunning = true;
            this.mCountDownTimerListener.onStartSearch();
            start();
        }

        public void doCancel() {
            this.mCountDownTimerListener.onCancelSearch(!this.mIsRunning);
            cancel();
            if (NativeScheduler.isTaskRunning()) {
                NativeScheduler.cancelSearchTask();
            }
        }

        public void onTick(long millisUntilFinished) {
            this.mCountDownTimerListener.onTickSearch(millisUntilFinished);
            if (!NativeScheduler.isTaskRunning()) {
                new Handler().post(new C12791());
            }
        }

        public void onFinish() {
            if (NativeScheduler.isTaskRunning()) {
                start();
                return;
            }
            this.mIsRunning = false;
            this.mCountDownTimerListener.onFinishSearch();
            SearchCountDownTimerExecutor.this.startNextJob();
        }
    }

    public SearchCountDownTimerExecutor() {
        this.mQueue = new ConcurrentLinkedQueue();
    }

    public long execute(CountDownTimerListenerIF countDownTimerListener, long millisInFuture, long countDownInterval) {
        long ticketNr;
        synchronized (this.mQueue) {
            boolean isFirst = this.mQueue.isEmpty();
            ticketNr = System.currentTimeMillis();
            SearchCountDownTimer searchCountDownTimer = new SearchCountDownTimer(ticketNr, countDownTimerListener, millisInFuture, countDownInterval);
            if (this.mQueue.add(searchCountDownTimer)) {
                if (isFirst) {
                    searchCountDownTimer.doStart();
                }
            } else {
                ticketNr = -1;
            }
        }
        return ticketNr;
    }

    private void startNextJob() {
        this.mQueue.poll();
        SearchCountDownTimer countDownTimerInternal = (SearchCountDownTimer) this.mQueue.peek();
        if (countDownTimerInternal != null) {
            countDownTimerInternal.doStart();
        }
    }

    public boolean cancel(long ticketNr) {
        int index = 0;
        boolean isCanceled = false;
        synchronized (this.mQueue) {
            Iterator<SearchCountDownTimer> it = this.mQueue.iterator();
            while (it.hasNext()) {
                SearchCountDownTimer timer = (SearchCountDownTimer) it.next();
                if (timer.getTicketNr() == ticketNr) {
                    timer.doCancel();
                    if (index == 0 && timer.isRunning()) {
                        startNextJob();
                    } else {
                        it.remove();
                    }
                    isCanceled = true;
                } else {
                    index++;
                }
            }
        }
        return isCanceled;
    }
}
