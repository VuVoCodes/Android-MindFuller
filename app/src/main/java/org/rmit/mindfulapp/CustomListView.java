package org.rmit.mindfulapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

// CUSTOMIZATIONS OF THE ITEM IN THE LSITVIEW
public class CustomListView extends ArrayAdapter<Excercise> {

    private ArrayList<Excercise> todoArray;
    private Activity context;

    public CustomListView(@NonNull Activity context, ArrayList<Excercise>todoArray){
        super(context, R.layout.custom_cell,todoArray);
        this.context = context;
        this.todoArray = todoArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.custom_cell,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) r.getTag();
        }
        try{
            viewHolder.tName.setText(todoArray.get(position).getSname());
            viewHolder.tDuration.setText(Integer.toString(todoArray.get(position).getDuration()) + " min");
            viewHolder.tStatus.setText(todoArray.get(position).getStatus());
        }catch (Exception e){

        }
        return r;
    }

    class ViewHolder{
        TextView tName;
        TextView tDuration;
        TextView tStatus;
        ViewHolder(View v){
            tName = v.findViewById(R.id.name);
            tDuration = v.findViewById(R.id.duration);
            tStatus = v.findViewById(R.id.status);
        }
    }
}
