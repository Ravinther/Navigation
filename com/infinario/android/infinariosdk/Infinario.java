package com.infinario.android.infinariosdk;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sygic.aura.fragments.AbstractFragment;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Infinario {
    private static Infinario instance;
    private static Object lockInstance;
    private int commandCounter;
    private CommandManager commandManager;
    private final Context context;
    private Map<String, String> customer;
    private GoogleCloudMessaging gcm;
    private IabHelper iabHelper;
    private Object lockFlushTimer;
    private Object lockPublic;
    private Preferences preferences;
    private int sessionCounter;
    private long sessionEnd;
    private Runnable sessionEndRunnable;
    private Runnable sessionFlushRunnable;
    private Handler sessionHandler;
    private Map<String, Object> sessionProperties;
    private long sessionStart;
    private long sessionTimeOut;
    private String token;
    private String userAgent;

    /* renamed from: com.infinario.android.infinariosdk.Infinario.1 */
    class C10481 implements Runnable {
        C10481() {
        }

        public void run() {
            synchronized (Infinario.this.lockPublic) {
                if (Infinario.this.preferences.getSessionEnd() != -1) {
                    Infinario.this.sessionEnd(Infinario.this.preferences.getSessionEnd(), (Infinario.this.preferences.getSessionEnd() - Infinario.this.preferences.getSessionStart()) / 1000, Infinario.this.preferences.getSessionEndProperties());
                }
            }
        }
    }

    /* renamed from: com.infinario.android.infinariosdk.Infinario.2 */
    class C10492 implements Runnable {
        C10492() {
        }

        public void run() {
            Infinario.this.flush();
        }
    }

    /* renamed from: com.infinario.android.infinariosdk.Infinario.6 */
    class C10506 implements Runnable {
        C10506() {
        }

        public void run() {
            try {
                Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(Infinario.this.context);
                Infinario.this.preferences.setGoogleAdvertisingId(adInfo.getId());
                synchronized (Infinario.this.lockPublic) {
                    HashMap<String, Object> advId = new HashMap();
                    advId.put("google_advertising_id", adInfo.getId());
                    Infinario.this._update(advId);
                }
            } catch (Exception e) {
                Log.e("Infinario", "Cannot initialize google advertising ID");
            }
        }
    }

    static {
        instance = null;
        lockInstance = new Object();
    }

    private Infinario(Context context, String token, String target, Map<String, String> customer) {
        this.gcm = null;
        this.commandCounter = 50;
        this.iabHelper = null;
        this.sessionStart = -1;
        this.sessionEnd = -1;
        this.token = token;
        this.context = context.getApplicationContext();
        this.preferences = Preferences.get(context);
        this.preferences.setToken(token);
        this.sessionProperties = new HashMap();
        this.sessionTimeOut = 60000;
        this.sessionCounter = 0;
        if (target != null) {
            this.preferences.setTarget(target.replaceFirst("/*$", ""));
        }
        if (this.preferences.getGoogleAdvertisingId().isEmpty()) {
            initializeGoogleAdvertisingId();
        }
        if (this.preferences.getDeviceType().isEmpty()) {
            initializeDeviceType();
        }
        if (this.preferences.getCookieId().isEmpty()) {
            this.preferences.setCookieId(Secure.getString(context.getContentResolver(), "android_id"));
        }
        this.userAgent = UserAgent.create(this.preferences);
        this.commandManager = new CommandManager(context, target, this.userAgent);
        this.iabHelper = new IabHelper(context);
        this.iabHelper.startSetup(null);
        if (customer == null) {
            customer = new HashMap();
        }
        customer.put("cookie", this.preferences.getCookieId());
        this.lockPublic = new Object();
        this.lockFlushTimer = new Object();
        this.sessionHandler = new Handler();
        this.sessionEndRunnable = new C10481();
        this.sessionFlushRunnable = new C10492();
        this.customer = customer;
        if (this.preferences.getAutomaticFlushing()) {
            startFlushTimer();
        }
    }

    public static Infinario getInstance(Context context, String token, String target, Map<String, String> customer) {
        if (instance == null) {
            synchronized (lockInstance) {
                if (instance == null) {
                    instance = new Infinario(context, token, target, customer);
                }
            }
        }
        return instance;
    }

    public static Infinario getInstance(Context context, String token, String target) {
        return getInstance(context, token, target, (Map) null);
    }

    public void identify(Map<String, String> customer, Map<String, Object> properties) {
        synchronized (this.lockPublic) {
            if (customer.containsKey("registered")) {
                this.customer.put("registered", customer.get("registered"));
                if (!((String) customer.get("registered")).equals(this.preferences.getRegistredId())) {
                    this.preferences.setRegistredId((String) customer.get("registered"));
                    Map<String, Object> identificationProperties = Device.deviceProperties(this.preferences);
                    identificationProperties.put("registered", customer.get("registered"));
                    _track("identification", identificationProperties);
                    _update(properties);
                }
            }
        }
    }

    public void identify(String customer, Map<String, Object> properties) {
        identify(translateId(customer), (Map) properties);
    }

    public void identify(String customer) {
        identify(customer, new HashMap());
    }

    public boolean update(Map<String, Object> properties) {
        boolean _update;
        synchronized (this.lockPublic) {
            _update = _update(properties);
        }
        return _update;
    }

    private boolean _update(Map<String, Object> properties) {
        if (!this.commandManager.schedule(new Customer(this.customer, this.token, properties))) {
            return false;
        }
        if (this.preferences.getAutomaticFlushing()) {
            startFlushTimer();
        }
        return true;
    }

    private void sessionStart(long timeStamp, Map<String, Object> properties) {
        this.preferences.setSessionStart(timeStamp);
        _track("session_start", mergeProperties(properties, -1), Long.valueOf(timeStamp));
    }

    private void sessionEnd(long timeStamp, long duration, Map<String, Object> properties) {
        _track("session_end", mergeProperties(properties, duration), Long.valueOf(timeStamp));
        this.preferences.setSessionStart(-1);
        this.preferences.setSessionEnd(-1, null);
    }

    public void trackSessionStartImpl(Map<String, Object> properties) {
        synchronized (this.lockPublic) {
            long now = new Date().getTime();
            long sessionEnd = this.preferences.getSessionEnd();
            long sessionStart = this.preferences.getSessionStart();
            if (this.sessionHandler != null) {
                this.sessionHandler.removeCallbacks(this.sessionEndRunnable);
            }
            if (sessionEnd != -1) {
                if (now - sessionEnd > this.sessionTimeOut) {
                    sessionEnd(sessionEnd, (sessionEnd - sessionStart) / 1000, this.preferences.getSessionEndProperties());
                    sessionStart(now, properties);
                }
            } else if (sessionStart == -1) {
                sessionStart(now, properties);
            } else {
                if (now - sessionStart > this.sessionTimeOut) {
                    sessionStart(now, properties);
                }
            }
        }
    }

    public void trackSessionEndImpl(Map<String, Object> properties) {
        synchronized (this.lockPublic) {
            this.preferences.setSessionEnd(new Date().getTime(), properties);
            this.sessionHandler.postDelayed(this.sessionEndRunnable, this.sessionTimeOut);
        }
    }

    public void trackSessionStart() {
        trackSessionStart(null);
    }

    public void trackSessionStart(Map<String, Object> properties) {
        synchronized (this.lockPublic) {
            this.sessionCounter++;
            int _sessionCounter = this.sessionCounter;
        }
        if (_sessionCounter == 1) {
            trackSessionStartImpl(properties);
        }
    }

    public void trackSessionEnd() {
        trackSessionEnd(null);
    }

    public void trackSessionEnd(Map<String, Object> properties) {
        synchronized (this.lockPublic) {
            if (this.sessionCounter > 0) {
                this.sessionCounter--;
            }
            int _sessionCounter = this.sessionCounter;
        }
        if (_sessionCounter == 0) {
            trackSessionEndImpl(properties);
        }
    }

    private Map<String, Object> mergeProperties(Map<String, Object> properties, long duration) {
        Map<String, Object> deviceProperties = Device.deviceProperties(this.preferences);
        String appVersionName = this.preferences.getAppVersionName();
        if (appVersionName != null) {
            deviceProperties.put("app_version", appVersionName);
        }
        if (duration != -1) {
            deviceProperties.put("duration", Long.valueOf(duration));
        }
        if (properties != null) {
            deviceProperties.putAll(properties);
        }
        return deviceProperties;
    }

    public boolean track(String type, Map<String, Object> properties, Long timestamp) {
        boolean _track;
        synchronized (this.lockPublic) {
            _track = _track(type, properties, timestamp);
        }
        return _track;
    }

    private boolean _track(String type, Map<String, Object> properties, Long timestamp) {
        if (!this.commandManager.schedule(new Event(this.customer, this.token, type, properties, timestamp))) {
            return false;
        }
        if (this.preferences.getAutomaticFlushing()) {
            startFlushTimer();
        }
        return true;
    }

    private boolean _track(String type, Map<String, Object> properties) {
        return _track(type, properties, null);
    }

    public boolean track(String type, Map<String, Object> properties) {
        return track(type, properties, null);
    }

    public void flush() {
        this.commandManager.flush(20);
    }

    public static void handleGooglePushNotification(Context context, Intent intent) {
        Preferences preferences = Preferences.get(context);
        if (preferences.getGooglePushNotifications() && checkPlayServices(context)) {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            Bundle extras = intent.getExtras();
            String messageType = gcm.getMessageType(intent);
            String senderId = preferences.getSenderId();
            if (!extras.isEmpty() && senderId != null && !senderId.equals("") && "gcm".equals(messageType) && extras.getString("from").equals(senderId)) {
                Log.d("Infinario", "Received data: " + intent.getExtras().toString());
                sendNotification(context, intent.getExtras(), preferences.getIcon());
            }
        }
    }

    private static Map<String, String> translateId(String customer) {
        Map<String, String> customer_ids = new HashMap();
        customer_ids.put("registered", customer);
        return customer_ids;
    }

    private static boolean checkPlayServices(Context context) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            return true;
        }
        Log.i("Infinario", "This device is not supported.");
        return false;
    }

    private static void sendNotification(Context context, Bundle data, int iconDrawable) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.putExtras(data);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 268435456);
            String message = data.getString("message");
            Builder builder = new Builder(context).setSmallIcon(iconDrawable).setContentTitle(data.getString(AbstractFragment.ARG_TITLE)).setStyle(new BigTextStyle().bigText(message)).setContentText(message).setAutoCancel(true);
            builder.setContentIntent(contentIntent);
            notificationManager.notify(444, builder.build());
        }
    }

    private void initializeGoogleAdvertisingId() {
        new Thread(new C10506()).start();
    }

    private void initializeDeviceType() {
        try {
            if ((this.context.getResources().getConfiguration().screenLayout & 15) >= 3) {
                Log.d("Infinario", "Detect tablet");
                this.preferences.setDeviceType("tablet");
                return;
            }
            Log.d("Infinario", "Detect mobile");
            this.preferences.setDeviceType("mobile");
        } catch (Exception e) {
            Log.e("Infinario", "Cannot initialize device type");
        }
    }

    private void startFlushTimer() {
        synchronized (this.lockFlushTimer) {
            if (this.sessionHandler != null) {
                stopFlushTimer();
                this.sessionHandler.postDelayed(this.sessionFlushRunnable, 10000);
            }
        }
    }

    private void stopFlushTimer() {
        synchronized (this.lockFlushTimer) {
            if (this.sessionHandler != null) {
                this.sessionHandler.removeCallbacks(this.sessionFlushRunnable);
            }
        }
    }
}
