package com.example.albert.measure.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;

public class OrientationSensor extends MySensorEvent implements SensorEventListener {

    private double azimuth, pitch, roll;

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

    private void update(float[] vectors) {
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);

        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix,
                SensorManager.AXIS_X, SensorManager.AXIS_Z, adjustedRotationMatrix);

        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);

        azimuth = orientation[0];
        pitch = Math.PI/2 - orientation[1];
        roll = orientation[2];
    }

    public double getAzimuth() {
        return azimuth;
    }

    public double getPitch() {
        return pitch;
    }

    public double getRoll() {
        return roll;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrientationSensor{" +
                "azimuth=" + azimuth +
                ", pitch=" + pitch +
                ", roll=" + roll +
                '}';
    }
}
