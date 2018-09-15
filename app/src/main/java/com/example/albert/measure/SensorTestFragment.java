package com.example.albert.measure;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.TimerTask;

public class SensorTestFragment extends Fragment
        implements SensorEventListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    View myView;

    SensorsActivity sensors;

    final Handler myHandler = new Handler();

    private TextView tvAzimut;
    private TextView tvPitch;
    private TextView tvRoll;
    private TextView tvSpeed;

    private int speed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sensors = new SensorsActivity(getContext());

        myView = inflater.inflate(R.layout.sensor_test, container, false);
        tvAzimut = myView.findViewById(R.id.tvAzimut);
        tvRoll = myView.findViewById(R.id.tvRoll);
        tvPitch = myView.findViewById(R.id.tvPitch);
        tvSpeed = myView.findViewById(R.id.tvSpeed);
        ToggleButton tbFreeze = myView.findViewById(R.id.tbFreeze);
        SeekBar sbSpeed = myView.findViewById(R.id.sbSpeed);

        tbFreeze.setOnCheckedChangeListener(this);
        sbSpeed.setOnSeekBarChangeListener(this);

        setSpeed(sbSpeed.getProgress());

        sensors.register();
        myHandler.post(myRunnable);

        return myView;
    }

    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            tvAzimut.setText("Azimut: " + sensors.azimut);
            tvPitch.setText("Pitch: " + sensors.pitch);
            tvRoll.setText("Roll: " + sensors.roll);
            myHandler.postDelayed(this, speed);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        sensors.onSensorChanged(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        sensors.onAccuracyChanged(sensor, i);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked) {
            sensors.unregister();
            myHandler.removeCallbacks(myRunnable);
        }
        else {
            sensors.register();
            myHandler.post(myRunnable);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        setSpeed(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void setSpeed(int x) {
        speed = ((x != 0) ? 250*x : 10);
        tvSpeed.setText("Speed: " + speed + " ms");
    }
}
