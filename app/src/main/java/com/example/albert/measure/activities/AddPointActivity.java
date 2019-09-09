package com.example.albert.measure.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Point;

import java.util.ArrayList;
import java.util.List;

public class AddPointActivity extends AddElementActivity {

    private EditText coordinateX;
    private EditText coordinateY;
    private EditText coordinateZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);

        context = this;

        coordinateX = findViewById(R.id.coordinate_x);
        coordinateY = findViewById(R.id.coordinate_y);
        coordinateZ = findViewById(R.id.coordinate_z);
        nameET = findViewById(R.id.name_edit_text);
        nameET.setText(String.format("Point%d", elements.getPointList().size()+1));
    }

    @Override
    List<Spinner> getSpinnerList() {
        return new ArrayList<>();
    }

    @Override
    void addElement() {
        if(nonEmptyEditText()) {
            double x = Double.valueOf(coordinateX.getText().toString());
            double y = Double.valueOf(coordinateY.getText().toString());
            double z = Double.valueOf(coordinateZ.getText().toString());
            Point point = new Point(name, x, y, z);
            if (validPoint(point)) {
                elements.getPointList().add(point);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("elements", elements);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
            else {
                Toast.makeText(context, "This point already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validPoint(Point p) {
        for (Point q : elements.getPointList()) {
            if(p.equals(q)) return false;
        }
        return true;
    }

    private boolean nonEmptyEditText() {
        boolean nonEmpty = true;
        if(coordinateX.getText().toString().isEmpty()) {
            coordinateX.setError("Cannot be empty");
            nonEmpty = false;
        } if(coordinateY.getText().toString().isEmpty()) {
            coordinateY.setError("Cannot be empty");
            nonEmpty = false;
        } if(coordinateZ.getText().toString().isEmpty()) {
            coordinateZ.setError("Cannot be empty");
            nonEmpty = false;
        } return nonEmpty;
    }
}
