package com.ayannah.asira.screen.borrower.tab_beranda;

import android.app.Application;

import com.ayannah.asira.adapter.BeritaPromoAdapter;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.adapter.MenuServiceAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Named;

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
    static MenuServiceAdapter menuAdapter(Application application){
        return new MenuServiceAdapter(application);
    }

    @Provides
    @ActivityScoped
    @Named("topuptagihan")
    static CommonListAdapter adapterTopUpTaguhan(){
        return new CommonListAdapter(CommonListAdapter.VIEW_LIST_TOPUP_TAGIHAN);
    }
}
