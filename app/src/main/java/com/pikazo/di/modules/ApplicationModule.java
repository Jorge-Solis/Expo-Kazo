package com.pikazo.di.modules;

import android.app.Application;
import android.content.Context;

import com.pikazo.global.SharedData;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    public SharedData provideSharedData(){
        return new SharedData();
    }

}
