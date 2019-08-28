package com.example.albert.measure.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Spinner;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Angle;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.elements.Vector;

import java.util.ArrayList;
import java.util.List;

public class AddAngleActivity extends AddElementActivity {

    private Spinner spinnerA;
    private Spinner spinnerB;
    private Spinner spinnerC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_angle);

        context = this;

        spinnerA = findViewById(R.id.a_spinner);
        spinnerB = findViewById(R.id.b_spinner);
        spinnerC = findViewById(R.id.c_spinner);
        nameET = findViewById(R.id.name_edit_text);

        spinnerA.setAdapter(getDataAdapter(getPointNames()));
        spinnerB.setAdapter(getDataAdapter(getPointNames()));
        spinnerC.setAdapter(getDataAdapter(getPointNames()));
    }

    @Override
    List<Spinner> getSpinnerList() {
        List<Spinner> spinnerList = new ArrayList<>();
        spinnerList.add(spinnerA);
        spinnerList.add(spinnerB);
        spinnerList.add(spinnerC);
        return spinnerList;
    }

    @Override
    void addElement() {
        Point pointA = points.get(getPointNames().indexOf(spinnerA.getSelectedItem().toString()));
        Point pointB = points.get(getPointNames().indexOf(spinnerB.getSelectedItem().toString()));
        Point pointC = points.get(getPointNames().indexOf(spinnerC.getSelectedItem().toString()));
        Angle angle = new Angle(name, pointA, pointC, pointB);
        angles.add(angle);
        Intent resultIntent = new Intent();
        ArrayList<Parcelable> parcelables = new ArrayList<Parcelable>(angles);
        resultIntent.putParcelableArrayListExtra("angles", parcelables);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
