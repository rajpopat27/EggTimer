package com.example.raj.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView textView;
    Button button;
    int min,sec;
    String tempSec;
    boolean timerState=false;
    CountDownTimer timer;
    public void resetTimer(){
        button.setText("Start");
        seekBar.setProgress(0);
        seekBar.setEnabled(true);
        textView.setText("0:00");
        timerState=false;
    }
    public void updateTextView(int time){
        min = time/ 60;
        sec = time-min*60;
        tempSec = String.valueOf(sec);
        if (sec<=9) {
            tempSec =("0"+sec);
        }

        textView.setText(Integer.toString(min)+ ":" + tempSec);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar= (SeekBar) findViewById(R.id.seekBar);
        textView= (TextView) findViewById(R.id.textView);
        button= (Button) findViewById(R.id.button);
        seekBar.setMax(600);
        seekBar.setProgress(0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTextView(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerState) {
                    timerState = true;
                    button.setText("Stop");
                    seekBar.setEnabled(false);
                     timer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                        @Override
                        public void onTick(long secsLeft) {
                            updateTextView((int) secsLeft / 1000);
                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(MainActivity.this, "Timer Completed", Toast.LENGTH_SHORT).show();
                            final MediaPlayer mediaPlayer =  MediaPlayer.create(getApplicationContext(),R.raw.alarm);
                            mediaPlayer.start();
                           new CountDownTimer(5000,1000) {
                               @Override
                               public void onTick(long l) {

                               }

                               @Override
                               public void onFinish() {
                                    mediaPlayer.stop();
                               }
                           }.start();
                            resetTimer();
                        }
                    };
                    timer.start();
                }
                else if(timerState){
                    timer.cancel();
                    resetTimer();

                }
            }
        });
    }
}
