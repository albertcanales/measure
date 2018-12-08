package com.example.albert.measure.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.albert.measure.R;

public class DistanceParamentersFragment extends Fragment implements View.OnClickListener {

    private View screen;
    private Context context;
    private Button btMeasure;
    private RadioGroup rgDirection;
    private RadioGroup rgOrientation;
    private RadioGroup rgHeightMode;
    private EditText etHeight;


    public enum Directions { PARALLEL, PERPENDICULAR }
    public enum Plane { VERTICAL, HORIZONTAL }
    public enum HeightMode { MANUAL, AUTOMATIC }

    private Directions direction;
    private Plane plane;
    private HeightMode heightMode;
    private float userHeight;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.distance_parameters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        screen = getView();
        context = getActivity();
        btMeasure = screen.findViewById(R.id.btMeasure);
        rgDirection = screen.findViewById(R.id.rgDirection);
        rgOrientation = screen.findViewById(R.id.rgOrientation);
        rgHeightMode = screen.findViewById(R.id.rgHeightMode);
        etHeight = screen.findViewById(R.id.etHeight);

        btMeasure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        getParameters();
    }

    private void getParameters() {
        if (rgDirection.getCheckedRadioButtonId() == R.id.rbParallel)
            direction = Directions.PARALLEL;
        else
            direction = Directions.PERPENDICULAR;

        if (rgOrientation.getCheckedRadioButtonId() == R.id.rbVertical)
            plane = Plane.VERTICAL;
        else
            plane = Plane.HORIZONTAL;

        if (rgHeightMode.getCheckedRadioButtonId() == R.id.rbAutomatic)
            heightMode = HeightMode.AUTOMATIC;
        else
            heightMode = HeightMode.MANUAL;

        userHeight = Float.parseFloat(etHeight.getText().toString());
    }

    public Directions getDirection() {
        return direction;
    }

    public Plane getPlane() {
        return plane;
    }

    public HeightMode getHeightMode() {
        return heightMode;
    }

    public float getUserHeight() {
        return userHeight;
    }
}
