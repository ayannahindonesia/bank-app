package com.ayannah.bantenbank.screen.navigationmenu.akunsaya;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
