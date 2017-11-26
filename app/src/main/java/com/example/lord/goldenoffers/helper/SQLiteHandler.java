package com.example.lord.goldenoffers.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "id3430729_database";

    // Login table name
    private static final String TABLE_BUSINESS = "business";

    // Offers table name
    private static final String TABLE_OFFERS = "items";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_AFM = "afm";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_CREATED_AT = "created_at";

    // Offers Table Columns names
    private static final String KEY_BUSINESS_ID = "business_id";
    private static final String KEY_BUSINESS_NAME = "business_name";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_REGISTRATION_DATE = "regDate";
    private static final String KEY_EXPIRATION_DATE = "expDate";
    private static final String KEY_IMAGE = "image";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_BUSINESS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_OWNER + " TEXT," + KEY_AFM + " TEXT,"
                + KEY_LATITUDE + " TEXT," + KEY_LONGITUDE +" TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);


        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_OFFERS + "("
                + KEY_BUSINESS_ID + " TEXT," + KEY_BUSINESS_NAME + " TEXT,"
                + KEY_PRODUCT_NAME + " TEXT,"
                + KEY_PRICE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
                + KEY_REGISTRATION_DATE + " TEXT," + KEY_EXPIRATION_DATE + " TEXT,"
                + KEY_IMAGE + "LONGBLOB"
                + ")";
                db.execSQL(CREATE_ITEMS_TABLE);


        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS + TABLE_OFFERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String owner, String afm, String latitude, String longitude, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_OWNER, owner); //Owner
        values.put(KEY_AFM, afm); //AFM
        values.put(KEY_LATITUDE, latitude);
        values.put(KEY_LONGITUDE, longitude);
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_BUSINESS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    public void addOffer(String business_id, String business_name, String product_name, String regDate, String expDate, String price, String description, String image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BUSINESS_ID, business_id);
        values.put(KEY_BUSINESS_NAME, business_name);
        values.put(KEY_PRODUCT_NAME, product_name);
        values.put(KEY_REGISTRATION_DATE, regDate);
        values.put(KEY_EXPIRATION_DATE, expDate);
        values.put(KEY_PRICE, price);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_IMAGE, image);

        // Inserting Row
        long id = db.insert(TABLE_OFFERS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New offer inserted into sqlite: " + id);

    }



    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_BUSINESS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }




    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_BUSINESS, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void deleteOffers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_OFFERS, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
