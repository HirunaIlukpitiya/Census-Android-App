package com.example.census;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.firebase.database.DatabaseReference;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class listDataActivity extends AppCompatActivity {

    String [] array1 = {"test1","test2","test"};

    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        setBgColor();
        CollectionReference usersRef = db.collection("users");

        dbHelper = new DbHelper(this);
        ArrayList<HashMap<String, Object>> userList = dbHelper.getUsers();

        ListView listView = (ListView) findViewById(R.id.userList);
        SimpleAdapter adapter = new SimpleAdapter(this, userList, R.layout.listrawlayout,
                new String[] {"name", "age", "gender","photo"}, new int[] {R.id.name, R.id.age, R.id.gender,R.id.photo});
        listView.setAdapter(adapter);

    }

    public void setBgColor(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.Census), Context.MODE_PRIVATE);
        int color = sharedPref.getInt("bgColor",-132097);
        ConstraintLayout Layout = findViewById(R.id.listData);
        Layout.setBackgroundColor(color);
    }
//    public void uploadToFirebase() {
//            ArrayList<HashMap<String, Object>> userList = getUsers();
//
//            for (HashMap<String, Object> user : userList) {
//                String id = user.get("id").toString();
//                String name = user.get("name").toString();
//                String age = user.get("age").toString();
//                String gender = user.get("gender").toString();
//                byte[] photo = (byte[]) user.get("photo");
//
//                User newUser = new User(name, age, gender, photo);
//                myRef.child(id).setValue(newUser);
//            }
//    }
}