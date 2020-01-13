package com.ayannah.asira.screen.agent.lpagent;

import android.app.Application;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.BankServiceInterface;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LPAgentPresenter implements LPAgentContract.Presenter {

//    private Application application;
    @Nullable
    private LPAgentContract.View mView;
    private PreferenceRepository prefRepo;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    LPAgentPresenter(RemoteRepository remoteRepository, PreferenceRepository prefRepo) {

//        this.application = application;
        this.prefRepo = prefRepo;
        this.remotRepo = remoteRepository;
//
        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(LPAgentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }


    @Override
    public void getTokenLender() {
        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getTokenLender()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(res -> {

            prefRepo.setPublicTokenLender("Bearer "+ res.getToken());

            successGetPublicTokenLender();

        }, err -> {
            mView.showErrorMessage(CommonUtils.errorResponseWithStatusCode(err));
        }));
    }

    private void successGetPublicTokenLender() {
        if(mView == null){
            return;
        }

        mComposite.add(remotRepo.getTokenAdminLender()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    prefRepo.setAdminTokenLender("Bearer "+response.getToken());

                }, error -> {

                    ANError anError = (ANError) error;
                    if(anError.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)){
                        mView.showErrorMessage("Connection Error"  + " Code: "+anError.getErrorCode());
                    }else {

                        if(anError.getErrorBody() != null){

                            JSONObject jsonObject = new JSONObject(anError.getErrorBody());
                            mView.showErrorMessage(jsonObject.optString("message") + " Code: "+anError.getErrorCode());
                        }
                    }

                }));
    }
}
