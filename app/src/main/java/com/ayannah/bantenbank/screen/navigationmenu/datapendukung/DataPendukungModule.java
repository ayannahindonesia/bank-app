package com.ayannah.bantenbank.screen.navigationmenu.datapendukung;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
