package com.example.albert.measure.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.albert.measure.R;
import com.example.albert.measure.activities.MainActivity;

/**
 * Created by Albert Canales on 15/07/18.
 */

public class SimpleLineFragment extends Fragment implements View.OnClickListener {

    View myView;

    MainActivity main = new MainActivity();

    Button button;
    Spinner unitSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.simple_line, container, false);


        String[] units = {"m", "dm", "cm", "mm"};
        unitSpinner = myView.findViewById(R.id.unitSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        unitSpinner.setAdapter(adapter);

        button = myView.findViewById(R.id.takePicture);
        button.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == button.getId()) {
            Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();
        }

    }

}