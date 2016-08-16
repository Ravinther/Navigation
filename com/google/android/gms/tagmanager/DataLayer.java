package com.google.android.gms.tagmanager;

import com.sygic.aura.C1090R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final Object OBJECT_NOT_PRESENT;
    private static final Pattern zzaPA;
    static final String[] zzaPz;
    private final ConcurrentHashMap<zzb, Integer> zzaPB;
    private final Map<String, Object> zzaPC;
    private final ReentrantLock zzaPD;
    private final LinkedList<Map<String, Object>> zzaPE;
    private final zzc zzaPF;
    private final CountDownLatch zzaPG;

    interface zzc {

        public interface zza {
            void zzo(List<zza> list);
        }

        void zza(zza com_google_android_gms_tagmanager_DataLayer_zzc_zza);

        void zza(List<zza> list, long j);

        void zzeE(String str);
    }

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.1 */
    class C10031 implements zzc {
        C10031() {
        }

        public void zza(zza com_google_android_gms_tagmanager_DataLayer_zzc_zza) {
            com_google_android_gms_tagmanager_DataLayer_zzc_zza.zzo(new ArrayList());
        }

        public void zza(List<zza> list, long j) {
        }

        public void zzeE(String str) {
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.2 */
    class C10042 implements zza {
        final /* synthetic */ DataLayer zzaPH;

        C10042(DataLayer dataLayer) {
            this.zzaPH = dataLayer;
        }

        public void zzo(List<zza> list) {
            for (zza com_google_android_gms_tagmanager_DataLayer_zza : list) {
                this.zzaPH.zzJ(this.zzaPH.zzj(com_google_android_gms_tagmanager_DataLayer_zza.zztP, com_google_android_gms_tagmanager_DataLayer_zza.zzID));
            }
            this.zzaPH.zzaPG.countDown();
        }
    }

    static final class zza {
        public final Object zzID;
        public final String zztP;

        zza(String str, Object obj) {
            this.zztP = str;
            this.zzID = obj;
        }

        public boolean equals(Object o) {
            if (!(o instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_tagmanager_DataLayer_zza = (zza) o;
            return this.zztP.equals(com_google_android_gms_tagmanager_DataLayer_zza.zztP) && this.zzID.equals(com_google_android_gms_tagmanager_DataLayer_zza.zzID);
        }

        public int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.zztP.hashCode()), Integer.valueOf(this.zzID.hashCode())});
        }

        public String toString() {
            return "Key: " + this.zztP + " value: " + this.zzID.toString();
        }
    }

    interface zzb {
        void zzH(Map<String, Object> map);
    }

    static {
        OBJECT_NOT_PRESENT = new Object();
        zzaPz = "gtm.lifetime".toString().split("\\.");
        zzaPA = Pattern.compile("(\\d+)\\s*([smhd]?)");
    }

    DataLayer() {
        this(new C10031());
    }

    DataLayer(zzc persistentStore) {
        this.zzaPF = persistentStore;
        this.zzaPB = new ConcurrentHashMap();
        this.zzaPC = new HashMap();
        this.zzaPD = new ReentrantLock();
        this.zzaPE = new LinkedList();
        this.zzaPG = new CountDownLatch(1);
        zzzO();
    }

    public static Map<String, Object> mapOf(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        Map<String, Object> hashMap = new HashMap();
        int i = 0;
        while (i < objects.length) {
            if (objects[i] instanceof String) {
                hashMap.put((String) objects[i], objects[i + 1]);
                i += 2;
            } else {
                throw new IllegalArgumentException("key is not a string: " + objects[i]);
            }
        }
        return hashMap;
    }

    private void zzJ(Map<String, Object> map) {
        this.zzaPD.lock();
        try {
            this.zzaPE.offer(map);
            if (this.zzaPD.getHoldCount() == 1) {
                zzzP();
            }
            zzK(map);
        } finally {
            this.zzaPD.unlock();
        }
    }

    private void zzK(Map<String, Object> map) {
        Long zzL = zzL(map);
        if (zzL != null) {
            List zzN = zzN(map);
            zzN.remove("gtm.lifetime");
            this.zzaPF.zza(zzN, zzL.longValue());
        }
    }

    private Long zzL(Map<String, Object> map) {
        Object zzM = zzM(map);
        return zzM == null ? null : zzeD(zzM.toString());
    }

    private Object zzM(Map<String, Object> map) {
        String[] strArr = zzaPz;
        int length = strArr.length;
        int i = 0;
        Object obj = map;
        while (i < length) {
            Object obj2 = strArr[i];
            if (!(obj instanceof Map)) {
                return null;
            }
            i++;
            obj = ((Map) obj).get(obj2);
        }
        return obj;
    }

    private List<zza> zzN(Map<String, Object> map) {
        Object arrayList = new ArrayList();
        zza(map, "", arrayList);
        return arrayList;
    }

    private void zzO(Map<String, Object> map) {
        synchronized (this.zzaPC) {
            for (String str : map.keySet()) {
                zzc(zzj(str, map.get(str)), this.zzaPC);
            }
        }
        zzP(map);
    }

    private void zzP(Map<String, Object> map) {
        for (zzb zzH : this.zzaPB.keySet()) {
            zzH.zzH(map);
        }
    }

    private void zza(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Entry entry : map.entrySet()) {
            String str2 = str + (str.length() == 0 ? "" : ".") + ((String) entry.getKey());
            if (entry.getValue() instanceof Map) {
                zza((Map) entry.getValue(), str2, collection);
            } else if (!str2.equals("gtm.lifetime")) {
                collection.add(new zza(str2, entry.getValue()));
            }
        }
    }

    static Long zzeD(String str) {
        Matcher matcher = zzaPA.matcher(str);
        if (matcher.matches()) {
            long parseLong;
            try {
                parseLong = Long.parseLong(matcher.group(1));
            } catch (NumberFormatException e) {
                zzbg.zzaE("illegal number in _lifetime value: " + str);
                parseLong = 0;
            }
            if (parseLong <= 0) {
                zzbg.zzaD("non-positive _lifetime: " + str);
                return null;
            }
            String group = matcher.group(2);
            if (group.length() == 0) {
                return Long.valueOf(parseLong);
            }
            switch (group.charAt(0)) {
                case C1090R.styleable.Theme_checkboxStyle /*100*/:
                    return Long.valueOf((((parseLong * 1000) * 60) * 60) * 24);
                case C1090R.styleable.Theme_ratingBarStyle /*104*/:
                    return Long.valueOf(((parseLong * 1000) * 60) * 60);
                case 'm':
                    return Long.valueOf((parseLong * 1000) * 60);
                case 's':
                    return Long.valueOf(parseLong * 1000);
                default:
                    zzbg.zzaE("unknown units in _lifetime: " + str);
                    return null;
            }
        }
        zzbg.zzaD("unknown _lifetime: " + str);
        return null;
    }

    private void zzzO() {
        this.zzaPF.zza(new C10042(this));
    }

    private void zzzP() {
        int i = 0;
        while (true) {
            Map map = (Map) this.zzaPE.poll();
            if (map != null) {
                zzO(map);
                int i2 = i + 1;
                if (i2 > 500) {
                    break;
                }
                i = i2;
            } else {
                return;
            }
        }
        this.zzaPE.clear();
        throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
    }

    public Object get(String key) {
        synchronized (this.zzaPC) {
            Map map = this.zzaPC;
            String[] split = key.split("\\.");
            int length = split.length;
            Object obj = map;
            int i = 0;
            while (i < length) {
                Object obj2 = split[i];
                if (obj instanceof Map) {
                    obj2 = ((Map) obj).get(obj2);
                    if (obj2 == null) {
                        return null;
                    }
                    i++;
                    obj = obj2;
                } else {
                    return null;
                }
            }
            return obj;
        }
    }

    public void push(String key, Object value) {
        push(zzj(key, value));
    }

    public void push(Map<String, Object> update) {
        try {
            this.zzaPG.await();
        } catch (InterruptedException e) {
            zzbg.zzaE("DataLayer.push: unexpected InterruptedException");
        }
        zzJ(update);
    }

    public void pushEvent(String eventName, Map<String, Object> update) {
        Map hashMap = new HashMap(update);
        hashMap.put("event", eventName);
        push(hashMap);
    }

    public String toString() {
        String stringBuilder;
        synchronized (this.zzaPC) {
            StringBuilder stringBuilder2 = new StringBuilder();
            for (Entry entry : this.zzaPC.entrySet()) {
                stringBuilder2.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", new Object[]{entry.getKey(), entry.getValue()}));
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }

    void zza(zzb com_google_android_gms_tagmanager_DataLayer_zzb) {
        this.zzaPB.put(com_google_android_gms_tagmanager_DataLayer_zzb, Integer.valueOf(0));
    }

    void zzb(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                zzb((List) obj, (List) list2.get(i));
            } else if (obj instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                zzc((Map) obj, (Map) list2.get(i));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i, obj);
            }
        }
    }

    void zzc(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                zzb((List) obj, (List) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                zzc((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    void zzeC(String str) {
        push(str, null);
        this.zzaPF.zzeE(str);
    }

    Map<String, Object> zzj(String str, Object obj) {
        Map hashMap = new HashMap();
        String[] split = str.toString().split("\\.");
        int i = 0;
        Map map = hashMap;
        while (i < split.length - 1) {
            HashMap hashMap2 = new HashMap();
            map.put(split[i], hashMap2);
            i++;
            Object obj2 = hashMap2;
        }
        map.put(split[split.length - 1], obj);
        return hashMap;
    }
}
