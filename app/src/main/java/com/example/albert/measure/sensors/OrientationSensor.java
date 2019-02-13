package com.example.albert.measure.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationSensor extends MySensorEvent implements SensorEventListener {

    private float azimuth, pitch, roll;

    public OrientationSensor(Context context) {
        super(context, Sensor.TYPE_ROTATION_VECTOR);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values.length > 4) {
            float[] truncatedRotationVector = new float[4];
            System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
            update(truncatedRotationVector);
        } else {
            update(event.values);
        }
    }

    @Override
    public void update(float[] vectors) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);

        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix,
                SensorManager.AXIS_X, SensorManager.AXIS_Z, adjustedRotationMatrix);

        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);

        int fromRadsToDegs = -57;
        azimuth = orientation[0] * fromRadsToDegs;
        pitch = orientation[1] * fromRadsToDegs;
        roll = orientation[2] * fromRadsToDegs;
    }

    public float getAzimuth() {
        return azimuth;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }

    @Override
    public String toString() {
        return "OrientationSensor{" +
                "azimuth=" + azimuth +
                ", pitch=" + pitch +
                ", roll=" + roll +
                '}';
    }
}
