package com.pikazo.di.components;

import com.pikazo.di.modules.MainModule;
import com.pikazo.view.activities.main.MainActivity;

import dagger.Component;

/**
 * Created by jsolisl-as on 07/10/2015.
 */
@ActivityScope
@Component(modules = {MainModule.class}, dependencies = {ApplicationComponent.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
