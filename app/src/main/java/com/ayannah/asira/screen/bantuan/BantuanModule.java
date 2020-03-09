package com.ayannah.asira.screen.bantuan;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BantuanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract BantuanFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract BantuanContract.Presenter requestPresenter(BantuanPresenter presenter);

    @Provides
    @ActivityScoped
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.FAQ);
    }
}
