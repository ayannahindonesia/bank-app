package com.ayannah.asira.screen.register.formemailphone;

import com.ayannah.asira.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormEmailPhoneModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormEmailPhoneFragment requestFragment();

}
