package com.example.lord.goldenoffers.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lord.goldenoffers.user.Desire;
import com.example.lord.goldenoffers.user.User;

import java.util.ArrayList;
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESIRES);
        onCreate(db);
    }

    public void addUser(int usersID, String username, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_KEY_ID, usersID);
        values.put(USER_KEY_NAME, username);
        values.put(USER_KEY_EMAIL, email);

        long id = db.insert(TABLE_USERS, null, values);
        db.close();

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addDesire(int prodID, String prodName, String priceLow, String priceHigh){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESIRE_KEY_ID, prodID);
        values.put(DESIRE_KEY_PROD_NAME, prodName);
        values.put(DESIRE_KEY_PRICE_LOW, priceLow);
        values.put(DESIRE_KEY_PRICE_HIGH, priceHigh);

        long id = db.insert(TABLE_DESIRES, null, values);
        Log.d(TAG, "New desire inserted into sqlite: " + id);
        db.close();
    }

    /*
    public int getUsersID() {

        int id;

        String selectQuery = "SELECT " + USER_KEY_ID + " FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            id = cursor.getInt(0);
        } else {
            id = -1;
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Getting users id from Sqlite: " + id);
        return id;
    }
    */

    public User getUserDetails() {

        User user = new User();

        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int idCol = cursor.getColumnIndex(USER_KEY_ID);
        int usernameCol = cursor.getColumnIndex(USER_KEY_NAME);
        int emailCol = cursor.getColumnIndex(USER_KEY_EMAIL);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            int id = cursor.getInt(idCol);
            String username = cursor.getString(usernameCol);
            String email = cursor.getString(emailCol);

            user.setId(id);
            user.setUsername(username);
            user.setEmail(email);
        }
        cursor.close();
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return user;
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
                int id = cursor.getInt(idCol);
                String name = cursor.getString(nameCol);
                float priceLow = cursor.getFloat(priceLowCol);
                float priceHigh = cursor.getInt(priceHighCol);
                Desire desire = new Desire(id, name, priceLow, priceHigh);
                listDesires.add(desire);
                Log.d(TAG, "Fetching desire from Sqlite: " + desire.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listDesires;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USERS, null, null);
        db.close();

        Log.d(TAG, "Deleted user from sqlite");
    }

    public void deleteDesires() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_DESIRES, null, null);
        db.close();
        Log.d(TAG, "Deleted all desires from sqlite");
    }
}
