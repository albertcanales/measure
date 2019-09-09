package com.example.albert.measure.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Area;
import com.example.albert.measure.elements.Point;

import java.util.ArrayList;
import java.util.List;

import static com.example.albert.measure.ui.main.SectionsPagerAdapter.*;

public class AddAreaActivity extends AddElementActivity {

    int areaType;
    private Spinner spinnerA;
    private Spinner spinnerB;
    private Spinner spinnerC;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area);

        context = this;

        final Spinner typeSpinner = findViewById(R.id.type_spinner);
        typeSpinner.setSelection(0);
        spinnerA = findViewById(R.id.a_spinner);
        spinnerB = findViewById(R.id.b_spinner);
        spinnerC = findViewById(R.id.c_spinner);
        imageView = findViewById(R.id.image);
        nameET = findViewById(R.id.name_edit_text);
        nameET.setText(String.format("Area%d", areaList.size()+1));

        spinnerA.setAdapter(getDataAdapter(getPointNames()));
        spinnerB.setAdapter(getDataAdapter(getPointNames()));
        spinnerC.setAdapter(getDataAdapter(getPointNames()));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setAreaType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
        Point pointA = pointList.get(getPointNames().indexOf(spinnerA.getSelectedItem().toString()));
        Point pointB = pointList.get(getPointNames().indexOf(spinnerB.getSelectedItem().toString()));
        Point pointC = pointList.get(getPointNames().indexOf(spinnerC.getSelectedItem().toString()));

        areaList.add(new Area(name, pointA, pointB, pointC, areaType));
        Intent resultIntent = new Intent();
        ArrayList<Parcelable> parcelables = new ArrayList<Parcelable>(areaList);
        resultIntent.putParcelableArrayListExtra(getString(TAB_TITLES[AREA_TAB]), parcelables);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void setAreaType(int areaType) {
        this.areaType = areaType;
        if (areaType == Area.TYPE_TRIANGLE)
            imageView.setImageDrawable(getDrawable(R.drawable.areatriangle));
        else
            imageView.setImageDrawable(getDrawable(R.drawable.areaparalelogram));
    }
}
