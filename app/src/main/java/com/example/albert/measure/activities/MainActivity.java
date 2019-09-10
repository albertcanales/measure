package com.example.albert.measure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.albert.measure.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.getstartedbt).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), HeightActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (findViewById(item.getItemId()) == findViewById(R.id.help))
            startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
        else if(findViewById(item.getItemId()) == findViewById(R.id.test_sensor))
            startActivity(new Intent(getApplicationContext(), SensorTestActivity.class));
        else
            onBackPressed();
        return true;
    }
}
