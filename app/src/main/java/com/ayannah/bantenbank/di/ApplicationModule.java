package com.ayannah.bantenbank.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class ApplicationModule {

    @Binds
    abstract Context bindContext(Application application);


}
