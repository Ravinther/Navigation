package com.google.ads.conversiontracking;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import com.google.ads.conversiontracking.C0556i.C0555a;
import java.io.IOException;
import java.util.List;

/* renamed from: com.google.ads.conversiontracking.a */
public class C0536a {
    private Context f1137a;

    /* renamed from: com.google.ads.conversiontracking.a.a */
    static class C0533a extends ContextWrapper {
        private final C0534b f1133a;
        private final C0535c f1134b;

        public C0533a(Context context) {
            super(context);
            this.f1133a = new C0534b(context);
            this.f1134b = new C0535c(context.getResources());
        }

        public PackageManager getPackageManager() {
            return this.f1133a;
        }

        public Resources getResources() {
            return this.f1134b;
        }
    }

    /* renamed from: com.google.ads.conversiontracking.a.b */
    static class C0534b extends PackageManager {
        private final Context f1135a;
        private final PackageManager f1136b;

        public C0534b(Context context) {
            this.f1135a = context;
            this.f1136b = context.getPackageManager();
        }

        public ApplicationInfo getApplicationInfo(String packageName, int flags) throws NameNotFoundException {
            ApplicationInfo applicationInfo = this.f1136b.getApplicationInfo(packageName, flags);
            if (packageName.equals(this.f1135a.getPackageName()) && (flags & 128) == 128) {
                if (applicationInfo.metaData == null) {
                    applicationInfo.metaData = new Bundle();
                }
                applicationInfo.metaData.putInt("com.google.android.gms.version", 4323000);
            }
            return applicationInfo;
        }

        public PackageInfo getPackageInfo(String packageName, int flags) throws NameNotFoundException {
            return this.f1136b.getPackageInfo(packageName, flags);
        }

        public void addPackageToPreferred(String packageName) {
            this.f1136b.addPackageToPreferred(packageName);
        }

        public boolean addPermission(PermissionInfo info) {
            return this.f1136b.addPermission(info);
        }

        public boolean addPermissionAsync(PermissionInfo info) {
            return this.f1136b.addPermissionAsync(info);
        }

        public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
            this.f1136b.addPreferredActivity(filter, match, set, activity);
        }

        public String[] canonicalToCurrentPackageNames(String[] names) {
            return this.f1136b.canonicalToCurrentPackageNames(names);
        }

        public int checkPermission(String permName, String pkgName) {
            return this.f1136b.checkPermission(permName, pkgName);
        }

        public int checkSignatures(int uid1, int uid2) {
            return this.f1136b.checkSignatures(uid1, uid2);
        }

        public int checkSignatures(String pkg1, String pkg2) {
            return this.f1136b.checkSignatures(pkg1, pkg2);
        }

        public void clearPackagePreferredActivities(String packageName) {
            this.f1136b.clearPackagePreferredActivities(packageName);
        }

        public String[] currentToCanonicalPackageNames(String[] names) {
            return this.f1136b.currentToCanonicalPackageNames(names);
        }

        public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) {
            this.f1136b.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay);
        }

        public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
            return this.f1136b.getActivityIcon(intent);
        }

        public Drawable getActivityIcon(ComponentName activityName) throws NameNotFoundException {
            return this.f1136b.getActivityIcon(activityName);
        }

        public ActivityInfo getActivityInfo(ComponentName component, int flags) throws NameNotFoundException {
            return this.f1136b.getActivityInfo(component, flags);
        }

        public Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
            return this.f1136b.getActivityLogo(intent);
        }

        public Drawable getActivityLogo(ComponentName activityName) throws NameNotFoundException {
            return this.f1136b.getActivityLogo(activityName);
        }

        public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
            return this.f1136b.getAllPermissionGroups(flags);
        }

        public int getApplicationEnabledSetting(String packageName) {
            return this.f1136b.getApplicationEnabledSetting(packageName);
        }

        public Drawable getApplicationIcon(String packageName) throws NameNotFoundException {
            return this.f1136b.getApplicationIcon(packageName);
        }

        public Drawable getApplicationIcon(ApplicationInfo info) {
            return this.f1136b.getApplicationIcon(info);
        }

        public CharSequence getApplicationLabel(ApplicationInfo info) {
            return this.f1136b.getApplicationLabel(info);
        }

        public Drawable getApplicationLogo(String packageName) throws NameNotFoundException {
            return this.f1136b.getApplicationLogo(packageName);
        }

        public Drawable getApplicationLogo(ApplicationInfo info) {
            return this.f1136b.getApplicationLogo(info);
        }

        public int getComponentEnabledSetting(ComponentName componentName) {
            return this.f1136b.getComponentEnabledSetting(componentName);
        }

        public Drawable getDefaultActivityIcon() {
            return this.f1136b.getDefaultActivityIcon();
        }

        public Drawable getDrawable(String packageName, int resid, ApplicationInfo appInfo) {
            return this.f1136b.getDrawable(packageName, resid, appInfo);
        }

        public List<ApplicationInfo> getInstalledApplications(int flags) {
            return this.f1136b.getInstalledApplications(flags);
        }

        public List<PackageInfo> getInstalledPackages(int flags) {
            return this.f1136b.getInstalledPackages(flags);
        }

        public String getInstallerPackageName(String packageName) {
            return this.f1136b.getInstallerPackageName(packageName);
        }

        public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags) throws NameNotFoundException {
            return this.f1136b.getInstrumentationInfo(className, flags);
        }

        public Intent getLaunchIntentForPackage(String packageName) {
            return this.f1136b.getLaunchIntentForPackage(packageName);
        }

        public String getNameForUid(int uid) {
            return this.f1136b.getNameForUid(uid);
        }

        public int[] getPackageGids(String packageName) throws NameNotFoundException {
            return this.f1136b.getPackageGids(packageName);
        }

        public String[] getPackagesForUid(int uid) {
            return this.f1136b.getPackagesForUid(uid);
        }

        public List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, int flags) {
            return this.f1136b.getPackagesHoldingPermissions(permissions, flags);
        }

        public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws NameNotFoundException {
            return this.f1136b.getPermissionGroupInfo(name, flags);
        }

        public PermissionInfo getPermissionInfo(String name, int flags) throws NameNotFoundException {
            return this.f1136b.getPermissionInfo(name, flags);
        }

        public int getPreferredActivities(List<IntentFilter> outFilters, List<ComponentName> outActivities, String packageName) {
            return this.f1136b.getPreferredActivities(outFilters, outActivities, packageName);
        }

        public List<PackageInfo> getPreferredPackages(int flags) {
            return this.f1136b.getPreferredPackages(flags);
        }

        public ProviderInfo getProviderInfo(ComponentName component, int flags) throws NameNotFoundException {
            return this.f1136b.getProviderInfo(component, flags);
        }

        public ActivityInfo getReceiverInfo(ComponentName component, int flags) throws NameNotFoundException {
            return this.f1136b.getReceiverInfo(component, flags);
        }

        public Resources getResourcesForActivity(ComponentName activityName) throws NameNotFoundException {
            return this.f1136b.getResourcesForActivity(activityName);
        }

        public Resources getResourcesForApplication(String appPackageName) throws NameNotFoundException {
            return this.f1136b.getResourcesForApplication(appPackageName);
        }

        public Resources getResourcesForApplication(ApplicationInfo app) throws NameNotFoundException {
            return this.f1136b.getResourcesForApplication(app);
        }

        public ServiceInfo getServiceInfo(ComponentName component, int flags) throws NameNotFoundException {
            return this.f1136b.getServiceInfo(component, flags);
        }

        public FeatureInfo[] getSystemAvailableFeatures() {
            return this.f1136b.getSystemAvailableFeatures();
        }

        public String[] getSystemSharedLibraryNames() {
            return this.f1136b.getSystemSharedLibraryNames();
        }

        public CharSequence getText(String packageName, int resid, ApplicationInfo appInfo) {
            return this.f1136b.getText(packageName, resid, appInfo);
        }

        public XmlResourceParser getXml(String packageName, int resid, ApplicationInfo appInfo) {
            return this.f1136b.getXml(packageName, resid, appInfo);
        }

        public boolean hasSystemFeature(String name) {
            return this.f1136b.hasSystemFeature(name);
        }

        public boolean isSafeMode() {
            return this.f1136b.isSafeMode();
        }

        public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
            return this.f1136b.queryBroadcastReceivers(intent, flags);
        }

        public List<ProviderInfo> queryContentProviders(String processName, int uid, int flags) {
            return this.f1136b.queryContentProviders(processName, uid, flags);
        }

        public List<InstrumentationInfo> queryInstrumentation(String targetPackage, int flags) {
            return this.f1136b.queryInstrumentation(targetPackage, flags);
        }

        public List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
            return this.f1136b.queryIntentActivities(intent, flags);
        }

        public List<ResolveInfo> queryIntentActivityOptions(ComponentName caller, Intent[] specifics, Intent intent, int flags) {
            return this.f1136b.queryIntentActivityOptions(caller, specifics, intent, flags);
        }

        public List<ResolveInfo> queryIntentContentProviders(Intent intent, int flags) {
            return this.f1136b.queryIntentContentProviders(intent, flags);
        }

        public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
            return this.f1136b.queryIntentServices(intent, flags);
        }

        public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws NameNotFoundException {
            return this.f1136b.queryPermissionsByGroup(group, flags);
        }

        public void removePackageFromPreferred(String packageName) {
            this.f1136b.removePackageFromPreferred(packageName);
        }

        public void removePermission(String name) {
            this.f1136b.removePermission(name);
        }

        public ResolveInfo resolveActivity(Intent intent, int flags) {
            return this.f1136b.resolveActivity(intent, flags);
        }

        public ProviderInfo resolveContentProvider(String name, int flags) {
            return this.f1136b.resolveContentProvider(name, flags);
        }

        public ResolveInfo resolveService(Intent intent, int flags) {
            return this.f1136b.resolveService(intent, flags);
        }

        public void setApplicationEnabledSetting(String packageName, int newState, int flags) {
            this.f1136b.setApplicationEnabledSetting(packageName, newState, flags);
        }

        public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
            this.f1136b.setComponentEnabledSetting(componentName, newState, flags);
        }

        public void setInstallerPackageName(String targetPackage, String installerPackageName) {
            this.f1136b.setInstallerPackageName(targetPackage, installerPackageName);
        }

        public void verifyPendingInstall(int id, int verificationCode) {
            this.f1136b.verifyPendingInstall(id, verificationCode);
        }

        public PackageInstaller getPackageInstaller() {
            return this.f1136b.getPackageInstaller();
        }

        public CharSequence getUserBadgedLabel(CharSequence label, UserHandle user) {
            return this.f1136b.getUserBadgedLabel(label, user);
        }

        public Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle user, Rect badgeLocation, int badgeDensity) {
            return this.f1136b.getUserBadgedDrawableForDensity(drawable, user, badgeLocation, badgeDensity);
        }

        public Drawable getUserBadgedIcon(Drawable icon, UserHandle user) {
            return this.f1136b.getUserBadgedIcon(icon, user);
        }

        public Drawable getActivityBanner(Intent intent) throws NameNotFoundException {
            return this.f1136b.getActivityBanner(intent);
        }

        public Drawable getActivityBanner(ComponentName activityName) throws NameNotFoundException {
            return this.f1136b.getActivityBanner(activityName);
        }

        public Drawable getApplicationBanner(ApplicationInfo info) {
            return this.f1136b.getApplicationBanner(info);
        }

        public Drawable getApplicationBanner(String packageName) throws NameNotFoundException {
            return this.f1136b.getApplicationBanner(packageName);
        }

        public Intent getLeanbackLaunchIntentForPackage(String packageName) {
            return this.f1136b.getLeanbackLaunchIntentForPackage(packageName);
        }
    }

    /* renamed from: com.google.ads.conversiontracking.a.c */
    static class C0535c extends Resources {
        public C0535c(Resources resources) {
            super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        }

        public String getString(int id) {
            return "";
        }
    }

    public C0536a(Context context) {
        this.f1137a = new C0533a(context);
    }

    public C0555a m1318a() {
        try {
            return C0556i.m1410a(this.f1137a);
        } catch (IOException e) {
            return null;
        } catch (IllegalStateException e2) {
            return null;
        } catch (C0557j e3) {
            return null;
        } catch (C0559k e4) {
            return null;
        }
    }
}
