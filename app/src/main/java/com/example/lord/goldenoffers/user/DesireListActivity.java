package com.example.lord.goldenoffers.user;
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

import java.util.ArrayList;
import java.util.List;

public class DesireListActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desire_list);

        // Session manager
        session = new SessionManager(getApplicationContext());

        final List<Desire> listDesires = UserLoggedInActivity.USER.getDesires();

        String[] arrNames = new String[listDesires.size()];
        int pos = 0;
        for(Desire desire : listDesires) {
            arrNames[pos++] = desire.getName();
        }

        ListView listView = (ListView) findViewById(R.id.listView1);
        ListAdapter adapter = new DesireListRowAdapter(this, arrNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String pickedName = String.valueOf(adapterView.getItemAtPosition(pos));
                Desire pickedDesire = getDesireByName(listDesires, pickedName);
                if(pickedDesire != null) {
                    //TODO do stuff
                    String msg = "Selected item -> " + pickedName;
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    //TODO error
                }
            }
        });
    }

    private Desire getDesireByName(List<Desire> listDesires, String name) {
        //TODO is right ?
        for(Desire desire : listDesires){
            if(name.equalsIgnoreCase(desire.getName())){
                return desire;
            }
        }
        return null;
    }
}