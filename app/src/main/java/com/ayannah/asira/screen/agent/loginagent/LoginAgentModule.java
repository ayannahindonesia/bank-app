package com.ayannah.asira.screen.agent.loginagent;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginAgentFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract LoginAgentContract.Presenter requestPResenter(LoginAgentPresenter presenter);
}
