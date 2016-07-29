package com.pikazo.di.modules;

import android.app.Application;
import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.pikazo.global.AppConstants;
import com.pikazo.global.SharedData;
import com.pikazo.rest.aws.LambdaProxy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

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

    @Provides
    @Singleton
    public LambdaProxy provideLambdaProxy(LambdaInvokerFactory factory) {
        return factory.build(LambdaProxy.class);
    }

    @Provides
    @Singleton
    public LambdaInvokerFactory provideLambdaInvokerFactory(
            CognitoCachingCredentialsProvider credentialsProvider) {
        return new LambdaInvokerFactory(application, Regions.US_EAST_1, credentialsProvider);
    }


    @Provides
    @Singleton
    public CognitoCachingCredentialsProvider provideCognitoCachingCredentialsProvider() {
        return new CognitoCachingCredentialsProvider(application, AppConstants.IDENTITY_POOL_ID,
                Regions.US_EAST_1);
    }

    @Provides
    @Singleton
    public AmazonS3 provideAmazonS3(CognitoCachingCredentialsProvider credentialsProvider) {
        return new AmazonS3Client(credentialsProvider);
    }

    @Provides
    @Singleton
    public TransferUtility provideTransferUtility(AmazonS3 s3, Context context) {
        return new TransferUtility(s3, context);
    }

    @Provides
    @Singleton
    public Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

}
