package com.ayannah.asira.screen.register.adddoc;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AddDocumentModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract AddDocumentFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract AddDocumentContract.Presenter requestPresenter(AddDocumentPresenter presenter);

}
