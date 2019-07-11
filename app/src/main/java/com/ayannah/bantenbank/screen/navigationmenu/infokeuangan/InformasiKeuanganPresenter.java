package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.model.UserProfile;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class InformasiKeuanganPresenter implements InformasiKeuanganContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposite;
    private RemoteRepository remotrepo;

    @Nullable
    private InformasiKeuanganContract.View mView;

    @Inject
    InformasiKeuanganPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotrepo){
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remotrepo = remotrepo;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(InformasiKeuanganContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }

    @Override
    public void getInfoPekerjaanDanKeuangan() {

        if(mView == null){
            return;
        }

        mView.loadInfoPekerjaanDanKeuangan(preferenceRepository);

    }

    @Override
    public void patchJobEarningData(JsonObject jsonObject) {
        if (mView == null) {
            return;
        }

        mComposite.add(remotrepo.updateProfile(jsonObject)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            updateLocalData(response);

            Toast.makeText(application, "Data Berhasil Dirubah", Toast.LENGTH_LONG).show();

        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObjectError = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObjectError.optString("message"));
                }
            }

        }));
    }

    private void updateLocalData(UserProfile response) {
        preferenceRepository.setFieldToWork(response.getFieldOfWork());
        preferenceRepository.setDepartment(response.getDepartment());
        preferenceRepository.setEmployeeId(response.getEmployeeId());
        preferenceRepository.setEmployerName(response.getEmployerName());
        preferenceRepository.setBeenWorkingFor(String.valueOf(response.getBeenWorkingfor()));
        preferenceRepository.setEmployerAddress(response.getEmployerAddress());
        preferenceRepository.setEmployerNumber(response.getEmployerNumber());
        preferenceRepository.setDirectSuperiorName(response.getDirectSuperiorname());
        preferenceRepository.setOccupation(response.getOccupation());
        preferenceRepository.setUserPrimaryIncome(String.valueOf(response.getMonthlyIncome()));
        preferenceRepository.setUserOtherIncome(String.valueOf(response.getOtherIncome()));
        preferenceRepository.setuserOtherSourceIncome(response.getOtherIncomesource());

        assert mView != null;
        mView.successUpdateJobEarningData();
    }
}
