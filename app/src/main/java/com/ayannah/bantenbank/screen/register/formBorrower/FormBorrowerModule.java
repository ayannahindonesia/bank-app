package com.ayannah.bantenbank.screen.register.formBorrower;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
