package com.ayannah.asira.screen.agent.tab_agent_profile;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AgentProfileModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AgentProfileFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract AgentProfileContract.Presenter requestPresenter(AgentProfilePresenter presenter);

}
