package com.ayannah.bantenbank.screen.register.formjobearning;

import com.ayannah.bantenbank.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormJobEarningModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormJobEarningFragment requestFragment();

}
