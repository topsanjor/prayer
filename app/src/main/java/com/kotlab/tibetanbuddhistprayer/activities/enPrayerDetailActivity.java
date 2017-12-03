package com.kotlab.tibetanbuddhistprayer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.kotlab.tibetanbuddhistprayer.R;

public class enPrayerDetailActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_prayer_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

}
