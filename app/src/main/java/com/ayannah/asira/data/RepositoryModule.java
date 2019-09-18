package com.ayannah.asira.data;

import com.ayannah.asira.data.local.BankServiceInterface;
import com.ayannah.asira.data.local.BankServiceLocal;
import com.ayannah.asira.data.local.PreferenceDataSource;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.local.ServiceProductInterface;
import com.ayannah.asira.data.local.ServiceProductLocal;
import com.ayannah.asira.data.remote.RemoteDataSource;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.data.remote.RemoteService;
import com.ayannah.asira.data.remote.interceptor.RefreshTokenInterceptor;

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

    @Binds
    abstract BankServiceInterface bankServiceInterface(BankServiceLocal bankServiceLocal);

    @Binds
    abstract ServiceProductInterface serviceProductInterface(ServiceProductLocal serviceProductLocal);

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
