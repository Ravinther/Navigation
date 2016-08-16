package com.bosch.myspin.serversdk.service.sharedmemory;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.io.IOException;

/* renamed from: com.bosch.myspin.serversdk.service.sharedmemory.a */
public class C0216a {
    private static final LogComponent f301a;
    private static int f302b;

    static {
        f301a = LogComponent.ScreenCapturing;
        f302b = 1500000;
    }

    public static C0217b m328a(IBinder iBinder) throws IOException {
        Logger.logDebug(f301a, "SharedMemoryCreator/requestSharedMemoryFromService");
        if (iBinder == null) {
            throw new IllegalArgumentException("Argument IBinder must no be null");
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        int readInt;
        try {
            obtain.writeInterfaceToken("com.bosch.de.myspin.android.appsdk.service.ICarConnectionClient");
            iBinder.transact(4097, obtain, obtain2, 0);
            if (obtain2 != null) {
                readInt = obtain2.readInt();
                if (readInt == 0) {
                    Logger.logDebug(f301a, "SharedMemoryCreator/hasFileDescriptors: " + obtain2.hasFileDescriptors());
                    parcelFileDescriptor = obtain2.readFileDescriptor();
                    readInt = obtain2.readInt();
                    if (parcelFileDescriptor == null) {
                        return new C0217b(parcelFileDescriptor.getFileDescriptor(), readInt);
                    }
                    throw new IOException("ParcelFileDescriptor is null");
                }
                throw new IOException("Reply content prefix error. Reply prefix: [" + readInt + " ]");
            }
            throw new IOException("Reply parcel is null");
        } catch (RemoteException e) {
            readInt = e;
            Logger.logError(f301a, "SharedMemoryCreator/getMemFile: RemoteException", readInt);
            readInt = 0;
            if (parcelFileDescriptor == null) {
                return new C0217b(parcelFileDescriptor.getFileDescriptor(), readInt);
            }
            throw new IOException("ParcelFileDescriptor is null");
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
