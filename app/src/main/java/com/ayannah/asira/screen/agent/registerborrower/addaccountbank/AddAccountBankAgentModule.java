package com.ayannah.asira.screen.agent.registerborrower.addaccountbank;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddAccountBankAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddAccountBankAgentFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract AddAccountBankAgentContract.Presenter requestPresenter(AddAccountBankAgentPresenter presenter);
}
