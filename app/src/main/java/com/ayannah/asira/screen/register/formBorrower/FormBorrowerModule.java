package com.ayannah.asira.screen.register.formBorrower;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormBorrowerModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormBorrowerFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract FormBorrowerContract.Presenter requestPresenter(FormBorrowerPresenter presenter);

}
