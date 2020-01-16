package com.ayannah.asira.screen.borrower.profile_menu.editinfopribadi;

import com.ayannah.asira.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class EditInfoPribadiModule {

    @ActivityScoped
    @Binds
    abstract EditInfoPribadiContract.Presenter reqPresenter(EditInfoPribadiPresenter presenter);


}
