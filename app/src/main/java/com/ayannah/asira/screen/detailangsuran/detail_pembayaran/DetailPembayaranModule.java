package com.ayannah.asira.screen.detailangsuran.detail_pembayaran;

import com.ayannah.asira.data.model.InstallmentDetails;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailPembayaranModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DetailPembayaranFragment reqFragment();

}
