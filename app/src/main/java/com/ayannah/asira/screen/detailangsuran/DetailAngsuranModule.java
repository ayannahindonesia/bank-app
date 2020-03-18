package com.ayannah.asira.screen.detailangsuran;

import com.ayannah.asira.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailAngsuranModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DetailAngsuranFragment requestFragment();

}
