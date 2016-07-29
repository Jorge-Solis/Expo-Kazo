package com.pikazo.view.activities.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.pikazo.PikazoApplication;
import com.pikazo.R;
import com.pikazo.global.AppConstants;
import com.pikazo.global.SharedData;
import com.pikazo.rest.dto.Job;
import com.pikazo.view.activities.BaseActivity;
import com.pikazo.view.activities.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class SplashScreen extends BaseActivity {

    @Inject
    @Singleton
    SharedData sharedData;
    @Inject
    @Singleton
    Realm realm;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Timer timer = new Timer();
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences(AppConstants.PREFERENCES_FILE_KEY,
                Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        setActivityGraph();
        ButterKnife.bind(this);
        configureViews();
        loadDataBase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSplashTimer();
    }

    /**
     * Get the base data needed for the app before displaying main screen(Portfolio)
     */
    private void loadDataBase(){
        RealmResults<Job> localQueue = realm.where(Job.class).findAll();
        sharedData.setUserQueueJobs(localQueue);
    }

    /**
     * Starts the splash screen timer
     */
    private void startSplashTimer() {
        String deviceId = sharedPreferences.getString(AppConstants.PREFERENCE_DEVICE_ID, "");
        // Create it if not exists
        if (deviceId.equals("")) {
            deviceId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            sharedPreferencesEditor.putString(AppConstants.PREFERENCE_DEVICE_ID, deviceId);
            sharedPreferencesEditor.commit();
        }
        sharedData.setDeviceID(deviceId);
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
        ((PikazoApplication) getApplication()).getApplicationComponent()
                .inject(this);
    }

    @Override
    protected void configureViews() {
    }
}
