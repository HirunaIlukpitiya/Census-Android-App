package com.example.census;

import static android.content.ContentValues.TAG;

import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listDataActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private FirebaseFirestore  db;
    private CollectionReference usersRef;
    private ArrayList<HashMap<String, Object>> userList;
    Button uploadFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list_data);
        db = FirebaseFirestore.getInstance();
        setBgColor();
        CollectionReference usersRef = db.collection("users");

        dbHelper = new DbHelper(this);
        ArrayList<HashMap<String, Object>> userList = dbHelper.getUsers();

        ListView listView = findViewById(R.id.userList);
        SimpleAdapter adapter = new SimpleAdapter(this, userList, R.layout.listrawlayout,
                new String[] {"name", "age", "gender","photo"}, new int[] {R.id.name, R.id.age, R.id.gender,R.id.photo});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view.getId() == R.id.photo) {
                    byte[] photo = (byte[]) data;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    ((ImageView) view).setImageBitmap(bitmap);
                    return true;
                }
                return false;
            }
        });
        listView.setAdapter(adapter);
        uploadFirebase = (Button) findViewById(R.id.uploadFirebase);
        uploadFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadUsersToFirebase();
            }
        });
    }



    public void setBgColor() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        int color = sharedPref.getInt("bgColor",-132097);
        ConstraintLayout Layout = findViewById(R.id.listData);
        Layout.setBackgroundColor(color);
    }


    public void showImagePopup(View v) {
        ImageView imageView = (ImageView) v;
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ImageView popupImageView = new ImageView(this);
        popupImageView.setImageBitmap(bitmap);
        PopupWindow popupWindow = new PopupWindow(popupImageView, 700,700, true);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    public void uploadUsersToFirebase() {
        ArrayList<HashMap<String, Object>> users = dbHelper.getUsers();


        for (HashMap<String, Object> user : users) {

            String photoString = Base64.encodeToString((byte[]) user.get("photo"), Base64.DEFAULT);


            DocumentReference documentReference = db.collection("users").document();


            Map<String, Object> data = new HashMap<>();
            data.put("name", user.get("name"));
            data.put("age", user.get("age"));
            data.put("gender", user.get("gender"));
            data.put("photo", photoString);
            documentReference.set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(listDataActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                            dbHelper.deleteAllUsers();
                            recreate();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(listDataActivity.this, "Error adding user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}