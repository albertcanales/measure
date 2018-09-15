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
import com.example.albert.measure.TriangleUtils;
import com.example.albert.measure.sensors.OrientationSensor;


public class DistanceActivity extends AppCompatActivity {

    private static final String TAG = DistanceActivity.class.getSimpleName();
    SurfaceHolder mSurfaceHolder;
    CameraManager mCameraManager;
    CameraSurfaceView cameraSurfaceView;
    Context context;

    private int color_id = 0;
    private OrientationSensor orientationSensor;
    private double distance_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SurfaceView mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
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
        int altura = 175 - 35;
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_calculate:
                double distance = getDistance(distance_s, true);
                Toast.makeText(context, String.valueOf(altura + distance), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_start:
                distance_s = getDistance(altura, false);
                Toast.makeText(context, String.valueOf(distance_s), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private double getDistance(double heigth, boolean up) {
        float pitch = orientationSensor.getPitch();
//                Log.i(TAG, "onOptionsItemSelected: " + String.valueOf(pitch));
        double distanceFromAngle = TriangleUtils.getDistanceFromAngle(pitch, heigth, up);
//        Toast.makeText(context, "onOptionsItemSelected: " + String.valueOf(pitch) + "  --  " + String.valueOf(distanceFromAngle), Toast.LENGTH_SHORT).show();
        return distanceFromAngle;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraSurfaceView.openCamera(this);
    }


}