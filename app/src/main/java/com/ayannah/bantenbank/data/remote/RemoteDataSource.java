package com.ayannah.bantenbank.data.remote;

import android.app.Application;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.bantenbank.BuildConfig;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.Kabupaten;
import com.ayannah.bantenbank.data.model.Kecamatan;
import com.ayannah.bantenbank.data.model.Kelurahan;
import com.ayannah.bantenbank.data.model.Loans.DataItem;
import com.ayannah.bantenbank.data.model.Loans.Loans;
import com.ayannah.bantenbank.data.model.OTPLoanResponse;
import com.ayannah.bantenbank.data.model.Provinsi;
import com.ayannah.bantenbank.data.model.Token;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.Credentials;
import okhttp3.Response;

public class RemoteDataSource implements RemoteRepository {

    private PreferenceRepository preferenceRepository;
    private Application application;

    @Inject
    RemoteDataSource(PreferenceRepository preferenceRepository, Application application){
        this.preferenceRepository = preferenceRepository;
        this.application = application;
    }

    @Override
    public Single<Provinsi> getProvinsi() {

        return Rx2AndroidNetworking.get(BuildConfig.API_INDONESIA_LOC + "daerahindonesia/provinsi")
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Provinsi.class);

    }

    @Override
    public Single<Kabupaten> getKabupaten(String idProvinsi) {
        return Rx2AndroidNetworking.get(BuildConfig.API_INDONESIA_LOC + "daerahindonesia/provinsi/{idprovinsi}/kabupaten")
                .addPathParameter("idprovinsi", idProvinsi)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kabupaten.class);
    }

    @Override
    public Single<Kecamatan> getKecamatan(String idKabupaten) {
        return Rx2AndroidNetworking.get(BuildConfig.API_INDONESIA_LOC + "daerahindonesia/provinsi/kabupaten/{idkabupaten}/kecamatan")
                .addPathParameter("idkabupaten", idKabupaten)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kecamatan.class);
    }

    @Override
    public Single<Kelurahan> getKelurahan(String idKecamatan) {
        return Rx2AndroidNetworking.get(BuildConfig.API_INDONESIA_LOC + "daerahindonesia/provinsi/kabupaten/kecamatan/{idkecamatan}/desa")
                .addPathParameter("idkecamatan", idKecamatan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Kelurahan.class);
    }

    @Override
    public Single<Token> getToken() {

        String credential = Credentials.basic("androkey", "androsecret");
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "clientauth")
                .addHeaders("Authorization", credential)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<Token> getTokenClient(JsonObject json) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "client/borrower_login")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Token.class);
    }

    @Override
    public Single<UserProfile> getUserLogin() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/profile")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(UserProfile.class);

    }

    @Override
    public Single<Loans> getAllLoans() {
        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addQueryParameter("rows", "")
                .addQueryParameter("page", "")
                .addQueryParameter("sort", "asc")
                .addQueryParameter("status", "")
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Loans.class);

    }

    @Override
    public Single<Response> postBorrowerRegister(JsonObject jsonObject) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL  + "client/register_borrower")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Response.class);
    }

    @Override
    public void postOTPRequestBorrower(JsonObject json) {
        AndroidNetworking.post(BuildConfig.API_URL + "unverified_borrower/otp_request")
                .addHeaders("Authorization", preferenceRepository.getPublicToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
//                            mView.showErrorMessage("Connection Error");
                            Toast.makeText(application, "Connection Error", Toast.LENGTH_LONG).show();
                        }else {

                            if(anError.getErrorBody() != null){

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(anError.getErrorBody());
                                    Toast.makeText(application, jsonObject.optString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                mView.showErrorMessage(jsonObject.optString("message"));
                            }
                        }
                    }
                });
    }

    @Override
    public Single<Response> postVerifyOTP(JsonObject jsonObject) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "unverified_borrower/otp_verify")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(Response.class);
    }

    @Override
    public Single<DataItem> postLoan(JsonObject json) {
        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getObjectSingle(DataItem.class);
    }

    @Override
    public void getOTPForLoan(String idLoan) {
//        return Rx2AndroidNetworking.get(BuildConfig.API_URL + "borrower/loan/{loan_id}/otp")
//                .addHeaders("Authorization", preferenceRepository.getUserToken())
//                .addPathParameter("loan_id", idLoan)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getObjectSingle(OTPLoanResponse.class);

        AndroidNetworking.get(BuildConfig.API_URL + "borrower/loan/{loan_id}/otp")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("loan_id", idLoan)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public void verifiedLoanByOTP(String idLoan, JsonObject json) {
//        return Rx2AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan/{idloan}/verify")
//                .addHeaders("Authorization", preferenceRepository.getUserToken())
//                .addApplicationJsonBody(json)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getObjectSingle(OTPLoanResponse.class);

        AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan/{idloan}/verify")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("idloan", idLoan)
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
