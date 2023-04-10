package com.example.garageapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.garageapp.utility.Cars;

import java.util.ArrayList;

public class CarsDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "garage";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "cars";

    // below variable is for our id column.
    private static final String MAKE_NAME = "make_name";
    private static final String MODEL_NAME = "model_name";
    private static final String CAR_IMAGE = "car_image";

    // creating a constructor for our database handler.
    public CarsDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + MAKE_NAME + " TEXT PRIMARY KEY, "
                + CAR_IMAGE + " TEXT, "
                + MODEL_NAME + " TEXT);";
        db.execSQL(query);
    }

    // this method is use to add new car to our sqlite database.
    public void addNewCar(String ModelName,String MakeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MAKE_NAME, MakeName);
        values.put(MODEL_NAME, ModelName);
        values.put(CAR_IMAGE,"null");
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Cars> readCarsList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Cars> CarsList = new ArrayList<>();
        if (cursorCourses.moveToFirst()) {
            do {
                CarsList.add(new Cars(cursorCourses.getString(2),cursorCourses.getString(4),cursorCourses.getInt(1),cursorCourses.getInt(3),cursorCourses.getBlob(5)));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return CarsList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
