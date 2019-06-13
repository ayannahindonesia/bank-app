package com.ayannah.bantenbank.screen.homemenu;

import android.app.Application;

import com.ayannah.bantenbank.adapter.BeritaPromoAdapter;
import com.ayannah.bantenbank.adapter.LoanAdapter;
import com.ayannah.bantenbank.adapter.MenuProductAdapter;
import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
    static LoanAdapter loanAdapter(Application application){
        return new LoanAdapter(application);
    }

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
