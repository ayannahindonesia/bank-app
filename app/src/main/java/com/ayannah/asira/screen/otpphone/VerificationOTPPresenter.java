package com.ayannah.asira.screen.otpphone;

import android.app.Application;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.rx2androidnetworking.Rx2AndroidNetworking;

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

            mView.OTPVerified();

        }, error -> {

             if (((ANError) error).getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){

                mView.showErrorMessage("Tidak Ada Koneksi", 0);

            } else {
                if(((ANError) error).getErrorBody() != null){

                    JSONObject jsonObject2 = new JSONObject(((ANError) error).getErrorBody());
                    mView.showErrorMessage(jsonObject2.optString("message"), ((ANError) error).getErrorCode());

                }
            }

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

                            mView.successVerifyLoan();
                        }

                        @Override
                        public void onError(ANError anError) {

                            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                                mView.showErrorMessage("Tidak Ada Koneksi", 0);
                            } else {
                                if(anError.getErrorBody() != null){

                                    JSONObject jsonObject2 = null;
                                    try {
                                        jsonObject2 = new JSONObject(anError.getErrorBody());
                                        mView.showErrorMessage(jsonObject2.optString("message"), anError.getErrorCode());
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
                        try {
                            JSONObject jsonObject2 = new JSONObject(anError.getErrorBody());
                            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                                mView.showErrorMessage("Tidak Ada Koneksi", 0);
                            } else {
                                if(anError.getErrorBody() != null){
                                    mView.showErrorMessage(jsonObject2.optString("message"), anError.getErrorCode());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                    preferenceRepository.setSpouseEducation(response.getSpouseLasteducation());
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

                    preferenceRepository.setIDCardImageID(response.getIdCardImage());
                    preferenceRepository.setTaxIDImageID(response.getTaxIDImage());
                    preferenceRepository.setBankID(response.getBank().getInt64());

                    preferenceRepository.setUserLogged(true);
                    preferenceRepository.setUserSetup(true);

                    //add new field for nickname and nationality
                    preferenceRepository.setUserNationality(response.getNationality());
                    preferenceRepository.setUserNickname(response.getNickname());


                    Log.e(TAG, "time to send token fcm device");
                    sendTokenDeviceToDB();
//                    mView.loginComplete();

                }, error ->{

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Tidak Ada Koneksi", 0);
                    }else {
                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                        }
                    }
                }));
    }

    @Override
    public void postVerifyLoanByOTPAgent(String id_loan, JsonObject json) {
        if(mView == null){

            return;
        }

        mComposite.add(Completable.fromAction(() -> {

            AndroidNetworking.post(BuildConfig.API_URL + "agent/loan/{idloan}/verify")
                    .addHeaders("Authorization", preferenceRepository.getUserToken())
                    .addPathParameter("idloan", id_loan)
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
                                mView.showErrorMessage("Tidak Ada Koneksi", 0);
                            } else {
                                if(anError.getErrorBody() != null){

                                    JSONObject jsonObject2 = null;
                                    try {
                                        jsonObject2 = new JSONObject(anError.getErrorBody());
                                        mView.showErrorMessage(jsonObject2.optString("message"), anError.getErrorCode());
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
    public void postOTPforRegisterBorrower(String id_borrower, String otp_code) {

        if(mView == null){
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("phone", preferenceRepository.getAgentPhone());
        json.addProperty("otp_code", otp_code);

        Rx2AndroidNetworking.post(BuildConfig.API_URL + "agent/otp_verify/{id}")
                .addHeaders("Authorization", preferenceRepository.getUserToken())
                .addPathParameter("id", id_borrower)
                .addApplicationJsonBody(json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        mView.successCreateBorrower();

                    }

                    @Override
                    public void onError(ANError anError) {

                        String msg = String.format("Kode OTP salah (%s)", anError.getErrorCode());

                        mView.showErrorMessage(msg, anError.getErrorCode());

                    }
                });
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

                    //set User Identity
                    if (isFrom.equals("otp")) {
                        mView.completeCreateUserToken();
                    }

                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Tidak ada koneksi", 0);
                    }else {
                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                        }
                    }
                }));
    }

    private void sendTokenDeviceToDB(){

        if(mView == null){
            return;
        }

        String token = FirebaseInstanceId.getInstance().getToken();

        mComposite.add(remotRepo.sendUserFCMToken(token)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            mView.loginComplete();

        }, error -> {

            ANError anError = (ANError) error;

            mView.showErrorMessage(CommonUtils.commonErrorFormat(error), anError.getErrorCode());


        }));

    }
}
