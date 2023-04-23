package com.example.census;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText newPassword;
    Button registerButton;
    String prevStarted = "yes";
    String password;
    int color;
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean(prevStarted, false)) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            moveToLogin();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        newPassword = (EditText) findViewById(R.id.newPassword);
        registerButton = (Button) findViewById(R.id.registerButton);

        setBgColor();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePassword();
                Toast.makeText(getApplicationContext(), "Password is saved", Toast.LENGTH_LONG).show();
                moveToLogin();
                Log.d(TAG, "onClick: worked ");


            }
        });
    }

    public void moveToLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        Log.d(TAG, "moveToLogin: worked");
    }
    public void savePassword(){
        password = newPassword.getText().toString();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("password",password);
        editor.apply();
    }

    public void setBgColor(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        color = sharedPref.getInt("bgColor",-132097);
        ConstraintLayout Layout = findViewById(R.id.register);
        Layout.setBackgroundColor(color);
    }
}