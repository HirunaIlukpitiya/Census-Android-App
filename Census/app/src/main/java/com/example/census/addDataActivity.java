package com.example.census;

import static android.content.ContentValues.TAG;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class addDataActivity extends AppCompatActivity {
    EditText name;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    String userGender;
    Bitmap photo;
    EditText age;
    Button camera;
    Button save;
    ImageView userPhoto;
    RadioGroup radioGender;
    RadioButton radioMale, radioFemale;
    private static final int pic_id = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        getSupportActionBar().hide();
        DbHelper dbHelper = new DbHelper(this);
        ProgressBar saveProgress = (ProgressBar) findViewById(R.id.saveProgress);
        saveProgress.setVisibility(ProgressBar.INVISIBLE);

        setBgColor();
        cameraPermission();

        userPhoto = (ImageView) findViewById(R.id.userPhoto);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        camera = (Button) findViewById(R.id.camera);
        radioGender = (RadioGroup) findViewById(R.id.radioGender);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        save = (Button) findViewById(R.id.save);
        userPhoto.setVisibility(ImageView.VISIBLE);

        radioButtonFunction();

        cameraOpen();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProgress.setVisibility(ProgressBar.VISIBLE);
                String userName = name.getText().toString();
                String userAge = age.getText().toString();

                byte[] photoBytes = getBytesFromBitmap(photo);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", userName);
                values.put("age", userAge);
                values.put("gender", userGender);
                values.put("photo", photoBytes);
                long newRowId = db.insert("users", null, values);

                Log.d(TAG, "name: " + userName);
                Log.d(TAG, "age: " + userAge);
                Log.d(TAG, "gender: " + userGender);
                Log.d(TAG, "photo: " + photo);
                Log.d(TAG, "userphoto: " + userPhoto);
                Handler handler = new Handler();
                int delay = 2000;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        saveProgress.setVisibility(ProgressBar.INVISIBLE);
                        if (newRowId == -1) {
                            Toast.makeText(getApplicationContext(), "Error!!! saving data stoped.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Success! Data saved", Toast.LENGTH_SHORT).show();

                        }
                        clearForm();
                    }
                }, delay);


            }
        });
    }
        private byte[] getBytesFromBitmap(Bitmap bitmap){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id) {
            photo = (Bitmap) data.getExtras().get("data");
            userPhoto.setImageBitmap(photo);
        }
    }
    public void clearForm(){
        name.setText("");
        age.setText("");
        radioGender.clearCheck();
        userPhoto.invalidate();
        userPhoto.setImageBitmap(null);
    }

    public void cameraPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }

    }

    public void radioButtonFunction(){
        radioMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RadioButton unSelect = (RadioButton) findViewById(R.id.radioFemale);
                    unSelect.setChecked(false);
                    Log.d(TAG, "onCheckedChanged male: ");
                    userGender = "male";
                }
                Log.d(TAG, "userGender M in method: " + userGender);
            }
        });

        radioFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RadioButton unSelect = (RadioButton) findViewById(R.id.radioMale);
                    unSelect.setChecked(false);
                    Log.d(TAG, "onCheckedChanged: female");
                    userGender = "female";
                }
                Log.d(TAG, "userGender F in method: " + userGender);
            }
        });
    }

    public void cameraOpen(){
        camera.setOnClickListener(v -> {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, pic_id);
        });
    }

    public void setBgColor(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        int color = sharedPref.getInt("bgColor",-132097);
        ConstraintLayout Layout = findViewById(R.id.addData);
        Layout.setBackgroundColor(color);
    }

}
