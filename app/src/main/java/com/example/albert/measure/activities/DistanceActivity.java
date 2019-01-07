package com.example.albert.measure.activities;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.albert.measure.CameraSurfaceView;
import com.example.albert.measure.R;
import com.example.albert.measure.DistanceUtils;
import com.example.albert.measure.sensors.OrientationSensor;


public class DistanceActivity extends AppCompatActivity {

    SurfaceHolder mSurfaceHolder;
    CameraManager mCameraManager;
    CameraSurfaceView cameraSurfaceView;
    Context context;

    private DistanceUtils distance;
    private OrientationSensor orientationSensor;

    private int color_id = 0;
    private double orientationAtPoints[][] = new double[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SurfaceView mSurfaceView = findViewById(R.id.surface_view);
        cameraSurfaceView = new CameraSurfaceView(mSurfaceView, context);
        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(cameraSurfaceView);
        mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);

        final ImageButton ib = findViewById(R.id.iv_camera_focus);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = color_id == 0 ? R.color.colorPrimary : R.color.colorAccent;
                ib.setColorFilter(ContextCompat.getColor(context, color));
                color_id = (color_id + 1) % 2;
            }
        });
        orientationSensor = new OrientationSensor(context);

    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraSurfaceView.openCamera(this);
        orientationSensor.register();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cameraSurfaceView.onStop();
        orientationSensor.unregister();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.mark_point:
                if(!item.getTitle().equals("Point C")) {
                    if (item.getTitle().equals("Point A")) {
                        setOrientationValues(0);
                        item.setTitle("Point B");
                        if (true) {       // If distance is simple, upcoming feature
                            setOrientationValues(1);
                            item.setTitle("Point C");
                        }
                    }
                    else {      // Title equals "Point B"
                        setOrientationValues(1);
                        item.setTitle("Point C");
                    }
                }
                else {
                    setOrientationValues(2);
                    distance = new DistanceUtils();
                    double result = distance.getDistance(orientationAtPoints);
                    if (result != -1)
                        Toast.makeText(context, String.valueOf(result), Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Still developing this feature", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setOrientationValues(int i) {
        double orientationValues[] = {
                orientationSensor.getPitch(),
                orientationSensor.getRoll(),
                orientationSensor.getAzimut(),
        };
        System.arraycopy(orientationValues, 0, orientationAtPoints[i], 0, 3);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraSurfaceView.openCamera(this);
    }
}