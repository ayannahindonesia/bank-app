package com.ayannah.bantenbank.screen.register.formemailphone;

import com.ayannah.bantenbank.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormEmailPhoneModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormEmailPhoneFragment requestFragment();

}
