package com.ayannah.asira.screen.navigationmenu.infopribadi;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class InfoPribadiModule {

    @ActivityScoped
    @Binds
    abstract InfoPribadiContract.Presenter reqeustPresenter(InfoPribadiPresenter presenter);

}
