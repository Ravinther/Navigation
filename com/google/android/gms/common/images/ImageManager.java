package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.internal.zzku;
import com.google.android.gms.internal.zzlf;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public final class ImageManager {
    private static final Object zzabB;
    private static HashSet<Uri> zzabC;
    private final Context mContext;
    private final Handler mHandler;
    private final ExecutorService zzabF;
    private final zzb zzabG;
    private final zzku zzabH;
    private final Map<zza, ImageReceiver> zzabI;
    private final Map<Uri, ImageReceiver> zzabJ;
    private final Map<Uri, Long> zzabK;

    private final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<zza> zzabL;
        final /* synthetic */ ImageManager zzabM;

        public void onReceiveResult(int resultCode, Bundle resultData) {
            this.zzabM.zzabF.execute(new zzc(this.zzabM, this.mUri, (ParcelFileDescriptor) resultData.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    private static final class zzb extends zzlf<zza, Bitmap> {
        protected /* synthetic */ void entryRemoved(boolean x0, Object x1, Object x2, Object x3) {
            zza(x0, (zza) x1, (Bitmap) x2, (Bitmap) x3);
        }

        protected /* synthetic */ int sizeOf(Object x0, Object x1) {
            return zza((zza) x0, (Bitmap) x1);
        }

        protected int zza(zza com_google_android_gms_common_images_zza_zza, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        protected void zza(boolean z, zza com_google_android_gms_common_images_zza_zza, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, com_google_android_gms_common_images_zza_zza, bitmap, bitmap2);
        }
    }

    private final class zzc implements Runnable {
        private final Uri mUri;
        final /* synthetic */ ImageManager zzabM;
        private final ParcelFileDescriptor zzabN;

        public zzc(ImageManager imageManager, Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.zzabM = imageManager;
            this.mUri = uri;
            this.zzabN = parcelFileDescriptor;
        }

        public void run() {
            com.google.android.gms.common.internal.zzb.zzci("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean z = false;
            Bitmap bitmap = null;
            if (this.zzabN != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.zzabN.getFileDescriptor());
                } catch (Throwable e) {
                    Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, e);
                    z = true;
                }
                try {
                    this.zzabN.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.zzabM.mHandler.post(new zzf(this.zzabM, this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
            }
        }
    }

    private final class zzf implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        final /* synthetic */ ImageManager zzabM;
        private boolean zzabP;
        private final CountDownLatch zzoR;

        public zzf(ImageManager imageManager, Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.zzabM = imageManager;
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zzabP = z;
            this.zzoR = countDownLatch;
        }

        private void zza(ImageReceiver imageReceiver, boolean z) {
            ArrayList zza = imageReceiver.zzabL;
            int size = zza.size();
            for (int i = 0; i < size; i++) {
                zza com_google_android_gms_common_images_zza = (zza) zza.get(i);
                if (z) {
                    com_google_android_gms_common_images_zza.zza(this.zzabM.mContext, this.mBitmap, false);
                } else {
                    this.zzabM.zzabK.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                    com_google_android_gms_common_images_zza.zza(this.zzabM.mContext, this.zzabM.zzabH, false);
                }
                if (!(com_google_android_gms_common_images_zza instanceof com.google.android.gms.common.images.zza.zzc)) {
                    this.zzabM.zzabI.remove(com_google_android_gms_common_images_zza);
                }
            }
        }

        public void run() {
            com.google.android.gms.common.internal.zzb.zzch("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (this.zzabM.zzabG != null) {
                if (this.zzabP) {
                    this.zzabM.zzabG.evictAll();
                    System.gc();
                    this.zzabP = false;
                    this.zzabM.mHandler.post(this);
                    return;
                } else if (z) {
                    this.zzabM.zzabG.put(new zza(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) this.zzabM.zzabJ.remove(this.mUri);
            if (imageReceiver != null) {
                zza(imageReceiver, z);
            }
            this.zzoR.countDown();
            synchronized (ImageManager.zzabB) {
                ImageManager.zzabC.remove(this.mUri);
            }
        }
    }

    static {
        zzabB = new Object();
        zzabC = new HashSet();
    }
}
