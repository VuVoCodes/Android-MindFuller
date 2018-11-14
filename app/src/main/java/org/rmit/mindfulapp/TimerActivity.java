package org.rmit.mindfulapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private TextView timeView;
    private Button controlButton;
    private Button endButton;
    private CountDownTimer countDownTimer;
    private long timeRemainMilliSec;
    private long initalTime;
    private boolean isRunning;
    private static final String TAG ="tag";
    private int excerID;
    private int excerDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timeView = findViewById(R.id.timeView);
        controlButton = findViewById(R.id.controlButton);
        endButton = findViewById(R.id.endButton);

        Intent intent = getIntent();
        excerDuration = (Integer) intent.getExtras().get("duration");
        excerID = (Integer) intent.getExtras().get("excerID");
        Log.d(TAG, String.valueOf(excerDuration));
        timeRemainMilliSec = excerDuration * 60 * 1000;
        initalTime = excerDuration * 60 * 1000;
        updateCounter();
    }

    public void startStop(View view){
        if(isRunning){
            stopCount();
        }else{
            startCount();
        }
    }

    public void startCount(){
        countDownTimer = new CountDownTimer(timeRemainMilliSec, 1000) {
            @Override
            public void onTick(long l) {
                timeRemainMilliSec = l;
                updateCounter();
            }
            @Override
            public void onFinish() {
                final AlertDialog alertDialog = new AlertDialog.Builder(TimerActivity.this).create();
                alertDialog.setTitle("You have completed the excercise !");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        returnStatus();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NAH I WANNA REDO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeRemainMilliSec = initalTime;
                        startCount();
                    }
                });
                alertDialog.show();
            }
        }.start();
        isRunning = true;
        controlButton.setText("PAUSE");
    }

    public void stopCount(){
        countDownTimer.cancel();
        controlButton.setText("START");
        Log.d(TAG, "stopCount: " + timeRemainMilliSec);
        isRunning = false;
    }

    public void updateCounter(){
        int hour = (int) (timeRemainMilliSec  / (1000*60*60)) % 24;
        int minute = (int) (timeRemainMilliSec / (1000*60)) % 60;
        int second = (int) (timeRemainMilliSec / 1000 % 60);

        String timeFormat = String.format(Locale.getDefault(),"%02d:%02d:%02d",hour,minute,second);
        timeView.setText(timeFormat);
    }

    public void returnStatus(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("statusReturn",initalTime);
        intent.putExtra("excerIdReturn",excerID);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void endTimer(View view){

        final AlertDialog alertDialog = new AlertDialog.Builder(TimerActivity.this).create();
        alertDialog.setTitle("Are u sure u wanna exit ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(TimerActivity.this,MainActivity.class);
                intent.putExtra("excerIdReturn",excerID);
                initalTime -= timeRemainMilliSec;
                intent.putExtra("statusReturn",initalTime);
                Log.d(TAG, "endTimer: " + timeRemainMilliSec);
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NAH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
