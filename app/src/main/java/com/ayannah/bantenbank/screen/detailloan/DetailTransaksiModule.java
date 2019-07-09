package com.ayannah.bantenbank.screen.detailloan;

import com.ayannah.bantenbank.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import static com.ayannah.bantenbank.screen.detailloan.DetailTransaksiActivity.ID_LOAN;

@Module
public abstract class DetailTransaksiModule {

    @ActivityScoped
    @Binds
    abstract DetailTransaksiContract.Presenter requestPresenter(DetailTransaksiPresenter presenter);

    @Provides
    @ActivityScoped
    static String idLoan(DetailTransaksiActivity activity){
        return activity.getIntent().getStringExtra(ID_LOAN);
    }
}
