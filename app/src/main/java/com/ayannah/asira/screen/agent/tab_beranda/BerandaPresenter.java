package com.ayannah.asira.screen.agent.tab_beranda;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.R;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.MenuAgent;
import com.ayannah.asira.data.remote.RemoteRepository;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BerandaPresenter implements BerandaContract.Presenter {

    @Nullable
    private BerandaContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mDisposable;

    @Inject
    BerandaPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(BerandaContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;

    }

    @Override
    public void getUserAttributes() {

        if(mView == null){
            return;
        }

        mView.showUserAttributes(preferenceRepository.getAgentName(), preferenceRepository.getAgentPhone());

    }

    @Override
    public void fetchNasabah() {

        if(mView == null){
            return;
        }

        mDisposable.add(remoteRepository.getListBorrower_new(null)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response ->{

            mView.showRecentAgent(response.getData());

        }, error ->{

            ANError anError = (ANError) error;
            if (anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
                mView.showErrorMessage("Tidak Ada Koneksi", 0);
            } else {

                if(anError.getErrorBody() != null){
                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"), anError.getErrorCode());

                }else {

                    mView.showErrorMessage("Bad gateway", 0);

                }

            }

        }));
    }

    @Override
    public void postOTPRequestBorrowerAgent(String id) {

        if(mView == null){
            return;
        }

        mDisposable.add(Completable.fromAction(() -> {

            remoteRepository.postOTPRequestBorrowerAgent(id);

            mView.goToOTPInput(preferenceRepository.getAgentPhone(), id);

        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}
