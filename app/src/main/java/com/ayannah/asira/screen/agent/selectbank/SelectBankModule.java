package com.ayannah.asira.screen.agent.selectbank;

import com.ayannah.asira.adapter.NestedSelectBankAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SelectBankModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SelectBankFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract SelectBankContract.Presenter reqPresenter(SelectBankPresenter presenter);

    @ActivityScoped
    @Provides
    static NestedSelectBankAdapter adapter(){
        return new NestedSelectBankAdapter();
    }
}
