package com.ayannah.bantenbank.di;

import com.ayannah.bantenbank.screen.earninginfo.EarningActivity;
import com.ayannah.bantenbank.screen.earninginfo.EarningModule;
import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;
import com.ayannah.bantenbank.screen.homemenu.MainMenuModule;
import com.ayannah.bantenbank.screen.loan.LoanActivity;
import com.ayannah.bantenbank.screen.loan.LoanModule;
import com.ayannah.bantenbank.screen.login.LoginActivity;
import com.ayannah.bantenbank.screen.login.LoginModule;
import com.ayannah.bantenbank.screen.navigationmenu.akunsaya.AkunSayaActivity;
import com.ayannah.bantenbank.screen.navigationmenu.akunsaya.AkunSayaModule;
import com.ayannah.bantenbank.screen.navigationmenu.datapendukung.DataPendukungActivity;
import com.ayannah.bantenbank.screen.navigationmenu.datapendukung.DataPendukungModule;
import com.ayannah.bantenbank.screen.navigationmenu.infokeuangan.InformasiKeuanganActivity;
import com.ayannah.bantenbank.screen.navigationmenu.infokeuangan.InformasiKeuanganModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainMenuModule.class)
    abstract MainMenuActivity mainMenuActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AkunSayaModule.class)
    abstract AkunSayaActivity akunSayaActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = InformasiKeuanganModule.class)
    abstract InformasiKeuanganActivity informasiKeuanganActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DataPendukungModule.class)
    abstract DataPendukungActivity dataPendukungActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoanModule.class)
    abstract LoanActivity loanActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = EarningModule.class)
    abstract EarningActivity earningActivity();

}
