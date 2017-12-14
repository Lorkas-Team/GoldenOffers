package com.example.lord.goldenoffers.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManagerForUsers {
    private static String TAG = SessionManagerForUsers.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "GoldenOffersLoginForUsers";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManagerForUsers(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
