package com.ayannah.bantenbank.data;

import com.ayannah.bantenbank.data.local.PreferenceDataSource;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteDataSource;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.data.remote.RemoteService;

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
    static RemoteService remoteService(){
        return new RemoteService();
    }


}
