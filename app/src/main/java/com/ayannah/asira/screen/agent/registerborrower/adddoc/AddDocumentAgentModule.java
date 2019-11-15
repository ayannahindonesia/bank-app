package com.ayannah.asira.screen.agent.registerborrower.adddoc;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddDocumentAgentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddDocumentAgentFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract AddDocumentAgentContract.Presenter requestPresenter(AddDocumentAgentPresenter presenter);

}
