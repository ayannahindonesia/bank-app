package com.ayannah.asira.screen.navigationmenu.akunsaya;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AkunSayaModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AkunSayaFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract AkunSayaContract.Presenter requestPresenter(AkunSayaPresenter presenter);


}
