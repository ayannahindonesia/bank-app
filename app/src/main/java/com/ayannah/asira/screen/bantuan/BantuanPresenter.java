package com.ayannah.asira.screen.bantuan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
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

    @Inject
    BantuanPresenter(Application applicationa, RemoteRepository remoteRepository){
        this.applicationa = applicationa;
        this.remoteRepository = remoteRepository;

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
    public void retrieveFaq() {

        if(mView == null){
            return;
        }

        mDisposable.add(
                remoteRepository.faq()
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
