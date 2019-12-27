package com.ayannah.asira.screen.borrower.borrower_landing_page;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BorrowerLandingModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract BorrowerLandingFragment reqFragment();

    @ActivityScoped
    @Binds
    abstract BorrowerLandingContract.Presenter requestPresenter(BorrowerLandingPresenter presenter);

}
