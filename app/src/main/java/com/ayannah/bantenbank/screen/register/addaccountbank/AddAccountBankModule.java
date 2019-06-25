package com.ayannah.bantenbank.screen.register.addaccountbank;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
