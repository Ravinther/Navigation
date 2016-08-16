package com.bosch.myspin.serversdk.service.sharedmemory;

import android.os.MemoryFile;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* renamed from: com.bosch.myspin.serversdk.service.sharedmemory.b */
public class C0217b extends MemoryFile {
    private static final LogComponent f303f;
    long f304a;
    int f305b;
    Class<?>[] f306c;
    Object[] f307d;
    Object[] f308e;

    static {
        f303f = LogComponent.ScreenCapturing;
    }

    public C0217b(FileDescriptor fileDescriptor, int i) throws IOException {
        super("MySpinServiceSharedMemory", 0);
        this.f306c = null;
        this.f307d = null;
        this.f308e = null;
        if (fileDescriptor == null) {
            throw new NullPointerException("File descriptor is null.");
        }
        this.f306c = new Class[]{FileDescriptor.class, Boolean.TYPE};
        this.f307d = new Object[]{fileDescriptor, Boolean.valueOf(true)};
        this.f308e = new Object[]{fileDescriptor, Boolean.valueOf(false)};
        this.f305b = i;
        m330a("mLength", Integer.valueOf(i));
        m330a("mFD", fileDescriptor);
        Object a = m329a("native_mmap", new Class[]{FileDescriptor.class, Integer.TYPE, Integer.TYPE}, new Object[]{fileDescriptor, Integer.valueOf(i), Integer.valueOf(3)});
        if (a == null) {
            throw new NullPointerException("Address object is null.");
        }
        if (a instanceof Integer) {
            this.f304a = ((Integer) a).longValue();
        } else {
            this.f304a = ((Long) a).longValue();
        }
        m330a("mAddress", a);
    }

    public long m331a() {
        return this.f304a;
    }

    public int m332b() {
        return this.f305b;
    }

    private void m330a(String str, Object obj) {
        try {
            Field declaredField = MemoryFile.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(this, obj);
        } catch (Throwable e) {
            Logger.logWarning(f303f, "SharedMemoryFile/setPrivate failed: ", e);
        }
    }

    private Object m329a(String str, Class<?>[] clsArr, Object[] objArr) {
        Object obj = null;
        try {
            Method declaredMethod = MemoryFile.class.getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            obj = declaredMethod.invoke(this, objArr);
        } catch (Throwable e) {
            Logger.logWarning(f303f, "SharedMemoryFile/invokePricate failed: ", e);
        }
        return obj;
    }
}
