package com.kotlab.tibetanbuddhistprayer.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.kotlab.tibetanbuddhistprayer.model.TibData;
import com.warkiz.widget.IndicatorSeekBar;

public class TibPrayerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private static final String TAG = "PrayerDetails";
    private TextView txttitle, txtbody;
    private TextView counttv,counttvsecond,countvtxttop,countvtxtbtm;
    private PechaDatabase pechaDatabase;
    private String title,body;
    private int prayer_id;
    private int prayer_count =0;
    private LinearLayout minuslayout,pluslayout;
    private Dialog dialog;
    private Button dialogbtn ;
    private int seekbarCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tib_prayer_detail);
        initView();
        setupToolBar(getResources().getString(R.string.txtpecha));
        getData();
        setcustomfont();
    }

    private void setcustomfont() {

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/monlam_five_header.ttf");
        txttitle.setTypeface(typeface);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/nototibetanregular.ttf");
        Typeface tfbody = Typeface.createFromAsset(getAssets(),"fonts/monlam_two_body.ttf");
        txtbody.setTypeface(tfbody);
        countvtxtbtm.setTypeface(tf);
        countvtxttop.setTypeface(tf);
    }

    private void initView() {
      toolbar = findViewById(R.id.toolbar);
      txtbody = findViewById(R.id.tibtxtbody);
      txttitle = findViewById(R.id.tibtxttitle);
      counttv = findViewById(R.id.counttv);
      counttvsecond = findViewById(R.id.counttvsecond);
      countvtxttop = findViewById(R.id.countvtxttop);
      countvtxtbtm = findViewById(R.id.countvtxtbtm);
      minuslayout = findViewById(R.id.layoutminus);
      pluslayout = findViewById(R.id.layoutplus);
      minuslayout.setOnClickListener(this);
      pluslayout.setOnClickListener(this);
      pechaDatabase = new PechaDatabase(this);
    }

    private void setupToolBar(String app_name) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(app_name.toUpperCase());
    }

    private void getData() {
        try {
            TibData tibData = (TibData) getIntent().getSerializableExtra("tibData");
            title = tibData.getTibtitle();
            body=tibData.getTibbody();
            prayer_id=tibData.getTibId();
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

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.layoutplus) {
            IncreaseCountNumber();
        }else if(v.getId()==R.id.layoutminus){
            DecreaseCountNumber();
        }else if(v.getId()==R.id.dialogbtn){
            dialog.dismiss();
            txtbody.setTextSize(seekbarCount);
        }
    }
    private void DecreaseCountNumber() {
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase,prayer_id);
        if(cursor.getCount()>0){
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
        }else {
            Toast.makeText(this,"it is already at 0",Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(TibPrayerDetailActivity.this,MainActivity.class);
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
        dialogbtn = dialog.findViewById(R.id.dialogbtn);
        dialogbtn.setOnClickListener(this);
        final TextView previewtv = dialog.findViewById(R.id.previewtv);

        seekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

                previewtv.setTextSize(progress);
                seekbarCount = progress;
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
        PechaDatabase pechaDatabase = new PechaDatabase(this);
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase,prayer_id);

        if(cursor.getCount()>0){

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
