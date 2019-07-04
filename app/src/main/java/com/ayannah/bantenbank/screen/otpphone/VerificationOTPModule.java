package com.ayannah.bantenbank.screen.otpphone;

import com.ayannah.bantenbank.di.ActivityScoped;
import com.ayannah.bantenbank.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class VerificationOTPModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract VerificationOTPFragment verificationOTPFragment();

    @ActivityScoped
    @Binds
    abstract VerificationOTPContract.Presenter verificationContract(VerificationOTPPresenter presenter);
}
