package com.ayannah.asira.screen.agent;

import com.ayannah.asira.di.FragmentScoped;

import dagger.android.ContributesAndroidInjector;

public abstract class LPAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LPAgentFragment reqFragment();


}
