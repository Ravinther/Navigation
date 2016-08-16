package io.fabric.sdk.android.services.common;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public class InstallerPackageNameProvider {
    private final MemoryValueCache<String> installerPackageNameCache;
    private final ValueLoader<String> installerPackageNameLoader;

    /* renamed from: io.fabric.sdk.android.services.common.InstallerPackageNameProvider.1 */
    class C18181 implements ValueLoader<String> {
        C18181() {
        }

        public String load(Context context) throws Exception {
            String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
            return installerPackageName == null ? "" : installerPackageName;
        }
    }

    public InstallerPackageNameProvider() {
        this.installerPackageNameLoader = new C18181();
        this.installerPackageNameCache = new MemoryValueCache();
    }

    public String getInstallerPackageName(Context appContext) {
        try {
            String name = (String) this.installerPackageNameCache.get(appContext, this.installerPackageNameLoader);
            if ("".equals(name)) {
                return null;
            }
            return name;
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Fabric", "Failed to determine installer package name", e);
            return null;
        }
    }
}
