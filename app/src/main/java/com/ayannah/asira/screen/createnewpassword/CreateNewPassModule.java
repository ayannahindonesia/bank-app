package com.ayannah.asira.screen.createnewpassword;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CreateNewPassModule {


    @ActivityScoped
    @Binds
    abstract CreateNewPassContract.Presenter requestPresenter(CreateNewPassPresenter presenter);


}
