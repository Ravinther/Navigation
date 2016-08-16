package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public abstract class PlatformServiceClient implements ServiceConnection {
    private final String applicationId;
    private final Context context;
    private final Handler handler;
    private CompletedListener listener;
    private final int protocolVersion;
    private int replyMessage;
    private int requestMessage;
    private boolean running;
    private Messenger sender;

    /* renamed from: com.facebook.internal.PlatformServiceClient.1 */
    class C03551 extends Handler {
        C03551() {
        }

        public void handleMessage(Message message) {
            PlatformServiceClient.this.handleMessage(message);
        }
    }

    public interface CompletedListener {
        void completed(Bundle bundle);
    }

    protected abstract void populateRequestBundle(Bundle bundle);

    public PlatformServiceClient(Context context, int requestMessage, int replyMessage, int protocolVersion, String applicationId) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext == null) {
            applicationContext = context;
        }
        this.context = applicationContext;
        this.requestMessage = requestMessage;
        this.replyMessage = replyMessage;
        this.applicationId = applicationId;
        this.protocolVersion = protocolVersion;
        this.handler = new C03551();
    }

    public void setCompletedListener(CompletedListener listener) {
        this.listener = listener;
    }

    public boolean start() {
        if (this.running || NativeProtocol.getLatestAvailableProtocolVersionForService(this.protocolVersion) == -1) {
            return false;
        }
        Intent intent = NativeProtocol.createPlatformServiceIntent(this.context);
        if (intent == null) {
            return false;
        }
        this.running = true;
        this.context.bindService(intent, this, 1);
        return true;
    }

    public void cancel() {
        this.running = false;
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        this.sender = new Messenger(service);
        sendMessage();
    }

    public void onServiceDisconnected(ComponentName name) {
        this.sender = null;
        try {
            this.context.unbindService(this);
        } catch (IllegalArgumentException e) {
        }
        callback(null);
    }

    private void sendMessage() {
        Bundle data = new Bundle();
        data.putString("com.facebook.platform.extra.APPLICATION_ID", this.applicationId);
        populateRequestBundle(data);
        Message request = Message.obtain(null, this.requestMessage);
        request.arg1 = this.protocolVersion;
        request.setData(data);
        request.replyTo = new Messenger(this.handler);
        try {
            this.sender.send(request);
        } catch (RemoteException e) {
            callback(null);
        }
    }

    protected void handleMessage(Message message) {
        if (message.what == this.replyMessage) {
            Bundle extras = message.getData();
            if (extras.getString("com.facebook.platform.status.ERROR_TYPE") != null) {
                callback(null);
            } else {
                callback(extras);
            }
            this.context.unbindService(this);
        }
    }

    private void callback(Bundle result) {
        if (this.running) {
            this.running = false;
            CompletedListener callback = this.listener;
            if (callback != null) {
                callback.completed(result);
            }
        }
    }
}
