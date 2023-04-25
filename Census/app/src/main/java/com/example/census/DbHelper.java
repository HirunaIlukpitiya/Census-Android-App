package com.example.census;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "census.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_PHOTO = "photo";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_AGE + " TEXT NOT NULL,"
            + COLUMN_GENDER + " TEXT NOT NULL,"
            + COLUMN_PHOTO + " BLOB);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newCVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }
    public ArrayList<HashMap<String, Object>> getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();

        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_AGE, COLUMN_GENDER, COLUMN_PHOTO};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            HashMap<String, Object> user = new HashMap<String, Object>();
            user.put("id", cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            user.put("name", cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            user.put("age", cursor.getString(cursor.getColumnIndex(COLUMN_AGE)));
            user.put("gender", cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
            user.put("photo", cursor.getBlob(cursor.getColumnIndex(COLUMN_PHOTO)));
            userList.add(user);
        }

        cursor.close();
        db.close();

        return userList;
    }

}