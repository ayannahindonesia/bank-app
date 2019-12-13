package com.ayannah.asira.screen.agent.listloan.pencairan;

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

public class PencairanPresenter implements PencairanContract.Presenter {

    private static final String TAG = PencairanPresenter.class.getSimpleName();

    private PencairanContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remoteRepository;

    @Inject
    PencairanPresenter(RemoteRepository remoteRepository){

        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(PencairanContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }

    @Override
    public void getOnPencairan(int idBank) {

        if(mView == null){
            return;
        }

        mComposite.add(remoteRepository.getAgentLoan(String.valueOf(idBank))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    Log.e(TAG, "total: "+res.getTotalData());

                    if(res.getTotalData() > 0) {

                        List<DataItem> processing = new ArrayList<>();

                        for(DataItem item: res.getData()){

                            if(item.getStatus().contains("approved")){
                                processing.add(item);
                            }
                        }

                        mView.showOnPencairan(processing);

                    }
                }, error ->{

                    mView.showErrorMessage(CommonUtils.errorResponseGetCode(error));
                }));
    }
}
