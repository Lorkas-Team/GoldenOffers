package com.example.lord.goldenoffers.user;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.lord.goldenoffers.R;
import com.example.lord.goldenoffers.helper.Desire;
import com.example.lord.goldenoffers.helper.SQLiteHandlerForUsers;

import java.util.ArrayList;
import java.util.List;

public class DesireListActivity extends AppCompatActivity {

    private SQLiteHandlerForUsers db;
    private List<Desire> listDesires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desire_list);

        db = new SQLiteHandlerForUsers(getApplicationContext());
        listDesires = new ArrayList<>();
        listDesires = db.getDesires();
        String[] arrNames = new String[listDesires.size()];
        int pos = 0;
        for(Desire desire : listDesires) {
            arrNames[pos++] = desire.getName();
        }

        ListAdapter theAdapter = new MyAdapter(this, arrNames);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(theAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String pickedName = String.valueOf(adapterView.getItemAtPosition(pos));
                Desire pickedDesire = getDesireByName(pickedName);
                if(pickedDesire != null) {
                    //do stuff
                } else {
                    //error
                }
            }
        });
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