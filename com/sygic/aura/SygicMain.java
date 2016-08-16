package com.sygic.aura;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceView;
import com.sygic.aura.clazz.LocationInfo;
import com.sygic.aura.clazz.SatelliteInfoArray;
import com.sygic.aura.events.ActivityEventListener;
import com.sygic.aura.events.ActivityUserInteractionListener;
import com.sygic.aura.events.CoreEventsListener;
import com.sygic.aura.events.EventService;
import com.sygic.aura.events.core.InitCoreListener;
import com.sygic.aura.events.key.KeyEventService;
import com.sygic.aura.events.touch.TouchEventService;
import com.sygic.aura.feature.Features;
import com.sygic.aura.feature.ResultListener;
import com.sygic.aura.hud.BroadcastHud;
import com.sygic.aura.hud.HudInterface;
import com.sygic.aura.utils.GuiUtils;
import java.util.ArrayList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class SygicMain {
    private static Activity mActivity;
    private static ActivityEventListener mActivityEventListener;
    private static ActivityUserInteractionListener mActivityUserInteractionListener;
    private static CoreEventsListener mCoreEventsListener;
    private static Handler mCoreHandler;
    private static EventService mEventService;
    private static Features mFeature;
    private static Handler mHandler;
    private static HudInterface mHudInterface;
    private static List<InitCoreListener> mInitCoreListeners;
    private static boolean mIsLoopEnabled;
    private static KeyEventService mKeyListener;
    private static SurfaceView mSurface;
    private static SygicMain mSygicMain;
    private static List<TouchEventService> mTouchEventListeners;
    private SparseArray<ResultListener> mActivityResult;
    private boolean mIsRunning;

    private static native void PostCommand(int i, int i2);

    private static native void SetDPI(float f);

    private static native void SurfaceCreated();

    private static native void SurfaceRotate(int i, int i2, int i3);

    private static native void SysSetRunningBackground(boolean z);

    public native void AccSetData(float f, float f2, float f3);

    public native int DoAppCycle();

    public native void Force3DBlit();

    public native void GpsSetCompassHeading(float f, float f2);

    public native void GpsSetData(LocationInfo locationInfo);

    public native void GpsSetNmeaData(String str);

    public native void GpsSetSatellites(SatelliteInfoArray satelliteInfoArray);

    public native void GpsSetStatus(int i);

    public native void HelperWinMain(String str);

    public native void InitJavaObjects();

    public native void KeyMessage(int i, int i2, boolean z);

    public native void KeybSetHeight(int i);

    public native void KeybSetHidden();

    public native void LogEvent(String str, String str2, int i, String str3, boolean z);

    public native void MouseMessage(float f, float f2, float f3);

    public native void MultipleTouchMessage(int i, int i2, int i3, int i4, int i5);

    public native void OnGpsStatus(boolean z);

    public native void OpenMySygic(String str, int i);

    public native int ProcessStoreResponse(String str, int i, int i2, int i3);

    public native void Quit();

    public native void RequestSurfaceReset();

    public native void ResetPushToken();

    public native void SetArguments(String str);

    public native void SetBatteryInfo(int i);

    public native void SetFBAccessToken(String str);

    public native void SetSwRenderer(boolean z);

    static {
        if (!SygicConsts.IS_PROTOTYPE) {
            System.loadLibrary("Aura");
        }
        mSygicMain = null;
        mFeature = null;
        mEventService = null;
        mSurface = null;
        mKeyListener = null;
        mActivityEventListener = null;
        mCoreEventsListener = null;
        mActivityUserInteractionListener = null;
        mTouchEventListeners = new ArrayList();
        mInitCoreListeners = new ArrayList();
        mIsLoopEnabled = true;
    }

    private SygicMain() {
        this.mIsRunning = false;
    }

    private SygicMain(Context context, Handler handler) {
        this.mIsRunning = false;
        mFeature = new Features(context, handler);
        mEventService = new EventService(context);
        mHudInterface = new BroadcastHud(context);
        initResultListeners();
        if (!SygicConsts.IS_PROTOTYPE) {
            InitJavaObjects();
        }
    }

    public static void initInstance(Context context, Handler handler) {
        if (mSygicMain == null || !mSygicMain.mIsRunning) {
            mSygicMain = new SygicMain(context, handler);
        }
    }

    public static SygicMain getInstance() {
        if (mSygicMain != null) {
            return mSygicMain;
        }
        throw new IllegalStateException("SygicMain class was not instanciated");
    }

    public static void setSurface(SurfaceView surface) {
        mSurface = surface;
        mFeature.getGlFeature().setSurface(surface);
        mFeature.getCommonFeature().setSurface(surface);
    }

    public static SurfaceView getSurface() {
        return mSurface;
    }

    public static void setActivity(Activity activity) {
        mActivity = activity;
    }

    public static void initFeature() {
        mFeature.setActivity(mActivity);
    }

    public static Activity getActivity() {
        return mActivity;
    }

    public static void setHandler(Handler handler) {
        mHandler = handler;
    }

    public void stopCore() {
        mCoreHandler = null;
        Quit();
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static void setCoreHandler(Handler handler) {
        mCoreHandler = handler;
    }

    public static Handler getCoreHandler() {
        return mCoreHandler;
    }

    public static boolean hasSurface() {
        return (mSygicMain == null || mFeature == null || mSurface == null) ? false : true;
    }

    public static void registerKeyListener(KeyEventService listener) {
        mKeyListener = listener;
    }

    public static void registerActivityEventListener(ActivityEventListener listener) {
        mActivityEventListener = listener;
    }

    public static void registerCoreEventsListener(CoreEventsListener listener) {
        mCoreEventsListener = listener;
    }

    public static void registerActivityUserInteractionListener(ActivityUserInteractionListener listener) {
        mActivityUserInteractionListener = listener;
    }

    public static void registerTouchListener(TouchEventService listener) {
        mTouchEventListeners.add(listener);
    }

    public static KeyEventService getKeyListener() {
        return mKeyListener;
    }

    public static ActivityEventListener getActivityEventListener() {
        return mActivityEventListener;
    }

    public static CoreEventsListener getCoreEventsListener() {
        return mCoreEventsListener;
    }

    public static ActivityUserInteractionListener getActivityUserInteractionListener() {
        return mActivityUserInteractionListener;
    }

    public static void notifyTouchListeners(MotionEvent event) {
        for (TouchEventService service : mTouchEventListeners) {
            service.onTouchEvent(event);
        }
    }

    public static void setNativeLoopEnabled(boolean bEnable) {
        mIsLoopEnabled = bEnable;
    }

    public static boolean isNativeLoopEnabled() {
        return mIsLoopEnabled;
    }

    public Features getFeature() {
        return mFeature;
    }

    public EventService getEventService() {
        return mEventService;
    }

    public static void registerInitCoreListener(InitCoreListener initCoreListener) {
        mInitCoreListeners.add(initCoreListener);
    }

    public static void unregisterInitCoreListener(InitCoreListener initCoreListener) {
        mInitCoreListeners.remove(initCoreListener);
    }

    public static AlertDialog showMessageDialog(AlertDialog dialog) {
        return GuiUtils.showMessage(mActivity, dialog);
    }

    private static void notifyInitCoreListeners() {
        for (InitCoreListener listener : mInitCoreListeners) {
            listener.onInitCoreDone();
        }
    }

    private void initResultListeners() {
        if (this.mActivityResult == null) {
            this.mActivityResult = new SparseArray();
        }
        this.mActivityResult.put(220, mFeature.getPhoneFeature());
        this.mActivityResult.put(221, mFeature.getSystemFeature());
        this.mActivityResult.put(215, mFeature.getSystemFeature());
        this.mActivityResult.put(216, mFeature.getSystemFeature());
        this.mActivityResult.put(223, mFeature.getSystemFeature());
        this.mActivityResult.put(224, mFeature.getSystemFeature());
        this.mActivityResult.put(225, mFeature.getSystemFeature());
        this.mActivityResult.put(226, mFeature.getSystemFeature());
        this.mActivityResult.put(227, mFeature.getStoreFeature());
        this.mActivityResult.put(228, mFeature.getTtsFeature());
    }

    public void delegateResult(int requestCode, int resultCode, Intent data) {
        if (this.mActivityResult != null && mFeature != null) {
            ResultListener resultListener = (ResultListener) this.mActivityResult.get(requestCode);
            if (resultListener != null) {
                resultListener.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void delegateActivityMethod(int nMethod) {
        switch (nMethod) {
            case TTSConst.TTSMULTILINE /*1*/:
                mFeature.getSystemFeature().onCreate();
            case TTSConst.TTSUNICODE /*3*/:
                mFeature.getSystemFeature().onStart();
            case TTSConst.TTSXML /*4*/:
                mFeature.getSystemFeature().onResume();
                mFeature.getGpsFeature().onResume();
                mFeature.getAutomotiveFeature().onResume();
            case TTSConst.TTSEVT_TEXT /*5*/:
                mFeature.getSystemFeature().onPause();
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                mFeature.getSystemFeature().onStop();
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                mFeature.getSystemFeature().onDestroy();
                mFeature.getAutomotiveFeature().onDestroy();
            default:
        }
    }

    public static void nativeSurfaceRotate(int cx, int cy, int nOrient) {
        if (!SygicConsts.IS_PROTOTYPE) {
            SurfaceRotate(cx, cy, nOrient);
        }
    }

    public static void nativeSurfaceCreated() {
        if (!SygicConsts.IS_PROTOTYPE) {
            SurfaceCreated();
        }
    }

    public static void nativeSetDPI(float fDpi) {
        if (!SygicConsts.IS_PROTOTYPE) {
            SetDPI(fDpi);
        }
    }

    public static void nativeSysSetRunningBackground(boolean bBackground) {
        if (!SygicConsts.IS_PROTOTYPE) {
            SysSetRunningBackground(bBackground);
        }
    }

    public static void nativePostCommand(int wParam, int lParam) {
        PostCommand(wParam, lParam);
    }

    public static void nativePostCommand(int wParam, short lParamL, short lParamH) {
        nativePostCommand(wParam, (lParamH << 16) | lParamL);
    }

    protected void SmsSend(String strNumber, String strText) {
        mFeature.getPhoneFeature().sendSms(strNumber, strText);
    }

    protected void PhoneCall(String strNumber) {
        mFeature.getPhoneFeature().makeCall(strNumber);
    }

    protected boolean PhoneHasNetwork() {
        return mFeature.getPhoneFeature().hasNetwork();
    }

    protected boolean PhoneIsRoaming() {
        return mFeature.getPhoneFeature().isRoaming();
    }

    protected int ContactsGetCount() {
        return mFeature.getPhoneFeature().getContactsCount();
    }

    protected boolean ContactsReset() {
        return mFeature.getPhoneFeature().resetContacts();
    }

    protected String ContactsReadNext() {
        return mFeature.getPhoneFeature().readNextContact();
    }

    protected String ContactsReadPhoto(int nPhotoId) {
        return mFeature.getPhoneFeature().readContactPhoto(nPhotoId);
    }

    protected String ContactsRead(int nContactId) {
        return mFeature.getPhoneFeature().readContact(nContactId);
    }

    protected int SoundInit(long lRate, int nChannels) {
        return mFeature.getSoundFeature().init(lRate, nChannels);
    }

    protected void SoundDeinit() {
        mFeature.getSoundFeature().deinit();
    }

    protected void SoundSetVolume(int nVolume) {
        mFeature.getSoundFeature().setVolume(nVolume);
    }

    protected void SoundWrite(byte[] lpBuffer, int nSize) {
        mFeature.getSoundFeature().write(lpBuffer, nSize);
    }

    protected void SoundPlay() {
        mFeature.getSoundFeature().play();
    }

    protected void SoundStop() {
        mFeature.getSoundFeature().stop();
    }

    protected boolean SoundMutex(boolean bMutex) {
        return mFeature.getSoundFeature().mutex(bMutex);
    }

    protected void ForceSpeaker(boolean bValue) {
        mFeature.getSoundFeature().forceSpeaker(bValue);
    }

    protected int GetBufferingTime() {
        return mFeature.getSoundFeature().getBufferingTime();
    }

    protected int TTS_Initialize(String strLanguage, String strVoice, int nVolume) {
        return mFeature.getTtsFeature().initialize(strLanguage, strVoice, nVolume);
    }

    protected String TTS_GetLanguageList(String[] supportedLocales, boolean installed) {
        return mFeature.getTtsFeature().getLanguageList(supportedLocales, installed);
    }

    protected String TTS_GetVoiceList(String strLanguage) {
        return mFeature.getTtsFeature().getVoiceList(strLanguage);
    }

    protected boolean TTS_SetVolume(int nVolume) {
        return mFeature.getTtsFeature().setVolume(nVolume);
    }

    protected boolean TTS_SetSpeed(int nRate) {
        return mFeature.getTtsFeature().setSpeed(nRate);
    }

    protected boolean TTS_Stop() {
        return mFeature.getTtsFeature().stop();
    }

    protected boolean TTS_Play(String strTextToPlay, boolean bSynch) {
        return mFeature.getTtsFeature().play(strTextToPlay, bSynch);
    }

    protected boolean TTS_Uninitialize() {
        return mFeature.getTtsFeature().uninitialize();
    }

    protected boolean TTS_Load(String strTtsPath, String strResPath, long dwType) {
        return mFeature.getTtsFeature().load(strTtsPath, strResPath, dwType);
    }

    protected boolean TTS_IsPlaying() {
        return mFeature.getTtsFeature().isPlaying();
    }

    protected void TTS_Unload() {
        mFeature.getTtsFeature().unload();
    }

    protected long TimeGetTickCount() {
        return mFeature.getTimeFeature().getTickCount();
    }

    protected long TimeGetCurrentTime() {
        return mFeature.getTimeFeature().getCurrentTime();
    }

    protected Object TimeGetTime(long lTime) {
        return mFeature.getTimeFeature().getTime(lTime);
    }

    protected long TimeConvertTime(int nYear, byte cbMonth, byte cbDay, byte cbHour, byte cbMin, byte cbSec) {
        return mFeature.getTimeFeature().convertTime(nYear, cbMonth, cbDay, cbHour, cbMin, cbSec);
    }

    protected int TimeGetTimeZone() {
        return mFeature.getTimeFeature().getTimeZone();
    }

    protected int GetDaylightSaving() {
        return mFeature.getTimeFeature().getDaylightSaving();
    }

    protected void BrowserOpenUri(String strUri, String strType, String strTag) {
        mFeature.getSystemFeature().browserOpenUri(strUri, strType, strTag);
    }

    protected void SendEmail(String strTo, String strSubject, String strBody) {
        mFeature.getSystemFeature().sendEmail(strTo, strSubject, strBody);
    }

    protected Object GetImage(int x, int y) {
        return mFeature.getSystemFeature().getImage(x, y);
    }

    protected Object GetPhoto(int x, int y) {
        return mFeature.getSystemFeature().getPhoto(x, y);
    }

    protected Object TakePhoto(long lX, long lY) {
        return mFeature.getSystemFeature().takePhoto(lX, lY);
    }

    protected void LogEvent(String strEvent, String strParams, String strValue, int nParam) {
        mFeature.getSystemFeature().logEvent(strEvent, strParams, strValue, nParam);
    }

    protected void EnableEventLogging(boolean bEnable) {
        mFeature.getSystemFeature().enableEventLogging(bEnable);
    }

    protected boolean GetFullscreen() {
        return mFeature.getSystemFeature().getFullscreen();
    }

    protected void SetFullscreen(boolean bFull) {
        mFeature.getSystemFeature().setFullscreen(bFull);
    }

    protected void CreateShortcut(String strName, String strUri) {
        mFeature.getSystemFeature().createShortcut(strName, strUri);
    }

    public void SetRotationLock(boolean bLock) {
        mFeature.getSystemFeature().setRotationLock(bLock);
    }

    public int GetRotationLock() {
        return mFeature.getSystemFeature().getRotationLock();
    }

    protected String GetDeviceName() {
        return mFeature.getSystemFeature().getDeviceName();
    }

    protected void EnableMirrorLink(int iMirrorLink) {
        mFeature.getAutomotiveFeature().enableMirrorLink(iMirrorLink);
    }

    protected String GetGUID() {
        return mFeature.getSystemFeature().getGUID();
    }

    protected String GetPushToken() {
        return mFeature.getSystemFeature().getPushToken();
    }

    protected String GetPackageName() {
        return mFeature.getSystemFeature().getPackageName();
    }

    protected String GetReferral() {
        return mFeature.getSystemFeature().getReferral();
    }

    protected String GetOSVersion() {
        return mFeature.getSystemFeature().getOSVersion();
    }

    protected boolean NetIsConnected() {
        return mFeature.getNetFeature().isConnected();
    }

    protected int NetGetType() {
        return mFeature.getNetFeature().getType();
    }

    protected int NetSecureConnect(String strHost) {
        return mFeature.getNetFeature().secureConnect(strHost);
    }

    protected boolean NetSecureDisconnect(int iHandle) {
        return mFeature.getNetFeature().secureDisconnect(iHandle);
    }

    protected int NetSecureSend(int iHandle, byte[] data, int nLen) {
        return mFeature.getNetFeature().secureSend(iHandle, data, nLen);
    }

    protected byte[] NetSecureReceive(int iHandle, int nLen) {
        return mFeature.getNetFeature().secureReceive(iHandle, nLen);
    }

    protected boolean StoreGetProductDetails(String strProductID) {
        return mFeature.getStoreFeature().getProductDetails(strProductID);
    }

    protected boolean StoreBuyProduct(String strProductID) {
        return mFeature.getStoreFeature().buyProduct(strProductID);
    }

    protected boolean StoreRestorePurchases() {
        return mFeature.getStoreFeature().restorePurchases();
    }

    protected void StoreCheckQueuedTransactions() {
        mFeature.getStoreFeature().checkQueuedTransactions();
    }

    protected boolean StoreIsEnabled() {
        return mFeature.getStoreFeature().isEnabled();
    }

    protected boolean StoreIsSupported() {
        return mFeature.getStoreFeature().isSupported();
    }

    protected void DeviceVibrate(long lTime) {
        mFeature.getDeviceFeature().vibrate(lTime);
    }

    protected String DeviceGetId(String strDevice) {
        return mFeature.getDeviceFeature().getId(strDevice);
    }

    protected String DeviceGetLocale() {
        return mFeature.getDeviceFeature().getLocale();
    }

    protected boolean DeviceFeature(boolean bReturned, int nFeature) {
        return mFeature.getDeviceFeature().getFeature(bReturned, nFeature);
    }

    protected String DeviceGetImei() {
        return mFeature.getDeviceFeature().getImei();
    }

    protected String DeviceGetMacAddress() {
        return mFeature.getDeviceFeature().getMacAddress();
    }

    protected boolean GpsOpen() {
        return mFeature.getGpsFeature().open();
    }

    protected void GpsClose() {
        mFeature.getGpsFeature().close();
    }

    protected boolean GpsIsEnabled() {
        return mFeature.getGpsFeature().isEnabled();
    }

    protected boolean GpsClearCache() {
        return mFeature.getGpsFeature().GpsClearCache();
    }

    protected boolean GpsUpdateCache() {
        return mFeature.getGpsFeature().GpsUpdateCache();
    }

    protected Object GetEglConfigs(boolean bGL2) {
        return mFeature.getGlFeature().getEglConfigs(bGL2);
    }

    protected int GetEglConfigAttr(int eglConfig, int nAttr) {
        return mFeature.getGlFeature().getEglConfigAttr(eglConfig, nAttr);
    }

    protected float GetEglVersion() {
        return mFeature.getGlFeature().getEglVersion();
    }

    protected int InitEgl(int eglConfig) {
        return mFeature.getGlFeature().initEgl(eglConfig);
    }

    protected void SetFixedSize(int w, int h) {
        mFeature.getGlFeature().setFixedSize(w, h);
    }

    protected void DestroyGlSurface() {
        mFeature.getGlFeature().destroyGlSurface();
    }

    protected void CreateGlSurface() {
        mFeature.getGlFeature().createGlSurface();
    }

    protected void Swap3DBuffers() {
        mFeature.getGlFeature().swap3DBuffers();
    }

    protected void DoDraw(int[] iArr, int sx, int sy, int sw, int sh) {
        mFeature.getGlFeature().doDraw(iArr, sx, sy, sw, sh);
    }

    protected void MakeCurrent() {
        mFeature.getGlFeature().makeCurrent();
    }

    protected String GetLibPath() {
        return mFeature.getCommonFeature().getLibPath();
    }

    protected void ShowKeyboard(boolean bShow) {
        mFeature.getCommonFeature().showKeyboard(bShow);
    }

    protected void SetKeyboardLayout(long lType) {
        mFeature.getCommonFeature().setKeyboardLayout(lType);
    }

    protected boolean CameraIsAvailable() {
        return mFeature.getCommonFeature().isCameraAvailable();
    }

    protected boolean CompassIsAvailable() {
        return mFeature.getCommonFeature().isCompassAvailable();
    }

    protected boolean IsTablet() {
        return mFeature.getCommonFeature().isTablet();
    }

    protected int GetDisplayResolution() {
        return mFeature.getCommonFeature().getDisplayResolution();
    }

    protected int GetAndroidSdkVersion() {
        return mFeature.getCommonFeature().getAndroidSdkVersion();
    }

    protected long GetFreeDiskSpace(String strPath) {
        return mFeature.getCommonFeature().getFreeDiskSpaceInKiloBytes(strPath);
    }

    protected void FileBackupSync(String strFile, int nOperation) {
    }

    protected String GetSdPath() {
        return mFeature.getCommonFeature().getSdPath();
    }

    protected String GetSygicRootPath() {
        return mFeature.getCommonFeature().getSygicRootPath();
    }

    public void SetIsRunning() {
        SetIsRunning(true);
    }

    public void SetIsRunning(boolean isRunning) {
        this.mIsRunning = isRunning;
        if (isRunning) {
            notifyInitCoreListeners();
        }
    }

    public boolean GetIsRunning() {
        return this.mIsRunning;
    }

    protected void UpdateHudValueInt(String key, int value) {
        mHudInterface.updateValue(key, value);
    }

    protected void UpdateHudValueIntArray(String key, int[] value) {
        mHudInterface.updateValue(key, value);
    }

    protected void UpdateHudValueLongArray(String key, long[] value) {
        mHudInterface.updateValue(key, value);
    }

    protected void UpdateHudValueString(String key, String value) {
        mHudInterface.updateValue(key, value);
    }

    protected void HudSend() {
        mHudInterface.send();
    }
}
