package com.example.census;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;

import org.jetbrains.annotations.NotNull;

import top.defaults.colorpicker.ColorPickerPopup;


public class MainActivity extends AppCompatActivity {

    Button preference;
    int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        setBgColor();

        preference = (Button) findViewById(R.id.preference);

        preference.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        int DefaultColor = 0;
                        new ColorPickerDialog
                                .Builder(MainActivity.this)
                                .setTitle("Pick Background Color")
                                .setColorShape(ColorShape.SQAURE)
                                .setDefaultColor(DefaultColor)
                                .setColorListener(new ColorListener() {
                                    @Override
                                    public void onColorSelected(int color, @NotNull String colorHex) {

                                        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putInt("bgColor",color);
                                        editor.apply();
                                        Log.d(TAG, "ColorSelected: " + color);
                                        Log.d(TAG, "onColorSelected hex: " + colorHex);
                                        Log.d(TAG, "onColorSelected: String color " + String.valueOf(color));
                                        Log.d(TAG, "onColorSelected: int " + Color.RED);
                                        ConstraintLayout Layout = findViewById(R.id.main);
                                        Layout.setBackgroundColor(color);
                                        Log.d(TAG, "LAYOUT: " + Layout);
                                    }
                                })
                                .show();
                    }
                });

    }
    public void setBgColor(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        color = sharedPref.getInt("bgColor",-132097);
        ConstraintLayout Layout = findViewById(R.id.main);
        Layout.setBackgroundColor(color);
    }
}