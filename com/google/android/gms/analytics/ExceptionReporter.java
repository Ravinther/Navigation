package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.google.android.gms.analytics.internal.zzae;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

public class ExceptionReporter implements UncaughtExceptionHandler {
    private final Context mContext;
    private final UncaughtExceptionHandler zzKp;
    private final Tracker zzKq;
    private ExceptionParser zzKr;
    private GoogleAnalytics zzKs;

    public ExceptionReporter(Tracker tracker, UncaughtExceptionHandler originalHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (context == null) {
            throw new NullPointerException("context cannot be null");
        } else {
            this.zzKp = originalHandler;
            this.zzKq = tracker;
            this.zzKr = new StandardExceptionParser(context, new ArrayList());
            this.mContext = context.getApplicationContext();
            zzae.m1446v("ExceptionReporter created, original handler is " + (originalHandler == null ? "null" : originalHandler.getClass().getName()));
        }
    }

    public void uncaughtException(Thread t, Throwable e) {
        String str = "UncaughtException";
        if (this.zzKr != null) {
            str = this.zzKr.getDescription(t != null ? t.getName() : null, e);
        }
        zzae.m1446v("Reporting uncaught exception: " + str);
        this.zzKq.send(new ExceptionBuilder().setDescription(str).setFatal(true).build());
        GoogleAnalytics zzhu = zzhu();
        zzhu.dispatchLocalHits();
        zzhu.zzhy();
        if (this.zzKp != null) {
            zzae.m1446v("Passing exception to the original handler");
            this.zzKp.uncaughtException(t, e);
        }
    }

    GoogleAnalytics zzhu() {
        if (this.zzKs == null) {
            this.zzKs = GoogleAnalytics.getInstance(this.mContext);
        }
        return this.zzKs;
    }

    UncaughtExceptionHandler zzhv() {
        return this.zzKp;
    }
}
