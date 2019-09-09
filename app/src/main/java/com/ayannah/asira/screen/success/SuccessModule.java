package com.ayannah.asira.screen.success;

import com.ayannah.asira.di.ActivityScoped;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.ayannah.asira.screen.success.SuccessActivity.SUCCESS_COND;
import static com.ayannah.asira.screen.success.SuccessActivity.SUCCESS_DESC;
import static com.ayannah.asira.screen.success.SuccessActivity.SUCCESS_TITLE;

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

    @Provides
    @ActivityScoped
    @Named("cond")
    static int cond(SuccessActivity activity){
        return activity.getIntent().getIntExtra(SUCCESS_COND, 0);
    }
}
