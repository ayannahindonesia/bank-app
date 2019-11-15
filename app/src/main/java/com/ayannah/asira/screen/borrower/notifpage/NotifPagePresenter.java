package com.ayannah.asira.screen.borrower.notifpage;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotifPagePresenter implements NotifPageContract.Presenter {

    private Application application;
    private CompositeDisposable mComposite;
    private RemoteRepository remotRepo;

    @Nullable
    private NotifPageContract.View mView;

    @Inject
    NotifPagePresenter(Application application, RemoteRepository remotRepo){
        this.remotRepo = remotRepo;
        this.application = application;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void getListNotification() {


        if(mView == null){
            return;
        }

//        List<String> datas = new ArrayList<>();
//
//        datas.add("test data 1 ini notifikasi data yang sedang diproses oleh bank XYZ");
//        datas.add("test data 2 ini notifikasi data yang diterima oleh bank ABC dan akan cair pada tanggal 10 agustus 2020");
//
//        mView.showDataNotif(datas);

        mComposite.add(remotRepo.getListNotification()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe( list -> {

            if(list.getData().size() > 0){

                mView.showDataNotif(list.getData());

            }

        }, error -> mView.showErrorMessage(CommonUtils.commonErrorFormat(error))));

    }

    @Override
    public void takeView(NotifPageContract.View view) {

        mView = view;

    }

    @Override
    public void dropView() {

        mView = null;
    }
}
