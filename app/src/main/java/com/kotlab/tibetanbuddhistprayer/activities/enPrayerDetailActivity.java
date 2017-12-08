package com.kotlab.tibetanbuddhistprayer.activities;

import android.content.Intent;
import android.database.Cursor;
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
import com.kotlab.tibetanbuddhistprayer.database.PechaDatabase;
import com.kotlab.tibetanbuddhistprayer.database.TableData;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;
public class enPrayerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    private static final String TAG = "PrayerDetails";
    private TextView txttitle, txtbody;
    private TextView counttv;
    private Button countbtn;
    private PechaDatabase pechaDatabase;
    private String title,body;
    private int prayer_id;
    private int prayer_count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_prayer_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        initView();
        setupToolBar("English Prayer");
        try {

            EnglishData englishData = (EnglishData) getIntent().getSerializableExtra("data");

            title = englishData.getTitle();
            body = englishData.getBody();
            prayer_id=englishData.getId();
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
        counttv= findViewById(R.id.counttv);

        countbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.countbtn) {
            IncreaseCountNumber();
        }
    }

    private void IncreaseCountNumber() {



        pechaDatabase = new PechaDatabase(this);
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase,prayer_id);
        if(cursor.getCount()==0){
            prayer_count ++;
            pechaDatabase.AddPrayerData(pechaDatabase,prayer_id,prayer_count);
            Log.d("PrayerCount", String.valueOf(prayer_count));

        }else {


            try{

                if (cursor.moveToFirst()) {
                    do {
                        prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));
                        Log.d("PrayerCount", String.valueOf(prayer_count));
                        prayer_count ++;


                    }while (cursor.moveToNext());


                }
                pechaDatabase.UpdatePrayeReadCount(pechaDatabase,prayer_id,prayer_count);

            }catch (Exception ex){
                ex.fillInStackTrace();

            }

            Log.d("LstRst", String.valueOf(prayer_count));

        }

        cursor.close();
        Log.d("PrayerCountTV", String.valueOf(prayer_count));

        String count = String.valueOf(prayer_count);
        counttv.setText(count);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(enPrayerDetailActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
                return true;


        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();

        PechaDatabase pechaDatabase = new PechaDatabase(this);
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase,prayer_id);

        if(cursor.getCount()>0){


            if (cursor.moveToFirst()) {
                do {
                    prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));
                    Log.d("PrayerCount", String.valueOf(prayer_count));



                }while (cursor.moveToNext());


            }
        }

        String count = String.valueOf(prayer_count);
        counttv.setText(count);

    }
}