package com.sygic.aura.feature.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.sygic.aura.CameraActivity;
import com.sygic.aura.ProjectsConsts;
import com.sygic.aura.SygicMain;
import com.sygic.aura.SygicPreferences;
import com.sygic.aura.WebActivity;
import com.sygic.aura.analytics.GoogleAnalyticsLogger;
import com.sygic.aura.c2dm.C2DMessaging;
import com.sygic.aura.clazz.ImageInfo;
import com.sygic.aura.feature.system.LowSystemFeature.EEventType;
import com.sygic.aura.utils.PictureData;
import com.sygic.aura.utils.Utils;
import com.sygic.base.C1799R;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import loquendo.tts.engine.TTSConst;

/* compiled from: LowSystemFeature */
class LowSystemFeatureBase extends LowSystemFeature {

    /* renamed from: com.sygic.aura.feature.system.LowSystemFeatureBase.1 */
    class LowSystemFeature implements MediaScannerConnectionClient {
        LowSystemFeature() {
        }

        public void onScanCompleted(String path, Uri uri) {
            if (path.equals(LowSystemFeatureBase.this.mImageInfo.getPath())) {
                LowSystemFeatureBase.this.mMsc.disconnect();
            }
        }

        public void onMediaScannerConnected() {
            LowSystemFeatureBase.this.mMsc.scanFile(LowSystemFeatureBase.this.mImageInfo.getPath(), null);
        }
    }

    /* renamed from: com.sygic.aura.feature.system.LowSystemFeatureBase.2 */
    static /* synthetic */ class LowSystemFeature {
        static final /* synthetic */ int[] f1259x9682750e;

        static {
            f1259x9682750e = new int[EEventType.values().length];
            try {
                f1259x9682750e[EEventType.ETNone.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1259x9682750e[EEventType.ETStart.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1259x9682750e[EEventType.ETEnd.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1259x9682750e[EEventType.ETUserId.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1259x9682750e[EEventType.ETMarketplace.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1259x9682750e[EEventType.ETFBEvent.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    protected LowSystemFeatureBase() {
    }

    protected LowSystemFeatureBase(Context context) {
        super(context);
    }

    public void browserOpenUri(String strUri, String strType, String strTag) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(strUri));
        intent.setFlags(268435456);
        if (strType == null) {
            try {
                this.mCtx.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (strType.equals("pdf")) {
            File f = new File(strUri);
            if (f != null && f.exists()) {
                intent.setDataAndType(Uri.fromFile(f), "application/pdf");
                try {
                    this.mCtx.startActivity(intent);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else if (strType.equals("fb")) {
            if (this.mFacebookConnect == null) {
                this.mFacebookConnect = new FacebookConnect();
            }
            if (!this.mFacebookConnect.isFBApplication(this.mCtx)) {
                this.mCtx.startActivity(intent);
            } else if (!this.mFacebookConnect.startSingleSignOn(this.mCtx, "105659562809354", 223)) {
            }
        } else if (strType.equals("db")) {
            if (this.mDropBoxLogin == null) {
                this.mDropBoxLogin = new DropBoxLogin(this.mCtx);
            }
            this.mDropBoxLogin.login(strUri);
        } else if (strType.equals("in")) {
            boolean consumed = SygicMain.getCoreEventsListener() != null && SygicMain.getCoreEventsListener().onWebViewOpenUri(strUri, strTag);
            if (!consumed) {
                Intent in = new Intent(this.mCtx, WebActivity.class);
                in.setData(Uri.parse(strUri));
                Activity activity = SygicMain.getInstance().getFeature().getActivity();
                if (activity != null) {
                    activity.startActivityForResult(in, 226);
                } else {
                    this.mCtx.startActivity(in);
                }
            }
        }
    }

    public void sendEmail(String strTo, String strSubject, String strBody) {
        if (strTo != null) {
            if (strBody == null) {
                strBody = "";
            }
            if (strSubject == null) {
                strSubject = "";
            }
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/html");
            intent.putExtra("android.intent.extra.EMAIL", strTo.split(";"));
            intent.putExtra("android.intent.extra.SUBJECT", strSubject);
            intent.putExtra("android.intent.extra.TEXT", Html.fromHtml(strBody));
            Activity activity = SygicMain.getInstance().getFeature().getActivity();
            if (activity != null) {
                activity.startActivityForResult(intent, 221);
            } else {
                this.mCtx.startActivity(intent);
            }
            waitForResult();
        }
    }

    public Object getImage(int x, int y) {
        int nImageSize = Math.min(x, y);
        String strImage = this.mCtx.getFilesDir() + "/avatar.jpg";
        if (this.mImageInfo == null) {
            this.mImageInfo = new ImageInfo();
        }
        this.mImageInfo.setImage(strImage, nImageSize);
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        Activity activity = SygicMain.getInstance().getFeature().getActivity();
        if (activity != null) {
            activity.startActivityForResult(intent, 215);
        } else {
            this.mCtx.startActivity(intent);
        }
        waitForResult();
        return this.mImageInfo;
    }

    public Object getPhoto(int x, int y) {
        int nImageSize = Math.min(x, y);
        String strImage = this.mCtx.getFilesDir() + "/avatar.jpg";
        if (this.mImageInfo == null) {
            this.mImageInfo = new ImageInfo();
        }
        this.mImageInfo.setImage(strImage, nImageSize);
        Intent intent = new Intent(this.mCtx.getApplicationContext(), CameraActivity.class);
        Activity activity = SygicMain.getInstance().getFeature().getActivity();
        if (activity != null) {
            activity.startActivityForResult(intent, 216);
        } else {
            this.mCtx.startActivity(intent);
        }
        waitForResult();
        return this.mImageInfo;
    }

    public Object takePhoto(long lX, long lY) {
        if (this.mImageInfo == null) {
            this.mImageInfo = new ImageInfo();
        }
        this.mImageInfo.setLatLong(lY, lX);
        Intent intent = new Intent(this.mCtx.getApplicationContext(), CameraActivity.class);
        Activity activity = SygicMain.getInstance().getFeature().getActivity();
        if (activity != null) {
            activity.startActivityForResult(intent, 216);
        } else {
            this.mCtx.startActivity(intent);
        }
        waitForResult();
        if (this.mImageInfo != null) {
            this.mMsc = new MediaScannerConnection(this.mCtx, new LowSystemFeature());
            this.mMsc.connect();
        }
        return this.mImageInfo;
    }

    public void logEvent(String strEvent, String strParams, String strValue, int nType) {
        if (!this.mbIsLabrary && this.m_bEnableEvents) {
            int i;
            Map params = null;
            String[] arrParams = null;
            if (strParams != null) {
                arrParams = strParams.split(":");
            }
            if (arrParams != null && arrParams.length > 0) {
                params = new HashMap();
                for (i = 0; i < arrParams.length / 2; i++) {
                    params.put(arrParams[i * 2], arrParams[(i * 2) + 1]);
                }
            }
            switch (LowSystemFeature.f1259x9682750e[EEventType.createFromInt(nType).ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    FlurryAgent.logEvent(strEvent, params);
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if (params != null) {
                        FlurryAgent.logEvent(strEvent, params, true);
                    } else {
                        FlurryAgent.logEvent(strEvent, true);
                    }
                case TTSConst.TTSUNICODE /*3*/:
                    FlurryAgent.endTimedEvent(strEvent);
                case TTSConst.TTSXML /*4*/:
                    FlurryAgent.setUserId(strEvent);
                case TTSConst.TTSEVT_TEXT /*5*/:
                    EventBuilder builder = new EventBuilder().setCategory("marketplace").setAction(strEvent).setLabel(strValue);
                    if (arrParams != null && arrParams.length > 0) {
                        for (i = 0; i < arrParams.length / 2; i++) {
                            builder.setCustomDimension(i + 1, arrParams[(i * 2) + 1]);
                        }
                    }
                    GoogleAnalyticsLogger.getInstance(this.mCtx).getTracker().send(builder.build());
                case TTSConst.TTSEVT_SENTENCE /*6*/:
                    if (params != null) {
                        if (!TextUtils.isEmpty(strValue)) {
                            try {
                                sendFacebookEvent(strEvent, Double.parseDouble(strValue), parseFacebookParams(params));
                                return;
                            } catch (NumberFormatException e) {
                                Log.w("Facebook_log", "trying to log FB event with incorrect VALUE_TO_SUM parameter");
                            }
                        }
                        sendFacebookEvent(strEvent, parseFacebookParams(params));
                    }
                default:
            }
        }
    }

    private void sendFacebookEvent(String strEvent, Bundle params) {
        String duplicityEvent = duplicateFacebookEvent(strEvent);
        if (duplicityEvent != null) {
            sendFacebookEvent(duplicityEvent, params);
        }
        AppEventsLogger.newLogger(this.mCtx.getApplicationContext()).logEvent(strEvent, params);
    }

    private void sendFacebookEvent(String strEvent, double valueToSum, Bundle params) {
        String duplicityEvent = duplicateFacebookEvent(strEvent);
        if (duplicityEvent != null) {
            sendFacebookEvent(duplicityEvent, valueToSum, params);
        }
        AppEventsLogger.newLogger(this.mCtx.getApplicationContext()).logEvent(strEvent, valueToSum, params);
    }

    private String duplicateFacebookEvent(String strEvent) {
        if ("fb_mobile_add_to_cart".equals(strEvent)) {
            return "product_priceClicked";
        }
        if ("fb_mobile_add_to_wishlist".equals(strEvent)) {
            return "product_show";
        }
        return null;
    }

    private Bundle parseFacebookParams(Map<String, String> params) {
        Bundle bundle = new Bundle(params.size());
        for (Entry<String, String> pairs : params.entrySet()) {
            bundle.putString((String) pairs.getKey(), (String) pairs.getValue());
        }
        return bundle;
    }

    public void enableEventLogging(boolean bEnable) {
        this.m_bEnableEvents = bEnable;
        if (bEnable) {
            startEvents();
        } else {
            stopEvents();
        }
    }

    private void waitForResult() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle;
        switch (requestCode) {
            case 215:
                if (data != null) {
                    bundle = data.getExtras();
                    Uri uri = data.getData();
                    if (bundle != null) {
                        PictureData.makePicture(bundle.getByteArray("_data"), this.mImageInfo, this.mCtx.getContentResolver());
                    } else if (uri != null) {
                        Cursor cur = this.mCtx.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
                        if (cur != null && cur.getCount() == 1) {
                            cur.moveToFirst();
                            int i = cur.getColumnIndex("_data");
                            if (i >= 0) {
                                PictureData.makePicture(cur.getString(i), this.mImageInfo);
                            }
                        }
                    }
                } else {
                    this.mImageInfo = null;
                }
                synchronized (this) {
                    notify();
                    break;
                }
            case 216:
                if (data != null) {
                    bundle = data.getExtras();
                    if (bundle != null) {
                        PictureData.makePicture(bundle.getByteArray("_data"), this.mImageInfo, this.mCtx.getContentResolver());
                    }
                } else {
                    this.mImageInfo = null;
                }
                synchronized (this) {
                    notify();
                    break;
                }
            case 221:
                synchronized (this) {
                    notify();
                    break;
                }
            case 223:
                if (this.mFacebookConnect != null && this.mFacebookConnect.startSingleSignOnCallback(requestCode, resultCode, data)) {
                    String strData = this.mFacebookConnect.getAccessToken();
                    if (!TextUtils.isEmpty(strData)) {
                        setArguments("fb105659562809354://?access_token=" + strData);
                    }
                }
            case 224:
                if (data != null) {
                    if (data.hasExtra("ACCESS_TOKEN")) {
                        String token = data.getStringExtra("ACCESS_TOKEN");
                        String secret = data.getStringExtra("ACCESS_SECRET");
                        SygicMain.getInstance().SetArguments("db-://oauth_token_secret=" + secret + "&oauth_token=" + token + "&uid=" + data.getStringExtra("UID"));
                    }
                }
                synchronized (this) {
                    notify();
                    break;
                }
            case 225:
                if (data != null) {
                    if (data.hasExtra("EXTRA_URL")) {
                        SygicMain.getInstance().SetArguments(data.getStringExtra("EXTRA_URL"));
                    }
                }
            case 226:
                if (data != null) {
                    if (data.hasExtra("EXTRA_ACCESS_TOKEN")) {
                        SygicMain.getInstance().SetArguments("https://accounts.google.com/o/oauth2/approval&response_code=" + data.getStringExtra("ACCESS_TOKEN"));
                    }
                }
            default:
        }
    }

    public boolean getFullscreen() {
        return SygicPreferences.getFullscreen(this.mCtx);
    }

    public void setFullscreen(boolean bFull) {
        SygicPreferences.setFullscreen(this.mCtx, bFull);
    }

    public void createShortcut(String strName, String strUri) {
        Intent i = new Intent("android.intent.action.VIEW", Uri.parse(strUri));
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra("android.intent.extra.shortcut.INTENT", i);
        intent.putExtra("android.intent.extra.shortcut.NAME", strName);
        intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(this.mCtx, C1799R.drawable.sygic));
        this.mCtx.sendBroadcast(intent);
    }

    public int getRotationLock() {
        Activity activity = SygicMain.getInstance().getFeature().getActivity();
        if (activity != null) {
            return activity.getRequestedOrientation();
        }
        return -1;
    }

    public void setRotationLock(boolean bLock) {
        int nRequestedOrientation = 4;
        if (bLock) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) this.mCtx.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
            switch (((WindowManager) this.mCtx.getSystemService("window")).getDefaultDisplay().getOrientation()) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    nRequestedOrientation = 1;
                    if (dm.widthPixels > dm.heightPixels) {
                        nRequestedOrientation = 0;
                        break;
                    }
                    break;
                case TTSConst.TTSMULTILINE /*1*/:
                    nRequestedOrientation = 0;
                    if (dm.widthPixels < dm.heightPixels && Utils.getAndroidVersion() >= 9) {
                        nRequestedOrientation = 9;
                        break;
                    }
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if (Utils.getAndroidVersion() >= 9) {
                        nRequestedOrientation = 9;
                        if (dm.widthPixels > dm.heightPixels) {
                            nRequestedOrientation = 8;
                            break;
                        }
                    }
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    if (Utils.getAndroidVersion() >= 9) {
                        nRequestedOrientation = 8;
                        if (dm.widthPixels < dm.heightPixels && Utils.getAndroidVersion() >= 9) {
                            nRequestedOrientation = 1;
                            break;
                        }
                    }
                    break;
            }
        }
        Activity activity = SygicMain.getInstance().getFeature().getActivity();
        if (activity != null) {
            activity.setRequestedOrientation(nRequestedOrientation);
        }
    }

    public String getDeviceName() {
        return Build.MODEL;
    }

    public String getGUID() {
        String strUid = UUID.randomUUID().toString();
        if (strUid != null) {
            strUid.replace("-", "");
        }
        return strUid;
    }

    public String getPushToken() {
        SygicPreferences.setResetPushToken(this.mCtx, false);
        return C2DMessaging.getRegistrationId(this.mCtx);
    }

    public String getPackageName() {
        return this.mCtx.getPackageName();
    }

    public String getOSVersion() {
        return VERSION.RELEASE;
    }

    public String getReferral() {
        String strReferral = "";
        String strProjRef = ProjectsConsts.getString(14);
        String strApkRef = getRefApp();
        if (strProjRef != null) {
            return strProjRef;
        }
        if (!TextUtils.isEmpty(strApkRef)) {
            return strApkRef;
        }
        if (!SygicPreferences.hasReferrer(this.mCtx)) {
            return strReferral;
        }
        Map<String, String> mapRef = SygicPreferences.retrieveReferralParams(this.mCtx);
        for (String strKey : SygicPreferences.PREFS_REFERRALS_EXPECTED_PARAMETERS) {
            if (mapRef.containsKey(strKey)) {
                strReferral = strReferral.concat(strKey).concat("=").concat((String) mapRef.get(strKey)).concat("&");
            }
        }
        if (strReferral.endsWith("&")) {
            return strReferral.substring(0, strReferral.length() - 1);
        }
        return strReferral;
    }

    private String getRefApp() {
        String str = null;
        try {
            List<PackageInfo> packages = this.mCtx.getPackageManager().getInstalledPackages(0);
            PackageInfo[] array = (PackageInfo[]) packages.toArray(new PackageInfo[packages.size()]);
            for (PackageInfo pkgInfo : array) {
                if (pkgInfo.packageName.startsWith("com.sygic.aura.referral.")) {
                    String strRef = pkgInfo.packageName.substring("com.sygic.aura.referral.".length());
                    if (!TextUtils.isEmpty(strRef)) {
                        str = "utm_source=" + strRef;
                    }
                    return str;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private void setArguments(String strData) {
        if (strData != null) {
            SygicMain.getInstance().SetArguments(strData);
        }
    }

    private void startEvents() {
        if (!this.mbIsLabrary) {
            FlurryAgent.onStartSession(this.mCtx);
        }
    }

    private void stopEvents() {
        if (!this.mbIsLabrary) {
            FlurryAgent.onEndSession(this.mCtx);
        }
    }

    public void onCreate() {
        if (!this.mbIsLabrary) {
            FlurryAgent.setContinueSessionMillis(10800000);
            String strApiKey = ProjectsConsts.getString(15);
            if (TextUtils.isEmpty(strApiKey)) {
                strApiKey = "6MBK65TP988BV479PBWS";
            }
            FlurryAgent.init(this.mCtx, strApiKey);
        }
    }

    public void onStart() {
        if (this.m_bEnableEvents) {
            startEvents();
        }
        if (SygicPreferences.getResetPushToken(this.mCtx)) {
            SygicMain.getInstance().ResetPushToken();
            SygicPreferences.setResetPushToken(this.mCtx, false);
        }
    }

    public void onStop() {
        if (this.m_bEnableEvents) {
            stopEvents();
        }
    }

    public void onDestroy() {
        if (!this.mbIsLabrary) {
            FlurryAgent.onEndSession(this.mCtx);
        }
    }

    public void onPause() {
    }

    public void onResume() {
    }
}
