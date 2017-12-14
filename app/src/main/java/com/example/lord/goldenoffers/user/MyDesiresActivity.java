package com.example.lord.goldenoffers.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

import java.util.List;

public class MyDesiresActivity extends AppCompatActivity {

    private List<Desire> listDesires;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desire_list);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SessionManager session = new SessionManager(getApplicationContext());

        SQLiteHandlerForUsers db = new SQLiteHandlerForUsers(getApplicationContext());
        listDesires = db.getDesires();
        if(listDesires.size() > 0) {

            //creating adapter object and setting it to recyclerview
            DesireAdapter adapter = new DesireAdapter(MyDesiresActivity.this, listDesires);
            recyclerView.setAdapter(adapter);

        } else {
            makeToast("Nothing to show.");
            Intent intent = new Intent(
                    MyDesiresActivity.this,
                    UserLoggedInActivity.class
            );
            startActivity(intent);
            finish();
        }
    }

    private void makeToast(String message) {
        Toast.makeText(
                getApplicationContext(),
                message, Toast.LENGTH_LONG
        ).show();
    }

    private Desire getDesireByName(String name) {
        for(Desire desire : listDesires){
            if(name.equalsIgnoreCase(desire.getName())){
                return desire;
            }
        }
        return null;
    }
}