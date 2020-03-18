package com.ayannah.asira.screen.detailangsuran.detail_pembayaran;

import com.ayannah.asira.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailPembayaranModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DetailPembayaranFragment reqFragment();

}
