package com.ayannah.asira.screen.borrower.tab_profile;

import androidx.annotation.Nullable;

import com.androidnetworking.error.ANError;
import com.ayannah.asira.data.local.PreferenceRepository;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter implements ProfileContract.Presenter {

    @Nullable
    private ProfileContract.View mView;

    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mDisposable;
    private RemoteRepository remoteRepository;

    @Inject
    ProfilePresenter(PreferenceRepository preferenceRepository, RemoteRepository remoteRepository){
        this.preferenceRepository = preferenceRepository;
        this.remoteRepository = remoteRepository;

        mDisposable = new CompositeDisposable();

    }

    @Override
    public void getUserIdentity() {
        if(mView != null){

            mView.userIdentity(preferenceRepository);

        }
    }

    @Override
    public void doLogout() {

        if(mView == null){
            return;
        }

        mDisposable.add(remoteRepository.sendUserFCMToken("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {

                    preferenceRepository.clearAll();

                    mView.logoutComplete();

                }, error ->{

                    ANError anError = (ANError) error;
                    mView.showErrorMessage("Terjadi kesalahan saat logout", anError.getErrorCode());

                }));
    }

    @Override
    public void takeView(ProfileContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;

    }
}
