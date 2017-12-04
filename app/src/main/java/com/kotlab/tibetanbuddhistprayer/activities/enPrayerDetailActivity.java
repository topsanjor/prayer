package com.kotlab.tibetanbuddhistprayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;

public class enPrayerDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static final String TAG ="PrayerDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_prayer_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

     try {

         EnglishData englishData = (EnglishData) getIntent().getSerializableExtra("data");

         String title = englishData.getTitle();
         String body = englishData.getBody();

         Log.d(TAG,"title ="+title+" and body ="+body);
     }catch (Exception ex) {

         ex.fillInStackTrace();
     }







    }

}
