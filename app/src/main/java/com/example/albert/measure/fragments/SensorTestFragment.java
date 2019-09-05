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

import java.util.Objects;

public class SensorTestFragment extends Fragment
        implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private final Handler myHandler = new Handler();
    private OrientationSensor sensors;
    private TextView tvAzimuth;
    private TextView tvPitch;
    private TextView tvRoll;
    private TextView tvPeriod;

    private int period;

    private final Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            tvAzimuth.setText(String.format("Azimuth: %s", Math.toDegrees(sensors.getAzimuth())));
            tvPitch.setText(String.format("Pitch: %s", Math.toDegrees(sensors.getPitch())));
            tvRoll.setText(String.format("Roll: %s", Math.toDegrees(sensors.getRoll())));
            myHandler.postDelayed(this, period);
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
        View myView = getView();
        sensors = new OrientationSensor(getContext());
        tvAzimuth = Objects.requireNonNull(myView).findViewById(R.id.tvAzimuth);
        tvRoll = myView.findViewById(R.id.tvRoll);
        tvPitch = myView.findViewById(R.id.tvPitch);
        tvPeriod = myView.findViewById(R.id.tvPeriod);

        ((ToggleButton) myView.findViewById(R.id.tbFreeze)).setOnCheckedChangeListener(this);

        SeekBar sbPeriod = myView.findViewById(R.id.sbPeriod);
        sbPeriod.setOnSeekBarChangeListener(this);
        setPeriod(sbPeriod.getProgress());

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
        setPeriod(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void setPeriod(int x) {
        period = x != 0 ? x * 250 : 10;
        tvPeriod.setText(String.format("Period: %d ms", period));
    }
}
