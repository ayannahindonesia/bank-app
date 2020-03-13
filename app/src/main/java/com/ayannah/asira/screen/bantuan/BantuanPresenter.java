package com.ayannah.asira.screen.bantuan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BantuanPresenter implements BantuanContract.Presenter{

    @Nullable
    private BantuanContract.View mView;

    private Application applicationa;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mDisposable;
    private PreferenceRepository preferenceRepository;

    @Inject
    BantuanPresenter(Application applicationa, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.applicationa = applicationa;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(BantuanContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
        mDisposable.clear();

    }

    @Override
    public void retrieveFaq(String query) {

        if(mView == null){
            return;
        }

        //get client auth
        if(preferenceRepository.getPublicToken() == null || preferenceRepository.getPublicToken().isEmpty()){

            mDisposable.add(remoteRepository.getToken()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {

                        preferenceRepository.setPublicToken("Bearer " + res.getToken());
                        faq(query);

                    }, err -> {
                        ANError anError = (ANError) err;
                        mView.showErrorMessage(err.getMessage(),anError.getErrorCode());
                    }));

        }else {

            faq(query);

        }
    }

    private void faq(String query){

        if(mView == null){
            return;
        }

        mDisposable.add(
                remoteRepository.faq(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response ->{

                            mView.showAllResult(response.getData());

                        }, error ->{

                            ANError anError = (ANError) error;
                            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                                mView.showErrorMessage("Tidak ada koneksi", anError.getErrorCode());
                            }else {

                                if(anError.getErrorBody() != null){

                                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                                    mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());
                                }else {

                                    mView.showErrorMessage( "Mohon coba beberapa saat lagi. Sedang dalam perbaikan",anError.getErrorCode());
                                }
                            }

                        }));
    }
}
