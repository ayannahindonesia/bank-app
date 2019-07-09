package com.ayannah.bantenbank.di;

import com.ayannah.bantenbank.screen.earninginfo.EarningActivity;
import com.ayannah.bantenbank.screen.earninginfo.EarningModule;
import com.ayannah.bantenbank.screen.historyloan.HistoryLoanActivity;
import com.ayannah.bantenbank.screen.historyloan.HistoryLoanModule;
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
import com.ayannah.bantenbank.screen.navigationmenu.infopribadi.InfoPribadiActivity;
import com.ayannah.bantenbank.screen.navigationmenu.infopribadi.InfoPribadiModule;
import com.ayannah.bantenbank.screen.otpphone.VerificationOTPActivity;
import com.ayannah.bantenbank.screen.otpphone.VerificationOTPModule;
import com.ayannah.bantenbank.screen.register.addaccountbank.AddAccountBankActivity;
import com.ayannah.bantenbank.screen.register.addaccountbank.AddAccountBankModule;
import com.ayannah.bantenbank.screen.register.adddoc.AddDocumentActivity;
import com.ayannah.bantenbank.screen.register.adddoc.AddDocumentModule;
import com.ayannah.bantenbank.screen.register.choosebank.ChooseBankActivity;
import com.ayannah.bantenbank.screen.register.choosebank.ChooseBankModule;
import com.ayannah.bantenbank.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.bantenbank.screen.register.formBorrower.FormBorrowerModule;
import com.ayannah.bantenbank.screen.register.formemailphone.FormEmailPhoneActivity;
import com.ayannah.bantenbank.screen.register.formemailphone.FormEmailPhoneModule;
import com.ayannah.bantenbank.screen.register.formjobearning.FormJobEarningActivity;
import com.ayannah.bantenbank.screen.register.formjobearning.FormJobEarningModule;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherActivity;
import com.ayannah.bantenbank.screen.register.formothers.FormOtherModule;
import com.ayannah.bantenbank.screen.success.SuccessActivity;
import com.ayannah.bantenbank.screen.success.SuccessModule;
import com.ayannah.bantenbank.screen.summary.SummaryTransactionActivity;
import com.ayannah.bantenbank.screen.summary.SummaryTransactionModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddAccountBankModule.class)
    abstract AddAccountBankActivity addAccountBankActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ChooseBankModule.class)
    abstract ChooseBankActivity chooseBankActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddDocumentModule.class)
    abstract AddDocumentActivity addDocumentActivity();

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

    @ActivityScoped
    @ContributesAndroidInjector(modules = HistoryLoanModule.class)
    abstract HistoryLoanActivity historyLoanActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FormEmailPhoneModule.class)
    abstract FormEmailPhoneActivity formEmailPhoneActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FormJobEarningModule.class)
    abstract FormJobEarningActivity formJobEarningActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FormBorrowerModule.class)
    abstract FormBorrowerActivity formBorrowerActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FormOtherModule.class)
    abstract FormOtherActivity formOtherActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = VerificationOTPModule.class)
    abstract VerificationOTPActivity verificationOTPActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = InfoPribadiModule.class)
    abstract InfoPribadiActivity infoPribadiActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SummaryTransactionModule.class)
    abstract SummaryTransactionActivity summaryTransactionActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SuccessModule.class)
    abstract SuccessActivity successActivity();


}
