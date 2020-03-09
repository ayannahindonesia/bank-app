package com.ayannah.asira.screen.borrower.tab_historyloan;


import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HistoryLoanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HistoryLoanFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract HistoryLoanContract.Presenter requestPresenter(HistoryLoanPresenter presenter);

    @Provides
    @ActivityScoped
    @Named("pinjaman")
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.AGENT_LIST_LOAN);
    }
}
