package com.example.census;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

public class addDataActivity extends AppCompatActivity {

ProgressBar saveProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        getSupportActionBar().hide();
    saveProgress = (ProgressBar) findViewById(R.id.saveProgress);
    }
}