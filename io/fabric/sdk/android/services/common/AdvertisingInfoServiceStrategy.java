package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class AdvertisingInfoServiceStrategy implements AdvertisingInfoStrategy {
    private final Context context;

    private static final class AdvertisingConnection implements ServiceConnection {
        private final LinkedBlockingQueue<IBinder> queue;
        private boolean retrieved;

        private AdvertisingConnection() {
            this.retrieved = false;
            this.queue = new LinkedBlockingQueue(1);
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                this.queue.put(service);
            } catch (InterruptedException e) {
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            this.queue.clear();
        }

        public IBinder getBinder() {
            if (this.retrieved) {
                Fabric.getLogger().m1455e("Fabric", "getBinder already called");
            }
            this.retrieved = true;
            try {
                return (IBinder) this.queue.poll(200, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                return null;
            }
        }
    }

    private static final class AdvertisingInterface implements IInterface {
        private final IBinder binder;

        public AdvertisingInterface(IBinder binder) {
            this.binder = binder;
        }

        public IBinder asBinder() {
            return this.binder;
        }

        public String getId() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            String id = null;
            try {
                data.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.binder.transact(1, data, reply, 0);
                reply.readException();
                id = reply.readString();
            } catch (Exception e) {
                Fabric.getLogger().m1453d("Fabric", "Could not get parcel from Google Play Service to capture AdvertisingId");
            } finally {
                reply.recycle();
                data.recycle();
            }
            return id;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean isLimitAdTrackingEnabled() throws android.os.RemoteException {
            /*
            r9 = this;
            r4 = 1;
            r5 = 0;
            r0 = android.os.Parcel.obtain();
            r3 = android.os.Parcel.obtain();
            r2 = 0;
            r6 = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
            r0.writeInterfaceToken(r6);	 Catch:{ Exception -> 0x002f }
            r6 = 1;
            r0.writeInt(r6);	 Catch:{ Exception -> 0x002f }
            r6 = r9.binder;	 Catch:{ Exception -> 0x002f }
            r7 = 2;
            r8 = 0;
            r6.transact(r7, r0, r3, r8);	 Catch:{ Exception -> 0x002f }
            r3.readException();	 Catch:{ Exception -> 0x002f }
            r6 = r3.readInt();	 Catch:{ Exception -> 0x002f }
            if (r6 == 0) goto L_0x002d;
        L_0x0025:
            r2 = r4;
        L_0x0026:
            r3.recycle();
            r0.recycle();
        L_0x002c:
            return r2;
        L_0x002d:
            r2 = r5;
            goto L_0x0026;
        L_0x002f:
            r1 = move-exception;
            r4 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ all -> 0x0044 }
            r5 = "Fabric";
            r6 = "Could not get parcel from Google Play Service to capture Advertising limitAdTracking";
            r4.m1453d(r5, r6);	 Catch:{ all -> 0x0044 }
            r3.recycle();
            r0.recycle();
            goto L_0x002c;
        L_0x0044:
            r4 = move-exception;
            r3.recycle();
            r0.recycle();
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy.AdvertisingInterface.isLimitAdTrackingEnabled():boolean");
        }
    }

    public AdvertisingInfoServiceStrategy(Context context) {
        this.context = context.getApplicationContext();
    }

    public AdvertisingInfo getAdvertisingInfo() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Fabric.getLogger().m1453d("Fabric", "AdvertisingInfoServiceStrategy cannot be called on the main thread");
            return null;
        }
        try {
            this.context.getPackageManager().getPackageInfo("com.android.vending", 0);
            AdvertisingConnection connection = new AdvertisingConnection();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            try {
                if (this.context.bindService(intent, connection, 1)) {
                    AdvertisingInterface adInterface = new AdvertisingInterface(connection.getBinder());
                    AdvertisingInfo advertisingInfo = new AdvertisingInfo(adInterface.getId(), adInterface.isLimitAdTrackingEnabled());
                    this.context.unbindService(connection);
                    return advertisingInfo;
                }
                Fabric.getLogger().m1453d("Fabric", "Could not bind to Google Play Service to capture AdvertisingId");
                return null;
            } catch (Exception e) {
                Fabric.getLogger().m1460w("Fabric", "Exception in binding to Google Play Service to capture AdvertisingId", e);
                this.context.unbindService(connection);
                return null;
            } catch (Throwable t) {
                Fabric.getLogger().m1454d("Fabric", "Could not bind to Google Play Service to capture AdvertisingId", t);
                return null;
            }
        } catch (NameNotFoundException e2) {
            Fabric.getLogger().m1453d("Fabric", "Unable to find Google Play Services package name");
            return null;
        } catch (Exception e3) {
            Fabric.getLogger().m1454d("Fabric", "Unable to determine if Google Play Services is available", e3);
            return null;
        }
    }
}
