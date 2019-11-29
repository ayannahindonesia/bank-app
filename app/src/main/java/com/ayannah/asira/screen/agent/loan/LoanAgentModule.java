package com.ayannah.asira.screen.agent.loan;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.ayannah.asira.screen.agent.loan.LoanAgentActivity.IDBANK;
import static com.ayannah.asira.screen.agent.loan.LoanAgentActivity.IDSERVICE;

@Module
public abstract class LoanAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoanAgentFragment requestFragmemt();

    @ActivityScoped
    @Binds
    abstract LoanAgentContract.Presenter requestPresenter(LoanAgentPresenter presenter);

    @Provides
    @ActivityScoped
    static String idService(LoanAgentActivity activity){
        return activity.getIntent().getStringExtra(IDSERVICE);
    }
}
