package com.ayannah.bantenbank.screen.login;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract LoginContract.Presenter requestPresenter(LoginPresenter presenter);

}
