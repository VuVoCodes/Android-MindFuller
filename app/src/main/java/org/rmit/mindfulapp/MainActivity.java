package org.rmit.mindfulapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TEST TAG";
    ArrayList<Excercise> todoArray;
    ListView todoListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoArray = new ArrayList<Excercise>();
        todoListView = findViewById(R.id.todoListView);

        Excercise excercise1 = new Excercise(1,"Mindfulness breathing",0,"new");
        Excercise excercise2 = new Excercise(2,"Bedtime retro",15,"new");
        Excercise excercise3 = new Excercise(3,"Bedtime retrospection",15,"new");

        todoArray.add(excercise1);
        todoArray.add(excercise2);
        todoArray.add(excercise3);

        displayExcercise();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this,"Pause",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this,"Resume",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(MainActivity.this,"Destroy",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(MainActivity.this,"Restart",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(MainActivity.this,"Start",Toast.LENGTH_LONG).show();

    }

    public void timerView(View view, ArrayList<Excercise> arrayList, Integer position) {
        Intent intent = new Intent(this,TimerActivity.class);
        intent.putExtra("duration",arrayList.get(position).duration);
        intent.putExtra("excerID", arrayList.get(position).id);
        startActivityForResult(intent,1);
    }

    public void displayExcercise(){
        CustomListView customListView = new CustomListView(this, todoArray);
        todoListView.setAdapter(customListView);
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               timerView(view,todoArray,position);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                Integer excerStatusID = (Integer) Objects.requireNonNull(data.getExtras()).get("excerIdReturn");
                for (Excercise excercise:todoArray) {
                    if(excercise.getId() == excerStatusID){
                        excercise.setStatus("Done");
                            displayExcercise();
                    }
                }
            }
        }
    }
}

