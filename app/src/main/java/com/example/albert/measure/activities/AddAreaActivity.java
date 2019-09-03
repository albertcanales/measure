package com.example.albert.measure.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Area;
import com.example.albert.measure.elements.Point;

import java.util.ArrayList;
import java.util.List;

public class AddAreaActivity extends AddElementActivity {

    int areaType;
    TableRow tableRowSpinnerD;
    private Spinner spinnerA;
    private Spinner spinnerB;
    private Spinner spinnerC;
    private Spinner spinnerD;

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
        spinnerD = findViewById(R.id.d_spinner);
        tableRowSpinnerD = findViewById(R.id.row_spinner_d);
        nameET = findViewById(R.id.name_edit_text);

        spinnerA.setAdapter(getDataAdapter(getPointNames()));
        spinnerB.setAdapter(getDataAdapter(getPointNames()));
        spinnerC.setAdapter(getDataAdapter(getPointNames()));
        spinnerD.setAdapter(getDataAdapter(getPointNames()));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 2) {
                    typeSpinner.setSelection(1);
                    Toast.makeText(context, "Option not available by now", Toast.LENGTH_SHORT).show();
                }
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
        if (areaType == Area.GENERAL_QUADRILATERAL)
            spinnerList.add(spinnerD);
        return spinnerList;
    }

    @Override
    void addElement() {
        Area area;
        Point pointA = pointList.get(getPointNames().indexOf(spinnerA.getSelectedItem().toString()));
        Point pointB = pointList.get(getPointNames().indexOf(spinnerB.getSelectedItem().toString()));
        Point pointC = pointList.get(getPointNames().indexOf(spinnerC.getSelectedItem().toString()));
        if (areaType == Area.GENERAL_QUADRILATERAL) {
            Point pointD = pointList.get(getPointNames().indexOf(spinnerD.getSelectedItem().toString()));
            area = new Area(name, pointA, pointB, pointC, pointD);
        } else {
            area = new Area(name, pointA, pointB, pointC, areaType);
        }
        // TODO Test if they are on a same plane
        areaList.add(area);
        Intent resultIntent = new Intent();
        ArrayList<Parcelable> parcelables = new ArrayList<Parcelable>(areaList);
        resultIntent.putParcelableArrayListExtra("areas", parcelables);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void setAreaType(int areaType) {
        // TODO + Select Image
        this.areaType = areaType;
        if (areaType == Area.TRIANGLE) {
            tableRowSpinnerD.setVisibility(View.GONE);
        } else if (areaType == Area.PARALLELOGRAM) {
            tableRowSpinnerD.setVisibility(View.GONE);
        } else {
            tableRowSpinnerD.setVisibility(View.VISIBLE);
        }
    }
}
