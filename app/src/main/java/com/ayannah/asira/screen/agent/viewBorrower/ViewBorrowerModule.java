package com.ayannah.asira.screen.agent.viewBorrower;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.ayannah.asira.screen.agent.viewBorrower.ViewBorrowerActivity.BANK_ID;

@Module
public abstract class ViewBorrowerModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ViewBorrowerFragment reqFragment();

    @ActivityScoped
    @Binds
    abstract ViewBorrowerContract.Presenter reqPresenter(ViewBorrowerPresenter presenter);

    @Provides
    @ActivityScoped
    static CommonListAdapter adapter(){
        return new CommonListAdapter(CommonListAdapter.VIEW_BORROWER_ON_AGENT);
    }

//    @Provides
//    @ActivityScoped
//    @Named("bank_id")
//    static String bank_id(ViewBorrowerActivity activity){
//        return activity.getIntent().getStringExtra(BANK_ID);
//    }

}
