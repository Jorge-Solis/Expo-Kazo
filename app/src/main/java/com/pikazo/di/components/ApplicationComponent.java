package com.pikazo.di.components;

import android.content.Context;

import com.pikazo.PikazoApplication;
import com.pikazo.di.modules.ApplicationModule;
import com.pikazo.global.SharedData;
import com.pikazo.rest.aws.LambdaProxy;
import com.pikazo.rest.aws.ServerSubmission;
import com.pikazo.view.activities.BaseActivity;
import com.pikazo.view.activities.splash.SplashScreen;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
@Singleton
@Component( modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context context();
    SharedData getSharedData();
    LambdaProxy getLambdaProxy();
    ServerSubmission getServerSubmission();
    Realm getRealm();
    void inject(PikazoApplication application);
    void inject(BaseActivity baseActivity);
    void inject(SplashScreen splashScreen);
}
