package com.ayannah.asira.screen.agent.services;

import android.app.Application;

import com.ayannah.asira.adapter.BeritaPromoAdapter;
import com.ayannah.asira.adapter.MenuServiceAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ListServicesAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ListServicesAgentFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract ListServicesAgentContract.Presenter requestPresenter(ListServicesAgentPresenter presenter);

    @Provides
    @ActivityScoped
    static MenuServiceAdapter menuAdapter(Application application){
        return new MenuServiceAdapter(application);
    }

    @Provides
    @ActivityScoped
    static BeritaPromoAdapter beritaPromoAdapter(Application application){
        return new BeritaPromoAdapter(application);
    }
}
