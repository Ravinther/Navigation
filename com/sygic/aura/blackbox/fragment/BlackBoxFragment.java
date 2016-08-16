package com.sygic.aura.blackbox.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.blackbox.AccLogger;
import com.sygic.aura.blackbox.BlackBoxAccelerometer;
import com.sygic.aura.blackbox.BlackBoxAccelerometer.BlackBoxCrashListener;
import com.sygic.aura.blackbox.BlackBoxService;
import com.sygic.aura.blackbox.interfaces.BlackBoxFragmentResultCallback;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.fragments.SettingsFragment;
import com.sygic.aura.views.font_specials.STextView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import loquendo.tts.engine.TTSConst;

public class BlackBoxFragment extends AbstractScreenFragment implements OnSharedPreferenceChangeListener, OnInfoListener, Callback, BlackBoxCrashListener, ExecutionOrder, BackPressedListener {
    private BlackBoxAccelerometer mAccelerometer;
    private int mBitRate;
    private LinkedList<String> mCachedFiles;
    private LinkedList<String> mCachedInfoFiles;
    private Camera mCamera;
    private int mCameraId;
    private boolean mClosingSelf;
    private STextView mCoordinatesText;
    private SimpleDateFormat mFileNameDateFormat;
    private int mFrameRate;
    private SurfaceHolder mHolder;
    private ViewGroup mInfoBar;
    private BufferedWriter mInfoWriter;
    private boolean mIsMinimized;
    private boolean mIsRecording;
    private View mMinimizeButton;
    private int mQuality;
    private Animatable mRecAnimation;
    private ImageView mRecInfo;
    private MediaRecorder mRecorder;
    private View mSaveButton;
    private View mSettingsButton;
    private STextView mSpeedText;
    private SurfaceView mSurface;
    private STextView mTimeText;
    private RepeatingThread mTimerThread;
    private boolean mVideoOnly;

    /* renamed from: com.sygic.aura.blackbox.fragment.BlackBoxFragment.1 */
    class C11431 implements OnClickListener {
        C11431() {
        }

        public void onClick(View view) {
            BlackBoxFragment.this.saveRecordingAndContinue();
        }
    }

    /* renamed from: com.sygic.aura.blackbox.fragment.BlackBoxFragment.2 */
    class C11442 implements OnClickListener {
        C11442() {
        }

        public void onClick(View view) {
            BlackBoxFragment.this.minimize();
        }
    }

    /* renamed from: com.sygic.aura.blackbox.fragment.BlackBoxFragment.3 */
    class C11453 implements OnClickListener {
        C11453() {
        }

        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt(SettingsFragment.ARG_XML, 2131034343);
            bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(BlackBoxFragment.this.getActivity(), 2131165346));
            bundle.putBoolean(SettingsFragment.ARG_CHANGE_SETTINGS, false);
            Fragments.add(BlackBoxFragment.this.getActivity(), SettingsFragment.class, "fragment_black_box_settings_tag", bundle);
        }
    }

    /* renamed from: com.sygic.aura.blackbox.fragment.BlackBoxFragment.4 */
    class C11464 implements DialogInterface.OnClickListener {
        final /* synthetic */ Context val$cachedContext;

        C11464(Context context) {
            this.val$cachedContext = context;
        }

        public void onClick(DialogInterface dialog, int which) {
            BlackBoxFragment.this.cleanUpFiles(this.val$cachedContext);
            BlackBoxFragment.this.closeSelf();
        }
    }

    /* renamed from: com.sygic.aura.blackbox.fragment.BlackBoxFragment.5 */
    class C11475 implements DialogInterface.OnClickListener {
        final /* synthetic */ Context val$cachedContext;

        C11475(Context context) {
            this.val$cachedContext = context;
        }

        public void onClick(DialogInterface dialog, int which) {
            BlackBoxFragment.this.moveTempFileToPublicStorage(this.val$cachedContext);
            BlackBoxFragment.this.closeSelf();
        }
    }

    /* renamed from: com.sygic.aura.blackbox.fragment.BlackBoxFragment.6 */
    class C11486 implements Runnable {
        final /* synthetic */ String val$position;
        final /* synthetic */ String val$speed;
        final /* synthetic */ String val$time;

        C11486(String str, String str2, String str3) {
            this.val$time = str;
            this.val$position = str2;
            this.val$speed = str3;
        }

        public void run() {
            if (BlackBoxFragment.this.mTimeText != null && BlackBoxFragment.this.mCoordinatesText != null && BlackBoxFragment.this.mSpeedText != null) {
                BlackBoxFragment.this.mTimeText.setText(this.val$time);
                if (this.val$position != null) {
                    BlackBoxFragment.this.mCoordinatesText.setText(this.val$position);
                }
                if (this.val$speed != null) {
                    BlackBoxFragment.this.mSpeedText.setText(this.val$speed);
                }
            }
        }
    }

    public BlackBoxFragment() {
        this.mIsRecording = false;
        this.mIsMinimized = true;
        this.mFileNameDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        this.mAccelerometer = new BlackBoxAccelerometer();
        this.mClosingSelf = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.registerOnSharedPreferenceChangeListener(this);
        this.mCamera = getCamera();
        if (this.mCamera == null) {
            closeSelf();
            SToast.makeText(context, 2131165339, 1).show();
        }
        this.mCachedFiles = new LinkedList();
        this.mCachedInfoFiles = new LinkedList();
        if (preferences.getBoolean(getString(2131166269), true)) {
            this.mAccelerometer.register(context, this);
        }
        this.mVideoOnly = preferences.getBoolean(getString(2131166272), false);
        this.mQuality = preferences.getInt(getString(2131166271), -1);
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        refreshMapOverlays(true);
    }

    public void onDestroy() {
        super.onDestroy();
        Context context = getActivity();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.unregisterOnSharedPreferenceChangeListener(this);
        if (preferences.getBoolean(getString(2131166269), true)) {
            this.mAccelerometer.unregister();
        }
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
        refreshMapOverlays(false);
        cleanUpFiles(context);
    }

    private void refreshMapOverlays(boolean visible) {
        MapOverlayFragment mapOverlayFragment = (MapOverlayFragment) getFragmentManager().findFragmentByTag("fragment_browse_controls_tag");
        if (mapOverlayFragment != null) {
            mapOverlayFragment.setBlackBoxMaximizeVisible(visible);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903098, container, false);
        this.mSaveButton = view.findViewById(2131624170);
        this.mMinimizeButton = view.findViewById(2131624172);
        this.mInfoBar = (ViewGroup) view.findViewById(2131624171);
        this.mSettingsButton = view.findViewById(2131624173);
        initInfoBar();
        this.mRecInfo = (ImageView) view.findViewById(2131624169);
        this.mRecAnimation = (Animatable) this.mRecInfo.getDrawable();
        this.mSurface = (SurfaceView) view.findViewById(2131624168);
        this.mSurface.setZOrderMediaOverlay(true);
        this.mSurface.getHolder().addCallback(this);
        view.findViewById(2131624170).setOnClickListener(new C11431());
        view.findViewById(2131624172).setOnClickListener(new C11442());
        this.mSettingsButton.setOnClickListener(new C11453());
        return view;
    }

    public static int getCameraId() {
        int numberOfCameras = Camera.getNumberOfCameras();
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 0) {
                return i;
            }
        }
        return 0;
    }

    private Camera getCamera() {
        if (this.mCamera != null) {
            return this.mCamera;
        }
        this.mCameraId = getCameraId();
        if (Camera.getNumberOfCameras() != 0) {
            try {
                this.mCamera = Camera.open(this.mCameraId);
            } catch (RuntimeException e) {
                CrashlyticsHelper.logException(getClass().getName(), "getCamera", e);
                return null;
            }
        }
        return this.mCamera;
    }

    private void initInfoBar() {
        CharSequence date = this.mTimeText != null ? this.mTimeText.getText() : "";
        this.mTimeText = (STextView) this.mInfoBar.findViewById(2131624380);
        this.mTimeText.setText(date);
        CharSequence coords = this.mCoordinatesText != null ? this.mCoordinatesText.getText() : "";
        this.mCoordinatesText = (STextView) this.mInfoBar.findViewById(2131624382);
        this.mCoordinatesText.setText(coords);
        CharSequence speed = this.mSpeedText != null ? this.mSpeedText.getText() : "";
        this.mSpeedText = (STextView) this.mInfoBar.findViewById(2131624381);
        this.mSpeedText.setText(speed);
    }

    private void saveRecordingAndContinue() {
        if (this.mIsRecording) {
            stopRecording();
            moveTempFileToPublicStorage(getActivity());
            this.mCachedFiles.clear();
            this.mCachedInfoFiles.clear();
            try {
                this.mCamera.reconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            startRecording();
        }
    }

    private void minimize() {
        LayoutParams params = (LayoutParams) this.mSurface.getLayoutParams();
        params.height = 1;
        params.width = 1;
        this.mSurface.setLayoutParams(params);
        PositionInfo.nativeEnableMapView();
        this.mInfoBar.setVisibility(8);
        this.mSaveButton.setVisibility(8);
        this.mMinimizeButton.setVisibility(8);
        this.mSettingsButton.setVisibility(8);
        this.mRecInfo.setVisibility(8);
        this.mRecAnimation.stop();
        this.mIsMinimized = true;
        if (this.mResultCallback != null) {
            ((BlackBoxFragmentResultCallback) this.mResultCallback).onBlackBoxMinimized();
        }
    }

    public void maximize() {
        LayoutParams params = (LayoutParams) this.mSurface.getLayoutParams();
        params.height = -1;
        params.width = -1;
        params.bottomMargin = 0;
        params.leftMargin = 0;
        this.mSurface.setLayoutParams(params);
        PositionInfo.nativeDisableMapView();
        this.mInfoBar.setVisibility(0);
        this.mSaveButton.setVisibility(0);
        this.mMinimizeButton.setVisibility(0);
        this.mSettingsButton.setVisibility(0);
        this.mRecInfo.setVisibility(0);
        this.mRecAnimation.start();
        this.mIsMinimized = false;
        if (this.mResultCallback != null) {
            ((BlackBoxFragmentResultCallback) this.mResultCallback).onBlackBoxMaximized();
        }
    }

    @TargetApi(9)
    public void setCameraDisplayOrientation() {
        if (VERSION.SDK_INT >= 9) {
            int orientation = calculateCameraOrientation();
            this.mCamera = getCamera();
            this.mCamera.setDisplayOrientation(orientation);
            if (!this.mIsRecording && this.mRecorder != null) {
                this.mRecorder.setOrientationHint(orientation);
            }
        }
    }

    @TargetApi(9)
    private int calculateCameraOrientation() {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(this.mCameraId, info);
        int degrees = getRotationDegrees(getActivity().getWindowManager().getDefaultDisplay().getRotation());
        if (info.facing == 1) {
            return (360 - ((info.orientation + degrees) % 360)) % 360;
        }
        return ((info.orientation - degrees) + 360) % 360;
    }

    private int getRotationDegrees(int rotation) {
        switch (rotation) {
            case TTSConst.TTSMULTILINE /*1*/:
                return 90;
            case TTSConst.TTSPARAGRAPH /*2*/:
                return 180;
            case TTSConst.TTSUNICODE /*3*/:
                return 270;
            default:
                return 0;
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setCameraDisplayOrientation();
        this.mInfoBar.removeAllViews();
        getActivity().getLayoutInflater().inflate(2130903159, this.mInfoBar, true);
        initInfoBar();
    }

    @TargetApi(10)
    private void startRecording() {
        AccLogger.startLogging();
        this.mRecorder = new MediaRecorder();
        this.mCamera = getCamera();
        setCameraDisplayOrientation();
        disableCameraShutterSound();
        try {
            this.mCamera.unlock();
            this.mRecorder.setCamera(this.mCamera);
            setRecorder();
            String fileName = getOutputFileName();
            this.mRecorder.setOutputFile(fileName);
            addFileToList(fileName, this.mCachedFiles);
            this.mRecorder.setMaxDuration(60000);
            this.mRecorder.setOnInfoListener(this);
            prepareRecorder(this.mHolder);
            this.mRecorder.start();
            String infoFileName = createInfoFile();
            addFileToList(infoFileName, this.mCachedInfoFiles);
            startTicking(infoFileName);
            this.mIsRecording = true;
        } catch (RuntimeException e) {
            CrashlyticsHelper.logException(getClass().getName(), "startRecording", e);
            SToast.makeText(getActivity(), 2131165348, 0).show();
            closeSelf();
        }
    }

    private void setRecorder() {
        this.mRecorder.setVideoSource(1);
        if (!this.mVideoOnly) {
            this.mRecorder.setAudioSource(5);
        }
        CamcorderProfile profile = CamcorderProfile.get(this.mCameraId, getQuality(this.mCameraId, this.mQuality));
        profile.videoCodec = 2;
        this.mFrameRate = profile.videoFrameRate;
        this.mBitRate = profile.videoBitRate;
        if (VERSION.SDK_INT >= 10) {
            profile.audioCodec = 3;
        } else {
            profile.audioCodec = 0;
        }
        this.mRecorder.setOutputFormat(profile.fileFormat);
        this.mRecorder.setVideoFrameRate(profile.videoFrameRate);
        this.mRecorder.setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);
        this.mRecorder.setVideoEncodingBitRate(profile.videoBitRate);
        this.mRecorder.setVideoEncoder(profile.videoCodec);
        if (!this.mVideoOnly) {
            this.mRecorder.setAudioEncodingBitRate(profile.audioBitRate);
            this.mRecorder.setAudioChannels(profile.audioChannels);
            this.mRecorder.setAudioSamplingRate(profile.audioSampleRate);
            this.mRecorder.setAudioEncoder(profile.audioCodec);
        }
    }

    @TargetApi(11)
    public static int getQuality(int cameraId, int preferredQuality) {
        if (preferredQuality >= 0 && CamcorderProfile.hasProfile(cameraId, preferredQuality)) {
            return preferredQuality;
        }
        if (CamcorderProfile.hasProfile(cameraId, 4)) {
            return 4;
        }
        if (CamcorderProfile.hasProfile(cameraId, 3)) {
            return 3;
        }
        if (CamcorderProfile.hasProfile(cameraId, 2)) {
            return 2;
        }
        return 0;
    }

    private static void addFileToList(String fileName, LinkedList<String> cache) {
        cache.addLast(fileName);
        if (cache.size() > 5) {
            File toBeDeleted = new File((String) cache.removeFirst());
            if (toBeDeleted.exists()) {
                toBeDeleted.delete();
            }
        }
    }

    @TargetApi(17)
    private void disableCameraShutterSound() {
        if (VERSION.SDK_INT >= 17) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(this.mCameraId, info);
            if (info.canDisableShutterSound) {
                try {
                    this.mCamera.enableShutterSound(false);
                } catch (NullPointerException e) {
                    CrashlyticsHelper.logException(getClass().getName(), "disableCameraShutterSound", e);
                }
            }
        }
    }

    private String getOutputFileName() {
        return new File(getCacheDir(), "blackbox_" + this.mFileNameDateFormat.format(new Date()) + ".mp4").getAbsolutePath();
    }

    private File getCacheDir() {
        Context context = getActivity();
        File result = context.getExternalCacheDir();
        if (result == null) {
            result = context.getCacheDir();
        }
        if (!result.exists()) {
            result.mkdirs();
        }
        return result;
    }

    private void prepareRecorder(SurfaceHolder holder) {
        this.mRecorder.setPreviewDisplay(holder.getSurface());
        try {
            this.mRecorder.prepare();
        } catch (IOException e) {
            CrashlyticsHelper.logException(getClass().getName(), "prepareRecorder", e);
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.mHolder = holder;
        if (!this.mIsMinimized) {
            PositionInfo.nativeDisableMapView();
        }
        startRecording();
        if (this.mResultCallback != null) {
            ((BlackBoxFragmentResultCallback) this.mResultCallback).onBlackBoxRecordStarted();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.mIsRecording) {
            stopRecording();
        }
        try {
            this.mCamera.lock();
        } catch (RuntimeException e) {
            CrashlyticsHelper.logException(getClass().getName(), "camera.lock", e);
        }
        this.mCamera.release();
        this.mCamera = null;
        Activity context = getActivity();
        if (isRemoving() || (context != null && context.isFinishing())) {
            cleanUpFiles(context);
        }
        if (this.mResultCallback != null && this.mClosingSelf) {
            boolean z;
            BlackBoxFragmentResultCallback blackBoxFragmentResultCallback = (BlackBoxFragmentResultCallback) this.mResultCallback;
            if (this.mIsMinimized) {
                z = false;
            } else {
                z = true;
            }
            blackBoxFragmentResultCallback.onBlackBoxRecordFinished(z);
            this.mClosingSelf = false;
        }
        PositionInfo.nativeEnableMapView();
    }

    private void stopRecording() {
        this.mIsRecording = false;
        stopTicking();
        try {
            this.mRecorder.stop();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        this.mRecorder.release();
        this.mRecorder = null;
        AccLogger.stopLogging();
    }

    public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {
        if (what == 800) {
            stopAndRestart();
        }
    }

    private void stopAndRestart() {
        stopAndRestart(false);
    }

    private void stopAndRestart(boolean clear) {
        stopRecording();
        if (clear) {
            cleanUpFiles(getActivity());
            this.mCachedFiles.clear();
            this.mCachedInfoFiles.clear();
        }
        try {
            this.mCamera.reconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startRecording();
    }

    public boolean onBackPressed() {
        if (this.mIsMinimized) {
            return false;
        }
        minimize();
        return true;
    }

    public void finishRecording() {
        if (this.mIsRecording) {
            stopRecording();
        }
        Context cachedContext = getActivity();
        new Builder(cachedContext).title(2131165338).body(2131165335).positiveButton(2131165337, new C11475(cachedContext)).negativeButton(2131165336, new C11464(cachedContext)).build().showAllowingStateLoss("fragment_black_box_save_tag");
    }

    private void closeSelf() {
        this.mClosingSelf = true;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().remove(this).commit();
        }
    }

    private void cleanUpFiles(Context context) {
        if (context != null) {
            Intent serviceIntent = new Intent(context, BlackBoxService.class);
            serviceIntent.setAction("blackbox_delete_files");
            serviceIntent.putExtra("blackbox_extra_files_paths", (String[]) this.mCachedFiles.toArray(new String[this.mCachedFiles.size()]));
            serviceIntent.putExtra("blackbox_extra_info_files_paths", (String[]) this.mCachedInfoFiles.toArray(new String[this.mCachedInfoFiles.size()]));
            context.startService(serviceIntent);
        }
    }

    private void moveTempFileToPublicStorage(Context context) {
        if (context != null) {
            Intent serviceIntent = new Intent(context, BlackBoxService.class);
            serviceIntent.setAction("blackbox_move_to_public");
            serviceIntent.putExtra("blackbox_extra_files_paths", (String[]) this.mCachedFiles.toArray(new String[this.mCachedFiles.size()]));
            serviceIntent.putExtra("blackbox_extra_info_files_paths", (String[]) this.mCachedInfoFiles.toArray(new String[this.mCachedInfoFiles.size()]));
            serviceIntent.putExtra("blackbox_extra_bitrate", this.mBitRate);
            serviceIntent.putExtra("blackbox_extra_framerate", this.mFrameRate);
            serviceIntent.putExtra("blackbox_extra_notif_saving", ResourceManager.getCoreString(context, 2131165350));
            serviceIntent.putExtra("blackbox_extra_notif_saved", ResourceManager.getCoreString(context, 2131165349));
            serviceIntent.putExtra("blackbox_extra_notif_share", ResourceManager.getCoreString(context, 2131165340));
            context.startService(serviceIntent);
            SToast.makeText(context, 2131165350, 1).show();
        }
    }

    private String createInfoFile() {
        return new File(getCacheDir(), "info_" + this.mFileNameDateFormat.format(new Date()) + ".txt").getAbsolutePath();
    }

    private void startTicking(String mInfoFile) {
        try {
            this.mInfoWriter = new BufferedWriter(new FileWriter(mInfoFile));
            this.mTimerThread = new RepeatingThread(this, 1000);
            this.mTimerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopTicking() {
        this.mTimerThread.setFinished(true);
        try {
            this.mInfoWriter.flush();
            this.mInfoWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUi(String time, String position, String speed) {
        if (isAdded()) {
            getActivity().runOnUiThread(new C11486(time, position, speed));
        }
    }

    public boolean runningCondition() {
        return true;
    }

    public boolean onPositive() {
        try {
            String dateTime = ResourceManager.nativeFormatCurrentDate() + " " + ResourceManager.nativeFormatCurrentTimeStampToDigits(true);
            String position = null;
            if (PositionInfo.nativeHasActualPosition(false)) {
                LongPosition longPos = PositionInfo.nativeGetVehiclePosition(true);
                position = ResourceManager.nativeFormatPosition(longPos.getX(), longPos.getY());
            }
            double speedValue = PositionInfo.nativeGetCurrentVehicleSpeed();
            String speedFormatted = null;
            if (speedValue != -1.0d) {
                speedFormatted = ResourceManager.nativeFormatSpeed(speedValue, false, true);
            }
            updateUi(dateTime, position, speedFormatted);
            this.mInfoWriter.write(dateTime + "|" + nullToEmpty(position) + "|" + nullToEmpty(speedFormatted));
            this.mInfoWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AccLogger.log();
        return true;
    }

    private String nullToEmpty(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    public boolean onNegative() {
        return false;
    }

    public void onCrash() {
        saveRecordingAndContinue();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (getString(2131166269).equals(key)) {
            if (sharedPreferences.getBoolean(key, true)) {
                this.mAccelerometer.register(getActivity(), this);
            } else {
                this.mAccelerometer.unregister();
            }
        }
        if (getString(2131166272).equals(key)) {
            this.mVideoOnly = sharedPreferences.getBoolean(key, false);
            stopAndRestart();
        }
        if (getString(2131166271).equals(key)) {
            this.mQuality = sharedPreferences.getInt(key, -1);
            stopAndRestart(true);
        }
    }

    public static BlackBoxFragment getBlackBoxFragment(FragmentManager fragmentManager) {
        return (BlackBoxFragment) fragmentManager.findFragmentByTag("fragment_black_box_tag");
    }
}
