package com.example.lord.goldenoffers.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;


public class SQLiteHandlerForUsers extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandlerForUsers.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "id3430729_database";


    //Login table name for user
    private static final String TABLE_USERS = "users";




    // LOGIN TABLE COLUMNS NAMES FOR USERS
    private static final String USER_KEY_ID = "id";
    private static final String USER_KEY_NAME = "username";
    private static final String USER_KEY_EMAIL = "email";


    public SQLiteHandlerForUsers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_KEY_ID + " INTEGER PRIMARY KEY," + USER_KEY_NAME + " TEXT,"
                + USER_KEY_EMAIL + " TEXT UNIQUE" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database for business
     * */


    public void addUser(String username, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_KEY_NAME, username); // Name
        values.put(USER_KEY_EMAIL, email); // Email


        // Inserting Row
        long id = db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("username", cursor.getString(1));
            user.put("email", cursor.getString(2));

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
        db.delete(TABLE_USERS, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
