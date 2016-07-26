package com.pikazo.rest.services;

import android.content.Context;

import javax.inject.Inject;

/**
 * Created by jsolisl-as on 06/10/2015.
 */
public class BaseService {

    protected Context context;

    @Inject
    public BaseService(Context context){
        this.context = context;
    }
}
