package com.ayannah.bantenbank.data;

import android.app.Application;

import com.ayannah.bantenbank.data.local.PreferenceDataSource;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteDataSource;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.data.remote.RemoteService;
import com.ayannah.bantenbank.data.remote.interceptor.RefreshTokenInterceptor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {

    @Binds
    abstract RemoteRepository remoteRepository(RemoteDataSource remoteDataSource);

    @Binds
    abstract PreferenceRepository preferenceRepository(PreferenceDataSource preferenceDataSource);

    @Provides
    @Singleton
    static RefreshTokenInterceptor refreshTokenInterceptor(PreferenceRepository preferenceRepository){

        return new RefreshTokenInterceptor(preferenceRepository);
    }

    @Provides
    @Singleton
    static RemoteService remoteService(RefreshTokenInterceptor interceptor){
        return new RemoteService(interceptor);
    }


}
