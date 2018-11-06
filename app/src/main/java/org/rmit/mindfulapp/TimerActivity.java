package org.rmit.mindfulapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private TextView timeView;
    private Button controlButton;
    private CountDownTimer countDownTimer;
    private long timeRemainMilliSec;
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

        Intent intent = getIntent();
        excerDuration = (Integer) intent.getExtras().get("duration");
        excerID = (Integer) intent.getExtras().get("excerID");
        Log.d(TAG, String.valueOf(excerDuration));
        timeRemainMilliSec = excerDuration * 60 * 1000; //15 minute
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
                returnStatus();
            }
        }.start();
        isRunning = true;
        controlButton.setText("PAUSE");
    }

    public void stopCount(){
        countDownTimer.cancel();
        controlButton.setText("START");
        isRunning = false;
    }

    public void updateCounter(){
        int minute = (int) (timeRemainMilliSec / 1000) / 60;
        int second = (int) (timeRemainMilliSec / 1000 % 60);

        String timeFormat = String.format(Locale.getDefault(),"%02d:%02d",minute,second);
        timeView.setText(timeFormat);
    }

    public void returnStatus(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("statusReturn","done");
        intent.putExtra("excerIdReturn",excerID);
        setResult(RESULT_OK,intent);
        finish();
    }
}
