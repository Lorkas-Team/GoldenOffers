package com.example.lord.goldenoffers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lord.goldenoffers.business.BusinessActivity;
import com.example.lord.goldenoffers.user.UserActivity;

public class MainActivity extends AppCompatActivity {

    public Button businessButton ;
    public Button userButton;


    public void init(){
        businessButton=(Button)findViewById(R.id.businessButton);
        businessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toy = new Intent(MainActivity.this,BusinessActivity.class);
                startActivity(toy);
            }
        });

        userButton=(Button)findViewById(R.id.userButton);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent toy = new Intent(MainActivity.this,UserActivity.class);
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
