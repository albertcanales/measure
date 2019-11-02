package com.example.albert.measure.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Area;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.elements.Volume;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddVolumeActivity extends AddElementActivity {

    private int volumeType = Volume.TYPE_PYRAMID;
    private Spinner spinnerA;
    private Spinner spinnerB;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_volume);

        context = this;

        final Spinner typeSpinner = findViewById(R.id.type_spinner);
        typeSpinner.setSelection(0);
        spinnerA = findViewById(R.id.a_spinner);
        spinnerB = findViewById(R.id.b_spinner);
        imageView = findViewById(R.id.image);
        nameET = findViewById(R.id.name_edit_text);
        nameET.setText(String.format(Locale.getDefault(), "Volume%d", elements.getVolumeList().size() + 1));

        spinnerA.setAdapter(getDataAdapter(elements.getAreaNames()));
        spinnerA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setVolumeType(volumeType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerB.setAdapter(getDataAdapter(elements.getPointNames()));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setVolumeType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setVolumeType(volumeType);
    }

    @Override
    List<Spinner> getSpinnerList() {
        return new ArrayList<>();   // Not necessary as there is no possible repetition
    }

    @Override
    void addElement() {
        Area base = elements.getAreaList().get(elements.getAreaNames().indexOf(spinnerA.getSelectedItem().toString()));
        Point pointHeight = elements.getPointList().get(elements.getPointNames().indexOf(spinnerB.getSelectedItem().toString()));
        elements.getVolumeList().add(new Volume(name, base, pointHeight, volumeType));
        Intent resultIntent = new Intent();
        resultIntent.putExtra("elements", elements);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void setVolumeType(int volumeType) {
        this.volumeType = volumeType;
        int areaType = elements.getAreaList().get(elements.getAreaNames().indexOf(spinnerA.getSelectedItem().toString())).getType();
        if (volumeType == Volume.TYPE_PYRAMID) {
            if (areaType == Area.TYPE_TRIANGLE)
                imageView.setImageDrawable(getDrawable(R.drawable.volume_triangle_pyramid));
            else
                imageView.setImageDrawable(getDrawable(R.drawable.volumeparalelogrampyramid));
        } else {
            if (areaType == Area.TYPE_TRIANGLE)
                imageView.setImageDrawable(getDrawable(R.drawable.volumetriangleprism));
            else
                imageView.setImageDrawable(getDrawable(R.drawable.volumeparalelogramprism));
        }
    }
}
