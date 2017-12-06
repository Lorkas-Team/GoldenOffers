package com.example.lord.goldenoffers.business;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.app.AppConfig;
import com.example.lord.goldenoffers.app.AppController;
import com.example.lord.goldenoffers.helper.SQLiteHandler;
import com.example.lord.goldenoffers.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddOfferActivity extends AppCompatActivity{

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText inputProductName;
    private EditText inputRegDate;
    private EditText inputExpDate;
    private DatePickerDialog datePickerDialog;
    private EditText inputPrice;
    private ImageView inputImage;
    private EditText inputDescription;
    private Button uploadOfferBtn;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private final int CODE_GALLEY_REQUEST = 999;
    private Bitmap bitmap;
    private String selectedItem1;
    private String selectedItem2;
    private String selectedItem3;
    private String selectedItem4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        inputProductName = (EditText) findViewById(R.id.etProductName);
        inputRegDate = (EditText) findViewById(R.id.etRegDate);
        inputExpDate = (EditText) findViewById(R.id.etExpDate);
        inputPrice = (EditText) findViewById(R.id.etPrice);
        inputImage = (ImageView) findViewById(R.id.imageView);
        inputDescription = (EditText) findViewById(R.id.etDescription);
        uploadOfferBtn = (Button) findViewById(R.id.uploadBtn);
        spinner1 = (Spinner) findViewById(R.id.spin1);
        spinner2 = (Spinner) findViewById(R.id.spin2);
        spinner3 = (Spinner) findViewById(R.id.spin3);
        spinner4 = (Spinner) findViewById(R.id.spin4);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        final String business_id = user.get("uid");
        final String business_name = user.get("name");



        //Spinners Handling

        Resources res = getResources();
        final String[] spinn1 = res.getStringArray(R.array.spin1);
        final String[] spinn2_1 = res.getStringArray(R.array.spin2_1);
        final String[] spinn2_2 = res.getStringArray(R.array.spin2_2);
        final String[] spinn2_3 = res.getStringArray(R.array.spin2_3);
        final String[] spinn2_4 = res.getStringArray(R.array.spin2_4);
        final String[] spinn2_5 = res.getStringArray(R.array.spin2_5);
        final String[] spinn2_6 = res.getStringArray(R.array.spin2_6);
        final String[] spinn2_7 = res.getStringArray(R.array.spin2_7);
        final String[] spinn2_8 = res.getStringArray(R.array.spin2_8);
        final String[] spinn3_1_2 = res.getStringArray(R.array.spin3_1_2);



        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.spin1, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem1 = parent.getItemAtPosition(position).toString();
                setSelectedItem1(selectedItem1);

                if(selectedItem1.equals(spinn1[0])){

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(spinner2.getContext(),
                            R.array.spin2_1, android.R.layout.simple_spinner_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedItem2 = parent.getItemAtPosition(position).toString();
                            setSelectedItem2(selectedItem2);

                            if(selectedItem2.equals(spinn2_1[0])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_1_1, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_1[1])){

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_1_2, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                        if(selectedItem3.equals(spinn3_1_2[0])) {

                                            spinner4.setEnabled(true);

                                            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(spinner4.getContext(),
                                                    R.array.spin4_2_1, android.R.layout.simple_spinner_item);

                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                            spinner4.setAdapter(adapter4);

                                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                    String selectedItem4 = parent.getItemAtPosition(position).toString();
                                                    setSelectedItem4(selectedItem4);

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });

                                        }else if(selectedItem3.equals(spinn3_1_2[1])){

                                            spinner4.setEnabled(true);

                                            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(spinner4.getContext(),
                                                    R.array.spin4_2_2, android.R.layout.simple_spinner_item);

                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                            spinner4.setAdapter(adapter4);

                                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                    String selectedItem4 = parent.getItemAtPosition(position).toString();

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });

                                        }else if(selectedItem3.equals(spinn3_1_2[3])){

                                            spinner4.setEnabled(true);

                                            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(spinner4.getContext(),
                                                    R.array.spin4_2_4, android.R.layout.simple_spinner_item);

                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                            spinner4.setAdapter(adapter4);

                                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                    String selectedItem4 = parent.getItemAtPosition(position).toString();

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });

                                        }else if(selectedItem3.equals(spinn3_1_2[4])){

                                            spinner4.setEnabled(true);

                                            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(spinner4.getContext(),
                                                    R.array.spin4_2_5, android.R.layout.simple_spinner_item);

                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                            spinner4.setAdapter(adapter4);

                                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                    String selectedItem4 = parent.getItemAtPosition(position).toString();

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });

                                        }else if(selectedItem3.equals(spinn3_1_2[5])){

                                            spinner4.setEnabled(true);

                                            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(spinner4.getContext(),
                                                    R.array.spin4_2_6, android.R.layout.simple_spinner_item);

                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                            spinner4.setAdapter(adapter4);

                                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                    String selectedItem4 = parent.getItemAtPosition(position).toString();

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });

                                        }else if(selectedItem3.equals(spinn3_1_2[7])){

                                            spinner4.setEnabled(true);

                                            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(spinner4.getContext(),
                                                    R.array.spin4_2_8, android.R.layout.simple_spinner_item);

                                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                            spinner4.setAdapter(adapter4);

                                            spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                    String selectedItem4 = parent.getItemAtPosition(position).toString();

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });

                                        }else {
                                            spinner4.setEnabled(false);
                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {


                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_1[2])){

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_1_3, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_1[3])){

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_1_4, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_1[4])){

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_1_5, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_1[5])){

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_1_6, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_1[6])){

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_1_7, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_1[7])){

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_1_8, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Toast.makeText(getApplicationContext(),
                                    "You must select Categories and Subcategories", Toast.LENGTH_LONG)
                                    .show();

                        }
                    });



                }else if(selectedItem1.equals(spinn1[1])){

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(spinner2.getContext(),
                            R.array.spin2_2, android.R.layout.simple_spinner_dropdown_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedItem2 = parent.getItemAtPosition(position).toString();
                            setSelectedItem2(selectedItem2);

                            if(selectedItem2.equals(spinn2_2[0])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_1, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[1])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_2, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[2])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_3, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[3])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_4, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[4])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_5, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }
                            else if(selectedItem2.equals(spinn2_2[5])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_6, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[6])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_7, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[7])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_8, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[8])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_9, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[9])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_10, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else if(selectedItem2.equals(spinn2_2[10])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_2_11, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                                spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        String selectedItem3 = parent.getItemAtPosition(position).toString();
                                        setSelectedItem3(selectedItem3);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }else if(selectedItem1.equals(spinn1[2])){

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(spinner2.getContext(),
                            R.array.spin2_3, android.R.layout.simple_spinner_dropdown_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedItem2 = parent.getItemAtPosition(position).toString();
                            setSelectedItem2(selectedItem2);

                            if(selectedItem2.equals(spinn2_3[0])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_3_1, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_3[1])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_3_2, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_3[2])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_3_3, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_3[3])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_3_4, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_3[4])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_3_5, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_3[5])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_3_6, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else if(selectedItem1.equals(spinn1[3])){

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(spinner2.getContext(),
                            R.array.spin2_4, android.R.layout.simple_spinner_dropdown_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedItem2 = parent.getItemAtPosition(position).toString();
                            setSelectedItem2(selectedItem2);

                            if(selectedItem2.equals(spinn2_4[0])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_4_1, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_4[1])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_4_2, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_4[2])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_4_3, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_4[3])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_4_4, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_4[4])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_4_5, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_4[5])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_4_6, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_4[6])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_4_7, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else if(selectedItem1.equals(spinn1[4])){

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(spinner2.getContext(),
                            R.array.spin2_5, android.R.layout.simple_spinner_dropdown_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedItem2 = parent.getItemAtPosition(position).toString();
                            setSelectedItem2(selectedItem2);

                            if(selectedItem2.equals(spinn2_5[0])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_1, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[1])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_2, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[2])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_3, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[3])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_4, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[4])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_5, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[5])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_6, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[6])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_7, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[7])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_8, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[8])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_9, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_5[9])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_5_10, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else if(selectedItem1.equals(spinn1[5])){

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(spinner2.getContext(),
                            R.array.spin2_6, android.R.layout.simple_spinner_dropdown_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedItem2 = parent.getItemAtPosition(position).toString();
                            setSelectedItem2(selectedItem2);

                            if(selectedItem2.equals(spinn2_6[0])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_6_1, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_6[1])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_6_2, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_6[2])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_6_3, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else if(selectedItem1.equals(spinn1[6])){

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(spinner2.getContext(),
                            R.array.spin2_7, android.R.layout.simple_spinner_dropdown_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedItem2 = parent.getItemAtPosition(position).toString();
                            setSelectedItem2(selectedItem2);

                            if(selectedItem2.equals(spinn2_7[0])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_7_1, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_7[1])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_7_2, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_7[2])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_7_3, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_7[3])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_7_4, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_7[4])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_7_5, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_7[5])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_7_6, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else if(selectedItem1.equals(spinn1[7])){

                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(spinner2.getContext(),
                            R.array.spin2_8, android.R.layout.simple_spinner_dropdown_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            String selectedItem2 = parent.getItemAtPosition(position).toString();
                            setSelectedItem2(selectedItem2);

                            if(selectedItem2.equals(spinn2_8[0])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_8_1, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_8[1])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_8_2, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_8[2])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_8_3, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_8[3])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_8_4, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }else if(selectedItem2.equals(spinn2_8[4])) {

                                ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(spinner3.getContext(),
                                        R.array.spin3_8_5, android.R.layout.simple_spinner_item);

                                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner3.setAdapter(adapter3);

                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),
                        "You must select Categories and Subcategories", Toast.LENGTH_LONG)
                        .show();

            }
        });



        inputRegDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c1 = Calendar.getInstance();
                int mYear = c1.get(Calendar.YEAR); //current year
                int mMonth = c1.get(Calendar.MONTH); // current month
                int mDay = c1.get(Calendar.DAY_OF_MONTH); //current day

                datePickerDialog = new DatePickerDialog(AddOfferActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        inputRegDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });

        inputExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c2 = Calendar.getInstance();
                int mYear = c2.get(Calendar.YEAR); //current year
                int mMonth = c2.get(Calendar.MONTH); // current month
                int mDay = c2.get(Calendar.DAY_OF_MONTH); //current day

                datePickerDialog = new DatePickerDialog(AddOfferActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        inputExpDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();

            }
        });


        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddOfferActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLEY_REQUEST
                );

            }
        });



        uploadOfferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_name = inputProductName.getText().toString().trim();
                String regDate = inputRegDate.getText().toString().trim();
                String expDate = inputExpDate.getText().toString().trim();
                String price = inputPrice.getText().toString().trim();
                String description = inputDescription.getText().toString().trim();


                    if(!product_name.isEmpty() && !regDate.isEmpty() && !expDate.isEmpty() && !price.isEmpty() && bitmap != null){

                        String image = imageToString(bitmap);

                        offerUpload(business_id, business_name, product_name, regDate, expDate, price, description, image, selectedItem1, selectedItem2, selectedItem3, selectedItem4);

                    }else {
                        Toast.makeText(getApplicationContext(),
                                "You must fill in all fields with  *  ", Toast.LENGTH_LONG)
                                .show();
                    }


            }
        });

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLEY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLEY_REQUEST);
            } else {
                Toast.makeText(getApplicationContext(),
                        "You don't have permission", Toast.LENGTH_LONG)
                        .show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CODE_GALLEY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void offerUpload(final String business_id, final String business_name, final String product_name, final String regDate,
                             final String expDate, final String price, final String description, final String image, final String selectedItem1,
                             final String selectedItem2, final String selectedItem3, final String selectedItem4) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Uploading Offer ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_OFFER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Add Offer Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // Offer successfully stored in MySQL
                        // Now store the offer in sqlite

                        JSONObject offers = jObj.getJSONObject("offers");
                        String business_id = offers.getString("business_id");
                        String business_name = offers.getString("business_name");
                        String product_name = offers.getString("product_name");
                        String regDate = offers.getString("regDate");
                        String expDate = offers.getString("expDate");
                        String price = offers.getString("price");
                        String description = offers.getString( "description");
                        String image = offers.getString("image");
                        String selectedItem1 = offers.getString("selectedItem1");
                        String selectedItem2 = offers.getString("selectedItem2");
                        String selectedItem3 = offers.getString("selectedItem3");
                        String selectedItem4 = offers.getString("selectedItem4");


                        // Inserting row in users table
                        db.addOffer(business_id, business_name, product_name, regDate, expDate, price, description, image,
                                selectedItem1, selectedItem2, selectedItem3, selectedItem4);

                        Toast.makeText(getApplicationContext(), "Offer successfully added!", Toast.LENGTH_LONG).show();

                        // Launch logged in activity
                        Intent intent = new Intent(
                                AddOfferActivity.this,
                                LoggedInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Upload Offer Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();

                params.put("business_id", business_id);
                params.put("business_name", business_name);
                params.put("product_name", product_name);
                params.put("regDate", regDate);
                params.put("expDate", expDate);
                params.put("price", price);
                params.put("description", description);
                params.put("image", image);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void setSelectedItem1(String selectedItem1) {
        this.selectedItem1 = selectedItem1;
    }

    public void setSelectedItem2(String selectedItem2) {
        this.selectedItem2 = selectedItem2;
    }

    public void setSelectedItem3(String selectedItem3) {
        this.selectedItem3 = selectedItem3;
    }

    public void setSelectedItem4(String selectedItem4) {
        this.selectedItem4 = selectedItem4;
    }
}
