package uk.co.senab.photoview.log;

import android.util.Log;

public class LoggerDefault implements Logger {
    public int m1486d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    public int m1487i(String tag, String msg) {
        return Log.i(tag, msg);
    }
}
