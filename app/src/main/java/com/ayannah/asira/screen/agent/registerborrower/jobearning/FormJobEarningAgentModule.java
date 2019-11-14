package com.ayannah.asira.screen.agent.registerborrower.jobearning;

import com.ayannah.asira.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormJobEarningAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormJobEarningAgentFragment requestFragment();
}
