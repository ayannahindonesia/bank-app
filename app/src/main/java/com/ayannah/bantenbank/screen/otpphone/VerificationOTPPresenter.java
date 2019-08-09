package com.ayannah.bantenbank.screen.otpphone;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.bantenbank.BuildConfig;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.ayannah.bantenbank.screen.homemenu.MainMenuActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class VerificationOTPPresenter implements VerificationOTPContract.Presenter {
    private static final String TAG = VerificationOTPPresenter.class.getSimpleName();

    private Application application;
    private PreferenceRepository preferenceRepository;
    private VerificationOTPContract.View mView;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    public VerificationOTPPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo) {
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.mComposite = new CompositeDisposable();
        this.remotRepo = remotRepo;
    }

    @Override
    public void takeView(VerificationOTPContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void postOTPVerify(JsonObject jsonObject) {
        mComposite.add(remotRepo.postVerifyOTP(jsonObject)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            Log.d("Succeess: ", "OTP Verified");
            Toast.makeText(application, "Pendaftaran Berhasil!", Toast.LENGTH_LONG).show();
            mView.OTPVerified();

        }, error -> {

            if (((ANError) error).getErrorCode() == 400) {
                mView.showErrorMessage("OTP Salah");
//                Toast.makeText(application, "Wrong OTP", Toast.LENGTH_LONG).show();
            } else if (((ANError) error).getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Tidak Ada Koneksi");
//                Toast.makeText(application, "Tidak Ada Koneksi", Toast.LENGTH_LONG).show();
            } else {
                if(((ANError) error).getErrorBody() != null){

                    JSONObject jsonObject2 = new JSONObject(((ANError) error).getErrorBody());
                    mView.showErrorMessage(jsonObject2.optString("message"));

                }
            }

//            ANError anError = (ANError) error;
//            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
////                            mView.showErrorMessage("Connection Error");
//                Toast.makeText(application, "Connection Error", Toast.LENGTH_LONG).show();
//            } else {
//
//                if (anError.getErrorBody() != null) {
//
//                    JSONObject jsonObject2 = new JSONObject(anError.getErrorBody());
//                    Toast.makeText(application, jsonObject2.optString("message"), Toast.LENGTH_LONG).show();
//                }
//            }

        }));
    }

    @Override
    public void postVerifyLoanByOTP(String idloan, JsonObject json) {

        if(mView == null){

            return;
        }

        mComposite.add(Completable.fromAction(() -> {

//            remotRepo.verifiedLoanByOTP(idloan, json);
            AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan/{idloan}/verify")
                    .addHeaders("Authorization", preferenceRepository.getUserToken())
                    .addPathParameter("idloan", idloan)
                    .addApplicationJsonBody(json)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("verify Loan: ", "Sukses");
                            mView.successVerifyLoan();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.d("verify Loan: ", "gagal");
                            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                                mView.showErrorMessage("Tidak Ada Koneksi");
                            } else {
                                if(anError.getErrorBody() != null){

                                    JSONObject jsonObject2 = null;
                                    try {
                                        jsonObject2 = new JSONObject(anError.getErrorBody());
                                        mView.showErrorMessage(jsonObject2.optString("message"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    });

//            mView.successVerifyLoan();

        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe());

    }

    @Override
    public void resubmitLoanOTP(int idloan, String otpCode) {

        JsonObject json = new JsonObject();
        json.addProperty("otp_code", otpCode);

        AndroidNetworking.post(BuildConfig.API_URL + "borrower/loan/{idloan}/verify")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("idloan", String.valueOf(idloan))
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        mView.successVerifyLoan();

                    }

                    @Override
                    public void onError(ANError anError) {

                        mView.showErrorMessage(String.format("status code %s", anError.getErrorCode()));

                    }
                });
    }

    @Override
    public void getPublicToken(String phone, String pass, String isFrom) {
        mComposite.add(remotRepo.getToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    preferenceRepository.setPublicToken("Bearer "+response.getToken());

                    Log.d(TAG, "public_token: "+preferenceRepository.getPublicToken());

                    getClientToken(phone, pass, isFrom);

                }));
    }

    @Override
    public void setUserIdentity() {
        if(mView == null){
            Toast.makeText(application, "something wrong in setUserIdentity()", Toast.LENGTH_SHORT).show();
            return;
        }

        mComposite.add(remotRepo.getUserLogin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    preferenceRepository.setIdUser(String.valueOf(response.getId()));
                    preferenceRepository.setUserEmail(response.getEmail());
                    preferenceRepository.setUserName(response.getFullname());
                    preferenceRepository.setUserPhone(response.getPhone());
                    preferenceRepository.setUserNIP(String.valueOf(response.getIdcardNumber()));
                    preferenceRepository.setUserGender(response.getGender());

                    preferenceRepository.setIdCardUser(response.getIdcardNumber());
                    preferenceRepository.setTaxCard(response.getTaxidNumber());
                    preferenceRepository.setUserBirtday(response.getBirthday());
                    preferenceRepository.setUserBirthPlace(response.getBirthplace());
                    preferenceRepository.setUserLastEducation(response.getLastEducation());
                    preferenceRepository.setUserMotherName(response.getMotherName());
                    preferenceRepository.setUserMarriageStatus(response.getMarriageStatus());
                    preferenceRepository.setUserSpouseName(response.getSpouseName());
                    preferenceRepository.setSpouseBirthDate(response.getSpouseBirthday());
                    preferenceRepository.setUserAddress(response.getAddress());
                    preferenceRepository.setUserProvince(response.getProvince());
                    preferenceRepository.setUserCity(response.getCity());
                    preferenceRepository.setUserNeighbourAssociation(response.getNeighbourAssociation());
                    preferenceRepository.setUserHamlets(response.getHamlets());
                    preferenceRepository.setUserHomePhoneNumber(response.getHomePhonenumber());
                    preferenceRepository.setSubDistrict(response.getSubdistrict());
                    preferenceRepository.setUrbanVillage(response.getUrbanVillage());
                    preferenceRepository.setHomeOwnerShip(response.getHomeOwnership());
                    preferenceRepository.setLivedFor(String.valueOf(response.getLivedFor()));
                    preferenceRepository.setOccupation(response.getOccupation());
                    preferenceRepository.setEmployeeId(response.getEmployeeId());
                    preferenceRepository.setEmployerName(response.getEmployerName());
                    preferenceRepository.setEmployerAddress(response.getEmployerAddress());
                    preferenceRepository.setDepartment(response.getDepartment());
                    preferenceRepository.setBeenWorkingFor(String.valueOf(response.getBeenWorkingfor()));
                    preferenceRepository.setDirectSuperiorName(response.getDirectSuperiorname());
                    preferenceRepository.setEmployerNumber(response.getEmployerNumber());
                    preferenceRepository.setFieldToWork(response.getFieldOfWork());
                    preferenceRepository.setUserRelatedPersonName(response.getRelatedPersonname());
                    preferenceRepository.setUserRelatedRelation(response.getRelatedRelation());
                    preferenceRepository.setUserRelatedPhoneNumber(response.getRelatedPhonenumber());
                    preferenceRepository.setUserRelatedHomeNumber(response.getRelatedHomenumber());
                    preferenceRepository.setUserRelatedAddress(response.getRelatedAddress());
                    preferenceRepository.setDependants(response.getDependants());

                    preferenceRepository.setUserPrimaryIncome(String.valueOf(response.getMonthlyIncome()));
                    preferenceRepository.setUserOtherIncome(String.valueOf(response.getOtherIncome()));
                    preferenceRepository.setuserOtherSourceIncome(response.getOtherIncomesource());

                    preferenceRepository.setUserLogged(true);
                    preferenceRepository.setUserSetup(true);

                    mView.loginComplete();

                    Log.d(TAG, "function loginComplete() executedd!");

                }, error ->{

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error");
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message") + " setUserIdentity()");
                        }
                    }

                }));
    }

    private void getClientToken(String phone, String pass, String isFrom) {
        JsonObject json = new JsonObject();
        json.addProperty("key", phone);
        json.addProperty("password", pass);

        Log.d(TAG, preferenceRepository.getPublicToken());

        mComposite.add(remotRepo.getTokenClient(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    preferenceRepository.setUserToken("Bearer "+response.getToken());

                    Log.d(TAG, "create token client complete");

                    //set User Identity
                    if (isFrom.equals("otp")) {
                        mView.completeCreateUserToken();
                    }

                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error");
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message")  + " getClientToken()");
                        }
                    }

                }));
    }
}
