package com.ayannah.asira.screen.register.choosebank;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ChooseBankModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ChooseBankFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract ChooseBankContract.Presenter requestPresenter(ChooseBankPresenter presenter);

}
