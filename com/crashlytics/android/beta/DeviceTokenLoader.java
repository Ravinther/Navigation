package com.crashlytics.android.beta;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.ValueLoader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeviceTokenLoader implements ValueLoader<String> {
    public String load(Context context) throws Exception {
        long start = System.nanoTime();
        String token = "";
        ZipInputStream zis = null;
        try {
            zis = getZipInputStreamOfAppApkFrom(context);
            token = determineDeviceToken(zis);
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    Fabric.getLogger().m1456e("Beta", "Failed to close the APK file", e);
                }
            }
        } catch (NameNotFoundException e2) {
            Fabric.getLogger().m1456e("Beta", "Failed to find this app in the PackageManager", e2);
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e3) {
                    Fabric.getLogger().m1456e("Beta", "Failed to close the APK file", e3);
                }
            }
        } catch (FileNotFoundException e4) {
            Fabric.getLogger().m1456e("Beta", "Failed to find the APK file", e4);
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e32) {
                    Fabric.getLogger().m1456e("Beta", "Failed to close the APK file", e32);
                }
            }
        } catch (IOException e322) {
            Fabric.getLogger().m1456e("Beta", "Failed to read the APK file", e322);
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e3222) {
                    Fabric.getLogger().m1456e("Beta", "Failed to close the APK file", e3222);
                }
            }
        } catch (Throwable th) {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e32222) {
                    Fabric.getLogger().m1456e("Beta", "Failed to close the APK file", e32222);
                }
            }
        }
        Fabric.getLogger().m1453d("Beta", "Beta device token load took " + (((double) (System.nanoTime() - start)) / 1000000.0d) + "ms");
        return token;
    }

    ZipInputStream getZipInputStreamOfAppApkFrom(Context context) throws NameNotFoundException, FileNotFoundException {
        return new ZipInputStream(new FileInputStream(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir));
    }

    String determineDeviceToken(ZipInputStream zis) throws IOException {
        String name;
        do {
            ZipEntry entry = zis.getNextEntry();
            if (entry == null) {
                return "";
            }
            name = entry.getName();
        } while (!name.startsWith("assets/com.crashlytics.android.beta/dirfactor-device-token="));
        return name.substring("assets/com.crashlytics.android.beta/dirfactor-device-token=".length(), name.length() - 1);
    }
}
