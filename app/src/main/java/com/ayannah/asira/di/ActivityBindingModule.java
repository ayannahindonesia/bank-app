package com.ayannah.asira.di;

import com.ayannah.asira.screen.agent.loan.LoanAgentActivity;
import com.ayannah.asira.screen.agent.loan.LoanAgentModule;
import com.ayannah.asira.screen.agent.loginagent.LoginAgentActivity;
import com.ayannah.asira.screen.agent.loginagent.LoginAgentModule;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.ayannah.asira.screen.agent.lpagent.LPAgentModule;
import com.ayannah.asira.screen.agent.navigationmenu.agentprofile.AgentProfileActivity;
import com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks.AgentProfileBankListActivity;
import com.ayannah.asira.screen.agent.navigationmenu.agentprofile.ListBanks.AgentProfileBankListModule;
import com.ayannah.asira.screen.agent.navigationmenu.agentprofile.AgentProfileModule;
import com.ayannah.asira.screen.agent.registerborrower.addaccountbank.AddAccountBankAgentActivity;
import com.ayannah.asira.screen.agent.registerborrower.addaccountbank.AddAccountBankAgentModule;
import com.ayannah.asira.screen.agent.registerborrower.adddoc.AddDocumentAgentActivity;
import com.ayannah.asira.screen.agent.registerborrower.adddoc.AddDocumentAgentModule;
import com.ayannah.asira.screen.agent.registerborrower.choosebank.ChooseBankAgentActivity;
import com.ayannah.asira.screen.agent.registerborrower.choosebank.ChooseBankAgentModule;
import com.ayannah.asira.screen.agent.registerborrower.formborrower.FormBorrowerAgentActivity;
import com.ayannah.asira.screen.agent.registerborrower.formborrower.FormBorrowerAgentModule;
import com.ayannah.asira.screen.agent.registerborrower.formother.FormOtherAgentActivity;
import com.ayannah.asira.screen.agent.registerborrower.formother.FormOtherAgentModule;
import com.ayannah.asira.screen.agent.registerborrower.jobearning.FormJobEarningAgentActivity;
import com.ayannah.asira.screen.agent.registerborrower.jobearning.FormJobEarningAgentModule;
import com.ayannah.asira.screen.agent.services.ListServicesAgentActivity;
import com.ayannah.asira.screen.agent.services.ListServicesAgentModule;
import com.ayannah.asira.screen.agent.viewBorrower.ViewBorrowerActivity;
import com.ayannah.asira.screen.agent.viewBorrower.ViewBorrowerModule;
import com.ayannah.asira.screen.borrower.navigationmenu.editinfopribadi.EditInfoPribadiActivity;
import com.ayannah.asira.screen.borrower.navigationmenu.editinfopribadi.EditInfoPribadiModule;
import com.ayannah.asira.screen.chooselogin.ChooseLoginActivity;
import com.ayannah.asira.screen.chooselogin.ChooseLoginModule;
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

    @ActivityScoped
    @ContributesAndroidInjector(modules = ChooseLoginModule.class)
    abstract ChooseLoginActivity chooseLoginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginAgentModule.class)
    abstract LoginAgentActivity loginAgentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ChooseBankAgentModule.class)
    abstract ChooseBankAgentActivity chooseBankAgentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddAccountBankAgentModule.class)
    abstract AddAccountBankAgentActivity addAccountBankAgentModule();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AddDocumentAgentModule.class)
    abstract AddDocumentAgentActivity addDocumentAgentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FormBorrowerAgentModule.class)
    abstract FormBorrowerAgentActivity formBorrowerAgentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FormJobEarningAgentModule.class)
    abstract FormJobEarningAgentActivity formJobEarningAgentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FormOtherAgentModule.class)
    abstract FormOtherAgentActivity formOtherAgentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ListServicesAgentModule.class)
    abstract ListServicesAgentActivity listServicesAgentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = EditInfoPribadiModule.class)
    abstract EditInfoPribadiActivity editInfoPribadiActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ViewBorrowerModule.class)
    abstract ViewBorrowerActivity viewBorrowerActivtiy();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoanAgentModule.class)
    abstract LoanAgentActivity loanAgentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AgentProfileModule.class)
    abstract AgentProfileActivity agentProfileActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = AgentProfileBankListModule.class)
    abstract AgentProfileBankListActivity agentProfileBankListActivity();
}
