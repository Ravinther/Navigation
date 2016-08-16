package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.ActivityLifecycleManager.Callbacks;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fabric {
    static final Logger DEFAULT_LOGGER;
    static volatile Fabric singleton;
    private WeakReference<Activity> activity;
    private ActivityLifecycleManager activityLifecycleManager;
    private final Context context;
    final boolean debuggable;
    private final ExecutorService executorService;
    private final IdManager idManager;
    private final InitializationCallback<Fabric> initializationCallback;
    private AtomicBoolean initialized;
    private final InitializationCallback<?> kitInitializationCallback;
    private final Map<Class<? extends Kit>, Kit> kits;
    final Logger logger;
    private final Handler mainHandler;

    /* renamed from: io.fabric.sdk.android.Fabric.1 */
    class C18091 extends Callbacks {
        C18091() {
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
            Fabric.this.setCurrentActivity(activity);
        }

        public void onActivityStarted(Activity activity) {
            Fabric.this.setCurrentActivity(activity);
        }

        public void onActivityResumed(Activity activity) {
            Fabric.this.setCurrentActivity(activity);
        }
    }

    /* renamed from: io.fabric.sdk.android.Fabric.2 */
    class C18102 implements InitializationCallback {
        final CountDownLatch kitInitializedLatch;
        final /* synthetic */ int val$size;

        C18102(int i) {
            this.val$size = i;
            this.kitInitializedLatch = new CountDownLatch(this.val$size);
        }

        public void success(Object o) {
            this.kitInitializedLatch.countDown();
            if (this.kitInitializedLatch.getCount() == 0) {
                Fabric.this.initialized.set(true);
                Fabric.this.initializationCallback.success(Fabric.this);
            }
        }

        public void failure(Exception exception) {
            Fabric.this.initializationCallback.failure(exception);
        }
    }

    public static class Builder {
        private String appIdentifier;
        private String appInstallIdentifier;
        private final Context context;
        private boolean debuggable;
        private Handler handler;
        private InitializationCallback<Fabric> initializationCallback;
        private Kit[] kits;
        private Logger logger;
        private PriorityThreadPoolExecutor threadPoolExecutor;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public Builder kits(Kit... kits) {
            if (this.kits != null) {
                throw new IllegalStateException("Kits already set.");
            }
            this.kits = kits;
            return this;
        }

        public Fabric build() {
            Map<Class<? extends Kit>, Kit> kitMap;
            if (this.threadPoolExecutor == null) {
                this.threadPoolExecutor = PriorityThreadPoolExecutor.create();
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
            if (this.logger == null) {
                if (this.debuggable) {
                    this.logger = new DefaultLogger(3);
                } else {
                    this.logger = new DefaultLogger();
                }
            }
            if (this.appIdentifier == null) {
                this.appIdentifier = this.context.getPackageName();
            }
            if (this.initializationCallback == null) {
                this.initializationCallback = InitializationCallback.EMPTY;
            }
            if (this.kits == null) {
                kitMap = new HashMap();
            } else {
                kitMap = Fabric.getKitMap(Arrays.asList(this.kits));
            }
            return new Fabric(this.context, kitMap, this.threadPoolExecutor, this.handler, this.logger, this.debuggable, this.initializationCallback, new IdManager(this.context, this.appIdentifier, this.appInstallIdentifier, kitMap.values()));
        }
    }

    static {
        DEFAULT_LOGGER = new DefaultLogger();
    }

    static Fabric singleton() {
        if (singleton != null) {
            return singleton;
        }
        throw new IllegalStateException("Must Initialize Fabric before using singleton()");
    }

    Fabric(Context context, Map<Class<? extends Kit>, Kit> kits, PriorityThreadPoolExecutor threadPoolExecutor, Handler mainHandler, Logger logger, boolean debuggable, InitializationCallback callback, IdManager idManager) {
        this.context = context;
        this.kits = kits;
        this.executorService = threadPoolExecutor;
        this.mainHandler = mainHandler;
        this.logger = logger;
        this.debuggable = debuggable;
        this.initializationCallback = callback;
        this.initialized = new AtomicBoolean(false);
        this.kitInitializationCallback = createKitInitializationCallback(kits.size());
        this.idManager = idManager;
    }

    public static Fabric with(Context context, Kit... kits) {
        if (singleton == null) {
            synchronized (Fabric.class) {
                if (singleton == null) {
                    setFabric(new Builder(context).kits(kits).build());
                }
            }
        }
        return singleton;
    }

    private static void setFabric(Fabric fabric) {
        singleton = fabric;
        fabric.init();
    }

    public Fabric setCurrentActivity(Activity activity) {
        this.activity = new WeakReference(activity);
        return this;
    }

    public Activity getCurrentActivity() {
        if (this.activity != null) {
            return (Activity) this.activity.get();
        }
        return null;
    }

    private void init() {
        setCurrentActivity(extractActivity(this.context));
        this.activityLifecycleManager = new ActivityLifecycleManager(this.context);
        this.activityLifecycleManager.registerCallbacks(new C18091());
        initializeKits(this.context);
    }

    public String getVersion() {
        return "1.3.5.68";
    }

    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    void initializeKits(Context context) {
        StringBuilder initInfo;
        Future<Map<String, KitInfo>> installedKitsFuture = getKitsFinderFuture(context);
        Collection<Kit> providedKits = getKits();
        Onboarding onboarding = new Onboarding(installedKitsFuture, providedKits);
        List<Kit> kits = new ArrayList(providedKits);
        Collections.sort(kits);
        onboarding.injectParameters(context, this, InitializationCallback.EMPTY, this.idManager);
        for (Kit kit : kits) {
            kit.injectParameters(context, this, this.kitInitializationCallback, this.idManager);
        }
        onboarding.initialize();
        if (getLogger().isLoggable("Fabric", 3)) {
            initInfo = new StringBuilder("Initializing ").append(getIdentifier()).append(" [Version: ").append(getVersion()).append("], with the following kits:\n");
        } else {
            initInfo = null;
        }
        for (Kit kit2 : kits) {
            kit2.initializationTask.addDependency(onboarding.initializationTask);
            addAnnotatedDependencies(this.kits, kit2);
            kit2.initialize();
            if (initInfo != null) {
                initInfo.append(kit2.getIdentifier()).append(" [Version: ").append(kit2.getVersion()).append("]\n");
            }
        }
        if (initInfo != null) {
            getLogger().m1453d("Fabric", initInfo.toString());
        }
    }

    void addAnnotatedDependencies(Map<Class<? extends Kit>, Kit> kits, Kit dependentKit) {
        DependsOn dependsOn = (DependsOn) dependentKit.getClass().getAnnotation(DependsOn.class);
        if (dependsOn != null) {
            for (Class<?> dependency : dependsOn.value()) {
                if (dependency.isInterface()) {
                    for (Kit kit : kits.values()) {
                        if (dependency.isAssignableFrom(kit.getClass())) {
                            dependentKit.initializationTask.addDependency(kit.initializationTask);
                        }
                    }
                } else if (((Kit) kits.get(dependency)) == null) {
                    throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
                } else {
                    dependentKit.initializationTask.addDependency(((Kit) kits.get(dependency)).initializationTask);
                }
            }
        }
    }

    private Activity extractActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    public ActivityLifecycleManager getActivityLifecycleManager() {
        return this.activityLifecycleManager;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public Collection<Kit> getKits() {
        return this.kits.values();
    }

    public static <T extends Kit> T getKit(Class<T> cls) {
        return (Kit) singleton().kits.get(cls);
    }

    public static Logger getLogger() {
        if (singleton == null) {
            return DEFAULT_LOGGER;
        }
        return singleton.logger;
    }

    public static boolean isDebuggable() {
        if (singleton == null) {
            return false;
        }
        return singleton.debuggable;
    }

    private static Map<Class<? extends Kit>, Kit> getKitMap(Collection<? extends Kit> kits) {
        HashMap<Class<? extends Kit>, Kit> map = new HashMap(kits.size());
        addToKitMap(map, kits);
        return map;
    }

    private static void addToKitMap(Map<Class<? extends Kit>, Kit> map, Collection<? extends Kit> kits) {
        for (Kit kit : kits) {
            map.put(kit.getClass(), kit);
            if (kit instanceof KitGroup) {
                addToKitMap(map, ((KitGroup) kit).getKits());
            }
        }
    }

    InitializationCallback<?> createKitInitializationCallback(int size) {
        return new C18102(size);
    }

    Future<Map<String, KitInfo>> getKitsFinderFuture(Context context) {
        return getExecutorService().submit(new FabricKitsFinder(context.getPackageCodePath()));
    }
}
