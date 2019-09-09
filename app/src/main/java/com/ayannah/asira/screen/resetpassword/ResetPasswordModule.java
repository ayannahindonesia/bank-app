package com.ayannah.asira.screen.resetpassword;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ResetPasswordModule {

    @ActivityScoped
    @Binds
    abstract ResetPasswordContract.Presenter requestPresenter(ResetPasswordPresenter presenter);

}
