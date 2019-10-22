package com.ayannah.asira.screen.earninginfo;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Inject;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.ayannah.asira.screen.earninginfo.EarningActivity.ID_SERVICE;

@Module
public abstract class EarningModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract EarningFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract EarningContract.Presenter requestPresenter(EarningPresenter presenter);

    @Provides
    @ActivityScoped
    static String idService(EarningActivity activity){
        return activity.getIntent().getStringExtra(ID_SERVICE);
    }
}
