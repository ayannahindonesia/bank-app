package com.ayannah.asira.screen.summary;

import com.ayannah.asira.data.model.Installments;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.ADMIN;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.ALASAN;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.ANGSURAN_BULAN;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.BANKACCOUNTNUMBER;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.BORROWERID;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.INSTALLMENT;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.INTEREST;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.LAYANAN;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.PENCAIRAN;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.PINJAMAN;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.PRODUCTID;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.TENOR;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.TUJUAN;
import static com.ayannah.asira.screen.summary.SummaryTransactionActivity.PRODUK;

@Module
public abstract class SummaryTransactionModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SummaryTransactionFragment requestFragment();

    @ActivityScoped
    @Binds
    abstract SummaryTransactionContract.Presenter requestPresenter(SummaryTransactionPresenter presenter);

    @Provides
    @ActivityScoped
    @Named("pinjaman")
    static int pinjaman(SummaryTransactionActivity activity){
        return activity.getIntent().getIntExtra(PINJAMAN, 0);
    }

    @Provides
    @ActivityScoped
    @Named("tenor")
    static int tenor(SummaryTransactionActivity activity){
        return activity.getIntent().getIntExtra(TENOR, 0);
    }

    @Provides
    @ActivityScoped
    @Named("angsuran_bulan")
    static double angsuranBulan(SummaryTransactionActivity activity){
        return activity.getIntent().getDoubleExtra(ANGSURAN_BULAN, 0);
    }

//    @Provides
//    @ActivityScoped
//    @Named("saldo")
//    static double saldoPinjaman(SummaryTransactionActivity activity){
//        return activity.getIntent().getDoubleExtra(SALDO_PINJAMAN, 0);
//    }

    @Provides
    @ActivityScoped
    @Named("alasan")
    static String alasan(SummaryTransactionActivity activity){
        return activity.getIntent().getStringExtra(ALASAN);
    }

    @Provides
    @ActivityScoped
    @Named("tujuan")
    static String tujuan(SummaryTransactionActivity activity){
        return activity.getIntent().getStringExtra(TUJUAN);
    }

    @Provides
    @ActivityScoped
    @Named("produk")
    static String produk(SummaryTransactionActivity activity){
        return activity.getIntent().getStringExtra(PRODUK);
    }


    @Provides
    @ActivityScoped
    @Named("admin")
    static int admin(SummaryTransactionActivity summaryTransactionActivity) {
        return summaryTransactionActivity.getIntent().getIntExtra(ADMIN, 0);
    }

//    @Provides
//    @ActivityScoped
//    @Named("interest")
//    static int interest(SummaryTransactionActivity summaryTransactionActivity) {
//        return summaryTransactionActivity.getIntent().getIntExtra(INTEREST, 0);
//    }

    @Provides
    @ActivityScoped
    @Named("layanan")
    static int layanan(SummaryTransactionActivity activity){
        return activity.getIntent().getIntExtra(LAYANAN, 0);
    }

    @Provides
    @ActivityScoped
    @Named("productid")
    static int productid(SummaryTransactionActivity activity){
        return activity.getIntent().getIntExtra(PRODUCTID, 0);
    }

    @Provides
    @ActivityScoped
    @Named("pencairan")
    static double pencairan(SummaryTransactionActivity activity){
        return activity.getIntent().getDoubleExtra(PENCAIRAN, 0);
    }

    @Provides
    @ActivityScoped
    @Named("borrowerID")
    static int borrowerID(SummaryTransactionActivity activity){
        return activity.getIntent().getIntExtra(BORROWERID, 0);
    }

    @Provides
    @ActivityScoped
    @Named("bankAccountNumber")
    static String bankAccountNumber(SummaryTransactionActivity activity){
        if (activity.getIntent().getStringExtra(BANKACCOUNTNUMBER) == null) {
            return "";
        } else {
            return activity.getIntent().getStringExtra(BANKACCOUNTNUMBER);
        }
    }

    @Provides
    @ActivityScoped
    @Named("installment")
    static ArrayList<Installments> installments(SummaryTransactionActivity activity) {
        return activity.getIntent().getParcelableArrayListExtra(INSTALLMENT);
    }
}
