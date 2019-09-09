package com.ayannah.asira.screen.earninginfo;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EarningModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract EarningFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract EarningContract.Presenter requestPresenter(EarningPresenter presenter);


}
