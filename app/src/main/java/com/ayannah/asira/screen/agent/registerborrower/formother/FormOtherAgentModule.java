package com.ayannah.asira.screen.agent.registerborrower.formother;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormOtherAgentModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormOtherAgentFragment formOtherFragment();

    @ActivityScoped
    @Binds
    abstract FormOtherAgentContract.Presenter requestPresenter(FormOtherAgentPresenter presenter);
}
