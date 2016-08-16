package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import loquendo.tts.engine.TTSConst;

class SnackbarManager {
    private static SnackbarManager sSnackbarManager;
    private SnackbarRecord mCurrentSnackbar;
    private final Handler mHandler;
    private final Object mLock;
    private SnackbarRecord mNextSnackbar;

    /* renamed from: android.support.design.widget.SnackbarManager.1 */
    class C00211 implements android.os.Handler.Callback {
        C00211() {
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    SnackbarManager.this.handleTimeout((SnackbarRecord) message.obj);
                    return true;
                default:
                    return false;
            }
        }
    }

    interface Callback {
        void dismiss();

        void show();
    }

    private static class SnackbarRecord {
        private final WeakReference<Callback> callback;
        private int duration;

        boolean isSnackbar(Callback callback) {
            return callback != null && this.callback.get() == callback;
        }
    }

    static SnackbarManager getInstance() {
        if (sSnackbarManager == null) {
            sSnackbarManager = new SnackbarManager();
        }
        return sSnackbarManager;
    }

    private SnackbarManager() {
        this.mLock = new Object();
        this.mHandler = new Handler(Looper.getMainLooper(), new C00211());
    }

    public void dismiss(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbar(callback)) {
                cancelSnackbarLocked(this.mCurrentSnackbar);
            }
            if (isNextSnackbar(callback)) {
                cancelSnackbarLocked(this.mNextSnackbar);
            }
        }
    }

    public void onDismissed(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbar(callback)) {
                this.mCurrentSnackbar = null;
                if (this.mNextSnackbar != null) {
                    showNextSnackbarLocked();
                }
            }
        }
    }

    public void onShown(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbar(callback)) {
                scheduleTimeoutLocked(this.mCurrentSnackbar);
            }
        }
    }

    public void cancelTimeout(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbar(callback)) {
                this.mHandler.removeCallbacksAndMessages(this.mCurrentSnackbar);
            }
        }
    }

    public void restoreTimeout(Callback callback) {
        synchronized (this.mLock) {
            if (isCurrentSnackbar(callback)) {
                scheduleTimeoutLocked(this.mCurrentSnackbar);
            }
        }
    }

    private void showNextSnackbarLocked() {
        if (this.mNextSnackbar != null) {
            this.mCurrentSnackbar = this.mNextSnackbar;
            this.mNextSnackbar = null;
            Callback callback = (Callback) this.mCurrentSnackbar.callback.get();
            if (callback != null) {
                callback.show();
            } else {
                this.mCurrentSnackbar = null;
            }
        }
    }

    private boolean cancelSnackbarLocked(SnackbarRecord record) {
        Callback callback = (Callback) record.callback.get();
        if (callback == null) {
            return false;
        }
        callback.dismiss();
        return true;
    }

    private boolean isCurrentSnackbar(Callback callback) {
        return this.mCurrentSnackbar != null && this.mCurrentSnackbar.isSnackbar(callback);
    }

    private boolean isNextSnackbar(Callback callback) {
        return this.mNextSnackbar != null && this.mNextSnackbar.isSnackbar(callback);
    }

    private void scheduleTimeoutLocked(SnackbarRecord r) {
        if (r.duration != -2) {
            int durationMs = 2750;
            if (r.duration > 0) {
                durationMs = r.duration;
            } else if (r.duration == -1) {
                durationMs = 1500;
            }
            this.mHandler.removeCallbacksAndMessages(r);
            this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 0, r), (long) durationMs);
        }
    }

    private void handleTimeout(SnackbarRecord record) {
        synchronized (this.mLock) {
            if (this.mCurrentSnackbar == record || this.mNextSnackbar == record) {
                cancelSnackbarLocked(record);
            }
        }
    }
}
