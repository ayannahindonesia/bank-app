package com.ayannah.asira.screen.borrower.tab_message;

import android.app.Application;

import androidx.annotation.Nullable;

import com.ayannah.asira.data.model.Notif;
import com.ayannah.asira.data.remote.RemoteRepository;
import com.ayannah.asira.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MessagePresenter implements MessageContract.Presenter {

    @Nullable
    private MessageContract.View mView;

    private Application application;
    private RemoteRepository remoteRepository;
    private CompositeDisposable mComposite;

    @Inject
    MessagePresenter(Application application, RemoteRepository remoteRepository){
        this.application = application;
        this.remoteRepository = remoteRepository;

        mComposite = new CompositeDisposable();
    }

    @Override
    public void takeView(MessageContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getNotification() {
        mComposite.add(remoteRepository.getListNotification()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( list -> {

                    if(list.getData().size() > 0){

                        mView.showDataNotif(list.getData());

                    }

                }, error -> mView.showErrorMessage(CommonUtils.commonErrorFormat(error), 400)));
    }
}
