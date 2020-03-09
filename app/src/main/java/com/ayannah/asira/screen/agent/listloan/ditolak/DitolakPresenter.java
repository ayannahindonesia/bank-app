package com.ayannah.asira.screen.agent.listloan.ditolak;

import android.util.Log;

import com.ayannah.asira.data.model.DummyLoanBorrower;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DitolakPresenter implements DitolakContract.Presenter {

    private static final String TAG = DitolakPresenter.class.getSimpleName();

    private DitolakContract.View mView;

    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    DitolakPresenter(RemoteRepository remoteRepository){

        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(DitolakContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }

    @Override
    public void getOnDitolak(int bankId) {
        if(mView == null){
            return;
        }

//        mComposite.add(remoteRepository.getAgentLoan(String.valueOf(bankId))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(res -> {
//
//                    Log.e(TAG, "total: "+res.getTotalData());
//
//                    if(res.getTotalData() > 0) {
//
//                        List<DataItem> processing = new ArrayList<>();
//
//                        for(DataItem item: res.getData()){
//
//                            if(item.getStatus().contains("rejected")){
//                                processing.add(item);
//                            }
//                        }
//
//                        mView.showOnDitolakLoan(processing);
//
//                    }
//                }, error ->{
//
//                    mView.showErrorMessage(CommonUtils.errorResponseGetCode(error));
//                }));
    }
}
