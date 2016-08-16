package io.fabric.sdk.android;

import android.os.SystemClock;
import android.text.TextUtils;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class FabricKitsFinder implements Callable<Map<String, KitInfo>> {
    final String apkFileName;

    FabricKitsFinder(String apkFileName) {
        this.apkFileName = apkFileName;
    }

    public Map<String, KitInfo> call() throws Exception {
        Map<String, KitInfo> kitInfos = new HashMap();
        long startScan = SystemClock.elapsedRealtime();
        int count = 0;
        ZipFile apkFile = loadApkFile();
        Enumeration<? extends ZipEntry> entries = apkFile.entries();
        while (entries.hasMoreElements()) {
            count++;
            ZipEntry entry = (ZipEntry) entries.nextElement();
            if (entry.getName().startsWith("fabric/")) {
                KitInfo kitInfo = loadKitInfo(entry, apkFile);
                if (kitInfo != null) {
                    kitInfos.put(kitInfo.getIdentifier(), kitInfo);
                    Fabric.getLogger().m1458v("Fabric", String.format("Found kit:[%s] version:[%s]", new Object[]{kitInfo.getIdentifier(), kitInfo.getVersion()}));
                }
            }
        }
        if (apkFile != null) {
            try {
                apkFile.close();
            } catch (IOException e) {
            }
        }
        Fabric.getLogger().m1458v("Fabric", "finish scanning in " + (SystemClock.elapsedRealtime() - startScan) + " reading:" + count);
        return kitInfos;
    }

    private KitInfo loadKitInfo(ZipEntry fabricFile, ZipFile apk) {
        InputStream inputStream = null;
        KitInfo kitInfo;
        try {
            inputStream = apk.getInputStream(fabricFile);
            Properties properties = new Properties();
            properties.load(inputStream);
            String id = properties.getProperty("fabric-identifier");
            String version = properties.getProperty("fabric-version");
            String buildType = properties.getProperty("fabric-build-type");
            if (TextUtils.isEmpty(id) || TextUtils.isEmpty(version)) {
                throw new IllegalStateException("Invalid format of fabric file," + fabricFile.getName());
            }
            kitInfo = new KitInfo(id, version, buildType);
            return kitInfo;
        } catch (IOException ie) {
            kitInfo = Fabric.getLogger();
            kitInfo.m1456e("Fabric", "Error when parsing fabric properties " + fabricFile.getName(), ie);
            return null;
        } finally {
            CommonUtils.closeQuietly(inputStream);
        }
    }

    protected ZipFile loadApkFile() throws IOException {
        return new ZipFile(this.apkFileName);
    }
}
