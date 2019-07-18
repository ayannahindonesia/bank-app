package com.ayannah.bantenbank.screen.register.adddoc;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

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
