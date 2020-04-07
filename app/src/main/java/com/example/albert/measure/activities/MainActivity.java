package com.example.albert.measure.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.albert.measure.R;

public class MainActivity extends AppCompatActivity {

    private boolean activityActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.get_started_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityActive) {
                    startActivity(new Intent(getApplicationContext(), HeightActivity.class));
                    activityActive = false;
                }
            }
        });
        findViewById(R.id.example_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.youtube.com/watch?v=hK7HbC1EGWM";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        findViewById(R.id.tutorial_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.youtube.com/watch?v=Xj892oAFnD0";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (activityActive) {
            activityActive = false;
            if (findViewById(item.getItemId()) == findViewById(R.id.help))
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
            else if (findViewById(item.getItemId()) == findViewById(R.id.test_sensor))
                startActivity(new Intent(getApplicationContext(), SensorTestActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityActive = true;
    }
}
