package com.kotlab.tibetanbuddhistprayer.activities;

import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.database.PechaDatabase;
import com.kotlab.tibetanbuddhistprayer.database.TableData;
import com.kotlab.tibetanbuddhistprayer.helper.UserSessionManager;
import com.kotlab.tibetanbuddhistprayer.model.EnglishData;
import com.warkiz.widget.IndicatorSeekBar;

import org.w3c.dom.Text;

public class enPrayerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private static final String TAG = "PrayerDetails";
    private TextView txttitle, txtbody;
    private TextView counttv,counttvsecond;
    private PechaDatabase pechaDatabase;
    private String title,body;
    private int prayer_id;
    private int prayer_count =0;
    private LinearLayout minuslayout,pluslayout;
    private Dialog dialog;
    private Button dialogbtn ;
    private float seekbarCount;
    private UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_en_prayer_detail);
        initView();
        setupToolBar(getString(R.string.endetailtext));
        getData();
        userSessionManager = new UserSessionManager(this);
        float entxtsize = userSessionManager.getEnvalue();
        txtbody.setTextSize(entxtsize);
        txttitle.setTextSize(entxtsize);
        seekbarCount = entxtsize;

    }

    private void getData() {

        try {

            EnglishData englishData = (EnglishData) getIntent().getSerializableExtra("enData");

            title = englishData.getTitle();
            body = englishData.getBody();
            prayer_id=englishData.getId();
            txttitle.setText(title);
            txtbody.setText(body);

        } catch (Exception ex) {

            ex.fillInStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu_en,menu);
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txttitle = (TextView) findViewById(R.id.entxttitle);
        txtbody = (TextView) findViewById(R.id.entxtbody);
        counttv= (TextView) findViewById(R.id.counttv);
        counttvsecond = (TextView) findViewById(R.id.counttvsecond);
        minuslayout = (LinearLayout) findViewById(R.id.layoutminus);
        pluslayout = (LinearLayout) findViewById(R.id.layoutplus);
        minuslayout.setOnClickListener(this);
        pluslayout.setOnClickListener(this);
        pechaDatabase = new PechaDatabase(this);

    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.layoutplus) {
            IncreaseCountNumber();
        }else if(v.getId()==R.id.layoutminus){
            DecreaseCountNumber();
        }else if(v.getId()==R.id.dialogbtn){

            dialog.dismiss();
            txtbody.setTextSize(seekbarCount);
            txttitle.setTextSize(seekbarCount);

        }
    }

    private void DecreaseCountNumber() {
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase,prayer_id);

        if(cursor.getCount()==1){

            try {
                if(cursor.moveToFirst()){
                    do{
                        if(prayer_count>0){
                            prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));
                            prayer_count--;
                        }else {
                            Toast.makeText(this,"it is already at 0",Toast.LENGTH_SHORT).show();
                        }
                    }while (cursor.moveToNext());
                }
                pechaDatabase.UpdatePrayeReadCount(pechaDatabase,prayer_id,prayer_count);
            }catch (Exception ex){
                ex.fillInStackTrace();
            }

        }

        cursor.close();
        String count = String.valueOf(prayer_count);
        counttv.setText(count);
        counttvsecond.setText(count);


    }

    private void IncreaseCountNumber() {

        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase,prayer_id);
        if(cursor.getCount()==0){
            prayer_count ++;
            pechaDatabase.AddPrayerData(pechaDatabase,prayer_id,prayer_count);

        }else {


            try{

                if (cursor.moveToFirst()) {
                    do {
                        prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));
                        prayer_count ++;
                    }while (cursor.moveToNext());
                }
                pechaDatabase.UpdatePrayeReadCount(pechaDatabase,prayer_id,prayer_count);

            }catch (Exception ex){
                ex.fillInStackTrace();
            }
        }
        cursor.close();
        String count = String.valueOf(prayer_count);
        counttv.setText(count);
        counttvsecond.setText(count);
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
            case R.id.menu_fontsize:
                changeFontSize();
                return true;

            case R.id.menu_reset:
                resetCount();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void resetCount() {

        long cursor =pechaDatabase.UpdatePrayeReadCount(pechaDatabase,prayer_id,0);
        prayer_count =0;
        Log.d("Cursor",String.valueOf(cursor));
        counttv.setText("0");
        counttvsecond.setText("0");
    }

    private void changeFontSize() {
        dialog= new Dialog(this);
        dialog.setContentView(R.layout.font_size_layout);
        dialog.setTitle("Preview Font Size");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        IndicatorSeekBar seekBar = dialog.findViewById(R.id.seekbar);
        seekBar.setProgress(seekbarCount);
        dialogbtn = dialog.findViewById(R.id.dialogbtn);
        dialogbtn.setOnClickListener(this);
        final TextView previewtv = dialog.findViewById(R.id.previewtv);
         previewtv.setTextSize(seekbarCount);

        seekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

                previewtv.setTextSize(progress);
                seekbarCount = progress;
                userSessionManager.setEnvalue(seekbarCount);

            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String tickBelowText, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

         pechaDatabase = new PechaDatabase(this);
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase,prayer_id);

        if(cursor.getCount()==1){


            if (cursor.moveToFirst()) {
                do {
                    prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));


                }while (cursor.moveToNext());


            }
        }

        String count = String.valueOf(prayer_count);
        counttv.setText(count);
        counttvsecond.setText(count);

    }
}