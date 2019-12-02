package com.ayannah.asira.screen.agent.listloan.pencairan;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PencairanModule {

    @ActivityScoped
    @Binds
    abstract PencairanContract.Presenter reqPresenter(PencairanPresenter presenter);

}
