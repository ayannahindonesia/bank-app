package com.ayannah.asira.screen.agent.loan;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.FormDynamic;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoanAgentPresenter implements LoanAgentContract.Presenter {

    private Application application;
    private RemoteRepository remoteRepository;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposite;

    @Nullable
    private LoanAgentContract.View mView;

    @Inject
    LoanAgentPresenter(Application application, RemoteRepository remoteRepository, PreferenceRepository preferenceRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(LoanAgentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getProducts(String idService) {
        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getAllProductsAgent(idService)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(resp -> {
            mView.successGetProducts(resp);
        }, err -> {
            mView.showErrorMessage(CommonUtils.commonErrorFormat(err));
        }));
    }

    @Override
    public void getLoanIntention() {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getReasons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    mView.showReason(response.getData());

                }, error -> mView.showErrorMessage(CommonUtils.commonErrorFormat(error))));

    }

    @Override
    public void setFormInfoToLocal(ArrayList<FormDynamic> arrFormForSend) {
        preferenceRepository.setFormInfoLocal(new Gson().toJson(arrFormForSend));
    }
}
