package com.ayannah.bantenbank.screen.resetpassword;

import com.ayannah.bantenbank.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ResetPasswordModule {

    @ActivityScoped
    @Binds
    abstract ResetPasswordContract.Presenter requestPresenter(ResetPasswordPresenter presenter);

}
