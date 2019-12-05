package com.ayannah.asira.screen.agent.listloan.pencairan;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class PencairanModule {

    @ActivityScoped
    @Binds
    abstract PencairanContract.Presenter reqPresenter(PencairanPresenter presenter);

    @ActivityScoped
    @Provides
    @Named("adapterPencairan")
    static CommonListAdapter adapterPencairan(){
        return new CommonListAdapter(CommonListAdapter.VIEW_LIST_BORROWERS_LOAN_AGENT);
    }

}
