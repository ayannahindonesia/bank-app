package com.ayannah.asira.screen.agent.viewBorrower;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ViewBorrowerModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ViewBorrowerFragment reqFragment();

    @ActivityScoped
    @Binds
    abstract ViewBorrowerContract.Presenter reqPresenter(ViewBorrowerPresenter presenter);

}
