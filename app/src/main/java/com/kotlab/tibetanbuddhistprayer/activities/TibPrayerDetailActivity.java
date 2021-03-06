package com.kotlab.tibetanbuddhistprayer.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kotlab.tibetanbuddhistprayer.R;
import com.kotlab.tibetanbuddhistprayer.database.PechaDatabase;
import com.kotlab.tibetanbuddhistprayer.database.TableData;
import com.kotlab.tibetanbuddhistprayer.helper.UserSessionManager;
import com.kotlab.tibetanbuddhistprayer.model.MyPrayerData;
import com.kotlab.tibetanbuddhistprayer.model.TibData;
import com.warkiz.widget.IndicatorSeekBar;

import java.util.ArrayList;

public class TibPrayerDetailActivity extends AppCompatActivity implements View.OnClickListener,GestureDetector.OnGestureListener{
    private Toolbar toolbar;
    private static final String TAG = "PrayerDetails";
    private TextView txttitle, txtbody;
    private TextView counttv, counttvsecond, countvtxttop, countvtxtbtm;
    private PechaDatabase pechaDatabase;
    private String title, body;
    private int prayer_id;
    private int prayer_count = 0;
    private LinearLayout minuslayout, pluslayout;
    private Dialog dialog;
    private Button dialogbtn;
    private float seekbarCount;
    private Typeface tf;
    private Context context;
    private UserSessionManager userSessionManager;
    private float pv;
    private GestureDetectorCompat gestureDetectorCompat ;
    private LinearLayout tibdetail_layout;
    private ArrayList<TibData> tibDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tib_prayer_detail);
        this.context = getApplicationContext();
        gestureDetectorCompat = new GestureDetectorCompat(context,TibPrayerDetailActivity.this);
        userSessionManager = new UserSessionManager(context);
        initView();
        setupToolBar(getResources().getString(R.string.txtpecha));
        getData();
        setcustomfont();
        float textsie = userSessionManager.getTibvalue();
        txtbody.setTextSize(textsie);
        txttitle.setTextSize(textsie);
        seekbarCount = textsie;
        try  {
            int size =tibDatas.size();
            Log.d(TAG,"Data size="+String.valueOf(size));

        }catch (Exception ex) {

            ex.fillInStackTrace();
        }

    }

    private void setcustomfont() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/monlam_five_header.ttf");
        txttitle.setTypeface(typeface);
        tf = Typeface.createFromAsset(getAssets(), "fonts/nototibetanregular.ttf");
        Typeface tfbody = Typeface.createFromAsset(getAssets(), "fonts/monlam_two_body.ttf");
        txtbody.setTypeface(tfbody);
        countvtxtbtm.setTypeface(tf);
        countvtxttop.setTypeface(tf);
    }

    private void initView() {
        tibdetail_layout = findViewById(R.id.tibdetailaLayout);
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
        settypeface(toolbar);
    }


    private void settypeface(Toolbar toolbar) {

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.
                        createFromAsset(getAssets(), "fonts/nototibetanregular.ttf");
                if (tv.getText().equals(toolbar.getTitle())) {
                    tv.setTypeface(titleFont);
                    break;
                }
            }
        }
    }

    private void getData() {

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        if (type != null) {

            if (type.equalsIgnoreCase("tibetan")) {

                try {
                    TibData tibData = (TibData) getIntent().getSerializableExtra("tibData");
                    title = tibData.getTibtitle();
                    body = tibData.getTibbody();
                    prayer_id = tibData.getTibId();
                    txttitle.setText(title);
                    txtbody.setText(body);
                } catch (Exception ex) {

                    ex.fillInStackTrace();
                }

            } else if (type.equalsIgnoreCase("mptibetan")) {

                try {

                    MyPrayerData mpdata = (MyPrayerData) getIntent().getSerializableExtra("tibData");
                    title = mpdata.getTitle();
                    body = mpdata.getBody();
                    prayer_id = mpdata.getId();
                    txttitle.setText(title);
                    txtbody.setText(body);
                } catch (Exception ex) {

                    ex.fillInStackTrace();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu_en, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.layoutplus) {
            IncreaseCountNumber();
        } else if (v.getId() == R.id.layoutminus) {
            DecreaseCountNumber();
        } else if (v.getId() == R.id.dialogbtn) {
            dialog.dismiss();
            txtbody.setTextSize(seekbarCount);
            txttitle.setTextSize(seekbarCount);
        }
    }

    private void DecreaseCountNumber() {
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase, prayer_id);
        if (cursor.getCount() > 0) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        if (prayer_count > 0) {
                            prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));
                            prayer_count--;
                        } else {
                            Toast.makeText(this, "it is already at 0", Toast.LENGTH_SHORT).show();
                        }
                    } while (cursor.moveToNext());
                }
                pechaDatabase.UpdatePrayeReadCount(pechaDatabase, prayer_id, prayer_count);
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        } else {
            Toast.makeText(this, "it is already at 0", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        String count = String.valueOf(prayer_count);
        counttv.setText(count);
        counttvsecond.setText(count);
    }

    private void IncreaseCountNumber() {

        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase, prayer_id);
        if (cursor.getCount() == 0) {
            prayer_count++;
            pechaDatabase.AddPrayerData(pechaDatabase, prayer_id, prayer_count);
        } else {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));
                        prayer_count++;
                    } while (cursor.moveToNext());
                }
                pechaDatabase.UpdatePrayeReadCount(pechaDatabase, prayer_id, prayer_count);
            } catch (Exception ex) {
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

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(TibPrayerDetailActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                return true;
            case R.id.menu_fontsize:
                changeFontSize();
                return true;
            case R.id.menu_reset:
                resetCount();
                return true;
            case R.id.menu_prayer:
                AddtoMyPrayer();
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    private void AddtoMyPrayer() {

        int prayer_count = 0;
        PechaDatabase pechaDatabase = new PechaDatabase(context);
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase, prayer_id);
        Log.d("Count Value", String.valueOf(cursor));
        try {
            if (cursor.moveToFirst()) {
                do {
                    prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));

                } while (cursor.moveToNext());
            }
            cursor.close();

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        if(cursor.isClosed()==false)
        {
            cursor.close();
        }
        String langtype="tibetan";
        long value = pechaDatabase.AddMyPrayerData(pechaDatabase,title,body,langtype,prayer_id,prayer_count);
        if(value==-1){
            showMessage("Something wrong try again.");
        }else {
            showMessage("successfully entered");
        }

    }

    private void showMessage(String s) {

        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    private void resetCount() {
        long cursor = pechaDatabase.UpdatePrayeReadCount(pechaDatabase, prayer_id, 0);
        prayer_count = 0;
        counttv.setText("0");
        counttvsecond.setText("0");
    }

    private void changeFontSize() {


        dialog = new Dialog(this);
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
        previewtv.setText(getResources().getString(R.string.previewtxttibetan));
        previewtv.setTypeface(tf);
        previewtv.setTextSize(seekbarCount);

        seekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {

                previewtv.setTextSize(progress);
                seekbarCount = progress;
                userSessionManager.setTibvalue(progress);
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
        Cursor cursor = pechaDatabase.getTotalReadCount(pechaDatabase, prayer_id);

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {

                    prayer_count = cursor.getInt(cursor.getColumnIndex(TableData.PrayerTable.COUNT));

                } while (cursor.moveToNext());
            }
        }
        String count = String.valueOf(prayer_count);
        counttv.setText(count);
        counttvsecond.setText(count);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if(e1.getX() - e2.getX() > 50){

            Toast.makeText(TibPrayerDetailActivity.this , " Swipe Left " , Toast.LENGTH_LONG).show();
            return true;
        }

        if(e2.getX() - e1.getX() > 50) {

            Toast.makeText(TibPrayerDetailActivity.this, " Swipe Right ", Toast.LENGTH_LONG).show();

            return true;
        }
        else {

            return true ;
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.gestureDetectorCompat.onTouchEvent(ev);
        Log.d(TAG,"Dispatched...");
        return super.dispatchTouchEvent(ev);
    }

}
