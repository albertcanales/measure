package com.example.albert.measure.activities;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.albert.measure.R;

import java.util.List;

public class AddPointActivity extends AddElementActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
    }

    @Override
    List<Spinner> getSpinnerList() {
        return null;
    }

    @Override
    void addElement() {

    }


}
