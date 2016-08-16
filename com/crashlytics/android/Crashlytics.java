package com.crashlytics.android;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Crashlytics extends Kit<Void> implements KitGroup {
    public final Answers answers;
    public final Beta beta;
    public final CrashlyticsCore core;
    public final Collection<? extends Kit> kits;

    public static class Builder {
        private Answers answers;
        private Beta beta;
        private CrashlyticsCore core;
        private com.crashlytics.android.core.CrashlyticsCore.Builder coreBuilder;

        public Builder core(CrashlyticsCore core) {
            if (core == null) {
                throw new NullPointerException("CrashlyticsCore Kit must not be null.");
            } else if (this.core != null) {
                throw new IllegalStateException("CrashlyticsCore Kit already set.");
            } else {
                this.core = core;
                return this;
            }
        }

        public Crashlytics build() {
            if (this.coreBuilder != null) {
                if (this.core != null) {
                    throw new IllegalStateException("Must not use Deprecated methods delay(), disabled(), listener(), pinningInfoProvider() with core()");
                }
                this.core = this.coreBuilder.build();
            }
            if (this.answers == null) {
                this.answers = new Answers();
            }
            if (this.beta == null) {
                this.beta = new Beta();
            }
            if (this.core == null) {
                this.core = new CrashlyticsCore();
            }
            return new Crashlytics(this.answers, this.beta, this.core);
        }
    }

    public Crashlytics() {
        this(new Answers(), new Beta(), new CrashlyticsCore());
    }

    Crashlytics(Answers answers, Beta beta, CrashlyticsCore core) {
        this.answers = answers;
        this.beta = beta;
        this.core = core;
        this.kits = Collections.unmodifiableCollection(Arrays.asList(new Kit[]{answers, beta, core}));
    }

    public String getVersion() {
        return "2.5.0.68";
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android:crashlytics";
    }

    public Collection<? extends Kit> getKits() {
        return this.kits;
    }

    protected Void doInBackground() {
        return null;
    }

    public static Crashlytics getInstance() {
        return (Crashlytics) Fabric.getKit(Crashlytics.class);
    }

    public static void logException(Throwable throwable) {
        checkInitialized();
        getInstance().core.logException(throwable);
    }

    public static void log(String msg) {
        checkInitialized();
        getInstance().core.log(msg);
    }

    public static void log(int priority, String tag, String msg) {
        checkInitialized();
        getInstance().core.log(priority, tag, msg);
    }

    public static void setBool(String key, boolean value) {
        checkInitialized();
        getInstance().core.setBool(key, value);
    }

    private static void checkInitialized() {
        if (getInstance() == null) {
            throw new IllegalStateException("Crashlytics must be initialized by calling Fabric.with(Context) prior to calling Crashlytics.getInstance()");
        }
    }
}
