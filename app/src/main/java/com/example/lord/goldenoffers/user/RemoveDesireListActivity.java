package com.example.lord.goldenoffers.user;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;
import com.example.lord.goldenoffers.helper.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class RemoveDesireListActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_desire_list);

        final SQLiteHandlerForUsers db = new SQLiteHandlerForUsers(getApplicationContext());
        session = new SessionManager(getApplicationContext());


        final List<Desire> listDesires = db.getDesires();

        final String[] arrNames = new String[listDesires.size()];
        int pos = 0;
        for (Desire desire : listDesires) {
            arrNames[pos++] = desire.getName();
            listDesires.add(new Desire(pos, desire.getName(), desire.getPriceLow(), desire.getPriceHigh()));
        }

        final ListView listView = (ListView) findViewById(R.id.listView1);
        ListAdapter adapter = new RemoveDesireListRowAdapter(this, arrNames);
        listView.setAdapter(adapter);
/*
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
*/
        //Long click to delete a desire
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public void onItemLongClickListener(AdapterView<?> adapterView, View view, int pos, long l) {
                // ? ? ?
                int id = listDesires.get(pos).getDbID();
                db.deleteAdesire(id);
                listView.removeViewAt(pos);
            }

        });
    }

        //test2
    private Desire getDesireByName(List<Desire> listDesires, String name) {
        //TODO is right ?
        for (Desire desire : listDesires) {
            if (name.equalsIgnoreCase(desire.getName())) {
                return desire;
            }
        }
        return null;
    }
}