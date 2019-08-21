package com.example.albert.measure.activities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import com.example.albert.measure.CameraSurfaceView;
import com.example.albert.measure.Point;
import com.example.albert.measure.R;
import com.example.albert.measure.sensors.OrientationSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PointMethodActivity extends AppCompatActivity implements View.OnClickListener {

    private CameraSurfaceView cameraSurfaceView;
    private Context context;
    private FloatingActionButton markPointFAB;
    private FloatingActionButton cancelPointFAB;

    private OrientationSensor orientationSensor;

    private List<Point> pointList = new ArrayList<>();
    private Point tempBasePoint = new Point();
    private int pointType = -1;     // -1 = None, 0 = Base, 1 = NonBase

    private int color_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        context = this;

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SurfaceView mSurfaceView = findViewById(R.id.surface_view);
        cameraSurfaceView = new CameraSurfaceView(mSurfaceView, context);
        SurfaceHolder mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(cameraSurfaceView);
        //CameraManager mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);

        markPointFAB = findViewById(R.id.mark_point);
        markPointFAB.setOnClickListener(this);
        cancelPointFAB = findViewById(R.id.cancel_point);
        cancelPointFAB.setOnClickListener(this);
        cancelPointFAB.setSize(FloatingActionButton.SIZE_MINI);
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
                cancelPointFAB.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(color)));
                color_id = (color_id + 1) % 2;
            }
        });

        orientationSensor = new OrientationSensor(context);

        setPointType(pointType);
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
        if(findViewById(item.getItemId()) != findViewById(R.id.help))
            onBackPressed();
        return true;
    }

    public void showPopup(MenuItem item) {
        PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.point_type, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.based) {
                    setPointType(0);    // Base point
                } else {
                    setPointType(1);    // Non-base point
                }
                return true;
            }
        });
        popup.show();
    }

    @Override
    public void onClick(View view) {
        Log.d("POINTS", "Button pressed");
        if(view == findViewById(R.id.cancel_point)) {
            tempBasePoint = new Point();
            setPointType(-1);
        }
        else //noinspection StatementWithEmptyBody
            if(view == findViewById(R.id.mark_point)) {
            measurePoint();
            Log.d("POINTS", pointList.toString());
        }
        else {  // DoneFAB pressed
            // TODO Finish measurement
        }
    }

    private void setPointType(int pointType) {
        this.pointType = pointType;
        if(pointType == -1) {
            markPointFAB.hide();
            cancelPointFAB.hide();
        }
        else {
            markPointFAB.show();
            cancelPointFAB.show();
        }
    }

    private void measurePoint() {
        Pair<Double, Double> angles = new Pair<>(Math.PI /  2 - orientationSensor.getPitch(),
                Math.PI / 2 - orientationSensor.getAzimuth());
        // TODO Measure it. Could be even treated it as a distance
        double h = 120;
        if(pointType == 0) {
            pointList.add(new Point(h, angles));
            setPointType(-1);
        }
        else {
            if (tempBasePoint.isDefault())
                tempBasePoint = new Point(h, angles);
            else {
                pointList.add(new Point(tempBasePoint, h, angles));
                tempBasePoint = new Point();
                setPointType(-1);
            }
        }
    }
}