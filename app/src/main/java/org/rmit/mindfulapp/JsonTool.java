package org.rmit.mindfulapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class JsonTool {

    ArrayList<Excercise> arrayList;
    Activity context;

    // SAVE THE JSON FILE
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

    // READ JSON FILE
    @RequiresApi(api = Build.VERSION_CODES.M)
    public ArrayList<Excercise> readFile(){
        if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return getJosonFile();
        }else{
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            ArrayList<Excercise>excercises = new ArrayList<Excercise>();
            Excercise defaultExcercise1 = new Excercise(1,"Mindfulness breathing",15,"NEW");
            Excercise defaultExcercise2 = new Excercise(2,"Bedtime retrospection",15,"NEW");
            excercises.add(defaultExcercise1);
            excercises.add(defaultExcercise2);
            return excercises;
        }
    }

    // FIND THE JSON FILE FROM THE EXTERNAL STORAGE OF THE MACHING
    public ArrayList<Excercise> getJosonFile(){
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
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"MindFuller.txt");
            try {
                Writer output;
                output = new BufferedWriter(new FileWriter(String.valueOf(file)));
                output.close();
                Scanner s = new Scanner(new File(String.valueOf(file)));
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
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
        return recievedArray;
    }

}
