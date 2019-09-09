package com.example.albert.measure.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.ElementsLists;
import com.example.albert.measure.ui.main.SectionsPagerAdapter;

import java.util.Objects;

public class ResultsActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    private ElementsLists elements;

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

        elements = Objects.requireNonNull(getIntent().getExtras()).getParcelable("elements");
    }

    public ElementsLists getElements() {
        Log.d("ELEMENTS", elements.toString());
        return elements;
    }

    public void setElements(ElementsLists elements) {
        Log.d("ELEMENTS", elements.toString());
        this.elements = elements;
    }

    public void refreshAdapter(int tabPosition) {
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(tabPosition, false);
    }
}