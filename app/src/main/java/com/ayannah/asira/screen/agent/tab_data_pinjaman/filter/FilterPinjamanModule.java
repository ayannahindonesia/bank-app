package com.ayannah.asira.screen.agent.tab_data_pinjaman.filter;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FilterPinjamanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FilterPinjamanFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract FilterPinjamanContract.Presenter requestPresenter(FilterPinjamanPresenter presenter);

}
