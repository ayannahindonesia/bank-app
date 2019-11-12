package com.ayannah.asira.screen.agent.loginagent;

import android.content.Intent;
import android.os.Bundle;

import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;

import javax.inject.Inject;

import butterknife.OnClick;

public class LoginAgentFragment extends BaseFragment implements LoginAgentContract.View {

    @Inject
    LoginAgentContract.Presenter mPresenter;

    @Inject
    public LoginAgentFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.agent_fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void initView(Bundle state) {

    }

    @OnClick(R.id.btnLogin)
    void loginAgent() {
        Intent intent = new Intent(parentActivity(), LPAgentActivity.class);
        startActivity(intent);
    }
}
