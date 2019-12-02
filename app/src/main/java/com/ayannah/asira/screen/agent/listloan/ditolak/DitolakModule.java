package com.ayannah.asira.screen.agent.listloan.ditolak;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DitolakModule {

    @ActivityScoped
    @Binds
    abstract DitolakContract.Presenter requestPresenter(DitolakPresenter presenter);


}
