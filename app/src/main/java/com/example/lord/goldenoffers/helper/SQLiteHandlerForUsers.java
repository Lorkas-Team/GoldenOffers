package com.example.lord.goldenoffers.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lord.goldenoffers.user.Desire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SQLiteHandlerForUsers extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandlerForUsers.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "id3430729_database";

    //user table
    private static final String TABLE_USERS = "users";
    private static final String USER_KEY_ID = "user_id";
    private static final String USER_KEY_NAME = "username";
    private static final String USER_KEY_EMAIL = "email";

    //desires table
    private static final String TABLE_DESIRES = "desires";
    private static final String DESIRE_KEY_ID = "desire_id";
    private static final String DESIRE_KEY_PROD_NAME = "prod_name";
    private static final String DESIRE_KEY_PRICE_LOW = "price_low";
    private static final String DESIRE_KEY_PRICE_HIGH = "price_high";

    //TODO onDestroy close db

    public SQLiteHandlerForUsers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_KEY_ID + " INTEGER PRIMARY KEY," + USER_KEY_NAME + " TEXT,"
                + USER_KEY_EMAIL + " TEXT UNIQUE" +")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_DESIRES_TABLE = "CREATE TABLE " + TABLE_DESIRES + "("
                + DESIRE_KEY_ID + " INTEGER PRIMARY KEY," + DESIRE_KEY_PROD_NAME + " TEXT UNIQUE,"
                + DESIRE_KEY_PRICE_LOW + " TEXT," + DESIRE_KEY_PRICE_HIGH + " TEXT" +")";
        db.execSQL(CREATE_DESIRES_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESIRES);

        // Create tables again
        onCreate(db);
    }

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

    public String getUsersEmail() {

        String email;

        String selectQuery = "SELECT " + USER_KEY_EMAIL + " FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            email = cursor.getString(0);
        } else {
            email = "";
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Getting users email from Sqlite: " + email);
        return email;
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int usernameCol = cursor.getColumnIndex(USER_KEY_NAME);
        int emailCol = cursor.getColumnIndex(USER_KEY_EMAIL);

        // Move to first row
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
                user.put("username", cursor.getString(usernameCol));
                user.put("email", cursor.getString(emailCol));
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USERS, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void addDesire(int prodID, String prodName, String priceLow, String priceHigh){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESIRE_KEY_ID, prodID);
        values.put(DESIRE_KEY_PROD_NAME, prodName);
        values.put(DESIRE_KEY_PRICE_LOW, priceLow);
        values.put(DESIRE_KEY_PRICE_HIGH, priceHigh);
        // Inserting Row
        long id = db.insert(TABLE_DESIRES, null, values);
        Log.d(TAG, "New desire inserted into sqlite: " + id);
        db.close(); // Closing database connection
    }







    public List<Desire> getDesires() {

        List<Desire> listDesires = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_DESIRES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int idCol = cursor.getColumnIndex(DESIRE_KEY_ID);
        int nameCol = cursor.getColumnIndex(DESIRE_KEY_PROD_NAME);
        int priceLowCol = cursor.getColumnIndex(DESIRE_KEY_PRICE_LOW);
        int priceHighCol = cursor.getColumnIndex(DESIRE_KEY_PRICE_HIGH);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                listDesires.add(
                        new Desire(
                                cursor.getInt(idCol),
                                cursor.getString(nameCol),
                                cursor.getFloat(priceLowCol),
                                cursor.getInt(priceHighCol)
                        )
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + listDesires.toString());
        return listDesires;
    }

    public void deleteDesires() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_DESIRES, null, null);
        db.close();
        Log.d(TAG, "Deleted all desires from sqlite");
    }

}
