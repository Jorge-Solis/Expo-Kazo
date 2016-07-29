package com.pikazo.di.components;

import com.pikazo.di.modules.PaintModule;
import com.pikazo.view.activities.Paint.PaintActivity;

import dagger.Component;

/**
 * Created by jsolisl-as on 07/10/2015.
 */
@ActivityScope
@Component(modules = {PaintModule.class}, dependencies = {ApplicationComponent.class})
public interface PaintComponent {
    void inject(PaintActivity view);
}
