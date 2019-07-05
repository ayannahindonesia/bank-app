package com.ayannah.bantenbank.screen.navigationmenu.infokeuangan;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.ayannah.bantenbank.data.local.PreferenceRepository;
import com.ayannah.bantenbank.data.remote.RemoteRepository;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class InformasiKeuanganPresenter implements InformasiKeuanganContract.Presenter {

    private Application application;
    private PreferenceRepository preferenceRepository;
    private CompositeDisposable mComposite;
    private RemoteRepository remotrepo;

    @Nullable
    private InformasiKeuanganContract.View mView;

    @Inject
    InformasiKeuanganPresenter(Application application, PreferenceRepository preferenceRepository, RemoteRepository remotrepo){
        this.application = application;
        this.preferenceRepository = preferenceRepository;
        this.remotrepo = remotrepo;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(InformasiKeuanganContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }

    @Override
    public void getInfoPekerjaanDanKeuangan() {

        if(mView == null){
            return;
        }

        mComposite.add(remotrepo.getUserLogin()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> {

            Toast.makeText(application, "berhasil", Toast.LENGTH_SHORT).show();

            mView.loadInfoPekerjaanDanKeuangan(response);

        }, error ->{

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
}
