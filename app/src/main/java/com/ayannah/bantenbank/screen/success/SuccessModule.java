package com.ayannah.bantenbank.screen.success;

import com.ayannah.bantenbank.di.ActivityScoped;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.ayannah.bantenbank.screen.success.SuccessActivity.SUCCESS_DESC;
import static com.ayannah.bantenbank.screen.success.SuccessActivity.SUCCESS_TITLE;

@Module
public abstract class SuccessModule {

    @Provides
    @ActivityScoped
    @Named("title")
    static String title(SuccessActivity activity){
        return activity.getIntent().getStringExtra(SUCCESS_TITLE);
    }


    @Provides
    @ActivityScoped
    @Named("desc")
    static String desc(SuccessActivity activity){
        return activity.getIntent().getStringExtra(SUCCESS_DESC);
    }
}
