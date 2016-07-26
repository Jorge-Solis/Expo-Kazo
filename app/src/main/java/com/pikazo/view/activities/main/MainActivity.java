package com.pikazo.view.activities.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pikazo.PikazoApplication;
import com.pikazo.R;
import com.pikazo.di.components.DaggerMainComponent;
import com.pikazo.di.modules.MainModule;
import com.pikazo.presenter.main.IMainPresenter;
import com.pikazo.view.activities.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainView {

    @Inject IMainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityGraph();
        ButterKnife.bind(this);
        configureViews();
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
        DaggerMainComponent.builder()
                .applicationComponent(((PikazoApplication)getApplication()).getApplicationComponent())
                .mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    protected void configureViews() {
    }
}
