package com.ayannah.asira.screen.loan;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoanFragment requestFragmemt();

    @ActivityScoped
    @Binds
    abstract LoanContract.Presenter requestPresenter(LoanPresenter presenter);

}
