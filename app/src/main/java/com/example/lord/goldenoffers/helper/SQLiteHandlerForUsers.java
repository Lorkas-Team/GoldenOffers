package com.example.lord.goldenoffers.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SQLiteHandlerForUsers extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandlerForUsers.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "id3430729_database";

    //user table
    private static final String TABLE_USERS = "users";
    private static final String USER_KEY_ID = "id";
    private static final String USER_KEY_DB_ID = "db_id";
    private static final String USER_KEY_NAME = "username";
    private static final String USER_KEY_EMAIL = "email";

    //desires table
    private static final String TABLE_DESIRES = "desires";
    private static final String DESIRE_KEY_ID = "id";
    private static final String DESIRE_KEY_DB_ID = "db_id";
    private static final String DESIRE_KEY_PROD_NAME = "prod_name";
    private static final String DESIRE_KEY_PRICE_LOW = "price_low";
    private static final String DESIRE_KEY_PRICE_HIGH = "price_high";

    //OFFERS table
    private static final String TABLE_OFFERS = "offers";
    private static final String OFFER_KEY_ID = "id";
    private static final String OFFER_KEY_DB_ID = "db_id";
    private static final String OFFER_KEY_DESIRE_ID = "desire_id";
    private static final String OFFER_KEY_PROD_NAME = "prod_name";
    private static final String OFFER_KEY_PRICE = "price";
    private static final String OFFER_KEY_DESCRIPTION = "description";
    private static final String OFFER_KEY_IMAGE = "image";
    private static final String OFFER_KEY_REG_DATE = "reg_date";
    private static final String OFFER_KEY_EXP_DATE = "exp_date";
    private static final String OFFER_KEY_UID = "uid";
    private static final String OFFER_KEY_BUSINESS_NAME = "business_name";
    private static final String OFFER_KEY_LONGITUDE = "longitude";
    private static final String OFFER_KEY_LATITUDE = "latitude";

    public SQLiteHandlerForUsers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_KEY_ID + " INTEGER PRIMARY KEY," + USER_KEY_DB_ID + " INTEGER,"
                + USER_KEY_NAME + " TEXT," + USER_KEY_EMAIL + " TEXT UNIQUE" +")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_DESIRES_TABLE = "CREATE TABLE " + TABLE_DESIRES + "("
                + DESIRE_KEY_ID + " INTEGER PRIMARY KEY," + DESIRE_KEY_DB_ID + " INTEGER,"
                + DESIRE_KEY_PROD_NAME + " TEXT UNIQUE," + DESIRE_KEY_PRICE_LOW + " TEXT,"
                + DESIRE_KEY_PRICE_HIGH + " TEXT" +")";
        db.execSQL(CREATE_DESIRES_TABLE);

        //TODO offers FOREIGN KEY / DATES / IMAGE type
        String CREATE_OFFERS_TABLE = "CREATE TABLE " + TABLE_OFFERS + "("
                + OFFER_KEY_ID + " INTEGER PRIMARY KEY," + OFFER_KEY_DB_ID + " INTEGER,"
                + OFFER_KEY_DESIRE_ID + " INTEGER," + OFFER_KEY_PROD_NAME + " TEXT UNIQUE,"
                + OFFER_KEY_PRICE + " TEXT," + OFFER_KEY_DESCRIPTION + " TEXT,"
                + OFFER_KEY_IMAGE + " TEXT," + OFFER_KEY_REG_DATE + " TEXT," + OFFER_KEY_EXP_DATE + " TEXT,"
                + OFFER_KEY_UID + " TEXT," + OFFER_KEY_BUSINESS_NAME + " TEXT,"
                + OFFER_KEY_LONGITUDE + " TEXT," + OFFER_KEY_LATITUDE + " TEXT,"
                + "FOREIGN KEY (" + OFFER_KEY_DESIRE_ID + ") REFERENCES " + TABLE_DESIRES + "(" + DESIRE_KEY_DB_ID + ")" + ")";
        db.execSQL(CREATE_OFFERS_TABLE);

        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESIRES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESIRES);
        onCreate(db);
    }

    public void addUser(int usersID, String username, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_KEY_DB_ID, usersID);
        //id from servers database, NOT from sqlite
        values.put(USER_KEY_NAME, username);
        values.put(USER_KEY_EMAIL, email);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addDesire(int prodID, String prodName, String priceLow, String priceHigh){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESIRE_KEY_DB_ID, prodID);
        //id from servers database, NOT from sqlite
        values.put(DESIRE_KEY_PROD_NAME, prodName);
        values.put(DESIRE_KEY_PRICE_LOW, priceLow);
        values.put(DESIRE_KEY_PRICE_HIGH, priceHigh);

        long id = db.insert(TABLE_DESIRES, null, values);
        Log.d(TAG, "New desire inserted into sqlite: " + id);
        db.close();
    }

    public void addOffer(int dBID, int desireID, String uid, String prodName,
                         String strPrice, String description, String strImage,
                         String strRegDate, String strExpDate, String businessName,
                         String strLongitude, String strLatitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OFFER_KEY_DB_ID, dBID);
        //id from servers database, NOT from sqlite
        values.put(OFFER_KEY_DESIRE_ID, desireID);
        values.put(OFFER_KEY_PROD_NAME, prodName);
        values.put(OFFER_KEY_PRICE, strPrice);
        values.put(OFFER_KEY_DESCRIPTION, description);
        values.put(OFFER_KEY_IMAGE, strImage);
        values.put(OFFER_KEY_REG_DATE, strRegDate);
        values.put(OFFER_KEY_EXP_DATE, strExpDate);
        values.put(OFFER_KEY_UID, uid);
        values.put(OFFER_KEY_BUSINESS_NAME, businessName);
        values.put(OFFER_KEY_LONGITUDE, strLongitude);
        values.put(OFFER_KEY_LATITUDE, strLatitude);

        long id = db.insert(TABLE_OFFERS, null, values);
        Log.d(TAG, "New offer inserted into sqlite: " + id);
        db.close();
    }

    public User getUserDetails() {

        User user = new User();

        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int dbIdCol = cursor.getColumnIndex(USER_KEY_DB_ID);
        //id from servers database, NOT from sqlite
        int usernameCol = cursor.getColumnIndex(USER_KEY_NAME);
        int emailCol = cursor.getColumnIndex(USER_KEY_EMAIL);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            int id = cursor.getInt(dbIdCol);
            String username = cursor.getString(usernameCol);
            String email = cursor.getString(emailCol);

            user.setDbID(id);
            user.setName(username);
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

        int dbIdCol = cursor.getColumnIndex(DESIRE_KEY_DB_ID);
        //id from servers database, NOT from sqlite
        int nameCol = cursor.getColumnIndex(DESIRE_KEY_PROD_NAME);
        int priceLowCol = cursor.getColumnIndex(DESIRE_KEY_PRICE_LOW);
        int priceHighCol = cursor.getColumnIndex(DESIRE_KEY_PRICE_HIGH);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                int id = cursor.getInt(dbIdCol);
                String name = cursor.getString(nameCol);
                float priceLow = cursor.getFloat(priceLowCol);
                float priceHigh = cursor.getFloat(priceHighCol);
                Desire desire = new Desire(id, name, priceLow, priceHigh);
                listDesires.add(desire);
                Log.d(TAG, "Fetching desire from Sqlite: " + desire.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listDesires;
    }

    //TODO array offers
    public HashMap<Integer, Offer> getOffers() {

        //DesireID, Offer
        HashMap<Integer, Offer> map = new HashMap<>();

        String selectQuery = "SELECT  * FROM " + TABLE_OFFERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int dbIDCol = cursor.getColumnIndex(OFFER_KEY_DB_ID);
        //id from servers database, NOT from sqlite
        int desireIDCol = cursor.getColumnIndex(OFFER_KEY_DESIRE_ID);
        int nameCol = cursor.getColumnIndex(OFFER_KEY_PROD_NAME);
        int priceCol = cursor.getColumnIndex(OFFER_KEY_PRICE);
        int descriptionCol = cursor.getColumnIndex(OFFER_KEY_DESCRIPTION);
        int imageCol = cursor.getColumnIndex(OFFER_KEY_IMAGE);
        int regDateCol = cursor.getColumnIndex(OFFER_KEY_REG_DATE);
        int expDateCol = cursor.getColumnIndex(OFFER_KEY_EXP_DATE);
        int uidCol = cursor.getColumnIndex(OFFER_KEY_UID);
        int businessNameCol = cursor.getColumnIndex(OFFER_KEY_BUSINESS_NAME);
        int longitudeCol = cursor.getColumnIndex(OFFER_KEY_LONGITUDE);
        int latitudeCol = cursor.getColumnIndex(OFFER_KEY_LATITUDE);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                int dbID = cursor.getInt(dbIDCol);
                int desireID = cursor.getInt(desireIDCol);
                String strUID = cursor.getString(uidCol);
                String name = cursor.getString(nameCol);
                float price = cursor.getFloat(priceCol);
                String description = cursor.getString(descriptionCol);
                String strImage = cursor.getString(imageCol);
                String strRegDate = cursor.getString(regDateCol);
                String strExpDate = cursor.getString(expDateCol);
                String businessName = cursor.getString(businessNameCol);
                String strLongitude = cursor.getString(longitudeCol);
                String strLatitude = cursor.getString(latitudeCol);

                Offer newOffer = new Offer(dbID, strUID, name, price,
                        description, strImage, strRegDate, strExpDate,
                        businessName, strLongitude, strLatitude
                );

                map.put(desireID, newOffer);
                //TODO offer to string
                Log.d(TAG, "Fetching desire from Sqlite: " + newOffer.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return map;
    }

    public void deleteAll() {
        deleteUsers();
        deleteDesires();
        deleteOffers();
    }
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.close();

        Log.d(TAG, "Deleted user from sqlite");
    }
    public void deleteDesires() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DESIRES, null, null);
        db.close();
        Log.d(TAG, "Deleted all desires from sqlite");
    }
    public void deleteOffers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OFFERS, null, null);
        db.close();
        Log.d(TAG, "Deleted all offers from sqlite");
    }
}
