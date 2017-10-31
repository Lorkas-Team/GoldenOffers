package com.example.lord.goldenoffers.business;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lord.goldenoffers.R;

public class BusinessActivity extends AppCompatActivity {
    //lord branch1
    //test
    public Button loginButton;
    public Button registerButton;

    public void business_init(){
        loginButton=(Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toy = new Intent(BusinessActivity.this,LoginActivity.class);
                startActivity(toy);
            }
        });

        registerButton=(Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toy = new Intent(BusinessActivity.this,RegisterActivity.class);
                startActivity(toy);

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussines);
        business_init();

    }
}
