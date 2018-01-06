package com.kotlab.tibetanbuddhistprayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.kotlab.tibetanbuddhistprayer.R;

public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutLog";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.toolbar);
        setupToolBar("About Us");
    }

    private void setupToolBar(String app_name) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(app_name.toUpperCase());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(AboutActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action =event.getAction();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(TAG,"Action was DOWN and nothing is good");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }

    }
}
