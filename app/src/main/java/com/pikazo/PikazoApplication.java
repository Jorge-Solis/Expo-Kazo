package com.pikazo;

import android.app.Application;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.pikazo.di.components.ApplicationComponent;
import com.pikazo.di.components.DaggerApplicationComponent;
import com.pikazo.di.modules.ApplicationModule;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setRequestListeners(requestListeners)
                .build();
        // Create a RealmConfiguration that saves the Realm file in the app's "files" directory.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        Fresco.initialize(this, config);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
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
