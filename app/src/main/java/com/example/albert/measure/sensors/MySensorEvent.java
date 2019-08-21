package com.example.albert.measure.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Objects;

public abstract class MySensorEvent implements SensorEventListener {

    private final SensorManager mSensorManager;
    private final Sensor mySensor;

    MySensorEvent(Context context, @SuppressWarnings("SameParameterValue") int sensorType) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mySensor = Objects.requireNonNull(mSensorManager).getDefaultSensor(sensorType);
    }

    public void register() {
        mSensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregister() {
        mSensorManager.unregisterListener(this, mySensor);
    }

}
