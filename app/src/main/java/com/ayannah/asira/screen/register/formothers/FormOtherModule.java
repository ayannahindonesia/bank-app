package com.ayannah.asira.screen.register.formothers;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormOtherModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormOtherFragment formOtherFragment();

    @ActivityScoped
    @Binds
    abstract FormOtherContract.Presenter requestPresenter(FormOtherPresenter presenter);
}
