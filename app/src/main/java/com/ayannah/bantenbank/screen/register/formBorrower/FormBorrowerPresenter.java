package com.ayannah.bantenbank.screen.register.formBorrower;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.remote.RemoteRepository;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FormBorrowerPresenter implements FormBorrowerContract.Presenter{

    @Nullable
    private FormBorrowerContract.View mView;

    private Application app;
    private RemoteRepository remotRepo;
    private CompositeDisposable mDisposable;

    @Inject
    FormBorrowerPresenter(Application application, RemoteRepository remotRepo){
        this.app = application;
        this.remotRepo = remotRepo;

        mDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(FormBorrowerContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;
    }

    @Override
    public void getProvince() {

        if(mView == null){
            return;
        }

        mDisposable.add(remotRepo.getProvinsi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            if(!response.isSuccess()){

                Toast.makeText(app, response.getMessage(), Toast.LENGTH_SHORT).show();
                mView.showProvices(response.getSemuaprovinsi());
            }
        }, error ->{

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection error");
            }else {
                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"));

                }
            }

        }));

    }

    @Override
    public void getDistrict(String idProvince) {

        if(mView == null){
            return;
        }

        mDisposable.add(remotRepo.getKabupaten(idProvince)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.isSuccess()){
                        mView.showDistrict(response.getDaftarKabupaten());
                    }
                }, error ->{

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection error");
                    }else {
                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));

                        }
                    }

                }));

    }

    @Override
    public void getSubDistrict(String idDistrict) {

        if(mView == null){
            return;
        }

        mDisposable.add(remotRepo.getKecamatan(idDistrict)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.isSuccess()){
                        mView.showSubDistrict(response.getDaftarKecamatan());
                    }
                }, error ->{

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection error");
                    }else {
                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));

                        }
                    }

                }));
    }

    @Override
    public void getKelurahan(String idSubDistrict) {

        if(mView == null){
            return;
        }

        mDisposable.add(remotRepo.getKelurahan(idSubDistrict)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(!response.isSuccess()){
                        mView.showKelurahan(response.getDaftarDesa());
                    }
                }, error ->{

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection error");
                    }else {
                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));

                        }
                    }

                }));

    }

    @Override
    public void getUser() {

        if(mView == null){
            return;
        }

        mDisposable.add(remotRepo.getToken()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

        }, error ->{

            ANError anError = (ANError)error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error");
            }else {

                if(anError.getErrorBody() != null){
                    JSONObject object = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(object.optString("message"));
                }
            }
        }));
    }


}
