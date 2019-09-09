package com.ayannah.asira.screen.register.addaccountbank;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddAccountBankModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddAccountBankFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract AddAccountBankContract.Presenter requestPresenter(AddAccountBankPresenter presenter);

}
