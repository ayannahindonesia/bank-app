package com.ayannah.asira.screen.agent.viewBorrower;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.model.NasabahAgent;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ViewBorrowerPresenter implements ViewBorrowerContract.Presenter {

    @Nullable
    private ViewBorrowerContract.View mView;

    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;
    private PreferenceRepository prefRepo;

    @Inject
    ViewBorrowerPresenter(RemoteRepository remoteRepository, PreferenceRepository prefRepo){

        this.prefRepo = prefRepo;
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(ViewBorrowerContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getDataBorrower(String bankId) {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getListBorrower(bankId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            //success
            mView.getAllData(res.getTotalData(), res.getData());

        }, error ->{

            //error
            mView.showErrorMessage(CommonUtils.errorResponseGetCode(error));

        }));

    }

    @Override
    public void getLenderToken() {
        if (mView == null) {
            return;
        }

        mComposite.add(remoteRepository.getTokenLender()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {
            prefRepo.setPublicTokenLender("Bearer "+res.getToken());

            getTokenAdminLender();
        }, err -> {
            mView.showErrorMessage(CommonUtils.errorResponseWithStatusCode(err));
        }));
    }

    @Override
    public void getTokenAdminLender() {
        mComposite.add(remoteRepository.getTokenAdminLender()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    prefRepo.setAdminTokenLender("Bearer "+response.getToken());

                }, error -> {

                    mView.showErrorMessage(CommonUtils.errorResponseWithStatusCode(error));

                }));
    }
}
