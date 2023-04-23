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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText loginPassword;
    Button loginButton;
    String password,savedPassword;
    int loginAttempt = 0,remainingAttempt = 3,color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        setBgColor();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                getPassword();
                getSavedPassword();
                if(Objects.equals(password, savedPassword)){
                    moveToNext();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Password Incorrect!!! Retry", Toast.LENGTH_SHORT).show();
                    loginAttempt = loginAttempt + 1;
                    remainingAttempt = remainingAttempt - 1;
                    Toast.makeText(getApplicationContext(), "Remaining Attempts "+remainingAttempt , Toast.LENGTH_SHORT).show();
                }
                if(loginAttempt == 3){
                    LoginActivity.this.finish();
                    finishAffinity();
                }
            }
        });
    }

    public void getPassword(){
        password = loginPassword.getText().toString();
    }

    public void getSavedPassword(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        savedPassword = sharedPref.getString("password","default");
    }

    public void moveToNext(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        Log.d(TAG, "moveToNext: worked");
    }

    public void setBgColor(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        color = sharedPref.getInt("bgColor",-132097);
        ConstraintLayout Layout = findViewById(R.id.login);
        Layout.setBackgroundColor(color);
    }
}