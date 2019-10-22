package com.ayannah.asira.screen.loan;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.ayannah.asira.screen.loan.LoanActivity.IDSERVICE;

@Module
public abstract class LoanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoanFragment requestFragmemt();

    @ActivityScoped
    @Binds
    abstract LoanContract.Presenter requestPresenter(LoanPresenter presenter);

    @Provides
    @ActivityScoped
    static String idService(LoanActivity activity){
        return activity.getIntent().getStringExtra(IDSERVICE);
    }

}
