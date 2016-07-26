package com.pikazo.presenter;

import android.content.Context;

import com.pikazo.PikazoApplication;
import com.pikazo.global.SharedData;

import javax.inject.Inject;

/**
 * Created by jsolisl-as on 02/10/2015.
 */
public class BasePresenter {

    protected SharedData sharedData;
    protected Context context;

    @Inject
    public BasePresenter(Context context){
        this.context = context;
        sharedData = ((PikazoApplication)context).getApplicationComponent()
                .getSharedData();
    }
}
