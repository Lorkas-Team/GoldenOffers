package com.example.lord.goldenoffers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.lord.goldenoffers.business.BusinessLoginActivity;
import com.example.lord.goldenoffers.user_activities.UserLoginActivity;

public class MainActivity extends AppCompatActivity {

    public void init(){
        Button businessButton = findViewById(R.id.businessButton);
        Button userButton = findViewById(R.id.userButton);
        businessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(
                        MainActivity.this,
                        BusinessLoginActivity.class
                );
                startActivity(toy);
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(
                        MainActivity.this,
                        UserLoginActivity.class
                );
                startActivity(toy);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}
