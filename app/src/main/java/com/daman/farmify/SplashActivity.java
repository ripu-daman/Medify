package com.daman.farmify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
boolean loggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("loginSp", Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("loggedin",false);
        if(loggedIn){
            handler.sendEmptyMessageDelayed(102,2500);
        }else{
            handler.sendEmptyMessageDelayed(101,2500);
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 101){
                Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }else if(msg.what == 102){
                Intent i = new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }else{

            }
        }
    };
}
