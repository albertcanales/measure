package com.example.albert.measure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.albert.measure.R;

public class DistanceParametersActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup rgDirection;
    private RadioGroup rgOrientation;
    private RadioGroup rgHeightMode;
    private EditText etHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_parameters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Measure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btMeasure = findViewById(R.id.btMeasure);
        rgDirection = findViewById(R.id.rgDirection);
        rgOrientation = findViewById(R.id.rgOrientation);
        rgHeightMode = findViewById(R.id.rgHeightMode);
        etHeight = findViewById(R.id.etHeight);

        btMeasure.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (isEmpty(etHeight)) {
            etHeight.setError("Cannot be empty");
        } else {
            Intent intent = new Intent(this, DistanceActivity.class);
            intent.putExtras(setParameters());
            startActivity(intent);
            finish();
        }
    }

    private Bundle setParameters() {
        Bundle parameters = new Bundle();
        if (rgDirection.getCheckedRadioButtonId() == R.id.rbParallel)
            parameters.putString("DIRECTION", "PARALLEL");
        else
            parameters.putString("DIRECTION", "PERPENDICULAR");

        if (rgOrientation.getCheckedRadioButtonId() == R.id.rbVertical)
            parameters.putString("PLANE", "VERTICAL");
        else
            parameters.putString("PLANE", "HORIZONTAL");

        if (rgHeightMode.getCheckedRadioButtonId() == R.id.rbAutomatic)
            parameters.putDouble("HEIGHT", getUserHeight() - 40);
        else
            parameters.putDouble("HEIGHT", getUserHeight());

        return parameters;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public double getUserHeight() {
        return Double.parseDouble(etHeight.getText().toString());
    }
}
