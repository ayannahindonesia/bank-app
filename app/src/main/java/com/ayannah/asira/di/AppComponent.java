package com.ayannah.asira.di;

import android.app.Application;

import com.ayannah.asira.BankApplication;
import com.ayannah.asira.data.RepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        RepositoryModule.class,
        AndroidSupportInjectionModule.class
})

public interface AppComponent extends AndroidInjector<BankApplication> {

//    void inject(MyFirebaseMessagingService service);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
