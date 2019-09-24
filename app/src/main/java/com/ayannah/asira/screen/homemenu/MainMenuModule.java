package com.ayannah.asira.screen.homemenu;

import android.app.Application;

import com.ayannah.asira.adapter.BeritaPromoAdapter;
import com.ayannah.asira.adapter.MenuProductAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainMenuModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainMenuFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract MainMenuContract.Presenter requestPresenter(MainMenuPresenter presenter);

    @Provides
    @ActivityScoped
    static BeritaPromoAdapter beritaPromoAdapter(Application application){
        return new BeritaPromoAdapter(application);
    }

    @Provides
    @ActivityScoped
    static MenuProductAdapter menuAdapter(Application application){
        return new MenuProductAdapter(application);
    }
}
