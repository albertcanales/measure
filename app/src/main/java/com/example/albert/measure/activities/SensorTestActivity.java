package com.example.albert.measure.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.albert.measure.R;
import com.example.albert.measure.sensors.OrientationSensor;

import java.util.Locale;
import java.util.Objects;


public class SensorTestActivity extends AppCompatActivity {

    private final Handler myHandler = new Handler();
    private OrientationSensor sensors;
    private TextView tvAzimuth;
    private TextView tvPitch;
    private TextView tvRoll;
    private ProgressBar progressPitch;
    private ProgressBar progressRoll;
    private ProgressBar progressAzimuth;
    private ProgressBar antiProgressPitch;
    private ProgressBar antiProgressRoll;
    private ProgressBar antiProgressAzimuth;

    private final Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            setValue(tvPitch, progressPitch, antiProgressPitch, sensors.getPitch());
            setValue(tvRoll, progressRoll, antiProgressRoll, sensors.getRoll());
            setValue(tvAzimuth, progressAzimuth, antiProgressAzimuth, sensors.getAzimuth());
            myHandler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        sensors = new OrientationSensor(this);

        tvPitch = findViewById(R.id.pitchValue);
        tvRoll = findViewById(R.id.rollValue);
        tvAzimuth = findViewById(R.id.azimuthValue);
        progressPitch = findViewById(R.id.pitchProgress);
        progressRoll = findViewById(R.id.rollProgress);
        progressAzimuth = findViewById(R.id.azimuthProgress);

        antiProgressPitch = findViewById(R.id.pitchProgress2);
        antiProgressRoll = findViewById(R.id.rollProgress2);
        antiProgressAzimuth = findViewById(R.id.azimuthProgress2);

        sensors.register();
        myHandler.post(myRunnable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void setValue(TextView tv, ProgressBar pb, ProgressBar pb2, double x) {
        int finalX = (int) Math.toDegrees(x);
        tv.setText(String.format(Locale.getDefault(), "%d°", finalX));
        if (finalX >= 0) pb.setProgress(finalX);
        else pb.setProgress(0);
        if (finalX <= 0) pb2.setProgress(360 + finalX);
        else pb2.setProgress(360);
    }
}
