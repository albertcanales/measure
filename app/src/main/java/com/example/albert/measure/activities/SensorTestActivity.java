package com.example.albert.measure.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.albert.measure.R;
import com.example.albert.measure.sensors.OrientationSensor;

import java.util.Objects;


public class SensorTestActivity extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        sensors = new OrientationSensor(this);

        tvAzimuth = findViewById(R.id.tvAzimuth);
        tvRoll = findViewById(R.id.tvRoll);
        tvPitch = findViewById(R.id.tvPitch);
        tvPeriod = findViewById(R.id.tvPeriod);

        ((ToggleButton) findViewById(R.id.tbFreeze)).setOnCheckedChangeListener(this);

        SeekBar sbPeriod = findViewById(R.id.sbPeriod);
        sbPeriod.setOnSeekBarChangeListener(this);
        setPeriod(sbPeriod.getProgress());

        startSensor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
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
