package com.sygic.aura;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.List;

/* compiled from: CameraActivity */
class CameraPreview extends SurfaceView implements Callback {
    Activity mActivity;
    Camera mCamera;
    SurfaceHolder mHolder;
    PictureCallback mPictureCallback;

    /* renamed from: com.sygic.aura.CameraPreview.1 */
    class CameraActivity implements PictureCallback {
        CameraActivity() {
        }

        public void onPictureTaken(byte[] data, Camera camera) {
            Intent intent = new Intent();
            intent.putExtra("_data", data);
            CameraPreview.this.mActivity.setResult(-1, intent);
            CameraPreview.this.mActivity.finish();
        }
    }

    CameraPreview(Context context) {
        super(context);
        this.mPictureCallback = new CameraActivity();
        this.mActivity = (Activity) context;
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            this.mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mCamera != null) {
            try {
                this.mCamera.setPreviewDisplay(holder);
            } catch (IOException e2) {
                this.mCamera.release();
                this.mCamera = null;
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.mCamera != null) {
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (this.mCamera != null) {
            Parameters parameters = this.mCamera.getParameters();
            try {
                parameters.setPreviewSize(w, h);
                Size optSize = getOptimalPreviewSize(parameters.getSupportedPreviewSizes(), w, h);
                parameters.setPreviewSize(optSize.width, optSize.height);
                this.mCamera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mCamera.startPreview();
        }
    }

    public Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        if (sizes == null) {
            return null;
        }
        double fTargetRatio = (double) (w / h);
        double fMinDiff = Double.MAX_VALUE;
        Size optSize = null;
        int nTargetHeight = h;
        for (Size size : sizes) {
            if (Math.abs(((double) (size.width / size.height)) - fTargetRatio) <= 0.05d && ((double) Math.abs(size.height - nTargetHeight)) < fMinDiff) {
                optSize = size;
                fMinDiff = (double) Math.abs(size.height - nTargetHeight);
            }
        }
        if (optSize != null) {
            return optSize;
        }
        fMinDiff = Double.MAX_VALUE;
        for (Size size2 : sizes) {
            if (((double) Math.abs(size2.height - nTargetHeight)) < fMinDiff) {
                optSize = size2;
                fMinDiff = (double) Math.abs(size2.height - nTargetHeight);
            }
        }
        return optSize;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.mCamera == null) {
            return false;
        }
        if (event.getAction() != 0 || (keyCode != 23 && keyCode != 27)) {
            return super.onKeyDown(keyCode, event);
        }
        this.mCamera.takePicture(null, null, this.mPictureCallback);
        return true;
    }
}
