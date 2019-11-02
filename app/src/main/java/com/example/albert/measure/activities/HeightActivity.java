package com.example.albert.measure.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.albert.measure.R;

import java.util.Objects;

public class HeightActivity extends AppCompatActivity {

    public static final int MODE_MANUAL = 0;
    private static final int MODE_AUTOMATIC = 1;

    private int heightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        final ImageView imageView = findViewById(R.id.image_height);
        RadioGroup radioGroup = findViewById(R.id.height_type_rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.type_manual_rb) {
                    imageView.setImageDrawable(getDrawable(R.drawable.modemanual));
                    heightMode = MODE_MANUAL;
                } else {
                    imageView.setImageDrawable(getDrawable(R.drawable.modeautomatic));
                    heightMode = MODE_AUTOMATIC;
                }
            }
        });
        radioGroup.check(R.id.type_manual_rb);

        final EditText editText = findViewById(R.id.height_et);

        findViewById(R.id.tutorial_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (text.isEmpty())
                    editText.setError("Cannot be empty");
                else {
                    double h = (heightMode == MODE_MANUAL) ? Double.valueOf(text) : 0.75 * Double.valueOf(text);
                    Intent i = new Intent(getApplicationContext(), TutorialActivity.class);
                    i.putExtra("h", h);
                    i.putExtra("mode", heightMode);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
