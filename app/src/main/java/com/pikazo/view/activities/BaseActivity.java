package com.pikazo.view.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.pikazo.di.components.DaggerApplicationComponent;
import com.pikazo.di.modules.ApplicationModule;
import com.pikazo.global.AppConstants;
import com.pikazo.global.SharedData;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    @Singleton
    protected SharedData sharedData;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor sharedPreferencesEditor;
    protected Realm realm;

    /**
     * Each time an Activity gets created, the dependencies injection extension will be called
     */
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        sharedPreferences = getSharedPreferences(AppConstants.PREFERENCES_FILE_KEY,
                Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .build().inject(this);
    }

    /**
     * Method used to inject dependencies needed by the extending Activity
     */
    protected abstract void setActivityGraph();

    /**
     * Method used to configure views such as setting listeners
     * NOTE: (Initialization is done via ButterKnife)
     */
    protected abstract void configureViews();
}
