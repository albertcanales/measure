package com.example.albert.measure.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

public class AddVolumeActivity extends AddElementActivity {

    int volumeType;
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
        nameET = findViewById(R.id.name_edit_text);
        imageView = findViewById(R.id.image);

        spinnerA.setAdapter(getDataAdapter(getAreaNames()));
        spinnerB.setAdapter(getDataAdapter(getPointNames()));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setVolumeType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    List<Spinner> getSpinnerList() {
        return new ArrayList<>();   // Not necessary there is no possible repeating elementType
    }

    @Override
    void addElement() {
        Area base = areaList.get(getAreaNames().indexOf(spinnerA.getSelectedItem().toString()));
        Point pointHeight = pointList.get(getPointNames().indexOf(spinnerB.getSelectedItem().toString()));
        volumeList.add(new Volume(name, base, pointHeight, volumeType));
        Intent resultIntent = new Intent();
        ArrayList<Parcelable> parcelables = new ArrayList<Parcelable>(volumeList);
        resultIntent.putParcelableArrayListExtra("volumes", parcelables);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void setVolumeType(int volumeType) {
        this.volumeType = volumeType;
        if (volumeType == Volume.TYPE_PYRAMID)
            imageView.setImageDrawable(getDrawable(R.drawable.volumepyramid));
        else
            imageView.setImageDrawable(getDrawable(R.drawable.volumeprism));
    }
}
