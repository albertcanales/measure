package com.example.albert.measure.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Spinner;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.elements.Vector;

import java.util.ArrayList;
import java.util.List;

import static com.example.albert.measure.ui.main.SectionsPagerAdapter.*;

public class AddVectorActivity extends AddElementActivity {

    private Spinner spinnerA;
    private Spinner spinnerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vector);

        context = this;

        spinnerA = findViewById(R.id.a_spinner);
        spinnerB = findViewById(R.id.b_spinner);
        nameET = findViewById(R.id.name_edit_text);
        nameET.setText(String.format("Distance%d", elements.getVectorList().size()+1));

        spinnerA.setAdapter(getDataAdapter(elements.getPointNames()));
        spinnerB.setAdapter(getDataAdapter(elements.getPointNames()));
    }

    @Override
    List<Spinner> getSpinnerList() {
        List<Spinner> spinnerList = new ArrayList<>();
        spinnerList.add(spinnerA);
        spinnerList.add(spinnerB);
        return spinnerList;
    }

    @Override
    void addElement() {
        Point pointA = elements.getPointList().get(elements.getPointNames().indexOf(spinnerA.getSelectedItem().toString()));
        Point pointB = elements.getPointList().get(elements.getPointNames().indexOf(spinnerB.getSelectedItem().toString()));
        Vector vector = new Vector(name, pointA, pointB);
        elements.getVectorList().add(vector);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("elements", elements);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
