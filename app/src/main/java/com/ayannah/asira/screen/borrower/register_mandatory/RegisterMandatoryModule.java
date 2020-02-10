package com.ayannah.asira.screen.borrower.register_mandatory;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RegisterMandatoryModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RegisterMandatoryFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract RegisterMandatoryContract.Presenter requestPresenter(RegisterMandatoryPresenter presenter);

}
