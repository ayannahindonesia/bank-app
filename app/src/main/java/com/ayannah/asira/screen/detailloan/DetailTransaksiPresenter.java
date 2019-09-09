package com.ayannah.asira.screen.detailloan;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailTransaksiPresenter implements DetailTransaksiContract.Presenter {

    @Nullable
    private DetailTransaksiContract.View mView;

    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    DetailTransaksiPresenter(RemoteRepository remotRepo){

        this.remotRepo = remotRepo;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void getInformationLoan(String idLoan) {

        if (mView == null){
            return;
        }

        mComposite.add(remotRepo.getLoanDetails(idLoan)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            mView.loadAllInformation(res);

        }, error -> {

            ANError anError = (ANError) error;
            if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                mView.showErrorMessage("Connection Error");
            }else {

                if(anError.getErrorBody() != null){

                    JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                    mView.showErrorMessage(jsonObject.optString("message"));
                }
            }
        }));

    }

    @Override
    public void checkLoanOnProcess() {

        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getAllLoans("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if(response.getData().size() > 0){

                        for(DataItem param: response.getData()){

                            if(param.getStatus().equals("processing")){

                                mView.showResultLoanOnProcess(true);
                                break;
                            }
                        }
                    }


                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error");
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message"));
                        }
                    }

                }));
    }

    @Override
    public void takeView(DetailTransaksiContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }
}
