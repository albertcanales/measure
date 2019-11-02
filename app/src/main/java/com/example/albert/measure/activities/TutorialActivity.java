package com.example.albert.measure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.albert.measure.R;
import com.example.albert.measure.ui.tutorial.SectionsPagerAdapter;
import com.shuhart.stepview.StepView;

import java.util.Objects;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private StepView stepView;
    private MenuItem skipItem;

    private int heightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setStepView(i);
                setLastPageSkipTitle(i + 1 == sectionsPagerAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        stepView = findViewById(R.id.step_view);
        stepView.setStepsNumber(sectionsPagerAdapter.getCount());
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                Log.d("STEP", String.valueOf(step));
                setStepView(step);
                refreshAdapter(step);
            }
        });

        heightMode = Objects.requireNonNull(getIntent().getExtras()).getInt("mode");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tutorial, menu);
        skipItem = menu.findItem(R.id.skip);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (findViewById(item.getItemId()) == findViewById(R.id.skip)) {
            Intent intent = new Intent(getApplicationContext(), PointMethodActivity.class);
            intent.putExtras(getIntent());
            startActivity(intent);
        } else
            onBackPressed();
        return true;
    }

    private void refreshAdapter(int tabPosition) {
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(tabPosition, false);
    }

    private void setStepView(int tabPosition) {
        stepView.go(tabPosition, true);
    }

    private void setLastPageSkipTitle(boolean lastPage) {
        String title = lastPage ? "Done" : "Skip";
        if (skipItem != null)
            skipItem.setTitle(title);
    }

    public int getHeightMode() {
        return heightMode;
    }
}