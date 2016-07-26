package com.pikazo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.pikazo.di.components.ApplicationComponent;
import com.pikazo.di.components.DaggerApplicationComponent;
import com.pikazo.di.modules.ApplicationModule;

import javax.inject.Singleton;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
@Singleton
public class PikazoApplication extends Application {

    private ApplicationComponent applicationComponent;
    private ApplicationModule applicationModule;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        applicationModule = new ApplicationModule(this);
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(getApplicationModule()).build();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    public ApplicationModule getApplicationModule(){
        return applicationModule;
    }

}
