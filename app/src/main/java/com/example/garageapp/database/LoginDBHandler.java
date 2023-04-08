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
    private static final String DB_NAME = "garage";

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
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + USER_EMAIL+ " TEXT PRIMARY KEY, "
                + USER_IMAGE+ " BLOB, "
                + USER_PASSWORD + " TEXT)";
        db.execSQL(query);
    }

    // this method is use to add new car to our sqlite database.
    public void addNewUser(String UserName,String UserPassword,String UserImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, UserName);
        values.put(USER_PASSWORD, UserPassword);
        values.put(USER_IMAGE, UserImage);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Boolean checkusername(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from login where email = ?", new String[]{email});
        Log.d(TAG, "checkusername: run and hit");
        if (cursor.getCount() > 0) {
            Log.i(TAG, "checkusername: "+cursor.getCount());
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkusernamepassword(String email, String password){
        Log.i(TAG, "checkusernamepassword: hit and run");
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from login where email = ? and password = ?", new String[] {email,password});
        if(cursor.getCount()>0) {
            Log.i(TAG, "checkusernamepassword: "+cursor.getCount());
            return true;
        }
        else {
            Log.i(TAG, "checkusernamepassword: "+cursor.getCount());
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
