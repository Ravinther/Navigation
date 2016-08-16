package com.crashlytics.android.ndk;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.crashlytics.android.core.internal.models.BinaryImageData;
import com.crashlytics.android.core.internal.models.CustomAttributeData;
import com.crashlytics.android.core.internal.models.DeviceData;
import com.crashlytics.android.core.internal.models.SessionEventData;
import com.crashlytics.android.core.internal.models.SignalData;
import com.crashlytics.android.core.internal.models.ThreadData;
import com.crashlytics.android.core.internal.models.ThreadData.FrameData;
import io.fabric.sdk.android.Fabric;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class JsonCrashDataParser {
    private static final BinaryImageData[] EMPTY_BINARY_IMAGES;
    private static final FrameData[] EMPTY_FRAMES;
    private static final ThreadData[] EMPTY_THREADS;
    private final FileIdStrategy fileIdStrategy;

    static {
        EMPTY_BINARY_IMAGES = new BinaryImageData[0];
        EMPTY_THREADS = new ThreadData[0];
        EMPTY_FRAMES = new FrameData[0];
    }

    public JsonCrashDataParser() {
        this(new Sha1FileIdStrategy());
    }

    JsonCrashDataParser(FileIdStrategy fileIdStrategy) {
        this.fileIdStrategy = fileIdStrategy;
    }

    public SessionEventData parseCrashEventData(String jsonString) throws JSONException {
        JSONObject jsonCrash = new JSONObject(jsonString);
        return new SessionEventData(jsonCrash.optLong("time"), parseSignalData(jsonCrash), parseThreadData(jsonCrash), parseBinaryImageData(jsonCrash), parseCustomAttributes(jsonString), parseDeviceData(jsonCrash));
    }

    public DeviceData parseDeviceData(JSONObject jsonCrash) {
        return new DeviceData(jsonCrash.optInt("orientation"), jsonCrash.optLong("total_physical_memory"), jsonCrash.optLong("total_internal_storage"), jsonCrash.optLong("available_physical_memory"), jsonCrash.optLong("available_internal_storage"), jsonCrash.optInt("battery"), jsonCrash.optInt("battery_velocity", 1), jsonCrash.optBoolean("proximity_enabled", false));
    }

    public SignalData parseSignalData(JSONObject jsonCrash) {
        return new SignalData(jsonCrash.optString("sig_name", ""), jsonCrash.optString("sig_code", ""), jsonCrash.optLong("si_addr"));
    }

    public BinaryImageData[] parseBinaryImageData(JSONObject jsonCrash) {
        JSONArray entries = jsonCrash.optJSONArray("maps");
        if (entries == null) {
            return EMPTY_BINARY_IMAGES;
        }
        List<BinaryImageData> binaryImages = new LinkedList();
        for (int i = 0; i < entries.length(); i++) {
            ProcMapEntry mapInfo = ProcMapEntryParser.parse(entries.optString(i));
            if (mapInfo != null) {
                String path = mapInfo.path;
                try {
                    binaryImages.add(new BinaryImageData(mapInfo.address, mapInfo.size, path, this.fileIdStrategy.getId(getLibraryFile(path))));
                } catch (IOException e) {
                    Fabric.getLogger().m1454d("JsonCrashDataParser", "Could not generate ID for file " + mapInfo.path, e);
                }
            }
        }
        return (BinaryImageData[]) binaryImages.toArray(new BinaryImageData[binaryImages.size()]);
    }

    public ThreadData[] parseThreadData(JSONObject jsonCrash) {
        JSONArray jsonThreads = jsonCrash.optJSONArray("threads");
        if (jsonThreads == null) {
            return EMPTY_THREADS;
        }
        int len = jsonThreads.length();
        ThreadData[] threads = new ThreadData[len];
        for (int i = 0; i < len; i++) {
            JSONObject jsonThread = jsonThreads.optJSONObject(i);
            String threadName = jsonThread.optString("name");
            int threadImportance = jsonThread.optBoolean("crashed") ? 4 : 0;
            threads[i] = new ThreadData(threadName, threadImportance, parseFrameData(jsonThread, threadImportance));
        }
        return threads;
    }

    public FrameData[] parseFrameData(JSONObject jsonThread, int threadImportance) {
        JSONArray jsonFrames = jsonThread.optJSONArray("frames");
        if (jsonFrames == null) {
            return EMPTY_FRAMES;
        }
        int len = jsonFrames.length();
        FrameData[] frames = new FrameData[len];
        for (int i = 0; i < len; i++) {
            String symbol;
            JSONObject frame = jsonFrames.optJSONObject(i);
            long pc = frame.optLong("pc");
            String maybeSymbol = frame.optString("symbol");
            if (maybeSymbol == null) {
                symbol = "";
            } else {
                symbol = maybeSymbol;
            }
            frames[i] = new FrameData(pc, symbol, "", 0, threadImportance);
        }
        return frames;
    }

    public CustomAttributeData[] parseCustomAttributes(String json) {
        return new CustomAttributeData[]{new CustomAttributeData("json_session", json)};
    }

    private static File getLibraryFile(String path) {
        File libFile = new File(path);
        if (libFile.exists()) {
            return libFile;
        }
        return correctDataPath(libFile);
    }

    private static File correctDataPath(File missingFile) {
        if (!missingFile.getAbsolutePath().startsWith("/data")) {
            return missingFile;
        }
        try {
            Context context = CrashlyticsNdk.getInstance().getContext();
            return new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).nativeLibraryDir, missingFile.getName());
        } catch (NameNotFoundException e) {
            Fabric.getLogger().m1456e("JsonCrashDataParser", "Error getting ApplicationInfo", e);
            return missingFile;
        }
    }
}
