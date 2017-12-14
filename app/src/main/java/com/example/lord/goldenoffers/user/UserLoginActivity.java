package com.example.lord.goldenoffers.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.lord.goldenoffers.helper.SessionManagerForUsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLoginActivity extends AppCompatActivity {

    private static final String TAG = UserRegisterActivity.class.getSimpleName();

    private Button btnLogin;
    private TextView tvRegister;
    private EditText inputEmail, inputPassword;

    private ProgressDialog pDialog;
    private SessionManagerForUsers session;
    private SQLiteHandlerForUsers db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        inputEmail = findViewById(R.id.email_input);
        inputPassword = findViewById(R.id.password_input);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandlerForUsers(getApplicationContext());
        session = new SessionManagerForUsers(getApplicationContext());

        if (session.isLoggedIn()) {
            Intent intent = new Intent(
                    UserLoginActivity.this,
                    UserLoggedInActivity.class
            );
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(isInputValid(email, password)) {
                    doLogin(email, password);
                }
            }

        });

        tvRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        UserRegisterActivity.class
                );
                startActivity(intent);
                finish();
            }
        });
    }

    private void doLogin(final String email, final String password) {

        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.USER_URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        session.setLogin(true);

                        JSONObject user = jObj.getJSONObject("user");
                        int usersDbID = user.getInt("id");
                        String username = user.getString("username");
                        String email = user.getString("email");
                        db.addUser(usersDbID, username, email);

                        Intent intent = new Intent(
                                UserLoginActivity.this,
                                UserLoggedInActivity.class
                        );
                        startActivity(intent);
                        finish();
                    } else {
                        clearUnvalidInput("both");
                        makeToast(jObj.getString("error_msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    makeToast("JSon Error : " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                makeToast(error.getMessage());
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean isInputValid(String email, String strPassword) {
        List<Object> response = InputChecker.isLoginInputValid(email, strPassword);
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

        if (unvalidInput.equalsIgnoreCase("email")) {
            inputEmail.setText("");
            inputEmail.requestFocus();
        } else if(unvalidInput.equalsIgnoreCase("password")){
            inputPassword.setText("");
            inputPassword.requestFocus();
        } else {
            inputEmail.setText("");
            inputEmail.requestFocus();
            inputPassword.setText("");
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