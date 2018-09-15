package com.example.albert.measure.fragments;

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

import com.example.albert.measure.R;
import com.example.albert.measure.sensors.OrientationSensor;

public class SensorTestFragment extends Fragment
        implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    final Handler myHandler = new Handler();
    View myView;
    OrientationSensor sensors;
    private TextView tvAzimut;
    private TextView tvPitch;
    private TextView tvRoll;
    private TextView tvSpeed;

    private int speed;
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            tvAzimut.setText(String.format("Azimut: %s", sensors.getAzimut()));
            tvPitch.setText(String.format("Pitch: %s", sensors.getPitch()));
            tvRoll.setText(String.format("Roll: %s", sensors.getRoll()));
            myHandler.postDelayed(this, speed);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sensor_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myView = getView();
        sensors = new OrientationSensor(getContext());
        tvAzimut = myView.findViewById(R.id.tvAzimut);
        tvRoll = myView.findViewById(R.id.tvRoll);
        tvPitch = myView.findViewById(R.id.tvPitch);
        tvSpeed = myView.findViewById(R.id.tvSpeed);

        ((ToggleButton) myView.findViewById(R.id.tbFreeze)).setOnCheckedChangeListener(this);

        SeekBar sbSpeed = myView.findViewById(R.id.sbSpeed);
        sbSpeed.setOnSeekBarChangeListener(this);
        setSpeed(sbSpeed.getProgress());

        startSensor();
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) stopSensor();
        else startSensor();
    }

    private void startSensor() {
        sensors.register();
        myHandler.post(myRunnable);
    }

    private void stopSensor() {
        sensors.unregister();
        myHandler.removeCallbacks(myRunnable);
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
        speed = x != 0 ? x * 250 : 10;
        tvSpeed.setText(String.format("Speed: %d ms", speed));
    }
}
