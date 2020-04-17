package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MainActivity context;
    private CountDownTimer countDownTimer;
    public boolean timerStopped;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        context = this;

        new CountDownTimer(2000, 2000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                // Here do what you like...
                Intent intent = new Intent(context, Login.class);
                startActivity(intent);
            }
        }.start();
}}
