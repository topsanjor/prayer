package com.kotlab.tibetanbuddhistprayer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;
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

        initView();
        setupToolBar("English Prayer");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    private void setupToolBar(String app_name) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(app_name.toUpperCase());


    }

    private void initView() {

        txttitle = (TextView) findViewById(R.id.entxttitle);
        txtbody = (TextView) findViewById(R.id.entxtbody);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(enPrayerDetailActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;


        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}