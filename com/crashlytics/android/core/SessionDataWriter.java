package com.crashlytics.android.core;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build.VERSION;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class SessionDataWriter {
    private static final ByteString SIGNAL_DEFAULT_BYTE_STRING;
    private final Context context;
    private StackTraceElement[] exceptionStack;
    private final int maxChainedExceptionsDepth;
    private final ByteString optionalBuildIdBytes;
    private final ByteString packageNameBytes;
    private RunningAppProcessInfo runningAppProcessInfo;
    private List<StackTraceElement[]> stacks;
    private Thread[] threads;

    static {
        SIGNAL_DEFAULT_BYTE_STRING = ByteString.copyFromUtf8("0");
    }

    public SessionDataWriter(Context context, String buildId, String packageName) {
        this.maxChainedExceptionsDepth = 8;
        this.context = context;
        this.packageNameBytes = ByteString.copyFromUtf8(packageName);
        this.optionalBuildIdBytes = buildId == null ? null : ByteString.copyFromUtf8(buildId.replace("-", ""));
    }

    public void writeBeginSession(CodedOutputStream cos, String sessionId, String generator, long startedAtSeconds) throws Exception {
        cos.writeBytes(1, ByteString.copyFromUtf8(generator));
        cos.writeBytes(2, ByteString.copyFromUtf8(sessionId));
        cos.writeUInt64(3, startedAtSeconds);
    }

    public void writeSessionApp(CodedOutputStream cos, String packageName, String versionCode, String versionName, String installUuid, int deliveryMechanism) throws Exception {
        ByteString packageNameBytes = ByteString.copyFromUtf8(packageName);
        ByteString versionCodeBytes = ByteString.copyFromUtf8(versionCode);
        ByteString versionNameBytes = ByteString.copyFromUtf8(versionName);
        ByteString installIdBytes = ByteString.copyFromUtf8(installUuid);
        cos.writeTag(7, 2);
        cos.writeRawVarint32(getSessionAppSize(packageNameBytes, versionCodeBytes, versionNameBytes, installIdBytes, deliveryMechanism));
        cos.writeBytes(1, packageNameBytes);
        cos.writeBytes(2, versionCodeBytes);
        cos.writeBytes(3, versionNameBytes);
        cos.writeTag(5, 2);
        cos.writeRawVarint32(getSessionAppOrgSize());
        cos.writeString(1, new ApiKey().getValue(this.context));
        cos.writeBytes(6, installIdBytes);
        cos.writeEnum(10, deliveryMechanism);
    }

    public void writeSessionOS(CodedOutputStream cos, boolean isRooted) throws Exception {
        ByteString releaseBytes = ByteString.copyFromUtf8(VERSION.RELEASE);
        ByteString codeNameBytes = ByteString.copyFromUtf8(VERSION.CODENAME);
        cos.writeTag(8, 2);
        cos.writeRawVarint32(getSessionOSSize(releaseBytes, codeNameBytes, isRooted));
        cos.writeEnum(1, 3);
        cos.writeBytes(2, releaseBytes);
        cos.writeBytes(3, codeNameBytes);
        cos.writeBool(4, isRooted);
    }

    public void writeSessionDevice(CodedOutputStream cos, String clsDeviceId, int arch, String model, int availableProcessors, long totalRam, long diskSpace, boolean isEmulator, Map<DeviceIdentifierType, String> ids, int state, String manufacturer, String modelClass) throws Exception {
        ByteString clsDeviceIDBytes = ByteString.copyFromUtf8(clsDeviceId);
        ByteString modelBytes = stringToByteString(model);
        ByteString modelClassBytes = stringToByteString(modelClass);
        ByteString manufacturerBytes = stringToByteString(manufacturer);
        cos.writeTag(9, 2);
        cos.writeRawVarint32(getSessionDeviceSize(arch, clsDeviceIDBytes, modelBytes, availableProcessors, totalRam, diskSpace, isEmulator, ids, state, manufacturerBytes, modelClassBytes));
        cos.writeBytes(1, clsDeviceIDBytes);
        cos.writeEnum(3, arch);
        cos.writeBytes(4, modelBytes);
        cos.writeUInt32(5, availableProcessors);
        cos.writeUInt64(6, totalRam);
        cos.writeUInt64(7, diskSpace);
        cos.writeBool(10, isEmulator);
        for (Entry<DeviceIdentifierType, String> id : ids.entrySet()) {
            cos.writeTag(11, 2);
            cos.writeRawVarint32(getDeviceIdentifierSize((DeviceIdentifierType) id.getKey(), (String) id.getValue()));
            cos.writeEnum(1, ((DeviceIdentifierType) id.getKey()).protobufIndex);
            cos.writeBytes(2, ByteString.copyFromUtf8((String) id.getValue()));
        }
        cos.writeUInt32(12, state);
        if (manufacturerBytes != null) {
            cos.writeBytes(13, manufacturerBytes);
        }
        if (modelClassBytes != null) {
            cos.writeBytes(14, modelClassBytes);
        }
    }

    public void writeSessionUser(CodedOutputStream cos, String id, String name, String email) throws Exception {
        if (id == null) {
            id = "";
        }
        ByteString idBytes = ByteString.copyFromUtf8(id);
        ByteString nameBytes = stringToByteString(name);
        ByteString emailBytes = stringToByteString(email);
        int size = 0 + CodedOutputStream.computeBytesSize(1, idBytes);
        if (name != null) {
            size += CodedOutputStream.computeBytesSize(2, nameBytes);
        }
        if (email != null) {
            size += CodedOutputStream.computeBytesSize(3, emailBytes);
        }
        cos.writeTag(6, 2);
        cos.writeRawVarint32(size);
        cos.writeBytes(1, idBytes);
        if (name != null) {
            cos.writeBytes(2, nameBytes);
        }
        if (email != null) {
            cos.writeBytes(3, emailBytes);
        }
    }

    public void writeSessionEvent(CodedOutputStream cos, long eventTime, Thread thread, Throwable ex, String eventType, Thread[] threads, float batteryLevel, int batteryVelocity, boolean proximityEnabled, int orientation, long usedRamBytes, long diskUsedBytes, RunningAppProcessInfo runningAppProcessInfo, List<StackTraceElement[]> stacks, StackTraceElement[] exceptionStack, LogFileManager logFileManager, Map<String, String> attributes) throws Exception {
        this.runningAppProcessInfo = runningAppProcessInfo;
        this.stacks = stacks;
        this.exceptionStack = exceptionStack;
        this.threads = threads;
        ByteString logByteString = logFileManager.getByteStringForLog();
        if (logByteString == null) {
            Fabric.getLogger().m1453d("Fabric", "No log data to include with this event.");
        }
        logFileManager.clearLog();
        cos.writeTag(10, 2);
        cos.writeRawVarint32(getSessionEventSize(thread, ex, eventType, eventTime, attributes, batteryLevel, batteryVelocity, proximityEnabled, orientation, usedRamBytes, diskUsedBytes, logByteString));
        cos.writeUInt64(1, eventTime);
        cos.writeBytes(2, ByteString.copyFromUtf8(eventType));
        writeSessionEventApp(cos, thread, ex, orientation, attributes);
        writeSessionEventDevice(cos, batteryLevel, batteryVelocity, proximityEnabled, orientation, usedRamBytes, diskUsedBytes);
        writeSessionEventLog(cos, logByteString);
    }

    private void writeSessionEventApp(CodedOutputStream cos, Thread thread, Throwable ex, int orientation, Map<String, String> customAttributes) throws Exception {
        cos.writeTag(3, 2);
        cos.writeRawVarint32(getEventAppSize(thread, ex, orientation, customAttributes));
        writeSessionEventAppExecution(cos, thread, ex);
        if (!(customAttributes == null || customAttributes.isEmpty())) {
            writeSessionEventAppCustomAttributes(cos, customAttributes);
        }
        if (this.runningAppProcessInfo != null) {
            cos.writeBool(3, this.runningAppProcessInfo.importance != 100);
        }
        cos.writeUInt32(4, orientation);
    }

    private void writeSessionEventAppExecution(CodedOutputStream cos, Thread exceptionThread, Throwable ex) throws Exception {
        cos.writeTag(1, 2);
        cos.writeRawVarint32(getEventAppExecutionSize(exceptionThread, ex));
        writeThread(cos, exceptionThread, this.exceptionStack, 4, true);
        int len = this.threads.length;
        for (int i = 0; i < len; i++) {
            writeThread(cos, this.threads[i], (StackTraceElement[]) this.stacks.get(i), 0, false);
        }
        writeSessionEventAppExecutionException(cos, ex, 1, 2);
        cos.writeTag(3, 2);
        cos.writeRawVarint32(getEventAppExecutionSignalSize());
        cos.writeBytes(1, SIGNAL_DEFAULT_BYTE_STRING);
        cos.writeBytes(2, SIGNAL_DEFAULT_BYTE_STRING);
        cos.writeUInt64(3, 0);
        cos.writeTag(4, 2);
        cos.writeRawVarint32(getBinaryImageSize());
        cos.writeUInt64(1, 0);
        cos.writeUInt64(2, 0);
        cos.writeBytes(3, this.packageNameBytes);
        if (this.optionalBuildIdBytes != null) {
            cos.writeBytes(4, this.optionalBuildIdBytes);
        }
    }

    private void writeSessionEventAppCustomAttributes(CodedOutputStream cos, Map<String, String> customAttributes) throws Exception {
        for (Entry<String, String> entry : customAttributes.entrySet()) {
            cos.writeTag(2, 2);
            cos.writeRawVarint32(getEventAppCustomAttributeSize((String) entry.getKey(), (String) entry.getValue()));
            cos.writeBytes(1, ByteString.copyFromUtf8((String) entry.getKey()));
            String value = (String) entry.getValue();
            if (value == null) {
                value = "";
            }
            cos.writeBytes(2, ByteString.copyFromUtf8(value));
        }
    }

    private void writeSessionEventAppExecutionException(CodedOutputStream cos, Throwable ex, int chainDepth, int field) throws Exception {
        cos.writeTag(field, 2);
        cos.writeRawVarint32(getEventAppExecutionExceptionSize(ex, 1));
        cos.writeBytes(1, ByteString.copyFromUtf8(ex.getClass().getName()));
        String message = ex.getLocalizedMessage();
        if (message != null) {
            cos.writeBytes(3, ByteString.copyFromUtf8(message));
        }
        for (StackTraceElement element : ex.getStackTrace()) {
            writeFrame(cos, 4, element, true);
        }
        Throwable cause = ex.getCause();
        if (cause == null) {
            return;
        }
        if (chainDepth < this.maxChainedExceptionsDepth) {
            writeSessionEventAppExecutionException(cos, cause, chainDepth + 1, 6);
            return;
        }
        int overflowCount = 0;
        while (cause != null) {
            cause = cause.getCause();
            overflowCount++;
        }
        cos.writeUInt32(7, overflowCount);
    }

    private void writeThread(CodedOutputStream cos, Thread thread, StackTraceElement[] stackTraceElements, int importance, boolean isCrashedThread) throws Exception {
        cos.writeTag(1, 2);
        cos.writeRawVarint32(getThreadSize(thread, stackTraceElements, importance, isCrashedThread));
        cos.writeBytes(1, ByteString.copyFromUtf8(thread.getName()));
        cos.writeUInt32(2, importance);
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            writeFrame(cos, 3, stackTraceElement, isCrashedThread);
        }
    }

    private void writeFrame(CodedOutputStream cos, int fieldIndex, StackTraceElement element, boolean isCrashedThread) throws Exception {
        int i = 4;
        cos.writeTag(fieldIndex, 2);
        cos.writeRawVarint32(getFrameSize(element, isCrashedThread));
        if (element.isNativeMethod()) {
            cos.writeUInt64(1, (long) Math.max(element.getLineNumber(), 0));
        } else {
            cos.writeUInt64(1, 0);
        }
        cos.writeBytes(2, ByteString.copyFromUtf8(element.getClassName() + "." + element.getMethodName()));
        if (element.getFileName() != null) {
            cos.writeBytes(3, ByteString.copyFromUtf8(element.getFileName()));
        }
        if (!element.isNativeMethod() && element.getLineNumber() > 0) {
            cos.writeUInt64(4, (long) element.getLineNumber());
        }
        if (!isCrashedThread) {
            i = 0;
        }
        cos.writeUInt32(5, i);
    }

    private void writeSessionEventDevice(CodedOutputStream cos, float batteryLevel, int batterVelocity, boolean proximityEnabled, int orientation, long heapAllocatedSize, long diskUsed) throws Exception {
        cos.writeTag(5, 2);
        cos.writeRawVarint32(getEventDeviceSize(batteryLevel, batterVelocity, proximityEnabled, orientation, heapAllocatedSize, diskUsed));
        cos.writeFloat(1, batteryLevel);
        cos.writeSInt32(2, batterVelocity);
        cos.writeBool(3, proximityEnabled);
        cos.writeUInt32(4, orientation);
        cos.writeUInt64(5, heapAllocatedSize);
        cos.writeUInt64(6, diskUsed);
    }

    private void writeSessionEventLog(CodedOutputStream cos, ByteString log) throws Exception {
        if (log != null) {
            cos.writeTag(6, 2);
            cos.writeRawVarint32(getEventLogSize(log));
            cos.writeBytes(1, log);
        }
    }

    private int getSessionAppSize(ByteString packageName, ByteString versionCode, ByteString versionName, ByteString installUuid, int deliveryMechanism) {
        int size = ((0 + CodedOutputStream.computeBytesSize(1, packageName)) + CodedOutputStream.computeBytesSize(2, versionCode)) + CodedOutputStream.computeBytesSize(3, versionName);
        int orgSize = getSessionAppOrgSize();
        return ((size + ((CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(orgSize)) + orgSize)) + CodedOutputStream.computeBytesSize(6, installUuid)) + CodedOutputStream.computeEnumSize(10, deliveryMechanism);
    }

    private int getSessionAppOrgSize() {
        return 0 + CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(new ApiKey().getValue(this.context)));
    }

    private int getSessionOSSize(ByteString release, ByteString codeName, boolean isRooted) {
        return (((0 + CodedOutputStream.computeEnumSize(1, 3)) + CodedOutputStream.computeBytesSize(2, release)) + CodedOutputStream.computeBytesSize(3, codeName)) + CodedOutputStream.computeBoolSize(4, isRooted);
    }

    private int getDeviceIdentifierSize(DeviceIdentifierType type, String value) {
        return CodedOutputStream.computeEnumSize(1, type.protobufIndex) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(value));
    }

    private int getSessionDeviceSize(int arch, ByteString clsDeviceID, ByteString model, int availableProcessors, long totalRam, long diskSpace, boolean isEmulator, Map<DeviceIdentifierType, String> ids, int state, ByteString manufacturer, ByteString modelClass) {
        int i;
        int size = (0 + CodedOutputStream.computeBytesSize(1, clsDeviceID)) + CodedOutputStream.computeEnumSize(3, arch);
        if (model == null) {
            i = 0;
        } else {
            i = CodedOutputStream.computeBytesSize(4, model);
        }
        size = ((((size + i) + CodedOutputStream.computeUInt32Size(5, availableProcessors)) + CodedOutputStream.computeUInt64Size(6, totalRam)) + CodedOutputStream.computeUInt64Size(7, diskSpace)) + CodedOutputStream.computeBoolSize(10, isEmulator);
        if (ids != null) {
            for (Entry<DeviceIdentifierType, String> id : ids.entrySet()) {
                int idSize = getDeviceIdentifierSize((DeviceIdentifierType) id.getKey(), (String) id.getValue());
                size += (CodedOutputStream.computeTagSize(11) + CodedOutputStream.computeRawVarint32Size(idSize)) + idSize;
            }
        }
        return ((size + CodedOutputStream.computeUInt32Size(12, state)) + (manufacturer == null ? 0 : CodedOutputStream.computeBytesSize(13, manufacturer))) + (modelClass == null ? 0 : CodedOutputStream.computeBytesSize(14, modelClass));
    }

    private int getBinaryImageSize() {
        int size = ((0 + CodedOutputStream.computeUInt64Size(1, 0)) + CodedOutputStream.computeUInt64Size(2, 0)) + CodedOutputStream.computeBytesSize(3, this.packageNameBytes);
        if (this.optionalBuildIdBytes != null) {
            return size + CodedOutputStream.computeBytesSize(4, this.optionalBuildIdBytes);
        }
        return size;
    }

    private int getSessionEventSize(Thread thread, Throwable ex, String eventType, long eventTime, Map<String, String> custAttrs, float batteryLevel, int batterVelocity, boolean proximityEnabled, int orientation, long heapAllocatedSize, long diskUsed, ByteString log) {
        int size = (0 + CodedOutputStream.computeUInt64Size(1, eventTime)) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(eventType));
        int eventAppSize = getEventAppSize(thread, ex, orientation, custAttrs);
        size += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(eventAppSize)) + eventAppSize;
        int eventDeviceSize = getEventDeviceSize(batteryLevel, batterVelocity, proximityEnabled, orientation, heapAllocatedSize, diskUsed);
        size += (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(eventDeviceSize)) + eventDeviceSize;
        if (log == null) {
            return size;
        }
        int logSize = getEventLogSize(log);
        return size + ((CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(logSize)) + logSize);
    }

    private int getEventAppSize(Thread thread, Throwable ex, int orientation, Map<String, String> customAttributes) {
        int executionSize = getEventAppExecutionSize(thread, ex);
        int size = 0 + ((CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(executionSize)) + executionSize);
        if (customAttributes != null) {
            for (Entry<String, String> entry : customAttributes.entrySet()) {
                int entrySize = getEventAppCustomAttributeSize((String) entry.getKey(), (String) entry.getValue());
                size += (CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(entrySize)) + entrySize;
            }
        }
        if (this.runningAppProcessInfo != null) {
            size += CodedOutputStream.computeBoolSize(3, this.runningAppProcessInfo.importance != 100);
        }
        return size + CodedOutputStream.computeUInt32Size(4, orientation);
    }

    private int getEventAppExecutionSize(Thread exceptionThread, Throwable ex) {
        int threadSize = getThreadSize(exceptionThread, this.exceptionStack, 4, true);
        int size = 0 + ((CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(threadSize)) + threadSize);
        int len = this.threads.length;
        for (int i = 0; i < len; i++) {
            threadSize = getThreadSize(this.threads[i], (StackTraceElement[]) this.stacks.get(i), 0, false);
            size += (CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(threadSize)) + threadSize;
        }
        int exceptionSize = getEventAppExecutionExceptionSize(ex, 1);
        size += (CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(exceptionSize)) + exceptionSize;
        int signalSize = getEventAppExecutionSignalSize();
        size += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(signalSize)) + signalSize;
        int binaryImageSize = getBinaryImageSize();
        return size + ((CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(binaryImageSize)) + binaryImageSize);
    }

    private int getEventAppCustomAttributeSize(String key, String value) {
        int size = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(key));
        if (value == null) {
            value = "";
        }
        return size + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(value));
    }

    private int getEventDeviceSize(float batteryLevel, int batterVelocity, boolean proximityEnabled, int orientation, long heapAllocatedSize, long diskUsed) {
        return (((((0 + CodedOutputStream.computeFloatSize(1, batteryLevel)) + CodedOutputStream.computeSInt32Size(2, batterVelocity)) + CodedOutputStream.computeBoolSize(3, proximityEnabled)) + CodedOutputStream.computeUInt32Size(4, orientation)) + CodedOutputStream.computeUInt64Size(5, heapAllocatedSize)) + CodedOutputStream.computeUInt64Size(6, diskUsed);
    }

    private int getEventLogSize(ByteString log) {
        return CodedOutputStream.computeBytesSize(1, log);
    }

    private int getEventAppExecutionExceptionSize(Throwable ex, int chainDepth) {
        int size = 0 + CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(ex.getClass().getName()));
        String message = ex.getLocalizedMessage();
        if (message != null) {
            size += CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(message));
        }
        for (StackTraceElement element : ex.getStackTrace()) {
            int frameSize = getFrameSize(element, true);
            size += (CodedOutputStream.computeTagSize(4) + CodedOutputStream.computeRawVarint32Size(frameSize)) + frameSize;
        }
        Throwable cause = ex.getCause();
        if (cause == null) {
            return size;
        }
        if (chainDepth < this.maxChainedExceptionsDepth) {
            int exceptionSize = getEventAppExecutionExceptionSize(cause, chainDepth + 1);
            return size + ((CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(exceptionSize)) + exceptionSize);
        }
        int overflowCount = 0;
        while (cause != null) {
            cause = cause.getCause();
            overflowCount++;
        }
        return size + CodedOutputStream.computeUInt32Size(7, overflowCount);
    }

    private int getEventAppExecutionSignalSize() {
        return ((0 + CodedOutputStream.computeBytesSize(1, SIGNAL_DEFAULT_BYTE_STRING)) + CodedOutputStream.computeBytesSize(2, SIGNAL_DEFAULT_BYTE_STRING)) + CodedOutputStream.computeUInt64Size(3, 0);
    }

    private int getFrameSize(StackTraceElement element, boolean isCrashedThread) {
        int size;
        int i = 2;
        if (element.isNativeMethod()) {
            size = 0 + CodedOutputStream.computeUInt64Size(1, (long) Math.max(element.getLineNumber(), 0));
        } else {
            size = 0 + CodedOutputStream.computeUInt64Size(1, 0);
        }
        size += CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(element.getClassName() + "." + element.getMethodName()));
        if (element.getFileName() != null) {
            size += CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(element.getFileName()));
        }
        if (!element.isNativeMethod() && element.getLineNumber() > 0) {
            size += CodedOutputStream.computeUInt64Size(4, (long) element.getLineNumber());
        }
        if (!isCrashedThread) {
            i = 0;
        }
        return size + CodedOutputStream.computeUInt32Size(5, i);
    }

    private int getThreadSize(Thread thread, StackTraceElement[] stackTraceElements, int importance, boolean isCrashedThread) {
        int size = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(thread.getName())) + CodedOutputStream.computeUInt32Size(2, importance);
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            int frameSize = getFrameSize(stackTraceElement, isCrashedThread);
            size += (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(frameSize)) + frameSize;
        }
        return size;
    }

    private ByteString stringToByteString(String s) {
        return s == null ? null : ByteString.copyFromUtf8(s);
    }
}
