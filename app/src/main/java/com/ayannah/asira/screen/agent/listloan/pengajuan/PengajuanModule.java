package com.ayannah.asira.screen.agent.listloan.pengajuan;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PengajuanModule {

    @ActivityScoped
    @Binds
    abstract PengajuanContract.Presenter requestPresenter(PengajuanPresenter presenter);

}
