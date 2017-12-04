package com.kotlab.tibetanbuddhistprayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.helper.UserSessionManager;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;

import org.w3c.dom.Text;

public class enPrayerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    private static final String TAG = "PrayerDetails";
    private TextView txttitle, txtbody;
    private TextView counttxt;
    private Button countbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_prayer_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txttitle = (TextView) findViewById(R.id.entxttitle);
        txtbody = (TextView) findViewById(R.id.entxtbody);

        initView();
        try {

            EnglishData englishData = (EnglishData) getIntent().getSerializableExtra("data");

            String title = englishData.getTitle();
            String body = englishData.getBody();
            txttitle.setText(title);
            txtbody.setText(body);

            Log.d(TAG, "title =" + title + " and body =" + body);
        } catch (Exception ex) {

            ex.fillInStackTrace();
        }


    }

    private void initView() {

        countbtn = (Button) findViewById(R.id.countbtn);
        counttxt = (TextView) findViewById(R.id.counttxt);
        countbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.countbtn) {
            IncreaseCountNumber();
        }
    }

    private void IncreaseCountNumber() {


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}