package com.ayannah.asira.screen.agent.listloan.pengajuan;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class PengajuanModule {

    @ActivityScoped
    @Binds
    abstract PengajuanContract.Presenter requestPresenter(PengajuanPresenter presenter);

    @ActivityScoped
    @Provides
    @Named("adapterPengajuan")
    static CommonListAdapter adapterPengajuan(){
        return new CommonListAdapter(CommonListAdapter.VIEW_LIST_BORROWERS_LOAN_AGENT);
    }
}
