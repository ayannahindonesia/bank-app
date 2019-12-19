package com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AgentProfileBankListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AgentProfileBankListFragment requestFragment();

    @Provides
    @ActivityScoped
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.VIEW_BANK_LIST);
    }

    @ActivityScoped
    @Binds
    abstract AgentProfileBankListContract.Presenter requestPresenter(AgentProfileBankListPresenter presenter);
}
