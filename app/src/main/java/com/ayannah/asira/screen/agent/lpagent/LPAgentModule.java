package com.ayannah.asira.screen.agent.lpagent;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LPAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LPAgentFragment reqFragment();

    @ActivityScoped
    @Binds
    abstract LPAgentContract.Presenter requestPresenter(LPAgentPresenter presenter);

}
