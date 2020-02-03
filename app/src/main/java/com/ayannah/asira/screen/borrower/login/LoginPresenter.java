package com.ayannah.asira.screen.borrower.login;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;
import com.ayannah.asira.util.MyFirebaseMessagingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private Application application;
    private PreferenceRepository preferenceRepository;

    @Nullable
    private LoginContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    LoginPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotRepo){
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remotRepo = remotRepo;

        mComposite = new CompositeDisposable();
    }


    @Override
    public void getPublicToken(String phone, String pass) {

        mComposite.add(remotRepo.getToken()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            preferenceRepository.setPublicToken("Bearer "+response.getToken());

            Log.d(TAG, "public_token: "+preferenceRepository.getPublicToken());

            getClientToken(phone, pass);

        }, error -> {

//            assert mView != null;
//            mView.showErrorMessage(error.getMessage());
            ANError anError = (ANError) error;
            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                mView.showErrorMessage("Tidak Ada Koneksi");
            } else if (anError.getErrorBody() != null) {
                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                mView.showErrorMessage(jsonObject.optString("message ") + anError.getErrorBody());
            }

        }));

    }

    @Override
    public void getClientToken(String phone, String pass) {

        if(mView == null){
            Toast.makeText(application, "something wrong in getClientToken()", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("key", phone);
        json.addProperty("password", pass);

        Log.d(TAG, preferenceRepository.getPublicToken());

        mComposite.add(remotRepo.getTokenClient(json)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            preferenceRepository.setUserToken("Bearer "+response.getToken());
            Log.d(TAG, "userToken " + response.getToken());

            //set User Identity
            if(preferenceRepository.getUserToken() == null || preferenceRepository.getUserToken().equals("Bearer ") || preferenceRepository.getUserToken().equals("Bearer null")){

                mView.isAccountWrong();

            }else {
                mView.completeCreateUserToken();
            }


        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message") + " on getClientToken()");
                }
            }

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

            preferenceRepository.setBankAccountBorrower(response.getBankAccountnumber());

//            mView.loginComplete();
            sendFCMTokenUser();

        }, error ->{

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error");
            }else {

//                if(anError.getErrorBody() != null){
//
//                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
//                    mView.showErrorMessage(jsonObject.optString("message"));
//                }
                if(anError.getErrorCode() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    mView.showErrorMessage("No Telp/Kata Sandi Salah");
                } else if (anError.getErrorCode() == HttpsURLConnection.HTTP_FORBIDDEN) {
                    mView.accountNotOTP();
                }else {
//                    mView.showErrorMessage(anError.getMessage());
                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"));
                }
            }

        }));

    }

    private void sendFCMTokenUser() {

        if(mView == null){
            return;
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if(!task.isSuccessful()){

                    mView.errorFCM("retrieving token firebase not success. Plase contact developer");
                    return;
                }

                String token = task.getResult().getToken();

                mComposite.add(remotRepo.sendUserFCMToken(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(res -> {

                            mView.loginComplete();

                        }, error ->{

                            mView.showErrorMessage(CommonUtils.commonErrorFormat(error));

                        }));

            }
        })
        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mView.errorFCM(e.getMessage());

                    }
                });

    }

    @Override
    public boolean isUserLogged() {
        return preferenceRepository.isUserLogged();
    }

    @Override
    public void postRequestOTP(JsonObject jsonObject) {
        if (mView == null) {
            Toast.makeText(application, "something wrong in setUserIdentity()", Toast.LENGTH_SHORT).show();
            return;
        }

        mComposite.add(Completable.fromAction(() -> {
            remotRepo.postOTPRequestBorrower(jsonObject);
            mView.successGetOTP();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    @Override
    public void takeView(LoginContract.View view) {

        mView = view;

    }


    @Override
    public void dropView() {

        mView = null;
    }
}
