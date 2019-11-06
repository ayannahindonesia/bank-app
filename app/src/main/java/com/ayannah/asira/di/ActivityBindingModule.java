package com.ayannah.asira.di;

import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.ayannah.asira.screen.agent.lpagent.LPAgentModule;
import com.ayannah.asira.screen.createnewpassword.CreateNewPassActivity;
import com.ayannah.asira.screen.createnewpassword.CreateNewPassModule;
import com.ayannah.asira.screen.detailloan.DetailTransaksiActivity;
import com.ayannah.asira.screen.detailloan.DetailTransaksiModule;
import com.ayannah.asira.screen.earninginfo.EarningActivity;
import com.ayannah.asira.screen.earninginfo.EarningModule;
import com.ayannah.asira.screen.historyloan.HistoryLoanActivity;
import com.ayannah.asira.screen.historyloan.HistoryLoanModule;
import com.ayannah.asira.screen.borrower.homemenu.MainMenuActivity;
import com.ayannah.asira.screen.borrower.homemenu.MainMenuModule;
import com.ayannah.asira.screen.loan.LoanActivity;
import com.ayannah.asira.screen.loan.LoanModule;
import com.ayannah.asira.screen.borrower.login.LoginActivity;
import com.ayannah.asira.screen.borrower.login.LoginModule;
import com.ayannah.asira.screen.borrower.navigationmenu.akunsaya.AkunSayaActivity;
import com.ayannah.asira.screen.borrower.navigationmenu.akunsaya.AkunSayaModule;
import com.ayannah.asira.screen.borrower.navigationmenu.datapendukung.DataPendukungActivity;
import com.ayannah.asira.screen.borrower.navigationmenu.datapendukung.DataPendukungModule;
import com.ayannah.asira.screen.borrower.navigationmenu.infokeuangan.InformasiKeuanganActivity;
import com.ayannah.asira.screen.borrower.navigationmenu.infokeuangan.InformasiKeuanganModule;
import com.ayannah.asira.screen.borrower.navigationmenu.infopribadi.InfoPribadiActivity;
import com.ayannah.asira.screen.borrower.navigationmenu.infopribadi.InfoPribadiModule;
import com.ayannah.asira.screen.borrower.notifpage.NotifPageActivity;
import com.ayannah.asira.screen.borrower.notifpage.NotifPageModule;
import com.ayannah.asira.screen.borrower.otpphone.VerificationOTPActivity;
import com.ayannah.asira.screen.borrower.otpphone.VerificationOTPModule;
import com.ayannah.asira.screen.register.addaccountbank.AddAccountBankActivity;
import com.ayannah.asira.screen.register.addaccountbank.AddAccountBankModule;
import com.ayannah.asira.screen.register.adddoc.AddDocumentActivity;
import com.ayannah.asira.screen.register.adddoc.AddDocumentModule;
import com.ayannah.asira.screen.register.choosebank.ChooseBankActivity;
import com.ayannah.asira.screen.register.choosebank.ChooseBankModule;
import com.ayannah.asira.screen.register.formBorrower.FormBorrowerActivity;
import com.ayannah.asira.screen.register.formBorrower.FormBorrowerModule;
import com.ayannah.asira.screen.register.formjobearning.FormJobEarningActivity;
import com.ayannah.asira.screen.register.formjobearning.FormJobEarningModule;
import com.ayannah.asira.screen.register.formothers.FormOtherActivity;
import com.ayannah.asira.screen.register.formothers.FormOtherModule;
import com.ayannah.asira.screen.resetpassword.ResetPasswordActivity;
import com.ayannah.asira.screen.resetpassword.ResetPasswordModule;
import com.ayannah.asira.screen.success.SuccessActivity;
import com.ayannah.asira.screen.success.SuccessModule;
import com.ayannah.asira.screen.summary.SummaryTransactionActivity;
import com.ayannah.asira.screen.summary.SummaryTransactionModule;

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

    @ActivityScoped
    @ContributesAndroidInjector(modules = DetailTransaksiModule.class)
    abstract DetailTransaksiActivity detailTransaksiActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ResetPasswordModule.class)
    abstract ResetPasswordActivity resetPasswordActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = CreateNewPassModule.class)
    abstract CreateNewPassActivity createNewPassActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = NotifPageModule.class)
    abstract NotifPageActivity notifPageActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LPAgentModule.class)
    abstract LPAgentActivity lpAgentActivity();

}
