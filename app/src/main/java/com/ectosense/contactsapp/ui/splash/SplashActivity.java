package com.ectosense.contactsapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;

import androidx.appcompat.app.AppCompatActivity;

import com.ectosense.contactsapp.R;
import com.ectosense.contactsapp.ui.home.HomeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GoToHomeScreen();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    private void GoToHomeScreen() {
        Intent homeScreenIntent = new Intent(SplashActivity.this,
                HomeActivity.class);
        startActivity(homeScreenIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
