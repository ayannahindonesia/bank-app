package com.ayannah.asira.screen.agent.tab_beranda;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BerandaModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract BerandaFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract BerandaContract.Presenter requestPresenter(BerandaPresenter presenter);

    @ActivityScoped
    @Provides
    @Named("borrowersAgent")
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.AGENT_VIEW_AGENTS_BORROWER);
    }

}
