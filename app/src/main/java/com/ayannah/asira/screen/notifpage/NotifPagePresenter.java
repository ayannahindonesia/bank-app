package com.ayannah.asira.screen.notifpage;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.adapter.CommonListAdapter;
import com.ayannah.asira.data.remote.RemoteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

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

        List<String> datas = new ArrayList<>();

        datas.add("test data 1 ini notifikasi data yang sedang diproses oleh bank XYZ");
        datas.add("test data 2 ini notifikasi data yang diterima oleh bank ABC dan akan cair pada tanggal 10 agustus 2020");

        mView.showDataNotif(datas);

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
