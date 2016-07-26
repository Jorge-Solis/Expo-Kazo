package com.pikazo.di.modules;

import android.content.Context;

import com.pikazo.presenter.main.IMainPresenter;
import com.pikazo.presenter.main.MainPresenter;
import com.pikazo.rest.services.IMainService;
import com.pikazo.rest.services.MainService;
import com.pikazo.view.activities.main.IMainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jsolisl-as on 05/10/2015.
 */
@Module
public class MainModule {

    private IMainView mainView;

    public MainModule(IMainView mainView) {
        this.mainView = mainView;
    }

    @Provides
    public IMainView provideMainView(){
        return mainView;
    }

    @Provides
    public IMainService provideMainService(Context context){
        return new MainService(context);
    }

    @Provides
    public IMainPresenter provideMainPresenter(Context context, IMainView mainView,
                                               IMainService mainService){
        return new MainPresenter(context, mainView, mainService);
    }

}
