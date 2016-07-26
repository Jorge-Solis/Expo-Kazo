package com.pikazo.presenter.main;

import android.content.Context;

import com.pikazo.presenter.BasePresenter;
import com.pikazo.rest.services.IMainService;
import com.pikazo.view.activities.main.IMainView;

import javax.inject.Inject;

/**
 * Created by jsolisl-as on 05/10/2015.
 */
public class MainPresenter extends BasePresenter implements IMainPresenter{

    private IMainView mainView;
    private IMainService mainService;

    @Inject
    public MainPresenter(Context context, IMainView mainView, IMainService mainService){
        super(context);
        this.mainView = mainView;
        this.mainService = mainService;
    }


}
