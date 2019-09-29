package com.example.albert.measure.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.albert.measure.R;
import com.example.albert.measure.elements.ElementsLists;
import com.example.albert.measure.ui.results.SectionsPagerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ResultsActivity extends AppCompatActivity {

    public static final List<String> UNITS = Arrays.asList("mm", "cm", "dm", "m");
    private String actualUnit = "cm";
    private boolean popupActive = false;

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private MenuItem unitMenuItem;

    private ElementsLists elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.results, menu);
        unitMenuItem = menu.findItem(R.id.unit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void showPopup(MenuItem item) {
        if (!popupActive) {

            popupActive = true;
            PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.unit_type, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    setActualUnit(item.getTitle().toString());
                    popupActive = false;
                    return true;
                }
            });
            popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu menu) {
                    popupActive = false;
                }
            });
            popup.show();
        }
    }

    public String getActualUnit() {
        return actualUnit;
    }

    public void setActualUnit(String actualUnit) {
        this.actualUnit = actualUnit;
        unitMenuItem.setTitle(actualUnit);
        refreshAdapter(viewPager.getCurrentItem());
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