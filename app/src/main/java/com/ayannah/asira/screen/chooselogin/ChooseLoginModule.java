package com.ayannah.asira.screen.chooselogin;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ChooseLoginModule {

    @ActivityScoped
    @Binds
    abstract ChooseLoginContract.Presenter requestPresenter(ChooseLoginPresenter presenter);
}
