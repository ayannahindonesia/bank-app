package com.ayannah.asira.screen.agent.lpagent;

import androidx.annotation.Nullable;

import javax.inject.Inject;

public class LPAgentPresenter implements LPAgentContract.Presenter {

    @Nullable
    private LPAgentContract.View mView;

    @Inject
    LPAgentPresenter(){}

    @Override
    public void takeView(LPAgentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {

        mView = null;

    }
}
