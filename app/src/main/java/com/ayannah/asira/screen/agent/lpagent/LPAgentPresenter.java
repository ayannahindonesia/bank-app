package com.ayannah.asira.screen.agent.lpagent;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.local.BankServiceInterface;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LPAgentPresenter implements LPAgentContract.Presenter {

    private Application application;
    @Nullable
    private LPAgentContract.View mView;
    private PreferenceRepository prefRepo;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Inject
    LPAgentPresenter(Application application, PreferenceRepository prefRepo, RemoteRepository remotRepo) {

        this.application = application;
        this.prefRepo = prefRepo;
        this.remotRepo = remotRepo;

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
    public void getCurrentAgentIdentity() {
        if(mView == null){
            return;
        }

        mView.displayUserIdentity(prefRepo.getAgentName(), prefRepo.getAgentUserName(), prefRepo.getAgentId(), prefRepo.getAgentProvider());
    }

    @Override
    public void logout() {
        prefRepo.clearAll();

        mView.successsLogout();
    }
}
