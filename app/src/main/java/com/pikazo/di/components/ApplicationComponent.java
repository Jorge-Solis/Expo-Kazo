package com.pikazo.di.components;

import android.content.Context;

import com.pikazo.PikazoApplication;
import com.pikazo.di.modules.ApplicationModule;
import com.pikazo.di.modules.RestModule;
import com.pikazo.global.SharedData;
import com.pikazo.view.activities.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
@Singleton
@Component( modules = {ApplicationModule.class, RestModule.class})
public interface ApplicationComponent {
    Context context();
    SharedData getSharedData();
    void inject(PikazoApplication application);
    void inject(BaseActivity baseActivity);
}
