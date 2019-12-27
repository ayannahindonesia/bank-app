package com.ayannah.asira.screen.chooselogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.ayannah.asira.R;
import com.ayannah.asira.screen.agent.loginagent.LoginAgentActivity;
import com.ayannah.asira.screen.agent.lpagent.LPAgentActivity;
import com.ayannah.asira.screen.borrower.borrower_landing_page.BorrowerLandingPage;
import com.ayannah.asira.screen.borrower.login.LoginActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public class ChooseLoginActivity extends DaggerAppCompatActivity implements ChooseLoginContract.View {

    @Inject
    ChooseLoginContract.Presenter mPresenter;

    @BindView(R.id.btnPersonal)
    ImageView btnPersonal;

    @BindView(R.id.btnAgent)
    ImageView btnAgent;

    private Unbinder mUnbinder;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);

        if (mPresenter.isUserLogged()) {
            loginBorrower();
        } else if (mPresenter.isAgentLogged()) {
            loginAgent();
        }

    }

    private void loginAgent() {
        Intent intent = new Intent(this, LPAgentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void loginBorrower() {
        Intent intent = new Intent(this, BorrowerLandingPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_login);
        mUnbinder = ButterKnife.bind(this);

    }

    @OnClick(R.id.btnAgent)
    void agentFlow() {
//        Toast.makeText(getBaseContext(), "Something BIG is under development", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoginAgentActivity.class);
        intent.putExtra("isBorrower", false);
        startActivity(intent);
    }

    @OnClick(R.id.btnPersonal)
    void borrowerFlow() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("isBorrower", true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
