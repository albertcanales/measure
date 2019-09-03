package com.example.albert.measure.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.measure.CameraSurfaceView;
import com.example.albert.measure.DistanceUtils;
import com.example.albert.measure.R;
import com.example.albert.measure.fragments.ResultsDialog;
import com.example.albert.measure.sensors.OrientationSensor;

import java.util.Objects;


public class DistanceActivity extends AppCompatActivity implements View.OnClickListener {

    private static double result;
    private CameraSurfaceView cameraSurfaceView;
    private Context context;
    private TextView markPointButton;
    private OrientationSensor orientationSensor;
    private String direction, plane;
    private double height;
    private int color_id = 0;
    private double[][] orientationAtPoints = new double[3][3];
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        context = this;

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SurfaceView mSurfaceView = findViewById(R.id.surface_view);
        cameraSurfaceView = new CameraSurfaceView(mSurfaceView, context);
        SurfaceHolder mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(cameraSurfaceView);
        //CameraManager mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);

        markPointButton = findViewById(R.id.mark_point);
        markPointButton.setOnClickListener(this);

        final Drawable markPointCircle = getResources().getDrawable(R.drawable.circle_background);
        final ImageButton ib = findViewById(R.id.iv_camera_focus);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color = color_id == 0 ? R.color.colorPrimary : R.color.colorAccent;
                ib.setColorFilter(ContextCompat.getColor(context, color));
                markPointCircle.mutate().setColorFilter(ContextCompat.getColor(markPointButton.getContext(), color), PorterDuff.Mode.SRC_IN);
                markPointButton.setBackground(markPointCircle);
                color_id = (color_id + 1) % 2;
            }
        });

        orientationSensor = new OrientationSensor(context);

        Bundle parameters = getIntent().getExtras();
        direction = Objects.requireNonNull(parameters).getString("DIRECTION");
        plane = parameters.getString("PLANE");
        height = parameters.getDouble("HEIGHT");
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
        getMenuInflater().inflate(R.menu.distance_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        String[] points = {"A", "B", "C"};
        setOrientationValues(i);
        if (i == 2) {
            DistanceUtils distance = new DistanceUtils();
            result = distance.getDistance(direction, plane, height, orientationAtPoints);
            orientationSensor.unregister();
            Log.d("RESULT_INPUT", toString());
            showResult(result);
        } else {
            i++;
            markPointButton.setText(points[i]);
            if (i == 1 && isDistanceSimple()) {
                setOrientationValues(i);
                i++;
            }
        }
    }

    private void setOrientationValues(int i) {
        double[] orientationValues = {
                Math.PI / 2 - orientationSensor.getPitch(),
                Math.PI / 2 - orientationSensor.getRoll(),
                Math.PI / 2 - orientationSensor.getAzimuth(),
        };
        System.arraycopy(orientationValues, 0, orientationAtPoints[i], 0, 3);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraSurfaceView.openCamera(this);
    }

    public double getResult() {
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "DistanceActivity{" +
                "direction='" + direction + '\'' +
                ", plane='" + plane + '\'' +
                ", height=" + height +
                ", A=" + orientationAtPoints[0][0] +
                ", B=" + orientationAtPoints[1][0] +
                ", C=" + orientationAtPoints[2][0] +
                ", result=" + result +
                '}';
    }

    @SuppressWarnings("SameReturnValue")
    private boolean isDistanceSimple() {
        return true;
    } // Upcoming feature

    private void showResult(double result) {
        if (result != -1) {
            Log.d("SENSOR_VALUES", toString());
            ResultsDialog dialog = new ResultsDialog();
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), "Result");
        } else
            Toast.makeText(context, "Still developing this feature", Toast.LENGTH_SHORT).show();
    }
}