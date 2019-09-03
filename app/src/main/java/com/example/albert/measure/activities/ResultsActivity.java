package com.example.albert.measure.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.Angle;
import com.example.albert.measure.elements.Area;
import com.example.albert.measure.elements.Point;
import com.example.albert.measure.elements.Vector;
import com.example.albert.measure.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultsActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    private List<Point> pointList = new ArrayList<>();
    private List<Angle> angleList = new ArrayList<>();
    private List<Vector> vectorList = new ArrayList<>();
    private List<Area> areaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setOffscreenPageLimit(1);
        // viewPager.setCurrentItem(2, false);

        pointList = Objects.requireNonNull(getIntent().getExtras()).getParcelableArrayList("pointList");
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
        Log.d("POINTS", pointList.toString());
    }

    public List<Angle> getAngleList() {
        return angleList;
    }

    public void setAngleList(List<Angle> angleList) {
        this.angleList = angleList;
        Log.d("ANGLES", angleList.toString());
    }

    public List<Vector> getVectorList() {
        return vectorList;
    }

    public void setVectorList(List<Vector> vectorList) {
        Log.d("VECTORS", vectorList.toString());
        this.vectorList = vectorList;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        Log.d("AREAS", areaList.toString());
        this.areaList = areaList;
    }

    public void refreshAdapter(int tabPosition) {
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(tabPosition, false);
    }
}