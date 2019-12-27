package com.ayannah.asira.screen.borrower.tab_profile;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProfileModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ProfileFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract ProfileContract.Presenter requestPresenter(ProfilePresenter presenter);

}
