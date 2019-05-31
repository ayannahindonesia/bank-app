package com.ayannah.bantenbank.screen.loan;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
