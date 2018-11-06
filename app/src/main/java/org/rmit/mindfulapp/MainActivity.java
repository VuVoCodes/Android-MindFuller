package org.rmit.mindfulapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "TEST TAG";
    ArrayList<Excercise> todoArray;
    ListView todoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoArray = new ArrayList<Excercise>();
        todoListView = findViewById(R.id.todoListView);

        Excercise excercise1 = new Excercise(1,"Mindfulness breathing",15,"NEW");
        Excercise excercise2 = new Excercise(2,"Bedtime retro",15,"NEW");

        todoArray.add(excercise1);
        todoArray.add(excercise2);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                Integer excerStatusID = (Integer) Objects.requireNonNull(data.getExtras()).get("excerIdReturn");
                for (Excercise excercise:todoArray) {
                    if(excercise.getId() == excerStatusID){
                        excercise.setStatus("DONE");
                            displayExcercise();
                    }
                }
            }else if(requestCode == 2){
                Integer idIndicator = 0;
                String returnName = (String) data.getExtras().get("returnName");
                Integer returnDuration = (Integer) Objects.requireNonNull(data.getExtras()).get("returnDuration");
                for (Excercise excercise:todoArray){
                    if(excercise.id > idIndicator){
                        idIndicator = excercise.id;
                    }
                }
                idIndicator +=1;
                addExcercise(idIndicator,returnName,returnDuration);
            }else if(requestCode == 3){
                Integer recievedEditID = (Integer) Objects.requireNonNull(data.getExtras()).get("returnID");
                for (Excercise excercise:todoArray){
                    if(excercise.id == recievedEditID){
                        excercise.setSname(data.getExtras().get("returnName").toString());
                        excercise.setDuration((Integer)data.getExtras().get("returnDuration"));
                    }
                }
                displayExcercise();
            }
        }
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
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ShowUpMenuActivity showUpMenuActivity = new ShowUpMenuActivity(MainActivity.this,position);
                showUpMenuActivity.showPopup(view);
                return true;
            }
        });
    }

    public void buttonReader(View view){
        switch (view.getId()){
            case R.id.addExcercise:
                Intent intent = new Intent(this,AddEditActivity.class);
                intent.putExtra("requestType","add");
                startActivityForResult(intent,2);
        }
    }

    public void addExcercise(int id, String name, int duration){
        Excercise excercise = new Excercise(id, name,duration,"NEW");
        todoArray.add(excercise);
        displayExcercise();
    }

    public void deleteExcercise(int id){
        todoArray.remove(id);
    }

    public void editExcercise(int id){
        Intent intent = new Intent(this,AddEditActivity.class);
        intent.putExtra("sentName",todoArray.get(id).sname);
        intent.putExtra("sentDuration",todoArray.get(id).duration);
        intent.putExtra("sentID",todoArray.get(id).id);
        intent.putExtra("requestType","edit");
        startActivityForResult(intent,3);
    }



    // CLASS FOR CREATION OF POPUP MENU
    public class ShowUpMenuActivity implements PopupMenu.OnMenuItemClickListener {

        private Activity context;
        private Integer excerNum;

        public ShowUpMenuActivity(Activity context, Integer excerNum) {
            this.context = context;
            this.excerNum = excerNum;
        }

        public void showPopup(View v){
            PopupMenu popupMenu = new PopupMenu(context,v);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.popup_layout);
            popupMenu.show();
        }

        @Override public boolean onMenuItemClick(MenuItem item){
            switch (item.getItemId()){
                case R.id.delete:
                    deleteExcercise(excerNum);
                    Toast.makeText(context,"item has been deleted",Toast.LENGTH_LONG).show();
                    displayExcercise();
                    break;
                case R.id.edit:
                    editExcercise(excerNum);
                    break;
            }
            return false;
        }
    }
}

