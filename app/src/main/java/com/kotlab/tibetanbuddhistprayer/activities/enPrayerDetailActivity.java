package com.kotlab.tibetanbuddhistprayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;


import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;

import org.w3c.dom.Text;

public class enPrayerDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static final String TAG ="PrayerDetails";
    private TextView txttitle, txtbody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_prayer_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txttitle = (TextView) findViewById(R.id.entxttitle);
        txtbody = (TextView) findViewById(R.id.entxtbody);

     try {

         EnglishData englishData = (EnglishData) getIntent().getSerializableExtra("data");

         String title = englishData.getTitle();
         String body = englishData.getBody();
         txttitle.setText(title);
         txtbody.setText(body);

         Log.d(TAG,"title ="+title+" and body ="+body);
     }catch (Exception ex) {

         ex.fillInStackTrace();
     }







    }

}
