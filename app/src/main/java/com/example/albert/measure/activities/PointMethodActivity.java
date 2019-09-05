package com.example.albert.measure.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.measure.CameraSurfaceView;
import com.example.albert.measure.R;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.sensors.OrientationSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PointMethodActivity extends AppCompatActivity implements View.OnClickListener {

    private CameraSurfaceView cameraSurfaceView;
    private Context context;
    private FloatingActionButton markPointFAB;
    private FloatingActionButton cancelPointFAB;
    private TextView pointNumberTV;

    private OrientationSensor orientationSensor;

    private List<Point> pointList;
    private Point tempBasePoint;
    private boolean addTempBasePoint;
    private int pointType;     // -1 = None, 0 = Base, 1 = NonBase
    private int numPoints;

    private int color_id;
    private boolean popupActive;
    private double h = 120;

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

        pointNumberTV = findViewById(R.id.pointNumber);
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
                pointNumberTV.setTextColor(getResources().getColor(color));
                color_id = (color_id + 1) % 2;
            }
        });

        orientationSensor = new OrientationSensor(context);

        initVariables();

        setPointType(pointType);
    }

    private void initVariables() {
        pointList = new ArrayList<>();
        pointList.add(new Point("Origin", 0, 0, 0));
        pointList.add(new Point("Device", 0, 0, h));
        tempBasePoint = new Point();
        addTempBasePoint = false;
        pointType = -1;
        numPoints = 0;
        color_id = 0;
        popupActive = false;
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
    protected void onRestart() {
        super.onRestart();
        initVariables();
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
        if (findViewById(item.getItemId()) != findViewById(R.id.help))
            onBackPressed();
        return true;
    }

    public void showPopup(MenuItem item) {
        if (!popupActive) {
            popupActive = true;
            PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.point_type, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if (pointType == -1) {
                        if (item.getItemId() == R.id.based) setPointType(0);    // Base point
                        else setPointType(1);    // Non-base point
                    } else mustFinishMeasurement();
                    popupActive = false;
                    return true;
                }
            });
            popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu menu) {
                    popupActive = false;
                }
            });
            popup.show();
        }
    }

    @Override
    public void onClick(View view) {
        Log.d("POINTS", "Button pressed");
        if (view == findViewById(R.id.cancel_point)) {
            tempBasePoint = new Point();
            setPointType(-1);
        } else if (view == findViewById(R.id.mark_point)) {
            measurePoint();
            Log.d("POINTS", pointList.toString());
        } else {  // DoneFAB pressed
            if (pointType == -1) {
                Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                ArrayList<Parcelable> parcelables = new ArrayList<Parcelable>(pointList);
                intent.putParcelableArrayListExtra("pointList", parcelables);
                startActivity(intent);
            } else {
                mustFinishMeasurement();
            }
        }
    }

    private void setPointType(int pointType) {
        this.pointType = pointType;
        if (pointType == -1) {
            markPointFAB.hide();
            cancelPointFAB.hide();
            pointNumberTV.animate().alpha(0.0f).setDuration(50);
        } else {
            markPointFAB.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_vertical_align_bottom_white));
            markPointFAB.show();
            cancelPointFAB.show();
            pointNumberTV.setText(String.valueOf(numPoints + 1));
            pointNumberTV.animate().alpha(1.0f).setDuration(50);
        }
    }

    private void measurePoint() {
        String name = "Point".concat(Integer.toString(numPoints + 1));
        Pair<Double, Double> angles = new Pair<>(orientationSensor.getPitch(), orientationSensor.getAzimuth());
        // TODO Measure it. Could be even treated it as a distance
        if (pointType == 0) {
            pointList.add(new Point(name, h, angles));
            setPointType(-1);
            numPoints++;
        } else {
            if (tempBasePoint.isDefault()) {
                addTempBasePoint = false;
                tempBasePoint = new Point(name.concat("Base"), h, angles);
                Log.d("TEMP_POINTS", tempBasePoint.toString());
                List<Point> closePoints = tempBasePoint.CloseBasePoints(pointList, Point.DEFAULT_PROXIMITY_DISTANCE);
                Log.d("CLOSE_POINTS", closePoints.toString());
                if (!closePoints.isEmpty()) {
                    chooseClosePoint(closePoints);
                } else addTempBasePoint = true;
                markPointFAB.hide();
                markPointFAB.setImageResource(R.drawable.ic_vertical_align_top_white);
                markPointFAB.show();
            } else {
                if (addTempBasePoint) pointList.add(tempBasePoint);
                pointList.add(new Point(name, tempBasePoint, h, angles));
                tempBasePoint = new Point();
                setPointType(-1);
                addTempBasePoint = false;
                numPoints++;
            }
        }
    }

    private void chooseClosePoint(final List<Point> points) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Same point as...");
        List<String> pointNames = new ArrayList<>();
        pointNames.add("None");
        for (Point p : points) pointNames.add(p.getName());
        builder.setItems(pointNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0)
                    addTempBasePoint = true;
                else
                    tempBasePoint = points.get(which - 1);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void mustFinishMeasurement() {
        Toast.makeText(context, "Finish the actual point first!", Toast.LENGTH_LONG).show();
    }
}