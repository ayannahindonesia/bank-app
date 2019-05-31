package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InformasiKeuanganModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract InformasiKeuanganFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract InformasiKeuanganContract.Presenter requestPresenter(InformasiKeuanganPresenter presenter);
}
