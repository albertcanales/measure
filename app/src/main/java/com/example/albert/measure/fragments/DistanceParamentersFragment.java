package com.example.albert.measure.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.example.albert.measure.activities.DistanceActivity;

public class DistanceParamentersFragment extends Fragment implements View.OnClickListener {

    Context context = getActivity();
    private RadioGroup rgDirection;
    private RadioGroup rgOrientation;
    private RadioGroup rgHeightMode;
    private EditText etHeight;

    private String direction, plane, heightMode;
    private float userHeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.distance_parameters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View screen = getView();

        Button btMeasure = screen.findViewById(R.id.btMeasure);
        rgDirection = screen.findViewById(R.id.rgDirection);
        rgOrientation = screen.findViewById(R.id.rgOrientation);
        rgHeightMode = screen.findViewById(R.id.rgHeightMode);
        etHeight = screen.findViewById(R.id.etHeight);

        btMeasure.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (isEmpty(etHeight)) {
            etHeight.setError("Cannot be empty");
        } else {
            setParameters();
            startActivity(new Intent(getActivity(), DistanceActivity.class));
        }
    }

    private void setParameters() {
        if (rgDirection.getCheckedRadioButtonId() == R.id.rbParallel)
            direction = "PARALLEL";
        else
            direction = "PERPENDICULAR";

        if (rgOrientation.getCheckedRadioButtonId() == R.id.rbVertical)
            plane = "VERTICAL";
        else
            plane = "HORIZONTAL";

        if (rgHeightMode.getCheckedRadioButtonId() == R.id.rbAutomatic)
            heightMode = "AUTOMATIC";
        else
            heightMode = "MANUAL";

        userHeight = Float.parseFloat(etHeight.getText().toString());
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public String getDirection() {
        return direction;
    }

    public String getPlane() {
        return plane;
    }

    public String getHeightMode() {
        return heightMode;
    }

    public float getUserHeight() {
        return userHeight;
    }
}
