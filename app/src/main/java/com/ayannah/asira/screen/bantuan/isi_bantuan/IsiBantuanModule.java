package com.ayannah.asira.screen.bantuan.isi_bantuan;

import com.ayannah.asira.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class IsiBantuanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract IsiBantuanFragment reqBantuan();

}
