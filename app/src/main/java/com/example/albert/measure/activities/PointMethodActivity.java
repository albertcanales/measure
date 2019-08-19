package com.example.albert.measure.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.albert.measure.CameraSurfaceView;
import com.example.albert.measure.Point;
import com.example.albert.measure.R;
import com.example.albert.measure.sensors.OrientationSensor;


public class PointMethodActivity extends AppCompatActivity implements View.OnClickListener {

    SurfaceHolder mSurfaceHolder;
    CameraManager mCameraManager;
    CameraSurfaceView cameraSurfaceView;
    Context context;

    private OrientationSensor orientationSensor;

    private int color_id = 0;

    private int i = 0;
    double h = 140; // cm
    //Point o = new Point(h);
    Point p, q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SurfaceView mSurfaceView = findViewById(R.id.surface_view);
        cameraSurfaceView = new CameraSurfaceView(mSurfaceView, context);
        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(cameraSurfaceView);
        mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);

        final FloatingActionButton markPointFAB = findViewById(R.id.mark_point);
        markPointFAB.setOnClickListener(this);
        final FloatingActionButton doneFAB = findViewById(R.id.done);
        doneFAB.setOnClickListener(this);
        doneFAB.setSize(FloatingActionButton.SIZE_MINI);
        final ImageButton ib = findViewById(R.id.iv_camera_focus);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = color_id == 0 ? R.color.colorPrimary : R.color.colorAccent;
                ib.setColorFilter(ContextCompat.getColor(context, color));
                markPointFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color)));
                doneFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color)));
                color_id = (color_id + 1) % 2;
            }
        });

        orientationSensor = new OrientationSensor(context);

        p = q = new Point();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraSurfaceView.openCamera(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.point_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item == findViewById(R.id.add_point))
            onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        Log.d("POINTS", "Button pressed");

        double[] orientationValues = {
                Math.PI / 2 - orientationSensor.getPitch(),
                Math.PI / 2 - orientationSensor.getRoll(),
                Math.PI / 2 - orientationSensor.getAzimuth(),
        };
        switch (i) {
            case 0:
                p = new Point(h, orientationValues);
                Log.d("POINTS", p.toString());
                break;
            case 1:
                q = new Point(h, orientationValues);
                Log.d("POINTS", q.toString());
                break;
            case 2:
                q = new Point(p, h, orientationValues);
            default:
                Toast.makeText(context, Double.toString(p.distanceTo(q)), Toast.LENGTH_SHORT).show();
                Log.d("POINTS", p.toString());
                Log.d("POINTS", q.toString());
                Log.d("POINTS", Double.toString(p.distanceTo(q)));
                break;
        }
        i++;
    }

    public void showPopup(MenuItem item) {
        PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.point_type, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        popup.show();
    }
}