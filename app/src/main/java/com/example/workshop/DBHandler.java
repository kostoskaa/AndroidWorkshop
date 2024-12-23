package com.example.workshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    public static final String dbName = "Register.db";
    public DBHandler(@Nullable Context context){
        super(context, "Register.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table allusers(email TEXT primary key, password TEXT, Usertype TEXT, carType TEXT, regPlate TEXT, name TEXT, rating FLOAT)");
        db.execSQL("create Table allrides(id INTEGER primary key,driver TEXT,passenger TEXT, price INTEGER, time TEXT, date TEXT, " +
                "originLat FLOAT, originLng FLOAT, destinationLat FLOAT, destinationLng FLOAT, start TEXT, destination TEXT, foreign key(driver) references allusers(id)," +
                "foreign key(passenger) references allusers(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists allusers");
        db.execSQL("drop Table if exists allrides");
    }

    public Boolean insertData(String name, String email, String password, String Usertype, String carType, String regPlate, Float rating ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("Usertype",Usertype);
        contentValues.put("carType",carType);
        contentValues.put("regPlate",regPlate);
        contentValues.put("rating",rating);
        long res = db.insert("allusers", null, contentValues);

        if (res == -1){
            return false;
        } else{
            return true;
        }
    }

    public boolean insertRide(String driverEmail, String passengerEmail, float price, String time, String date,
                              double originLat, double originLng, double destinationLat, double destinationLng, String start, String destination) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("driver", driverEmail);
        contentValues.put("passenger", passengerEmail);
        contentValues.put("price", price);
        contentValues.put("time", time);
        contentValues.put("date", date);
        contentValues.put("originLat", originLat);
        contentValues.put("originLng", originLng);
        contentValues.put("destinationLat", destinationLat);
        contentValues.put("destinationLng", destinationLng);
        contentValues.put("start", start);
        contentValues.put("destination", destination);

        long result = db.insert("allrides", null, contentValues);

        if (result == -1){
            return false;
        } else{
            return true;
        }
    }

    public Cursor getId(String driver, String start, String destination, Integer price ){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT id FROM allrides WHERE driver = ? and start = ? and destination = ? and price = ? ",
                new String[]{driver, start, destination, String.valueOf(price) });
        return cursor;
    }

    public void updatePassenger(int rideId, String passengerEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("passenger", passengerEmail);

        // Update the row where 'id' matches the rideId
        int rowsAffected = db.update("allrides", values, "id = ?", new String[]{String.valueOf(rideId)});

        if (rowsAffected > 0) {
            Log.d("DBHandler", "Passenger column updated successfully for ride ID: " + rideId);
        } else {
            Log.d("DBHandler", "Update failed. No matching row found for ride ID: " + rideId);
        }

        db.close();
    }

    public Cursor getLatLng(String driverEmail) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(
                "SELECT originLat, originLng, destinationLat, destinationLng FROM allrides WHERE driver = ?",
                new String[]{driverEmail} // Use the driverEmail parameter
        );
        return cursor;
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from allusers where email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public Boolean checkEmailPassword(String email, String password ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from allusers where email = ? and password = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // Close cursor to free resources
        return exists;
    }

    public String checkUserType(String email, String password ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select Usertype from allusers where email = ? and password = ?", new String[]{email, password});
        String user="";
        if (cursor.moveToFirst()) {
            user = cursor.getString(0);
        }
        cursor.close(); // Close cursor to free resources
        return user;
    }

    public Cursor getData(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select name,email,rating,carType,regPlate from allusers Where email = ?", new String[]{email} );
        return cursor;
    }
    public Cursor getData1() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select email,name,rating from allusers Where Usertype = ?", new String[]{"Passenger"});
        return cursor;
    }

    public Cursor getDataForRides() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT driver, start, destination, price FROM allrides ", null);
        return cursor;
    }

    public boolean updateRating(String email, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("rating", rating);
        Log.d("DBHandler", "Updating rating for email: " + email + " with rating: " + rating);
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM allusers WHERE email=?", new String[]{email});
            if (cursor.getCount() == 0) {
                Log.e("DBHandler", "No user found with email: " + email);
                cursor.close();
                return false;
            }
            cursor.close();

            int result = db.update("allusers", contentValues, "email=?", new String[]{email});
            return result > 0;
        } catch (Exception e) {
            Log.e("DBHandler", "Error updating rating: " + e.getMessage());
            return false;
        }
    }
}