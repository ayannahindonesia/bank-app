package com.ayannah.bantenbank;

import com.ayannah.bantenbank.data.remote.RemoteService;
import com.ayannah.bantenbank.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BankApplication extends DaggerApplication {

    @Inject
    RemoteService remoteService;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        remoteService.init(this);

    }
}
