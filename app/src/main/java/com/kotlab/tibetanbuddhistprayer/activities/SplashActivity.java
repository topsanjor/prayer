package com.kotlab.tibetanbuddhistprayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.kotlab.tibetanbuddhistprayer.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        },SPLASH_TIME);
    }
}
