package com.ayannah.asira.screen.agent.tab_pesan;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PesanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PesanFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract PesanContract.Presenter requestPresenter(PesanPresenter presenter);

    @ActivityScoped
    @Provides
    @Named("notif")
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.VIEW_NOTIFPAGE);
    }

}
