package com.ayannah.asira.screen.register.formjobearning;

import com.ayannah.asira.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormJobEarningModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormJobEarningFragment requestFragment();

}
