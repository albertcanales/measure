package com.example.albert.measure.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class MySensorEvent implements SensorEventListener {

    public Context context;
    public SensorManager mSensorManager;
    public Sensor mySensor;

    public MySensorEvent(Context context, int sensorType) {
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mySensor = mSensorManager.getDefaultSensor(sensorType);
    }

    public abstract void update(float[] vectors);

    public void register() {
        mSensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregister() {
        mSensorManager.unregisterListener(this, mySensor);
    }

}
