package com.ayannah.asira.screen.borrower.profile_menu.infokeuangan;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

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
