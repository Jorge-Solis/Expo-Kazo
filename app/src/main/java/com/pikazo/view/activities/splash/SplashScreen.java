package com.pikazo.view.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pikazo.R;
import com.pikazo.view.activities.BaseActivity;
import com.pikazo.view.activities.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class SplashScreen extends BaseActivity {

    private Timer timer = new Timer();
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        configureViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSplashTimer();
    }

    private void startSplashTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent().setClass(
                        SplashScreen.this, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };
        timer.schedule(timerTask, 1500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setActivityGraph() {
        // Ignore...
    }

    @Override
    protected void configureViews() {
    }
}
