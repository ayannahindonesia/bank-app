package com.ayannah.bantenbank.screen.register.choosebank;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ChooseBankModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ChooseBank requestFragment();

    @ActivityScoped
    @Binds
    abstract ChooseBankContract.Presenter requestPresenter(ChooseBankPresenter presenter);

}
