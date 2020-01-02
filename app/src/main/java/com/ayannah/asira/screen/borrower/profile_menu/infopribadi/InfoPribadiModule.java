package com.ayannah.asira.screen.borrower.profile_menu.infopribadi;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class InfoPribadiModule {

    @ActivityScoped
    @Binds
    abstract InfoPribadiContract.Presenter reqeustPresenter(InfoPribadiPresenter presenter);

}
