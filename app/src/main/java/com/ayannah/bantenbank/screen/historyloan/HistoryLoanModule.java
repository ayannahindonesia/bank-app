package com.ayannah.bantenbank.screen.historyloan;

import android.app.Application;

import com.ayannah.bantenbank.adapter.LoanAdapter;
import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
    static LoanAdapter loanAdapter(Application application){
        return new LoanAdapter(application);

    }
}
