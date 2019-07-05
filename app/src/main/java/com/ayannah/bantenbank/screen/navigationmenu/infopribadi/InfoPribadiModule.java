package com.ayannah.bantenbank.screen.navigationmenu.infopribadi;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InfoPribadiModule {

    @ActivityScoped
    @Binds
    abstract InfoPribadiContract.Presenter reqeustPresenter(InfoPribadiPresenter presenter);

}
