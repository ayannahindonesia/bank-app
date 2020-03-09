package com.ayannah.asira.screen.agent.listloan.pengajuan;

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

public class PengajuanPresenter implements PengajuanContract.Presenter {


    private static final String TAG = PengajuanPresenter.class.getSimpleName();

    private PengajuanContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;

    @Inject
    PengajuanPresenter(RemoteRepository remoteRepository){

        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();


    }

    @Override
    public void takeView(PengajuanContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getOnProcessLoan(int idbank) {

        if(mView == null){
            return;
        }

//        mComposite.add(remoteRepository.getAgentLoan(String.valueOf(idbank))
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(res -> {
//
//            Log.e(TAG, "total: "+res.getTotalData());
//
//            if(res.getTotalData() > 0) {
//
//                List<DataItem> processing = new ArrayList<>();
//
//                for(DataItem item: res.getData()){
//
//                    if(item.getStatus().contains("processing")){
//                        processing.add(item);
//                    }
//                }
//
//                mView.showOnProcessLoan(processing);
//
//            }
//        }, error ->{
//
//            mView.showErrorMessage(CommonUtils.errorResponseGetCode(error));
//        }));



    }
}
