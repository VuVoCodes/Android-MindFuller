package org.rmit.mindfulapp;

import android.content.Context;
import android.os.Environment;

import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class JsonTool {

    ArrayList<Excercise> arrayList;


    public JSONObject saveFile(int id, String name, int duration, String status){
        JSONObject object = new JSONObject();
        try{
            object.put("id",Integer.valueOf(id));
            object.put("name",name);
            object.put("duration",Integer.valueOf(duration));
            object.put("status",status);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return object;
    }

    public ArrayList<Excercise> readFile(){
        File emptyFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"MindFuller.txt");
        ArrayList<Excercise> recievedArray = new ArrayList<>();

        if(emptyFile.exists()){
            Log.d(TAG, "readFile: existed --------------------------------");
            try {
                Scanner s = new Scanner(new File(String.valueOf(emptyFile)));
                while (s.hasNext()) {
                    JSONObject obj = new JSONObject(s.nextLine());
                    Log.d(TAG, obj.toString());
                    Excercise excercise = new Excercise(Integer.parseInt(obj.get("id").toString())
                                                        ,obj.get("name").toString()
                                                        ,Integer.parseInt(obj.get("duration").toString())
                                                        ,obj.get("status").toString());
                    recievedArray.add(excercise);
                }
                s.close();
            } catch (FileNotFoundException | JSONException e) {
                e.printStackTrace();
            }
        }else {
            Log.d(TAG, "readFile: not exist");
        }
        return recievedArray;
    }
}
