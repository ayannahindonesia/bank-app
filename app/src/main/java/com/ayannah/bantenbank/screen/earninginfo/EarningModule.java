package com.ayannah.bantenbank.screen.earninginfo;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
