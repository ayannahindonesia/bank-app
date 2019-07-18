package com.ayannah.bantenbank.screen.createnewpassword;

import com.ayannah.bantenbank.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CreateNewPassModule {


    @ActivityScoped
    @Binds
    abstract CreateNewPassContract.Presenter requestPresenter(CreateNewPassPresenter presenter);


}
