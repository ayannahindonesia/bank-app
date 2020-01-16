package com.ayannah.asira.screen.agent.listloan;

import androidx.fragment.app.FragmentManager;

import com.ayannah.asira.adapter.TabAdapterListLoan;
import com.ayannah.asira.di.ActivityScoped;
import com.ayannah.asira.di.FragmentScoped;
import com.ayannah.asira.screen.agent.listloan.ditolak.DitolakFragment;
import com.ayannah.asira.screen.agent.listloan.pencairan.PencairanFragment;
import com.ayannah.asira.screen.agent.listloan.pengajuan.PengajuanFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ListLoanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PengajuanFragment requestFragmentPengajuan();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PencairanFragment requestFragmentPecairan();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DitolakFragment requestFragmentDitolak();

    @ActivityScoped
    @Binds
    abstract ListLoanContract.Presenter reqPresenter(ListLoanPresenter presenter);

    @ActivityScoped
    @Provides
    static TabAdapterListLoan adapterListLoan(ListLoanActivtiy fm){
        return new TabAdapterListLoan(fm.getSupportFragmentManager());
    }

}
