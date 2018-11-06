package org.rmit.mindfulapp;

import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowUpMenuActivity implements PopupMenu.OnMenuItemClickListener {

    private Activity context;
    private Integer excerNum;
    private ArrayList<Excercise> arrayList;

    public ShowUpMenuActivity(Activity context, Integer excerNum, ArrayList<Excercise> arrayList) {
        this.context = context;
        this.excerNum = excerNum;
        this.arrayList = arrayList;
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
                arrayList.remove(excerNum);
                Toast.makeText(context,"item has been deleted",Toast.LENGTH_LONG).show();
                context.recreate();
        }
        return false;
    }
}
