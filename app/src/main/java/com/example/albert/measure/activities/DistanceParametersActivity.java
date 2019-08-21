package com.example.albert.measure.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.albert.measure.parameterTypes.Direction;
import com.example.albert.measure.parameterTypes.HeightMode;
import com.example.albert.measure.parameterTypes.Offset;
import com.example.albert.measure.parameterTypes.Plane;
import com.example.albert.measure.R;
import com.example.albert.measure.fragments.HeightDialog;
import com.example.albert.measure.fragments.TwoOptionParameterFragment;

import java.util.Objects;

public class DistanceParametersActivity extends AppCompatActivity implements TwoOptionParameterFragment.OnDataPass {

    private final Bundle bundle = new Bundle();

    private int i = 0;
    private final Fragment[] parametersList = {new Plane(), new Direction(), new Offset(), new HeightMode()};
    private final String[] parametersNames = {"PLANE", "DIRECTION", "OFFSET", "HEIGHT_MODE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_parameters);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Measure");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame, parametersList[0]);
        transaction.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onDataPass(String data) {
        Log.d("PARAMETER_VALUE", data);
        bundle.putString(parametersNames[i], data);
        i++;
        if (i < parametersList.length) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, parametersList[i]);
            transaction.commit();
        }
        else {
                HeightDialog dialog = new HeightDialog();
                dialog.setCancelable(false);
                dialog.show(getSupportFragmentManager(), "Height");
                dialog.passParameters(bundle);
        }
    }
}
