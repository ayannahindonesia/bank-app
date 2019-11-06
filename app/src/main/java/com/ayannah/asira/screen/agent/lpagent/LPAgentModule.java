package com.ayannah.asira.screen.agent.lpagent;

import com.ayannah.asira.di.FragmentScoped;

import dagger.android.ContributesAndroidInjector;

public abstract class LPAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LPAgentFragment reqFragment();


}
