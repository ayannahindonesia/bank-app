package com.ayannah.asira.screen.agent.listloan.ditolak;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DitolakModule {

    @ActivityScoped
    @Binds
    abstract DitolakContract.Presenter requestPresenter(DitolakPresenter presenter);

    @ActivityScoped
    @Provides
    static CommonListAdapter adapterDitolak(){
        return new CommonListAdapter(CommonListAdapter.VIEW_LIST_BORROWERS_LOAN_AGENT);
    }

}
