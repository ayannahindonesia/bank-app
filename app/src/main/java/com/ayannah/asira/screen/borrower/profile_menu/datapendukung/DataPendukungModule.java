package com.ayannah.asira.screen.borrower.profile_menu.datapendukung;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DataPendukungModule {


    @FragmentScoped
    @ContributesAndroidInjector
    abstract DataPendukungFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract DataPendukungContract.Presenter requestPresenter(DataPendukungPresenter presenter);

}
