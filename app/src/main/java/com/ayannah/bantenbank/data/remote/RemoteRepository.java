package com.ayannah.bantenbank.data.remote;

import com.ayannah.bantenbank.data.model.BankDetail;
import com.ayannah.bantenbank.data.model.BankList;
import com.ayannah.bantenbank.data.model.CheckAccount;
import com.ayannah.bantenbank.data.model.Kabupaten;
import com.ayannah.bantenbank.data.model.Kecamatan;
import com.ayannah.bantenbank.data.model.Kelurahan;
import com.ayannah.bantenbank.data.model.Loans.DataItem;
import com.ayannah.bantenbank.data.model.Loans.Loans;
import com.ayannah.bantenbank.data.model.Provinsi;
import com.ayannah.bantenbank.data.model.Token;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.ayannah.bantenbank.data.model.Products;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import okhttp3.Response;

public interface RemoteRepository {

    Single<Provinsi> getProvinsi();

    Single<Kabupaten> getKabupaten(String idProvinsi);

    Single<Kecamatan> getKecamatan(String idKabupaten);

    Single<Kelurahan> getKelurahan(String idKecamatan);

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
}
