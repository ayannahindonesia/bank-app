package com.ayannah.asira.screen.borrower.tab_rewards;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RewardsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RewardsFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract RewardsContract.Presenter requestPresenter(RewardsPresenter presenter);

}
