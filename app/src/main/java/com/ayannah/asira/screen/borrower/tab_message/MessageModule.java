package com.ayannah.asira.screen.borrower.tab_message;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MessageModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MessageFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract MessageContract.Presenter requestPresenter(MessagePresenter presenter);

    @ActivityScoped
    @Provides
    @Named("notif")
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.VIEW_NOTIFPAGE);
    }

}
