package com.example.census;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setBgColor();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
    public void setBgColor(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        color = sharedPref.getInt("bgColor",-132097);
        ConstraintLayout Layout = findViewById(R.id.splash);
        Layout.setBackgroundColor(color);
    }
}