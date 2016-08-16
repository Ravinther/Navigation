package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzlr;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

public final class LargeParcelTeleporter implements SafeParcelable {
    public static final Creator<LargeParcelTeleporter> CREATOR;
    final int mVersionCode;
    ParcelFileDescriptor zzEo;
    private Parcelable zzEp;
    private boolean zzEq;

    /* renamed from: com.google.android.gms.ads.internal.request.LargeParcelTeleporter.1 */
    class C05881 implements Runnable {
        final /* synthetic */ OutputStream zzEr;
        final /* synthetic */ byte[] zzEs;
        final /* synthetic */ LargeParcelTeleporter zzEt;

        C05881(LargeParcelTeleporter largeParcelTeleporter, OutputStream outputStream, byte[] bArr) {
            this.zzEt = largeParcelTeleporter;
            this.zzEr = outputStream;
            this.zzEs = bArr;
        }

        public void run() {
            Closeable dataOutputStream = new DataOutputStream(this.zzEr);
            try {
                dataOutputStream.writeInt(this.zzEs.length);
                dataOutputStream.write(this.zzEs);
            } catch (Throwable e) {
                zzb.zzb("Error transporting the ad response", e);
                zzp.zzbA().zzc(e, true);
            } finally {
                zzlr.zzb(dataOutputStream);
            }
        }
    }

    static {
        CREATOR = new zzl();
    }

    LargeParcelTeleporter(int versionCode, ParcelFileDescriptor parcelFileDescriptor) {
        this.mVersionCode = versionCode;
        this.zzEo = parcelFileDescriptor;
        this.zzEp = null;
        this.zzEq = true;
    }

    public LargeParcelTeleporter(SafeParcelable teleportee) {
        this.mVersionCode = 1;
        this.zzEo = null;
        this.zzEp = teleportee;
        this.zzEq = false;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if (this.zzEo == null) {
            Parcel obtain = Parcel.obtain();
            try {
                this.zzEp.writeToParcel(obtain, 0);
                byte[] marshall = obtain.marshall();
                this.zzEo = zzf(marshall);
            } finally {
                obtain.recycle();
            }
        }
        zzl.zza(this, dest, flags);
    }

    public <T extends SafeParcelable> T zza(Creator<T> creator) {
        if (this.zzEq) {
            if (this.zzEo == null) {
                zzb.m1444e("File descriptor is empty, returning null.");
                return null;
            }
            Closeable dataInputStream = new DataInputStream(new AutoCloseInputStream(this.zzEo));
            try {
                byte[] bArr = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr, 0, bArr.length);
                zzlr.zzb(dataInputStream);
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.unmarshall(bArr, 0, bArr.length);
                    obtain.setDataPosition(0);
                    this.zzEp = (SafeParcelable) creator.createFromParcel(obtain);
                    this.zzEq = false;
                } finally {
                    obtain.recycle();
                }
            } catch (Throwable e) {
                throw new IllegalStateException("Could not read from parcel file descriptor", e);
            } catch (Throwable th) {
                zzlr.zzb(dataInputStream);
            }
        }
        return (SafeParcelable) this.zzEp;
    }

    protected <T> ParcelFileDescriptor zzf(byte[] bArr) {
        try {
            ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
            new Thread(new C05881(this, new AutoCloseOutputStream(createPipe[1]), bArr)).start();
            return createPipe[0];
        } catch (Throwable e) {
            zzb.zzb("Error transporting the ad response", e);
            zzp.zzbA().zzc(e, true);
            return null;
        }
    }
}
