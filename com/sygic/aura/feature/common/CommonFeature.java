package com.sygic.aura.feature.common;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.sygic.aura.SygicMain;
import com.sygic.aura.utils.FileUtils;
import com.sygic.aura.utils.GuiUtils;
import com.sygic.aura.utils.Utils;
import java.util.Timer;
import java.util.TimerTask;

public abstract class CommonFeature {
    private transient Context mCtx;
    private transient EditText mEditDummy;
    private transient Handler mHandler;
    private transient InputMethodManager mInputMethodManager;
    private transient PackageManager mPackageManager;
    private transient BroadcastReceiver mReceiverBattery;
    private transient SurfaceView mSurface;
    private transient TextWatcherDummy mTWDummy;
    private transient Timer mTimerKeybCheck;
    private transient boolean m_bKeybVis;
    private transient int m_nKeybHeightLandscape;
    private transient int m_nKeybHeightPortrait;
    private transient int m_nKeybType;

    /* renamed from: com.sygic.aura.feature.common.CommonFeature.1 */
    class C12251 implements Runnable {
        C12251() {
        }

        public void run() {
            CommonFeature.this.mEditDummy.setVisibility(0);
            CommonFeature.this.mEditDummy.setImeOptions(268435456);
            CommonFeature.this.mEditDummy.requestFocus();
            CommonFeature.this.mInputMethodManager.showSoftInput(CommonFeature.this.mEditDummy, 2);
            CommonFeature.this.mEditDummy.setVisibility(4);
        }
    }

    /* renamed from: com.sygic.aura.feature.common.CommonFeature.2 */
    class C12262 extends TimerTask {
        private int mHeight;

        C12262() {
            this.mHeight = CommonFeature.this.mSurface.getHeight();
        }

        public void run() {
            Rect rc = new Rect();
            CommonFeature.this.mSurface.getWindowVisibleDisplayFrame(rc);
            if (rc.height() == CommonFeature.this.mSurface.getHeight()) {
                SygicMain.getInstance().KeybSetHidden();
                cancel();
                if (CommonFeature.this.mTimerKeybCheck != null) {
                    CommonFeature.this.mTimerKeybCheck.cancel();
                    CommonFeature.this.mTimerKeybCheck.purge();
                    CommonFeature.this.mTimerKeybCheck = null;
                }
            } else if (this.mHeight != CommonFeature.this.mSurface.getHeight()) {
                CommonFeature.this.setKeybHeight(CommonFeature.this.mSurface.getHeight() - rc.height(), ((WindowManager) CommonFeature.this.mCtx.getSystemService("window")).getDefaultDisplay().getOrientation());
                this.mHeight = CommonFeature.this.mSurface.getHeight();
            }
        }
    }

    /* renamed from: com.sygic.aura.feature.common.CommonFeature.3 */
    class C12273 implements Runnable {
        final /* synthetic */ long val$lType;

        C12273(long j) {
            this.val$lType = j;
        }

        public void run() {
            if (!(CommonFeature.this.mTWDummy == null || CommonFeature.this.mEditDummy == null)) {
                CommonFeature.this.mTWDummy.reset();
                CommonFeature.this.mEditDummy.setText("");
            }
            int nType = 0;
            if (this.val$lType < 999) {
                nType = 1;
            } else if (this.val$lType < 2000) {
                nType = 1;
            } else if (this.val$lType < 2100) {
                nType = 33;
            } else if (this.val$lType < 2200) {
                nType = 17;
            } else if (this.val$lType < 2300) {
                nType = 524417;
            }
            if (nType != CommonFeature.this.m_nKeybType) {
                if (CommonFeature.this.mEditDummy != null) {
                    CommonFeature.this.mEditDummy.setInputType(nType);
                }
                CommonFeature.this.m_nKeybType = nType;
            }
        }
    }

    /* renamed from: com.sygic.aura.feature.common.CommonFeature.4 */
    class C12284 extends BroadcastReceiver {
        C12284() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                int nLevel = intent.getIntExtra("level", 0);
                int nStatus = intent.getIntExtra("status", 1);
                int nScale = intent.getIntExtra("scale", 100);
                if (nStatus == 2) {
                    nStatus = 2;
                } else if (nStatus == 3 || nStatus == 4) {
                    nStatus = 3;
                } else if (nStatus == 5) {
                    nStatus = 5;
                } else {
                    nStatus = 1;
                }
                SygicMain.getInstance().SetBatteryInfo((((int) ((((float) nLevel) / ((float) nScale)) * 100.0f)) << 8) | nStatus);
            }
        }
    }

    protected CommonFeature() {
        this.m_nKeybType = 0;
        this.m_nKeybHeightPortrait = 0;
        this.m_nKeybHeightLandscape = 0;
    }

    protected CommonFeature(Context context, Handler handler) {
        this.m_nKeybType = 0;
        this.m_nKeybHeightPortrait = 0;
        this.m_nKeybHeightLandscape = 0;
        this.mCtx = context;
        this.mHandler = handler;
        this.mInputMethodManager = (InputMethodManager) this.mCtx.getSystemService("input_method");
        this.mPackageManager = this.mCtx.getPackageManager();
    }

    public static CommonFeature createInstance(Context context, Handler handler) {
        return new CommonFeatureBase(context, handler);
    }

    public void setSurface(SurfaceView surface) {
        this.mSurface = surface;
    }

    public void setEditText(EditText et) {
        this.mEditDummy = et;
        if (this.mEditDummy != null) {
            this.mTWDummy = new TextWatcherDummy();
            this.mEditDummy.addTextChangedListener(this.mTWDummy);
            this.mEditDummy.setOnKeyListener(this.mTWDummy);
            this.mEditDummy.setOnEditorActionListener(this.mTWDummy);
        }
    }

    public String getLibPath() {
        return "/data/data/" + this.mCtx.getPackageName() + "/lib";
    }

    public String getSdPath() {
        return FileUtils.getSDCardPath();
    }

    public String getSygicRootPath() {
        return Utils.getSygicDirPath();
    }

    public int getAndroidSdkVersion() {
        return Utils.getAndroidVersion();
    }

    public int getDisplayResolution() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) this.mCtx.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
        return (dm.widthPixels << 16) | dm.heightPixels;
    }

    public long getFreeDiskSpaceInKiloBytes(String strPath) {
        return getFreeDiskSpaceInBytes(strPath) / 1024;
    }

    @TargetApi(18)
    public long getFreeDiskSpaceInBytes(String strPath) {
        if (strPath == null || !FileUtils.fileExists(strPath)) {
            strPath = FileUtils.getSDCardPath();
        }
        StatFs stats = new StatFs(strPath);
        if (VERSION.SDK_INT >= 18) {
            return stats.getAvailableBlocksLong() * stats.getBlockSizeLong();
        }
        return ((long) stats.getAvailableBlocks()) * ((long) stats.getBlockSize());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showKeyboard(boolean r12) {
        /*
        r11 = this;
        r4 = 2;
        r3 = 1;
        r2 = 0;
        r0 = r11.mInputMethodManager;
        if (r0 == 0) goto L_0x0019;
    L_0x0007:
        r0 = r11.mEditDummy;
        if (r0 == 0) goto L_0x0019;
    L_0x000b:
        if (r12 == 0) goto L_0x009c;
    L_0x000d:
        r0 = r11.mHandler;
        r1 = new com.sygic.aura.feature.common.CommonFeature$1;
        r1.<init>();
        r0.post(r1);
        r11.m_bKeybVis = r3;
    L_0x0019:
        r0 = r11.m_bKeybVis;
        if (r0 == 0) goto L_0x00b3;
    L_0x001d:
        r0 = r11.mCtx;
        r0 = r0.getResources();
        r0 = r0.getConfiguration();
        r0 = r0.hardKeyboardHidden;
        if (r0 != r4) goto L_0x00b3;
    L_0x002b:
        monitor-enter(r11);
        r0 = r11.mCtx;	 Catch:{ all -> 0x00b0 }
        r1 = "window";
        r0 = r0.getSystemService(r1);	 Catch:{ all -> 0x00b0 }
        r0 = (android.view.WindowManager) r0;	 Catch:{ all -> 0x00b0 }
        r0 = r0.getDefaultDisplay();	 Catch:{ all -> 0x00b0 }
        r9 = r0.getOrientation();	 Catch:{ all -> 0x00b0 }
        if (r9 == 0) goto L_0x0043;
    L_0x0041:
        if (r9 != r4) goto L_0x0050;
    L_0x0043:
        r0 = r11.m_nKeybHeightPortrait;	 Catch:{ all -> 0x00b0 }
        if (r0 == 0) goto L_0x0050;
    L_0x0047:
        if (r9 == r3) goto L_0x004c;
    L_0x0049:
        r0 = 3;
        if (r9 != r0) goto L_0x0081;
    L_0x004c:
        r0 = r11.m_nKeybHeightLandscape;	 Catch:{ all -> 0x00b0 }
        if (r0 == 0) goto L_0x0081;
    L_0x0050:
        r10 = new android.graphics.Rect;	 Catch:{ all -> 0x00b0 }
        r10.<init>();	 Catch:{ all -> 0x00b0 }
        r7 = 0;
        r8 = r7;
    L_0x0057:
        r0 = 50;
        java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x00ab }
    L_0x005c:
        r0 = r11.mSurface;	 Catch:{ all -> 0x00b0 }
        r0.getWindowVisibleDisplayFrame(r10);	 Catch:{ all -> 0x00b0 }
        r0 = r11.mSurface;	 Catch:{ all -> 0x00b0 }
        r0 = r0.getHeight();	 Catch:{ all -> 0x00b0 }
        r1 = r10.height();	 Catch:{ all -> 0x00b0 }
        if (r0 != r1) goto L_0x00cc;
    L_0x006d:
        r7 = r8 + 1;
        r0 = 10;
        if (r8 < r0) goto L_0x00ca;
    L_0x0073:
        r0 = r11.mSurface;	 Catch:{ all -> 0x00b0 }
        r0 = r0.getHeight();	 Catch:{ all -> 0x00b0 }
        r1 = r10.height();	 Catch:{ all -> 0x00b0 }
        r0 = r0 - r1;
        r11.setKeybHeight(r0, r9);	 Catch:{ all -> 0x00b0 }
    L_0x0081:
        r0 = r11.mTimerKeybCheck;	 Catch:{ all -> 0x00b0 }
        if (r0 != 0) goto L_0x009a;
    L_0x0085:
        r0 = new java.util.Timer;	 Catch:{ all -> 0x00b0 }
        r0.<init>();	 Catch:{ all -> 0x00b0 }
        r11.mTimerKeybCheck = r0;	 Catch:{ all -> 0x00b0 }
        r0 = r11.mTimerKeybCheck;	 Catch:{ all -> 0x00b0 }
        r1 = new com.sygic.aura.feature.common.CommonFeature$2;	 Catch:{ all -> 0x00b0 }
        r1.<init>();	 Catch:{ all -> 0x00b0 }
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r4 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0.schedule(r1, r2, r4);	 Catch:{ all -> 0x00b0 }
    L_0x009a:
        monitor-exit(r11);	 Catch:{ all -> 0x00b0 }
    L_0x009b:
        return;
    L_0x009c:
        r0 = r11.mInputMethodManager;
        r1 = r11.mEditDummy;
        r1 = r1.getWindowToken();
        r0.hideSoftInputFromWindow(r1, r2);
        r11.m_bKeybVis = r2;
        goto L_0x0019;
    L_0x00ab:
        r6 = move-exception;
        r6.printStackTrace();	 Catch:{ all -> 0x00b0 }
        goto L_0x005c;
    L_0x00b0:
        r0 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x00b0 }
        throw r0;
    L_0x00b3:
        r0 = r11.mTimerKeybCheck;
        if (r0 == 0) goto L_0x009b;
    L_0x00b7:
        monitor-enter(r11);
        r0 = r11.mTimerKeybCheck;	 Catch:{ all -> 0x00c7 }
        r0.cancel();	 Catch:{ all -> 0x00c7 }
        r0 = r11.mTimerKeybCheck;	 Catch:{ all -> 0x00c7 }
        r0.purge();	 Catch:{ all -> 0x00c7 }
        r0 = 0;
        r11.mTimerKeybCheck = r0;	 Catch:{ all -> 0x00c7 }
        monitor-exit(r11);	 Catch:{ all -> 0x00c7 }
        goto L_0x009b;
    L_0x00c7:
        r0 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x00c7 }
        throw r0;
    L_0x00ca:
        r8 = r7;
        goto L_0x0057;
    L_0x00cc:
        r7 = r8;
        goto L_0x0073;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.common.CommonFeature.showKeyboard(boolean):void");
    }

    public void setKeyboardLayout(long lType) {
        if (lType != -1) {
            this.mHandler.post(new C12273(lType));
        }
    }

    private void setKeybHeight(int nHeight, int nOrient) {
        if (nOrient == 0 || nOrient == 2) {
            this.m_nKeybHeightPortrait = nHeight;
            if (this.m_nKeybHeightPortrait != 0) {
                SygicMain.getInstance().KeybSetHeight(this.m_nKeybHeightPortrait);
            }
        } else if (nOrient == 1 || nOrient == 3) {
            this.m_nKeybHeightLandscape = nHeight;
            if (this.m_nKeybHeightLandscape != 0) {
                SygicMain.getInstance().KeybSetHeight(this.m_nKeybHeightLandscape);
            }
        }
    }

    public boolean isKeybVisible() {
        return this.m_bKeybVis;
    }

    public void setKeybVisible(boolean bVisible) {
        this.m_bKeybVis = bVisible;
    }

    public boolean isCameraAvailable() {
        if (this.mPackageManager != null) {
            return this.mPackageManager.hasSystemFeature("android.hardware.camera");
        }
        return true;
    }

    public boolean isCompassAvailable() {
        if (this.mPackageManager != null) {
            return this.mPackageManager.hasSystemFeature("android.hardware.sensor.compass");
        }
        return true;
    }

    public boolean isTablet() {
        boolean bLarge;
        boolean bXLarge;
        if ((this.mCtx.getResources().getConfiguration().screenLayout & 15) == 4) {
            bXLarge = true;
        } else {
            bXLarge = false;
        }
        if ((this.mCtx.getResources().getConfiguration().screenLayout & 15) == 3) {
            bLarge = true;
        } else {
            bLarge = false;
        }
        if (bLarge || bXLarge) {
            return true;
        }
        return false;
    }

    public void showNotification(String strTitle, String strText) {
        GuiUtils.showStandartNotification(this.mCtx, strTitle, strText);
    }

    public void registerBatteryReceiver() {
        if (this.mReceiverBattery == null) {
            this.mReceiverBattery = new C12284();
            this.mCtx.registerReceiver(this.mReceiverBattery, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        }
    }

    public void unregisterBatteryReceiver() {
        if (this.mReceiverBattery != null) {
            try {
                this.mCtx.unregisterReceiver(this.mReceiverBattery);
                this.mReceiverBattery = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handleWebLink(android.content.Intent r41) {
        /*
        r40 = this;
        r5 = "ACCESS_TOKEN";
        r4 = "ACCESS_SECRET";
        r9 = "UID";
        r6 = "MY_SYGIC";
        r7 = "MY_SYGIC_DETAIL";
        r8 = "OPEN_URL";
        if (r41 == 0) goto L_0x0133;
    L_0x0014:
        r37 = "com.bosch.myspin.action.INITIATE_NAVIGATION";
        r38 = r41.getAction();
        r37 = r37.equals(r38);
        if (r37 == 0) goto L_0x0133;
    L_0x0021:
        r37 = r41.getExtras();
        r38 = "com.bosch.myspin.EXTRA_NAVIGATION_DESTINATION_LOCATION";
        r15 = r37.getParcelable(r38);
        r15 = (android.location.Location) r15;
        if (r15 == 0) goto L_0x0068;
    L_0x0030:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r38 = r15.getLongitude();
        r37 = r37.append(r38);
        r38 = "|";
        r37 = r37.append(r38);
        r38 = r15.getLatitude();
        r37 = r37.append(r38);
        r38 = "|drive";
        r37 = r37.append(r38);
        r37 = r37.toString();
        r0 = r40;
        r1 = r37;
        r0.setArguments(r1);
        r37 = 1;
    L_0x0067:
        return r37;
    L_0x0068:
        r11 = 5;
        r37 = 5;
        r0 = r37;
        r0 = new java.lang.String[r0];
        r34 = r0;
        r37 = 0;
        r38 = r41.getExtras();
        r39 = "com.bosch.myspin.EXTRA_NAVIGATION_DESTINATION_COUNTRY";
        r38 = r38.getString(r39);
        r34[r37] = r38;
        r37 = 1;
        r38 = r41.getExtras();
        r39 = "com.bosch.myspin.EXTRA_NAVIGATION_DESTINATION_CITY";
        r38 = r38.getString(r39);
        r34[r37] = r38;
        r37 = 2;
        r38 = r41.getExtras();
        r39 = "com.bosch.myspin.EXTRA_NAVIGATION_DESTINATION_POSTCODE";
        r38 = r38.getString(r39);
        r34[r37] = r38;
        r37 = 3;
        r38 = r41.getExtras();
        r39 = "com.bosch.myspin.EXTRA_NAVIGATION_DESTINATION_STREET";
        r38 = r38.getString(r39);
        r34[r37] = r38;
        r37 = 4;
        r38 = r41.getExtras();
        r39 = "com.bosch.myspin.EXTRA_NAVIGATION_DESTINATION_HOUSENO";
        r38 = r38.getString(r39);
        r34[r37] = r38;
        r22 = "com.sygic.aura://address|";
        r13 = 0;
        r14 = 0;
    L_0x00c1:
        r37 = 5;
        r0 = r37;
        if (r14 >= r0) goto L_0x010e;
    L_0x00c7:
        r37 = r34[r14];
        if (r37 == 0) goto L_0x00d3;
    L_0x00cb:
        r37 = r34[r14];
        r37 = android.text.TextUtils.isEmpty(r37);
        if (r37 == 0) goto L_0x00ee;
    L_0x00d3:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r0 = r37;
        r1 = r22;
        r37 = r0.append(r1);
        r38 = " |";
        r37 = r37.append(r38);
        r22 = r37.toString();
    L_0x00eb:
        r14 = r14 + 1;
        goto L_0x00c1;
    L_0x00ee:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r0 = r37;
        r1 = r22;
        r37 = r0.append(r1);
        r38 = r34[r14];
        r37 = r37.append(r38);
        r38 = "|";
        r37 = r37.append(r38);
        r22 = r37.toString();
        r13 = 1;
        goto L_0x00eb;
    L_0x010e:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r0 = r37;
        r1 = r22;
        r37 = r0.append(r1);
        r38 = "drive";
        r37 = r37.append(r38);
        r22 = r37.toString();
        if (r13 == 0) goto L_0x0133;
    L_0x0128:
        r0 = r40;
        r1 = r22;
        r0.setArguments(r1);
        r37 = 1;
        goto L_0x0067;
    L_0x0133:
        if (r41 == 0) goto L_0x01b1;
    L_0x0135:
        r37 = "ACCESS_TOKEN";
        r0 = r41;
        r1 = r37;
        r37 = r0.hasExtra(r1);
        if (r37 == 0) goto L_0x01b1;
    L_0x0142:
        r37 = "ACCESS_TOKEN";
        r0 = r41;
        r1 = r37;
        r30 = r0.getStringExtra(r1);
        r37 = "ACCESS_SECRET";
        r0 = r41;
        r1 = r37;
        r29 = r0.getStringExtra(r1);
        r37 = "UID";
        r0 = r41;
        r1 = r37;
        r33 = r0.getStringExtra(r1);
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "db-";
        r37 = r37.append(r38);
        r38 = com.sygic.aura.feature.system.DropBoxLogin.mStrAppKey;
        r37 = r37.append(r38);
        r38 = "://?oauth_token_secret=";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r29;
        r37 = r0.append(r1);
        r38 = "&oauth_token=";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r30;
        r37 = r0.append(r1);
        r38 = "&uid=";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r33;
        r37 = r0.append(r1);
        r35 = r37.toString();
        r0 = r40;
        r1 = r35;
        r0.setArguments(r1);
        r37 = 1;
        goto L_0x0067;
    L_0x01b1:
        if (r41 == 0) goto L_0x01da;
    L_0x01b3:
        r37 = "MY_SYGIC";
        r0 = r41;
        r1 = r37;
        r37 = r0.hasExtra(r1);
        if (r37 == 0) goto L_0x01da;
    L_0x01c0:
        r37 = "MY_SYGIC";
        r0 = r41;
        r1 = r37;
        r23 = r0.getStringExtra(r1);
        r37 = com.sygic.aura.SygicMain.getInstance();
        r38 = 0;
        r0 = r37;
        r1 = r23;
        r2 = r38;
        r0.OpenMySygic(r1, r2);
    L_0x01da:
        if (r41 == 0) goto L_0x0203;
    L_0x01dc:
        r37 = "MY_SYGIC_DETAIL";
        r0 = r41;
        r1 = r37;
        r37 = r0.hasExtra(r1);
        if (r37 == 0) goto L_0x0203;
    L_0x01e9:
        r37 = "MY_SYGIC_DETAIL";
        r0 = r41;
        r1 = r37;
        r23 = r0.getStringExtra(r1);
        r37 = com.sygic.aura.SygicMain.getInstance();
        r38 = 1;
        r0 = r37;
        r1 = r23;
        r2 = r38;
        r0.OpenMySygic(r1, r2);
    L_0x0203:
        if (r41 == 0) goto L_0x0238;
    L_0x0205:
        r37 = "OPEN_URL";
        r0 = r41;
        r1 = r37;
        r37 = r0.hasExtra(r1);
        if (r37 == 0) goto L_0x0238;
    L_0x0212:
        r37 = "OPEN_URL";
        r0 = r41;
        r1 = r37;
        r23 = r0.getStringExtra(r1);
        r37 = com.sygic.aura.SygicMain.getInstance();
        r37 = r37.getFeature();
        r37 = r37.getSystemFeature();
        r38 = 0;
        r39 = 0;
        r0 = r37;
        r1 = r23;
        r2 = r38;
        r3 = r39;
        r0.browserOpenUri(r1, r2, r3);
    L_0x0238:
        if (r41 == 0) goto L_0x02f7;
    L_0x023a:
        r37 = r41.getType();
        if (r37 == 0) goto L_0x02f7;
    L_0x0240:
        r37 = com.sygic.aura.SygicMain.getCoreEventsListener();
        if (r37 == 0) goto L_0x02f7;
    L_0x0246:
        r37 = "android.intent.action.VIEW";
        r38 = r41.getAction();
        r37 = r37.equals(r38);
        if (r37 == 0) goto L_0x0298;
    L_0x0253:
        r37 = r41.getType();
        r38 = "image/";
        r37 = r37.startsWith(r38);
        if (r37 == 0) goto L_0x0298;
    L_0x0260:
        r37 = com.sygic.aura.SygicMain.getCoreEventsListener();
        r38 = r41.getData();
        r17 = r37.onPhotoChosen(r38);
        if (r17 == 0) goto L_0x0298;
    L_0x026e:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r17;
        r37 = r0.append(r1);
        r38 = "|show";
        r37 = r37.append(r38);
        r37 = r37.toString();
        r0 = r40;
        r1 = r37;
        r0.setArguments(r1);
        r37 = 1;
        goto L_0x0067;
    L_0x0298:
        r37 = "android.intent.action.SEND";
        r38 = r41.getAction();
        r37 = r37.equals(r38);
        if (r37 == 0) goto L_0x02f7;
    L_0x02a5:
        r37 = r41.getType();
        r38 = "image/";
        r37 = r37.startsWith(r38);
        if (r37 == 0) goto L_0x02f7;
    L_0x02b2:
        r38 = com.sygic.aura.SygicMain.getCoreEventsListener();
        r37 = "android.intent.extra.STREAM";
        r0 = r41;
        r1 = r37;
        r37 = r0.getParcelableExtra(r1);
        r37 = (android.net.Uri) r37;
        r0 = r38;
        r1 = r37;
        r17 = r0.onPhotoChosen(r1);
        if (r17 == 0) goto L_0x02f7;
    L_0x02cd:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r17;
        r37 = r0.append(r1);
        r38 = "|show";
        r37 = r37.append(r38);
        r37 = r37.toString();
        r0 = r40;
        r1 = r37;
        r0.setArguments(r1);
        r37 = 1;
        goto L_0x0067;
    L_0x02f7:
        if (r41 == 0) goto L_0x08c9;
    L_0x02f9:
        r37 = r41.getData();
        if (r37 == 0) goto L_0x08c9;
    L_0x02ff:
        r37 = r41.getAction();
        if (r37 == 0) goto L_0x08c9;
    L_0x0305:
        r37 = r41.getData();
        r28 = r37.getScheme();
        r37 = r41.getData();
        r24 = r37.getHost();
        r22 = r41.getDataString();
        if (r28 != 0) goto L_0x031f;
    L_0x031b:
        r37 = 0;
        goto L_0x0067;
    L_0x031f:
        r37 = "content";
        r0 = r28;
        r1 = r37;
        r37 = r0.equals(r1);
        if (r37 == 0) goto L_0x033c;
    L_0x032c:
        r37 = "content://com.android.contacts/data/";
        r38 = "com.sygic.aura://contact|";
        r0 = r22;
        r1 = r37;
        r2 = r38;
        r22 = r0.replaceFirst(r1, r2);
    L_0x033c:
        r37 = "http";
        r0 = r28;
        r1 = r37;
        r37 = r0.equals(r1);
        if (r37 != 0) goto L_0x0356;
    L_0x0349:
        r37 = "https";
        r0 = r28;
        r1 = r37;
        r37 = r0.equals(r1);
        if (r37 == 0) goto L_0x038a;
    L_0x0356:
        if (r24 == 0) goto L_0x0558;
    L_0x0358:
        r37 = "com.sygic.aura";
        r0 = r24;
        r1 = r37;
        r37 = r0.equals(r1);
        if (r37 == 0) goto L_0x0558;
    L_0x0365:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r0 = r37;
        r1 = r28;
        r37 = r0.append(r1);
        r38 = "://com.sygic.aura/";
        r37 = r37.append(r38);
        r37 = r37.toString();
        r38 = "com.sygic.aura://";
        r0 = r22;
        r1 = r37;
        r2 = r38;
        r22 = r0.replaceFirst(r1, r2);
    L_0x038a:
        r37 = "geo";
        r0 = r28;
        r1 = r37;
        r37 = r0.equals(r1);
        if (r37 == 0) goto L_0x0456;
    L_0x0397:
        r37 = r41.getData();
        r27 = r37.getQuery();
        r37 = android.text.TextUtils.isEmpty(r27);
        if (r37 != 0) goto L_0x078c;
    L_0x03a5:
        r37 = "q=";
        r0 = r27;
        r1 = r37;
        r37 = r0.startsWith(r1);
        if (r37 == 0) goto L_0x078c;
    L_0x03b2:
        r37 = 2;
        r0 = r27;
        r1 = r37;
        r37 = r0.substring(r1);
        r38 = ",";
        r18 = r37.split(r38);
        r0 = r18;
        r0 = r0.length;
        r37 = r0;
        r38 = 2;
        r0 = r37;
        r1 = r38;
        if (r0 < r1) goto L_0x0765;
    L_0x03d0:
        r37 = 0;
        r37 = r18[r37];
        r38 = "-?\\d+(\\.\\d+)?";
        r37 = r37.matches(r38);
        if (r37 == 0) goto L_0x0765;
    L_0x03dd:
        r37 = 0;
        r25 = r18[r37];
        r37 = 1;
        r26 = r18[r37];
        r37 = "(";
        r0 = r26;
        r1 = r37;
        r37 = r0.contains(r1);
        if (r37 == 0) goto L_0x0409;
    L_0x03f2:
        r37 = 0;
        r38 = "(";
        r0 = r26;
        r1 = r38;
        r38 = r0.indexOf(r1);
        r0 = r26;
        r1 = r37;
        r2 = r38;
        r26 = r0.substring(r1, r2);
    L_0x0409:
        r37 = "-?\\d+(\\.\\d+)?";
        r0 = r26;
        r1 = r37;
        r37 = r0.matches(r1);
        if (r37 == 0) goto L_0x0444;
    L_0x0416:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r26;
        r37 = r0.append(r1);
        r38 = "|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r25;
        r37 = r0.append(r1);
        r38 = "|show";
        r37 = r37.append(r38);
        r22 = r37.toString();
    L_0x0444:
        if (r22 == 0) goto L_0x0456;
    L_0x0446:
        r37 = "\\r|\\n";
        r38 = " ";
        r0 = r22;
        r1 = r37;
        r2 = r38;
        r22 = r0.replaceAll(r1, r2);
    L_0x0456:
        r37 = "google.navigation";
        r0 = r28;
        r1 = r37;
        r37 = r0.equals(r1);
        if (r37 == 0) goto L_0x04dd;
    L_0x0463:
        r37 = r41.getData();
        r27 = r37.getQuery();
        if (r27 == 0) goto L_0x04dd;
    L_0x046d:
        r37 = android.text.TextUtils.isEmpty(r27);
        if (r37 != 0) goto L_0x04dd;
    L_0x0473:
        r37 = "ll=";
        r0 = r27;
        r1 = r37;
        r37 = r0.startsWith(r1);
        if (r37 == 0) goto L_0x07d6;
    L_0x0480:
        r37 = 3;
        r38 = "&";
        r0 = r27;
        r1 = r38;
        r38 = r0.indexOf(r1);
        r0 = r27;
        r1 = r37;
        r2 = r38;
        r27 = r0.substring(r1, r2);
        r37 = ",";
        r0 = r27;
        r1 = r37;
        r18 = r0.split(r1);
        r0 = r18;
        r0 = r0.length;
        r37 = r0;
        r38 = 2;
        r0 = r37;
        r1 = r38;
        if (r0 < r1) goto L_0x04dd;
    L_0x04af:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r38 = 1;
        r38 = r18[r38];
        r37 = r37.append(r38);
        r38 = "|";
        r37 = r37.append(r38);
        r38 = 0;
        r38 = r18[r38];
        r37 = r37.append(r38);
        r38 = "|drive";
        r37 = r37.append(r38);
        r22 = r37.toString();
    L_0x04dd:
        r37 = "navi";
        r0 = r28;
        r1 = r37;
        r37 = r0.equals(r1);
        if (r37 == 0) goto L_0x0542;
    L_0x04ea:
        r37 = r41.getData();
        r16 = r37.getSchemeSpecificPart();
        r37 = "\\s";
        r38 = "";
        r0 = r16;
        r1 = r37;
        r2 = r38;
        r16 = r0.replaceAll(r1, r2);
        r37 = android.text.TextUtils.isEmpty(r16);
        if (r37 != 0) goto L_0x0542;
    L_0x0508:
        r37 = "0,0?";
        r0 = r16;
        r1 = r37;
        r37 = r0.startsWith(r1);
        if (r37 == 0) goto L_0x0831;
    L_0x0515:
        r37 = r41.getData();
        r27 = r37.getQuery();
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://address|";
        r37 = r37.append(r38);
        r38 = 2;
        r0 = r27;
        r1 = r38;
        r38 = r0.substring(r1);
        r37 = r37.append(r38);
        r38 = "| | | | |drive";
        r37 = r37.append(r38);
        r22 = r37.toString();
    L_0x0542:
        r37 = "UTF-8";
        r0 = r22;
        r1 = r37;
        r22 = java.net.URLDecoder.decode(r0, r1);	 Catch:{ UnsupportedEncodingException -> 0x08c3 }
    L_0x054d:
        r0 = r40;
        r1 = r22;
        r0.setArguments(r1);
        r37 = 1;
        goto L_0x0067;
    L_0x0558:
        if (r24 == 0) goto L_0x038a;
    L_0x055a:
        r37 = "maps.google";
        r0 = r24;
        r1 = r37;
        r37 = r0.startsWith(r1);
        if (r37 == 0) goto L_0x038a;
    L_0x0567:
        r37 = r41.getData();
        r38 = "daddr";
        r21 = r37.getQueryParameter(r38);
        r37 = r41.getData();
        r38 = "dirflg";
        r32 = r37.getQueryParameter(r38);
        r37 = android.text.TextUtils.isEmpty(r21);
        if (r37 != 0) goto L_0x0675;
    L_0x0583:
        r37 = "@";
        r0 = r21;
        r1 = r37;
        r14 = r0.indexOf(r1);
        if (r14 <= 0) goto L_0x0596;
    L_0x0590:
        r0 = r21;
        r21 = r0.substring(r14);
    L_0x0596:
        r37 = ",";
        r0 = r21;
        r1 = r37;
        r18 = r0.split(r1);
        r0 = r18;
        r0 = r0.length;
        r37 = r0;
        r38 = 2;
        r0 = r37;
        r1 = r38;
        if (r0 < r1) goto L_0x0634;
    L_0x05ae:
        r37 = 0;
        r37 = r18[r37];
        r38 = "-?\\d+(\\.\\d+)?";
        r37 = r37.matches(r38);
        if (r37 == 0) goto L_0x0634;
    L_0x05bb:
        r37 = 0;
        r25 = r18[r37];
        r37 = 1;
        r26 = r18[r37];
        r31 = "drive";
        r37 = "+to:";
        r0 = r26;
        r1 = r37;
        r37 = r0.contains(r1);
        if (r37 == 0) goto L_0x05ea;
    L_0x05d3:
        r37 = 0;
        r38 = "+to:";
        r0 = r26;
        r1 = r38;
        r38 = r0.indexOf(r1);
        r0 = r26;
        r1 = r37;
        r2 = r38;
        r26 = r0.substring(r1, r2);
    L_0x05ea:
        if (r32 == 0) goto L_0x05fc;
    L_0x05ec:
        r37 = "w";
        r0 = r32;
        r1 = r37;
        r37 = r0.equals(r1);
        if (r37 == 0) goto L_0x05fc;
    L_0x05f9:
        r31 = "walk";
    L_0x05fc:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r26;
        r37 = r0.append(r1);
        r38 = "|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r25;
        r37 = r0.append(r1);
        r38 = "|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r31;
        r37 = r0.append(r1);
        r22 = r37.toString();
        goto L_0x038a;
    L_0x0634:
        r37 = "+";
        r38 = " ";
        r0 = r21;
        r1 = r37;
        r2 = r38;
        r21 = r0.replace(r1, r2);
        r37 = ",";
        r38 = " ";
        r0 = r21;
        r1 = r37;
        r2 = r38;
        r21 = r0.replace(r1, r2);
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://address|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r21;
        r37 = r0.append(r1);
        r38 = "| | | | |show";
        r37 = r37.append(r38);
        r22 = r37.toString();
        goto L_0x038a;
    L_0x0675:
        r37 = r41.getData();
        r38 = "hnear";
        r27 = r37.getQueryParameter(r38);
        if (r27 == 0) goto L_0x06bd;
    L_0x0682:
        r37 = ":";
        r0 = r27;
        r1 = r37;
        r37 = r0.contains(r1);
        if (r37 == 0) goto L_0x06bd;
    L_0x068f:
        r37 = 58;
        r0 = r27;
        r1 = r37;
        r37 = r0.indexOf(r1);
        r38 = 44;
        r0 = r27;
        r1 = r38;
        r38 = r0.indexOf(r1);
        r0 = r37;
        r1 = r38;
        if (r0 >= r1) goto L_0x06bd;
    L_0x06a9:
        r37 = 44;
        r0 = r27;
        r1 = r37;
        r37 = r0.indexOf(r1);
        r37 = r37 + 1;
        r0 = r27;
        r1 = r37;
        r27 = r0.substring(r1);
    L_0x06bd:
        r37 = android.text.TextUtils.isEmpty(r27);
        if (r37 == 0) goto L_0x06ce;
    L_0x06c3:
        r37 = r41.getData();
        r38 = "q";
        r27 = r37.getQueryParameter(r38);
    L_0x06ce:
        r37 = android.text.TextUtils.isEmpty(r27);
        if (r37 != 0) goto L_0x038a;
    L_0x06d4:
        r37 = "-?\\d*\\.?\\d+,-?\\d*\\.?\\d+";
        r0 = r27;
        r1 = r37;
        r37 = r0.matches(r1);
        if (r37 == 0) goto L_0x0724;
    L_0x06e1:
        r37 = ",";
        r0 = r27;
        r1 = r37;
        r10 = r0.split(r1);
        r37 = 0;
        r19 = r10[r37];
        r37 = 1;
        r20 = r10[r37];
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r20;
        r37 = r0.append(r1);
        r38 = "|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r19;
        r37 = r0.append(r1);
        r38 = "|show";
        r37 = r37.append(r38);
        r22 = r37.toString();
        goto L_0x038a;
    L_0x0724:
        r37 = "+";
        r38 = " ";
        r0 = r27;
        r1 = r37;
        r2 = r38;
        r27 = r0.replace(r1, r2);
        r37 = ",";
        r38 = " ";
        r0 = r27;
        r1 = r37;
        r2 = r38;
        r27 = r0.replace(r1, r2);
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://address|";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r27;
        r37 = r0.append(r1);
        r38 = "| | | | |show";
        r37 = r37.append(r38);
        r22 = r37.toString();
        goto L_0x038a;
    L_0x0765:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://address|";
        r37 = r37.append(r38);
        r38 = 2;
        r0 = r27;
        r1 = r38;
        r38 = r0.substring(r1);
        r37 = r37.append(r38);
        r38 = "| | | | |show";
        r37 = r37.append(r38);
        r22 = r37.toString();
        goto L_0x0444;
    L_0x078c:
        if (r27 == 0) goto L_0x0444;
    L_0x078e:
        r37 = ",";
        r0 = r27;
        r1 = r37;
        r18 = r0.split(r1);
        r0 = r18;
        r0 = r0.length;
        r37 = r0;
        r38 = 2;
        r0 = r37;
        r1 = r38;
        if (r0 < r1) goto L_0x0444;
    L_0x07a6:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r38 = 1;
        r38 = r18[r38];
        r37 = r37.append(r38);
        r38 = "|";
        r37 = r37.append(r38);
        r38 = 0;
        r38 = r18[r38];
        r37 = r37.append(r38);
        r38 = "|show";
        r37 = r37.append(r38);
        r22 = r37.toString();
        goto L_0x0444;
    L_0x07d6:
        r37 = "q=\\d+\\.\\d+,\\d+\\.\\d+";
        r0 = r27;
        r1 = r37;
        r37 = r0.matches(r1);
        if (r37 == 0) goto L_0x04dd;
    L_0x07e3:
        r37 = 2;
        r0 = r27;
        r1 = r37;
        r37 = r0.substring(r1);
        r38 = ",";
        r18 = r37.split(r38);
        r0 = r18;
        r0 = r0.length;
        r37 = r0;
        r38 = 2;
        r0 = r37;
        r1 = r38;
        if (r0 != r1) goto L_0x04dd;
    L_0x0801:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "com.sygic.aura://coordinate|";
        r37 = r37.append(r38);
        r38 = 1;
        r38 = r18[r38];
        r37 = r37.append(r38);
        r38 = "|";
        r37 = r37.append(r38);
        r38 = 0;
        r38 = r18[r38];
        r37 = r37.append(r38);
        r38 = "|drive";
        r37 = r37.append(r38);
        r22 = r37.toString();
        goto L_0x04dd;
    L_0x0831:
        r22 = "com.sygic.aura://coordinate|";
        r37 = "&wp=";
        r0 = r16;
        r1 = r37;
        r36 = r0.split(r1);
        r14 = 0;
    L_0x0840:
        r0 = r36;
        r0 = r0.length;
        r37 = r0;
        r0 = r37;
        if (r14 >= r0) goto L_0x08a9;
    L_0x0849:
        r37 = r36[r14];
        r38 = ",";
        r10 = r37.split(r38);
        r0 = r10.length;
        r37 = r0;
        r38 = 2;
        r0 = r37;
        r1 = r38;
        if (r0 < r1) goto L_0x08a6;
    L_0x085d:
        r37 = "0";
        r38 = 0;
        r38 = r10[r38];
        r37 = r37.equals(r38);
        if (r37 != 0) goto L_0x08a6;
    L_0x086a:
        r37 = "0";
        r38 = 1;
        r38 = r10[r38];
        r37 = r37.equals(r38);
        if (r37 != 0) goto L_0x08a6;
    L_0x0877:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r0 = r37;
        r1 = r22;
        r37 = r0.append(r1);
        r38 = 1;
        r38 = r10[r38];
        r37 = r37.append(r38);
        r38 = "|";
        r37 = r37.append(r38);
        r38 = 0;
        r38 = r10[r38];
        r37 = r37.append(r38);
        r38 = "|";
        r37 = r37.append(r38);
        r22 = r37.toString();
    L_0x08a6:
        r14 = r14 + 1;
        goto L_0x0840;
    L_0x08a9:
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r0 = r37;
        r1 = r22;
        r37 = r0.append(r1);
        r38 = "drive";
        r37 = r37.append(r38);
        r22 = r37.toString();
        goto L_0x0542;
    L_0x08c3:
        r12 = move-exception;
        r12.printStackTrace();
        goto L_0x054d;
    L_0x08c9:
        r37 = 0;
        goto L_0x0067;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.common.CommonFeature.handleWebLink(android.content.Intent):boolean");
    }

    private void setArguments(String strData) {
        if (SygicMain.getInstance() != null && strData != null && strData.length() > 0) {
            SygicMain.getInstance().SetArguments(strData);
        }
    }
}
