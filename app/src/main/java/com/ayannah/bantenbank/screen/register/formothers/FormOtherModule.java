package com.ayannah.bantenbank.screen.register.formothers;

import com.ayannah.bantenbank.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FormOtherModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FormOtherFragment formOtherFragment();

}
