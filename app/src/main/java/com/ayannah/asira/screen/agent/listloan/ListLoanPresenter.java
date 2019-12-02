package com.ayannah.asira.screen.agent.listloan;

import com.ayannah.asira.data.local.PreferenceRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ListLoanPresenter implements ListLoanContract.Presenter {

    private ListLoanContract.View mView;

    private CompositeDisposable mComposite;
    private PreferenceRepository preferenceRepository;

    @Inject
    ListLoanPresenter(PreferenceRepository preferenceRepository){
        this.preferenceRepository = preferenceRepository;

        mComposite = new CompositeDisposable();

    }

    @Override
    public void takeView(ListLoanContract.View view) {

        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }
}
