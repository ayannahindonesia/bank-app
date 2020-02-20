package com.ayannah.asira.screen.borrower.register_personal_info;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RegisterPersonalInfoModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RegisterPersonalInfoFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract RegisterPersonalInfoContract.Presenter requestPresenter(RegisterPersonalInfoPresenter presenter);

}
