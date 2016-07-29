package com.pikazo.di.modules;

import android.content.Context;

import com.pikazo.presenter.paint.IPaintPresenter;
import com.pikazo.presenter.paint.PaintPresenter;
import com.pikazo.rest.services.PaintService;
import com.pikazo.view.activities.Paint.PaintView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jsolisl-as on 05/10/2015.
 */
@Module
public class PaintModule {

    private PaintView view;

    public PaintModule(PaintView paintActivity) {
        this.view = paintActivity;
    }

    @Provides
    public PaintView provideView(){
        return view;
    }

    @Provides
    public PaintService provideService(Context context){
        return new PaintService(context);
    }

    @Provides
    public IPaintPresenter provideMainPresenter(Context context, PaintView paintView,
                                                PaintService paintService){
        return new PaintPresenter(context, paintView, paintService);
    }

}
