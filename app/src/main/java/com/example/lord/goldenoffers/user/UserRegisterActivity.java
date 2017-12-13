package com.example.lord.goldenoffers.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.helper.InputChecker;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity {

    private static final String TAG = UserRegisterActivity.class.getSimpleName();

    private Button btnRegister;
    private EditText inputUsername, inputEmail, inputPassword, inputPasswordRepeat;

    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandlerForUsers db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        inputUsername = findViewById(R.id.username_input);
        inputEmail = findViewById(R.id.email_input);
        inputPassword = findViewById(R.id.password_input);
        inputPasswordRepeat = findViewById(R.id.password_repeat_input);
        btnRegister = findViewById(R.id.btn_register);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandlerForUsers(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(
                    UserRegisterActivity.this,
                    UserLoggedInActivity.class
            );
            startActivity(intent);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String username = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String strPassword = inputPassword.getText().toString().trim();
                String strPasswordRepeat = inputPasswordRepeat.getText().toString().trim();
                if(isInputValid(username, email, strPassword, strPasswordRepeat)) {
                    registerUser(username, email, strPassword);
                }
            }
        });
    }

    private void registerUser(final String username, final String email,final String password) {

        String tag_string_req = "req_register";

        pDialog.setMessage("Registration");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        JSONObject user = jObj.getJSONObject("user");
                        int usersDbID = user.getInt("id");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        db.addUser(usersDbID, username, email);

                        makeToast("Registration Done.\nNow Login.");
                        Intent intent = new Intent(
                                UserRegisterActivity.this,
                                UserLoginActivity.class
                        );
                        startActivity(intent);
                        finish();
                    } else {
                        String msgError = jObj.getString("error_msg");
                        if(msgError.contains(email)) clearUnvalidInput("email");
                        makeToast(msgError);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                makeToast(error.getMessage());
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean isInputValid(String username, String email, String strPassword, String strPasswordRepeat) {
        List<Object> response = InputChecker.isUserRegisterInputValid(
                username, email, strPassword, strPasswordRepeat
        );
        boolean error = (boolean) response.get(0);
        if(error) {
            String msgError = (String) response.get(1);
            String unvalidInput = (String) response.get(2);
            clearUnvalidInput(unvalidInput);
            makeToast(msgError);
            return false;
        } else return true;
    }

    private void clearUnvalidInput(String unvalidInput) {

        switch(unvalidInput) {
            case "username" :
                inputUsername.setText("");
                inputUsername.requestFocus();
                break;
            case "email" :
                inputEmail.setText("");
                inputEmail.requestFocus();
                break;
            default :
                inputPassword.setText("");
                inputPasswordRepeat.setText("");
                inputPassword.requestFocus();
        }
    }

    private void makeToast(String message) {
        Toast.makeText(
                getApplicationContext(),
                message, Toast.LENGTH_LONG
        ).show();
    }

    private void showDialog() {
        if (!pDialog.isShowing()) pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing()) pDialog.dismiss();
    }
}

