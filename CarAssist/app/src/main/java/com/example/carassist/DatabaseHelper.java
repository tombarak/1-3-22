package com.example.carassist;

/**
 * Created by Parsania Hardik on 15/01/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 11/01/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "carassist_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CARS = "cars";
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String PRICE= "price";
    private static final String AGENCY = "agency";
    private static final String COMMENTS  = "comments";
    private static final String PHOTO = "photo";

    /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/
    private static final String CREATE_TABLE_CARS  = "CREATE TABLE "
            + TABLE_CARS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT,"+ PRICE + " INT,"+ AGENCY + " TEXT,"+COMMENTS + " TEXT,"+ PHOTO+ " TEXT);";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_CARS);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE_CARS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_CARS + "'");
        onCreate(db);
    }
    public long addCar(Car c) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(NAME, c.getName());
        values.put(PRICE, c.getPrice());
        values.put(AGENCY, c.getAgency());
        values.put(COMMENTS, c.getComments());
        values.put(PHOTO, c.getPhotoPath());

       // insert row in students table
        long insert = 0;
        try
        {
             insert  = db.insertOrThrow(TABLE_CARS, null, values);
        }
        catch(SQLException e)
        {
            // Sep 12, 2013 6:50:17 AM
            Log.e("Exception","SQLException"+String.valueOf(e.getMessage()));
            e.printStackTrace();
        }
        return insert;
    }


    public ArrayList<String> getAllCarsList() {
        ArrayList<String> carsArrayList = new ArrayList<String>();
        String carname="";
        String selectQuery = "SELECT  * FROM " + TABLE_CARS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                carname = c.getString(c.getColumnIndex(NAME));
               // adding to Students list
                carsArrayList.add(carname);
            } while (c.moveToNext());
            Log.d("array", carsArrayList.toString());
        }
        return carsArrayList;
    }


    public Car getCarDetails(String carName) {
        Car car  = new Car("",0,"","","");

        String selectQuery = "SELECT  * FROM " + TABLE_CARS + " WHERE "+NAME+" = " + "'"+carName+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            car = new Car(c.getString(c.getColumnIndex(NAME)), c.getInt(c.getColumnIndex(PRICE)), c.getString(c.getColumnIndex(AGENCY)), c.getString(c.getColumnIndex(COMMENTS)), c.getString(c.getColumnIndex(PHOTO)));
            return car;
        }
        return car;
    }
}

