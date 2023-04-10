package com.example.garageapp.database;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LoginDBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "Garage.db";

    // below int is our database version
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "login";

    private static final String USER_EMAIL = "email";

    private static final String USER_PASSWORD = "password";
    private static final String USER_IMAGE = "image";

    // creating a constructor for our database handler.
    public LoginDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS login(email TEXT PRIMARY KEY, image BLOB, password TEXT);";
        db.execSQL(query);
    }

    // this method is use to add new car to our sqlite database.
    public long addNewUser(String UserEmail,String UserPassword,byte[] UserImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, UserEmail);
        values.put(USER_IMAGE, UserImage);
        values.put(USER_PASSWORD, UserPassword);
        long id = db.insert(TABLE_NAME, null, values);
        SQLiteDatabase rdb = this.getReadableDatabase();
        Cursor cursor = rdb.rawQuery("SELECT * FROM login",null);
        String result = "";
        while (cursor.moveToNext()) {
            String result_0 = cursor.getString(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            result += result_0 + " " + result_1 +" "+result_2+
                    System.getProperty("line.separator");
        }
        cursor.close();
        rdb.close();
        Log.i(TAG, "addNewUser: "+result);
        db.close();
        return id;
    }

    public Boolean checkusername(String email) {
        Log.i(TAG, "hit and run");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE email = ?", new String[]{email});
        Log.i(TAG, "count: "+cursor.getCount());
        return cursor.moveToFirst();
    }

    public Boolean checkusernamepassword(String email, String password){
        Log.i(TAG, "run and hit");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE email = ? AND password = ?", new String[]{email, password});
        Log.i(TAG,"count: "+cursor.getCount());
        return cursor.moveToFirst();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
