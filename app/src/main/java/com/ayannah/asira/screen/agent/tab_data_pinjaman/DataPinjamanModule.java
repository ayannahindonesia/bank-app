package com.ayannah.asira.screen.agent.tab_data_pinjaman;

import android.app.Application;

import com.ayannah.asira.adapter.ChooseBankAdapter;
import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DataPinjamanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DataPinjamanFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract DataPinjamanContract.Presenter requestPresenter(DataPinjamanPresenter presenter);

    @Provides
    @ActivityScoped
    @Named("loan")
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.AGENT_LIST_LOAN);
    }

}
