package com.ayannah.asira.data.remote;

import com.ayannah.asira.data.model.AgentProfile;
import com.ayannah.asira.data.model.AgentProviderDetail;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.BankList;
import com.ayannah.asira.data.model.BankService;
import com.ayannah.asira.data.model.CheckAccount;
import com.ayannah.asira.data.model.CheckBorrower;
import com.ayannah.asira.data.model.FCMTokenResponse;
import com.ayannah.asira.data.model.Kabupaten;
import com.ayannah.asira.data.model.Kecamatan;
import com.ayannah.asira.data.model.Kelurahan;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.Loans.Loans;
import com.ayannah.asira.data.model.NasabahAgent;
import com.ayannah.asira.data.model.Notif;
import com.ayannah.asira.data.model.Provinsi;
import com.ayannah.asira.data.model.ReasonLoan;
import com.ayannah.asira.data.model.ServiceProducts;
import com.ayannah.asira.data.model.ServiceProductsAgent;
import com.ayannah.asira.data.model.Token;
import com.ayannah.asira.data.model.UserProfile;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import okhttp3.Response;

public interface RemoteRepository {

    Single<Provinsi> getProvinsi();

    Single<Kabupaten> getKabupaten(String idProvinsi);

    Single<Kecamatan> getKecamatan(String idKabupaten);

    Single<Kelurahan> getKelurahan(String idKecamatan);

    Single<Token> getTokenLender();

    Single<Token> getToken();

    Single<Token> getTokenClient(JsonObject json);

    Single<UserProfile> getUserLogin();

    Single<Response> postBorrowerRegister(JsonObject jsonObject);

    void postOTPRequestBorrower(JsonObject json);

    Single<Response> postVerifyOTP(JsonObject jsonObject);

    Single<Loans> getAllLoans(String sortByStatus);

    Single<DataItem> postLoan(JsonObject json);

    void getOTPForLoan(String idLoan);

    void verifiedLoanByOTP(String idLoan, JsonObject json);

    Single<DataItem> getLoanDetails(String idLoan);

    Single<UserProfile> updateProfile(JsonObject json);

    Single<CheckAccount>checkAccount(String email, String phone, String idcard, String taxid);

    Single<CheckAccount> sendEmailToResetPassword(JsonObject jsonObject);

    Single<UserProfile> postNewPassword(JsonObject jsonObject);

    Single<BankDetail> getBanksDetail(String bankID);

    Single<BankList> getAllBanks();

    Single<BankService> getBankServices();

    Single<ServiceProducts> getAllProducts(String idService);

    Single<ServiceProducts> getAllProductsGlobal();

    Single<ReasonLoan> getReasons();

    Single<Token> getTokenAdminLender();

    Single<FCMTokenResponse> sendUserFCMToken(String fcmToken);

    Single<Token> getClientAgentToken(JsonObject json);

    Single<AgentProfile> getAgentProfile();

    Single<Notif> getListNotification();

    Single<Response> postBorrowerRegisterAgent(JsonObject jsonObject);

    Single<BankList> getAllBanksAgent();

    Single<NasabahAgent> getListBorrower(String idBank);

    Single<CheckBorrower> checkExistingBorrowerAgent(JsonObject paramCheckBorrower);

    Single<BankService> getServicesAgent(String bank_id);

    Single<ServiceProductsAgent> getAllProductsAgent(String idService);

    Single<AgentProfile> patchAgentProfile(JSONObject jsonPatchAgentProfile);

    Single<AgentProviderDetail> getAgentProvider(String agentProvider);
}
