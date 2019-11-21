package com.ayannah.asira.screen.agent.viewBorrower;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.model.NasabahAgent;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

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

    @Inject
    ViewBorrowerPresenter(RemoteRepository remoteRepository){

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
}
