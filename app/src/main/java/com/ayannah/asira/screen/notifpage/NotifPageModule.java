package com.ayannah.asira.screen.notifpage;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NotifPageModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract NotifPageFragment reqFragment();

    @ActivityScoped
    @Binds
    abstract NotifPageContract.Presenter reqPresenter(NotifPagePresenter presenter);

    @Provides
    @ActivityScoped
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.VIEW_NOTIFPAGE);
    }

}
