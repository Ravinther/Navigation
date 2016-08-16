package com.sygic.aura.search.data;

import android.os.Handler;

@Deprecated
public class NativeScheduler {
    private static final Handler mHandler;
    private static boolean mIsRunning;
    private static long mObjectPtr;
    private static final Runnable mRun;

    /* renamed from: com.sygic.aura.search.data.NativeScheduler.1 */
    static class C15491 implements Runnable {
        C15491() {
        }

        public void run() {
            if (NativeScheduler.ProcessScheduledTask(NativeScheduler.mObjectPtr)) {
                NativeScheduler.mIsRunning = true;
                NativeScheduler.mHandler.post(this);
                return;
            }
            NativeScheduler.mIsRunning = false;
            NativeScheduler.mObjectPtr = 0;
        }
    }

    private static native void CancelScheduledSearchTask(long j);

    private static native boolean ProcessScheduledTask(long j);

    static {
        mIsRunning = false;
        mHandler = new Handler();
        mRun = new C15491();
    }

    private static void scheduleSearchTask(long objectPtr) {
        cancelSearchTask();
        mObjectPtr = objectPtr;
        mHandler.post(mRun);
    }

    @Deprecated
    public static void cancelSearchTask() {
        if (mObjectPtr != 0) {
            CancelScheduledSearchTask(mObjectPtr);
        }
    }

    @Deprecated
    public static boolean isTaskRunning() {
        return mIsRunning;
    }
}
