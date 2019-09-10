package com.example.albert.measure.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Angle;
import com.example.albert.measure.elements.Point;

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
        nameET.setText(String.format("Angle%d", elements.getAngleList().size()+1));

        spinnerA.setAdapter(getDataAdapter(elements.getPointNames()));
        spinnerB.setAdapter(getDataAdapter(elements.getPointNames()));
        spinnerC.setAdapter(getDataAdapter(elements.getPointNames()));
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
        Point pointA = elements.getPointList().get(elements.getPointNames().indexOf(spinnerA.getSelectedItem().toString()));
        Point pointB = elements.getPointList().get(elements.getPointNames().indexOf(spinnerB.getSelectedItem().toString()));
        Point pointC = elements.getPointList().get(elements.getPointNames().indexOf(spinnerC.getSelectedItem().toString()));
        Angle angle = new Angle(name, pointB, pointC, pointA);
        elements.getAngleList().add(angle);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("elements", elements);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
