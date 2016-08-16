package com.crashlytics.android.ndk;

import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsKitBinder;
import com.crashlytics.android.core.internal.CrashEventDataProvider;
import com.crashlytics.android.core.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.json.JSONException;

public class CrashlyticsNdk extends Kit<Void> implements CrashEventDataProvider {
    private final JsonCrashDataParser crashDataParser;
    private CrashFileManager crashFileManager;
    SessionEventData lastCrashEventData;
    private final NativeApi nativeApi;

    public CrashlyticsNdk() {
        this(new JniNativeApi());
    }

    CrashlyticsNdk(NativeApi nativeApi) {
        this.nativeApi = nativeApi;
        this.crashDataParser = new JsonCrashDataParser();
    }

    public String getVersion() {
        return "1.1.0.61";
    }

    public String getIdentifier() {
        return "com.crashlytics.sdk.android.crashlytics-ndk";
    }

    public static CrashlyticsNdk getInstance() {
        return (CrashlyticsNdk) Fabric.getKit(CrashlyticsNdk.class);
    }

    public SessionEventData getCrashEventData() {
        return this.lastCrashEventData;
    }

    protected boolean onPreExecute() {
        if (Fabric.getKit(CrashlyticsCore.class) != null) {
            return onPreExecute(new TimeBasedCrashFileManager(getKitStorageDirectory()), CrashlyticsCore.getInstance(), new CrashlyticsKitBinder());
        }
        throw new UnmetDependencyException("CrashlyticsNdk requires Crashlytics");
    }

    boolean onPreExecute(CrashFileManager crashFileManager, CrashlyticsCore crashlyticsCore, CrashlyticsKitBinder kitBinder) {
        this.crashFileManager = crashFileManager;
        boolean initSuccess = false;
        try {
            initSuccess = this.nativeApi.initialize(crashFileManager.getNewCrashFile().getCanonicalPath(), getContext().getAssets());
        } catch (IOException e) {
            Fabric.getLogger().m1456e("CrashlyticsNdk", "Error initializing CrashlyticsNdk", e);
        }
        if (initSuccess) {
            kitBinder.bindCrashEventDataProvider(crashlyticsCore, this);
            Fabric.getLogger().m1453d("CrashlyticsNdk", "Crashlytics NDK initialization successful");
        }
        return initSuccess;
    }

    protected Void doInBackground() {
        File lastCrashFile = this.crashFileManager.getLastWrittenCrashFile();
        if (lastCrashFile != null && lastCrashFile.exists()) {
            Fabric.getLogger().m1453d("CrashlyticsNdk", "Found NDK crash file...");
            String rawCrashData = readJsonCrashFile(lastCrashFile);
            if (rawCrashData != null) {
                try {
                    this.lastCrashEventData = this.crashDataParser.parseCrashEventData(rawCrashData);
                } catch (JSONException e) {
                    Fabric.getLogger().m1456e("CrashlyticsNdk", "Failed to parse NDK crash data.", e);
                }
            }
        }
        this.crashFileManager.clearCrashFiles();
        return null;
    }

    private File getKitStorageDirectory() {
        return new FileStoreImpl(this).getFilesDir();
    }

    private String readJsonCrashFile(File crashFile) {
        Exception e;
        Throwable th;
        String crashData = null;
        Fabric.getLogger().m1453d("CrashlyticsNdk", "Reading NDK crash data...");
        FileInputStream fis = null;
        try {
            FileInputStream fis2 = new FileInputStream(crashFile);
            try {
                crashData = CommonUtils.streamToString(fis2);
                CommonUtils.closeOrLog(fis2, "Error closing crash data file.");
                fis = fis2;
            } catch (Exception e2) {
                e = e2;
                fis = fis2;
                try {
                    Fabric.getLogger().m1456e("CrashlyticsNdk", "Failed to read NDK crash data.", e);
                    CommonUtils.closeOrLog(fis, "Error closing crash data file.");
                    return crashData;
                } catch (Throwable th2) {
                    th = th2;
                    CommonUtils.closeOrLog(fis, "Error closing crash data file.");
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fis = fis2;
                CommonUtils.closeOrLog(fis, "Error closing crash data file.");
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            Fabric.getLogger().m1456e("CrashlyticsNdk", "Failed to read NDK crash data.", e);
            CommonUtils.closeOrLog(fis, "Error closing crash data file.");
            return crashData;
        }
        return crashData;
    }
}
