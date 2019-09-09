package com.ayannah.asira.screen.otpphone;

import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.ayannah.asira.screen.otpphone.VerificationOTPActivity.IDLOAN;
import static com.ayannah.asira.screen.otpphone.VerificationOTPActivity.PURPOSES;

@Module
public abstract class VerificationOTPModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract VerificationOTPFragment verificationOTPFragment();

    @ActivityScoped
    @Binds
    abstract VerificationOTPContract.Presenter verificationContract(VerificationOTPPresenter presenter);

    @Provides
    @ActivityScoped
    @Named("purpose")
    static String purpose(VerificationOTPActivity activity){
        return activity.getIntent().getStringExtra(PURPOSES);
    }

    @Provides
    @ActivityScoped
    @Named("idloan")
    static int idLoan(VerificationOTPActivity activity){
        return activity.getIntent().getIntExtra(IDLOAN, 0);
    }

}
