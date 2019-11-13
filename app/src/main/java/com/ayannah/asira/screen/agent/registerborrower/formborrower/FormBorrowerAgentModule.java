package com.ayannah.asira.screen.agent.registerborrower.formborrower;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormBorrowerAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormBorrowerAgentFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract FormBorrowerAgentContract.Presenter requestPresenter(FormBorrowerAgentPresenter presenter);
}
