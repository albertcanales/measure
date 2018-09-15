package com.example.albert.measure;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Toast;

public class SensorsActivity extends Activity implements SensorEventListener {

    Context context;

    private SensorManager mSensorManager;
    private Sensor mRotationSensor;

    public static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_FASTEST;
    private static final int FROM_RADS_TO_DEGS = -57;

    public float azimut, pitch, roll;

    public SensorsActivity (Context context) {
        this.context = context;
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public void checkSensors() {
        try {
            register();
        } catch (Exception e) {
            Toast.makeText(this, "Hardware compatibility issue", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mRotationSensor) {
            if (event.values.length > 4) {
                float[] truncatedRotationVector = new float[4];
                System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
                update(truncatedRotationVector);
            } else {
                update(event.values);
            }
        }
    }

    private void update(float[] vectors) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisZ = SensorManager.AXIS_Z;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisZ, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        azimut = orientation[0] * FROM_RADS_TO_DEGS;
        pitch = orientation[1] * FROM_RADS_TO_DEGS;
        roll = orientation[2] * FROM_RADS_TO_DEGS;
    }

    public void register() {
        mSensorManager.registerListener(this, mRotationSensor, SENSOR_DELAY);
    }

    public void unregister() {
        mSensorManager.unregisterListener(this, mRotationSensor);
    }
}
