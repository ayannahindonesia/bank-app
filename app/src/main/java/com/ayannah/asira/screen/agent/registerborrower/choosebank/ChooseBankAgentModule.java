package com.ayannah.asira.screen.agent.registerborrower.choosebank;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ChooseBankAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ChooseBankAgentFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract ChooseBankAgentContract.Presenter requestPresenter(ChooseBankAgentPresenter presenter);
}
