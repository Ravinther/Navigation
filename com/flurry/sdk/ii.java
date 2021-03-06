package com.flurry.sdk;

import android.os.Build;
import android.os.Build.VERSION;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ii {
    private static final String f709a;
    private byte[] f710b;

    static {
        f709a = ii.class.getSimpleName();
    }

    public ii(String str, String str2, boolean z, boolean z2, long j, long j2, List<ik> list, Map<iy, byte[]> map, Map<String, List<String>> map2, Map<String, List<String>> map3, Map<String, Map<String, String>> map4, long j3) throws IOException {
        byte[] bArr;
        Throwable th;
        this.f710b = null;
        Closeable closeable = null;
        Closeable dataOutputStream;
        try {
            MessageDigest jiVar = new ji();
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStream digestOutputStream = new DigestOutputStream(byteArrayOutputStream, jiVar);
            dataOutputStream = new DataOutputStream(digestOutputStream);
            try {
                int size;
                dataOutputStream.writeShort(30);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeLong(0);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(3);
                dataOutputStream.writeShort(jd.m963a());
                dataOutputStream.writeLong(j3);
                dataOutputStream.writeUTF(str);
                dataOutputStream.writeUTF(str2);
                dataOutputStream.writeShort(map.size());
                for (Entry entry : map.entrySet()) {
                    dataOutputStream.writeShort(((iy) entry.getKey()).f871d);
                    bArr = (byte[]) entry.getValue();
                    dataOutputStream.writeShort(bArr.length);
                    dataOutputStream.write(bArr);
                }
                dataOutputStream.writeByte(0);
                dataOutputStream.writeBoolean(z);
                dataOutputStream.writeBoolean(z2);
                dataOutputStream.writeLong(j);
                dataOutputStream.writeLong(j2);
                dataOutputStream.writeShort(6);
                dataOutputStream.writeUTF("device.model");
                dataOutputStream.writeUTF(Build.MODEL);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.brand");
                dataOutputStream.writeUTF(Build.BRAND);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.id");
                dataOutputStream.writeUTF(Build.ID);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("version.release");
                dataOutputStream.writeUTF(VERSION.RELEASE);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.device");
                dataOutputStream.writeUTF(Build.DEVICE);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeUTF("build.product");
                dataOutputStream.writeUTF(Build.PRODUCT);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeShort(map2 != null ? map2.keySet().size() : 0);
                if (map2 != null) {
                    jq.m1016a(3, f709a, "sending referrer values because it exists");
                    for (Entry entry2 : map2.entrySet()) {
                        jq.m1016a(3, f709a, "Referrer Entry:  " + ((String) entry2.getKey()) + "=" + entry2.getValue());
                        dataOutputStream.writeUTF((String) entry2.getKey());
                        jq.m1016a(3, f709a, "referrer key is :" + ((String) entry2.getKey()));
                        dataOutputStream.writeShort(((List) entry2.getValue()).size());
                        for (String str3 : (List) entry2.getValue()) {
                            dataOutputStream.writeUTF(str3);
                            jq.m1016a(3, f709a, "referrer value is :" + str3);
                        }
                    }
                }
                dataOutputStream.writeBoolean(false);
                if (map3 != null) {
                    size = map3.keySet().size();
                } else {
                    size = 0;
                }
                jq.m1016a(3, f709a, "optionsMapSize is:  " + size);
                dataOutputStream.writeShort(size);
                if (map3 != null) {
                    jq.m1016a(3, f709a, "sending launch options");
                    for (Entry entry22 : map3.entrySet()) {
                        jq.m1016a(3, f709a, "Launch Options Key:  " + ((String) entry22.getKey()));
                        dataOutputStream.writeUTF((String) entry22.getKey());
                        dataOutputStream.writeShort(((List) entry22.getValue()).size());
                        for (String str32 : (List) entry22.getValue()) {
                            dataOutputStream.writeUTF(str32);
                            jq.m1016a(3, f709a, "Launch Options value is :" + str32);
                        }
                    }
                }
                int size2 = map4 != null ? map4.keySet().size() : 0;
                jq.m1016a(3, f709a, "numOriginAttributions is:  " + size);
                dataOutputStream.writeShort(size2);
                if (map4 != null) {
                    for (Entry entry222 : map4.entrySet()) {
                        jq.m1016a(3, f709a, "Origin Atttribute Key:  " + ((String) entry222.getKey()));
                        dataOutputStream.writeUTF((String) entry222.getKey());
                        dataOutputStream.writeShort(((Map) entry222.getValue()).size());
                        jq.m1016a(3, f709a, "Origin Attribute Map Size for " + ((String) entry222.getKey()) + ":  " + ((Map) entry222.getValue()).size());
                        for (Entry entry3 : ((Map) entry222.getValue()).entrySet()) {
                            String str4;
                            jq.m1016a(3, f709a, "Origin Atttribute for " + ((String) entry222.getKey()) + ":  " + ((String) entry3.getKey()) + ":" + ((String) entry3.getValue()));
                            dataOutputStream.writeUTF(entry3.getKey() != null ? (String) entry3.getKey() : "");
                            if (entry3.getValue() != null) {
                                str4 = (String) entry3.getValue();
                            } else {
                                str4 = "";
                            }
                            dataOutputStream.writeUTF(str4);
                        }
                    }
                }
                dataOutputStream.writeUTF(kz.m1236c(jc.m946a().m957c()));
                size = list.size();
                dataOutputStream.writeShort(size);
                for (int i = 0; i < size; i++) {
                    dataOutputStream.write(((ik) list.get(i)).m712a());
                }
                dataOutputStream.writeShort(0);
                digestOutputStream.on(false);
                dataOutputStream.write(jiVar.m991a());
                dataOutputStream.close();
                bArr = byteArrayOutputStream.toByteArray();
                lc.m1265a(dataOutputStream);
            } catch (Throwable th2) {
                th = th2;
                lc.m1265a(dataOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            dataOutputStream = null;
            lc.m1265a(dataOutputStream);
            throw th;
        }
        this.f710b = bArr;
    }

    public byte[] m678a() {
        return this.f710b;
    }
}
