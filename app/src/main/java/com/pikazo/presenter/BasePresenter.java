package com.pikazo.presenter;

import android.content.Context;

import com.pikazo.PikazoApplication;
import com.pikazo.global.SharedData;
import com.pikazo.rest.aws.LambdaProxy;
import com.pikazo.rest.aws.ServerSubmission;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
public class BasePresenter {

    protected SharedData sharedData;
    protected LambdaProxy lambdaProxy;
    protected Context context;
    protected ServerSubmission serverSubmission;
    protected Realm realm;

    @Inject
    public BasePresenter(Context context){
        this.context = context;
        sharedData = ((PikazoApplication)context).getApplicationComponent().getSharedData();
        lambdaProxy = ((PikazoApplication)context).getApplicationComponent().getLambdaProxy();
        serverSubmission = ((PikazoApplication)context).getApplicationComponent()
                .getServerSubmission();
        realm = ((PikazoApplication)context).getApplicationComponent().getRealm();
    }
}
