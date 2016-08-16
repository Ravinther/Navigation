package com.sygic.aura.blackbox;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build.VERSION;

public class BlackBoxAccelerometer implements SensorEventListener {
    private double[] gravity;
    private BlackBoxCrashListener mCrashListener;
    private long mLastCrashTimestamp;
    private SensorManager mSensorManager;

    public interface BlackBoxCrashListener {
        void onCrash();
    }

    public BlackBoxAccelerometer() {
        this.gravity = new double[3];
        this.mLastCrashTimestamp = 0;
    }

    public void register(Context ctx, BlackBoxCrashListener crashListener) {
        Sensor mAccelerometer;
        this.mSensorManager = (SensorManager) ctx.getSystemService("sensor");
        if (VERSION.SDK_INT >= 9) {
            mAccelerometer = this.mSensorManager.getDefaultSensor(10);
        } else {
            mAccelerometer = this.mSensorManager.getDefaultSensor(1);
        }
        if (mAccelerometer != null) {
            this.mCrashListener = crashListener;
            this.mSensorManager.registerListener(this, mAccelerometer, 3);
        }
    }

    public void unregister() {
        if (this.mCrashListener != null) {
            this.mCrashListener = null;
            this.mLastCrashTimestamp = 0;
            this.mSensorManager.unregisterListener(this);
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 1) {
            compensateGravity(sensorEvent);
        }
        float maxAcceleration = Math.max(Math.max(Math.abs(sensorEvent.values[0]), Math.abs(sensorEvent.values[1])), Math.abs(sensorEvent.values[2]));
        if (maxAcceleration >= 20.0f && this.mCrashListener != null) {
            long crashTime = System.nanoTime();
            if (crashTime - this.mLastCrashTimestamp >= 60000000000L) {
                this.mLastCrashTimestamp = crashTime;
                this.mCrashListener.onCrash();
            }
        }
        AccLogger.addValue(maxAcceleration);
    }

    private void compensateGravity(SensorEvent sensorEvent) {
        this.gravity[0] = (0.8d * this.gravity[0]) + (0.19999999999999996d * ((double) sensorEvent.values[0]));
        this.gravity[1] = (0.8d * this.gravity[1]) + (0.19999999999999996d * ((double) sensorEvent.values[1]));
        this.gravity[2] = (0.8d * this.gravity[2]) + (0.19999999999999996d * ((double) sensorEvent.values[2]));
        float[] fArr = sensorEvent.values;
        fArr[0] = (float) (((double) fArr[0]) - this.gravity[0]);
        fArr = sensorEvent.values;
        fArr[1] = (float) (((double) fArr[1]) - this.gravity[1]);
        fArr = sensorEvent.values;
        fArr[2] = (float) (((double) fArr[2]) - this.gravity[2]);
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
