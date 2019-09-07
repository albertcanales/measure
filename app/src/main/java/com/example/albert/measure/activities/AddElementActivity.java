package com.example.albert.measure.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Angle;
import com.example.albert.measure.elements.Area;
import com.example.albert.measure.elements.Element;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.elements.Vector;
import com.example.albert.measure.elements.Volume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.albert.measure.ui.main.SectionsPagerAdapter.*;

public abstract class AddElementActivity extends AppCompatActivity {

    EditText nameET;
    String name;

    List<Point> pointList;
    List<Angle> angleList;
    List<Vector> vectorList;
    List<Area> areaList;
    List<Volume> volumeList;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        pointList = getIntent().getParcelableArrayListExtra(getString(TAB_TITLES[POINT_TAB]));
        angleList = getIntent().getParcelableArrayListExtra(getString(TAB_TITLES[ANGLE_TAB]));
        vectorList = getIntent().getParcelableArrayListExtra(getString(TAB_TITLES[VECTOR_TAB]));
        areaList = getIntent().getParcelableArrayListExtra(getString(TAB_TITLES[AREA_TAB]));
        volumeList = getIntent().getParcelableArrayListExtra(getString(TAB_TITLES[VOLUME_TAB]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_element, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (findViewById(item.getItemId()) == findViewById(R.id.add_element_ok)) {
            Log.d("ADD_ELEMENT", "Adding value...");
            if (validInput()) {
                addElement();
            }
        } else {
            setResult(Activity.RESULT_CANCELED);
            onBackPressed();
        }
        return true;
    }

    List<String> getPointNames() {
        List<String> names = new ArrayList<>();
        for (Point p : pointList) names.add(p.getName());
        return names;
    }

    List<String> getAngleNames() {
        List<String> names = new ArrayList<>();
        for (Angle a : angleList) names.add(a.getName());
        return names;
    }

    List<String> getVectorNames() {
        List<String> names = new ArrayList<>();
        for (Vector v : vectorList) names.add(v.getName());
        return names;
    }

    List<String> getAreaNames() {
        List<String> names = new ArrayList<>();
        for (Area a : areaList) names.add(a.getName());
        return names;
    }

    List<String> getVolumeNames() {
        List<String> names = new ArrayList<>();
        for (Volume v : volumeList) names.add(v.getName());
        return names;
    }

    abstract List<Spinner> getSpinnerList();

    ArrayAdapter<String> getDataAdapter(List<String> items) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return dataAdapter;
    }

    boolean validInput() {
        return checkName() && validSpinners();
    }

    private boolean checkName() {
        name = nameET.getText().toString().trim();
        return Element.validNameOfET(nameET, pointList, angleList, vectorList, areaList, volumeList) == 0;
    }

    // Check if have the same value, O(n^2)
    boolean validSpinners() {
        List<Spinner> spinners = getSpinnerList();
        for (int i = 0; i < spinners.size(); i++) {
            for (int j = i + 1; j < spinners.size(); j++) {
                if (spinners.get(i).getSelectedItem().toString().equals(
                        spinners.get(j).getSelectedItem().toString())) {
                    Toast.makeText(context, "All spinners must have different elements", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
    }

    abstract void addElement();
}
