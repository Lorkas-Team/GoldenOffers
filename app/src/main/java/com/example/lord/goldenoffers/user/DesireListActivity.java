package com.example.lord.goldenoffers.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

import java.util.List;

public class DesireListActivity extends AppCompatActivity {

    private List<Desire> listDesires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desire_list);

        SessionManager session = new SessionManager(getApplicationContext());

        SQLiteHandlerForUsers db = new SQLiteHandlerForUsers(getApplicationContext());
        listDesires = db.getDesires();
        if(listDesires.size() > 0) {
            int pos = 0;
            String[] arrNames = new String[listDesires.size()];
            for (Desire desire : listDesires) {
                arrNames[pos++] = desire.getName();
            }

            ListView listView = findViewById(R.id.listView1);
            ListAdapter adapter = new DesireListRowAdapter(this, arrNames);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    String pickedName = String.valueOf(adapterView.getItemAtPosition(pos));
                    Desire pickedDesire = getDesireByName(pickedName);
                    if (pickedDesire != null) {
                        makeToast("Selected item : \n" + pickedDesire.toString());
                    }
                }
            });
        } else {
            makeToast("Nothing to show.");
            Intent intent = new Intent(
                    DesireListActivity.this,
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