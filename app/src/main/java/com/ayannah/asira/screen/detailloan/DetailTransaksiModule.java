package com.ayannah.asira.screen.detailloan;

import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.di.ActivityScoped;

import java.util.Objects;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import static com.ayannah.asira.screen.detailloan.DetailTransaksiActivity.ID_LOAN;
import static com.ayannah.asira.screen.detailloan.DetailTransaksiActivity.LOAN_DETAIL;

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

    @Provides
    @ActivityScoped
    static DataItem loanDetails(DetailTransaksiActivity activity) {
        return (DataItem) Objects.requireNonNull(activity.getIntent().getExtras()).get(LOAN_DETAIL);
    }
}
